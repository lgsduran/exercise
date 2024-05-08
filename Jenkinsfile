pipeline {
    agent any
    stages {
        stage("Unit Testing") {
           agent {
            docker { 
                image 'maven:3.9.6-eclipse-temurin-17-alpine'                

                args '-e TESTCONTAINERS_HOST_OVERRIDE=host.docker.internal \
                      -v /var/run/docker.sock:/var/run/docker.sock'
            }
        }
      steps {
        sh '''
              echo "Running as $(whoami):$(getent group $(id -g) | cut -d: -f1) ($(id -u):$(id -g))" 
              mvn test
           '''
      }
        }
    }
}