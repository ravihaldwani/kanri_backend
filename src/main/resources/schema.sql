CREATE TABLE IF NOT EXISTS USER_ENTITY
(
    ID                  BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    FIRST_NAME          VARCHAR(100),
    LAST_NAME           VARCHAR(100),
    PHONE               VARCHAR(20),
    EMAIL               VARCHAR(20),
    PASSWORD            VARCHAR(100),
    ROLES               VARCHAR[],
    CREATED_DATE        TIMESTAMP,
    STATUS              VARCHAR(20),
    ACTIVATED           BOOLEAN,
    ACTIVATION_KEY      VARCHAR(64),
    CREATED_BY_ID       BIGINT,
    LAST_MODIFIED_BY_ID BIGINT
);

CREATE TABLE IF NOT EXISTS SPACE_ENTITY
(
    ID               BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    DOMAIN_NAME      VARCHAR(100),
    CREATED_DATE     TIMESTAMP,
    STATUS           VARCHAR(20),
    CREATED_BY       BIGINT,
    LAST_MODIFIED_BY BIGINT,
    USERS            BIGINT[],
    BOARDS           BIGINT[]
);

CREATE TABLE IF NOT EXISTS SPACE_ENTITY
(
    ID               BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    DOMAIN_NAME      VARCHAR(100),
    CREATED_DATE     TIMESTAMP,
    STATUS           VARCHAR(20),
    CREATED_BY       BIGINT,
    LAST_MODIFIED_BY BIGINT
);

CREATE TABLE IF NOT EXISTS BOARD_ENTITY
(
    ID               BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    NAME             VARCHAR(100),
    CREATED_DATE     TIMESTAMP,
    STATUS           VARCHAR(20),
    CREATED_BY       BIGINT,
    LAST_MODIFIED_BY BIGINT
);

CREATE TABLE IF NOT EXISTS BOARD_PERMISSIONS
(
    BOARD_ENTITY BIGINT REFERENCES BOARD_ENTITY (ID),
    USER_ID      BIGINT,
    PERMISSIONS  VARCHAR[]
);