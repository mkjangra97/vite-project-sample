def getBranchType(String branchName) {
    if (branchName?.contains('main')) {
        return 'main'
    } else if (branchName?.contains('feature')) {
        return 'feature'
    } else {
        return 'unknown'
    }
}
