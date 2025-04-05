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
        }
    }


    //remove the old war file from the server
    //rm -rf ~/.m2/repository/org/apache/maven/plugins/maven-war-plugin
    //maven clean install
    //student app project requires java 11 version 
    //sudo apt install openjdk-11-jdk -y
    //to update java version  --> sudo update-alternatives --config java

    //build command --> mvn package