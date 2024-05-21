pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'       
            }
        }
        stage('Deploy to Tomcat server') {
            environment {
                TOMCAT_CREDS=credentials('pi-ssh-key2')
                TOMCAT_SERVER="172.18.0.2"
                ROOT_WAR_LOCATION="/usr/local/tomcat/webapps"
                BUILD_PATH="target"
                BUILT_WAR_FILE="exercise-3.1.1.war"
                REMOTE_WAR_FOLDER="exercise-3.1.1"               
            }
            steps {
                // sh 'sshpass -p $TOMCAT_CREDS_PSW ssh $TOMCAT_CREDS_USR@$TOMCAT_SERVER "/usr/local/tomcat/bin/catalina.sh stop"'   
                sh 'sshpass -p $TOMCAT_CREDS_PSW ssh $TOMCAT_CREDS_USR@$TOMCAT_SERVER "rm -rf $ROOT_WAR_LOCATION/$REMOTE_WAR_FOLDER; rm -f $ROOT_WAR_LOCATION/$BUILT_WAR_FILE"'
                sh 'sshpass -p $TOMCAT_CREDS_PSW scp $BUILD_PATH/$BUILT_WAR_FILE $TOMCAT_CREDS_USR@$TOMCAT_SERVER:$ROOT_WAR_LOCATION/'
                // sh 'sshpass -p $TOMCAT_CREDS_PSW ssh $TOMCAT_CREDS_USR@$TOMCAT_SERVER "chown $TOMCAT_CREDS_USR:$TOMCAT_CREDS_USR $ROOT_WAR_LOCATION/$BUILT_WAR_FILE"'              
                sh 'sshpass -p $TOMCAT_CREDS_PSW ssh $TOMCAT_CREDS_USR@$TOMCAT_SERVER "/usr/local/tomcat/bin/catalina.sh start"'
            }
        }
        stage('Unit Test') {
            steps {
                sh 'mvn test'       
            }
        }
    }
}
