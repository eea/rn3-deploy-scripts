pipeline {

    agent { node { label 'kubectl' } }
    tools {
        maven 'maven3'

    }

    
    stages {
        stage('Deployment Init') {
            steps {
                sh 'echo "Starting Deployment Pipeline"'                
            }
        }
        stage('Setting Environmental Variables') {
			
			steps {
				script {
					
					withCredentials([file(credentialsId: 'kubectl-config-dev', variable 'FILE')]) {
					env.KUBECONFIG=$FILE
					}
					env.TARGET_ENV="reportnet"
					env.REPORTNET_BACKEND="https://dev-api.reportnet.europa.eu"
					env.EULOGIN="https://dev-auth.reportnet.europa.eu/auth/realms/Reportnet/protocol/openid-connect/auth?client_id=reportnet\\&redirect_uri=http%3A%2F%2Fdev.reportnet.europa.eu%2Feulogin%2F&response_mode=fragment\\&response_type=code\\&scope=openid"
					env.WEB_SOCKET="ws://dev.reportnet.europa.eu/communication/reportnet-websocket"
					env.REPO="k8s-swi001:5000"
					env.TAG_SUFIX=""
					env.FRONTEND_PORT="30888"
					env.KEYCLOAK="true"
					env.MONGO_HOSTS="mongo-mongodb-replicaset-0.mongo-mongodb-replicaset.eea-dev.svc.cluster.local:27017\\,mongo-mongodb-replicaset-1.mongo-mongodb-replicaset.eea-dev.svc.cluster.local:27017\\,mongo-mongodb-replicaset-2.mongo-mongodb-replicaset.eea-dev.svc.cluster.local:27017/?readPreference=primary&retryWrites=true"
					env.REDIRECT_URI="http://dev.reportnet.europa.eu/eulogin/"
					env.R3_CALLBACK_URL="http://dev-api.reportnet.europa.eu"
					env.FME_TOPIC="Reportnet3_Dev_Topic"
					env.ZIPKIN_URL_SCHEMA="http"
					
				}
			}
	      }
       
		stage('Start environment building') {
            steps {
                sh 'echo "Starting $TARGET_ENV building"'                
            }
        }
        
		stage('Update Database') {
            steps {
                sh '''
                    mvn -f $WORKSPACE/helm/database -DPOSTGRES_SERVER=1kvm-rn3dev-04.pdmz.eea:31293 -DPOSTGRES_USER=53p057n373.0! -DPOSTGRES_PASS=password flyway:migrate
                '''
	          }
        }
		stage('Scaling down services'){
		
			steps {
				sh '''
					kubectl -n $TARGET_ENV --kubeconfig=$KUBECONFIG scale deploy api-gateway communication dataflow dataset document frontend recordstore rod ums validation collaboration --replicas=0
                '''
			}
		}
        stage('Configure Common Application properties') {
            steps {
                sh '''
                    helm install application-config $WORKSPACE/helm/application-config -n $TARGET_ENV --kubeconfig=$KUBECONFIG 
                '''
				sleep(20)
				sh '''
                    helm uninstall application-config  -n $TARGET_ENV --kubeconfig=$KUBECONFIG 
                '''

            }
        }
        stage('Deploy API Gateway') {
            steps {
                sh '''
                    helm install api-gateway-preconfig $WORKSPACE/helm/reportnet-api-gateway/preconfig -n $TARGET_ENV --kubeconfig=$KUBECONFIG 
                '''
				sleep(20)
				sh '''
                    helm uninstall api-gateway-preconfig  -n $TARGET_ENV --kubeconfig=$KUBECONFIG 
                '''
                sh '''
                    helm upgrade api-gateway $WORKSPACE/helm/reportnet-api-gateway/service -n $TARGET_ENV --kubeconfig=$KUBECONFIG  -i  --wait --set version=1.0,repo=$REPO,tagSufix=$TAG_SUFIX,zipkin.schema=$ZIPKIN_URL_SCHEMA
                '''
                
				
            }
        }
		stage('Deploy Dataflow') {
            steps {
                sh '''
                    helm install dataflow-preconfig $WORKSPACE/helm/reportnet-dataflow/preconfig -n $TARGET_ENV --kubeconfig=$KUBECONFIG 
                '''
				sleep(20)
				sh '''
                    helm uninstall dataflow-preconfig  -n $TARGET_ENV --kubeconfig=$KUBECONFIG 
                '''
                sh '''
                    helm upgrade dataflow $WORKSPACE/helm/reportnet-dataflow/service -n $TARGET_ENV  -i --kubeconfig=$KUBECONFIG  --wait --set version=1.0,repo=$REPO,tagSufix=$TAG_SUFIX,zipkin.schema=http,fme.integration.callback.urlbase=$R3_CALLBACK_URL,fme.topic=$FME_TOPIC

                '''
                
				
            }
        }
		stage('Deploy Recordstore') {
            steps {
                sh '''
                    helm install recordstore-preconfig $WORKSPACE/helm/reportnet-recordstore/preconfig -n $TARGET_ENV --kubeconfig=$KUBECONFIG 
                '''
				sleep(20)
				sh '''
                    helm uninstall recordstore-preconfig  -n $TARGET_ENV
                '''
				/*sh '''
                    helm upgrade recordstore-persistence $WORKSPACE/helm/reportnet-recordstore/pvc -n $TARGET_ENV --kubeconfig=$KUBECONFIG   -i --wait --set repo=k8s-swi001:5000,tagSufix=_sandbox
                '''*/
                sh '''
                    helm upgrade recordstore $WORKSPACE/helm/reportnet-recordstore/service -n $TARGET_ENV --kubeconfig=$KUBECONFIG  -i --wait --set version=3.0,repo=$REPO,tagSufix=$TAG_SUFIX,zipkin.schema=$ZIPKIN_URL_SCHEMA
                '''
				sleep(20)
            }
        }
        stage('Deploy Validation') {
            steps {
                sh '''
                    helm install validation-preconfig $WORKSPACE/helm/reportnet-validation/preconfig -n $TARGET_ENV --kubeconfig=$KUBECONFIG 
                '''
				sleep(20)
				sh '''
                    helm uninstall validation-preconfig  -n $TARGET_ENV --kubeconfig=$KUBECONFIG 
                '''
                sh '''
                    helm upgrade validation $WORKSPACE/helm/reportnet-validation/service -n $TARGET_ENV --kubeconfig=$KUBECONFIG  -i --wait --set version=1.0,repo=$REPO,tagSufix=$TAG_SUFIX,replicas=1,mongo.hosts=$MONGO_HOSTS,zipkin.schema=$ZIPKIN_URL_SCHEMA
                '''
                
				
            }
        }
		stage('Deploy Dataset') {
            steps {
                sh '''
                    helm install dataset-preconfig $WORKSPACE/helm/reportnet-dataset/preconfig -n $TARGET_ENV --kubeconfig=$KUBECONFIG 
                '''
				sleep(20)
				sh '''
                    helm uninstall dataset-preconfig  -n $TARGET_ENV
                '''
                sh '''
                    helm upgrade dataset $WORKSPACE/helm/reportnet-dataset/service -n $TARGET_ENV --kubeconfig=$KUBECONFIG   -i --wait --set version=1.0,repo=$REPO,tagSufix=$TAG_SUFIX,replicas=1,mongo.hosts=$MONGO_HOSTS,zipkin.schema=$ZIPKIN_URL_SCHEMA
                '''
            }
        }
		stage('Deploy User Management') {
            steps {
                sh '''
                    helm install ums-preconfig $WORKSPACE/helm/reportnet-ums/preconfig -n $TARGET_ENV --kubeconfig=$KUBECONFIG 
                '''
				sleep(20)
				sh '''
                    helm uninstall ums-preconfig  -n $TARGET_ENV --kubeconfig=$KUBECONFIG 
                '''
                sh '''
                    helm upgrade ums $WORKSPACE/helm/reportnet-ums/service -n $TARGET_ENV --kubeconfig=$KUBECONFIG  -i --wait --set version=3.0,repo=$REPO,tagSufix=$TAG_SUFIX,keycloak.redirect_uri=$REDIRECT_URI,zipkin.schema=$ZIPKIN_URL_SCHEMA
                '''
            }
        }
		stage('Deploy Document Container') {
            steps {
                sh '''
                    helm install document-preconfig $WORKSPACE/helm/reportnet-document/preconfig -n $TARGET_ENV --kubeconfig=$KUBECONFIG 
                '''
				sleep(20)
				sh '''
                    helm uninstall document-preconfig  -n $TARGET_ENV --kubeconfig=$KUBECONFIG 
                '''
                sh '''
                    helm upgrade document $WORKSPACE/helm/reportnet-document/service -n $TARGET_ENV --kubeconfig=$KUBECONFIG  -i --wait --set version=3.0,repo=$REPO,tagSufix=$TAG_SUFIX,mongo.hosts=$MONGO_HOSTS,zipkin.schema=$ZIPKIN_URL_SCHEMA
                '''
            }
        }
		stage('Deploy Communication') {
            steps {
                sh '''
                    helm install communication-preconfig $WORKSPACE/helm/reportnet-communication/preconfig -n $TARGET_ENV --kubeconfig=$KUBECONFIG 
                '''
				sleep(20)
				sh '''
                    helm uninstall communication-preconfig  -n $TARGET_ENV --kubeconfig=$KUBECONFIG 
                '''
                sh '''
                    helm upgrade communication $WORKSPACE/helm/reportnet-communication/service -n $TARGET_ENV --kubeconfig=$KUBECONFIG   -i --wait --set version=3.0,repo=k8s-swi001:5000,tagSufix=$TAG_SUFIX,communication.port=32402,zipkin.schema=$ZIPKIN_URL_SCHEMA
                '''
            }
        }
		stage('Deploy Collaboration') {
            steps {
                sh '''
                    helm install collaboration-preconfig $WORKSPACE/helm/reportnet-collaboration/preconfig -n $TARGET_ENV  --kubeconfig=$KUBECONFIG 
                '''
				sleep(20)
				sh '''
                    helm uninstall collaboration-preconfig  -n $TARGET_ENV --kubeconfig=$KUBECONFIG 
                '''
                sh '''
                    helm upgrade collaboration $WORKSPACE/helm/reportnet-collaboration/service -n $TARGET_ENV --kubeconfig=$KUBECONFIG  -i --wait --set version=3.0,repo=$REPO,tagSufix=$TAG_SUFIX
                '''
            }
        }
		/*stage('Deploy Indexsearch') {
            steps {
                sh '''
                    helm install indexsearch-preconfig $WORKSPACE/helm/reportnet-indexsearch/preconfig -n $TARGET_ENV  --kubeconfig=$KUBECONFIG 
                '''
				sleep(20)
				sh '''
                    helm uninstall indexsearch-preconfig  -n $TARGET_ENV --kubeconfig=$KUBECONFIG 
                '''
                sh '''
                    helm upgrade indexsearch $WORKSPACE/helm/reportnet-indexsearch/service -n $TARGET_ENV --kubeconfig=$KUBECONFIG  -i --wait --set repo=k8s-swi001:5000,tagSufix=_sandbox
                '''
            }
        }*/
		stage('Deploy ReportNet Frontend') {
            steps {
               sh '''
                    helm upgrade frontend $WORKSPACE/helm/reportnet-frontend/service -n $TARGET_ENV --kubeconfig=$KUBECONFIG  -i --wait --set version=3.0,repo=$REPO,tagSufix=$TAG_SUFIX,backend=$REPORTNET_BACKEND,keycloak=$KEYCLOAK,websocket=$WEB_SOCKET,eulogin=$EULOGIN,frontendPort=31888
                '''
                
            }
        }
		
		stage('Deploy Rod') {
            steps {
              sh '''
                    helm install rod-preconfig $WORKSPACE/helm/reportnet-rod/preconfig -n $TARGET_ENV --kubeconfig=$KUBECONFIG 
                '''
				sleep(20)
				sh '''
                    helm uninstall rod-preconfig  -n $TARGET_ENV --kubeconfig=$KUBECONFIG 
                '''
                sh '''
                    helm upgrade rod $WORKSPACE/helm/reportnet-rod/service -n $TARGET_ENV --kubeconfig=$KUBECONFIG  -i --wait --set version=3.0,repo=$REPO,tagSufix=$TAG_SUFIX,zipkin.schema=$ZIPKIN_URL_SCHEMA
                '''
                
            }
        }
        
        
    }
    post {
        always {
            script {
                    sh 'echo "Building process for $TARGET_ENV finished"'
                 
            }
        }
        
    }
}