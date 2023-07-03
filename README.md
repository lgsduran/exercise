# Exercise

![](https://img.shields.io/badge/build-success-brightgreen.svg)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

This API project is a result of an execise

## Stacks

- [Java 11](http://www.oracle.com/technetwork/java/javase/downloads)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Maven](https://maven.apache.org)
- [MariaDB](https://mariadb.org)
- [springdoc-openapi](https://springdoc.org)

## Sample User

#### POST Request sample
```json
[
    "name1",
    "name2",
    "name3",
    "name4"
]
```

#### Response
```json
[
    {
        "id": 1,
        "name": "name1",
        "created_At": "2023-07-03T15:26:28.853045300Z"
    },
    {
        "id": 2,
        "name": "name2",
        "created_At": "2023-07-03T15:26:28.853045300Z"
    },
    {
        "id": 3,
        "name": "name3",
        "created_At": "2023-07-03T15:26:28.853045300Z"
    },
    {
        "id": 4,
        "name": "name4",
        "created_At": "2023-07-03T15:26:28.853045300Z"
    }
]
```

## Swagger Docs

The project has been configured with a basic Swagger that exposes the commonly used API's along with the expected parameters.

![ScreenShot](/image/openAPI3.png)

Once the API is up & running, click on **[here](http://localhost:8081/swagger-ui/index.html)** to be redirected to the documentation.

## Copyright

Released under the Apache License 2.0. See the [LICENSE](https://github.com/codecentric/springboot-sample-app/blob/master/LICENSE) file.
