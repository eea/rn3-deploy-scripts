def CopySecretFiles() {
                    //println 'Test SOTAV SOTAV SOTAV SOTAV'
                    echo "Test SOTAV SOTAV SOTAV SOTAV Test SOTAV SOTAV SOTAV SOTAV Test SOTAV SOTAV SOTAV SOTAV Test SOTAV SOTAV SOTAV SOTAV"
					 echo "Saving Secret Files into place for Env  ${params.Env}"
					 //#######################################################################################################################
					 //copy the secret file to destination
					withCredentials([file(credentialsId: 'applications.properties', variable: 'FILE')]) {
					    //text = readFile(FILE)
						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties"
						writeFile file: '$WORKSPACE/helm/eaa-deploy/application-config/files/application.properties', text: readFile(FILE)
					}			
					//println "${text}"

		 			//Api Gateway
		 			withCredentials([file(credentialsId: 'api-gateway.properties', variable: 'FILE')]) {
		 				//text = readFile(FILE)
		 				echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-api-gateway/preconfig/files/api-gateway.properties"
		 				writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-api-gateway/preconfig/files/api-gateway.properties', text: readFile(FILE)
						sleep(2)
		 				sh "sed -i 's/ELASTIC_INDEX:dev-apigateway-metrics/ELASTIC_INDEX:${params.Env}-apigateway-metrics/g' $WORKSPACE/helm/eaa-deploy/reportnet-api-gateway/preconfig/files/api-gateway.properties"
		 			}

					//Collaboration
					withCredentials([file(credentialsId: 'collaboration-service.properties', variable: 'FILE')]) {
						//text = readFile(FILE)
						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-collaboration/preconfig/files/collaboration-service.properties"
						writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-collaboration/preconfig/files/collaboration-service.properties', text: readFile(FILE)
					}			
					//println "${text}"	

					//Communication
					withCredentials([file(credentialsId: 'communication-service.properties', variable: 'FILE')]) {
						//text = readFile(FILE)
						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-communication/preconfig/files/communication-service.properties"
						writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-communication/preconfig/files/communication-service.properties', text: readFile(FILE)
					}			
					//println "${text}"	

					//Dataflow
					withCredentials([file(credentialsId: 'dataflow-service.properties', variable: 'FILE')]) {
						//text = readFile(FILE)
						echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-dataflow/preconfig/files/dataflow-service.properties"
						writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-dataflow/preconfig/files/dataflow-service.properties', text: readFile(FILE)
					}
					//println "${text}"	

					//Dataset
					withCredentials([file(credentialsId: 'dataset-service.properties', variable: 'FILE')]) {
						//text = readFile(FILE)
					    echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-dataset/preconfig/files/dataset-service.properties"
						writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-dataset/preconfig/files/dataset-service.properties', text: readFile(FILE)
					}
					//println "${text}"	

					// //Document document-service.properties 
					// withCredentials([file(credentialsId: 'document-service.properties', variable: 'FILE')]) {
					// 	//text = readFile(FILE)
					// 	echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-document/preconfig/files/document-service.properties"
					// 	writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-document/preconfig/files/document-service.properties', text: readFile(FILE)
					// }
					// //println "${text}"	

					// //reportnet-e2e 
					// withCredentials([file(credentialsId: 'e2e_application.properties', variable: 'FILE')]) {
					// 	//text = readFile(FILE)
					// 	echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-e2e/preconfig/files/application.properties"
					// 	writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-e2e/preconfig/files/application.properties', text: readFile(FILE)
					// }
					// //println "${text}"	

					// //Indexsearch
					// withCredentials([file(credentialsId: 'indexsearch-service.properties', variable: 'FILE')]) {
					// 	//text = readFile(FILE)
					// 	echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-indexsearch/preconfig/files/indexsearch-service.properties"
					// 	writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-indexsearch/preconfig/files/indexsearch-service.properties', text: readFile(FILE)
					// }
					// //println "${text}"	

					// //Orchestrator
					// withCredentials([file(credentialsId: 'orchestrator-service.properties', variable: 'FILE')]) {
					// 	//text = readFile(FILE)
					// 	echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-orchestrator/preconfig/files/orchestrator-service.properties"
					// 	writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-orchestrator/preconfig/files/orchestrator-service.properties ', text: readFile(FILE)
					// }
					// //println "${text}"	

					// //RecordStore
					// withCredentials([file(credentialsId: 'recordstore-service.properties', variable: 'FILE')]) {
					// 	//text = readFile(FILE)
					// 	echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-recordstore/preconfig/files/recordstore-service.properties"
					// 	writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-recordstore/preconfig/files/recordstore-service.properties', text: readFile(FILE)
					// }
					// //println "${text}"	

					// //Rod
					// withCredentials([file(credentialsId: 'rod-service.properties', variable: 'FILE')]) {
					// 	//text = readFile(FILE)
					// 	echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-rod/preconfig/files/rod-service.properties"
					// 	writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-rod/preconfig/files/rod-service.properties', text: readFile(FILE)
					// }
					// //println "${text}"	

					// //UMS
					// withCredentials([file(credentialsId: 'ums-service.properties', variable: 'FILE')]) {
					// 	//text = readFile(FILE)
					// 	echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-ums/preconfig/files/ums-service.properties"
					// 	writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-ums/preconfig/files/ums-service.properties', text: readFile(FILE)
					// }
					// //println "${text}"	

					// //Validation
					// withCredentials([file(credentialsId: 'validation-service.properties', variable: 'FILE')]) {
					// 	//text = readFile(FILE)
					// 	echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-validation/preconfig/files/validation-service.properties"
					// 	writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-validation/preconfig/files/validation-service.properties', text: readFile(FILE)
					// }
					// //println "${text}"	

					// //#######################################################################################################################

}

return this