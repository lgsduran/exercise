node {
    checkout scm
    
    def imageTest = docker.image('maven:3.9.6-eclipse-temurin-17-alpine')
        imageTest.withRun('-e "TESTCONTAINERS_HOST_OVERRIDE=host.docker.internal"' +
                   ' -v /var/run/docker.sock:/var/run/docker.sock')
        imageTest.inside {
        sh 'make test'
    }

    
}