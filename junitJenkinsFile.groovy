pipeline {
    post {
        always {
            deleteDir()
            emailext body: "${currentBuild.currentResult}: Job ${env.JOB_NAME} build ${env.BUILD_NUMBER}\n More info at: ${env.BUILD_URL}",
                        recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
                        subject: "Jenkins Build ${currentBuild.currentResult}: Job ${env.JOB_NAME}",
                        to: '$DEFAULT_REPLYTO'
        }
    }
    agent any
    stages {
        stage('clone and clean repo') {
            steps {
                bat 'git clone https://github.com/majallouz/TimeSheet.git'
                bat 'mvn clean -f TimeSheet'
            }
        }
        stage('Test') {
            steps { bat 'mvn test -f TimeSheet'
            }
            post {
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
            }
        }

        stage('Deploy') {
            steps {
                bat 'mvn package -f TimeSheet'
                bat 'mvn deploy -f TimeSheet'
                bat 'mvn sonar:sonar -f TimeSheet'
            }
        }
    }
}
