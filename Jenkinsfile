pipeline{
   agent any
    environment {
           DOCKER_CREDENTIALS = credentials('jenkinspipeline2')  // Docker 레지스트리의 인증 정보 ID
       }
   stages{
       stage("Set Variables"){
            steps{
            sh "echo SetVariables"

            script{
                DOCKER_HUB_URL = 'registry.hub.docker.com'
                DOCKER_HUB_FULL_URL = 'https://' + DOCKER_HUB_URL
                DOCKER_HUB_CREDENTIAL_ID = 'docker-hub'
                }
            }
       }
       stage("Permission"){
           steps{
               sh "chmod +x ./gradlew"
           }
       }
       stage("Compile"){
           steps{
               sh "./gradlew compileJava"
           }
       }
       stage("Unit Test"){
           steps{
               sh "./gradlew test"
            }
       }
       stage("Code Coverage"){
            steps{
                sh "./gradlew jacocoTestCoverageVerification"
                sh "./gradlew jacocoTestReport"
                publishHTML(target: [
                reportDir: 'build/reports/jacoco/test/html',
                reportFiles: 'index.html',
                reportName: 'Jacoco Report'
                ])
            }
       }
        stage("Gradle Build"){
              steps{
                  sh "./gradlew clean build"
              }
        }
        stage("docker image build"){
               steps{
                  sh 'docker build -t dragonhailstone/jenkinspipeline2 .'
              }
        }
        stage('docker hub login'){
               steps{
                  sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
               }
        }
    }
}