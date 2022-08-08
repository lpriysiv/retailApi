# retailApi
Spring boot microservice that holds product data. Hosts a get and a put endpoint to fetch a product by id and update the price of a product.
Get endpoint: `/products/{id}`
Put endpoint: `/products/{id}` with param `price=new price`
The service runs on port 8080

## Project setup steps:

### Mongo Database
The application database and the test databases are hosted on docker containers. To setup the data and run the databases, run the following command.
The application database runs on port 27017 and the test database runs on port 27018.

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
