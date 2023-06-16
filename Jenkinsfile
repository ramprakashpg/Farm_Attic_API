pipeline {
    agent any

    stages{

        stage('Build'){
            steps {
                sh './gradlew clean build'
            }
        }
        stage("Test"){
            steps{
                sh './gradlew clean test'
            }
        }
    }

     post {
          always {
                cleanWs notFailBuild: true
                  }
          }
}
