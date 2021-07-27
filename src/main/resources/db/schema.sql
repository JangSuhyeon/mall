DROP TABLE IF EXISTS category;

CREATE TABLE category (
                          id bigint,
                          super_id bigint,
                          name varchar(255) not null,
                          PRIMARY KEY (id)
);