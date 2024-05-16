pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                //sh 'mvn clean package -DskipTests'
                sh 'pwd'
                sh '$WORKSPACE'
                sh 'whoami'        
            }
        }
        stage('copy the war file to the Tomcat server') {
            environment {
                TOMCAT_CREDS=credentials('pi-ssh-key2')
                TOMCAT_SERVER="172.18.0.2"
                ROOT_WAR_LOCATION="/home/ubuntu"
            }
            steps {                
                sh 'sshpass -p $TOMCAT_CREDS_PSW ssh $TOMCAT_CREDS_USR@$TOMCAT_SERVER rm -f $ROOT_WAR_LOCATION/test.txt'
                sh 'sshpass -p $TOMCAT_CREDS_PSW scp target/exercise-0.0.1-SNAPSHOT.war $TOMCAT_CREDS_USR@$TOMCAT_SERVER:$ROOT_WAR_LOCATION/'
            }
        }
    }
}
