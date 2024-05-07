pipeline {
    agent any
    stages {
        stage("Unit Testing") {
           agent {
            docker { 
                image 'maven:3.9.6-eclipse-temurin-17-alpine'                
                

            }
        }
      steps {
        sh 'mvn test'
      }
        }
    }
}