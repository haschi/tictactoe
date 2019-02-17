pipeline {
  agent any
  stages {
    stage('Build & Test') {
      agent {
        node {
          label 'docker'
        }

      }
      steps {
        echo "Baue Version ${env.VERSION}"
        sh 'printenv $VERSION'
        sh 'mvn --version'        
        configFileProvider([configFile(fileId: '988b0ff2-69fe-4a07-b087-5ff6c8063dc9', variable: 'MAVEN_SETTINGS_XML')]) {
          sh "mvn --batch-mode --settings $MAVEN_SETTINGS_XML -Dmaven.test.failure.ignore clean package"
        }

        stash(name: 'build-test-artifacts', includes: '**/target/surefire-reports/TEST-*.xml,**/target/failsafe-reports/TEST-*.xml,**/target/*.jar')
      }
    }
    stage('Report & Publish') {
      parallel {
        stage('Report & Publish') {
          agent {
            node {
              label 'docker'
            }

          }
          steps {
            unstash 'build-test-artifacts'
            junit '**/target/surefire-reports/TEST-*.xml,**/target/failsafe-reports/TEST-*.xml'
            archiveArtifacts(artifacts: '**/target/*.jar', onlyIfSuccessful: true)
          }
        }
        stage('error') {
          agent {
            node {
              label 'docker'
            }

          }
          steps {
            nexusPublisher(nexusInstanceId: 'nexus', nexusRepositoryId: 'haschi-devel')
          }
        }
      }
    }
  }
  tools {
    maven 'Maven 3.6.0'
  }
  environment {
    VERSION = "${env.BRANCH_NAME}.${env.BUILD_NUMBER}"
  }
}
