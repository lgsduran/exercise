pipeline {
    agent any
    stages {
        stage("Unit Testing") {
           agent {
            docker { 
                image 'maven:latest'
                args '-v $HOME/.m2:/root/.m2'
            }
        }
      steps {
        sh 'mvn verify'
      }
        }
    }
}