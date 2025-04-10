CREATE TABLE IF NOT EXISTS public.accounts
(
    id uuid NOT NULL,
    aadhaar_number character varying(255) COLLATE pg_catalog."default",
    account_number character varying(255) COLLATE pg_catalog."default",
    account_type character varying(255) COLLATE pg_catalog."default",
    balance numeric(38,2),
    ifsc_code character varying(255) COLLATE pg_catalog."default",
    is_locked boolean NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    pan_number character varying(255) COLLATE pg_catalog."default",
    version integer,
    CONSTRAINT accounts_pkey PRIMARY KEY (id),
    CONSTRAINT uk6kplolsdtr3slnvx97xsy2kc8 UNIQUE (account_number)
)