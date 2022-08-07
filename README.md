# retailApi
Simple microservice that holds product data. Hosts a get and a put endpoint to fetch and update data

## Project setup steps:

### Mongo Database
The database is hosted on a docker container. To setup and run the database, run the following command.
Mongo database runs on port 27017

```
docker-compose up -d
```

### Build 
To build the project using the gradle configuration, run gradle build from project root.

```
./gradlew build
```

### Test
Tests can be run and coverage reports are generated as html document under `build/jacocoHtml/index.html`
```
./gradlew test
```

### Run the application using gradle
```
./gradlew bootRun
```

### Run the application using the built jar file
```
java -jar build/libs/casestudy-0.0.1-SNAPSHOT.jar
```

### Api docs
Api docs are auto generated using Swagger-3 and OpenApi at the url `/swagger-ui.html`
