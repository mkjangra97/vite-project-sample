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
                    // ✅ Branch name properly normalize karo
                    def rawBranch = env.BRANCH_NAME 
                                 ?: env.GIT_BRANCH 
                                 ?: ''

                    // origin/ aur refs/heads/ dono strip karo
                    def branch = rawBranch
                                    .replaceAll('origin/', '')
                                    .replaceAll('refs/heads/', '')
                                    .trim()
        
                    echo "🔍 Raw Branch Value : ${rawBranch}"
                    echo "🔍 Normalized Branch: ${branch}"

                    // ✅ contains() ki jagah exact match ya startsWith()
                    if (branch == 'main' || branch == 'master') {
                        echo "📦 Main branch — npm ci"
                        sh 'npm ci'
                    } else if (branch.startsWith('feature/') || branch.startsWith('feature-')) {
                        echo "📦 Feature branch — npm install"
                        sh 'npm install'
                    } else {
                        echo "⚠️ Unknown branch: ${branch}"
                        sh 'npm install'
                    }
                }
            }        
        }

        stage('Build & Deploy') {
            steps {
                script {
                    def branch = env.BRANCH_NAME ?: env.GIT_BRANCH?.replaceAll('origin/', '')
                    echo "🔍 Deploying Branch: ${branch}"

                    if (branch?.contains('main')) {
                        echo "🚀 Deploying to PRODUCTION"
                        sh 'npm run build'
                        sh """
                            sshpass -p 'm' rsync -avz \
                              -e 'ssh -o StrictHostKeyChecking=no' \
                              -r ./dist/ manish@192.168.10.158:/var/www/main/
                        """
                        echo "✅ Live: http://192.168.10.158:3000"

                    } else if (branch?.contains('feature')) {
                        echo "🔧 Deploying to STAGING"
                        sh 'npm run build'
                        sh """
                            sshpass -p 'm' rsync -avz \
                              -e 'ssh -o StrictHostKeyChecking=no' \
                              -r ./dist/ manish@192.168.10.158:/var/www/feature/
                        """
                        echo "✅ Live: http://192.168.10.158:3001"

                    } else {
                        echo "⚠️ Unknown branch: ${branch} — Skipping deploy"
                    }
                }
            }
        }
    }

    post {
        success { echo "✅ Pipeline successful on branch: ${env.BRANCH_NAME ?: env.GIT_BRANCH}!" }
        failure { echo "❌ Pipeline failed on branch: ${env.BRANCH_NAME ?: env.GIT_BRANCH}!" }
    }
}
