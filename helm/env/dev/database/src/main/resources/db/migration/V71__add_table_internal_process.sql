CREATE TABLE IF NOT EXISTS public.internal_process (
id int8 NOT NULL,
type varchar NOT NULL,
dataflow_id int8 NOT NULL,
data_provider_id int8 NOT NULL,
data_collection_id int8 NOT NULL,
transaction_id varchar NOT NULL,
aggregate_id varchar NOT NULL,
CONSTRAINT internal_process_pk PRIMARY KEY (id)
);
CREATE SEQUENCE IF NOT EXISTS public.internal_process_id_seq
  INCREMENT BY 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1
  NO CYCLE;
GRANT ALL ON TABLE public.internal_process TO testuser,dataflow,dataset,validation,recordstore;
GRANT ALL ON SEQUENCE public.internal_process_id_seq TO testuser,dataflow,dataset,validation,recordstore;