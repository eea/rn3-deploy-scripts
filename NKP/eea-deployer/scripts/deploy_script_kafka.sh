#!/bin/bash

# Author: Tavoulas Sotiris @ eea.europa.eu
# Version: 1.0.1
# Date published: 22/09/2026
# Date updated: 22/09/2026
# 

# usage :
# .deploy_script.sh $KUBECONFIG $NAMESPACE $TARGET_POD
#
# example :
# .deploy_script.sh ~/.kube/03d-test/03prod.yaml reportnet ../kafka/. rn3-test-nkp-kafka.yaml /
# $(grep INTER_BROKER_PASSWORD .secrets.prv | cut -d '=' -f2) /
# $(grep CONTROLLER_PASSWORD .secrets.prv | cut -d '=' -f2) /
# test-nkp-kafka-01/
# 3Gi /
# 3Gi



helm --kubeconfig $1 -n $2 upgrade --install kafka $3/. -f $4  \
--set sasl.interbroker.password=$5 \
--set sasl.controller.password=$6 \
--set kraft.clusterId=$7 \
--set resources.requests.memory=$8 \
--set resources.limit.memory=$9

exit;