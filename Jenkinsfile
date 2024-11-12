pipeline{
   agent any
    environment {
           DOCKERHUB_CREDENTIALS = credentials('jenkinspipeline2')  // Docker 레지스트리의 인증 정보 ID
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
                  sh 'docker build -t dragonhailstone/jenkinspipeline2:3 .'
              }
        }
        stage('docker hub login'){
               steps{
                  sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
               }
        }
         stage('docker hub push'){
            steps{
                sh 'docker push dragonhailstone/jenkinspipeline2:3'
            }
         }
         stage('deploy'){
            steps{
                sh 'docker run -d --rm 8765:8080 jenkinspipeline2 dragonhailstone/jenkinspipeline2'
            }
         }
    }
}