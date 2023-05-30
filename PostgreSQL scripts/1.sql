-- Table: public.algoriphms

-- DROP TABLE IF EXISTS public.algoriphms;

CREATE TABLE IF NOT EXISTS public.algoriphms
(
    "Id" oid NOT NULL,
    "Alg" oid NOT NULL,
    "Definition" text COLLATE pg_catalog."default" NOT NULL,
    "Description" text COLLATE pg_catalog."default" NOT NULL,
    "Example" text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT algoriphms_pkey PRIMARY KEY ("Id"),
    CONSTRAINT "algoriphms_Alg_fkey" FOREIGN KEY ("Alg")
        REFERENCES public.spr_alg_struc ("Id") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.algoriphms
    OWNER to postgres;