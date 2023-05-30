-- Table: public.structures

-- DROP TABLE IF EXISTS public.structures;

CREATE TABLE IF NOT EXISTS public.structures
(
    "Id" oid NOT NULL,
    "Struct" oid NOT NULL,
    "Definition" text COLLATE pg_catalog."default" NOT NULL,
    "Example" text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT structures_pkey PRIMARY KEY ("Id"),
    CONSTRAINT "structures_Struct_fkey" FOREIGN KEY ("Struct")
        REFERENCES public.spr_alg_struc ("Id") MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.structures
    OWNER to postgres;