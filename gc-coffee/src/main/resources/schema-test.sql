
DROP TABLE IF EXISTS product;

CREATE TABLE product(
    id BINARY(16) NOT NULL PRIMARY KEY,
    name varchar(20) NOT NULL,
    category varchar(50) NOT NULL,
    price bigint NOT NULL,
    description varchar(500) DEFAULT NULL,
    created_at datetime(6) NOT NULL,
    updated_at datetime(6) DEFAULT NULL
);