pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                //sh 'mvn clean package -DskipTests'
                sh 'pwd'
                sh '$WORSPACE'                
            }
        }
        stage('copy the war file to the Tomcat server') {
            environment {
                TOMCAT_CREDS=credentials('pi-ssh-key2')
                TOMCAT_SERVER="172.18.0.2"
                ROOT_WAR_LOCATION="/home/ubuntu"
            }
            steps {
                sh 'ls -lha target/'
                //sh 'sshpass -p $TOMCAT_CREDS_PSW ssh $TOMCAT_CREDS_USR@$TOMCAT_SERVER "rm -f /home/ubuntu/test.txt"'
                //sh 'sshpass -p $TOMCAT_CREDS_PSW scp -tt $TOMCAT_CREDS_USR@$TOMCAT_SERVER'
            }
        }
    }
}
