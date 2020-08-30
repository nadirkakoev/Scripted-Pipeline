node {
    withCredentials([sshUserPrivateKey(credentialsId: 'f4a82031-e1d8-475f-8019-95e76f844d28', 
    keyFileVariable: 'sshkey', passphraseVariable: '',
    usernameVariable: 'sshusername')]) {
        stage('Init') {
            sh ' ssh -o StrictHostKeyChecking=no -i $sshkey   $sshusername@157.245.89.156 yum install epel-release  -y'
        }
        stage('Install git') {
            sh ' ssh -o StrictHostKeyChecking=no -i $sshkey   $sshusername@157.245.89.156 yum install git  -y'
        }
        stage('Install Java') {
            sh ' ssh -o StrictHostKeyChecking=no -i $sshkey   $sshusername@157.245.89.156 yum install java-1.8.0-openjdk-devel -y'
        }
    }
}

