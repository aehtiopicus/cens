--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: banco; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE banco (
    id bigint NOT NULL,
    nombre character varying(255)
);


ALTER TABLE public.banco OWNER TO postgres;

--
-- Name: beneficio; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE beneficio (
    id bigint NOT NULL,
    descripcion character varying(255),
    remunerativo boolean,
    titulo character varying(255)
);


ALTER TABLE public.beneficio OWNER TO postgres;

--
-- Name: beneficiocliente; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE beneficiocliente (
    id bigint NOT NULL,
    tipo character varying(255),
    valor real,
    vigente boolean,
    beneficio_id bigint,
    cliente_id bigint
);


ALTER TABLE public.beneficiocliente OWNER TO postgres;

--
-- Name: cens_activity_feed; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cens_activity_feed (
    id bigint NOT NULL,
    fecha_creacion timestamp without time zone,
    tipo_de_fuente character varying(255),
    id_creador bigint,
    prefil_creador character varying(255),
    ultima_notificacion timestamp without time zone,
    notificado boolean,
    visto boolean,
    id_dirigido bigint,
    prefil_dirigido character varying(255)
);


ALTER TABLE public.cens_activity_feed OWNER TO postgres;

--
-- Name: cens_alumno; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cens_alumno (
    id bigint NOT NULL,
    alumnotype character varying(255),
    baja boolean,
    dni bigint NOT NULL,
    nrolegajo character varying(255),
    miembrocens_id bigint NOT NULL,
    asignaturas_id bigint
);


ALTER TABLE public.cens_alumno OWNER TO postgres;

--
-- Name: cens_alumno_cens_asignatura; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cens_alumno_cens_asignatura (
    cens_alumno_id bigint NOT NULL,
    asignaturas_id bigint NOT NULL
);


ALTER TABLE public.cens_alumno_cens_asignatura OWNER TO postgres;

--
-- Name: cens_asesor; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cens_asesor (
    id bigint NOT NULL,
    baja boolean,
    miembrocens_id bigint NOT NULL,
    profesor_id bigint
);


ALTER TABLE public.cens_asesor OWNER TO postgres;

--
-- Name: cens_asignatura; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cens_asignatura (
    id bigint NOT NULL,
    horarios character varying(1000),
    modalidad character varying(255),
    nombre character varying(255),
    curso_id bigint,
    profesor_id bigint,
    profesorsuplente_id bigint,
    vigente boolean
);


ALTER TABLE public.cens_asignatura OWNER TO postgres;

--
-- Name: cens_cambio_estado_feed; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cens_cambio_estado_feed (
    id bigint NOT NULL,
    comentariotype character varying(255),
    fecha_creacion date,
    id_creador bigint,
    prefil_creador character varying(255),
    ultima_notificacion date,
    notificado boolean,
    visto boolean,
    id_dirigido bigint,
    prefil_dirigido character varying(255),
    estadorevisiontype character varying(255),
    estadorevisiontypeviejo character varying(255),
    tipoid bigint,
    ignorado boolean
);


ALTER TABLE public.cens_cambio_estado_feed OWNER TO postgres;

--
-- Name: cens_comentario; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cens_comentario (
    id bigint NOT NULL,
    baja boolean,
    comentario character varying(1000),
    fecha timestamp without time zone,
    tipocomentario character varying(255),
    tipoid bigint,
    asesor_id bigint,
    filecensinfo_id bigint,
    parent_id bigint,
    profesor_id bigint,
    apellido_nombre_dni character varying(255),
    primero boolean,
    children_id bigint
);


ALTER TABLE public.cens_comentario OWNER TO postgres;

--
-- Name: cens_comentario_feed; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cens_comentario_feed (
    id bigint NOT NULL,
    fecha_creacion date,
    id_creador bigint,
    prefil_creador character varying(255),
    ultima_notificacion date,
    notificado boolean,
    visto boolean,
    id_dirigido bigint,
    prefil_dirigido character varying(255),
    comentariocens_id bigint,
    comentariocensid bigint,
    comentariotype character varying(255),
    ignorado boolean
);


ALTER TABLE public.cens_comentario_feed OWNER TO postgres;

--
-- Name: cens_contacto; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cens_contacto (
    id bigint NOT NULL,
    tipocontacto character varying(255),
    miembrocens_id bigint,
    datocontacto character varying(255)
);


ALTER TABLE public.cens_contacto OWNER TO postgres;

--
-- Name: cens_contacto_revision; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cens_contacto_revision (
    id bigint NOT NULL,
    contactorealizado boolean NOT NULL,
    contenidocontacto character varying(1000),
    tipocontacto character varying(255)
);


ALTER TABLE public.cens_contacto_revision OWNER TO postgres;

--
-- Name: cens_curso; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cens_curso (
    id bigint NOT NULL,
    nombre character varying(255),
    yearcurso integer NOT NULL
);


ALTER TABLE public.cens_curso OWNER TO postgres;

--
-- Name: cens_curso_cens_profesor; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cens_curso_cens_profesor (
    cens_curso_id bigint NOT NULL,
    profesores_id bigint NOT NULL
);


ALTER TABLE public.cens_curso_cens_profesor OWNER TO postgres;

--
-- Name: cens_estado_feed; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cens_estado_feed (
    id bigint NOT NULL,
    fecha_creacion timestamp without time zone,
    id_creador bigint,
    prefil_creador character varying(255),
    ultima_notificacion timestamp without time zone,
    notificado boolean,
    visto boolean,
    id_dirigido bigint,
    prefil_dirigido character varying(255),
    estadotype character varying(255),
    comentariotype character varying(255),
    ignorado boolean
);


ALTER TABLE public.cens_estado_feed OWNER TO postgres;

--
-- Name: cens_file_info; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cens_file_info (
    id bigint NOT NULL,
    baja boolean DEFAULT false,
    creationdate date,
    creator_id bigint,
    creatortype character varying(255),
    filelastmodify date,
    filelocation character varying(255),
    path character varying(1000),
    name character varying(255),
    size bigint,
    filetype character varying(255),
    real_name character varying(255)
);


ALTER TABLE public.cens_file_info OWNER TO postgres;

--
-- Name: cens_material_didactico; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cens_material_didactico (
    id bigint NOT NULL,
    descripcionformato character varying(500),
    tipoformato character varying(255),
    ubicacion character varying(500),
    ubicaciontype character varying(255),
    profesor_id bigint NOT NULL,
    programa_id bigint NOT NULL,
    descripcion character varying(500),
    estadorevisiontype character varying(255),
    nombre character varying(255),
    nro integer NOT NULL,
    fileinfo_id bigint,
    divisionperiodotype character varying(255)
);


ALTER TABLE public.cens_material_didactico OWNER TO postgres;

--
-- Name: cens_material_didactico_evaluado; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cens_material_didactico_evaluado (
    id bigint NOT NULL,
    fechaentrega date,
    fechaevaluacion date,
    nota double precision,
    alumno_id bigint,
    materialdidactico_id bigint
);


ALTER TABLE public.cens_material_didactico_evaluado OWNER TO postgres;

--
-- Name: cens_miembros_cens; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cens_miembros_cens (
    id bigint NOT NULL,
    apellido character varying(255),
    baja boolean,
    dni character varying(255),
    fechanac date,
    nombre character varying(255),
    usuario_id bigint
);


ALTER TABLE public.cens_miembros_cens OWNER TO postgres;

--
-- Name: cens_perfil_usuario_cens; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cens_perfil_usuario_cens (
    id bigint NOT NULL,
    perfiltype character varying(255),
    usuario_id bigint
);


ALTER TABLE public.cens_perfil_usuario_cens OWNER TO postgres;

--
-- Name: cens_preceptor; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cens_preceptor (
    id bigint NOT NULL,
    baja boolean,
    miembrocens_id bigint NOT NULL
);


ALTER TABLE public.cens_preceptor OWNER TO postgres;

--
-- Name: cens_profesor; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cens_profesor (
    id bigint NOT NULL,
    baja boolean,
    miembrocens_id bigint NOT NULL
);


ALTER TABLE public.cens_profesor OWNER TO postgres;

--
-- Name: cens_programa; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cens_programa (
    id bigint NOT NULL,
    cantcartillas integer NOT NULL,
    descripcion character varying(500),
    estadorevisiontype character varying(255),
    nombre character varying(255),
    asignatura_id bigint,
    fileinfo_id bigint,
    profesor_id bigint
);


ALTER TABLE public.cens_programa OWNER TO postgres;

--
-- Name: cens_programa_comentario; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cens_programa_comentario (
    id bigint NOT NULL,
    baja boolean,
    comentario character varying(1000),
    fecha timestamp without time zone,
    asesor_id bigint,
    filecensinfo_id bigint,
    profesor_id bigint,
    programa_id bigint
);


ALTER TABLE public.cens_programa_comentario OWNER TO postgres;

--
-- Name: cens_revision_material_didactico; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cens_revision_material_didactico (
    id bigint NOT NULL,
    fechaentrega date,
    fechafinal date,
    profesor_id bigint
);


ALTER TABLE public.cens_revision_material_didactico OWNER TO postgres;

--
-- Name: cens_revision_material_didactico_detalle; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cens_revision_material_didactico_detalle (
    id bigint NOT NULL,
    descripcion character varying(500),
    estadorevisiontype character varying(255),
    fecharevision date,
    asesor_id bigint,
    contactorevision_id bigint,
    revisionmaterialdidactico_id bigint
);


ALTER TABLE public.cens_revision_material_didactico_detalle OWNER TO postgres;

--
-- Name: cens_usuarios; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cens_usuarios (
    id bigint NOT NULL,
    enabled boolean,
    password character varying(255),
    username character varying(255),
    fileinfo_id bigint
);


ALTER TABLE public.cens_usuarios OWNER TO postgres;

--
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cliente (
    id bigint NOT NULL,
    direccion character varying(255),
    email character varying(255),
    emailcontacto character varying(255),
    estadoclienteenum character varying(255),
    fecha_baja timestamp without time zone,
    fecha_alta timestamp without time zone,
    fixed boolean,
    hsextrasconpresentismo boolean,
    nombre character varying(255),
    nombre_contacto character varying(255),
    razonsocial character varying(255),
    telefono character varying(255),
    gerenteoperacion_id bigint,
    jefeoperacion_id bigint
);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- Name: cliente_beneficiocliente; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cliente_beneficiocliente (
    cliente_id bigint NOT NULL,
    beneficios_id bigint NOT NULL
);


ALTER TABLE public.cliente_beneficiocliente OWNER TO postgres;

--
-- Name: empleado; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE empleado (
    id bigint NOT NULL,
    apellidos character varying(255),
    capitas integer,
    cbu character varying(255),
    celular character varying(255),
    chomba boolean,
    codigocontratacion integer,
    contactourgencias character varying(255),
    convenio character varying(255),
    cuil character varying(255),
    dni character varying(255),
    domicilio character varying(255),
    estado character varying(255),
    estadocivil character varying(255),
    fechaegresonovatium timestamp without time zone,
    fechaingresonovatium timestamp without time zone,
    fechanacimiento timestamp without time zone,
    fechapreocupacional timestamp without time zone,
    legajo integer,
    mailnovatium character varying(255),
    mailpersonal character varying(255),
    mailmpresa character varying(255),
    mochila boolean,
    nombres character varying(255),
    nrocuenta character varying(255),
    observaciones character varying(255),
    resultadopreocupacional character varying(255),
    sucursal character varying(255),
    telfijo character varying(255),
    telurgencias character varying(255),
    banco_id bigint,
    motivobaja_id bigint,
    nacionalidad_id bigint,
    obrasocial_id bigint,
    prepaga_id bigint
);


ALTER TABLE public.empleado OWNER TO postgres;

--
-- Name: empleado_relacionlaboral; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE empleado_relacionlaboral (
    empleado_id bigint NOT NULL,
    relacionlaboral_id bigint NOT NULL
);


ALTER TABLE public.empleado_relacionlaboral OWNER TO postgres;

--
-- Name: empleado_relacionpuestoempleado; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE empleado_relacionpuestoempleado (
    empleado_id bigint NOT NULL,
    puestos_id bigint NOT NULL
);


ALTER TABLE public.empleado_relacionpuestoempleado OWNER TO postgres;

--
-- Name: empleado_sueldo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE empleado_sueldo (
    empleado_id bigint NOT NULL,
    sueldo_id bigint NOT NULL
);


ALTER TABLE public.empleado_sueldo OWNER TO postgres;

--
-- Name: empleado_vacaciones; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE empleado_vacaciones (
    empleado_id bigint NOT NULL,
    vacaciones_id bigint NOT NULL
);


ALTER TABLE public.empleado_vacaciones OWNER TO postgres;

--
-- Name: empleadoviejofecha; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE empleadoviejofecha (
    id bigint NOT NULL,
    fechaegresonovatium timestamp without time zone,
    fechaingresonovatium timestamp without time zone,
    legajo integer,
    empleado_id bigint NOT NULL,
    motivo_id bigint
);


ALTER TABLE public.empleadoviejofecha OWNER TO postgres;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- Name: informeconsolidado; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE informeconsolidado (
    id bigint NOT NULL,
    estado character varying(255),
    periodo date,
    tipo character varying(255)
);


ALTER TABLE public.informeconsolidado OWNER TO postgres;

--
-- Name: informeconsolidado_informeconsolidadodetalle; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE informeconsolidado_informeconsolidadodetalle (
    informeconsolidado_id bigint NOT NULL,
    detalle_id bigint NOT NULL
);


ALTER TABLE public.informeconsolidado_informeconsolidadodetalle OWNER TO postgres;

--
-- Name: informeconsolidadodetalle; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE informeconsolidadodetalle (
    id bigint NOT NULL,
    adelantos character varying(255),
    asistenciapuntualidad character varying(255),
    basicomes character varying(255),
    celular character varying(255),
    cheque character varying(255),
    codigocontratacionempleado integer,
    conceptonoremunerativo character varying(255),
    conceptoremunerativoplus character varying(255),
    cont176510 character varying(255),
    contos character varying(255),
    diastrabajados double precision,
    embargos character varying(255),
    hsextras100 double precision,
    hsextras50 double precision,
    importeinasistenciasinjustificadas character varying(255),
    importeinasistenciassingocedesueldo character varying(255),
    neto character varying(255),
    netoadepositar character varying(255),
    nroinasistenciasinjustificadas double precision,
    nroinasistenciassingocedesueldo double precision,
    periodo date,
    prepaga character varying(255),
    reintegros character varying(255),
    ret11y3 character varying(255),
    retganancia character varying(255),
    retobrasocial character varying(255),
    sacbase character varying(255),
    sacdias double precision,
    sacvalor character varying(255),
    sueldobasico character varying(255),
    sueldobruto character varying(255),
    sueldobrutoremunerativo character varying(255),
    usarcheque boolean,
    vacacionesdias double precision,
    vacacionesvalor character varying(255),
    valorhsextras100 character varying(255),
    valorhsextras50 character varying(255),
    empleado_id bigint,
    informemensualdetalle_id bigint
);


ALTER TABLE public.informeconsolidadodetalle OWNER TO postgres;

--
-- Name: informeconsolidadodetalle_informeconsolidadodetallebeneficio; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE informeconsolidadodetalle_informeconsolidadodetallebeneficio (
    informeconsolidadodetalle_id bigint NOT NULL,
    beneficios_id bigint NOT NULL
);


ALTER TABLE public.informeconsolidadodetalle_informeconsolidadodetallebeneficio OWNER TO postgres;

--
-- Name: informeconsolidadodetallebeneficio; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE informeconsolidadodetallebeneficio (
    id bigint NOT NULL,
    importe character varying(255),
    beneficio_id bigint
);


ALTER TABLE public.informeconsolidadodetallebeneficio OWNER TO postgres;

--
-- Name: informemensual; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE informemensual (
    id bigint NOT NULL,
    estado character varying(255),
    periodo date,
    cliente_id bigint
);


ALTER TABLE public.informemensual OWNER TO postgres;

--
-- Name: informemensual_informemensualdetalle; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE informemensual_informemensualdetalle (
    informemensual_id bigint NOT NULL,
    detalle_id bigint NOT NULL
);


ALTER TABLE public.informemensual_informemensualdetalle OWNER TO postgres;

--
-- Name: informemensualdetalle; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE informemensualdetalle (
    id bigint NOT NULL,
    condicion character varying(255),
    diastrabajados double precision,
    hsextrasafacturaral100 double precision,
    hsextrasafacturaral50 double precision,
    hsextrasaliquidaral100 double precision,
    hsextrasaliquidaral50 double precision,
    porcentaje double precision,
    presentismo double precision,
    sueldobasico double precision,
    relacionlaboral_id bigint
);


ALTER TABLE public.informemensualdetalle OWNER TO postgres;

--
-- Name: informemensualdetalle_informemensualdetallebeneficio; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE informemensualdetalle_informemensualdetallebeneficio (
    informemensualdetalle_id bigint NOT NULL,
    beneficios_id bigint NOT NULL
);


ALTER TABLE public.informemensualdetalle_informemensualdetallebeneficio OWNER TO postgres;

--
-- Name: informemensualdetallebeneficio; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE informemensualdetallebeneficio (
    id bigint NOT NULL,
    valor character varying(255),
    beneficio_id bigint
);


ALTER TABLE public.informemensualdetallebeneficio OWNER TO postgres;

--
-- Name: motivobaja; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE motivobaja (
    id bigint NOT NULL,
    articulolct integer,
    motivo character varying(255)
);


ALTER TABLE public.motivobaja OWNER TO postgres;

--
-- Name: nacionalidad; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE nacionalidad (
    id bigint NOT NULL,
    nombre character varying(255)
);


ALTER TABLE public.nacionalidad OWNER TO postgres;

--
-- Name: obrasocial; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE obrasocial (
    id bigint NOT NULL,
    nombre character varying(255)
);


ALTER TABLE public.obrasocial OWNER TO postgres;

--
-- Name: perfil; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE perfil (
    id bigint NOT NULL,
    nombre character varying(255),
    perfil character varying(255)
);


ALTER TABLE public.perfil OWNER TO postgres;

--
-- Name: prepaga; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE prepaga (
    id bigint NOT NULL,
    nombre character varying(255)
);


ALTER TABLE public.prepaga OWNER TO postgres;

--
-- Name: puesto; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE puesto (
    id bigint NOT NULL,
    nombre character varying(255)
);


ALTER TABLE public.puesto OWNER TO postgres;

--
-- Name: relacionlaboral; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE relacionlaboral (
    id bigint NOT NULL,
    fechafin timestamp without time zone,
    fechainicio timestamp without time zone,
    cliente_id bigint,
    empleado_id bigint,
    puesto_id bigint
);


ALTER TABLE public.relacionlaboral OWNER TO postgres;

--
-- Name: relacionpuestoempleado; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE relacionpuestoempleado (
    id bigint NOT NULL,
    fechafin timestamp without time zone,
    fechainicio timestamp without time zone,
    puesto_id bigint,
    relacionlaboral_id bigint
);


ALTER TABLE public.relacionpuestoempleado OWNER TO postgres;

--
-- Name: sueldo; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE sueldo (
    id bigint NOT NULL,
    basico character varying(255),
    fechafin timestamp without time zone,
    fechainicio timestamp without time zone,
    presentismo character varying(255),
    empleado_id bigint
);


ALTER TABLE public.sueldo OWNER TO postgres;

--
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE usuario (
    id bigint NOT NULL,
    apellido character varying(255),
    enabled boolean,
    mail character varying(255),
    nombre character varying(255),
    password character varying(255),
    username character varying(255)
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- Name: usuario_perfil; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE usuario_perfil (
    id bigint NOT NULL,
    perfil_id bigint,
    usuario_id bigint
);


ALTER TABLE public.usuario_perfil OWNER TO postgres;

--
-- Name: vacaciones; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE vacaciones (
    id bigint NOT NULL,
    fechahasta timestamp without time zone,
    fechainicio timestamp without time zone,
    observaciones character varying(255),
    empleado_id bigint
);


ALTER TABLE public.vacaciones OWNER TO postgres;

--
-- Data for Name: banco; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY banco (id, nombre) FROM stdin;
\.


--
-- Data for Name: beneficio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY beneficio (id, descripcion, remunerativo, titulo) FROM stdin;
\.


--
-- Data for Name: beneficiocliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY beneficiocliente (id, tipo, valor, vigente, beneficio_id, cliente_id) FROM stdin;
\.


--
-- Data for Name: cens_activity_feed; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cens_activity_feed (id, fecha_creacion, tipo_de_fuente, id_creador, prefil_creador, ultima_notificacion, notificado, visto, id_dirigido, prefil_dirigido) FROM stdin;
\.


--
-- Data for Name: cens_alumno; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cens_alumno (id, alumnotype, baja, dni, nrolegajo, miembrocens_id, asignaturas_id) FROM stdin;
27	\N	t	0	\N	8	\N
\.


--
-- Data for Name: cens_alumno_cens_asignatura; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cens_alumno_cens_asignatura (cens_alumno_id, asignaturas_id) FROM stdin;
\.


--
-- Data for Name: cens_asesor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cens_asesor (id, baja, miembrocens_id, profesor_id) FROM stdin;
5	f	4	\N
33	f	32	\N
37	f	36	\N
51	f	47	\N
9	f	8	\N
\.


--
-- Data for Name: cens_asignatura; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cens_asignatura (id, horarios, modalidad, nombre, curso_id, profesor_id, profesorsuplente_id, vigente) FROM stdin;
62	test	test	test	49	42	11	t
89		modalidad	otra asignatura	49	\N	\N	t
88	f	f	Test no profe	59	\N	\N	t
66	a	a	Abc	61	11	\N	t
69	d	d	d	61	11	\N	t
71	f	f	f	61	42	\N	t
65	otra	otra	otra	49	11	\N	t
64	1212	que	Ingles2	97	11	\N	t
70	e	e	e	61	\N	11	t
68	caa	c	c	61	\N	11	t
67	b	bd	b	61	11	\N	t
\.


--
-- Data for Name: cens_cambio_estado_feed; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cens_cambio_estado_feed (id, comentariotype, fecha_creacion, id_creador, prefil_creador, ultima_notificacion, notificado, visto, id_dirigido, prefil_dirigido, estadorevisiontype, estadorevisiontypeviejo, tipoid, ignorado) FROM stdin;
499	MATERIAL	2015-03-27	8	PROFESOR	2015-02-19	t	f	47	ASESOR	LISTO	LISTO	496	f
500	MATERIAL	2015-03-27	8	PROFESOR	2015-02-19	t	f	36	ASESOR	LISTO	LISTO	496	f
509	PROGRAMA	2015-03-27	32	PROFESOR	2015-02-19	t	f	47	ASESOR	LISTO	LISTO	507	f
510	PROGRAMA	2015-03-27	32	PROFESOR	2015-02-19	t	f	36	ASESOR	LISTO	LISTO	507	f
511	PROGRAMA	2015-03-27	32	PROFESOR	2015-02-19	t	f	8	ASESOR	LISTO	LISTO	507	f
498	MATERIAL	2015-03-27	8	PROFESOR	2015-02-19	t	f	32	ASESOR	LISTO	LISTO	496	f
484	PROGRAMA	2015-03-26	-1	ASESOR	2015-02-19	t	f	32	PROFESOR	ASIGNADO	ASIGNADO	72	f
491	PROGRAMA	2015-03-26	8	PROFESOR	2015-02-19	t	f	47	ASESOR	LISTO	LISTO	391	f
492	PROGRAMA	2015-03-26	8	PROFESOR	2015-02-19	t	f	36	ASESOR	LISTO	LISTO	391	f
490	PROGRAMA	2015-03-26	8	PROFESOR	2015-02-19	t	f	32	ASESOR	LISTO	LISTO	391	f
486	PROGRAMA	2015-03-26	-1	ASESOR	2015-02-19	t	f	8	PROFESOR	CAMBIOS	CAMBIOS	72	f
493	PROGRAMA	2015-03-25	-1	ASESOR	2015-02-19	t	f	8	PROFESOR	ACEPTADO	ACEPTADO	72	f
501	PROGRAMA	2015-03-27	-1	ASESOR	2015-02-19	t	f	8	PROFESOR	CAMBIOS	CAMBIOS	391	f
504	PROGRAMA	2015-03-27	-1	ASESOR	2015-02-19	t	f	8	PROFESOR	ASIGNADO	ASIGNADO	391	f
512	PROGRAMA	2015-03-27	32	PROFESOR	2015-02-19	t	f	47	ASESOR	LISTO	LISTO	507	f
513	PROGRAMA	2015-03-27	32	PROFESOR	2015-02-19	t	f	36	ASESOR	LISTO	LISTO	507	f
514	PROGRAMA	2015-03-27	32	PROFESOR	2015-02-19	t	f	8	ASESOR	LISTO	LISTO	507	f
515	PROGRAMA	2015-03-27	-1	ASESOR	2015-02-19	t	f	32	PROFESOR	ACEPTADO	ACEPTADO	507	f
\.


--
-- Data for Name: cens_comentario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cens_comentario (id, baja, comentario, fecha, tipocomentario, tipoid, asesor_id, filecensinfo_id, parent_id, profesor_id, apellido_nombre_dni, primero, children_id) FROM stdin;
453	f	4	2015-03-19 17:40:21.092	PROGRAMA	391	\N	\N	\N	42	PROFESOR A, micaela (1212121)	t	\N
461	f	fa	2015-03-19 17:41:51.906	PROGRAMA	391	\N	\N	457	42	PROFESOR A, micaela (1212121)	f	\N
465	f	dsa	2015-03-19 17:44:44.608	PROGRAMA	72	33	\N	377	\N	ASESOR A, micaela (1212121)	f	\N
469	f	sf	2015-03-19 19:40:14.156	PROGRAMA	72	33	\N	\N	\N	ASESOR A, micaela (1212121)	t	\N
473	f	fgdfgdfgdf	2015-03-19 19:41:34.685	PROGRAMA	72	9	\N	\N	\N	ASESOR CIPOLLA, mica (33968270)	t	\N
476	f	Cambio de estado del programa de LISTO a ASIGNADO	2015-03-20 18:18:47.538	PROGRAMA	391	9	\N	\N	\N	ASESOR CIPOLLA, mica (33968270)	t	\N
485	f	Cambio de estado del programa de ACEPTADO a ASIGNADO	2015-03-26 10:28:40.607	PROGRAMA	72	9	\N	\N	\N	ASESOR CIPOLLA, mica (33968270)	t	\N
494	f	Cambio de estado del programa de CAMBIOS a ACEPTADO	2015-03-26 17:43:35.352	PROGRAMA	72	33	\N	\N	\N	ASESOR A, micaela (1212121)	t	\N
505	f	Cambio de estado del programa de CAMBIOS a ASIGNADO	2015-03-27 14:21:58.404	PROGRAMA	391	33	\N	\N	\N	ASESOR A, micaela (1212121)	t	\N
379	f	que tal	2015-03-19 12:22:20.07	PROGRAMA	72	33	\N	377	\N	ASESOR A, micaela (1212121)	f	\N
383	f	ff	2015-03-19 13:06:01.017	PROGRAMA	72	33	\N	379	\N	ASESOR A, micaela (1212121)	f	\N
387	f	8	2015-03-19 13:42:44.911	PROGRAMA	72	33	\N	\N	\N	ASESOR A, micaela (1212121)	t	\N
393	t	89	2015-03-19 13:49:21.348	PROGRAMA	391	\N	\N	\N	42	PROFESOR A, micaela (1212121)	t	\N
395	t	8	2015-03-19 13:51:01.582	PROGRAMA	391	\N	\N	\N	42	PROFESOR A, micaela (1212121)	t	\N
397	t	90	2015-03-19 13:54:20.783	PROGRAMA	391	\N	\N	\N	42	PROFESOR A, micaela (1212121)	t	\N
405	t	9	2015-03-19 14:30:23.188	PROGRAMA	391	\N	\N	\N	42	PROFESOR A, micaela (1212121)	t	\N
413	t	8	2015-03-19 15:06:49.837	PROGRAMA	391	\N	\N	\N	42	PROFESOR A, micaela (1212121)	t	\N
421	t	7	2015-03-19 15:36:00.639	PROGRAMA	391	\N	\N	\N	42	PROFESOR A, micaela (1212121)	t	\N
429	t	9	2015-03-19 16:09:42.279	PROGRAMA	391	\N	\N	\N	42	PROFESOR A, micaela (1212121)	t	\N
437	t	8	2015-03-19 16:27:14.555	PROGRAMA	391	\N	\N	\N	42	PROFESOR A, micaela (1212121)	t	\N
445	t	7	2015-03-19 16:57:37.512	PROGRAMA	391	\N	\N	\N	42	PROFESOR A, micaela (1212121)	t	\N
457	f	34	2015-03-19 17:41:39.377	PROGRAMA	391	\N	\N	\N	42	PROFESOR A, micaela (1212121)	t	\N
463	f	34	2015-03-19 17:44:13.184	PROGRAMA	72	33	\N	381	\N	ASESOR A, micaela (1212121)	f	\N
467	f	fa	2015-03-19 17:45:04.736	PROGRAMA	72	33	\N	377	\N	ASESOR A, micaela (1212121)	f	\N
471	f	sd	2015-03-19 19:40:38.309	PROGRAMA	72	33	\N	\N	\N	ASESOR A, micaela (1212121)	t	\N
474	f	2	2015-03-19 19:43:25.773	PROGRAMA	72	9	\N	\N	\N	ASESOR CIPOLLA, mica (33968270)	t	\N
479	f	Cambio de estado del programa de ASIGNADO a RECHAZADO	2015-03-20 18:32:16.686	PROGRAMA	391	9	\N	\N	\N	ASESOR CIPOLLA, mica (33968270)	t	\N
487	f	Cambio de estado del programa de ASIGNADO a CAMBIOS	2015-03-26 10:28:56.575	PROGRAMA	72	33	\N	\N	\N	ASESOR A, micaela (1212121)	t	\N
502	f	Cambio de estado del programa de LISTO a CAMBIOS	2015-03-27 14:21:14.105	PROGRAMA	391	33	\N	\N	\N	ASESOR A, micaela (1212121)	t	\N
516	f	Cambio de estado del programa de LISTO a ACEPTADO	2015-03-27 14:44:11.211	PROGRAMA	507	33	\N	\N	\N	ASESOR A, micaela (1212121)	t	\N
377	f	hola	2015-03-19 12:21:14.173	PROGRAMA	72	9	\N	\N	\N	ASESOR CIPOLLA, mica (33968270)	t	\N
381	f	hol	2015-03-19 13:04:36.89	PROGRAMA	72	33	\N	\N	\N	ASESOR A, micaela (1212121)	t	\N
385	f	sa	2015-03-19 13:14:08.63	PROGRAMA	72	33	\N	377	\N	ASESOR A, micaela (1212121)	f	\N
389	f	6	2015-03-19 13:47:15.907	PROGRAMA	72	33	\N	377	\N	ASESOR A, micaela (1212121)	f	\N
394	t	0	2015-03-19 13:50:27.69	PROGRAMA	391	\N	\N	\N	42	PROFESOR A, micaela (1212121)	t	\N
396	t	89	2015-03-19 13:52:00.567	PROGRAMA	391	\N	\N	\N	42	PROFESOR A, micaela (1212121)	t	\N
401	t	9	2015-03-19 14:28:16.971	PROGRAMA	391	\N	\N	\N	42	PROFESOR A, micaela (1212121)	t	\N
409	t	8	2015-03-19 14:43:53.472	PROGRAMA	391	\N	\N	\N	42	PROFESOR A, micaela (1212121)	t	\N
417	t	8	2015-03-19 15:10:32.404	PROGRAMA	391	\N	\N	\N	42	PROFESOR A, micaela (1212121)	t	\N
425	t	89	2015-03-19 15:41:37.522	PROGRAMA	391	\N	\N	\N	42	PROFESOR A, micaela (1212121)	t	\N
433	t	8	2015-03-19 16:24:48.427	PROGRAMA	391	\N	\N	\N	42	PROFESOR A, micaela (1212121)	t	\N
441	t	7	2015-03-19 16:29:27.318	PROGRAMA	391	\N	\N	\N	42	PROFESOR A, micaela (1212121)	t	\N
\.


--
-- Data for Name: cens_comentario_feed; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cens_comentario_feed (id, fecha_creacion, id_creador, prefil_creador, ultima_notificacion, notificado, visto, id_dirigido, prefil_dirigido, comentariocens_id, comentariocensid, comentariotype, ignorado) FROM stdin;
466	2015-03-19	32	ASESOR	2015-02-19	t	f	8	ASESOR	\N	465	PROGRAMA	f
468	2015-03-19	32	ASESOR	2015-02-19	t	f	8	ASESOR	\N	467	PROGRAMA	f
470	2015-03-19	32	ASESOR	2015-02-19	t	f	8	PROFESOR	\N	469	PROGRAMA	f
472	2015-03-19	32	ASESOR	2015-02-19	t	f	8	PROFESOR	\N	471	PROGRAMA	f
378	2015-03-19	8	ASESOR	2015-02-19	t	f	8	PROFESOR	\N	377	PROGRAMA	f
382	2015-03-19	32	ASESOR	2015-02-19	t	f	8	PROFESOR	\N	381	PROGRAMA	f
477	2015-03-20	8	ASESOR	2015-02-19	t	f	32	PROFESOR	\N	476	PROGRAMA	f
480	2015-03-20	8	ASESOR	2015-02-19	t	f	32	PROFESOR	\N	479	PROGRAMA	f
462	2015-03-19	32	PROFESOR	2015-02-19	t	f	32	PROFESOR	\N	461	PROGRAMA	f
454	2015-03-19	32	PROFESOR	2015-02-19	t	f	47	ASESOR	\N	453	PROGRAMA	f
384	2015-03-19	32	ASESOR	2015-02-19	t	f	32	ASESOR	\N	383	PROGRAMA	f
464	2015-03-19	32	ASESOR	2015-02-19	t	f	32	ASESOR	\N	463	PROGRAMA	f
380	2015-03-19	32	ASESOR	2015-02-19	t	f	8	ASESOR	\N	379	PROGRAMA	f
386	2015-03-19	32	ASESOR	2015-02-19	t	f	8	ASESOR	\N	385	PROGRAMA	f
388	2015-03-19	32	ASESOR	2015-02-19	t	f	8	PROFESOR	\N	387	PROGRAMA	f
390	2015-03-19	32	ASESOR	2015-02-19	t	f	8	ASESOR	\N	389	PROGRAMA	f
488	2015-03-26	32	ASESOR	2015-02-19	t	f	8	PROFESOR	\N	487	PROGRAMA	f
495	2015-03-26	32	ASESOR	2015-02-19	t	f	8	PROFESOR	\N	494	PROGRAMA	f
455	2015-03-19	32	PROFESOR	2015-02-19	t	f	36	ASESOR	\N	453	PROGRAMA	f
458	2015-03-19	32	PROFESOR	2015-02-19	t	f	47	ASESOR	\N	457	PROGRAMA	f
459	2015-03-19	32	PROFESOR	2015-02-19	t	f	36	ASESOR	\N	457	PROGRAMA	f
456	2015-03-19	32	PROFESOR	2015-02-19	t	f	8	ASESOR	\N	453	PROGRAMA	f
460	2015-03-19	32	PROFESOR	2015-02-19	t	f	8	ASESOR	\N	457	PROGRAMA	f
503	2015-03-27	32	ASESOR	2015-02-19	t	f	8	PROFESOR	\N	502	PROGRAMA	f
506	2015-03-27	32	ASESOR	2015-02-19	t	f	8	PROFESOR	\N	505	PROGRAMA	f
\.


--
-- Data for Name: cens_contacto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cens_contacto (id, tipocontacto, miembrocens_id, datocontacto) FROM stdin;
368	MAIL	8	mikacip@gmail.com
\.


--
-- Data for Name: cens_contacto_revision; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cens_contacto_revision (id, contactorealizado, contenidocontacto, tipocontacto) FROM stdin;
\.


--
-- Data for Name: cens_curso; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cens_curso (id, nombre, yearcurso) FROM stdin;
49	cursera	2015
58	fake	2015
59	testftp2	2015
60	testcursores	2015
61	test	2015
94	test2	2015
95	test3	2015
96	test4	2015
97	test5	2015
98	test6	2015
99	test7	2015
100	test8	2015
101	test9	2015
\.


--
-- Data for Name: cens_curso_cens_profesor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cens_curso_cens_profesor (cens_curso_id, profesores_id) FROM stdin;
\.


--
-- Data for Name: cens_estado_feed; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cens_estado_feed (id, fecha_creacion, id_creador, prefil_creador, ultima_notificacion, notificado, visto, id_dirigido, prefil_dirigido, estadotype, comentariotype, ignorado) FROM stdin;
\.


--
-- Data for Name: cens_file_info; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cens_file_info (id, baja, creationdate, creator_id, creatortype, filelastmodify, filelocation, path, name, size, filetype, real_name) FROM stdin;
76	t	2015-01-26	11	PROFESOR	\N	FTP	61/asignaturas/67/programa/1422278736641Credencial.pdf	Credencial.pdf	47680	PROGRAMA	\N
79	t	2015-01-26	11	PROFESOR	\N	FTP	61/asignaturas/67/programa/1422283746706Credencial.pdf	Credencial.pdf	47680	PROGRAMA	\N
80	t	2015-01-26	11	PROFESOR	\N	FTP	61/asignaturas/67/programa/1422283809339Javier_Vega_ENdic2014.doc	Javier_Vega_ENdic2014.doc	72192	PROGRAMA	\N
191	f	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422631996949Boleta.pdf
192	f	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422632004147Boleta.pdf
194	f	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422632234702Boleta.pdf
81	t	2015-01-26	11	PROFESOR	\N	FTP	61/asignaturas/67/programa/1422283867764Boleta.pdf	Boleta.pdf	30018	PROGRAMA	\N
82	t	2015-01-26	11	PROFESOR	\N	FTP	61/asignaturas/67/programa/1422288742142Boleta.pdf	Boleta.pdf	30018	PROGRAMA	\N
83	t	2015-01-26	11	PROFESOR	\N	FTP	61/asignaturas/67/programa/1422288779910Boleta.pdf	Boleta.pdf	30018	PROGRAMA	\N
84	t	2015-01-26	11	PROFESOR	\N	FTP	61/asignaturas/67/programa/1422288811014Boleta.pdf	Boleta.pdf	30018	PROGRAMA	\N
85	t	2015-01-26	11	PROFESOR	\N	FTP	61/asignaturas/67/programa/1422288853026Boleta.pdf	Boleta.pdf	30018	PROGRAMA	\N
86	t	2015-01-26	11	PROFESOR	\N	FTP	61/asignaturas/67/programa/1422288963350Boleta.pdf	Boleta.pdf	30018	PROGRAMA	\N
73	t	2015-01-26	11	PROFESOR	\N	FTP	61/asignaturas/64/programa/1422278363453V01M4RN.pdf	V01M4RN.pdf	87939	PROGRAMA	\N
195	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422632269123Boleta.pdf
196	f	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422632545277Boleta.pdf
198	f	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422632568064Boleta.pdf
92	t	2015-01-26	11	PROFESOR	\N	FTP	61/asignaturas/64/programa/	tpm1vstpm2.xlsx	12188	PROGRAMA	1422305131779tpm1vstpm2.xlsx
91	t	2015-01-26	11	PROFESOR	\N	FTP	61/asignaturas/64/programa/	tpm1vstpm2.xlsx	12188	PROGRAMA	1422305059493tpm1vstpm2.xlsx
90	f	2015-01-26	11	PROFESOR	\N	FTP	61/asignaturas/67/programa/	Boleta.pdf	30018	PROGRAMA	1422305021704Boleta.pdf
87	t	2015-01-26	11	PROFESOR	\N	FTP	61/asignaturas/67/programa/	Boleta.pdf	30018	PROGRAMA	1422290818499Boleta.pdf
78	f	2015-01-26	11	PROFESOR	\N	FTP	61/asignaturas/68/programa/	Boleta.pdf	30018	PROGRAMA	1422278914025Boleta.pdf
200	f	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422633059938Boleta.pdf
202	f	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422633068254Boleta.pdf
186	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422625334660Boleta.pdf
187	f	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422631957976Boleta.pdf
189	f	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422631986133Boleta.pdf
204	f	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422633095890Boleta.pdf
206	f	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422633283413Boleta.pdf
208	f	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422633294299Boleta.pdf
211	f	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422633307440Boleta.pdf
213	f	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422633314405Boleta.pdf
215	f	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422633329343Boleta.pdf
217	f	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422633351417Boleta.pdf
225	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422636986010Boleta.pdf
226	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422637045784Boleta.pdf
227	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422637055169Boleta.pdf
224	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422636967596Boleta.pdf
221	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422636942113Boleta.pdf
229	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422637171633Boleta.pdf
230	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422637199504Boleta.pdf
231	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422637980587Boleta.pdf
232	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422637999218Boleta.pdf
233	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422638017836Boleta.pdf
234	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422638046919Boleta.pdf
235	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422638080551Boleta.pdf
236	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422638095439Boleta.pdf
237	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422638116420Boleta.pdf
238	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422638144032Boleta.pdf
239	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422638193481Boleta.pdf
228	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422637127096Boleta.pdf
241	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422638274683Boleta.pdf
245	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422645197050Boleta.pdf
247	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422645930338Boleta.pdf
248	f	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422645946384Boleta.pdf
250	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422646020625Boleta.pdf
251	f	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422646033321Boleta.pdf
254	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422646124522Boleta.pdf
258	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422646620561Boleta.pdf
256	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422646242297Boleta.pdf
255	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422646155705Boleta.pdf
246	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422645405576Boleta.pdf
244	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422638830505Boleta.pdf
240	t	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422638242852Boleta.pdf
293	f	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422649084015Boleta.pdf
295	f	2015-01-30	9	ASESOR	\N	FTP	comentarios/	Boleta.pdf	30018	COMENTARIO	1422650184431Boleta.pdf
93	t	2015-01-26	11	PROFESOR	\N	FTP	97/asignaturas/64/programa/	V01M4RN.pdf	87939	PROGRAMA	1422305249400V01M4RN.pdf
318	f	2015-02-27	11	PROFESOR	\N	FTP	97/asignaturas/64/programa/	Boleta.pdf	30018	PROGRAMA	1425076715560Boleta.pdf
321	t	2015-03-02	6	USUARIO	\N	FTP	avatar/6/	cow.png	98980	FOTO	1425303126462cow.png
322	t	2015-03-02	6	USUARIO	\N	FTP	avatar/6/	cow.png	98980	FOTO	1425304754192cow.png
323	t	2015-03-02	6	USUARIO	\N	FTP	avatar/6/	cow.png	98980	FOTO	1425304768785cow.png
324	t	2015-03-02	6	USUARIO	\N	FTP	avatar/6/	cow.png	98980	FOTO	1425304842275cow.png
325	t	2015-03-02	6	USUARIO	\N	FTP	avatar/6/	Captura de pantalla de 2014-11-14 15_46_06.png	142117	FOTO	1425304869154Captura de pantalla de 2014-11-14 15_46_06.png
326	t	2015-03-02	6	USUARIO	\N	FTP	avatar/6/	Captura de pantalla de 2014-11-14 15_46_06.png	142117	FOTO	1425304899498Captura de pantalla de 2014-11-14 15_46_06.png
327	t	2015-03-02	6	USUARIO	\N	FTP	avatar/6/	cow.png	98980	FOTO	1425305351762cow.png
328	t	2015-03-02	6	USUARIO	\N	FTP	avatar/6/	Captura de pantalla de 2014-11-14 15_46_06.png	142117	FOTO	1425305422874Captura de pantalla de 2014-11-14 15_46_06.png
329	t	2015-03-02	6	USUARIO	\N	FTP	avatar/6/	cow.png	98980	FOTO	1425306121157cow.png
330	t	2015-03-02	6	USUARIO	\N	FTP	avatar/6/	cow.png	98980	FOTO	1425306503526cow.png
331	t	2015-03-02	6	USUARIO	\N	FTP	avatar/6/	Captura de pantalla de 2014-11-14 15_46_06.png	142117	FOTO	1425306633577Captura de pantalla de 2014-11-14 15_46_06.png
332	t	2015-03-02	6	USUARIO	\N	FTP	avatar/6/	Captura de pantalla de 2014-11-14 15_46_06.png	142117	FOTO	1425306800182Captura de pantalla de 2014-11-14 15_46_06.png
333	t	2015-03-02	6	USUARIO	\N	FTP	avatar/6/	Captura de pantalla de 2014-11-14 15_46_06.png	142117	FOTO	1425307468201Captura de pantalla de 2014-11-14 15_46_06.png
334	t	2015-03-02	6	USUARIO	\N	FTP	avatar/6/	cow.png	98980	FOTO	1425307497163cow.png
335	t	2015-03-02	6	USUARIO	\N	FTP	avatar/6/	intro.png	62475	FOTO	1425307509479intro.png
336	t	2015-03-02	6	USUARIO	\N	FTP	avatar/6/	cow.png	98980	FOTO	1425307662142cow.png
342	f	2015-03-02	30	USUARIO	\N	FTP	avatar/30/	intro.png	62475	FOTO	1425319525075intro.png
339	t	2015-03-02	6	USUARIO	\N	FTP	avatar/6/	Captura de pantalla de 2014-11-14 15_46_06.png	142117	FOTO	1425319454268Captura de pantalla de 2014-11-14 15_46_06.png
343	f	2015-03-02	6	USUARIO	\N	FTP	avatar/6/	cow.png	98980	FOTO	1425319550258cow.png
345	t	2015-03-04	6	USUARIO	\N	FTP	avatar/6/	Penguins.jpg	777835	FOTO	1425500854779Penguins.jpg
346	t	2015-03-04	6	USUARIO	\N	FTP	avatar/6/	Tulips.jpg	620888	FOTO	1425500921868Tulips.jpg
347	t	2015-03-04	6	USUARIO	\N	FTP	avatar/6/	Tulips.jpg	620888	FOTO	1425501109589Tulips.jpg
348	t	2015-03-04	6	USUARIO	\N	FTP	avatar/6/	Jellyfish.jpg	775702	FOTO	1425501138459Jellyfish.jpg
349	t	2015-03-04	6	USUARIO	\N	FTP	avatar/6/	Penguins.jpg	777835	FOTO	1425501264383Penguins.jpg
350	f	2015-03-04	6	USUARIO	\N	FTP	avatar/6/	Tulips.jpg	620888	FOTO	1425501268221Tulips.jpg
392	t	2015-03-19	42	PROFESOR	\N	FTP	49/asignaturas/62/programa/	Javier_Vega_ENdic2014.doc	72192	PROGRAMA	1426783681479Javier_Vega_ENdic2014.doc
489	f	2015-03-26	11	PROFESOR	\N	FTP	49/asignaturas/62/programa/	Javier_Vega_ENdic2014.doc	72192	PROGRAMA	1427387204766Javier_Vega_ENdic2014.doc
497	f	2015-03-27	11	PROFESOR	\N	FTP	97/asignaturas/64/material/	Javier_Vega_ENdic2014.doc	72192	MATERIAL	1427464336705Javier_Vega_ENdic2014.doc
508	f	2015-03-27	42	PROFESOR	\N	FTP	61/asignaturas/71/programa/	Javier_Vega_ENdic2014.doc	72192	PROGRAMA	1427478163883Javier_Vega_ENdic2014.doc
517	t	2015-03-31	30	USUARIO	\N	FTP	avatar/30/	2015-02-11.jpg	14489	FOTO	14278083062892015-02-11.jpg
518	t	2015-03-31	30	USUARIO	\N	FTP	avatar/30/	Penguins.jpg	777835	FOTO	1427808331350Penguins.jpg
523	f	2015-04-06	30	USUARIO	\N	FTP	avatar/30/	Chrysanthemum.jpg	879394	FOTO	1428340525789Chrysanthemum.jpg
\.


--
-- Data for Name: cens_material_didactico; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cens_material_didactico (id, descripcionformato, tipoformato, ubicacion, ubicaciontype, profesor_id, programa_id, descripcion, estadorevisiontype, nombre, nro, fileinfo_id, divisionperiodotype) FROM stdin;
496	\N	\N	\N	\N	11	72	\N	LISTO	565	1	497	SEMESTRE_1
\.


--
-- Data for Name: cens_material_didactico_evaluado; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cens_material_didactico_evaluado (id, fechaentrega, fechaevaluacion, nota, alumno_id, materialdidactico_id) FROM stdin;
\.


--
-- Data for Name: cens_miembros_cens; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cens_miembros_cens (id, apellido, baja, dni, fechanac, nombre, usuario_id) FROM stdin;
4	s	t	1	2015-01-12	a	2
32	A	f	1212121	1993-05-02	micaela	30
47	micae	f	156	2000-01-03	yoyo	45
36	Z	f	98	1980-01-01	micae	34
8	cipolla	f	33968270	2001-10-20	mica	6
521	testSalty	f	32455756	1998-04-01	testSalty	519
\.


--
-- Data for Name: cens_perfil_usuario_cens; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cens_perfil_usuario_cens (id, perfiltype, usuario_id) FROM stdin;
1	ROLE_ADMINISTRADOR	1
3	ASESOR	2
29	ROLE_PROFESOR	6
31	ROLE_ASESOR	30
35	ROLE_ASESOR	34
41	ROLE_PROFESOR	30
50	ROLE_ASESOR	45
63	ROLE_ASESOR	6
520	ROLE_PROFESOR	519
\.


--
-- Data for Name: cens_preceptor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cens_preceptor (id, baja, miembrocens_id) FROM stdin;
\.


--
-- Data for Name: cens_profesor; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cens_profesor (id, baja, miembrocens_id) FROM stdin;
11	f	8
42	f	32
48	t	47
44	t	36
522	f	521
\.


--
-- Data for Name: cens_programa; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cens_programa (id, cantcartillas, descripcion, estadorevisiontype, nombre, asignatura_id, fileinfo_id, profesor_id) FROM stdin;
74	1	test2	NUEVO	test2	66	\N	11
77	1	\N	NUEVO	vaca	68	\N	11
75	5	\N	LISTO	gr	67	90	11
72	2	esto es una prueba	ACEPTADO	Test	64	318	11
391	1	\N	ASIGNADO	8	62	489	11
507	2	\N	ACEPTADO	2	71	508	42
\.


--
-- Data for Name: cens_programa_comentario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cens_programa_comentario (id, baja, comentario, fecha, asesor_id, filecensinfo_id, profesor_id, programa_id) FROM stdin;
\.


--
-- Data for Name: cens_revision_material_didactico; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cens_revision_material_didactico (id, fechaentrega, fechafinal, profesor_id) FROM stdin;
\.


--
-- Data for Name: cens_revision_material_didactico_detalle; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cens_revision_material_didactico_detalle (id, descripcion, estadorevisiontype, fecharevision, asesor_id, contactorevision_id, revisionmaterialdidactico_id) FROM stdin;
\.


--
-- Data for Name: cens_usuarios; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cens_usuarios (id, enabled, password, username, fileinfo_id) FROM stdin;
1	t	WltgOOb1ksa2dWUHr1NsBDdx+0evWAz7	fake	\N
2	f	WltgOOb1ksa2dWUHr1NsBDdx+0evWAz7	a	\N
6	t	be+jw5owmqCZQo2/oTAxrZ1/eCNju2ZJ	mikacip	350
45	t	3IHsbWh/KsfKVWVqPQaDOvJjr3akYBAn	fa	\N
519	t	tMk9Zs/ob3NZZ2QkudbsWenrwCF+OhqX	testsalty	\N
34	t	269JhLVotieZhp5WybP15qb5jE684uv+	cow	\N
30	t	K8gVChCU2su0+bxOoRqcwSyfP1hDBJjq	aaa	523
\.


--
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cliente (id, direccion, email, emailcontacto, estadoclienteenum, fecha_baja, fecha_alta, fixed, hsextrasconpresentismo, nombre, nombre_contacto, razonsocial, telefono, gerenteoperacion_id, jefeoperacion_id) FROM stdin;
\.


--
-- Data for Name: cliente_beneficiocliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY cliente_beneficiocliente (cliente_id, beneficios_id) FROM stdin;
\.


--
-- Data for Name: empleado; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY empleado (id, apellidos, capitas, cbu, celular, chomba, codigocontratacion, contactourgencias, convenio, cuil, dni, domicilio, estado, estadocivil, fechaegresonovatium, fechaingresonovatium, fechanacimiento, fechapreocupacional, legajo, mailnovatium, mailpersonal, mailmpresa, mochila, nombres, nrocuenta, observaciones, resultadopreocupacional, sucursal, telfijo, telurgencias, banco_id, motivobaja_id, nacionalidad_id, obrasocial_id, prepaga_id) FROM stdin;
\.


--
-- Data for Name: empleado_relacionlaboral; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY empleado_relacionlaboral (empleado_id, relacionlaboral_id) FROM stdin;
\.


--
-- Data for Name: empleado_relacionpuestoempleado; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY empleado_relacionpuestoempleado (empleado_id, puestos_id) FROM stdin;
\.


--
-- Data for Name: empleado_sueldo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY empleado_sueldo (empleado_id, sueldo_id) FROM stdin;
\.


--
-- Data for Name: empleado_vacaciones; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY empleado_vacaciones (empleado_id, vacaciones_id) FROM stdin;
\.


--
-- Data for Name: empleadoviejofecha; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY empleadoviejofecha (id, fechaegresonovatium, fechaingresonovatium, legajo, empleado_id, motivo_id) FROM stdin;
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('hibernate_sequence', 523, true);


--
-- Data for Name: informeconsolidado; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY informeconsolidado (id, estado, periodo, tipo) FROM stdin;
\.


--
-- Data for Name: informeconsolidado_informeconsolidadodetalle; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY informeconsolidado_informeconsolidadodetalle (informeconsolidado_id, detalle_id) FROM stdin;
\.


--
-- Data for Name: informeconsolidadodetalle; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY informeconsolidadodetalle (id, adelantos, asistenciapuntualidad, basicomes, celular, cheque, codigocontratacionempleado, conceptonoremunerativo, conceptoremunerativoplus, cont176510, contos, diastrabajados, embargos, hsextras100, hsextras50, importeinasistenciasinjustificadas, importeinasistenciassingocedesueldo, neto, netoadepositar, nroinasistenciasinjustificadas, nroinasistenciassingocedesueldo, periodo, prepaga, reintegros, ret11y3, retganancia, retobrasocial, sacbase, sacdias, sacvalor, sueldobasico, sueldobruto, sueldobrutoremunerativo, usarcheque, vacacionesdias, vacacionesvalor, valorhsextras100, valorhsextras50, empleado_id, informemensualdetalle_id) FROM stdin;
\.


--
-- Data for Name: informeconsolidadodetalle_informeconsolidadodetallebeneficio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY informeconsolidadodetalle_informeconsolidadodetallebeneficio (informeconsolidadodetalle_id, beneficios_id) FROM stdin;
\.


--
-- Data for Name: informeconsolidadodetallebeneficio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY informeconsolidadodetallebeneficio (id, importe, beneficio_id) FROM stdin;
\.


--
-- Data for Name: informemensual; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY informemensual (id, estado, periodo, cliente_id) FROM stdin;
\.


--
-- Data for Name: informemensual_informemensualdetalle; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY informemensual_informemensualdetalle (informemensual_id, detalle_id) FROM stdin;
\.


--
-- Data for Name: informemensualdetalle; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY informemensualdetalle (id, condicion, diastrabajados, hsextrasafacturaral100, hsextrasafacturaral50, hsextrasaliquidaral100, hsextrasaliquidaral50, porcentaje, presentismo, sueldobasico, relacionlaboral_id) FROM stdin;
\.


--
-- Data for Name: informemensualdetalle_informemensualdetallebeneficio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY informemensualdetalle_informemensualdetallebeneficio (informemensualdetalle_id, beneficios_id) FROM stdin;
\.


--
-- Data for Name: informemensualdetallebeneficio; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY informemensualdetallebeneficio (id, valor, beneficio_id) FROM stdin;
\.


--
-- Data for Name: motivobaja; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY motivobaja (id, articulolct, motivo) FROM stdin;
\.


--
-- Data for Name: nacionalidad; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY nacionalidad (id, nombre) FROM stdin;
\.


--
-- Data for Name: obrasocial; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY obrasocial (id, nombre) FROM stdin;
\.


--
-- Data for Name: perfil; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY perfil (id, nombre, perfil) FROM stdin;
\.


--
-- Data for Name: prepaga; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY prepaga (id, nombre) FROM stdin;
\.


--
-- Data for Name: puesto; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY puesto (id, nombre) FROM stdin;
\.


--
-- Data for Name: relacionlaboral; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY relacionlaboral (id, fechafin, fechainicio, cliente_id, empleado_id, puesto_id) FROM stdin;
\.


--
-- Data for Name: relacionpuestoempleado; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY relacionpuestoempleado (id, fechafin, fechainicio, puesto_id, relacionlaboral_id) FROM stdin;
\.


--
-- Data for Name: sueldo; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY sueldo (id, basico, fechafin, fechainicio, presentismo, empleado_id) FROM stdin;
\.


--
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY usuario (id, apellido, enabled, mail, nombre, password, username) FROM stdin;
\.


--
-- Data for Name: usuario_perfil; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY usuario_perfil (id, perfil_id, usuario_id) FROM stdin;
\.


--
-- Data for Name: vacaciones; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY vacaciones (id, fechahasta, fechainicio, observaciones, empleado_id) FROM stdin;
\.


--
-- Name: banco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY banco
    ADD CONSTRAINT banco_pkey PRIMARY KEY (id);


--
-- Name: beneficio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY beneficio
    ADD CONSTRAINT beneficio_pkey PRIMARY KEY (id);


--
-- Name: beneficiocliente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY beneficiocliente
    ADD CONSTRAINT beneficiocliente_pkey PRIMARY KEY (id);


--
-- Name: cens_activity_feed_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_activity_feed
    ADD CONSTRAINT cens_activity_feed_pkey PRIMARY KEY (id);


--
-- Name: cens_alumno_cens_asignatura_asignaturas_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_alumno_cens_asignatura
    ADD CONSTRAINT cens_alumno_cens_asignatura_asignaturas_id_key UNIQUE (asignaturas_id);


--
-- Name: cens_alumno_miembrocens_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_alumno
    ADD CONSTRAINT cens_alumno_miembrocens_id_key UNIQUE (miembrocens_id);


--
-- Name: cens_alumno_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_alumno
    ADD CONSTRAINT cens_alumno_pkey PRIMARY KEY (id);


--
-- Name: cens_asesor_miembrocens_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_asesor
    ADD CONSTRAINT cens_asesor_miembrocens_id_key UNIQUE (miembrocens_id);


--
-- Name: cens_asesor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_asesor
    ADD CONSTRAINT cens_asesor_pkey PRIMARY KEY (id);


--
-- Name: cens_asignatura_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_asignatura
    ADD CONSTRAINT cens_asignatura_pkey PRIMARY KEY (id);


--
-- Name: cens_cambio_estado_feed_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_cambio_estado_feed
    ADD CONSTRAINT cens_cambio_estado_feed_pkey PRIMARY KEY (id);


--
-- Name: cens_comentario_feed_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_comentario_feed
    ADD CONSTRAINT cens_comentario_feed_pkey PRIMARY KEY (id);


--
-- Name: cens_comentario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_comentario
    ADD CONSTRAINT cens_comentario_pkey PRIMARY KEY (id);


--
-- Name: cens_contacto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_contacto
    ADD CONSTRAINT cens_contacto_pkey PRIMARY KEY (id);


--
-- Name: cens_contacto_revision_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_contacto_revision
    ADD CONSTRAINT cens_contacto_revision_pkey PRIMARY KEY (id);


--
-- Name: cens_curso_cens_profesor_profesores_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_curso_cens_profesor
    ADD CONSTRAINT cens_curso_cens_profesor_profesores_id_key UNIQUE (profesores_id);


--
-- Name: cens_curso_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_curso
    ADD CONSTRAINT cens_curso_pkey PRIMARY KEY (id);


--
-- Name: cens_estado_feed_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_estado_feed
    ADD CONSTRAINT cens_estado_feed_pkey PRIMARY KEY (id);


--
-- Name: cens_file_info_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_file_info
    ADD CONSTRAINT cens_file_info_pkey PRIMARY KEY (id);


--
-- Name: cens_material_didactico_evaluado_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_material_didactico_evaluado
    ADD CONSTRAINT cens_material_didactico_evaluado_pkey PRIMARY KEY (id);


--
-- Name: cens_material_didactico_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_material_didactico
    ADD CONSTRAINT cens_material_didactico_pkey PRIMARY KEY (id);


--
-- Name: cens_material_didactico_profesor_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_material_didactico
    ADD CONSTRAINT cens_material_didactico_profesor_id_key UNIQUE (profesor_id);


--
-- Name: cens_material_didactico_programa_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_material_didactico
    ADD CONSTRAINT cens_material_didactico_programa_id_key UNIQUE (programa_id);


--
-- Name: cens_miembros_cens_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_miembros_cens
    ADD CONSTRAINT cens_miembros_cens_pkey PRIMARY KEY (id);


--
-- Name: cens_perfil_usuario_cens_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_perfil_usuario_cens
    ADD CONSTRAINT cens_perfil_usuario_cens_pkey PRIMARY KEY (id);


--
-- Name: cens_preceptor_miembrocens_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_preceptor
    ADD CONSTRAINT cens_preceptor_miembrocens_id_key UNIQUE (miembrocens_id);


--
-- Name: cens_preceptor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_preceptor
    ADD CONSTRAINT cens_preceptor_pkey PRIMARY KEY (id);


--
-- Name: cens_profesor_miembrocens_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_profesor
    ADD CONSTRAINT cens_profesor_miembrocens_id_key UNIQUE (miembrocens_id);


--
-- Name: cens_profesor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_profesor
    ADD CONSTRAINT cens_profesor_pkey PRIMARY KEY (id);


--
-- Name: cens_programa_comentario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_programa_comentario
    ADD CONSTRAINT cens_programa_comentario_pkey PRIMARY KEY (id);


--
-- Name: cens_programa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_programa
    ADD CONSTRAINT cens_programa_pkey PRIMARY KEY (id);


--
-- Name: cens_revision_material_didactico_detalle_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_revision_material_didactico_detalle
    ADD CONSTRAINT cens_revision_material_didactico_detalle_pkey PRIMARY KEY (id);


--
-- Name: cens_revision_material_didactico_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_revision_material_didactico
    ADD CONSTRAINT cens_revision_material_didactico_pkey PRIMARY KEY (id);


--
-- Name: cens_usuarios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_usuarios
    ADD CONSTRAINT cens_usuarios_pkey PRIMARY KEY (id);


--
-- Name: cliente_beneficiocliente_beneficios_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cliente_beneficiocliente
    ADD CONSTRAINT cliente_beneficiocliente_beneficios_id_key UNIQUE (beneficios_id);


--
-- Name: cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (id);


--
-- Name: empleado_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY empleado
    ADD CONSTRAINT empleado_pkey PRIMARY KEY (id);


--
-- Name: empleado_relacionlaboral_relacionlaboral_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY empleado_relacionlaboral
    ADD CONSTRAINT empleado_relacionlaboral_relacionlaboral_id_key UNIQUE (relacionlaboral_id);


--
-- Name: empleado_relacionpuestoempleado_puestos_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY empleado_relacionpuestoempleado
    ADD CONSTRAINT empleado_relacionpuestoempleado_puestos_id_key UNIQUE (puestos_id);


--
-- Name: empleado_sueldo_sueldo_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY empleado_sueldo
    ADD CONSTRAINT empleado_sueldo_sueldo_id_key UNIQUE (sueldo_id);


--
-- Name: empleado_vacaciones_vacaciones_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY empleado_vacaciones
    ADD CONSTRAINT empleado_vacaciones_vacaciones_id_key UNIQUE (vacaciones_id);


--
-- Name: empleadoviejofecha_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY empleadoviejofecha
    ADD CONSTRAINT empleadoviejofecha_pkey PRIMARY KEY (id);


--
-- Name: informeconsolidado_informeconsolidadodetalle_detalle_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY informeconsolidado_informeconsolidadodetalle
    ADD CONSTRAINT informeconsolidado_informeconsolidadodetalle_detalle_id_key UNIQUE (detalle_id);


--
-- Name: informeconsolidado_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY informeconsolidado
    ADD CONSTRAINT informeconsolidado_pkey PRIMARY KEY (id);


--
-- Name: informeconsolidadodetalle_informeconsolidadod_beneficios_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY informeconsolidadodetalle_informeconsolidadodetallebeneficio
    ADD CONSTRAINT informeconsolidadodetalle_informeconsolidadod_beneficios_id_key UNIQUE (beneficios_id);


--
-- Name: informeconsolidadodetalle_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY informeconsolidadodetalle
    ADD CONSTRAINT informeconsolidadodetalle_pkey PRIMARY KEY (id);


--
-- Name: informeconsolidadodetallebeneficio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY informeconsolidadodetallebeneficio
    ADD CONSTRAINT informeconsolidadodetallebeneficio_pkey PRIMARY KEY (id);


--
-- Name: informemensual_informemensualdetalle_detalle_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY informemensual_informemensualdetalle
    ADD CONSTRAINT informemensual_informemensualdetalle_detalle_id_key UNIQUE (detalle_id);


--
-- Name: informemensual_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY informemensual
    ADD CONSTRAINT informemensual_pkey PRIMARY KEY (id);


--
-- Name: informemensualdetalle_informemensualdetallebe_beneficios_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY informemensualdetalle_informemensualdetallebeneficio
    ADD CONSTRAINT informemensualdetalle_informemensualdetallebe_beneficios_id_key UNIQUE (beneficios_id);


--
-- Name: informemensualdetalle_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY informemensualdetalle
    ADD CONSTRAINT informemensualdetalle_pkey PRIMARY KEY (id);


--
-- Name: informemensualdetallebeneficio_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY informemensualdetallebeneficio
    ADD CONSTRAINT informemensualdetallebeneficio_pkey PRIMARY KEY (id);


--
-- Name: motivobaja_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY motivobaja
    ADD CONSTRAINT motivobaja_pkey PRIMARY KEY (id);


--
-- Name: nacionalidad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY nacionalidad
    ADD CONSTRAINT nacionalidad_pkey PRIMARY KEY (id);


--
-- Name: obrasocial_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY obrasocial
    ADD CONSTRAINT obrasocial_pkey PRIMARY KEY (id);


--
-- Name: perfil_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY perfil
    ADD CONSTRAINT perfil_pkey PRIMARY KEY (id);


--
-- Name: prepaga_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY prepaga
    ADD CONSTRAINT prepaga_pkey PRIMARY KEY (id);


--
-- Name: puesto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY puesto
    ADD CONSTRAINT puesto_pkey PRIMARY KEY (id);


--
-- Name: relacionlaboral_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY relacionlaboral
    ADD CONSTRAINT relacionlaboral_pkey PRIMARY KEY (id);


--
-- Name: relacionpuestoempleado_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY relacionpuestoempleado
    ADD CONSTRAINT relacionpuestoempleado_pkey PRIMARY KEY (id);


--
-- Name: sueldo_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY sueldo
    ADD CONSTRAINT sueldo_pkey PRIMARY KEY (id);


--
-- Name: uk_9pyeibjaw3cbl8vimdkdxpc0k; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_alumno_cens_asignatura
    ADD CONSTRAINT uk_9pyeibjaw3cbl8vimdkdxpc0k UNIQUE (asignaturas_id);


--
-- Name: uk_9tyd7by15kq8mbwoa5xvqir0u; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_profesor
    ADD CONSTRAINT uk_9tyd7by15kq8mbwoa5xvqir0u UNIQUE (miembrocens_id);


--
-- Name: uk_hed8rrhu20bo0yb8ungid7xnm; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_alumno
    ADD CONSTRAINT uk_hed8rrhu20bo0yb8ungid7xnm UNIQUE (miembrocens_id);


--
-- Name: uk_o6cgel3ede2numvw212e1w67n; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_asesor
    ADD CONSTRAINT uk_o6cgel3ede2numvw212e1w67n UNIQUE (miembrocens_id);


--
-- Name: uk_sg8701t9mde1ioovgki7nxug8; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cens_preceptor
    ADD CONSTRAINT uk_sg8701t9mde1ioovgki7nxug8 UNIQUE (miembrocens_id);


--
-- Name: usuario_perfil_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuario_perfil
    ADD CONSTRAINT usuario_perfil_pkey PRIMARY KEY (id);


--
-- Name: usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- Name: vacaciones_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY vacaciones
    ADD CONSTRAINT vacaciones_pkey PRIMARY KEY (id);


--
-- Name: fk15973116af076ddb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_revision_material_didactico
    ADD CONSTRAINT fk15973116af076ddb FOREIGN KEY (profesor_id) REFERENCES cens_profesor(id);


--
-- Name: fk22e2ebe7a54fda99; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_contacto
    ADD CONSTRAINT fk22e2ebe7a54fda99 FOREIGN KEY (miembrocens_id) REFERENCES cens_miembros_cens(id);


--
-- Name: fk297f57ab96488e4e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY informemensualdetalle_informemensualdetallebeneficio
    ADD CONSTRAINT fk297f57ab96488e4e FOREIGN KEY (informemensualdetalle_id) REFERENCES informemensualdetalle(id);


--
-- Name: fk297f57abf52c943b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY informemensualdetalle_informemensualdetallebeneficio
    ADD CONSTRAINT fk297f57abf52c943b FOREIGN KEY (beneficios_id) REFERENCES informemensualdetallebeneficio(id);


--
-- Name: fk334b85fa3cc7b09e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cliente
    ADD CONSTRAINT fk334b85fa3cc7b09e FOREIGN KEY (jefeoperacion_id) REFERENCES usuario(id);


--
-- Name: fk334b85fa90868592; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cliente
    ADD CONSTRAINT fk334b85fa90868592 FOREIGN KEY (gerenteoperacion_id) REFERENCES usuario(id);


--
-- Name: fk471e00cb3161766e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY empleado
    ADD CONSTRAINT fk471e00cb3161766e FOREIGN KEY (banco_id) REFERENCES banco(id);


--
-- Name: fk471e00cb4eea2106; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY empleado
    ADD CONSTRAINT fk471e00cb4eea2106 FOREIGN KEY (nacionalidad_id) REFERENCES nacionalidad(id);


--
-- Name: fk471e00cb85dd588e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY empleado
    ADD CONSTRAINT fk471e00cb85dd588e FOREIGN KEY (prepaga_id) REFERENCES prepaga(id);


--
-- Name: fk471e00cbd2319566; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY empleado
    ADD CONSTRAINT fk471e00cbd2319566 FOREIGN KEY (obrasocial_id) REFERENCES obrasocial(id);


--
-- Name: fk471e00cbf1857206; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY empleado
    ADD CONSTRAINT fk471e00cbf1857206 FOREIGN KEY (motivobaja_id) REFERENCES motivobaja(id);


--
-- Name: fk4f3efe9df3bec5c2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_usuarios
    ADD CONSTRAINT fk4f3efe9df3bec5c2 FOREIGN KEY (fileinfo_id) REFERENCES cens_file_info(id);


--
-- Name: fk4ffcd49a26e6bec6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY empleado_relacionlaboral
    ADD CONSTRAINT fk4ffcd49a26e6bec6 FOREIGN KEY (empleado_id) REFERENCES empleado(id);


--
-- Name: fk4ffcd49a549bc06e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY empleado_relacionlaboral
    ADD CONSTRAINT fk4ffcd49a549bc06e FOREIGN KEY (relacionlaboral_id) REFERENCES relacionlaboral(id);


--
-- Name: fk54041c6b1395077b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_programa_comentario
    ADD CONSTRAINT fk54041c6b1395077b FOREIGN KEY (asesor_id) REFERENCES cens_asesor(id);


--
-- Name: fk54041c6b5485403b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_programa_comentario
    ADD CONSTRAINT fk54041c6b5485403b FOREIGN KEY (filecensinfo_id) REFERENCES cens_file_info(id);


--
-- Name: fk54041c6ba7d234fb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_programa_comentario
    ADD CONSTRAINT fk54041c6ba7d234fb FOREIGN KEY (programa_id) REFERENCES cens_programa(id);


--
-- Name: fk54041c6baf076ddb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_programa_comentario
    ADD CONSTRAINT fk54041c6baf076ddb FOREIGN KEY (profesor_id) REFERENCES cens_profesor(id);


--
-- Name: fk57cd23fd6c5a288e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuario_perfil
    ADD CONSTRAINT fk57cd23fd6c5a288e FOREIGN KEY (usuario_id) REFERENCES usuario(id);


--
-- Name: fk57cd23fda696de26; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuario_perfil
    ADD CONSTRAINT fk57cd23fda696de26 FOREIGN KEY (perfil_id) REFERENCES perfil(id);


--
-- Name: fk590a740e26e6bec6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY relacionlaboral
    ADD CONSTRAINT fk590a740e26e6bec6 FOREIGN KEY (empleado_id) REFERENCES empleado(id);


--
-- Name: fk590a740e5b55a50e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY relacionlaboral
    ADD CONSTRAINT fk590a740e5b55a50e FOREIGN KEY (cliente_id) REFERENCES cliente(id);


--
-- Name: fk590a740e8c9fcae6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY relacionlaboral
    ADD CONSTRAINT fk590a740e8c9fcae6 FOREIGN KEY (puesto_id) REFERENCES puesto(id);


--
-- Name: fk6de581333fbdb732; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_perfil_usuario_cens
    ADD CONSTRAINT fk6de581333fbdb732 FOREIGN KEY (usuario_id) REFERENCES cens_usuarios(id);


--
-- Name: fk6eb9f5ab2e5554fb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY informeconsolidadodetalle_informeconsolidadodetallebeneficio
    ADD CONSTRAINT fk6eb9f5ab2e5554fb FOREIGN KEY (beneficios_id) REFERENCES informeconsolidadodetallebeneficio(id);


--
-- Name: fk6eb9f5ab57738c4e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY informeconsolidadodetalle_informeconsolidadodetallebeneficio
    ADD CONSTRAINT fk6eb9f5ab57738c4e FOREIGN KEY (informeconsolidadodetalle_id) REFERENCES informeconsolidadodetalle(id);


--
-- Name: fk734cbe62a7d234fb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_material_didactico
    ADD CONSTRAINT fk734cbe62a7d234fb FOREIGN KEY (programa_id) REFERENCES cens_programa(id);


--
-- Name: fk734cbe62af076ddb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_material_didactico
    ADD CONSTRAINT fk734cbe62af076ddb FOREIGN KEY (profesor_id) REFERENCES cens_profesor(id);


--
-- Name: fk734cbe62f3bec5c2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_material_didactico
    ADD CONSTRAINT fk734cbe62f3bec5c2 FOREIGN KEY (fileinfo_id) REFERENCES cens_file_info(id);


--
-- Name: fk79ebeb2e1395077b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_revision_material_didactico_detalle
    ADD CONSTRAINT fk79ebeb2e1395077b FOREIGN KEY (asesor_id) REFERENCES cens_asesor(id);


--
-- Name: fk79ebeb2e71739b39; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_revision_material_didactico_detalle
    ADD CONSTRAINT fk79ebeb2e71739b39 FOREIGN KEY (revisionmaterialdidactico_id) REFERENCES cens_revision_material_didactico(id);


--
-- Name: fk79ebeb2efc1218fb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_revision_material_didactico_detalle
    ADD CONSTRAINT fk79ebeb2efc1218fb FOREIGN KEY (contactorevision_id) REFERENCES cens_contacto_revision(id);


--
-- Name: fk7e95189c9cd20ace; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY informeconsolidadodetallebeneficio
    ADD CONSTRAINT fk7e95189c9cd20ace FOREIGN KEY (beneficio_id) REFERENCES beneficio(id);


--
-- Name: fk8c6a1158d7409426; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY informemensual_informemensualdetalle
    ADD CONSTRAINT fk8c6a1158d7409426 FOREIGN KEY (informemensual_id) REFERENCES informemensual(id);


--
-- Name: fk8c6a1158dd18abc7; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY informemensual_informemensualdetalle
    ADD CONSTRAINT fk8c6a1158dd18abc7 FOREIGN KEY (detalle_id) REFERENCES informemensualdetalle(id);


--
-- Name: fk8f68182a54fda99; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_profesor
    ADD CONSTRAINT fk8f68182a54fda99 FOREIGN KEY (miembrocens_id) REFERENCES cens_miembros_cens(id);


--
-- Name: fk90a3df59f03c0fb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_programa
    ADD CONSTRAINT fk90a3df59f03c0fb FOREIGN KEY (asignatura_id) REFERENCES cens_asignatura(id);


--
-- Name: fk90a3df5af076ddb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_programa
    ADD CONSTRAINT fk90a3df5af076ddb FOREIGN KEY (profesor_id) REFERENCES cens_profesor(id);


--
-- Name: fk90a3df5f3bec5c2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_programa
    ADD CONSTRAINT fk90a3df5f3bec5c2 FOREIGN KEY (fileinfo_id) REFERENCES cens_file_info(id);


--
-- Name: fk9b455c1126e6bec6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY empleadoviejofecha
    ADD CONSTRAINT fk9b455c1126e6bec6 FOREIGN KEY (empleado_id) REFERENCES empleado(id);


--
-- Name: fk9b455c11f413137c; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY empleadoviejofecha
    ADD CONSTRAINT fk9b455c11f413137c FOREIGN KEY (motivo_id) REFERENCES motivobaja(id);


--
-- Name: fk9d61844e5b55a50e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY beneficiocliente
    ADD CONSTRAINT fk9d61844e5b55a50e FOREIGN KEY (cliente_id) REFERENCES cliente(id);


--
-- Name: fk9d61844e9cd20ace; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY beneficiocliente
    ADD CONSTRAINT fk9d61844e9cd20ace FOREIGN KEY (beneficio_id) REFERENCES beneficio(id);


--
-- Name: fka0e18867517711; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_curso_cens_profesor
    ADD CONSTRAINT fka0e18867517711 FOREIGN KEY (cens_curso_id) REFERENCES cens_curso(id);


--
-- Name: fka0e18867f79fdced; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_curso_cens_profesor
    ADD CONSTRAINT fka0e18867f79fdced FOREIGN KEY (profesores_id) REFERENCES cens_profesor(id);


--
-- Name: fkac60f5791395077b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_comentario
    ADD CONSTRAINT fkac60f5791395077b FOREIGN KEY (asesor_id) REFERENCES cens_asesor(id);


--
-- Name: fkac60f5793c66ed99; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_comentario
    ADD CONSTRAINT fkac60f5793c66ed99 FOREIGN KEY (parent_id) REFERENCES cens_comentario(id);


--
-- Name: fkac60f5795485403b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_comentario
    ADD CONSTRAINT fkac60f5795485403b FOREIGN KEY (filecensinfo_id) REFERENCES cens_file_info(id);


--
-- Name: fkac60f579a8921c24; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_comentario
    ADD CONSTRAINT fkac60f579a8921c24 FOREIGN KEY (children_id) REFERENCES cens_comentario(id);


--
-- Name: fkac60f579af076ddb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_comentario
    ADD CONSTRAINT fkac60f579af076ddb FOREIGN KEY (profesor_id) REFERENCES cens_profesor(id);


--
-- Name: fkae0489bc26e6bec6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY vacaciones
    ADD CONSTRAINT fkae0489bc26e6bec6 FOREIGN KEY (empleado_id) REFERENCES empleado(id);


--
-- Name: fkb26eae6549bc06e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY relacionpuestoempleado
    ADD CONSTRAINT fkb26eae6549bc06e FOREIGN KEY (relacionlaboral_id) REFERENCES relacionlaboral(id);


--
-- Name: fkb26eae68c9fcae6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY relacionpuestoempleado
    ADD CONSTRAINT fkb26eae68c9fcae6 FOREIGN KEY (puesto_id) REFERENCES puesto(id);


--
-- Name: fkb970d3f35b55a50e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cliente_beneficiocliente
    ADD CONSTRAINT fkb970d3f35b55a50e FOREIGN KEY (cliente_id) REFERENCES cliente(id);


--
-- Name: fkb970d3f3db59efad; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cliente_beneficiocliente
    ADD CONSTRAINT fkb970d3f3db59efad FOREIGN KEY (beneficios_id) REFERENCES beneficiocliente(id);


--
-- Name: fkbbea4558d5575626; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY informeconsolidado_informeconsolidadodetalle
    ADD CONSTRAINT fkbbea4558d5575626 FOREIGN KEY (informeconsolidado_id) REFERENCES informeconsolidado(id);


--
-- Name: fkbbea4558dcb01307; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY informeconsolidado_informeconsolidadodetalle
    ADD CONSTRAINT fkbbea4558dcb01307 FOREIGN KEY (detalle_id) REFERENCES informeconsolidadodetalle(id);


--
-- Name: fkbd52770b653abb23; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_asignatura
    ADD CONSTRAINT fkbd52770b653abb23 FOREIGN KEY (profesorsuplente_id) REFERENCES cens_profesor(id);


--
-- Name: fkbd52770baf076ddb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_asignatura
    ADD CONSTRAINT fkbd52770baf076ddb FOREIGN KEY (profesor_id) REFERENCES cens_profesor(id);


--
-- Name: fkbd52770bfde7e379; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_asignatura
    ADD CONSTRAINT fkbd52770bfde7e379 FOREIGN KEY (curso_id) REFERENCES cens_curso(id);


--
-- Name: fkbf336a9c8da8d9ee; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_alumno
    ADD CONSTRAINT fkbf336a9c8da8d9ee FOREIGN KEY (asignaturas_id) REFERENCES cens_asignatura(id);


--
-- Name: fkbf336a9ca54fda99; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_alumno
    ADD CONSTRAINT fkbf336a9ca54fda99 FOREIGN KEY (miembrocens_id) REFERENCES cens_miembros_cens(id);


--
-- Name: fkbf8edfdba54fda99; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_asesor
    ADD CONSTRAINT fkbf8edfdba54fda99 FOREIGN KEY (miembrocens_id) REFERENCES cens_miembros_cens(id);


--
-- Name: fkbf8edfdbaf076ddb; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_asesor
    ADD CONSTRAINT fkbf8edfdbaf076ddb FOREIGN KEY (profesor_id) REFERENCES cens_profesor(id);


--
-- Name: fkc2e301b026e6bec6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY empleado_vacaciones
    ADD CONSTRAINT fkc2e301b026e6bec6 FOREIGN KEY (empleado_id) REFERENCES empleado(id);


--
-- Name: fkc2e301b08539c226; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY empleado_vacaciones
    ADD CONSTRAINT fkc2e301b08539c226 FOREIGN KEY (vacaciones_id) REFERENCES vacaciones(id);


--
-- Name: fkc3448050549bc06e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY informemensualdetalle
    ADD CONSTRAINT fkc3448050549bc06e FOREIGN KEY (relacionlaboral_id) REFERENCES relacionlaboral(id);


--
-- Name: fkc5f61bf018f6b479; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_material_didactico_evaluado
    ADD CONSTRAINT fkc5f61bf018f6b479 FOREIGN KEY (materialdidactico_id) REFERENCES cens_material_didactico(id);


--
-- Name: fkc5f61bf0802883db; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_material_didactico_evaluado
    ADD CONSTRAINT fkc5f61bf0802883db FOREIGN KEY (alumno_id) REFERENCES cens_alumno(id);


--
-- Name: fkcadd98d426e6bec6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY sueldo
    ADD CONSTRAINT fkcadd98d426e6bec6 FOREIGN KEY (empleado_id) REFERENCES empleado(id);


--
-- Name: fkce73f1763fbdb732; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_miembros_cens
    ADD CONSTRAINT fkce73f1763fbdb732 FOREIGN KEY (usuario_id) REFERENCES cens_usuarios(id);


--
-- Name: fkce76619026e6bec6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY informeconsolidadodetalle
    ADD CONSTRAINT fkce76619026e6bec6 FOREIGN KEY (empleado_id) REFERENCES empleado(id);


--
-- Name: fkce76619096488e4e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY informeconsolidadodetalle
    ADD CONSTRAINT fkce76619096488e4e FOREIGN KEY (informemensualdetalle_id) REFERENCES informemensualdetalle(id);


--
-- Name: fkde890f475b55a50e; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY informemensual
    ADD CONSTRAINT fkde890f475b55a50e FOREIGN KEY (cliente_id) REFERENCES cliente(id);


--
-- Name: fke3dd91dc37912f28; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY informemensualdetallebeneficio
    ADD CONSTRAINT fke3dd91dc37912f28 FOREIGN KEY (beneficio_id) REFERENCES beneficiocliente(id);


--
-- Name: fke6fe2ac826e6bec6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY empleado_sueldo
    ADD CONSTRAINT fke6fe2ac826e6bec6 FOREIGN KEY (empleado_id) REFERENCES empleado(id);


--
-- Name: fke6fe2ac841b82126; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY empleado_sueldo
    ADD CONSTRAINT fke6fe2ac841b82126 FOREIGN KEY (sueldo_id) REFERENCES sueldo(id);


--
-- Name: fke9167f688da8d9ee; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_alumno_cens_asignatura
    ADD CONSTRAINT fke9167f688da8d9ee FOREIGN KEY (asignaturas_id) REFERENCES cens_asignatura(id);


--
-- Name: fke9167f68caf16343; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_alumno_cens_asignatura
    ADD CONSTRAINT fke9167f68caf16343 FOREIGN KEY (cens_alumno_id) REFERENCES cens_alumno(id);


--
-- Name: fkf6d52ca44e5f187b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_comentario_feed
    ADD CONSTRAINT fkf6d52ca44e5f187b FOREIGN KEY (comentariocens_id) REFERENCES cens_comentario(id);


--
-- Name: fkffbb89f4a54fda99; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cens_preceptor
    ADD CONSTRAINT fkffbb89f4a54fda99 FOREIGN KEY (miembrocens_id) REFERENCES cens_miembros_cens(id);


--
-- Name: fkffed14da26e6bec6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY empleado_relacionpuestoempleado
    ADD CONSTRAINT fkffed14da26e6bec6 FOREIGN KEY (empleado_id) REFERENCES empleado(id);


--
-- Name: fkffed14da35afafa7; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY empleado_relacionpuestoempleado
    ADD CONSTRAINT fkffed14da35afafa7 FOREIGN KEY (puestos_id) REFERENCES relacionpuestoempleado(id);


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

