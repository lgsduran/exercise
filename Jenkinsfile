pipeline {
    agent any
    stages {
        stage("Unit Testing") {
           agent {
            docker { 
                image 'maven:latest'
                args  '-v /var/run/docker.sock:/var/run/docker.sock'             

            }
        }
      steps {
        sh 'mvn verify'
      }
        }
    }
}