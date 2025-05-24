pipeline {
    agent any
    stages {
        stage('Git Checkout') {
            steps {
                git branch: 'main', changelog: false, credentialsId: '40b19253-cfc7-44ff-b897-b9079469aa04', poll: false, url: 'git@github.com:Dragalone/event-manager-backend.git'
            }
        }
        stage('Build'){
            steps {
                sh "./gradlew clean build -x test"
            }
        }
        stage('Docker Build & Push'){
            steps{
                script {
                    withCredentials([usernamePassword(credentialsId: '8ca74603-54b9-47f0-9357-fd63d74c848d', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
                        sh "docker login -u ${env.USERNAME} -p ${env.PASSWORD}"
                        sh "docker build -t quickreg-backend -f Dockerfile ."
                        sh "docker tag quickreg-backend dragalone/quickreg-backend:latest"
                        sh "docker push dragalone/quickreg-backend:latest"

                    }
                }
            }
        }
        stage('Deploy'){
            steps {
                sh "docker-compose up -d --force-recreate event-manager-backend"
            }
        }
    }
}