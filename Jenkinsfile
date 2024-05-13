pipeline {
  agent {
    docker {
      image 'maven:3.9.3-eclipse-temurin-17'
      args '-e TESTCONTAINERS_HOST_OVERRIDE=host.docker.internal'
    } 
  }
  environment {
    TOMCAT_CREDS=credentials('pi-ssh-key')
    TOMCAT_SERVER="172.18.0.3"
  }           
  stages {
    /**stage('Build') {
          steps {
            sh 'pwd'
            sh 'mvn clean package -DskipTests'
          }
    }**/
    stage('copy the war file to the Tomcat server') {
      steps {
        sh 'whoami'
        sh 'apt-get update -q'
        sh 'apt-get install -qy --no-install-recommends openssh-server'
        sh 'ls -lha /usr/bin/'        
        sh 'ssh ubuntu@$TOMCAT_SERVER "/usr/local/tomcat/bin/catalina.sh stop"'
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