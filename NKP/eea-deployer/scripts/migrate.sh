#!/bin/bash

# Author: Tavoulas Sotiris @ eea.europa.eu
# Version: 1.0.1
# Date published: 10/06/2026
# Date updated: 10/06/2026
# 
# from eea_deployer folder
# usage :
# ./scripts/migrate.sh $KUBECONFIG $NAMESPACE $TARGET_POD
#
# example :
# ./scripts/migrate.sh ~/.kube/03d-test/03prod.yaml reportnet rn3-pg-helm-postgresql-1


echo $1
echo $2
echo $3

dbpass=$(grep DBPASS .secrets.prv | cut -d '=' -f2);
repmgrpass=$(grep REPMGRPASS .secrets.prv | cut -d '=' -f2);
adminpass=$(grep ADMINPASS .secrets.prv | cut -d '=' -f2);



kubectl --kubeconfig $1 -n $2 exec  $3 -c postgresql -- bash -c "cd /tmp/migration/ && export PGPASSWORD=$dbpass && psql -U postgres -d postgres -c 'CREATE ROLE recordstore;' -c 'CREATE ROLE repmgr;'"
kubectl --kubeconfig $1 -n $2 exec  $3 -c postgresql -- bash -c "cd /tmp/migration/ && export PGPASSWORD=$dbpass && psql -U postgres -d postgres -c 'CREATE ROLE dataflow;' -c 'CREATE ROLE dataset;'"
kubectl --kubeconfig $1 -n $2 exec  $3 -c postgresql -- bash -c "cd /tmp/migration/ && export PGPASSWORD=$dbpass && psql -U postgres -d postgres -c 'CREATE ROLE validation;'"


folder=$(ls ../postgresql/migration/*.sql | sort  -Vst '/' );
for f in $folder; 
do 
    fname=`basename "$f"`
    echo "$fname"
    echo "########################     $fname START #######################"
    kubectl --kubeconfig $1 -n $2 exec  $3 -c postgresql -- bash -c "cd /tmp/migration/ && export PGPASSWORD=$dbpass && psql -U postgres -d metabase -c 'BEGIN TRANSACTION;' -f /tmp/migration/"$fname" -c 'COMMIT;'"
    echo "########################     $fname END #######################"
done;
exit;
