pipeline {
    agent any
    stages {
        stage("Unit Testing") {
           agent {
            docker { 
                image 'maven:latest'
                args '-v $HOME/.m2:/root/.m2'
                args '-v /var/run/docker.sock:/var/run/docker.sock'             

            }
        }
      steps {
        sh 'mvn verify'
      }
        }
    }
}