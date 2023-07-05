#!groovy

def CopySecretFiles()
 {
                    echo "Saving Secret Files into place for Env  ${params.Env}"
					 //#######################################################################################################################
					 //copy the secret file to destination
					withCredentials([file(credentialsId: 'application.properties', variable: 'FILE')]) {

						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties"
						writeFile file: '$WORKSPACE/helm/eaa-deploy/application-config/files/application.properties', text: readFile(FILE)
                        //sh "sed -i 's/dev.reportnet.europa.eu/${params.Env}.reportnet.europa.eu/g' $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties"
					}			


		 			//Api Gateway
		 			withCredentials([file(credentialsId: 'api-gateway.properties', variable: 'FILE')]) {

		 				echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-api-gateway/preconfig/files/api-gateway.properties"
		 				writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-api-gateway/preconfig/files/api-gateway.properties', text: readFile(FILE)
						
		 				//sh "sed -i 's/ELASTIC_INDEX:dev-apigateway-metrics/ELASTIC_INDEX:${params.Env}-apigateway-metrics/g' $WORKSPACE/helm/eaa-deploy/reportnet-api-gateway/preconfig/files/api-gateway.properties"
		 			}

					
					//Collaboration
					withCredentials([file(credentialsId: 'collaboration-service.properties', variable: 'FILE')]) {

						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-collaboration/preconfig/files/collaboration-service.properties"
						writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-collaboration/preconfig/files/collaboration-service.properties', text: readFile(FILE)
                        //sh "sed -i 's/ELASTIC_INDEX:dev-apigateway-metrics/ELASTIC_INDEX:${params.Env}-apigateway-metrics/g' $WORKSPACE/helm/eaa-deploy/reportnet-collaboration/preconfig/files/collaboration-service.properties"
					}			


					//Communication
					withCredentials([file(credentialsId: 'communication-service.properties', variable: 'FILE')]) {

						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-communication/preconfig/files/communication-service.properties"
						writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-communication/preconfig/files/communication-service.properties', text: readFile(FILE)
                        //sh "sed -i 's/ELASTIC_INDEX:dev-communication-metrics/ELASTIC_INDEX:${params.Env}-communication-metrics/g' $WORKSPACE/helm/eaa-deploy/reportnet-communication/preconfig/files/communication-service.properties"
					}			


					//Dataflow
					withCredentials([file(credentialsId: 'dataflow-service.properties', variable: 'FILE')]) {
						//text = readFile(FILE)
						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-dataflow/preconfig/files/dataflow-service.properties"
						writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-dataflow/preconfig/files/dataflow-service.properties', text: readFile(FILE)
                        //sh "sed -i 's/ELASTIC_INDEX:dev-dataflow-metrics/ELASTIC_INDEX:${params.Env}-dataflow-metrics/g' $WORKSPACE/helm/eaa-deploy/reportnet-dataflow/preconfig/files/dataflow-service.properties"
					}
					//println "${text}"	

					//Dataset
					withCredentials([file(credentialsId: 'dataset-service.properties', variable: 'FILE')]) {
						//text = readFile(FILE)
					    echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-dataset/preconfig/files/dataset-service.properties"
						writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-dataset/preconfig/files/dataset-service.properties', text: readFile(FILE)
                        //sh "sed -i 's/ELASTIC_INDEX:dev-dataset-metrics/ELASTIC_INDEX:${params.Env}-dataset-metrics/g' $WORKSPACE/helm/eaa-deploy/reportnet-dataset/preconfig/files/dataset-service.properties"
					}
					//println "${text}"	

					//Document document-service.properties 
					withCredentials([file(credentialsId: 'document-service.properties', variable: 'FILE')]) {
						//text = readFile(FILE)
						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-document/preconfig/files/document-service.properties"
						writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-document/preconfig/files/document-service.properties', text: readFile(FILE)
                        //sh "sed -i 's/ELASTIC_INDEX:dev-document-metrics/ELASTIC_INDEX:${params.Env}-document-metrics/g' $WORKSPACE/helm/eaa-deploy/reportnet-document/preconfig/files/document-service.properties"
					}
					//println "${text}"	

					//RecordStore
					withCredentials([file(credentialsId: 'recordstore-service.properties', variable: 'FILE')]) {
						//text = readFile(FILE)
						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-recordstore/preconfig/files/recordstore-service.properties"
						writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-recordstore/preconfig/files/recordstore-service.properties', text: readFile(FILE)
                        //sh "sed -i 's/ELASTIC_INDEX:dev-recordstore-metrics/ELASTIC_INDEX:${params.Env}-recordstore-metrics/g' $WORKSPACE/helm/eaa-deploy/reportnet-recordstore/preconfig/files/recordstore-service.properties"
					}
					//println "${text}"	

					//reportnet-e2e 
					withCredentials([file(credentialsId: 'e2e_application.properties', variable: 'FILE')]) {
						//text = readFile(FILE)
						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-e2e/preconfig/files/application.properties"
						writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-e2e/preconfig/files/application.properties', text: readFile(FILE)
                        
					}
					//println "${text}"	

					//Indexsearch
					withCredentials([file(credentialsId: 'indexsearch-service.properties', variable: 'FILE')]) {
						//text = readFile(FILE)
						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-indexsearch/preconfig/files/indexsearch-service.properties"
						writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-indexsearch/preconfig/files/indexsearch-service.properties', text: readFile(FILE)

					}
					//println "${text}"	

					//Orchestrator
					withCredentials([file(credentialsId: 'orchestrator-service.properties', variable: 'FILE')]) {
						//text = readFile(FILE)
						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-orchestrator/preconfig/files/orchestrator-service.properties"
						//writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-orchestrator/preconfig/files/orchestrator-service.properties ', text: readFile(FILE)

					}
					//println "${text}"	



					//Rod
					withCredentials([file(credentialsId: 'rod-service.properties', variable: 'FILE')]) {
					    //text = readFile(FILE)
						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-rod/preconfig/files/rod-service.properties"
						writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-rod/preconfig/files/rod-service.properties', text: readFile(FILE)
                        //sh "sed -i 's/ELASTIC_INDEX:dev-rod-metrics/ELASTIC_INDEX:${params.Env}-rod-metrics/g' $WORKSPACE/helm/eaa-deploy/reportnet-rod/preconfig/files/rod-service.properties"
					}
					//println "${text}"	

					//UMS
					withCredentials([file(credentialsId: 'ums-service.properties', variable: 'FILE')]) {
						//text = readFile(FILE)
						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-ums/preconfig/files/ums-service.properties"
						writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-ums/preconfig/files/ums-service.properties', text: readFile(FILE)
                        //sh "sed -i 's/ELASTIC_INDEX:dev-ums-metrics/ELASTIC_INDEX:${params.Env}-ums-metrics/g' $WORKSPACE/helm/eaa-deploy/reportnet-ums/preconfig/files/ums-service.properties"
					}
					//println "${text}"	

					//Validation
					withCredentials([file(credentialsId: 'validation-service.properties', variable: 'FILE')]) {
						//text = readFile(FILE)
						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-validation/preconfig/files/validation-service.properties"
						writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-validation/preconfig/files/validation-service.properties', text: readFile(FILE)
                        //sh "sed -i 's/ELASTIC_INDEX:dev-validation-metrics/ELASTIC_INDEX:${params.Env}-validation-metrics/g' $WORKSPACE/helm/eaa-deploy/reportnet-validation/preconfig/files/validation-service.properties"
					}
					//println "${text}"	

					// //#######################################################################################################################

 }

return this
