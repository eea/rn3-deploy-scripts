#!/bin/bash
echo 'user:passmd5' >> /opt/bitnami/pgpool/conf/pool_passwd
echo 'user:passmd5' >> /opt/bitnami/pgpool/conf/pool_passwd
echo 'user:passmd5' >> /opt/bitnami/pgpool/conf/pool_passwd
echo 'user:passmd5' >> /opt/bitnami/pgpool/conf/pool_passwd
echo 'user:passmd5' >> /opt/bitnami/pgpool/conf/pool_passwd
sed -i 's/client_idle_limit = 0/client_idle_limit = 1200/g' /opt/bitnami/pgpool/conf/pgpool.conf