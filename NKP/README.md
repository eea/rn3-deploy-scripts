# EEA Environment Deployment Documentation

## Deploy Side Services (StatefulSets)
- Secrets
- Redis
- Kafka
- Mongo
- Postgresql
- Citus
- Keycloak
- Consul

## Deploy Reportnet Services
- API Gateway
- Collanoration
- Communication
- Dataflow
- Rod
- UMS
- Document
- Orchestrator
- Recordstore
- Dataset
- Validation
- Frontend
- Ingress

## Useful Commands
- Get a shell
  - kubectl exec -it POD -- sh -c "/bin/bash"
- Forward one port to a pod
  - kubectl port-forward svc/SERVICE LPORT:RPORT
- Copy a file from a pod
  - kubectl cp POD:/FILENAME ./DEST
- Change a PV Reclaim Policy on demand
  - kubectl patch pv PV_name -p '{"spec":{"persistentVolumeReclaimPolicy":"Retain"}}'
- Scale a deployment to 0 pods
  - kubectl scale deployment DEPLOYMENT --replicas=0
