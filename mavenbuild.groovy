    pipeline{
        agent {label 'webserver'}
        stages{
            stage('pull code'){
                steps{
                    echo 'clonnig a repository...'
         
                    git 'https://github.com/AnupDudhe/studentapp-ui.git'
                }
            }
            
            stage('maven build') {
                steps{
                    echo 'building a maven project...'
                    sh 'mvn package'
                }
            }

            stage ('deploy'){
                steps{
                    echo 'deploying a war file...'
                    sh '''

                    sudo curl -O https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.102/bin/apache-tomcat-9.0.102.zip
                    sudo mv apache-tomcat-9.0.102.zip /opt/
                    sudo apt-get install unzip -y
                    sudo unzip  /opt/apache-tomcat-9.0.102.zip -d /opt/
                    sudo rm -rf /opt/apache-tomcat-9.0.102.zip
                    sudo mv    /home/ubuntu/workspace/studentapp/target/studentapp-2.2-SNAPSHOT.war /opt/apache-tomcat-9.0.102/webapps/studentapp.war
                    sudo curl -O https://s3-us-west-2.amazonaws.com/studentapi-cit/mysql-connector.jar 
                    sudo mv mysql-connector.jar /opt/apache-tomcat-9.0.102/lib/mysql-connector.jar
                    sudo bash /opt/apache-tomcat-9.0.102/bin/catalina.sh stop  
                    sudo bash /opt/apache-tomcat-9.0.102/bin/catalina.sh start 

                    sh '''
                }
            }
        }
    }


    //remove the old war file from the server
    //rm -rf ~/.m2/repository/org/apache/maven/plugins/maven-war-plugin
    //maven clean install
    //student app project requires java 11 version 
    //sudo apt install openjdk-11-jdk -y
    //to update java version  --> sudo update-alternatives --config java

    //build command --> mvn package