# Alura Challenge Back-End

![](https://img.shields.io/badge/build-success-brightgreen.svg)
[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

This API project is a result of [Alura Challenge Back-End](https://www.alura.com.br/challenges/back-end-2) whose mainly propose was to manage a budget.

## Stacks

- [Java 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Maven](https://maven.apache.org)
- [MariaDB](https://mariadb.org)
- [springdoc-openapi](https://springdoc.org)

## Deploying the application to AWS
Click **[here](http://budget-env.eba-tipzkj2s.sa-east-1.elasticbeanstalk.com/swagger-ui/index.html#/)** to access the API.

## Sample User

#### Request
```json
{
   "username": "test",
   "password":"123456"
}
```

#### Response
```json
{
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNjQ0Njc0NDc4LCJleHAiOjE2NDQ3NjA4Nzh9.dIz7oA0b8cuVWjHq-yjFfDA7hEaun3W3lrYdBqKRGdeZ239oq-1yLSt6SQceNWlAMxGm5vC0qg7XOghRO1iILw",
    "type": "Bearer",
    "id": 5,
    "username": "test",
    "email": "test@gmail.com",
    "roles": [
        "USER"
    ]
}
```

## Swagger Docs

The project has been configured with a basic Swagger that exposes the commonly used API's along with the expected params.

![ScreenShot](/images/openAPI3.png)

Click **[here](http://budget-env.eba-tipzkj2s.sa-east-1.elasticbeanstalk.com/swagger-ui/index.html#/)** to be redirected to the documentation.

## Copyright

Released under the Apache License 2.0. See the [LICENSE](https://github.com/codecentric/springboot-sample-app/blob/master/LICENSE) file.
