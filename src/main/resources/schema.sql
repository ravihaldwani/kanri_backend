CREATE TABLE "user"
(
    ID           INTEGER SERIAL PRIMARY KEY,
    FIRST_NAME   VARCHAR(100),
    LAST_NAME    VARCHAR(100),
    PHONE        VARCHAR(20),
    EMAIL        VARCHAR(20),
    PASSWORD     VARCHAR(100),
    CREATED_DATE DATETIME
);