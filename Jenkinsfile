pipeline {
    agent any
    stages {
        stage("Unit Testing") {
           agent {
            docker { 
                image 'maven:latest'
                args  '--rm -i -v "/var/run/docker.sock:/var/run/docker.sock" "$PWD:$PWD" -w "$PWD"'
            }
        }
      steps {
        sh 'mvn -B clean verify'
      }
        }
    }
}