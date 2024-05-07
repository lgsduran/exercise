pipeline {
    agent any
    stages {
        stage("Unit Testing") {
           agent {
            docker { 
                image 'maven:latest'
                args  '-i'             

            }
        }
      steps {
        sh 'mvn verify'
      }
        }
    }
}