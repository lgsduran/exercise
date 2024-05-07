pipeline {
    agent any
    stages {
        stage("Unit Testing") {
           agent {
            docker { 
                image 'maven:3.9.6-eclipse-temurin-17-alpine'                

                args '''
                        -i \
                        --rm \
                        -w $PWD \
                        -v /var/run/docker.sock:/var/run/docker.sock \
                        -v /var/jenkins_home/workspace/exercise:/var/jenkins_home/workspace/exercise
                        '''             

            }
        }
      steps {
        sh 'mvn verify'
      }
        }
    }
}