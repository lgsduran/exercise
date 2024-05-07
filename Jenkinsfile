pipeline {
    agent any
    stages {
        stage("Unit Testing") {
           agent {
            docker { 
                image 'maven:3.9.3-eclipse-temurin-17'
                args  '-v /var/run/docker.sock:/var/run/docker.sock'
            }
        }
      steps {
        sh 'mvn -B clean verify'
      }
        }
    }
}