pipeline {
    agent any
    stages {
        stage("Unit Testing") {
           agent {
            docker { 
                image 'maven:latest'
      

            }
        }
      steps {
        sh 'mvn verify'
      }
        }
    }
}