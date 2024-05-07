pipeline {
    agent any
    stages {
        stage("Unit Testing") {
           agent {
            docker { 
                image 'maven:3.9.6-eclipse-temurin-17-alpine'
                args '-v $PWD:$PWD'
                args '-w $PWD'
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