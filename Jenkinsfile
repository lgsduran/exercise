pipeline {
    agent any
    stages {
        stage("Unit Testing") {
           agent {
            docker { 
                image 'maven:3.9.6-eclipse-temurin-17-alpine'                


            }
        }
      steps {
        sh '  -i \
              --rm \
              -v $PWD:$PWD \
              -w $PWD \
              -v /var/run/docker.sock:/var/run/docker.sock \
              mvn verify'
      }
        }
    }
}