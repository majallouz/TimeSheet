pipeline {
  agent any
  environment {

    mvnHome = tool 'maven_3.3.3'
    registry = "mohamedalijallouz/timesheet"
    registryCredential = 'dockerHub'
    dockerImage = ''

  }
  stages {

    stage('clone and clean repo') {
      steps {

        git changelog: false, branch: 'Dali_branch', credentialsId: 'majallouz', poll: false, url: 'https://github.com/majallouz/TimeSheet.git'
        /* bat 'git clone https://github.com/majallouz/TimeSheet.git'*/
        bat "${mvnHome}/bin/mvn clean" 
      }

    }
    stage('Test') {

      steps {
        bat "${mvnHome}/bin/mvn test"
      }
      post {
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
            }
    }
    stage('sonar'){
        steps{
                bat "${mvnHome}/bin/mvn sonar:sonar"
    
        }
    }
    stage('Deploy') {
      steps {

        bat "${mvnHome}/bin/mvn package"
        bat "${mvnHome}/bin/mvn deploy"

      }
    }
    stage('Cleaning up') { 

            steps { 

                bat "docker rmi $registry:$BUILD_NUMBER" 

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
    

    stage('clean workspace') {

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