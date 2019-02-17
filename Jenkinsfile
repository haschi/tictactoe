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
        echo 'Baue Version ${VERSION}'
        sh '''printenv
mvn --version'''
        sh 'mvn -B -Dmaven.test.failure.ignore clean package'
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
    VERSION = '${BRANCH_NAME}.${BUILD_NUMBER}'
  }
}