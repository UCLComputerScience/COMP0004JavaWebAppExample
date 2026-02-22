package uk.ac.ucl.main;

// This example program starts an embedded Tomcat server (Tomcat running inside a normal Java main() program).
//
// High-level flow:
//  1) Read configuration (port and directories) from system properties / environment variables.
//  2) Configure logging to both the console and a file.
//  3) Create and configure a Tomcat instance.
//  4) Add a web application (the static web resources) and map compiled classes into /WEB-INF/classes.
//  5) Start Tomcat and block the main thread while the server runs.

// Used for handling I/O failures (e.g., creating the log file).
import java.io.IOException;
// File utilities used to validate that directories exist.
import java.nio.file.Files;
// Represents filesystem paths in a platform-independent way.
import java.nio.file.Path;
// Builds Path objects from Strings.
import java.nio.file.Paths;
// Java's built-in logging API.
import java.util.logging.*;

// Tomcat API: represents the web application context (similar to a deployed webapp in Tomcat).
import org.apache.catalina.Context;
// Tomcat API: abstraction for where Tomcat loads resources (classes, static files) from.
import org.apache.catalina.WebResourceRoot;
// Tomcat API: the main entry point for embedded Tomcat.
import org.apache.catalina.startup.Tomcat;
// Tomcat API: resource set backed by a directory on disk.
import org.apache.catalina.webresources.DirResourceSet;
// Tomcat API: default WebResourceRoot implementation.
import org.apache.catalina.webresources.StandardRoot;

// This class contains only static helper methods and a main() entry point.
// In production code you might encapsulate configuration and server startup into separate classes.
public class Main
{
  // Default HTTP port if the user does not configure one.
  private static final int DEFAULT_PORT = 8080;
  // Default location of static web resources (HTML/CSS/JS) relative to the project root.
  private static final String DEFAULT_WEBAPP_DIR = "src/main/webapp/";
  // Default location Maven uses for compiled .class files.
  private static final String DEFAULT_TARGET_CLASSES = "target/classes";
  // Standard servlet path where a web application's compiled classes live.
  // Mapping our build output here lets Tomcat load our servlets without needing a packaged WAR.
  private static final String WEB_INF_CLASSES = "/WEB-INF/classes";
  // Log file written in the working directory.
  private static final String LOGFILE = "logfile.txt";

  // Read the server port from either:
  //  - a Java system property (-DSERVER_PORT=...) OR
  //  - an environment variable (SERVER_PORT)
  // If neither is provided we fall back to DEFAULT_PORT.
  private static int getPort() {
    // System properties are usually set via JVM arguments; environment variables come from the OS.
    String port = System.getProperty("SERVER_PORT", System.getenv("SERVER_PORT"));
    return (port != null) ? Integer.parseInt(port) : DEFAULT_PORT;
  }

  // Read the webapp directory (static resources) from a system property or environment variable.
  // This allows the same code to run in different environments without recompiling.
  private static String getWebappDir() {
    // Prefer system property; if missing, try environment variable; otherwise default.
    String dir = System.getProperty("WEBAPP_DIR", System.getenv("WEBAPP_DIR"));
    return (dir != null) ? dir : DEFAULT_WEBAPP_DIR;
  }

  // Read the directory containing compiled .class files from a system property or environment variable.
  // For Maven projects, this is typically target/classes.
  private static String getClassesDir() {
    // Prefer system property; if missing, try environment variable; otherwise default.
    String dir = System.getProperty("CLASSES_DIR", System.getenv("CLASSES_DIR"));
    return (dir != null) ? dir : DEFAULT_TARGET_CLASSES;
  }

  // Register a JVM shutdown hook.
  // A shutdown hook runs when the JVM is exiting (e.g., Ctrl+C, SIGTERM, IDE stop button).
  // We use it to stop and destroy Tomcat so resources (threads, sockets) are released cleanly.
  public static Thread addShutdown(final Tomcat tomcat, final Logger logger)
  {
    // The shutdown hook is a Thread. The code in the lambda runs when the JVM begins shutdown.
    Thread shutdownHook = new Thread(() -> {
      try
      {
        // Defensive check: avoid NullPointerException if startup failed before Tomcat was created.
        if (tomcat != null)
        {
          // Stop accepting new requests and shut down internal components.
          tomcat.stop();
          // Release resources held by Tomcat (connectors, threads, classloaders).
          tomcat.destroy();
          logger.info("Tomcat has shut down normally.");
        }
      } catch (Exception e)
      {
        logger.log(Level.SEVERE, "Error shutting down Tomcat", e);
      }
    });
    // Register the hook with the JVM runtime so it will be invoked on shutdown.
    Runtime.getRuntime().addShutdownHook(shutdownHook);
    return shutdownHook;
  }

  // Configure logging for this application.
  // We send logs to:
  //  - the console (useful during development)
  //  - a file (useful for diagnosing issues after the fact)
  private static Logger initialiseLogger()
  {
    // Create a named logger associated with this class.
    Logger logger = Logger.getLogger(Main.class.getName());
    // Disable the default parent handlers to avoid duplicate log lines.
    logger.setUseParentHandlers(false);

    // ConsoleHandler prints log records to standard error (typically shown in the IDE/terminal).
    ConsoleHandler consoleHandler = new ConsoleHandler();
    consoleHandler.setLevel(Level.INFO);
    logger.addHandler(consoleHandler);

    // FileHandler writes log records to a file. Creating it can fail (e.g., permissions),
    // so we handle IOException.
    try {
      // Create/append a log file. By default FileHandler may rotate; here we use a simple single file.
      FileHandler fileHandler = new FileHandler(LOGFILE);
      // SimpleFormatter produces human-readable log lines (timestamp, level, message, etc.).
      fileHandler.setFormatter(new SimpleFormatter());
      fileHandler.setLevel(Level.INFO);
      logger.addHandler(fileHandler);
    } catch (IOException e) {
      logger.log(Level.SEVERE, "Failed to create log file", e);
    }

    // Set the minimum level this logger will publish.
    logger.setLevel(Level.INFO);
    return logger;
  }

  // Create a Tomcat Context for our web application.
  // The Context ties together:
  //  - the URL path ("/") where the app is mounted
  //  - the directory containing the web resources (webappDirectory)
  private static Context getContext(Path webappDirectory, Tomcat tomcat)
  {
    // Validate configuration early and fail fast with a clear error message.
    if (!Files.exists(webappDirectory) || !Files.isDirectory(webappDirectory))
    {
      throw new IllegalArgumentException("Webapp directory does not exist: " + webappDirectory);
    }
    // Mount the web application at the root context path ("/").
    return tomcat.addWebapp("/", webappDirectory.toAbsolutePath().toString());
  }

  // Configure where Tomcat should look for resources.
  // We add the compiled classes directory as a "pre-resource" mapped into /WEB-INF/classes.
  // This is convenient during development: you can run without packaging a WAR.
  private static void setResources(Context context, Path targetClassesDirectory)
  {
    // StandardRoot is Tomcat's default implementation for resource lookup.
    WebResourceRoot resources = new StandardRoot(context);
    // Pre-resources take precedence over resources in the webapp directory.
    // We map the build output directory into /WEB-INF/classes so Tomcat can load servlets/classes.
    resources.addPreResources(new DirResourceSet(resources, WEB_INF_CLASSES,
      targetClassesDirectory.toAbsolutePath().toString(), "/"));
    // Attach the resource configuration to the web application context.
    context.setResources(resources);
  }

  // Application entry point.
  // This method wires everything together and starts the embedded server.
  public static void main(String[] args)
  {
    // Set up logging first so subsequent steps can report progress and errors.
    final Logger logger = initialiseLogger();
    // Read configuration values (with defaults).
    final int port = getPort();
    // Resolve directories into Path objects.
    final Path webappDirectory = Paths.get(getWebappDir());
    final Path targetClassesDirectory = Paths.get(getClassesDir());
    // Create the embedded Tomcat instance.
    final Tomcat tomcat = new Tomcat();

    try
    {
      // Configure the TCP port Tomcat will listen on.
      tomcat.setPort(port);
      // Force creation of the default HTTP connector.
      // Without this, some embedded setups don't fully initialise the connector until start().
      tomcat.getConnector();
      // Ensure Tomcat is stopped cleanly when the JVM exits.
      addShutdown(tomcat, logger);

      // Create the web application context pointing at the web resources directory.
      Context context = getContext(webappDirectory, tomcat);
      // Map compiled classes into the standard /WEB-INF/classes location.
      setResources(context, targetClassesDirectory);

      // Start Tomcat (bind port, start threads, initialise the web application).
      tomcat.start();
      logger.info("Server started successfully on port " + port);
      // Block the main thread so the process stays alive.
      // Tomcat runs request handling on its own threads.
      tomcat.getServer().await();
    }
    // Thrown by our own validation when configuration is incorrect.
    catch (IllegalArgumentException e)
    {
      logger.log(Level.SEVERE, "Configuration error", e);
    }
    // Catch-all for unexpected startup errors. In production code you might handle specific exceptions.
    catch (Exception e)
    {
      logger.log(Level.SEVERE, "Error occurred while starting the server", e);
    }
  }
}