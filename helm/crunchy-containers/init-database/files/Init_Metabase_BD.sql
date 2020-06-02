CREATE DATABASE datasets
    WITH 
    OWNER = testuser
    ENCODING = 'UTF8'
    TEMPLATE = template0
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
\c datasets
CREATE SCHEMA dataset_0 AUTHORIZATION testuser;
CREATE TABLE dataset_0.DATASET_VALUE (ID bigint, ID_DATASET_SCHEMA text, PRIMARY key (ID))  TABLESPACE pg_default;
CREATE TABLE dataset_0.TABLE_VALUE (ID bigint, ID_TABLE_SCHEMA text, DATASET_ID bigint, PRIMARY key (ID), FOREIGN KEY (dataset_id) REFERENCES dataset_0.dataset_value(id) ON DELETE CASCADE) TABLESPACE pg_default;
CREATE TABLE dataset_0.RECORD_VALUE(ID bigint, ID_RECORD_SCHEMA text, ID_TABLE bigint, DATASET_PARTITION_ID bigint, PRIMARY key (ID), FOREIGN KEY (id_table) REFERENCES dataset_0.table_value(id) ON DELETE CASCADE) TABLESPACE pg_default;
CREATE TABLE dataset_0.FIELD_VALUE (ID bigint, TYPE text, VALUE text, ID_FIELD_SCHEMA text, ID_RECORD bigint, PRIMARY key (ID), FOREIGN KEY (id_record) REFERENCES dataset_0.record_value(id) ON DELETE CASCADE) TABLESPACE pg_default;
CREATE TABLE dataset_0.VALIDATION (ID bigint,ID_RULE text,LEVEL_ERROR text, MESSAGE text, TYPE_ENTITY text, VALIDATION_DATE text, ORIGIN_NAME TEXT, PRIMARY KEY (id));
CREATE TABLE dataset_0.DATASET_VALIDATION (ID bigint,ID_DATASET bigint,ID_VALIDATION bigint, PRIMARY KEY (ID), FOREIGN KEY (ID_VALIDATION) REFERENCES dataset_0.VALIDATION(ID) ON DELETE CASCADE,FOREIGN KEY (ID_DATASET) REFERENCES dataset_0.DATASET_VALUE(id) ON DELETE CASCADE) TABLESPACE pg_default;
CREATE TABLE dataset_0.TABLE_VALIDATION (ID bigint,ID_TABLE bigint,ID_VALIDATION bigint, PRIMARY KEY (ID), FOREIGN KEY (ID_VALIDATION) REFERENCES dataset_0.VALIDATION(ID) ON DELETE CASCADE,FOREIGN KEY (ID_TABLE) REFERENCES dataset_0.TABLE_VALUE(id) ON DELETE CASCADE) TABLESPACE pg_default;
CREATE TABLE dataset_0.RECORD_VALIDATION (ID bigint,ID_RECORD bigint,ID_VALIDATION bigint, PRIMARY KEY (ID), FOREIGN KEY (ID_VALIDATION) REFERENCES dataset_0.VALIDATION(ID) ON DELETE CASCADE,FOREIGN KEY (ID_RECORD) REFERENCES dataset_0.RECORD_VALUE(id) ON DELETE CASCADE) TABLESPACE pg_default;
CREATE TABLE dataset_0.FIELD_VALIDATION (ID bigint,ID_FIELD bigint,ID_VALIDATION bigint, PRIMARY KEY (ID), FOREIGN KEY (ID_VALIDATION) REFERENCES dataset_0.VALIDATION(ID) ON DELETE CASCADE ,FOREIGN KEY (ID_FIELD) REFERENCES dataset_0.FIELD_VALUE(id) ON DELETE CASCADE) TABLESPACE pg_default;



ALTER TABLE dataset_0.DATASET_VALUE  OWNER to testuser;
ALTER TABLE dataset_0.TABLE_VALUE  OWNER to testuser;
ALTER TABLE dataset_0.RECORD_VALUE  OWNER to testuser;
ALTER TABLE dataset_0.FIELD_VALUE  OWNER to testuser;
ALTER TABLE dataset_0.DATASET_VALIDATION OWNER TO testuser;
ALTER TABLE dataset_0.TABLE_VALIDATION OWNER TO testuser;
ALTER TABLE dataset_0.FIELD_VALIDATION OWNER TO testuser;
ALTER TABLE dataset_0.RECORD_VALIDATION OWNER TO testuser;
ALTER TABLE dataset_0.VALIDATION OWNER TO testuser;


CREATE SEQUENCE dataset_0.field_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE;
CREATE SEQUENCE dataset_0.record_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE;
CREATE SEQUENCE dataset_0.table_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE;
CREATE SEQUENCE dataset_0.field_validation_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE;
CREATE SEQUENCE dataset_0.record_validation_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE;
CREATE SEQUENCE dataset_0.table_validation_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE;
CREATE SEQUENCE dataset_0.dataset_validation_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE;
CREATE SEQUENCE dataset_0.validation_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE;

	

ALTER SEQUENCE dataset_0.field_sequence OWNER TO testuser;
GRANT ALL ON SEQUENCE dataset_0.field_sequence TO testuser;

ALTER SEQUENCE dataset_0.record_sequence OWNER TO testuser;
GRANT ALL ON SEQUENCE dataset_0.record_sequence TO testuser;

ALTER SEQUENCE dataset_0.table_sequence OWNER TO testuser;
GRANT ALL ON SEQUENCE dataset_0.table_sequence TO testuser;

CREATE INDEX idx_record_value ON dataset_0.field_value(id_record);
CREATE INDEX idx_field_schema ON dataset_0.field_value(id_field_schema);
CREATE INDEX idx_dataset_value ON dataset_0.table_value(dataset_id);
CREATE INDEX idx_table_schema ON dataset_0.table_value(id_table_schema);
CREATE INDEX idx_table_value ON dataset_0.record_value(id_table);
CREATE INDEX idx_record_schema ON dataset_0.record_value(id_record_schema);
CREATE INDEX idx_table_validation_table ON dataset_0.table_validation(id_table);
CREATE INDEX idx_table_validation_validation ON dataset_0.table_validation(id_validation);
CREATE INDEX idx_dataset_validation_dataset ON dataset_0.dataset_validation(id_dataset);
CREATE INDEX idx_dataset_validation_validation ON dataset_0.dataset_validation(id_validation);
CREATE INDEX idx_field_validation_field ON dataset_0.field_validation(id_field);
CREATE INDEX idx_field_validation_validation ON dataset_0.field_validation(id_validation);
CREATE INDEX idx_record_validation_record ON dataset_0.record_validation(id_record);
CREATE INDEX idx_record_validation_validation ON dataset_0.record_validation(id_validation);
CREATE INDEX idx_validation_level_error ON dataset_0.validation(level_error);


ALTER SEQUENCE dataset_0.field_sequence OWNER TO testuser;
GRANT ALL ON SEQUENCE dataset_0.field_validation_sequence TO testuser;

ALTER SEQUENCE dataset_0.record_sequence OWNER TO testuser;
GRANT ALL ON SEQUENCE dataset_0.record_validation_sequence TO testuser;

ALTER SEQUENCE dataset_0.table_sequence OWNER TO testuser;
GRANT ALL ON SEQUENCE dataset_0.table_validation_sequence TO testuser;

ALTER SEQUENCE dataset_0.validation_sequence OWNER TO testuser;
GRANT ALL ON SEQUENCE dataset_0.validation_sequence TO testuser;

ALTER SEQUENCE dataset_0.dataset_validation_sequence OWNER TO testuser;
GRANT ALL ON SEQUENCE dataset_0.dataset_validation_sequence TO testuser;


CREATE OR REPLACE FUNCTION dataset_0.is_numeric(text) RETURNS boolean  LANGUAGE plpgsql  IMMUTABLE STRICT AS $function$ DECLARE x NUMERIC; BEGIN x = $1::NUMERIC; RETURN TRUE; EXCEPTION WHEN others THEN RETURN FALSE; END; $function$;
CREATE OR REPLACE FUNCTION dataset_0.is_double(text) RETURNS boolean LANGUAGE plpgsql IMMUTABLE STRICT AS $function$ DECLARE x double precision;BEGIN    x = $1::double precision;    RETURN TRUE;EXCEPTION WHEN others THEN    RETURN FALSE;END;$function$;
CREATE OR REPLACE FUNCTION dataset_0.is_date(s character varying) RETURNS boolean LANGUAGE plpgsql AS $function$ begin   perform s::date;  return true; exception when others then  return false; end; $function$;

ALTER FUNCTION dataset_0.is_numeric OWNER TO testuser;
GRANT EXECUTE ON FUNCTION dataset_0.is_numeric(text) TO testuser;
ALTER FUNCTION dataset_0.is_double OWNER TO testuser;
GRANT EXECUTE ON FUNCTION dataset_0.is_double(text) TO testuser;
ALTER FUNCTION dataset_0.is_date OWNER TO testuser;
GRANT EXECUTE ON FUNCTION dataset_0.is_date(varchar) TO testuser;


-- Initializing metabase structure historified as flyway will do

-- Flyway V1

--TABLES

CREATE  TABLE IF NOT EXISTS public.dataflow (
	id bigserial NOT NULL,
	description varchar(255) NULL,
	"name" varchar(255) NULL,
	creation_date timestamp null,
	DEADLINE_DATE timestamp null,
	status VARCHAR null,
	CONSTRAINT dataflow_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.dataset (
	id bigserial NOT NULL,
	date_creation timestamp NULL,
	DATASET_NAME varchar(255) NULL,
	dataflowid int8 NULL,
	status varchar(255) NULL,
	url_connection varchar(255) NULL,
	visibility varchar(255) NULL,
	dataset_schema varchar(255) NULL,
	data_provider_id int8 NULL,
	CONSTRAINT dataset_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.contributor (
	id bigserial NOT NULL,
	email varchar(255) NULL,
	user_id varchar(255) NULL,
	dataflow_id serial NOT NULL,
	CONSTRAINT contributor_pkey PRIMARY KEY (id),
	CONSTRAINT dataflow_contributor_fkey FOREIGN KEY (dataflow_id) REFERENCES dataflow(id)
);

CREATE TABLE IF NOT EXISTS public.data_collection (
	due_date timestamp NULL,
	id bigserial NOT NULL,
	CONSTRAINT data_collection_pkey PRIMARY KEY (id),
	CONSTRAINT dataset_data_collection_fkey FOREIGN KEY (id) REFERENCES dataset(id)
);

CREATE TABLE IF NOT EXISTS public.design_dataset (
	"type" varchar(255) NULL,
	id serial NOT NULL,
	CONSTRAINT design_dataset_pkey PRIMARY KEY (id),
	CONSTRAINT dataset_design_fkey FOREIGN KEY (id) REFERENCES dataset(id)
);

CREATE TABLE IF NOT EXISTS public."document" (
	id bigserial NOT NULL,
	"language" varchar(255) NULL,
	"name" varchar(255) NULL,
	description varchar(255) NULL,
	dataflow_id serial NOT NULL,
	size int8 NULL,
	date timestamp NULL,
	is_public bool NULL,
	CONSTRAINT document_pkey PRIMARY KEY (id),
	CONSTRAINT document_dataflow_fkey FOREIGN KEY (dataflow_id) REFERENCES dataflow(id)
);

CREATE TABLE IF NOT EXISTS public.eu_dataset (
	"name" varchar(255) NULL,
	visible bool NULL,
	id bigserial NOT NULL,
	CONSTRAINT eu_dataset_pkey PRIMARY KEY (id),
	CONSTRAINT eu_dataset_dataset_fkey FOREIGN KEY (id) REFERENCES dataset(id)
);

CREATE TABLE IF NOT EXISTS public.partition_dataset (
	id bigserial NOT NULL,
	user_name varchar(255) NULL,
	id_dataset serial NOT NULL,
	CONSTRAINT partition_dataset_pkey PRIMARY KEY (id),
	CONSTRAINT partition_dataset_dataset_fkey FOREIGN KEY (id_dataset) REFERENCES dataset(id)
);

CREATE TABLE IF NOT EXISTS public.reporting_dataset (
	id bigserial NOT NULL,
	CONSTRAINT reporting_dataset_pkey PRIMARY KEY (id),
	CONSTRAINT reporting_dataset_dataset_fkey FOREIGN KEY (id) REFERENCES dataset(id)
);

CREATE TABLE IF NOT EXISTS public."snapshot" (
	datacollection_id int8 NULL,
	"description" varchar(255) NULL,
	REPORTING_DATASET_ID int8 null,
	id bigserial NOT NULL,
	"release" bool NULL DEFAULT false,
	CONSTRAINT snapshot_pkey PRIMARY KEY (id),
	CONSTRAINT snapshot_data_collection_fkey FOREIGN KEY (datacollection_id) REFERENCES data_collection(id),
	CONSTRAINT snapshot_dataset_fkey FOREIGN KEY (id) REFERENCES dataset(id)
);

CREATE TABLE IF NOT EXISTS public.submission_agreement (
	id bigserial NOT NULL,
	description varchar(255) NULL,
	"name" varchar(255) NULL,
	dataflow_id serial NOT NULL,
	CONSTRAINT submission_agreement_pkey PRIMARY KEY (id),
	CONSTRAINT submission_agreement_dataflow_fkey FOREIGN KEY (dataflow_id) REFERENCES dataflow(id)
);



CREATE TABLE IF NOT EXISTS public.weblink (
	id bigserial NOT NULL,
	description varchar(255) NULL,
	url varchar(255) NULL,
	dataflow_id serial NOT NULL,
	CONSTRAINT weblink_pkey PRIMARY KEY (id),
	CONSTRAINT weblink_dataflow_fkey FOREIGN KEY (dataflow_id) REFERENCES dataflow(id)
);

CREATE TABLE IF NOT EXISTS public.USER_REQUEST (
	id bigserial NOT NULL,
	USER_REQUESTER varchar(255) NULL,
	USER_REQUESTED varchar(255) NULL,
	REQUEST_TYPE VARCHAR NULL,
	CONSTRAINT USER_REQUEST_PKEY PRIMARY KEY (id)
	
);

CREATE TABLE IF NOT EXISTS public.dataflow_user_request (
	dataflow_id bigserial NOT NULL,
	user_request_id bigserial NOT NULL,
	CONSTRAINT dataflow_user_request_pkey PRIMARY KEY (dataflow_id, user_request_id),
	CONSTRAINT user_request_pkey FOREIGN KEY (user_request_id) REFERENCES user_request(id),
	CONSTRAINT user_request_DATAFLOW_pkey FOREIGN KEY (dataflow_id) REFERENCES dataflow(id)
);

CREATE TABLE IF NOT EXISTS public.lock (
	id int4 NOT NULL,
	create_date timestamp NULL,
	created_by varchar NULL,
	lock_type int4 NULL,
	lock_criteria bytea NULL,
	CONSTRAINT lock_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public."snapshot_schema" (
	id bigserial NOT null,
	"description" varchar(255) NULL,
	DESIGN_DATASET_ID int8 null,
	CONSTRAINT snapshot_schema_pkey PRIMARY KEY (id),
	CONSTRAINT snapshot_schema_dataset_fkey FOREIGN KEY (id) REFERENCES dataset(id)
);

CREATE TABLE IF NOT EXISTS public."statistics" (
	id bigserial NOT NULL,
	id_dataset int8 NULL,
	id_table_schema text NULL,
	stat_name text NULL,
	value text NULL,
	CONSTRAINT statistics_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.data_provider (
	id int8 NOT NULL,
	"label" varchar(255) NULL,
	"type" varchar(255) NULL,
	code varchar NULL,
	group_id int8 NULL,
	CONSTRAINT representative_pk PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.representative (
	id int8 NOT NULL,
	data_provider_id int8 NULL,
	dataflow_id int8 NULL,
	user_id varchar(255) NULL,
	user_mail varchar(255) NULL,
	CONSTRAINT data_provider_pk FOREIGN KEY (data_provider_id) REFERENCES data_provider(id),
	CONSTRAINT dataflow_id FOREIGN KEY (dataflow_id) REFERENCES dataflow(id)
);

CREATE TABLE IF NOT EXISTS public.codelist_category (
	id bigserial NOT NULL,
	description varchar(255) NULL,
	short_code varchar(255) NULL,
	CONSTRAINT codelist_category_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.codelist (
	id bigserial NOT NULL,
	description varchar(255) NULL,
	"name" varchar(255) NULL,
	status int4 NULL,
	"version" varchar(255) NULL,
	id_category int8 NOT NULL,
	CONSTRAINT codelist_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.codelist_item (
	id bigserial NOT NULL,
	definition varchar(255) NULL,
	"label" varchar(255) NULL,
	short_code varchar(255) NULL,
	id_codelist int8 NOT NULL,
	CONSTRAINT codelist_item_pkey PRIMARY KEY (id),
	CONSTRAINT codelist_fk FOREIGN KEY (id_codelist) REFERENCES codelist(id)
);

--GRANTS

GRANT USAGE ON SCHEMA public TO testuser ;
GRANT USAGE ON SCHEMA public TO testuser ;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO testuser ;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO testuser ;
GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA public TO testuser ;

ALTER TABLE public.contributor OWNER TO testuser;
GRANT ALL ON TABLE public.contributor TO testuser;
ALTER TABLE public.data_collection OWNER TO testuser;
GRANT ALL ON TABLE public.data_collection TO testuser;
ALTER TABLE public.dataflow OWNER TO testuser;
GRANT ALL ON TABLE public.dataflow TO testuser;
ALTER TABLE public.dataset OWNER TO testuser;
GRANT ALL ON TABLE public.dataset TO testuser;
ALTER TABLE public.design_dataset OWNER TO testuser;
GRANT ALL ON TABLE public.design_dataset TO testuser;
ALTER TABLE public."document" OWNER TO testuser;
GRANT ALL ON TABLE public."document" TO testuser;
ALTER TABLE public.eu_dataset OWNER TO testuser;
GRANT ALL ON TABLE public.eu_dataset TO testuser;
ALTER TABLE public.partition_dataset OWNER TO testuser;
GRANT ALL ON TABLE public.partition_dataset TO testuser;
ALTER TABLE public.reporting_dataset OWNER TO testuser;
GRANT ALL ON TABLE public.reporting_dataset TO testuser;
ALTER TABLE public."snapshot" OWNER TO testuser;
GRANT ALL ON TABLE public."snapshot" TO testuser;
ALTER TABLE public.submission_agreement OWNER TO testuser;
GRANT ALL ON TABLE public.submission_agreement TO testuser;
ALTER TABLE public.weblink OWNER TO testuser;
GRANT ALL ON TABLE public.weblink TO testuser;
ALTER TABLE public.USER_REQUEST OWNER TO testuser;
GRANT ALL ON TABLE public.USER_REQUEST TO testuser;
ALTER TABLE public.dataflow_user_request OWNER TO testuser;
GRANT ALL ON TABLE public.dataflow_user_request TO testuser;
ALTER TABLE public.lock OWNER TO testuser;
GRANT ALL ON TABLE public.lock TO testuser;
ALTER TABLE public.snapshot_schema OWNER TO testuser;
GRANT ALL ON TABLE public.snapshot_schema TO testuser;
ALTER TABLE public.statistics OWNER TO testuser;
GRANT ALL ON TABLE public.statistics TO testuser;
ALTER TABLE public.representative OWNER TO testuser;
GRANT ALL ON TABLE public.representative TO testuser;
ALTER TABLE public.data_provider OWNER TO testuser;
GRANT ALL ON TABLE public.data_provider TO testuser;
ALTER TABLE public.codelist OWNER TO testuser;
GRANT ALL ON TABLE public.codelist TO testuser;
ALTER TABLE public.codelist_category OWNER TO testuser;
GRANT ALL ON TABLE public.codelist_category TO testuser;
ALTER TABLE public.codelist_item OWNER TO testuser;
GRANT ALL ON TABLE public.codelist_item TO testuser;

--INDEXES--
CREATE INDEX IF NOT EXISTS INDX_ISRELEASED ON SNAPSHOT (release);
CREATE INDEX IF NOT EXISTS INDX_REPORTING_DS_ID ON SNAPSHOT (reporting_dataset_id);

CREATE INDEX IF NOT EXISTS statistics_id_dataset_idx ON public.statistics (id_dataset);

-- Flyway V2
ALTER TABLE public.codelist ALTER COLUMN id_category DROP NOT NULL;

-- Flyway V3
ALTER TABLE public.representative ADD COLUMN IF NOT EXISTS receipt_downloaded bool NOT NULL DEFAULT false;
ALTER TABLE public.representative ADD COLUMN IF NOT EXISTS receipt_outdated bool NOT NULL DEFAULT false;

-- Flyway V4
ALTER TABLE public."snapshot" ADD COLUMN IF NOT EXISTS "blocked" bool NULL;
ALTER TABLE public."snapshot" ADD COLUMN IF NOT EXISTS "date_released" timestamp NULL;

-- Flyway V5
ALTER TABLE public.data_collection DROP CONSTRAINT dataset_data_collection_fkey;
ALTER TABLE public.data_collection ADD CONSTRAINT dataset_data_collection_fkey FOREIGN KEY (id) REFERENCES dataset(id) ON DELETE CASCADE;

ALTER TABLE public.partition_dataset DROP CONSTRAINT partition_dataset_dataset_fkey;
ALTER TABLE public.partition_dataset ADD CONSTRAINT partition_dataset_dataset_fkey FOREIGN KEY (id_dataset) REFERENCES dataset(id) ON DELETE CASCADE;

ALTER TABLE public.reporting_dataset DROP CONSTRAINT reporting_dataset_dataset_fkey;
ALTER TABLE public.reporting_dataset ADD CONSTRAINT reporting_dataset_dataset_fkey FOREIGN KEY (id) REFERENCES dataset(id) ON DELETE CASCADE;

ALTER TABLE public.design_dataset DROP CONSTRAINT dataset_design_fkey;
ALTER TABLE public.design_dataset ADD CONSTRAINT dataset_design_fkey FOREIGN KEY (id) REFERENCES dataset(id) ON DELETE CASCADE;

-- Flyway V6
CREATE TABLE IF NOT EXISTS public.FOREIGN_RELATIONS (
	ID bigserial not null, 
	ID_PK varchar(255), 
	DATASET_ID_DESTINATION bigint, 
	DATASET_ID_ORIGIN bigint, 
	id_fk_origin varchar NULL,
	CONSTRAINT foreign_relations_pkey PRIMARY KEY (id),
	CONSTRAINT foreign_relations_destination_fkey FOREIGN KEY (dataset_id_destination) REFERENCES dataset(id) ON DELETE CASCADE,
	CONSTRAINT foreign_relations_origin_fkey FOREIGN KEY (dataset_id_origin) REFERENCES dataset(id) ON DELETE CASCADE
);

-- Flyway V7
DROP TABLE IF EXISTS public.CODELIST_ITEM;
DROP TABLE IF EXISTS public.CODELIST;
DROP TABLE IF EXISTS public.CODELIST_CATEGORY;

-- Flyway V8
ALTER TABLE public.representative ADD COLUMN IF NOT EXISTS has_datasets bool NOT NULL DEFAULT true;

-- Flyway V9
ALTER TABLE public."document" DROP CONSTRAINT IF EXISTS document_dataflow_fkey;
ALTER TABLE public."document" ADD CONSTRAINT document_dataflow_fkey FOREIGN KEY (dataflow_id) REFERENCES dataflow(id) ON DELETE CASCADE;

ALTER TABLE public.contributor DROP CONSTRAINT IF EXISTS dataflow_contributor_fkey;
ALTER TABLE public.contributor ADD CONSTRAINT dataflow_contributor_fkey FOREIGN KEY (dataflow_id) REFERENCES dataflow(id) ON DELETE CASCADE;

ALTER TABLE public.weblink DROP CONSTRAINT IF EXISTS weblink_dataflow_fkey;
ALTER TABLE public.weblink ADD CONSTRAINT weblink_dataflow_fkey FOREIGN KEY (dataflow_id) REFERENCES dataflow(id) ON DELETE CASCADE;

ALTER TABLE public.representative DROP CONSTRAINT IF EXISTS  dataflow_id;
ALTER TABLE public.representative DROP CONSTRAINT IF EXISTS  dataflow_fk;
ALTER TABLE public.representative ADD CONSTRAINT dataflow_fk FOREIGN KEY (dataflow_id) REFERENCES dataflow(id) ON DELETE CASCADE;

ALTER TABLE public.dataflow_user_request DROP CONSTRAINT IF EXISTS user_request_dataflow_pkey;
ALTER TABLE public.dataflow_user_request ADD CONSTRAINT user_request_dataflow_pkey FOREIGN KEY (dataflow_id) REFERENCES dataflow(id) ON DELETE CASCADE;

-- Flyway V10
ALTER TABLE public.dataflow ADD COLUMN IF NOT EXISTS obligation_id int4 NULL;

-- Flyway V11
ALTER TABLE public.partition_dataset DROP CONSTRAINT IF EXISTS partition_dataset_dataset_fkey;
ALTER TABLE public.partition_dataset ADD CONSTRAINT partition_dataset_dataset_fkey FOREIGN KEY (id_dataset) REFERENCES dataset(id) ON DELETE CASCADE;

ALTER TABLE public.design_dataset DROP CONSTRAINT IF EXISTS dataset_design_fkey;
ALTER TABLE public.design_dataset ADD CONSTRAINT dataset_design_fkey FOREIGN KEY (id) REFERENCES dataset(id) ON DELETE CASCADE;

-- Flyway V12
create sequence if not exists representative_id_seq INCREMENT BY 1
	MINVALUE 1
	MAXVALUE 2147483647
	CACHE 1
	NO CYCLE;

-- Flyway V13
ALTER TABLE public.data_provider DROP CONSTRAINT IF EXISTS unique_data_provider;
ALTER TABLE public.data_provider ADD CONSTRAINT unique_data_provider UNIQUE ("type",code);
