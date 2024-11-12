pipeline{
   agent any
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
        stage("Image Build & Push Docker Images"){
            steps{
                echo 'Image Build & Image Push'
                withCredentials([usernamePassword(
                    credentialsId: DOCKER_HUB_CREDENTIAL_ID,
                    usernameVariable: 'dragonhailstone'
                )]){
                    script{
                        docker.withRegistry(
                            DOCKER_HUB_FULL_URL, DOCKER_HUB_CREDENTIAL_ID
                        )
                    }
                }
            }
        }
        stage("Docker Image Build"){
              steps{
                  sh  "echo docker builds"
                  sh "docker build -t jenkinspipeline2 ."
              }
        }
   }
}