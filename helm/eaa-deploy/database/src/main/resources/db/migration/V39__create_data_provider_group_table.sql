CREATE TABLE public.data_provider_group (
	id int8 NOT NULL,
	"name" varchar(255) NULL,
	"type" varchar(255) NULL,
	CONSTRAINT data_provider_gp_pk PRIMARY KEY (id)
) WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = ‘data_provider_group’)\gexec;

GRANT ALL ON TABLE public.data_provider_group TO testuser, dataflow, dataset, validation, recordstore;
