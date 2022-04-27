
-- DROP TABLE IF EXISTS product;

CREATE TABLE product(
    id BINARY(16) NOT NULL PRIMARY KEY,
    name varchar(20) NOT NULL,
    category varchar(50) NOT NULL,
    price bigint NOT NULL,
    description varchar(500) DEFAULT NULL,
    created_at datetime(6) NOT NULL,
    updated_at datetime(6) DEFAULT NULL
);

CREATE TABLE order
(
    id     binary(16) PRIMARY KEY,
    email        VARCHAR(50)  NOT NULL,
    address      VARCHAR(200) NOT NULL,
    postcode     VARCHAR(200) NOT NULL,
    order_status VARCHAR(50)  NOT NULL,
    created_at   datetime(6) NOT NULL,
    updated_at   datetime(6) DEFAULT NULL
);

CREATE TABLE order_item(
    sequence    bigint      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    order_id    binary(16)  NOT NULL,
    product_id  binary(16)  NOT NULL,
    category    VARCHAR(50) NOT NULL,
    price       bigint      NOT NULL,
    quantity    int         NOT NULL,
    created_at  datetime(6) NOT NULL,
    updated_at  datetime(6) DEFAULT NULL,
    INDEX (order_id),
    CONSTRAINT fk_order_items_to_order FOREIGN KEY (order_id) REFERENCES order (id) ON DELETE CASCADE ,
    CONSTRAINT fk_order_items_to_product FOREIGN KEY (product_id) REFERENCES product (id)
)
