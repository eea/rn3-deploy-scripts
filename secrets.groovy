def CopySecretFiles() {
                    //println 'Test SOTAV SOTAV SOTAV SOTAV'
                    echo "Test SOTAV SOTAV SOTAV SOTAV Test SOTAV SOTAV SOTAV SOTAV Test SOTAV SOTAV SOTAV SOTAV Test SOTAV SOTAV SOTAV SOTAV"
		 			withCredentials([file(credentialsId: 'api-gateway.properties', variable: 'FILE')]) {
		 				//text = readFile(FILE)
		 				echo "writing properties secret file to $WORKSPACE/helm/eaa-deploy/reportnet-api-gateway/preconfig/files/api-gateway.properties"
		 				writeFile file: '$WORKSPACE/helm/eaa-deploy/reportnet-api-gateway/preconfig/files/api-gateway.properties', text: readFile(FILE)
						sleep(2)
		 				sh "sed -i 's/ELASTIC_INDEX:dev-apigateway-metrics/ELASTIC_INDEX:${params.Env}-apigateway-metrics/g' $WORKSPACE/helm/eaa-deploy/reportnet-api-gateway/preconfig/files/api-gateway.properties"
		 			}	

}

return this