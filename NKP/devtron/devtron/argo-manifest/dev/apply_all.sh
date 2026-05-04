#!/bin/bash


rn3_apps=(
#"velero"
)

export ENV=dev
export NS=devtroncd
export KC="~./kube/staging"
export KUBECONFIG_SAVED="$KC"
#export CN=     #arn:aws:eks:aws-region:id:cluster/cluster_name
export BASE_SRC_CODE=/home/sotav/TRASYS_GIT_PRV/EAA/rn3-deploy-scripts/NKP/eea

for app in ${rn3_apps[@]}; do
    cd _$app
    fname=$(ls |grep "argo_application_")
    cd ../
    kubectl --kubeconfig=$KC -n $NS apply --filename _$app/$fname
done


