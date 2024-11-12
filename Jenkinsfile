pipeline{
   agent any
    environment {
           DOCKER_CREDENTIALS_ID = 'jenkinspipeline2'  // Docker 레지스트리의 인증 정보 ID
           IMAGE_NAME = 'jenkinspipeline2'  // Docker 이미지 이름
           TAG = 'latest'  // 태그 설정
           REGISTRY_URL = '3.36.131.51:5000'
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
        stage("Docker Image Build"){
              steps{
                  sh "docker build -t $REGISTRY_URL/$IMAGE_NAME:$TAG ."
              }
        }
stage('Push Docker Image') {
            steps {
                    // Docker 이미지를 EC2 Registry로 푸시
                    sh 'docker push $REGISTRY_URL/$IMAGE_NAME:$TAG'
            }
        }
    }
}