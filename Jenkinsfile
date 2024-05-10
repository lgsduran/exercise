pipeline {
    agent any
    stages {
       stage('Prune Docker data') {
        steps {
          sh 'docker system prune -a --volumes -f'
        }
    }
        stage("Unit Testing") {
           agent {
            docker {
                image 'maven:3.9.6-eclipse-temurin-17-alpine'
                args '-e TESTCONTAINERS_HOST_OVERRIDE=host.docker.internal'                      
            }
        }
          steps {
            sh 'mvn test'       
          }
        }
    }
}