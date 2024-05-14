node {
    checkout scm
    /*
     * In order to communicate with the MySQL server, this Pipeline explicitly
     * maps the port (`3306`) to a known port on the host machine.
     */
    docker.image('maven:3.9.3-eclipse-temurin-17').withRun('--network jenkins_pipeline'+
                                           ' -e "TESTCONTAINERS_HOST_OVERRIDE=host.docker.internal"') { c ->
        stage "Checkout and build deps"
                sh " apt-get update -q && apt-get install -qy --no-install-recommends openssh-server sshpass iputils-ping net-tools"
                sh "mvn test"
    }
}

node {
    stage "Prepare environment"
        checkout scm
        def environment  = docker.image ('maven:3.9.3-eclipse-temurin-17')

        environment.inside {
            stage "Checkout and build deps"
                sh " apt-get update -q && apt-get install -qy --no-install-recommends openssh-server sshpass iputils-ping net-tools"
                sh "mvn test"
        }

    stage "Cleanup"
        deleteDir()
}