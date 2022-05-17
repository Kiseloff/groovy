def getSrc() {
    echo "getting the sources..."
    sh 'rm -rf ./project'
    sh 'git clone -b jenkins-jobs https://gitlab.com/nanuchi/java-maven-app.git ./project'
    sh 'cd ./project'
}

def buildJar() {
    echo "building the app..."
    sh 'mvn package -f ./project'
}

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'nexus-credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'ls -la'
        sh 'docker build -t 192.168.88.14:8083/java-maven-app:4.0 .'
        sh "echo $PASS | docker login -u $USER --password-stdin 192.168.88.14:8083"
        sh 'docker push 192.168.88.14:8083/java-maven-app:4.0'
    }
}

def deployApp() {
    echo "deploying the app..."
}

return this
