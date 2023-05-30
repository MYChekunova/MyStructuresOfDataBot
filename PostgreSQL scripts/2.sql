-- Table: public.spr_alg_struc

-- DROP TABLE IF EXISTS public.spr_alg_struc;

CREATE TABLE IF NOT EXISTS public.spr_alg_struc
(
    "Id" oid NOT NULL,
    "AorS" character varying(30) COLLATE pg_catalog."default" NOT NULL,
    "isAlg" boolean NOT NULL,
    CONSTRAINT spr_alg_struc_pkey PRIMARY KEY ("Id")
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.spr_alg_struc
    OWNER to postgres;