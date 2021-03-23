#!/bin/bash
echo 'testuser:md599e8713364988502fa6189781bcf648f' >> /opt/bitnami/pgpool/conf/pool_passwd
echo 'dataflow:md5928cb8f47a17c110ec3836fcbabc976b' >> /opt/bitnami/pgpool/conf/pool_passwd
echo 'dataset:md5b48e53545a60e28f455b798e47fd3bdb' >> /opt/bitnami/pgpool/conf/pool_passwd
echo 'validation:md51ca3e86a924c07264cf43c0f2f00ad5c' >> /opt/bitnami/pgpool/conf/pool_passwd
echo 'recordstore:md5756b7ae2393844e708eba18629818472' >> /opt/bitnami/pgpool/conf/pool_passwd
sed -i 's/client_idle_limit = 0/client_idle_limit = 1200/g' /opt/bitnami/pgpool/conf/pgpool.conf