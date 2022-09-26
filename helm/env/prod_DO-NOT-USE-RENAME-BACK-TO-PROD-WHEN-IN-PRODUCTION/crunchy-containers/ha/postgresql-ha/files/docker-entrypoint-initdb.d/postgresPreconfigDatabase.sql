ALTER SYSTEM SET max_connections = '600';
ALTER SYSTEM SET shared_buffers = '8GB';
ALTER SYSTEM SET maintenance_work_mem = '2GB';
ALTER SYSTEM SET work_mem = '128MB';
ALTER SYSTEM SET checkpoint_completion_target = '0.9';
ALTER SYSTEM SET random_page_cost = '1.1';
ALTER SYSTEM SET effective_io_concurrency = '300';
ALTER SYSTEM SET min_wal_size = '1GB';
ALTER SYSTEM SET max_wal_size = '4GB';
ALTER SYSTEM SET synchronous_commit='remote_apply';
ALTER SYSTEM SET effective_cache_size='24GB';
ALTER SYSTEM SET wal_buffers='16MB';
ALTER SYSTEM SET default_statistics_target = '100';
ALTER SYSTEM SET max_worker_processes = '12';
ALTER SYSTEM SET max_parallel_workers_per_gather = '4';
ALTER SYSTEM SET max_parallel_workers = '12';
ALTER SYSTEM SET max_parallel_maintenance_workers = '4';
ALTER SYSTEM SET idle_in_transaction_session_timeout = '3600s';

CREATE extension if not exists postgis;
CREATE extension if not exists fuzzystrmatch;
CREATE EXTENSION if not exists postgis_tiger_geocoder;


CREATE USER testuser WITH PASSWORD '53p057n373.0!';
CREATE USER dataset WITH PASSWORD '53p057n373.0!';
CREATE USER dataflow WITH PASSWORD '53p057n373.0!';
CREATE USER recordstore WITH PASSWORD '53p057n373.0!';
CREATE USER validation WITH PASSWORD '53p057n373.0!';

DROP DATABASE IF EXISTS datasets;
DROP DATABASE IF EXISTS metabase;
DROP DATABASE IF EXISTS keycloak;

CREATE DATABASE datasets
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    TEMPLATE = template0
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
	
CREATE DATABASE metabase
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    TEMPLATE = template0
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
	
CREATE DATABASE keycloak
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    TEMPLATE = template0
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
GRANT ALL PRIVILEGES ON DATABASE datasets TO testuser,dataflow,dataset,recordstore,validation;
GRANT ALL PRIVILEGES ON DATABASE metabase TO testuser,dataflow,dataset,recordstore,validation;
GRANT ALL PRIVILEGES ON DATABASE keycloak TO testuser,dataflow,dataset,recordstore,validation;

\c keycloak

GRANT ALL PRIVILEGES ON ALL tables in schema public to testuser,dataflow,dataset,recordstore,validation;
grant all privileges on all sequences in schema public to testuser,dataflow,dataset,recordstore,validation;
\c metabase

GRANT ALL PRIVILEGES ON ALL tables in schema public to testuser,dataflow,dataset,recordstore,validation;
grant all privileges on all sequences in schema public to testuser,dataflow,dataset,recordstore,validation;
\c datasets
CREATE extension if not exists postgis;
CREATE extension if not exists fuzzystrmatch;
CREATE EXTENSION if not exists postgis_tiger_geocoder;