pipeline {
    agent any

    environment {
        IMAGE_NAME = 'tondockerhub/servicetask'
        TAG = "${env.BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/JKR23/ToDo_TaskService.git'
            }
        }

        stage('Build Maven') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t $IMAGE_NAME:$TAG ."
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([string(credentialsId: 'docker-hub-pass', variable: 'DOCKER_PASS')]) {
                    sh "echo $DOCKER_PASS | docker login -u tondockerhub --password-stdin"
                    sh "docker push $IMAGE_NAME:$TAG"
                }
            }
        }

        stage('Deploy (optional)') {
            steps {
                echo 'Déploiement vers Kubernetes ici si prêt...'
            }
        }
    }
}
