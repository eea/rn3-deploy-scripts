

CREATE schema dataset_1 AUTHORIZATION testuser;
CREATE TABLE dataset_1.DATASET_VALUE (ID bigint, ID_DATASET_SCHEMA text, PRIMARY key (ID))  TABLESPACE pg_default;
CREATE TABLE dataset_1.TABLE_VALUE (ID bigint, ID_TABLE_SCHEMA text, DATASET_ID bigint, PRIMARY key (ID), FOREIGN KEY (dataset_id) REFERENCES dataset_1.dataset_value(id) ON DELETE CASCADE) TABLESPACE pg_default;
CREATE TABLE dataset_1.RECORD_VALUE(ID text, ID_RECORD_SCHEMA text, ID_TABLE bigint, DATASET_PARTITION_ID bigint, DATA_PROVIDER_CODE text, PRIMARY key (ID), FOREIGN KEY (id_table) REFERENCES dataset_1.table_value(id) ON DELETE CASCADE) TABLESPACE pg_default;
CREATE TABLE dataset_1.FIELD_VALUE (ID text, TYPE text, VALUE text, ID_FIELD_SCHEMA text, ID_RECORD text, PRIMARY key (ID), FOREIGN KEY (id_record) REFERENCES dataset_1.record_value(id) ON DELETE CASCADE) TABLESPACE pg_default;
CREATE TABLE dataset_1.VALIDATION (ID bigint,ID_RULE text,LEVEL_ERROR text, MESSAGE text, TYPE_ENTITY text, VALIDATION_DATE text, ORIGIN_NAME TEXT, PRIMARY KEY (id));
CREATE TABLE dataset_1.DATASET_VALIDATION (ID bigint,ID_DATASET bigint,ID_VALIDATION bigint, PRIMARY KEY (ID), FOREIGN KEY (ID_VALIDATION) REFERENCES dataset_1.VALIDATION(ID) ON DELETE CASCADE,FOREIGN KEY (ID_DATASET) REFERENCES dataset_1.DATASET_VALUE(id) ON DELETE CASCADE) TABLESPACE pg_default;
CREATE TABLE dataset_1.TABLE_VALIDATION (ID bigint,ID_TABLE bigint,ID_VALIDATION bigint, PRIMARY KEY (ID), FOREIGN KEY (ID_VALIDATION) REFERENCES dataset_1.VALIDATION(ID) ON DELETE CASCADE,FOREIGN KEY (ID_TABLE) REFERENCES dataset_1.TABLE_VALUE(id) ON DELETE CASCADE) TABLESPACE pg_default;
CREATE TABLE dataset_1.RECORD_VALIDATION (ID bigint,ID_RECORD text,ID_VALIDATION bigint, PRIMARY KEY (ID), FOREIGN KEY (ID_VALIDATION) REFERENCES dataset_1.VALIDATION(ID) ON DELETE CASCADE,FOREIGN KEY (ID_RECORD) REFERENCES dataset_1.RECORD_VALUE(id) ON DELETE CASCADE) TABLESPACE pg_default;
CREATE TABLE dataset_1.FIELD_VALIDATION (ID bigint,ID_FIELD text,ID_VALIDATION bigint, PRIMARY KEY (ID), FOREIGN KEY (ID_VALIDATION) REFERENCES dataset_1.VALIDATION(ID) ON DELETE CASCADE ,FOREIGN KEY (ID_FIELD) REFERENCES dataset_1.FIELD_VALUE(id) ON DELETE CASCADE) TABLESPACE pg_default;



ALTER TABLE dataset_1.DATASET_VALUE  OWNER to testuser;
ALTER TABLE dataset_1.TABLE_VALUE  OWNER to testuser;
ALTER TABLE dataset_1.RECORD_VALUE  OWNER to testuser;
ALTER TABLE dataset_1.FIELD_VALUE  OWNER to testuser;
ALTER TABLE dataset_1.DATASET_VALIDATION OWNER TO testuser;
ALTER TABLE dataset_1.TABLE_VALIDATION OWNER TO testuser;
ALTER TABLE dataset_1.FIELD_VALIDATION OWNER TO testuser;
ALTER TABLE dataset_1.RECORD_VALIDATION OWNER TO testuser;
ALTER TABLE dataset_1.VALIDATION OWNER TO testuser;


CREATE SEQUENCE dataset_1.field_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE;
CREATE SEQUENCE dataset_1.record_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE;
CREATE SEQUENCE dataset_1.table_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE;
CREATE SEQUENCE dataset_1.field_validation_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE;
CREATE SEQUENCE dataset_1.record_validation_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE;
CREATE SEQUENCE dataset_1.table_validation_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE;
CREATE SEQUENCE dataset_1.dataset_validation_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE;
CREATE SEQUENCE dataset_1.validation_sequence INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE;

	

ALTER SEQUENCE dataset_1.field_sequence OWNER TO testuser;
GRANT ALL ON SEQUENCE dataset_1.field_sequence TO testuser;

ALTER SEQUENCE dataset_1.record_sequence OWNER TO testuser;
GRANT ALL ON SEQUENCE dataset_1.record_sequence TO testuser;

ALTER SEQUENCE dataset_1.table_sequence OWNER TO testuser;
GRANT ALL ON SEQUENCE dataset_1.table_sequence TO testuser;

CREATE INDEX idx_record_value ON dataset_1.field_value(id_record);
CREATE INDEX idx_field_schema ON dataset_1.field_value(id_field_schema);
CREATE INDEX idx_dataset_value ON dataset_1.table_value(dataset_id);
CREATE INDEX idx_table_schema ON dataset_1.table_value(id_table_schema);
CREATE INDEX idx_table_value ON dataset_1.record_value(id_table);
CREATE INDEX idx_record_schema ON dataset_1.record_value(id_record_schema);
CREATE INDEX idx_table_validation_table ON dataset_1.table_validation(id_table);
CREATE INDEX idx_table_validation_validation ON dataset_1.table_validation(id_validation);
CREATE INDEX idx_dataset_validation_dataset ON dataset_1.dataset_validation(id_dataset);
CREATE INDEX idx_dataset_validation_validation ON dataset_1.dataset_validation(id_validation);
CREATE INDEX idx_field_validation_field ON dataset_1.field_validation(id_field);
CREATE INDEX idx_field_validation_validation ON dataset_1.field_validation(id_validation);
CREATE INDEX idx_record_validation_record ON dataset_1.record_validation(id_record);
CREATE INDEX idx_record_validation_validation ON dataset_1.record_validation(id_validation);
CREATE INDEX idx_validation_level_error ON dataset_1.validation(level_error);


ALTER SEQUENCE dataset_1.field_sequence OWNER TO testuser;
GRANT ALL ON SEQUENCE dataset_1.field_validation_sequence TO testuser;

ALTER SEQUENCE dataset_1.record_sequence OWNER TO testuser;
GRANT ALL ON SEQUENCE dataset_1.record_validation_sequence TO testuser;

ALTER SEQUENCE dataset_1.table_sequence OWNER TO testuser;
GRANT ALL ON SEQUENCE dataset_1.table_validation_sequence TO testuser;

ALTER SEQUENCE dataset_1.validation_sequence OWNER TO testuser;
GRANT ALL ON SEQUENCE dataset_1.validation_sequence TO testuser;

ALTER SEQUENCE dataset_1.dataset_validation_sequence OWNER TO testuser;
GRANT ALL ON SEQUENCE dataset_1.dataset_validation_sequence TO testuser;


CREATE OR REPLACE FUNCTION dataset_1.is_numeric(text) RETURNS boolean  LANGUAGE plpgsql  IMMUTABLE STRICT AS $function$ DECLARE x NUMERIC; BEGIN x = $1::NUMERIC; RETURN TRUE; EXCEPTION WHEN others THEN RETURN FALSE; END; $function$;
CREATE OR REPLACE FUNCTION dataset_1.is_double(text) RETURNS boolean LANGUAGE plpgsql IMMUTABLE STRICT AS $function$ DECLARE x double precision;BEGIN    x = $1::double precision;    RETURN TRUE;EXCEPTION WHEN others THEN    RETURN FALSE;END;$function$;
CREATE OR REPLACE FUNCTION dataset_1.is_date(s character varying) RETURNS boolean LANGUAGE plpgsql AS $function$ begin   perform s::date;  return true; exception when others then  return false; end; $function$;

ALTER FUNCTION dataset_1.is_numeric OWNER TO testuser;
GRANT EXECUTE ON FUNCTION dataset_1.is_numeric(text) TO testuser;
ALTER FUNCTION dataset_1.is_double OWNER TO testuser;
GRANT EXECUTE ON FUNCTION dataset_1.is_double(text) TO testuser;
ALTER FUNCTION dataset_1.is_date OWNER TO testuser;
GRANT EXECUTE ON FUNCTION dataset_1.is_date(varchar) TO testuser;


commit;



INSERT INTO dataset_1.dataset_value (id,id_dataset_schema) VALUES 
(1,'5e1df0a9e66db00be4255aa6');

INSERT INTO dataset_1.table_value (id,id_table_schema,dataset_id) VALUES 
(1,'5e1df0f5e66db00be4255aaa',1);

INSERT INTO dataset_1.record_value (id,id_record_schema,id_table,dataset_partition_id,data_provider_code) VALUES 
(-1089303124365,'5e1df0f5e66db00be4255aab',1,1,'ES');

INSERT INTO dataset_1.field_value (id,"type",value,id_field_schema,id_record) VALUES 
(-1089303124365,'TEXT','Iberia','5e1df0f9e66db00be4255aac',-1089303124365);

commit;