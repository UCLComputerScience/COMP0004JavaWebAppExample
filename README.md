# WebAppExample

A minimal Java web application intended for junior developers learning the basics of Java web apps, servlets, and JSPs. The app runs an embedded Tomcat server and serves static resources from `src/main/webapp`.

## Prerequisites

- Java 25 (as configured in `pom.xml`)
- Maven 3.9+

## Project Structure

- `src/main/java` — Java source code (including the embedded Tomcat bootstrap in `uk.ac.ucl.main.Main`)
- `src/main/webapp` — Static web resources and JSPs
- `target` — Build output (created by Maven)
- `war-file` — Packaged WAR output (created by Maven)

## Compile

Build the project and produce a WAR file:

```bash
mvn clean package
```

This writes the WAR to `war-file/`.

## Run (Embedded Tomcat)

First compile the project, then run the main class via Maven:

```bash
mvn clean compile exec:exec
```

By default the server starts on port `8080`. Open:

```
http://localhost:8080
```

## Configuration

You can configure the server using system properties or environment variables:

- `SERVER_PORT` — Port to bind (default: `8080`)
- `WEBAPP_DIR` — Web resources directory (default: `src/main/webapp/`)
- `CLASSES_DIR` — Compiled classes directory (default: `target/classes`)

Example (using environment variables):

```bash
SERVER_PORT=9090 mvn clean compile exec:exec
```

## Notes for Learners

- The entry point is `uk.ac.ucl.main.Main` in `src/main/java/uk/ac/ucl/main/Main.java`.
- Packaging as a WAR is useful if you want to deploy to an external Tomcat later.
