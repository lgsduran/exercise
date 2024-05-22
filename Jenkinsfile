pipeline {
    agent any
    options { ansiColor('xterm') }
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
            agent {
                docker {
                    image 'maven:3.9.3-eclipse-temurin-17'
                    args '--network jenkins_pipeline -e TESTCONTAINERS_HOST_OVERRIDE=host.docker.internal'
                } 
            }
            steps {
                sh 'mvn test'       
            }
        }
        stage('API Test') {
            agent { docker { image 'node:22.2.0-alpine3.18' } }
            environment {
                FOLDER="/var/jenkins_home/workspace/exercise/postman"
                HOSTNAME="http://172.18.0.2"
                PORT="8087"
                APP= "exercise-3.1.1"
            }
            steps {
                sh 'npm install -g newman && npm install -g newman-reporter-html'
                sh 'newman run $FOLDER/Tests.postman_collection.json -e $FOLDER/workspace.postman_globals.json --folder "postman" --global-var "baseUrl=$HOSTNAME:$PORT/$APP/" --disable-unicode -r junit,html --reporter-junit-export var/reports/newman/junit/newman.xml --reporter-html-export var/reports/newman/html/index.html'
                publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'var/reports/newman/html', reportFiles: 'index.html', reportName: 'Newman API Test', reportTitles: ''])
            }       
        }   
    }
}
