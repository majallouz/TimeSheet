pipeline {
agent any
environment {
 
    mvnHome = tool 'MAVEN_3.3.9'
    registry = "yasmineboutrif/timesheet" 
    registryCredential = 'dockerHub' 
    dockerImage = '' 
   
}
stages{
    
stage('clone and clean repo'){
    steps {

        git changelog: false, branch: 'yasmine_branch',  credentialsId: 'yboutrif', poll: false, url: 'https://github.com/majallouz/TimeSheet.git'
                /* bat 'git clone https://github.com/majallouz/TimeSheet.git'*/
 bat "${mvnHome}/bin/mvn clean"
    }

}


stage('Test') {

            steps {

                bat "${mvnHome}/bin/mvn test"

                /* bat 'mvn test -f TimeSheet' */

            }

            post {

                always {

                    junit '**/target/surefire-reports/TEST-*.xml'

                }

            }

        }

        stage('Sonar') {

            steps {

                bat "${mvnHome}/bin/mvn sonar:sonar"

            }

        }

        stage('Deploy') {

            steps {

                bat "${mvnHome}/bin/mvn package deploy "

            }

        }
        
                stage('Building our image') { 

            steps { 

                script { 

                    dockerImage = docker.build registry + ":$BUILD_NUMBER" 

                }

            } 

        }
        
        
        stage('Deploy our image') { 
            steps { 

                script { 

                    docker.withRegistry( '', registryCredential ) { 

                        dockerImage.push() 

                    }

                } 

            }

        } 
        
       stage('Cleaning up') { 

            steps { 

                bat "docker rmi $registry:$BUILD_NUMBER" 

            }

        } 

       

        stage('clean ws') {



            steps {

                    cleanWs()

            }



        }

    }

     post {

        always {            

            emailext body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",

                        recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],

                        subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}",

                        to: '$DEFAULT_RECIPIENTS'

        }

    } 

}