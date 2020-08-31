properties([
    parameters([
        string(defaultValue: '', description: 'Please enter VM IP', name: 'nodeIP', trim: true),
        string(defaultValue: '', description: 'Please enter branch name', name: 'branch', trim: true)
        ])
    ])
if (nodeIP?.trim()) {
    node {
        stage('Pull Repo') {
            git branch: 'master', changelog: false, poll: false, url: 'https://github.com/nadirkakoev/ansible-jenkins-.git'
            }
        withEnv(['ANSIBLE_HOST_KEY_CHECKING=False']) {
            stage("Install Apache"){
                ansiblePlaybook credentialsId: 'f4a82031-e1d8-475f-8019-95e76f844d28', inventory: '${nodeIP},', playbook: 'main.yml'
                }
            }  
        }  
}

            
else {
    error 'Please enter valid IP address'
}