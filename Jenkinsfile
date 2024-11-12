pipeline{
   agent any
    environment {
           DOCKER_CREDENTIALS_ID = 'jenkinspipeline2'  // Docker 레지스트리의 인증 정보 ID
           IMAGE_NAME = 'dragonhailstone/jenkinspipeline2'  // Docker 이미지 이름
           TAG = 'latest'  // 태그 설정
           REGISTRY_URL = 'http://3.36.131.51:5000'
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
        stage('Login to Docker Registry') {
            steps {
                script {
                    // Docker 레지스트리 로그인
                    docker.withRegistry("$REGISTRY_URL", "$DOCKER_CREDENTIALS_ID") {
                        // 로그인이 성공하면 계속 진행
                    }
                }
            }
        }
stage('Push Docker Image') {
            steps {
                script {
                    // Docker 이미지를 EC2 Registry로 푸시
                    sh 'docker push $REGISTRY_URL/$IMAGE_NAME:$TAG'
                }
            }
        }
    }
   }
}