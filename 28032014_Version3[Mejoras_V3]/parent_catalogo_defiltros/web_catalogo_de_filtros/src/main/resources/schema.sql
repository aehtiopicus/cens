-- Schema: public
-- CREATE SCHEMA IF NOT EXISTS public AUTHORIZATION catalogodefiltros;


GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO public;


--- Agregar el resultado del pg_dump debajo de esta línea: 


-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--


CREATE OR REPLACE PROCEDURAL LANGUAGE plpgsql;

ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO catalogodefiltros;

-- ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO catalogodefiltros;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: authorities; Type: TABLE; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

DROP TABLE IF EXISTS filtro CASCADE;
DROP TABLE IF EXISTS filtro_filtro CASCADE;
DROP TABLE IF EXISTS filtro_vehiculo CASCADE;
DROP TABLE IF EXISTS filtroparaqueriesoptimizadas CASCADE;
DROP TABLE IF EXISTS filtroparaqueriesoptimizadas_filtro CASCADE;
DROP TABLE IF EXISTS generic_generator CASCADE;
DROP TABLE IF EXISTS marca_vehiculo CASCADE;
DROP TABLE IF EXISTS modelo_vehiculo CASCADE;
DROP TABLE IF EXISTS tipo_vehiculo CASCADE;
DROP TABLE IF EXISTS vehiculo CASCADE;
DROP TABLE IF EXISTS vehiculo_filtro CASCADE;

DROP TABLE IF EXISTS lista CASCADE;
DROP TABLE IF EXISTS cliente_lista CASCADE;
DROP TABLE IF EXISTS precio_base CASCADE;



-- Marcas (tipos) priorizadas
-- Table: marca_filtro_prioridad

CREATE TABLE IF NOT EXISTS marca_filtro_prioridad
(
  id bigint NOT NULL,
  cod_marca character varying(255) NOT NULL,
  nombre_marca character varying(255) NOT NULL,
  prioridad integer DEFAULT 0 NOT NULL,
  CONSTRAINT marca_filtro_prioridad_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

ALTER TABLE marca_filtro_prioridad OWNER TO catalogodefiltros;
-- Precios 
-- Table: precio_venta_configuracion



CREATE TABLE IF NOT EXISTS precio_venta_configuracion
(
  id bigint NOT NULL,
  ajuste boolean NOT NULL,
  porcentaje double precision NOT NULL,
  CONSTRAINT precio_venta_configuracion_pkey PRIMARY KEY (id)
);

ALTER TABLE precio_venta_configuracion OWNER TO catalogodefiltros;


-- Table: lista

-- DROP TABLE lista;

CREATE TABLE lista
(
  id bigint NOT NULL,
  nombrelista character varying(255),
  porcentaje character varying(255),
  CONSTRAINT lista_pkey PRIMARY KEY (id)
);
ALTER TABLE lista OWNER TO catalogodefiltros;

-- Index: lista_id_idx

-- DROP INDEX lista_id_idx;

CREATE INDEX lista_id_idx
  ON lista
  USING btree
  (id);

-- Index: lista_nombrelista_idx

-- DROP INDEX lista_nombrelista_idx;

CREATE INDEX lista_nombrelista_idx
  ON lista
  USING btree
  (nombrelista);

-- Index: lista_porcentaje_idx

-- DROP INDEX lista_porcentaje_idx;

CREATE INDEX lista_porcentaje_idx
  ON lista
  USING btree
  (porcentaje);

DROP TABLE IF EXISTS vendedor CASCADE;

CREATE TABLE vendedor
(
  id bigint NOT NULL,
  codigo_vendedor character varying(255),
  nombre_vendedor character varying(255),
  CONSTRAINT vendedor_pkey PRIMARY KEY (id),
  CONSTRAINT vendedor_codigo_vendedor_key UNIQUE (codigo_vendedor)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE vendedor
  OWNER TO catalogodefiltros;

CREATE TABLE cliente_lista
(
  id bigint NOT NULL,
  codigo_cliente character varying(255),
  lista_id bigint,
 vendedor_codigo_vendedor character varying(255),
  CONSTRAINT cliente_lista_pkey PRIMARY KEY (id),
  CONSTRAINT fk3ce968fe5e979f4b FOREIGN KEY (lista_id) REFERENCES lista (id),
  CONSTRAINT fk3ce968feb7d4d34b FOREIGN KEY (vendedor_codigo_vendedor)
      REFERENCES vendedor (codigo_vendedor) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION 
)
--ALTER TABLE ONLY filtro_filtro
   -- ADD CONSTRAINT fk1868c41f469b463d FOREIGN KEY (reemplazos_id) REFERENCES filtro(id);
;
ALTER TABLE cliente_lista OWNER TO catalogodefiltros;

-- Index: cliente_lista_codigo_cliente_idx

-- DROP INDEX cliente_lista_codigo_cliente_idx;

CREATE INDEX cliente_lista_codigo_cliente_idx
  ON cliente_lista
  USING btree
  (codigo_cliente);

-- Index: cliente_lista_id_idx

-- DROP INDEX cliente_lista_id_idx;

CREATE INDEX cliente_lista_id_idx
  ON cliente_lista
  USING btree
  (id);




-- Table: precio_base

-- DROP TABLE precio_base;

CREATE TABLE precio_base
(
  id bigint NOT NULL,
  precio character varying(255),
  CONSTRAINT precio_base_pkey PRIMARY KEY (id)
);
ALTER TABLE precio_base OWNER TO catalogodefiltros;

-- Index: precio_base_id_idx

-- DROP INDEX precio_base_id_idx;

CREATE INDEX precio_base_id_idx
  ON precio_base
  USING btree
  (id);

-- Index: precio_base_precio_idx

-- DROP INDEX precio_base_precio_idx;

CREATE INDEX precio_base_precio_idx
  ON precio_base
  USING btree
  (precio);

--
-- Name: filtro; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE filtro (
    id bigint NOT NULL,
    alturafiltro character varying(255),
    anchofiltro character varying(255),
    codigocorto character varying(255),
    codigocortolimpio character varying(255),
    codigodebarra character varying(255),
    codigolargo character varying(255),
    codigolargolimpio character varying(255),
    descripcion character varying(255),
    empresa character varying(255),
    foto character varying(255),
    largofiltro character varying(255),
    marca character varying(255),
    medidas character varying(255),
    original boolean,
    propio boolean,
    roscafiltro character varying(255),
    prioridadmarca integer DEFAULT 0 NOT NULL,
    roscasensorfiltro character varying(255),
    subtipo character varying(255),
    tipo character varying(255),
	preciobase_id bigint,
 	CONSTRAINT fkb408cd08355d98a9 FOREIGN KEY (preciobase_id) REFERENCES precio_base (id) 
);


ALTER TABLE public.filtro OWNER TO catalogodefiltros;

--
-- Name: filtro_filtro; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE filtro_filtro (
    filtro_id bigint NOT NULL,
    reemplazos_id bigint NOT NULL
);


ALTER TABLE public.filtro_filtro OWNER TO catalogodefiltros;

--
-- Name: filtro_vehiculo; Type: TABLE; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE TABLE filtro_vehiculo (
    filtro_id bigint NOT NULL,
    aplicaciones_id bigint NOT NULL
);


ALTER TABLE public.filtro_vehiculo OWNER TO catalogodefiltros;

--
-- Name: filtroparaqueriesoptimizadas; Type: TABLE; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE TABLE filtroparaqueriesoptimizadas (
    id bigint NOT NULL,
    alturafiltro character varying(255),
    anchofiltro character varying(255),
    codigocorto character varying(255),
    codigocortolimpio character varying(255),
    codigodebarra character varying(255),
    codigolargo character varying(255),
    codigolargolimpio character varying(255),
    descripcion character varying(255),
    empresa character varying(255),
    foto character varying(255),
    id_filtro bigint,
    largofiltro character varying(255),
    marca character varying(255),
    marcaaplicacion character varying(255),
    medidas character varying(255),
    prioridadmarca integer DEFAULT 0 NOT NULL,
    modeloaplicacion character varying(255),
    original boolean,
    propio boolean,
    roscafiltro character varying(255),
    roscasensorfiltro character varying(255),
    subtipo character varying(255),
    tipo character varying(255),
    tipoaplicacion character varying(255),
  	precio character varying(255)
);


ALTER TABLE public.filtroparaqueriesoptimizadas OWNER TO catalogodefiltros;

--
-- Name: filtroparaqueriesoptimizadas_filtro; Type: TABLE; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE TABLE filtroparaqueriesoptimizadas_filtro (
    filtro_id bigint NOT NULL,
    reemplazos_id bigint NOT NULL
);


ALTER TABLE public.filtroparaqueriesoptimizadas_filtro OWNER TO catalogodefiltros;

--
-- Name: generic_generator; Type: TABLE; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE TABLE generic_generator (
    sequence_name character varying(255),
    sequence_value integer
);


ALTER TABLE public.generic_generator OWNER TO catalogodefiltros;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: catalogodefiltros
--

DROP SEQUENCE IF EXISTS hibernate_sequence;
 
CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO catalogodefiltros;

--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: catalogodefiltros
--

--SELECT pg_catalog.setval('hibernate_sequence', 1, false);


--
-- Name: marca_vehiculo; Type: TABLE; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE TABLE marca_vehiculo (
    id bigint NOT NULL,
    nombre character varying(255)
);


ALTER TABLE public.marca_vehiculo OWNER TO catalogodefiltros;

--
-- Name: modelo_vehiculo; Type: TABLE; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE TABLE modelo_vehiculo (
    id bigint NOT NULL,
    nombre character varying(255)
);


ALTER TABLE public.modelo_vehiculo OWNER TO catalogodefiltros;

--
-- Name: tipo_vehiculo; Type: TABLE; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE TABLE tipo_vehiculo (
    id bigint NOT NULL,
    nombre character varying(255)
);


ALTER TABLE public.tipo_vehiculo OWNER TO catalogodefiltros;

--
-- Name: users; Type: TABLE; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE TABLE IF NOT EXISTS users
(
  username character varying(25) NOT NULL,
  email character varying(400) NOT NULL,
  enabled boolean,
  fechadecaducidaddelicencia bigint,
  fechadeiniciodelicencia bigint,
  udate_time_stamp bigint DEFAULT 0,
  idequipo character varying(255), 
  codigo_de_usuario character varying(255),
  "password" character varying(255),
  vendedor boolean,
  CONSTRAINT users_pkey PRIMARY KEY (username)
);

CREATE TABLE IF NOT EXISTS authorities
(
  id bigint NOT NULL,
  authority character varying(255) NOT NULL,
  username character varying(25),
  CONSTRAINT authorities_pkey PRIMARY KEY (id),
  CONSTRAINT fk8e78bb01ff09974f FOREIGN KEY (username)
      REFERENCES users (username) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


ALTER TABLE public.users OWNER TO catalogodefiltros;

--
-- Name: vehiculo; Type: TABLE; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE TABLE vehiculo (
    id bigint NOT NULL,
    image character varying(255),
    marca bigint,
    modelo bigint,
    tipovehiculo bigint
);


ALTER TABLE public.vehiculo OWNER TO catalogodefiltros;

--
-- Name: vehiculo_filtro; Type: TABLE; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE TABLE vehiculo_filtro (
    vehiculo_id bigint NOT NULL,
    repuestos_id bigint NOT NULL
);


ALTER TABLE public.vehiculo_filtro OWNER TO catalogodefiltros;

--
-- Data for Name: authorities; Type: TABLE DATA; Schema: public; Owner: catalogodefiltros
--

-- COPY authorities (id, authority, username) FROM stdin;



--
-- Data for Name: filtro; Type: TABLE DATA; Schema: public; Owner: catalogodefiltros
--

-- COPY filtro (id, alturafiltro, anchofiltro, codigocorto, codigocortolimpio, codigodebarra, codigolargo, codigolargolimpio, descripcion, empresa, foto, largofiltro, marca, medidas, original, propio, roscafiltro, roscasensorfiltro, subtipo, tipo) FROM stdin;



--
-- Data for Name: filtro_filtro; Type: TABLE DATA; Schema: public; Owner: catalogodefiltros
--

-- COPY filtro_filtro (filtro_id, reemplazos_id) FROM stdin;



--
-- Data for Name: filtro_vehiculo; Type: TABLE DATA; Schema: public; Owner: catalogodefiltros
--

-- COPY filtro_vehiculo (filtro_id, aplicaciones_id) FROM stdin;



--
-- Data for Name: filtroparaqueriesoptimizadas; Type: TABLE DATA; Schema: public; Owner: catalogodefiltros
--

-- COPY filtroparaqueriesoptimizadas (id, alturafiltro, anchofiltro, codigocorto, codigocortolimpio, codigodebarra, codigolargo, codigolargolimpio, descripcion, empresa, foto, id_filtro, largofiltro, marca, marcaaplicacion, medidas, modeloaplicacion, original, propio, roscafiltro, roscasensorfiltro, subtipo, tipo, tipoaplicacion) FROM stdin;



--
-- Data for Name: filtroparaqueriesoptimizadas_filtro; Type: TABLE DATA; Schema: public; Owner: catalogodefiltros
--

-- COPY filtroparaqueriesoptimizadas_filtro (filtro_id, reemplazos_id) FROM stdin;



--
-- Data for Name: generic_generator; Type: TABLE DATA; Schema: public; Owner: catalogodefiltros
--

-- COPY generic_generator (sequence_name, sequence_value) FROM stdin;



--
-- Data for Name: marca_vehiculo; Type: TABLE DATA; Schema: public; Owner: catalogodefiltros
--

-- COPY marca_vehiculo (id, nombre) FROM stdin;



--
-- Data for Name: modelo_vehiculo; Type: TABLE DATA; Schema: public; Owner: catalogodefiltros
--

-- COPY modelo_vehiculo (id, nombre) FROM stdin;



--
-- Data for Name: tipo_vehiculo; Type: TABLE DATA; Schema: public; Owner: catalogodefiltros
--

-- COPY tipo_vehiculo (id, nombre) FROM stdin;



--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: catalogodefiltros
--

-- COPY users (username, email, enabled, password) FROM stdin;



--
-- Data for Name: vehiculo; Type: TABLE DATA; Schema: public; Owner: catalogodefiltros
--

-- COPY vehiculo (id, image, marca, modelo, tipovehiculo) FROM stdin;



--
-- Data for Name: vehiculo_filtro; Type: TABLE DATA; Schema: public; Owner: catalogodefiltros
--

-- COPY vehiculo_filtro (vehiculo_id, repuestos_id) FROM stdin;



--
-- Name: authorities_pkey; Type: CONSTRAINT; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

--ALTER TABLE ONLY authorities
--    ADD CONSTRAINT authorities_pkey PRIMARY KEY (id);


--
-- Name: filtro_pkey; Type: CONSTRAINT; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

ALTER TABLE ONLY filtro
    ADD CONSTRAINT filtro_pkey PRIMARY KEY (id);


--
-- Name: filtroparaqueriesoptimizadas_filtro_reemplazos_id_key; Type: CONSTRAINT; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

ALTER TABLE ONLY filtroparaqueriesoptimizadas_filtro
    ADD CONSTRAINT filtroparaqueriesoptimizadas_filtro_reemplazos_id_key UNIQUE (reemplazos_id);


--
-- Name: filtroparaqueriesoptimizadas_pkey; Type: CONSTRAINT; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

ALTER TABLE ONLY filtroparaqueriesoptimizadas
    ADD CONSTRAINT filtroparaqueriesoptimizadas_pkey PRIMARY KEY (id);


--
-- Name: marca_vehiculo_pkey; Type: CONSTRAINT; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

ALTER TABLE ONLY marca_vehiculo
    ADD CONSTRAINT marca_vehiculo_pkey PRIMARY KEY (id);


--
-- Name: modelo_vehiculo_pkey; Type: CONSTRAINT; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

ALTER TABLE ONLY modelo_vehiculo
    ADD CONSTRAINT modelo_vehiculo_pkey PRIMARY KEY (id);


--
-- Name: tipo_vehiculo_pkey; Type: CONSTRAINT; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

ALTER TABLE ONLY tipo_vehiculo
    ADD CONSTRAINT tipo_vehiculo_pkey PRIMARY KEY (id);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

--ALTER TABLE ONLY users
--    ADD CONSTRAINT users_pkey PRIMARY KEY (username);


--
-- Name: vehiculo_pkey; Type: CONSTRAINT; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

ALTER TABLE ONLY vehiculo
    ADD CONSTRAINT vehiculo_pkey PRIMARY KEY (id);


--
-- Name: filtro_codigocorto_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtro_codigocorto_idx ON filtro USING btree (codigocorto);


--
-- Name: filtro_codigocortolimpio_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtro_codigocortolimpio_idx ON filtro USING btree (codigocortolimpio);


--
-- Name: filtro_codigolargo_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtro_codigolargo_idx ON filtro USING btree (codigolargo);


--
-- Name: filtro_codigolargolimpio_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtro_codigolargolimpio_idx ON filtro USING btree (codigolargolimpio);


--
-- Name: filtro_filtro_filtro_id_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtro_filtro_filtro_id_idx ON filtro_filtro USING hash (filtro_id);


--
-- Name: filtro_filtro_reemplazos_id_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtro_filtro_reemplazos_id_idx ON filtro_filtro USING hash (reemplazos_id);


--
-- Name: filtro_id_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtro_id_idx ON filtro USING btree (id);


--
-- Name: filtro_marca_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtro_marca_idx ON filtro USING btree (marca);


--
-- Name: filtro_propio_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtro_propio_idx ON filtro USING btree (propio);


--
-- Name: filtro_subtipo_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtro_subtipo_idx ON filtro USING btree (subtipo);


--
-- Name: filtro_tipo_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtro_tipo_idx ON filtro USING btree (tipo);


--
-- Name: filtro_vehiculo_aplicaciones_id_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtro_vehiculo_aplicaciones_id_idx ON filtro_vehiculo USING hash (aplicaciones_id);


--
-- Name: filtro_vehiculo_filtro_id_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtro_vehiculo_filtro_id_idx ON filtro_vehiculo USING hash (filtro_id);


--
-- Name: filtroparaqueriesoptimizadas_codigocorto_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtroparaqueriesoptimizadas_codigocorto_idx ON filtroparaqueriesoptimizadas USING btree (codigocorto);


--
-- Name: filtroparaqueriesoptimizadas_codigocortolimpio_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtroparaqueriesoptimizadas_codigocortolimpio_idx ON filtroparaqueriesoptimizadas USING btree (codigocortolimpio);


--
-- Name: filtroparaqueriesoptimizadas_codigodebarra_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtroparaqueriesoptimizadas_codigodebarra_idx ON filtroparaqueriesoptimizadas USING btree (codigodebarra);


--
-- Name: filtroparaqueriesoptimizadas_codigolargo_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtroparaqueriesoptimizadas_codigolargo_idx ON filtroparaqueriesoptimizadas USING btree (codigolargo);


--
-- Name: filtroparaqueriesoptimizadas_codigolargolimpio_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtroparaqueriesoptimizadas_codigolargolimpio_idx ON filtroparaqueriesoptimizadas USING btree (codigolargolimpio);


--
-- Name: filtroparaqueriesoptimizadas_empresa_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtroparaqueriesoptimizadas_empresa_idx ON filtroparaqueriesoptimizadas USING btree (empresa);


--
-- Name: filtroparaqueriesoptimizadas_filtro_filtro_id_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtroparaqueriesoptimizadas_filtro_filtro_id_idx ON filtroparaqueriesoptimizadas_filtro USING hash (filtro_id);


--
-- Name: filtroparaqueriesoptimizadas_filtro_reemplazos_id_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtroparaqueriesoptimizadas_filtro_reemplazos_id_idx ON filtroparaqueriesoptimizadas_filtro USING hash (reemplazos_id);


--
-- Name: filtroparaqueriesoptimizadas_id_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtroparaqueriesoptimizadas_id_idx ON filtroparaqueriesoptimizadas USING btree (id);


--
-- Name: filtroparaqueriesoptimizadas_marca_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtroparaqueriesoptimizadas_marca_idx ON filtroparaqueriesoptimizadas USING btree (marca);


--
-- Name: filtroparaqueriesoptimizadas_marcaaplicacion_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtroparaqueriesoptimizadas_marcaaplicacion_idx ON filtroparaqueriesoptimizadas USING btree (marcaaplicacion);


--
-- Name: filtroparaqueriesoptimizadas_modeloaplicacion_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtroparaqueriesoptimizadas_modeloaplicacion_idx ON filtroparaqueriesoptimizadas USING btree (modeloaplicacion);


--
-- Name: filtroparaqueriesoptimizadas_propio_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtroparaqueriesoptimizadas_propio_idx ON filtroparaqueriesoptimizadas USING btree (propio);


--
-- Name: filtroparaqueriesoptimizadas_subtipo_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtroparaqueriesoptimizadas_subtipo_idx ON filtroparaqueriesoptimizadas USING btree (subtipo);


--
-- Name: filtroparaqueriesoptimizadas_tipo_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtroparaqueriesoptimizadas_tipo_idx ON filtroparaqueriesoptimizadas USING btree (tipo);


--
-- Name: filtroparaqueriesoptimizadas_tipoaplicacion_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX filtroparaqueriesoptimizadas_tipoaplicacion_idx ON filtroparaqueriesoptimizadas USING btree (tipoaplicacion);


--
-- Name: marca_vehiculo_id_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX marca_vehiculo_id_idx ON marca_vehiculo USING btree (id);


--
-- Name: marca_vehiculo_nombre_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX marca_vehiculo_nombre_idx ON marca_vehiculo USING btree (nombre);


--
-- Name: modelo_vehiculo_id_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX modelo_vehiculo_id_idx ON modelo_vehiculo USING btree (id);


--
-- Name: modelo_vehiculo_nombre_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX modelo_vehiculo_nombre_idx ON modelo_vehiculo USING btree (nombre);


--
-- Name: tipo_vehiculo_id_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX tipo_vehiculo_id_idx ON tipo_vehiculo USING btree (id);


--
-- Name: tipo_vehiculo_nombre_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX tipo_vehiculo_nombre_idx ON tipo_vehiculo USING btree (nombre);


--
-- Name: vehiculo_filtro_repuestos_id_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX vehiculo_filtro_repuestos_id_idx ON vehiculo_filtro USING hash (repuestos_id);


--
-- Name: vehiculo_filtro_vehiculo_id_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX vehiculo_filtro_vehiculo_id_idx ON vehiculo_filtro USING hash (vehiculo_id);


--
-- Name: vehiculo_id_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX vehiculo_id_idx ON vehiculo USING btree (id);


--
-- Name: vehiculo_marca_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX vehiculo_marca_idx ON vehiculo USING btree (marca);


--
-- Name: vehiculo_modelo_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX vehiculo_modelo_idx ON vehiculo USING btree (modelo);


--
-- Name: vehiculo_tipovehiculo_idx; Type: INDEX; Schema: public; Owner: catalogodefiltros; Tablespace: 
--

CREATE INDEX vehiculo_tipovehiculo_idx ON vehiculo USING btree (tipovehiculo);


-- Index: filtroparaqueriesoptimizadas_id_idx

DROP INDEX IF EXISTS filtroparaqueriesoptimizadas_id_idx;

CREATE INDEX filtroparaqueriesoptimizadas_id_idx
  ON filtroparaqueriesoptimizadas
  USING hash
  (id);

-- Index: filtroparaqueriesoptimizadas_tipo_idx

DROP INDEX IF EXISTS filtroparaqueriesoptimizadas_tipo_idx;

CREATE INDEX filtroparaqueriesoptimizadas_tipo_idx
  ON filtroparaqueriesoptimizadas
  USING btree
  (tipo);

-- Index: filtroparaqueriesoptimizadas_precio_idx

DROP INDEX IF EXISTS filtroparaqueriesoptimizadas_precio_idx;

CREATE INDEX filtroparaqueriesoptimizadas_precio_idx
  ON filtroparaqueriesoptimizadas
  USING btree
  (precio);


--
-- Name: fk1868c41f469b463d; Type: FK CONSTRAINT; Schema: public; Owner: catalogodefiltros
--

ALTER TABLE ONLY filtro_filtro
    ADD CONSTRAINT fk1868c41f469b463d FOREIGN KEY (reemplazos_id) REFERENCES filtro(id);


--
-- Name: fk1868c41f7f2c8269; Type: FK CONSTRAINT; Schema: public; Owner: catalogodefiltros
--

ALTER TABLE ONLY filtro_filtro
    ADD CONSTRAINT fk1868c41f7f2c8269 FOREIGN KEY (filtro_id) REFERENCES filtro(id);


--
-- Name: fk444d06bc7f2c8269; Type: FK CONSTRAINT; Schema: public; Owner: catalogodefiltros
--

ALTER TABLE ONLY filtro_vehiculo
    ADD CONSTRAINT fk444d06bc7f2c8269 FOREIGN KEY (filtro_id) REFERENCES filtro(id);


--
-- Name: fk444d06bcff4c77f1; Type: FK CONSTRAINT; Schema: public; Owner: catalogodefiltros
--

ALTER TABLE ONLY filtro_vehiculo
    ADD CONSTRAINT fk444d06bcff4c77f1 FOREIGN KEY (aplicaciones_id) REFERENCES vehiculo(id);


--
-- Name: fk780e7965574b056e; Type: FK CONSTRAINT; Schema: public; Owner: catalogodefiltros
--

ALTER TABLE ONLY vehiculo
    ADD CONSTRAINT fk780e7965574b056e FOREIGN KEY (marca) REFERENCES marca_vehiculo(id);


--
-- Name: fk780e79659f9b5b00; Type: FK CONSTRAINT; Schema: public; Owner: catalogodefiltros
--

ALTER TABLE ONLY vehiculo
    ADD CONSTRAINT fk780e79659f9b5b00 FOREIGN KEY (modelo) REFERENCES modelo_vehiculo(id);


--
-- Name: fk780e7965caa663c1; Type: FK CONSTRAINT; Schema: public; Owner: catalogodefiltros
--

ALTER TABLE ONLY vehiculo
    ADD CONSTRAINT fk780e7965caa663c1 FOREIGN KEY (tipovehiculo) REFERENCES tipo_vehiculo(id);


--
-- Name: fk8e78bb01ff09974f; Type: FK CONSTRAINT; Schema: public; Owner: catalogodefiltros
--

-- ALTER TABLE ONLY authorities
--    ADD CONSTRAINT fk8e78bb01ff09974f FOREIGN KEY (username) REFERENCES users(username);


--
-- Name: fkbbb6e522affa9989; Type: FK CONSTRAINT; Schema: public; Owner: catalogodefiltros
--

ALTER TABLE ONLY vehiculo_filtro
    ADD CONSTRAINT fkbbb6e522affa9989 FOREIGN KEY (vehiculo_id) REFERENCES vehiculo(id);


--
-- Name: fkbbb6e522d4521bf; Type: FK CONSTRAINT; Schema: public; Owner: catalogodefiltros
--

ALTER TABLE ONLY vehiculo_filtro
    ADD CONSTRAINT fkbbb6e522d4521bf FOREIGN KEY (repuestos_id) REFERENCES filtro(id);


--
-- Name: fke910fb6824500e20; Type: FK CONSTRAINT; Schema: public; Owner: catalogodefiltros
--

ALTER TABLE ONLY filtroparaqueriesoptimizadas_filtro
    ADD CONSTRAINT fke910fb6824500e20 FOREIGN KEY (filtro_id) REFERENCES filtroparaqueriesoptimizadas(id);


--
-- Name: fke910fb68469b463d; Type: FK CONSTRAINT; Schema: public; Owner: catalogodefiltros
--

ALTER TABLE ONLY filtroparaqueriesoptimizadas_filtro
    ADD CONSTRAINT fke910fb68469b463d FOREIGN KEY (reemplazos_id) REFERENCES filtro(id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

ALTER TABLE filtroparaqueriesoptimizadas CLUSTER ON filtroparaqueriesoptimizadas_codigocortolimpio_idx;
ALTER TABLE filtroparaqueriesoptimizadas CLUSTER ON filtroparaqueriesoptimizadas_codigolargolimpio_idx;

-- Table: referencias

-- DROP TABLE referencias;

CREATE TABLE IF NOT EXISTS referencias
(
  id bigint NOT NULL,
  valor character varying(255),
  CONSTRAINT referencias_pkey PRIMARY KEY (id)
);
ALTER TABLE referencias
  OWNER TO catalogodefiltros;

--
-- Pedidos
--

-- Table: pedido

DROP TABLE IF EXISTS pedido CASCADE;

CREATE TABLE pedido 
(
  id bigint NOT NULL,
  eliminado boolean,
  fecha_inicio timestamp without time zone,
  fecha_fin timestamp without time zone,
  CONSTRAINT pedido_pkey PRIMARY KEY (id)
);
ALTER TABLE pedido OWNER TO catalogodefiltros;


-- Table: pedido_detalle

DROP TABLE IF EXISTS pedido_detalle CASCADE;

CREATE TABLE pedido_detalle
(
  id bigint NOT NULL,
  cantidad integer NOT NULL,
  filtro_id bigint NOT NULL,
  precio character varying(255),
  CONSTRAINT pedido_detalle_pkey PRIMARY KEY (id),
  CONSTRAINT fk9db0a67d7f2c8269 FOREIGN KEY (filtro_id)
      REFERENCES filtro (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
ALTER TABLE pedido_detalle OWNER TO catalogodefiltros;
ALTER TABLE public.authorities OWNER TO catalogodefiltros;

-- Table: pedido_pedido_detalle

DROP TABLE IF EXISTS pedido_pedido_detalle CASCADE;

CREATE TABLE pedido_pedido_detalle
(
  pedido_id bigint NOT NULL,
  detalles_id bigint NOT NULL,
  CONSTRAINT fk22dfeab71115841 FOREIGN KEY (detalles_id)
      REFERENCES pedido_detalle (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk22dfeab76bfd49 FOREIGN KEY (pedido_id)
      REFERENCES pedido (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT pedido_pedido_detalle_detalles_id_key UNIQUE (detalles_id)
);
ALTER TABLE pedido_pedido_detalle OWNER TO catalogodefiltros;

CREATE TABLE IF NOT EXISTS cfs_system_update
(
  id bigint NOT NULL,
  udate_time_stamp bigint,
  update_type character varying(255) NOT NULL,
  user_id character varying(25),
  CONSTRAINT cfs_system_update_pkey PRIMARY KEY (id),
  CONSTRAINT fkc5d48f4a61afc88 FOREIGN KEY (user_id)
      REFERENCES users (username) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE cfs_system_update OWNER TO catalogodefiltros;



