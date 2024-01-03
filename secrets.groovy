#!groovy

def CopySecretFiles()
 {
					sh "echo ######################################################################################################################"
                    echo "Saving Secret Files into place for Env  ${params.Env}"
					sh "echo ######################################################################################################################"
					 //copy the secret file to destination
					withCredentials([file(credentialsId: 'application.properties', variable: 'FILE')]) {
						//sh "rm  $WORKSPACE/helm/eaa-deploy/application-config/files/application*"
						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties"
						sh "cp \$FILE $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties"
						sh "sed -i 's/dev.reportnet.europa.eu/${params.Env}.reportnet.europa.eu/g' $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties"
						//sh "ls -la  $WORKSPACE/helm/eaa-deploy/application-config/files/"
						//sh "cat $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties"

						// Changes on 07122023 cause of test failing to connect to keycloak !!!!
						//KEYCLOAK_REDIRECT_URI
						// from 
						// config/application/eea.keycloak.redirect_uri=${KEYCLOAK_REDIRECT_URI:https://reportnet.europa.eu/eulogin/}
						// to
						// config/application/eea.keycloak.redirect_uri=${KEYCLOAK_REDIRECT_URI:https://test.reportnet.europa.eu/eulogin/}}
						// and for keycloak key in test ENV 
						// config/application/eea.keycloak.secret=${KEYCLOAK_SECRET:0380996f-a7ad-4667-8ba4-14995e408d24}
						// to 
						// config/application/eea.keycloak.secret=${KEYCLOAK_SECRET:39938213-b771-4ab3-a5ed-c525e88026b7}


						// FOR EACH ENV WE NEED TO PUT THE KEYCLOAK KEY TO THE BELOW BLOCK - WHENEVER THERE IS A NEW KEY !


						    if ("${params.Env}" == "dev") { 
								echo -n  "This is dev and i am changing the KEYCLOAK_SECRET"
								sh  "sed -i 's/3c03f809-d830-4af8-9cfe-9471297b3b4a/3c03f809-d830-4af8-9cfe-9471297b3b4a-TEST/g' $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties"
								}

						// sh "case ${params.Env} in
						// 	dev)
						// 		echo -n "Application properties on ${params.Env} changed "
						// 		sh "sed -i 's/0380996f-a7ad-4667-8ba4-14995e408d24/0380996f-a7ad-4667-8ba4-14995e408d24-TEST/g' $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties"
						// 		;;

						// 	test)
						// 		echo -n "test"
						// 		sh "sed -i 's/0380996f-a7ad-4667-8ba4-14995e408d24/39938213-b771-4ab3-a5ed-c525e88026b7/g' $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties"
						// 		;;

						// 	sandbox)
						// 		echo -n "sandbox"
						// 		sh "sed -i 's/0380996f-a7ad-4667-8ba4-14995e408d24/39938213-b771-4ab3-a5ed-c525e88026b7/g' $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties"
						// 		;;

						// 	staging)
						// 		echo -n "staging"
						// 		sh "sed -i 's/0380996f-a7ad-4667-8ba4-14995e408d24/39938213-b771-4ab3-a5ed-c525e88026b7/g' $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties"
						// 		;;

						// 	transport | prod2)
						// 		echo -n "prod2-transport"
						// 		sh "sed -i 's/0380996f-a7ad-4667-8ba4-14995e408d24/39938213-b771-4ab3-a5ed-c525e88026b7/g' $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties"
						// 		;;

						// 	prod)
						// 		echo -n "prod"
						// 		sh "sed -i 's/0380996f-a7ad-4667-8ba4-14995e408d24/39938213-b771-4ab3-a5ed-c525e88026b7/g' $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties"
						// 		;;
						// 	*)
						// 		echo -n "No_Enviroment_was_selected --- ERROR !!!!!!"
						// 		;;
						// 	esac

						// 	"






					}	


		 			//Api Gateway
		 			withCredentials([file(credentialsId: 'api-gateway.properties', variable: 'FILE')]) {
		 				echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-api-gateway/preconfig/files/api-gateway.properties"
						sh "cp \$FILE $WORKSPACE/helm/eaa-deploy/reportnet-api-gateway/preconfig/files/api-gateway.properties"
						sh "sed -i 's/ELASTIC_INDEX:dev-apigateway-metrics/ELASTIC_INDEX:${params.Env}-apigateway-metrics/g' $WORKSPACE/helm/eaa-deploy/reportnet-api-gateway/preconfig/files/api-gateway.properties"
						//sh "ls -la  $WORKSPACE/helm/eaa-deploy/reportnet-api-gateway/preconfig/files/"
		 			}
					//Collaboration
					withCredentials([file(credentialsId: 'collaboration-service.properties', variable: 'FILE')]) {
						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-collaboration/preconfig/files/collaboration-service.properties"
						sh "cp \$FILE $WORKSPACE/helm/eaa-deploy/reportnet-collaboration/preconfig/files/collaboration-service.properties"
						sh "sed -i 's/ELASTIC_INDEX:dev-apigateway-metrics/ELASTIC_INDEX:${params.Env}-apigateway-metrics/g' $WORKSPACE/helm/eaa-deploy/reportnet-collaboration/preconfig/files/collaboration-service.properties"
						//sh "ls -la  $WORKSPACE/helm/eaa-deploy/reportnet-collaboration/preconfig/files/"
					}			
					//Communication
					withCredentials([file(credentialsId: 'communication-service.properties', variable: 'FILE')]) {
						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-communication/preconfig/files/communication-service.properties"
						sh "cp \$FILE $WORKSPACE/helm/eaa-deploy/reportnet-communication/preconfig/files/communication-service.properties"
                        sh "sed -i 's/ELASTIC_INDEX:dev-communication-metrics/ELASTIC_INDEX:${params.Env}-communication-metrics/g' $WORKSPACE/helm/eaa-deploy/reportnet-communication/preconfig/files/communication-service.properties"
						//sh "ls -la  $WORKSPACE/helm/eaa-deploy/reportnet-communication/preconfig/files/"
					}			
					//Dataflow
					withCredentials([file(credentialsId: 'dataflow-service.properties', variable: 'FILE')]) {
						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-dataflow/preconfig/files/dataflow-service.properties"
						sh "cp \$FILE $WORKSPACE/helm/eaa-deploy/reportnet-dataflow/preconfig/files/dataflow-service.properties"
                        sh "sed -i 's/ELASTIC_INDEX:dev-dataflow-metrics/ELASTIC_INDEX:${params.Env}-dataflow-metrics/g' $WORKSPACE/helm/eaa-deploy/reportnet-dataflow/preconfig/files/dataflow-service.properties"
						//sh "ls -la  $WORKSPACE/helm/eaa-deploy/reportnet-dataflow/preconfig/files/"
					}
					//Dataset
					withCredentials([file(credentialsId: 'dataset-service.properties', variable: 'FILE')]) {
					    echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-dataset/preconfig/files/dataset-service.properties"
						sh "cp \$FILE $WORKSPACE/helm/eaa-deploy/reportnet-dataset/preconfig/files/dataset-service.properties"
                        sh "sed -i 's/ELASTIC_INDEX:dev-dataset-metrics/ELASTIC_INDEX:${params.Env}-dataset-metrics/g' $WORKSPACE/helm/eaa-deploy/reportnet-dataset/preconfig/files/dataset-service.properties"
						//sh "ls -la  $WORKSPACE/helm/eaa-deploy/reportnet-dataset/preconfig/files/"
					}
					//Document document-service.properties 
					withCredentials([file(credentialsId: 'document-service.properties', variable: 'FILE')]) {
						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-document/preconfig/files/document-service.properties"
						sh "cp \$FILE $WORKSPACE/helm/eaa-deploy/reportnet-document/preconfig/files/document-service.properties"
                        sh "sed -i 's/ELASTIC_INDEX:dev-document-metrics/ELASTIC_INDEX:${params.Env}-document-metrics/g' $WORKSPACE/helm/eaa-deploy/reportnet-document/preconfig/files/document-service.properties"
						//sh "ls -la  $WORKSPACE/helm/eaa-deploy/reportnet-document/preconfig/files/"
					}
					//RecordStore
					withCredentials([file(credentialsId: 'recordstore-service.properties', variable: 'FILE')]) {
						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-recordstore/preconfig/files/recordstore-service.properties"
						sh "cp \$FILE $WORKSPACE/helm/eaa-deploy/reportnet-recordstore/preconfig/files/recordstore-service.properties"
                        sh "sed -i 's/ELASTIC_INDEX:dev-recordstore-metrics/ELASTIC_INDEX:${params.Env}-recordstore-metrics/g' $WORKSPACE/helm/eaa-deploy/reportnet-recordstore/preconfig/files/recordstore-service.properties"
						//sh "ls -la  $WORKSPACE/helm/eaa-deploy/reportnet-recordstore/preconfig/files/"
					}

					//reportnet-e2e 
					//withCredentials([file(credentialsId: 'e2e_application.properties', variable: 'FILE')]) {
					//	echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-e2e/preconfig/files/application.properties"
					//	writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-e2e/preconfig/files/application.properties', text: readFile(FILE)
                    //    sh "ls -la  $WORKSPACE/helm/eaa-deploy/reportnet-e2e/preconfig/files/"
					//}

					//Indexsearch
					withCredentials([file(credentialsId: 'indexsearch-service.properties', variable: 'FILE')]) {
						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-indexsearch/preconfig/files/indexsearch-service.properties"
						sh "cp \$FILE $WORKSPACE/helm/eaa-deploy/reportnet-indexsearch/preconfig/files/indexsearch-service.properties"

						//sh "ls -la  $WORKSPACE/helm/eaa-deploy/reportnet-indexsearch/preconfig/files/"
					}
					//Orchestrator
					withCredentials([file(credentialsId: 'orchestrator-service.properties', variable: 'FILE')]) {
						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-orchestrator/preconfig/files/orchestrator-service.properties"
						sh "cp \$FILE $WORKSPACE/helm/eaa-deploy/reportnet-orchestrator/preconfig/files/orchestrator-service.properties"

						//sh "ls -la  $WORKSPACE/helm/eaa-deploy/reportnet-orchestrator/preconfig/files/"
					}
					//Rod
					withCredentials([file(credentialsId: 'rod-service.properties', variable: 'FILE')]) {
						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-rod/preconfig/files/rod-service.properties"
						sh "cp \$FILE $WORKSPACE/helm/eaa-deploy/reportnet-rod/preconfig/files/rod-service.properties"
                        sh "sed -i 's/ELASTIC_INDEX:dev-rod-metrics/ELASTIC_INDEX:${params.Env}-rod-metrics/g' $WORKSPACE/helm/eaa-deploy/reportnet-rod/preconfig/files/rod-service.properties"
						//sh "ls -la  $WORKSPACE/helm/eaa-deploy/reportnet-rod/preconfig/files/"
					}
					//UMS
					withCredentials([file(credentialsId: 'ums-service.properties', variable: 'FILE')]) {
						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-ums/preconfig/files/ums-service.properties"
						sh "cp \$FILE $WORKSPACE/helm/eaa-deploy/reportnet-ums/preconfig/files/ums-service.properties"
                        sh "sed -i 's/ELASTIC_INDEX:dev-ums-metrics/ELASTIC_INDEX:${params.Env}-ums-metrics/g' $WORKSPACE/helm/eaa-deploy/reportnet-ums/preconfig/files/ums-service.properties"
						sh "ls -la  $WORKSPACE/helm/eaa-deploy/reportnet-ums/preconfig/files/"
					}
					//Validation
					withCredentials([file(credentialsId: 'validation-service.properties', variable: 'FILE')]) {
						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-validation/preconfig/files/validation-service.properties"
						sh "cp \$FILE $WORKSPACE/helm/eaa-deploy/reportnet-validation/preconfig/files/validation-service.properties"
                        sh "sed -i 's/ELASTIC_INDEX:dev-validation-metrics/ELASTIC_INDEX:${params.Env}-validation-metrics/g' $WORKSPACE/helm/eaa-deploy/reportnet-validation/preconfig/files/validation-service.properties"
						sh "ls -la  $WORKSPACE/helm/eaa-deploy/reportnet-validation/preconfig/files/"
					}
					sh "echo ######################################################################################################################"


 }

return this
