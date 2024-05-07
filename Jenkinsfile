pipeline {
    agent any
    stages {
        stage("Unit Testing") {
           agent {
            docker { 
                image 'maven:3.9.6-eclipse-temurin-17-alpine'
                args '-v $HOME/.m2:/root/.m2'
            }
        }
      steps {
        sh 'mvn verify'
      }
        }
    }
}