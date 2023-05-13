pipeline{
    agent{
        label "worker-01-jenkins"
    }
    tools{
        maven 'MAVEN_HOME'
    }
    stages{
        stage("Pull the Code From SCM"){
            steps{
                echo "========Pull the Code From SCM========"
                git branch: 'deploy',
                    url: 'https://github.com/Narsi-Myteaching/weshopify-brands-service.git'
                echo "========source code pulling completed========"
            }
        }
        stage("copy the files to ansible server"){
            steps{
                echo "Connecting to Ansible Server"
                sshagent(['ANSIBLE_SERVER']){
                    sh 'scp weshopify-brands-deployment.yml ansible-admin@172.31.7.122:/opt/weshopify-brands-cd'
                    sh 'scp weshopify-brands-service.yml ansible-admin@172.31.7.122:/opt/weshopify-brands-cd'
                    sh 'scp weshopify-brands-svc-playbook.yml ansible-admin@172.31.7.122:/opt/weshopify-brands-cd'
                    
                }
            }
        }

        stage("Executing Playbook on Docker Machine"){
            steps{
                    echo "Executing Playbook on Docker Machine"
                    sshagent(['ANSIBLE_SERVER']){
                    sh '''
                        ssh -tt ansible-admin@172.31.7.122 << EOF
                            ansible-playbook /opt/weshopify-brands-cd/weshopify-brands-svc-playbook.yml
                            exit
                        EOF
                    '''
                }
            }
        }
    }
}
