node {
    checkout scm
    
    docker.image('maven:3.9.6-eclipse-temurin-17-alpine')
          .withRun(' -v /var/run/docker.sock:/var/run/docker.sock')
          .inside() {
                    

        sh 'mvn test'
    }
}