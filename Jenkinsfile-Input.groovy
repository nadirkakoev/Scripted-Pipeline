node {
  stage("Building"){
    echo 'Building..'
  }
  stage("Testing"){
    echo 'Testing..'
  }
  stage('Input'){
      input 'Do you want to proceed?'
  }
  stage("Deploying "){
    echo 'Deploying....'
  }
}