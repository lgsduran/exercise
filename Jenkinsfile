pipeline {
    agent any
    stages {
        stage('Build deps') {
            agent {
                docker {
                    image 'maven:3.9.3-eclipse-temurin-17'
                    args '--network jenkins_pipeline -e TESTCONTAINERS_HOST_OVERRIDE=host.docker.internal'
                }
            }
            stages {
                 stage('Build') {
                    steps {
                        //sh 'mvn clean package -DskipTests'
                        sh 'echo test'
                    }
                }
                stage('copy the war file to the Tomcat server') {
                    environment {
                        TOMCAT_CREDS=credentials('pi-ssh-key2')
                        TOMCAT_SERVER="172.18.0.2"
                        ROOT_WAR_LOCATION="/home/ubuntu"
                    }
                    steps {
                        sh 'apt-get update -q && apt-get install -qy --no-install-recommends openssh-server sshpass iputils-ping net-tools'
                        sh 'apt-get clean && rm -rf /var/lib/apt'
                        sh 'ifconfig'
                        sh 'ping -c 5 172.18.0.2'
                        //sh 'sshpass -p $TOMCAT_CREDS_PSW ssh $TOMCAT_CREDS_USR@$TOMCAT_SERVER "rm -f /home/ubuntu/test.txt"'
                        //sh 'sshpass -p $TOMCAT_CREDS_PSW ssh -tt $TOMCAT_CREDS_USR@$TOMCAT_SERVER'
                    }
                }
            }
        }       
    }
}
