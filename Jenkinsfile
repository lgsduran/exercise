pipeline {
    agent none
    stages {
        stage("Unit Testing") {
            steps {
                script {
                    sh '''docker run \
                          -e TESTCONTAINERS_HOST_OVERRIDE=host.docker.internal \
                          -it \
                          --rm \
                          -v $PWD:$PWD \ 
                          -w $PWD \ 
                          -v /var/run/docker.sock:/var/run/docker.sock \
                          maven:latest \
                          mvn verify'''
                }
            }
        }
    }
}