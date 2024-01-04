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


// Dev PubKey
// from
//from 
// KEYCLOAK_CLIENT_PUBLIC_KEY:MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuvOUY9g2UITSyRMk4yNnA0sgzWmjKdRFNHGixZRxDzyjUjd6jrgdTYcmC7NXRZK9+BJujOMd9ArjECVkKiReKudmg7l/zEWfB60EM+XDP3aqox2/v3ytwoasCQJF0MmoiljrSChHLygNJmEaOY++3UcXbw+OwTBK2C9LQNG1X3U0xv6tPNXSqq0OUtFka9Otjujam/8hrqurPOW3tWPfaMvh5gw07CBhe+YnIqmTI/LuJxjIf0Q7tbE2HD8TJLeS8LqySHqXlZPQ9tDiqZtkm8e3jGs2U7ZgHy3DNLz1xB5M8rBKA2/3bI+FIfY6dg/M4uPN4LgPxm5NXOpdA2k34wIDAQAB}
// KEYCLOAK_CLIENT_PUBLIC_KEY:MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgbMIQ/yJEarq3gz5Ie+wqXs6p878xDeZcM/u8zu/f950BLAcNoNrXTt6lyFThhqxnD4O2N6Ffz4TIOigNwHsXbVGPZy2N1o8Smdaxk+YvrbzOXFELYEna2CZtwV6Gl7nkoLjmZVS143QunYLJ3d34ZTRskp5CYrFJRjaCBnB5LXGilAzaEdLRb4Rr6jU9xker7HGOx4/ZWeNkE3IwLkCzkKeGO8Jz7HS+xzwaMuYXCIl/8WD8e0fcKb6RzruDfepGtQmoVtmmzLF+3kQJOHN0vu+QeYkF7mLkkBAbXDbEgihILnOyocF1S+pEsxaHPkiVeslH32ieV1NWbqf7t3UPwIDAQAB}
						// FOR EACH ENV WE NEED TO PUT THE NEW KEYCLOAK KEY TO THE BELOW BLOCK - WHENEVER THERE IS A NEW KEY !
						 sh """	case "${params.Env}" in
								dev )
									export OLD_PUB_KEY="KEYCLOAK_CLIENT_PUBLIC_KEY:MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuvOUY9g2UITSyRMk4yNnA0sgzWmjKdRFNHGixZRxDzyjUjd6jrgdTYcmC7NXRZK9+BJujOMd9ArjECVkKiReKudmg7l/zEWfB60EM+XDP3aqox2/v3ytwoasCQJF0MmoiljrSChHLygNJmEaOY++3UcXbw+OwTBK2C9LQNG1X3U0xv6tPNXSqq0OUtFka9Otjujam/8hrqurPOW3tWPfaMvh5gw07CBhe+YnIqmTI/LuJxjIf0Q7tbE2HD8TJLeS8LqySHqXlZPQ9tDiqZtkm8e3jGs2U7ZgHy3DNLz1xB5M8rBKA2/3bI+FIfY6dg/M4uPN4LgPxm5NXOpdA2k34wIDAQAB"
									export NEW_PUB_KEY="KEYCLOAK_CLIENT_PUBLIC_KEY:MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgbMIQ/yJEarq3gz5Ie+wqXs6p878xDeZcM/u8zu/f950BLAcNoNrXTt6lyFThhqxnD4O2N6Ffz4TIOigNwHsXbVGPZy2N1o8Smdaxk+YvrbzOXFELYEna2CZtwV6Gl7nkoLjmZVS143QunYLJ3d34ZTRskp5CYrFJRjaCBnB5LXGilAzaEdLRb4Rr6jU9xker7HGOx4/ZWeNkE3IwLkCzkKeGO8Jz7HS+xzwaMuYXCIl/8WD8e0fcKb6RzruDfepGtQmoVtmmzLF+3kQJOHN0vu+QeYkF7mLkkBAbXDbEgihILnOyocF1S+pEsxaHPkiVeslH32ieV1NWbqf7t3UPwIDAQAB"
									echo "dev Environment was selected" 
									echo "The contents of the application.properties file is currently : " 
									cat $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties | grep "KEYCLOAK_SECRET"
									cat $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties | grep "KEYCLOAK_REDIRECT_URI"
									echo "Changing the file  " 
									sed -i "s/KEYCLOAK_CLIENT_PUBLIC_KEY:MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuvOUY9g2UITSyRMk4yNnA0sgzWmjKdRFNHGixZRxDzyjUjd6jrgdTYcmC7NXRZK9+BJujOMd9ArjECVkKiReKudmg7l/zEWfB60EM+XDP3aqox2/v3ytwoasCQJF0MmoiljrSChHLygNJmEaOY++3UcXbw+OwTBK2C9LQNG1X3U0xv6tPNXSqq0OUtFka9Otjujam/8hrqurPOW3tWPfaMvh5gw07CBhe+YnIqmTI/LuJxjIf0Q7tbE2HD8TJLeS8LqySHqXlZPQ9tDiqZtkm8e3jGs2U7ZgHy3DNLz1xB5M8rBKA2/3bI+FIfY6dg/M4uPN4LgPxm5NXOpdA2k34wIDAQAB/KEYCLOAK_CLIENT_PUBLIC_KEY:MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgbMIQ/yJEarq3gz5Ie+wqXs6p878xDeZcM/u8zu/f950BLAcNoNrXTt6lyFThhqxnD4O2N6Ffz4TIOigNwHsXbVGPZy2N1o8Smdaxk+YvrbzOXFELYEna2CZtwV6Gl7nkoLjmZVS143QunYLJ3d34ZTRskp5CYrFJRjaCBnB5LXGilAzaEdLRb4Rr6jU9xker7HGOx4/ZWeNkE3IwLkCzkKeGO8Jz7HS+xzwaMuYXCIl/8WD8e0fcKb6RzruDfepGtQmoVtmmzLF+3kQJOHN0vu+QeYkF7mLkkBAbXDbEgihILnOyocF1S+pEsxaHPkiVeslH32ieV1NWbqf7t3UPwIDAQAB/g" $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties
									
									sed -i "s/KEYCLOAK_SECRET:0380996f-a7ad-4667-8ba4-14995e408d24/KEYCLOAK_SECRET:8bb193b1-fc5e-4112-9c4c-58a0d615c7e8/g" $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties
									sed -i "s/reportnet.europa.eu/${params.Env}.reportnet.europa.eu/g" $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties
									
									echo "The contents of application.properties file now is  : "
									cat $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties | grep "KEYCLOAK_SECRET"
									cat $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties | grep "KEYCLOAK_REDIRECT_URI"
									;;
								test ) 
									export KC_OLD_PUB_KEY="KEYCLOAK_CLIENT_PUBLIC_KEY:MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuvOUY9g2UITSyRMk4yNnA0sgzWmjKdRFNHGixZRxDzyjUjd6jrgdTYcmC7NXRZK9+BJujOMd9ArjECVkKiReKudmg7l/zEWfB60EM+XDP3aqox2/v3ytwoasCQJF0MmoiljrSChHLygNJmEaOY++3UcXbw+OwTBK2C9LQNG1X3U0xv6tPNXSqq0OUtFka9Otjujam/8hrqurPOW3tWPfaMvh5gw07CBhe+YnIqmTI/LuJxjIf0Q7tbE2HD8TJLeS8LqySHqXlZPQ9tDiqZtkm8e3jGs2U7ZgHy3DNLz1xB5M8rBKA2/3bI+FIfY6dg/M4uPN4LgPxm5NXOpdA2k34wIDAQAB"
									export KC_NEW_PUB_KEY="KEYCLOAK_CLIENT_PUBLIC_KEY:MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgbMIQ/yJEarq3gz5Ie+wqXs6p878xDeZcM/u8zu/f950BLAcNoNrXTt6lyFThhqxnD4O2N6Ffz4TIOigNwHsXbVGPZy2N1o8Smdaxk+YvrbzOXFELYEna2CZtwV6Gl7nkoLjmZVS143QunYLJ3d34ZTRskp5CYrFJRjaCBnB5LXGilAzaEdLRb4Rr6jU9xker7HGOx4/ZWeNkE3IwLkCzkKeGO8Jz7HS+xzwaMuYXCIl/8WD8e0fcKb6RzruDfepGtQmoVtmmzLF+3kQJOHN0vu+QeYkF7mLkkBAbXDbEgihILnOyocF1S+pEsxaHPkiVeslH32ieV1NWbqf7t3UPwIDAQAB"
									sed -i "s/$KC_OLD_PUB_KEY/$KC_NEW_PUB_KEY/g" $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties
									sed -i "s/KEYCLOAK_SECRET:0380996f-a7ad-4667-8ba4-14995e408d24/KEYCLOAK_SECRET:cd7691a4-9f26-48a2-be9a-4d4dbde2b331/g" $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties
									sed -i "s/reportnet.europa.eu/${params.Env}.reportnet.europa.eu/g" $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties
									;;
								sandbox | testing )
									export OLD_PUB_KEY="KEYCLOAK_CLIENT_PUBLIC_KEY:MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuvOUY9g2UITSyRMk4yNnA0sgzWmjKdRFNHGixZRxDzyjUjd6jrgdTYcmC7NXRZK9+BJujOMd9ArjECVkKiReKudmg7l/zEWfB60EM+XDP3aqox2/v3ytwoasCQJF0MmoiljrSChHLygNJmEaOY++3UcXbw+OwTBK2C9LQNG1X3U0xv6tPNXSqq0OUtFka9Otjujam/8hrqurPOW3tWPfaMvh5gw07CBhe+YnIqmTI/LuJxjIf0Q7tbE2HD8TJLeS8LqySHqXlZPQ9tDiqZtkm8e3jGs2U7ZgHy3DNLz1xB5M8rBKA2/3bI+FIfY6dg/M4uPN4LgPxm5NXOpdA2k34wIDAQAB"
									export NEW_PUB_KEY="KEYCLOAK_CLIENT_PUBLIC_KEY:~~~~~~~~~~~~~~~~~ CHANGE ME ~~~~~~~~~~~~~~~~~~~~~"
									sed -i "s/KEYCLOAK_SECRET:0380996f-a7ad-4667-8ba4-14995e408d24/KEYCLOAK_SECRET:24c0724e-6f20-48f6-917f-e83ef5f03926/g" $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties
									sed -i "s/reportnet.europa.eu/${params.Env}.reportnet.europa.eu/g" $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties
									;;
								prod )
									export OLD_PUB_KEY="KEYCLOAK_CLIENT_PUBLIC_KEY:MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuvOUY9g2UITSyRMk4yNnA0sgzWmjKdRFNHGixZRxDzyjUjd6jrgdTYcmC7NXRZK9+BJujOMd9ArjECVkKiReKudmg7l/zEWfB60EM+XDP3aqox2/v3ytwoasCQJF0MmoiljrSChHLygNJmEaOY++3UcXbw+OwTBK2C9LQNG1X3U0xv6tPNXSqq0OUtFka9Otjujam/8hrqurPOW3tWPfaMvh5gw07CBhe+YnIqmTI/LuJxjIf0Q7tbE2HD8TJLeS8LqySHqXlZPQ9tDiqZtkm8e3jGs2U7ZgHy3DNLz1xB5M8rBKA2/3bI+FIfY6dg/M4uPN4LgPxm5NXOpdA2k34wIDAQAB"
									export NEW_PUB_KEY="KEYCLOAK_CLIENT_PUBLIC_KEY:~~~~~~~~~~~~~~~~~ CHANGE ME ~~~~~~~~~~~~~~~~~~~~~"
									sed -i "s/KEYCLOAK_SECRET:0380996f-a7ad-4667-8ba4-14995e408d24/KEYCLOAK_SECRET: ~~~~~ CHANGE ME ~~~~~~ /g" $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties
									sed -i "s/reportnet.europa.eu/${params.Env}.reportnet.europa.eu/g" $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties
									;;
								prod2 | transport )
									export OLD_PUB_KEY="KEYCLOAK_CLIENT_PUBLIC_KEY:MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuvOUY9g2UITSyRMk4yNnA0sgzWmjKdRFNHGixZRxDzyjUjd6jrgdTYcmC7NXRZK9+BJujOMd9ArjECVkKiReKudmg7l/zEWfB60EM+XDP3aqox2/v3ytwoasCQJF0MmoiljrSChHLygNJmEaOY++3UcXbw+OwTBK2C9LQNG1X3U0xv6tPNXSqq0OUtFka9Otjujam/8hrqurPOW3tWPfaMvh5gw07CBhe+YnIqmTI/LuJxjIf0Q7tbE2HD8TJLeS8LqySHqXlZPQ9tDiqZtkm8e3jGs2U7ZgHy3DNLz1xB5M8rBKA2/3bI+FIfY6dg/M4uPN4LgPxm5NXOpdA2k34wIDAQAB"
									export NEW_PUB_KEY="KEYCLOAK_CLIENT_PUBLIC_KEY:~~~~~~~~~~~~~~~~~ CHANGE ME ~~~~~~~~~~~~~~~~~~~~~"
									sed -i "s/KEYCLOAK_SECRET:0380996f-a7ad-4667-8ba4-14995e408d24/KEYCLOAK_SECRET: ~~~~~ CHANGE ME ~~~~~~ /g" $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties
									sed -i "s/reportnet.europa.eu/${params.Env}.reportnet.europa.eu/g" $WORKSPACE/helm/eaa-deploy/application-config/files/application.properties
									;;
								esac
							"""







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
