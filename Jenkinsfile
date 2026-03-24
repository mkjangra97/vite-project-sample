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
                    def rawBranch = env.BRANCH_NAME ?: env.GIT_BRANCH ?: ''
                    def branch = rawBranch
                                    .replaceAll('origin/', '')
                                    .replaceAll('refs/heads/', '')
                                    .trim()

                    echo "🔍 Raw   : ${rawBranch}"
                    echo "🔍 Clean : ${branch}"

                    if (branch == 'main' || branch == 'master') {
                        echo "📦 Main branch — npm ci"
                        sh 'npm ci'
                    } else if (branch.startsWith('feature/') || branch.startsWith('feature-') || branch == 'feature') {
                        echo "📦 Feature branch — npm install"
                        sh 'npm install'
                    } else {
                        echo "⚠️ Unknown branch: ${branch} — npm install"
                        sh 'npm install'
                    }
                }
            }
        }

        stage('Build & Deploy') {
            steps {
                script {
                    // ✅ Normalize YAHAN BHI karo — purana code nahi
                    def rawBranch = env.BRANCH_NAME ?: env.GIT_BRANCH ?: ''
                    def branch = rawBranch
                                    .replaceAll('origin/', '')
                                    .replaceAll('refs/heads/', '')
                                    .trim()

                    // ✅ Library function use karo
                    def branchType = getBranchType(branch)
                    echo "🔍 Branch  : ${branch}"
                    echo "🔍 Type    : ${branchType}"

                    if (branchType == 'main') {
                        echo "🚀 Deploying to PRODUCTION"
                        deployBuild(branch, '/var/www/main/')
                        echo "✅ Live: http://192.168.10.158:3000"

                    } else if (branchType == 'feature') {
                        echo "🔧 Deploying to STAGING"
                        deployBuild(branch, '/var/www/feature/')
                        echo "✅ Live: http://192.168.10.158:3001"

                    } else {
                        echo "⚠️ Unknown branch: ${branch} — deploy skip"
                    }
                }
            }
        }
    }

    post {
        success {
            script {
                def b = (env.BRANCH_NAME ?: env.GIT_BRANCH ?: 'unknown')
                            .replaceAll('origin/', '').replaceAll('refs/heads/', '').trim()
                echo "✅ Pipeline successful on branch: ${b}"
            }
        }
        failure {
            script {
                def b = (env.BRANCH_NAME ?: env.GIT_BRANCH ?: 'unknown')
                            .replaceAll('origin/', '').replaceAll('refs/heads/', '').trim()
                echo "❌ Pipeline failed on branch: ${b}"
            }
        }
    }
}
