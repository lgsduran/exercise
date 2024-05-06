pipeline {
    agent any
    stages {
        stage("Unit Testing") {
            steps {

                    sh '''
                        docker run \
                        -v /var/run/docker.sock:/var/run/docker.sock \
                        maven:latest \
                        mvn verify
                        '''                
            }
        }
    }
}