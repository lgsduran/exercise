pipeline {
    agent any
    stages {
        stage("Unit Testing") {
            steps {
                    sh '''docker run \
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