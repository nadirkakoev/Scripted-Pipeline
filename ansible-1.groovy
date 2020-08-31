properties([
    parameters([
        string(defaultValue: '', description: 'Please Inter VM IP!!!',  name: 'nodeIP', trim: true)
        ])
    ])

if (nodeIP?.trim()) {
        node {
            withCredentials([sshUserPrivateKey(credentialsId: 'f4a82031-e1d8-475f-8019-95e76f844d28',  keyFileVariable: 'sshkey', passphraseVariable: '', usernameVariable: 'sshusername')]) {
                stage('Init') {
                    sh ' ssh -o StrictHostKeyChecking=no -i $sshkey  $sshusername@${nodeIP} yum install epel-release  -y'
                }
                stage('Install git') {
                    sh ' ssh -o StrictHostKeyChecking=no -i $sshkey  $sshusername@${nodeIP} yum install git  -y'
                }
                stage('Install Ansible') {
                    sh ' ssh -o StrictHostKeyChecking=no -i $sshkey  $sshusername@${nodeIP} yum install ansible  -y'
            }
        }
    }
} 
else {
   error 'Please enter valid ip address!!'
}

