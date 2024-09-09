#!/bin/bash
# Trasys.gr Port Forwarding for environments Tavoulas Sotiris Sep/2024
#
# LAST UPDATE @ 06/09/2024 Tavoulas Sotiris
#
# Usage  "./pf.sh on" to start the port forwarding
# Usage  "./pf.sh off" to stop and kill all kubectl processes port forwards
# Sotav specific
#
# cd /home/sotav/TRASYS_GIT_PRV/EAA/rn3-deploy-scripts/helm/eaa-deploy/scripts

# Conventions agreed!
#01-20=web-ui
#21-40=svs 
#41-60=metrics

# Change only the below variables
declare -A envr1
declare -A envr2

envr1[dev]=reportnet
envr1[test]=reportnet
envr1[sandbox]=reportnet
#envr1[prod]=reportnet
#envr1[prod2]=reportnet

envr2[dev-r2]=kavoulas


#######################################################################################################################
#######################################################################################################################


if [ -z "$1" ]; then
 echo "Please enter an option like : './pf.sh on' or './pf.sh off' "
 exit;
fi

if [ "$1" = "on" ] ; then
    echo "Selection was: $1"
    echo "Starting the portforward"

    # COMMENT THIS OUT IF YOU ALSO DO FORWARDING BY YOUR OWN 
    #before starting kill any previous port forward from the kubectl 
    pkill kubectl -9



    c=0
    for item in "${!envr1[@]}";
      do
            echo "-------------------------------START------------------------------------------"
            printf "$item environment has the namespace of  ${envr1[$item]} \n"
            export KC=~/.kube/$item/config
            export NS=${envr1[$item]}
            #echo $KC
            #echo $NS
            c=$((c + 1))
            echo "$c"
            
            #kubectl --kubeconfig $KC -n $NS get services
            #####################################################################################################

            echo "exposing service consul-ui to https://localhost:1"$c"702"
                kubectl --kubeconfig $KC -n $NS port-forward service/consul-ui 1"$c"702:8500 &
            
            echo "exposing service rn-kafka-ui to https://localhost:1"$c"703"
                kubectl --kubeconfig $KC -n $NS port-forward service/rn-kafka-ui 1"$c"703:9002 &


            echo "exposing service minio-rn3-service to https://localhost:1"$c"704"
                kubectl --kubeconfig $KC -n $NS port-forward service/minio-rn3-service 1"$c"704:9001 &


            echo "exposing service keycloak-http to https://localhost:1"$c"705"
                kubectl --kubeconfig $KC -n $NS port-forward service/keycloak-http 1"$c"705:80 &


            # echo "exposing service rn3-postgresql-ha-pgpool to https://localhost:1"$c"721"
            #     kubectl --kubeconfig $KC -n $NS port-forward service/rn3-postgresql-ha-pgpool 1"$c"721:5432 &

            # echo "exposing service rn3-redis-master to https://localhost:1"$c"722"
            #     kubectl --kubeconfig $KC -n $NS port-forward service/rn3-redis-master 1"$c"722:6379 &

            # echo "exposing service rn3-redis-master to https://localhost:1"$c"741"
            #     kubectl --kubeconfig $KC -n $NS port-forward service/rn3-mongo-mongodb-metrics 1"$c"741:9216 &

            # echo "exposing service rn3-postgresql-ha-postgresql-metrics to https://localhost:1"$c"742"
            #     kubectl --kubeconfig $KC -n $NS port-forward service/rn3-postgresql-ha-postgresql-metrics 1"$c"742:9187 &
            #####################################################################################################
            echo "--------------------------------END--------------------------------------------"
            

      done



    for item in "${!envr2[@]}";
      do
            echo "-------------------------------START------------------------------------------"
            printf "$item environment has the namespace of  ${envr2[$item]} \n"
            export KC=~/.kube/$item/config
            export NS=${envr2[$item]}
            #echo $KC
            #echo $NS
            
            c=$((c + 1))
            echo "$c"
            #kubectl --kubeconfig $KC -n $NS get services
            #####################################################################################################

            echo "exposing service mongo-ui to https://localhost:1"$c"701"
                kubectl --kubeconfig $KC -n $NS port-forward service/rn3-mongo-ui 1"$c"701:8085 &

            echo "exposing service rn3-consul-ui to https://localhost:1"$c"702"
                kubectl --kubeconfig $KC -n $NS port-forward service/rn3-consul-ui 1"$c"702:8500 &
            
            echo "exposing service rn3-keycloak to https://localhost:1"$c"703"
                kubectl --kubeconfig $KC -n $NS port-forward service/rn3-keycloak 1"$c"703:80 &

            echo "exposing service rn3-postgresql-ha-pgpool to https://localhost:1"$c"721"
                kubectl --kubeconfig $KC -n $NS port-forward service/rn3-postgresql-ha-pgpool 1"$c"721:5432 &

            echo "exposing service rn3-redis-master to https://localhost:1"$c"722"
                kubectl --kubeconfig $KC -n $NS port-forward service/rn3-redis-master 1"$c"722:6379 &

            echo "exposing service rn3-redis-master to https://localhost:1"$c"741"
                kubectl --kubeconfig $KC -n $NS port-forward service/rn3-mongo-mongodb-metrics 1"$c"741:9216 &

            echo "exposing service rn3-postgresql-ha-postgresql-metrics to https://localhost:1"$c"742"
                kubectl --kubeconfig $KC -n $NS port-forward service/rn3-postgresql-ha-postgresql-metrics 1"$c"742:9187 &
            #####################################################################################################

            echo "--------------------------------END--------------------------------------------"
            
      done
#######################################################################################################################
#######################################################################################################################
fi

if [ "$1" = "off" ] ; then
    echo "Selection was: $1"
    echo "Stoping the portforward"
    pkill kubectl -9
fi

exit;
