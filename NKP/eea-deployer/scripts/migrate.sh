#!/bin/bash

# Author: Tavoulas Sotiris @ eea.europa.eu
# Version: 1.0.1
# Date published: 10/06/2026
# Date updated: 10/06/2026

dbpass=$(grep DBPASS .secrets.prv | cut -d '=' -f2);
repmgrpass=$(grep REPMGRPASS .secrets.prv | cut -d '=' -f2);
adminpass=$(grep ADMINPASS .secrets.prv | cut -d '=' -f2);

folder=$(ls ../postgresql/migration/*.sql | sort  -Vst '/');
for f in $folder; 
do 
    fname=`basename "$f"`
    echo "$fname"
    kubectl --kubeconfig $KC -n $NS exec -n reportnet rn3-pg-helm-postgresql-0 -c postgresql -- bash -c "cd /tmp/migration/ && export PGPASSWORD=$dbpass && psql -U postgres -d metabase -c 'BEGIN TRANSACTION;' -f /tmp/migration/"$fname" -c 'COMMIT;'"
    echo "########################     $fname was imported     #######################"
done;
exit;
