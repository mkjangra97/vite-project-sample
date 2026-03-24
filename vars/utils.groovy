def getBranchType(String branchName) {
    if (!branchName) return 'unknown'
    
    // ✅ Pehle normalize karo
    def clean = branchName
                    .replaceAll('origin/', '')
                    .replaceAll('refs/heads/', '')
                    .trim()
    
    // ✅ Exact match — contains() nahi
    if (clean == 'main' || clean == 'master') {
        return 'main'
    } else if (clean.startsWith('feature/') || clean.startsWith('feature-') || clean == 'feature') {
        return 'feature'
    } else {
        return 'unknown'
    }
}
