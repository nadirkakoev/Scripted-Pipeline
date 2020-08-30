properties([
    parameters([
        string(defaultValue: '', description: 'Please Inter VM IP!!!',  name: 'nodeIP', trim: true),
        string(defaultValue: '', description: 'Please Enter Git Branch!!!', name: 'Git Branch!!!', trim: false)
        ])
    ])

if (nodeIP?.trim()) {
        node {
            withCredentials([sshUserPrivateKey(credentialsId: 'f4a82031-e1d8-475f-8019-95e76f844d28',  keyFileVariable: 'sshkey', passphraseVariable: '', usernameVariable: 'sshusername')]) {
                stage('Pull Repo') {
                    sh ' ssh -o StrictHostKeyChecking=no -i $sshkey  $sshusername@${nodeIP} git clone https://github.com/ikambarov/melodi.git'
                }
                stage('Install Apache') {
                    sh ' ssh -o StrictHostKeyChecking=no -i $sshkey  $sshusername@${nodeIP} yum install httpd   -y'
                }
                stage('Start and Make BP') {
                    sh ' ssh -o StrictHostKeyChecking=no -i $sshkey  $sshusername@${nodeIP} sudo systemctl start httpd && sudo systemctl enable httpd '
                }
                stage('CHOWN') {
                    sh ' ssh -o StrictHostKeyChecking=no -i $sshkey  $sshusername@${nodeIP} sudo chown -R apache:apache /var/www/html/'
                }
                 stage('Copy files') {
                    sh ' ssh -o StrictHostKeyChecking=no -i $sshkey  $sshusername@${nodeIP} cp -rf melodi/* /var/www/html/'
                }
                 stage('Clean Workspace') {
                    cleanWs()
                }
                 stage('Slack Message') {
                    slackSend channel: 'ISLAM brothers task!!!', message: 'Task is DONE!!!'
                
            }
        }
    }
} 
else {
   error 'Please enter valid ip address!!'
}

