pipeline {
    agent any
    stages {
        stage("Unit Testing") {
            steps {

                    sh '''docker run \
                          -e DOCKER_HOST=tcp://docker:2376 \
                          -e TESTCONTAINERS_HOST_OVERRIDE=host.docker.internal \
                          -e DOCKER_CERT_PATH=/certs/client \
                          -e DOCKER_TLS_VERIFY=1 \
                          -i \
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