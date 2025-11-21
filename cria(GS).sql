-- Gerado por Oracle SQL Developer Data Modeler 19.4.0.350.1424
--   em:        2025-11-19 00:00:41 BRST
--   site:      Oracle Database 11g
--   tipo:      Oracle Database 11g





CREATE TABLE t_equilibrium_depto (
    id_depto  NUMBER(9) NOT NULL,
    nm_depto  VARCHAR2(20) NOT NULL
);

ALTER TABLE t_equilibrium_depto
    ADD CONSTRAINT ck_equilibrium_nome_depto CHECK ( nm_depto IN (
        'Engineering',
        'HR',
        'IT',
        'Marketing',
        'Sales',
        'Support'
    ) );

COMMENT ON COLUMN t_equilibrium_depto.id_depto IS
    'Esse atributo irá receber a chave primária do nome do departamento. Esse número é sequencial  e é gerado automaticamente pelo sistema. Seu conteúdo é obrigatório.';

COMMENT ON COLUMN t_equilibrium_depto.nm_depto IS
    'Este atributo irá receber o nome do departamento. Seu conteudo é obrigatório e terá como opções: HR, Sales, Marketing, Engineering, IT, Support.';

ALTER TABLE t_equilibrium_depto ADD CONSTRAINT pk_equilibrium_depto PRIMARY KEY ( id_depto );

CREATE TABLE t_equilibrium_email (
    id_func   NUMBER(9) NOT NULL,
    id_email  NUMBER(9) NOT NULL,
    ds_email  VARCHAR2(100) NOT NULL,
    st_email  CHAR(1) NOT NULL
);

ALTER TABLE t_equilibrium_email
    ADD CONSTRAINT ck_equilibrium_st_email_func CHECK ( st_email IN (
        'A',
        'I'
    ) );

COMMENT ON COLUMN t_equilibrium_email.id_email IS
    'Esse atributo irá receber a chave primária do email do paciente. Esse número é sequencial e gerado automaticamente pelo sistema. Seu conteúdo é obrigatório.';

COMMENT ON COLUMN t_equilibrium_email.ds_email IS
    'Esse atributo irá receber o email do paciente.  Seu conteúdo é obrigatório.';

COMMENT ON COLUMN t_equilibrium_email.st_email IS
    'Esse atributo irá receber o status do email do paciente.  Seu conteúdo é obrigatório e deve possuir os seguintes valores: (A)tivo ou (I)nativo.';

ALTER TABLE t_equilibrium_email ADD CONSTRAINT pk_equilibrium_email_func PRIMARY KEY ( id_email,
                                                                                       id_func );

CREATE TABLE t_equilibrium_funcionario (
    id_func                 NUMBER(9) NOT NULL,
    id_depto                NUMBER(9) NOT NULL,
    nm_func                 VARCHAR2(80) NOT NULL,
    nr_cpf                  VARCHAR2(11) NOT NULL,
    nr_idade                NUMBER(3) NOT NULL,
    fl_gender               VARCHAR2(20) NOT NULL,
    ds_job_role             VARCHAR2(40) NOT NULL,
    nr_years_at_company     NUMBER(3) NOT NULL,
    nr_work_hours_per_week  NUMBER(2) NOT NULL,
    ds_remote_work          VARCHAR2(8) NOT NULL
);

ALTER TABLE t_equilibrium_funcionario
    ADD CONSTRAINT ck_equilibrium_usuario_gender CHECK ( fl_gender IN (
        'Female',
        'Male',
        'Non-binary',
        'Prefer not to say'
    ) );

ALTER TABLE t_equilibrium_funcionario
    ADD CONSTRAINT ck_equilibrium_usuario_work CHECK ( ds_remote_work IN (
        'Hybrid',
        'No',
        'Yes'
    ) );

COMMENT ON COLUMN t_equilibrium_funcionario.id_func IS
    'Esse atributo irá receber a chave primária do paciente. Esse número é sequencial e gerado automaticamente pelo sistema. Seu conteúdo é obrigatório.';

COMMENT ON COLUMN t_equilibrium_funcionario.nm_func IS
    'Esse atributo irá receber o nome completo do  paciente.  Seu conteúdo é obrigatório.';

COMMENT ON COLUMN t_equilibrium_funcionario.nr_cpf IS
    'Esse atributo irá receber o número do  CPF paciente.  Seu conteúdo é obrigatório e será validado de acordo com regras de negócio.';

COMMENT ON COLUMN t_equilibrium_funcionario.nr_idade IS
    'Esse atributo irá receber a data de nascimento do  paciente.  Seu conteúdo é obrigatório.';

COMMENT ON COLUMN t_equilibrium_funcionario.fl_gender IS
    'Esse atributo irá receber a flag do sexo biológico de nascimento do Paciente. Os valores possíveis são (F)emea  ou (M)acho. Seu conteúdo é obrigatório.';

COMMENT ON COLUMN t_equilibrium_funcionario.ds_job_role IS
    'Esse atributo irá receber a Escolaridade do  paciente.  Seu conteúdo é obrigatório.';

ALTER TABLE t_equilibrium_funcionario ADD CONSTRAINT pk_equilibrium_func PRIMARY KEY ( id_func );

ALTER TABLE t_equilibrium_funcionario ADD CONSTRAINT uk_equilibrium_cpf_func UNIQUE ( nr_cpf );

CREATE TABLE t_equilibrium_tel (
    id_func      NUMBER(9) NOT NULL,
    id_telefone  NUMBER(9) NOT NULL,
    nr_ddi       NUMBER(3) NOT NULL,
    nr_ddd       NUMBER(3) NOT NULL,
    nr_telefone  NUMBER(9) NOT NULL,
    st_telefone  CHAR(1) NOT NULL
);

ALTER TABLE t_equilibrium_tel
    ADD CONSTRAINT ck_equilibrium_st_tel_func CHECK ( st_telefone IN (
        'A',
        'I'
    ) );

COMMENT ON COLUMN t_equilibrium_tel.id_func IS
    'Esse atributo irá receber a chave primária do paciente. Esse número é sequencial e gerado automaticamente pelo sistema. Seu conteúdo é obrigatório.';

COMMENT ON COLUMN t_equilibrium_tel.id_telefone IS
    'Esse atributo irá receber a chave primária do telefone do paciente. Esse número é sequencial iniciando com 1 a partir do id do paciente e é  gerado automaticamente pelo sistema. Seu conteúdo é obrigatório.';

COMMENT ON COLUMN t_equilibrium_tel.nr_ddi IS
    'Este atributo irá receber o número do DDI(país) do telefone do  paciente. Seu conteudo é obrigatório.';

COMMENT ON COLUMN t_equilibrium_tel.nr_ddd IS
    'Esse atributo irá receber o número do DDD(estado) do telefone paciente.  Seu conteúdo é obrigatório.';

COMMENT ON COLUMN t_equilibrium_tel.nr_telefone IS
    'Esse atributo irá receber o número do telefone do  DDD do telefone paciente.  Seu conteúdo é obrigatório.';

COMMENT ON COLUMN t_equilibrium_tel.st_telefone IS
    'Esse atributo irá receber o status do telefone do paciente.  Seu conteúdo é obrigatório e deve possuir os seguintes valores: (A)tivo ou (I)nativo.';

ALTER TABLE t_equilibrium_tel ADD CONSTRAINT pk_equilibrium_tel_func PRIMARY KEY ( id_telefone,
                                                                                   id_func );

CREATE TABLE t_equilibrium_teste_situacao (
    id_teste                   NUMBER(9) NOT NULL,
    id_func                    NUMBER(9) NOT NULL,
    nr_job_satisfaction        NUMBER(4, 2) NOT NULL,
    nr_stress_level            NUMBER(4, 2) NOT NULL,
    nr_productivity_score      NUMBER(4, 2) NOT NULL,
    nr_sleep_hours             NUMBER(3) NOT NULL,
    nr_physical_activity       NUMBER(3) NOT NULL,
    ds_mental_support          VARCHAR2(3) NOT NULL,
    nr_manager_support_score   NUMBER(4, 2) NOT NULL,
    ds_therapy_access          VARCHAR2(3) NOT NULL,
    nr_mental_health_days_off  NUMBER(3) NOT NULL,
    nr_salary_range            VARCHAR2(10) NOT NULL,
    nr_work_life_score         NUMBER(4, 2) NOT NULL,
    nr_team_size               NUMBER(3) NOT NULL,
    nr_career_growth_score     NUMBER(4, 2) NOT NULL,
    nr_burnout_score           NUMBER(4, 2) NOT NULL
);

ALTER TABLE t_equilibrium_teste_situacao
    ADD CONSTRAINT ck_equilibrium_mental_sup CHECK ( ds_mental_support IN (
        'No',
        'Yes'
    ) );

ALTER TABLE t_equilibrium_teste_situacao
    ADD CONSTRAINT ck_equilibrium_therapy_access CHECK ( ds_therapy_access IN (
        'No',
        'Yes'
    ) );

COMMENT ON COLUMN t_equilibrium_teste_situacao.id_teste IS
    'Esse atributo irá receber a chave primária do teste realizado pelo paciente no site. Esse número é sequencial e gerado automaticamente pelo sistema. Seu conteúdo é obrigatório.';

COMMENT ON COLUMN t_equilibrium_teste_situacao.id_func IS
    'Esse atributo irá receber a chave primária do paciente. Esse número é sequencial e gerado automaticamente pelo sistema. Seu conteúdo é obrigatório.';

ALTER TABLE t_equilibrium_teste_situacao ADD CONSTRAINT pk_equilibrium_teste_situacao PRIMARY KEY ( id_teste );

ALTER TABLE t_equilibrium_funcionario
    ADD CONSTRAINT fk_equilibrium_depto_func FOREIGN KEY ( id_depto )
        REFERENCES t_equilibrium_depto ( id_depto );

ALTER TABLE t_equilibrium_email
    ADD CONSTRAINT fk_equilibrium_func_email FOREIGN KEY ( id_func )
        REFERENCES t_equilibrium_funcionario ( id_func );

ALTER TABLE t_equilibrium_tel
    ADD CONSTRAINT fk_equilibrium_func_tel FOREIGN KEY ( id_func )
        REFERENCES t_equilibrium_funcionario ( id_func );

ALTER TABLE t_equilibrium_teste_situacao
    ADD CONSTRAINT fk_equilibrium_func_tst_sit FOREIGN KEY ( id_func )
        REFERENCES t_equilibrium_funcionario ( id_func );

--Sequence--
CREATE SEQUENCE SQ_T_EQUILIBRIUM_FUNCIONARIO START WITH 1;

CREATE SEQUENCE SQ_T_EQUILIBRIUM_DEPTO START WITH 1;

CREATE SEQUENCE SQ_T_EQUILIBRIUM_TEL START WITH 1;

CREATE SEQUENCE SQ_T_EQUILIBRIUM_EMAIL START WITH 1;

CREATE SEQUENCE SQ_T_EQUILIBRIUM_TESTE_SITUACAO START WITH 1;


-- Relatório do Resumo do Oracle SQL Developer Data Modeler: 
-- 
-- CREATE TABLE                             5
-- CREATE INDEX                             0
-- ALTER TABLE                             17
-- CREATE VIEW                              0
-- ALTER VIEW                               0
-- CREATE PACKAGE                           0
-- CREATE PACKAGE BODY                      0
-- CREATE PROCEDURE                         0
-- CREATE FUNCTION                          0
-- CREATE TRIGGER                           0
-- ALTER TRIGGER                            0
-- CREATE COLLECTION TYPE                   0
-- CREATE STRUCTURED TYPE                   0
-- CREATE STRUCTURED TYPE BODY              0
-- CREATE CLUSTER                           0
-- CREATE CONTEXT                           0
-- CREATE DATABASE                          0
-- CREATE DIMENSION                         0
-- CREATE DIRECTORY                         0
-- CREATE DISK GROUP                        0
-- CREATE ROLE                              0
-- CREATE ROLLBACK SEGMENT                  0
-- CREATE SEQUENCE                          0
-- CREATE MATERIALIZED VIEW                 0
-- CREATE MATERIALIZED VIEW LOG             0
-- CREATE SYNONYM                           0
-- CREATE TABLESPACE                        0
-- CREATE USER                              0
-- 
-- DROP TABLESPACE                          0
-- DROP DATABASE                            0
-- 
-- REDACTION POLICY                         0
-- 
-- ORDS DROP SCHEMA                         0
-- ORDS ENABLE SCHEMA                       0
-- ORDS ENABLE OBJECT                       0
-- 
-- ERRORS                                   0
-- WARNINGS                                 0
