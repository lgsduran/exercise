node {
    checkout scm    
    docker.image('maven:3.9.6-eclipse-temurin-17-alpine')
          .withRun('-e "TESTCONTAINERS_HOST_OVERRIDE=host.docker.internal"' +
                   ' -v /var/run/docker.sock:/var/run/docker.sock') 
                   { c ->      

        sh 'mvn test'
    }
}