#!/bin/bash
# Author:	Tavoulas Sotiris Sotirios.TAVOULAS-KAVOULAS@trasys.gr P70693@nrb.be
# Created:	12-01-2024 
# Modified:	----------

#Usage of the script : ./pgpool-usr-fix.sh ENV NAMESPACE KUBECONFIG
#Example : ./pgpool-usr-fix.sh test reportnet .kube/test/config
#or 
#Example : ./pgpool-usr-fix.sh test reportnet ../../../../.kube/test/config
#			#SOTAV_SPECIFIC USE ON MY TERMINAL
#           # cd /drives/c/Users/P70693/EAA/rn3-deploy-scripts/helm/eaa-deploy/scripts
#           # ./pgpool-usr-fix.sh prod2 reportnet ../../../../.kube/prod2/config
#           # ./pgpool-usr-fix.sh test reportnet ../../../../.kube/test/config
#           # ./pgpool-usr-fix.sh dev reportnet ../../../../.kube/dev/config

# ~/EAA/rn3-deploy-scripts/helm/eaa-deploy/scripts

# Temporary colors
NORMAL=''
GREEN=''
RED=''
ITALIC=''
BOLD=''
if tty -s; then
  NORMAL="$(tput sgr0)"
  GREEN=$(tput setaf 2)
  RED="$(tput setaf 1)"
  BOLD=$(tput bold)
  ITALIC=$(tput sitm)
fi

ENV=$1
NS=$2
KC=$3

users=("echo 'postgres:md5a667f81c18c360beb563089097a5b94f' > /opt/bitnami/pgpool/conf/pool_passwd && \
echo 'testuser:md5cdc0ba8c75f2cc98122838c15c470a0d' >> /opt/bitnami/pgpool/conf/pool_passwd && \
echo 'dataflow:md554b98b73cfe60e759ef332490d7b7371' >> /opt/bitnami/pgpool/conf/pool_passwd && \
echo 'dataset:md5230a13dcd5e8f72ce9ce047e5c69906d' >> /opt/bitnami/pgpool/conf/pool_passwd && \
echo 'validation:md5b6e17a172ec57d0cdc9a14553410b8ef' >> /opt/bitnami/pgpool/conf/pool_passwd && \
echo 'recordstore:md5e2a1ff8ce8fe36ca27f6f2212fe025da' >> /opt/bitnami/pgpool/conf/pool_passwd");

# For testing the PoC uncomment the below and comment out the above block or just any new user to the above block !!!
#users=("echo 'postgres:md5a667f81c18c360beb563089097a5b94f' > /opt/bitnami/pgpool/conf/pool_passwd && \
#echo 'testuser:md5cdc0ba8c75f2cc98122838c15c470a0d' >> /opt/bitnami/pgpool/conf/pool_passwd && \
#echo 'dataflow:md554b98b73cfe60e759ef332490d7b7371' >> /opt/bitnami/pgpool/conf/pool_passwd && \
#echo 'dataset:md5230a13dcd5e8f72ce9ce047e5c69906d' >> /opt/bitnami/pgpool/conf/pool_passwd && \
#echo 'validation:md5b6e17a172ec57d0cdc9a14553410b8ef' >> /opt/bitnami/pgpool/conf/pool_passwd && \
#echo 'recordstore:md5e2a1ff8ce8fe36ca27f6f2212fe025da' >> /opt/bitnami/pgpool/conf/pool_passwd && \
#echo 'sotav:md5e2a1ff8ce8fe36ca27f6f2212fe025da' >> /opt/bitnami/pgpool/conf/pool_passwd && \ 
#echo 'fotis:md5e2a1ff8ce8fe36ca27f6f2212fe025da' >> /opt/bitnami/pgpool/conf/pool_passwd");

	if [ -z "$1" ]
	then
		echo "########################################################"
		echo "########################################################"
		echo "Usage of the script : ./pgpool-usr-fix.sh ENV NAMESPACE KUBECONFIG"
		echo " "
		echo "Example : ./pgpool-usr-fix.sh test reportnet .kube/test/config"
		echo "or "
		echo "Example : ./pgpool-usr-fix.sh test reportnet ../../../../.kube/test/config"
		echo " "
		echo "########################################################"
		echo "########################################################"
		echo " The script run without the vars set, so i will set the default ones which is for test environment"
		echo "########################################################"
		echo "########################################################"
		ENV="test"
	fi;
	if [ -z "$2" ]
	then
		NS="reportnet"
	fi;
	if [ -z "$3" ]
	then
		KC=".kube/$ENV/config"
	fi;

export ENV=$ENV
export NS=$NS
export KC=$KC

echo $ENV
echo $NS
echo $KC

Array=($(kubectl --kubeconfig=$KC -n $NS get pods | grep helm-pgpool | awk -F " " '{print $1}'));
for str in ${Array[@]}
 do
  echo $str
  
	kubectl --kubeconfig=$KC exec -it -n $NS $str -- sh -c "$users"
	echo "the contents of the file on $str is :"
	kubectl --kubeconfig=$KC exec -it -n $NS $str -- sh -c "cat /opt/bitnami/pgpool/conf/pool_passwd"
	echo "########################################################"
done


echo "Done"
