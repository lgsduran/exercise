node {
    stage "Prepare environment"
        checkout scm
        def environment  = docker.image ('maven:3.9.3-eclipse-temurin-17').withRun('--network jenkins_pipeline -e TESTCONTAINERS_HOST_OVERRIDE=host.docker.internal')

        environment.inside {
            stage "Checkout and build deps"
                sh " apt-get update -q && apt-get install -qy --no-install-recommends openssh-server sshpass iputils-ping net-tools"
                sh "mvn test"
                sh "ifconfig"
        }

    stage "Cleanup"
        deleteDir()
}