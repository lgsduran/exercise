node {
    checkout scm
    /*
     * In order to communicate with the MySQL server, this Pipeline explicitly
     * maps the port (`3306`) to a known port on the host machine.
     */
    docker.image('maven:latest').withRun('-v "/var/run/docker.sock:/var/run/docker.sock"' +
                                           ' -p 3306:3306') { c ->
       
        sh 'mvn verify'
    }
}