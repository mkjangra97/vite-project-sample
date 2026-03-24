def call(String branch, String deployPath) {
    echo "Building for branch: ${branch}"
    sh 'npm run build'
    sh """
        sshpass -p 'm' rsync -avz \
          -e 'ssh -o StrictHostKeyChecking=no' \
          -r ./dist/ manish@192.168.10.158:${deployPath}
    """
}
