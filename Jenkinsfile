pipeline {
    agent any
    
    stages {
        stage('Code Checkout'){
          steps {
            checkout scm
              }
            }
        stage('Install Dependencies') {
            steps {
                sh 'npm install'
            }
        }
        stage('Main Branch') {
            when {
                expression {
                    env.GIT_BRANCH?.contains('main')
                }
            }
            steps {
                echo "Build the Code"
                sh '''
                npm run build
                sshpass -p 'm' rsync -avz -e 'ssh -o StrictHostKeyChecking=no' -r ./dist/ manish@192.168.10.158:/var/www/main/
                '''
            }
        }
        stage('Feature Branch') {
            when {
                expression {
                    env.GIT_BRANCH?.contains('feature')
                }
            }
            steps {
                echo "Build the Code"
                sh '''
                npm run build
                sshpass -p 'm' rsync -avz -e 'ssh -o StrictHostKeyChecking=no' -r ./dist/ manish@192.168.10.158:/var/www/feature/
                '''
            }
        }
    }
}
