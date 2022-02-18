pipeline {


    agent any
    stages {
         stage('clone and clean repo') {
            steps {
                git changelog: false, branch: 'Malek_branch',  credentialsId: 'mgara07', poll: false, url: 'https://github.com/majallouz/TimeSheet.git'
                /* bat 'git clone https://github.com/majallouz/TimeSheet.git'
                bat 'mvn clean -f TimeSheet' */
            }
        }
        stage('Test') {
            steps { 
                bat 'mvn test'
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
                bat 'mvn sonar:sonar'
            }
        }
        stage('Deploy') {
            steps {
                bat 'mvn package deploy '
            }
        }
        
        stage('clean ws') {

            steps {
                    cleanWs()
            }

        }
        stage('Building image') {

        steps {

          script {

            dockerImage = docker.build registry + ":$BUILD_NUMBER"

          }

        }

      }

    
    stage('Deploy image') {

      steps {

        script {

          docker.withRegistry('', registryCredential) {

            dockerImage.push()

          }

        }

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
