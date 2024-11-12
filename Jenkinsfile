pipeline{
   agent any
    environment {
           DOCKER_CREDENTIALS = 'jenkinspipeline2'  // Docker 레지스트리의 인증 정보 ID
       }
   stages{
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
                  sh 'docker build -t dragonhailstone/jenkinspipeline2:2 .'
              }
        }
        stage('Login to Docker Hub') {
            steps {
                script {
                    // Docker Hub에 로그인 (비상호작용 방식)
                    docker.withRegistry('https://index.docker.io/v1/', "$DOCKER_CREDENTIALS") {
                        // 로그인 후에 이미지 푸시
                    }
                }
            }
        }
         stage('docker hub push'){
            steps{
                sh 'docker push dragonhailstone/jenkinspipeline2:2'
            }
         }
    }
}