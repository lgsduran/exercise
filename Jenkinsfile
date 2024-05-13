pipeline {
  agent {
    docker {
      image 'maven:3.9.3-eclipse-temurin-17'
      args '--network jenkins_pipeline -e TESTCONTAINERS_HOST_OVERRIDE=host.docker.internal'
    } 
  }
  environment {
    TOMCAT_CREDS=credentials('pi-ssh-key')
    TOMCAT_SERVER="172.18.0.3"
  }           
  stages {
    /**stage('Build') {
          steps {
            sh 'mvn clean package -DskipTests'
          }
    }**/
    stage('copy the war file to the Tomcat server') {
      steps {
        sh 'apt-get update -q && apt-get install -qy --no-install-recommends openssh-server sshpass iputils-ping net-tools'    
        sh 'ls -lha /usr/bin/'
        sh 'ifconfig'
        sh 'sshpass -p ubuntu ssh ubuntu@$TOMCAT_SERVER'
      }
    }

    /**stage('Start container') {
      steps {
        sh 'COMPOSE_PROFILES=all docker compose up -d --quiet-pull --wait'
        sh 'docker-compose ps'
      }
    }**/
  }
}