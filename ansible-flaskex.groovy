properties([
    parameters([
        string(defaultValue: '', description: 'Please enter VM IP', name: 'nodeIP', trim: true)
          ])
    ])  
    
if (nodeIP?.length() > 6)  {        
    node {
        stage('Pull Repo') {
            git branch: 'master', changelog: false, poll: false, url: 'https://github.com/nadirkakoev/ansible-flaskex.git'
            }
        withEnv(['ANSIBLE_HOST_KEY_CHECKING=False','FLASKEX_REPO=https://github.com/nadirkakoev/ansible-flaskex.git','FLASKEX_BRANCH=master']) {
            stage("Install Prerequisities"){
                ansiblePlaybook credentialsId: 'f4a82031-e1d8-475f-8019-95e76f844d28', inventory: '${nodeIP},', playbook: 'prerequisites.yml'
                }
            stage("Pull Flaskex"){
                ansiblePlaybook credentialsId: 'f4a82031-e1d8-475f-8019-95e76f844d28', inventory: '${nodeIP},', playbook: 'pull_repo.yml'
                }
            stage("Install Python"){
                ansiblePlaybook credentialsId: 'f4a82031-e1d8-475f-8019-95e76f844d28', inventory: '${nodeIP},', playbook: 'install_python.yml'
                }
             stage("Start Application"){
                ansiblePlaybook credentialsId: 'f4a82031-e1d8-475f-8019-95e76f844d28', inventory: '${nodeIP},', playbook: 'start_app.yml'
                }
        }   
             
              
    }     
}

            
else {
    error 'Please enter valid IP address'
}



