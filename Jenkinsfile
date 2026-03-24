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
        stage('Debug') {
           steps {
            sh 'echo BRANCH_NAME=$BRANCH_NAME'
            sh 'echo GIT_BRANCH=$GIT_BRANCH'
    }
}
        stage('Main Branch') {
            when {
                branch 'main'
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
                branch 'feature'
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
