#!/bin/bash
# Trasys.gr Clear Consul services  by Tavoulas Sotiris November/2022
#
# LAST UPDATE @ 11/11/2022 Tavoulas Sotiris / Sotirios.TAVOULAS-KAVOULAS@trasys.gr
# 

tmp_fld="./data"
services=( 
"apiGateway"
"collaboration"
"communication"
"orchestrator"
"dataflow"
"dataset"
"document"
"recordstore"
"rod"
"ums"
"validation"
)




for (( i=0; i<${#services[@]}; i++ ));
do
		curl http://kvm-rn3prod2-01.pdmz.eea:31546/v1/catalog/service/${services[$i]}| jq . > $tmp_fld/${services[$i]}.json 
		cat $tmp_fld/${services[$i]}.json  |grep '"ServiceID":' > $tmp_fld/${services[$i]}.txt 
		cat $tmp_fld/${services[$i]}.txt | awk -F": " '{print $2}' > $tmp_fld/AWK_${services[$i]}.txt
		sed -i 's/"//g' $tmp_fld/AWK_${services[$i]}.txt 
		sed -i 's/,//g' $tmp_fld/AWK_${services[$i]}.txt
		filename="$tmp_fld/AWK_${services[$i]}.txt"

		cat $filename

		echo Start
		while read p; do 
			#sleep 2
			echo "Removing $p from  http://kvm-rn3prod2-01.pdmz.eea:31546 "
			curl -v -X PUT http://kvm-rn3prod2-01.pdmz.eea:31546/v1/agent/service/deregister/$p
			#sleep 1
			echo "Removing $p from the second endpoint http://10.50.4.185:32233 just for the fun of it !!!!"
			curl -v -X PUT http://10.42.134.174:31546/v1/agent/service/deregister/$p
			
			
		done < "$filename"
done



