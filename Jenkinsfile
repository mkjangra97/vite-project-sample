@Library('library-01') _

pipeline {
    agent any

    stages {
        stage('Code Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Install Dependencies') {
            steps {
                script {
                    // ✅ If-Else for npm install
                    if (env.BRANCH_NAME?.contains('main')) {
                        echo "📦 Main branch — Production install (no devDependencies)"
                        sh 'npm install'

                    } else if (env.BRANCH_NAME?.contains('feature')) {
                        echo "📦 Feature branch — Full install with devDependencies"
                        sh 'npm install'

                    } else {
                        echo "⚠️ Unknown branch — Default install"
                        sh 'npm install'
                    }
                }
            }
        }

        stage('Build & Deploy') {
            steps {
                script {
                    if (env.BRANCH_NAME?.contains('main')) {
                        echo "🚀 Main branch — Deploying to PRODUCTION"
                        sh 'npm run build'
                        sh """
                            sshpass -p 'm' rsync -avz \
                              -e 'ssh -o StrictHostKeyChecking=no' \
                              -r ./dist/ manish@192.168.10.158:/var/www/main/
                        """

                    } else if (env.BRANCH_NAME?.contains('feature')) {
                        echo "🔧 Feature branch — Deploying to STAGING"
                        sh 'npm run build'
                        sh """
                            sshpass -p 'm' rsync -avz \
                              -e 'ssh -o StrictHostKeyChecking=no' \
                              -r ./dist/ manish@192.168.10.158:/var/www/feature/
                        """

                    } else {
                        echo "⚠️ Unknown branch: ${env.BRANCH_NAME} — Skipping deploy"
                    }
                }
            }
        }
    }

    post {
        success { echo "✅ Pipeline successful!" }
        failure { echo "❌ Pipeline failed!" }
    }
}
