pipeline {
    agent any
    options { ansiColor('xterm') }
    environment {
                TOMCAT_SERVER="172.18.0.4"
                PORT="8090"
            }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'       
            }
        }
        stage('Deploy to Tomcat server') {
            environment {
                TOMCAT_CREDS=credentials('pi-ssh-key2')                
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
            // agent { 
            //     docker { 
            //         image 'node:latest'
            //         args '--network jenkins_pipeline -e TESTCONTAINERS_HOST_OVERRIDE=host.docker.internal'
            //     }
            // }
            environment {
                FOLDER="/var/jenkins_home/workspace/exercise/postman"
                APP= "exercise-3.1.1"
            }
            steps {
                sh 'apt update && apt install npm nodejs -y'
                sh 'npm install -g newman && npm install -g newman-reporter-html && npm install -g newman-reporter-openapi && npm install -g newman-reporter-postman-cloud && npm install -g newman-reporter-xunit'
                sh 'newman run $FOLDER/Tests.postman_collection.json -g $FOLDER/workspace.postman_globals.json --env-var "baseUrl=$TOMCAT_SERVER:$PORT/$APP" --disable-unicode --no-color ----reporters cli,html,junit --report-junit-export "target/pipelineReport.xml"--reporter-html-export "target/pipelineReport.html"'                
            }       
        }   
    }
 post { 
        always { 
           //publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'var/reports/newman/html', reportFiles: 'index.html', reportName: 'Newman API Test', reportTitles: ''])
            publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'target', reportFiles: 'index.html', reportName: 'HTML Report', reportTitles: ''])
            // publish junit test results
            //junit 'newman/*.xml'
        }
    }
}
