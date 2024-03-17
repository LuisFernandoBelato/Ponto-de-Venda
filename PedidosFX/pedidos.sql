--
-- PostgreSQL database dump
--

-- Dumped from database version 14.4
-- Dumped by pg_dump version 14.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: categorias; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categorias (
    cat_id integer NOT NULL,
    cat_nome character varying(20),
    cat_desc text
);


ALTER TABLE public.categorias OWNER TO postgres;

--
-- Name: categorias_cat_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.categorias_cat_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.categorias_cat_id_seq OWNER TO postgres;

--
-- Name: categorias_cat_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categorias_cat_id_seq OWNED BY public.categorias.cat_id;


--
-- Name: clientes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.clientes (
    cli_id integer NOT NULL,
    cli_documento numeric(14,0),
    cli_nome character varying(40),
    cli_endereco character varying(80),
    cli_bairro character varying(30),
    cli_cidade character varying(25),
    cli_cep character varying(9),
    cli_uf character(2),
    cli_email character varying(30)
);


ALTER TABLE public.clientes OWNER TO postgres;

--
-- Name: clientes_cli_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.clientes_cli_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.clientes_cli_id_seq OWNER TO postgres;

--
-- Name: clientes_cli_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.clientes_cli_id_seq OWNED BY public.clientes.cli_id;


--
-- Name: itens_pedidos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.itens_pedidos (
    itp_id integer NOT NULL,
    itp_quant integer,
    itp_preco numeric(10,2),
    pro_id integer,
    ped_id integer
);


ALTER TABLE public.itens_pedidos OWNER TO postgres;

--
-- Name: itens_pedidos_itp_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.itens_pedidos_itp_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.itens_pedidos_itp_id_seq OWNER TO postgres;

--
-- Name: itens_pedidos_itp_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.itens_pedidos_itp_id_seq OWNED BY public.itens_pedidos.itp_id;


--
-- Name: pedidos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pedidos (
    ped_id integer NOT NULL,
    ped_data date,
    ped_frete numeric(10,2),
    ped_total numeric(10,2),
    cli_id integer
);


ALTER TABLE public.pedidos OWNER TO postgres;

--
-- Name: pedidos_pro_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pedidos_pro_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pedidos_pro_id_seq OWNER TO postgres;

--
-- Name: pedidos_pro_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pedidos_pro_id_seq OWNED BY public.pedidos.ped_id;


--
-- Name: produtos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.produtos (
    pro_id integer NOT NULL,
    pro_nome character varying(30),
    pro_preco numeric(10,2),
    pro_estoque numeric(8,1),
    cat_id integer
);


ALTER TABLE public.produtos OWNER TO postgres;

--
-- Name: produtos_pro_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.produtos_pro_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.produtos_pro_id_seq OWNER TO postgres;

--
-- Name: produtos_pro_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.produtos_pro_id_seq OWNED BY public.produtos.pro_id;


--
-- Name: categorias cat_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categorias ALTER COLUMN cat_id SET DEFAULT nextval('public.categorias_cat_id_seq'::regclass);


--
-- Name: clientes cli_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clientes ALTER COLUMN cli_id SET DEFAULT nextval('public.clientes_cli_id_seq'::regclass);


--
-- Name: itens_pedidos itp_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itens_pedidos ALTER COLUMN itp_id SET DEFAULT nextval('public.itens_pedidos_itp_id_seq'::regclass);


--
-- Name: pedidos ped_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedidos ALTER COLUMN ped_id SET DEFAULT nextval('public.pedidos_pro_id_seq'::regclass);


--
-- Name: produtos pro_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produtos ALTER COLUMN pro_id SET DEFAULT nextval('public.produtos_pro_id_seq'::regclass);


--
-- Data for Name: categorias; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.categorias VALUES (1, 'hortifruti', 'frutas, verduras, legumes');
INSERT INTO public.categorias VALUES (2, 'padaria', 'pães, doces, bolos e afins');
INSERT INTO public.categorias VALUES (3, 'rotisseria', 'pratos prontos, assados e fritos');
INSERT INTO public.categorias VALUES (4, 'bebidas', 'bebidas alcoolicas e não alcoolicas');
INSERT INTO public.categorias VALUES (5, 'frios', 'queijos e embutidos em geral');


--
-- Data for Name: clientes; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.clientes VALUES (1, 12155569344, 'Cleuzo Homero Reis', 'Rua Berimbau, 150', 'Vila Nivea', 'Tangará', '12160000', 'SP', 'cl@email.com.br');
INSERT INTO public.clientes VALUES (2, 54473111000155, 'Marta brinquedos', 'Av Brasil, 120', 'Jardim Sumare', 'Presidente Prudente', '19500160', 'SP', 'brink@gmail.com');


--
-- Data for Name: itens_pedidos; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: pedidos; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- Data for Name: produtos; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.produtos VALUES (1, 'mussarela', 45.00, 100.0, 5);
INSERT INTO public.produtos VALUES (2, 'presunto', 38.00, 10.0, 5);
INSERT INTO public.produtos VALUES (3, 'Coca Zero lata 330ml', 3.50, 50.0, 4);
INSERT INTO public.produtos VALUES (4, 'heineken long neck', 6.85, 45.0, 4);
INSERT INTO public.produtos VALUES (5, 'einsenbanh garrafa 600ml', 8.00, 5.0, 4);
INSERT INTO public.produtos VALUES (6, 'frango assado', 38.00, 12.0, 3);
INSERT INTO public.produtos VALUES (7, 'pão francês', 12.50, 1000.0, 2);
INSERT INTO public.produtos VALUES (8, 'broa milho', 29.00, 1000.0, 2);
INSERT INTO public.produtos VALUES (9, 'tomate', 9.00, 50.0, 1);
INSERT INTO public.produtos VALUES (10, 'alface americana', 4.35, 50.0, 1);
INSERT INTO public.produtos VALUES (11, 'cebla roxa', 9.89, 100.0, 1);
INSERT INTO public.produtos VALUES (12, 'banana prata', 9.55, 10.0, 1);


--
-- Name: categorias_cat_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categorias_cat_id_seq', 5, true);


--
-- Name: clientes_cli_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.clientes_cli_id_seq', 2, true);


--
-- Name: itens_pedidos_itp_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.itens_pedidos_itp_id_seq', 1, false);


--
-- Name: pedidos_pro_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pedidos_pro_id_seq', 1, false);


--
-- Name: produtos_pro_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.produtos_pro_id_seq', 12, true);


--
-- Name: categorias categorias_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categorias
    ADD CONSTRAINT categorias_pkey PRIMARY KEY (cat_id);


--
-- Name: clientes clientes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.clientes
    ADD CONSTRAINT clientes_pkey PRIMARY KEY (cli_id);


--
-- Name: itens_pedidos itens_pedidos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itens_pedidos
    ADD CONSTRAINT itens_pedidos_pkey PRIMARY KEY (itp_id);


--
-- Name: pedidos pedidos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedidos
    ADD CONSTRAINT pedidos_pkey PRIMARY KEY (ped_id);


--
-- Name: produtos produtos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produtos
    ADD CONSTRAINT produtos_pkey PRIMARY KEY (pro_id);


--
-- Name: itens_pedidos itens_pedidos_ped_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itens_pedidos
    ADD CONSTRAINT itens_pedidos_ped_id_fkey FOREIGN KEY (ped_id) REFERENCES public.pedidos(ped_id) NOT VALID;


--
-- Name: itens_pedidos itens_pedidos_pro_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.itens_pedidos
    ADD CONSTRAINT itens_pedidos_pro_id_fkey FOREIGN KEY (pro_id) REFERENCES public.produtos(pro_id) NOT VALID;


--
-- Name: pedidos pedidos_cli_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedidos
    ADD CONSTRAINT pedidos_cli_id_fkey FOREIGN KEY (cli_id) REFERENCES public.clientes(cli_id) NOT VALID;


--
-- Name: produtos produtos_cat_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produtos
    ADD CONSTRAINT produtos_cat_id_fkey FOREIGN KEY (cat_id) REFERENCES public.categorias(cat_id);


--
-- PostgreSQL database dump complete
--

