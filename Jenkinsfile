pipeline {
    agent any
    stages {
        stage("Unit Testing") {
           agent {
            docker { 
                image 'maven:3.9.3-eclipse-temurin-17'
                

            }
        }
      steps {
        sh 'mvn verify'
      }
        }
    }
}