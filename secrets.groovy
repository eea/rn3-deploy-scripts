#!groovy

def CopySecretFiles()
 {
					sh "echo ######################################################################################################################"
                    echo "Saving Secret Files into place for Env  ${params.Env}"
					sh "echo ######################################################################################################################"
					 //copy the secret file to destination
					withCredentials([file(credentialsId: 'application.properties', variable: 'FILE')]) {
						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties"
						sh "cp \$FILE $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties"
						sh "sed -i 's/dev.reportnet.europa.eu/${params.Env}.reportnet.europa.eu/g' $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties"
						//sh "ls -la  $WORKSPACE/helm/eaa-deploy/application-config/files/"
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
