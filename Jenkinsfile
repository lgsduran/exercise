pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'       
            }
        }
        stage('copy the war file to the Tomcat server') {
            environment {
                TOMCAT_CREDS=credentials('pi-ssh-key2')
                TOMCAT_SERVER="172.18.0.3"
                ROOT_WAR_LOCATION="/usr/local/tomcat/webapps"
                WORKSPACE_FOLDER="target"
                ROOT_WAR_FILE="exercise-3.1.1.war"
                REMOTE_WAR_FOLDER="exercise-3.1.1"               
            }
            steps {
                //sh 'sshpass -p $TOMCAT_CREDS_PSW ssh $TOMCAT_CREDS_USR@$TOMCAT_SERVER "/usr/local/tomcat/bin/catalina.sh stop"'   
                sh 'sshpass -p $TOMCAT_CREDS_PSW ssh $TOMCAT_CREDS_USR@$TOMCAT_SERVER "rm -rf $ROOT_WAR_LOCATION/$REMOTE_WAR_FOLDER; rm -f $ROOT_WAR_LOCATION/$ROOT_WAR_FILE"'
                sh 'sshpass -p $TOMCAT_CREDS_PSW scp $WORKSPACE_FOLDER/$ROOT_WAR_FILE $TOMCAT_CREDS_USR@$TOMCAT_SERVER:$ROOT_WAR_LOCATION/'                
                sh 'sshpass -p $TOMCAT_CREDS_PSW ssh $TOMCAT_CREDS_USR@$TOMCAT_SERVER "/usr/local/tomcat/bin/catalina.sh run"'
                //sh 'sshpass -p $TOMCAT_CREDS_PSW ssh $TOMCAT_CREDS_USR@$TOMCAT_SERVER "chown $TOMCAT_CREDS_USR:$TOMCAT_CREDS_USR $ROOT_WAR_LOCATION/ROOT.war"'
            }
        }
    }
}
