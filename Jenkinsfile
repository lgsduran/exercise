node {
    checkout scm
    
    docker.image('maven:3.9.6-eclipse-temurin-17-alpine')          
          .inside() {
                    

        sh 'mvn test'
    }
}