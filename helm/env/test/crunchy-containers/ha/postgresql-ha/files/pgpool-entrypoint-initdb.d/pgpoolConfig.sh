#!/bin/bash
echo 'testuser:md5cdc0ba8c75f2cc98122838c15c470a0d' >> /opt/bitnami/pgpool/conf/pool_passwd
echo 'dataflow:md554b98b73cfe60e759ef332490d7b7371' >> /opt/bitnami/pgpool/conf/pool_passwd
echo 'dataset:md5230a13dcd5e8f72ce9ce047e5c69906d' >> /opt/bitnami/pgpool/conf/pool_passwd
echo 'validation:md5b6e17a172ec57d0cdc9a14553410b8ef' >> /opt/bitnami/pgpool/conf/pool_passwd
echo 'recordstore:md5e2a1ff8ce8fe36ca27f6f2212fe025da' >> /opt/bitnami/pgpool/conf/pool_passwd
sed -i 's/client_idle_limit = 0/client_idle_limit = 1200/g' /opt/bitnami/pgpool/conf/pgpool.conf