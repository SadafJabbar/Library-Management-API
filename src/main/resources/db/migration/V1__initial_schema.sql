CREATE TABLE category_entity (
                                 id BIGINT NOT NULL AUTO_INCREMENT,
                                 name VARCHAR(255),
                                 book_count BIGINT,
                                 PRIMARY KEY (id)
);

CREATE TABLE user_entity (
                             id BIGINT NOT NULL AUTO_INCREMENT,
                             name VARCHAR(255),
                             email VARCHAR(255),
                             created_at TIMESTAMP(6),
                             membership_status VARCHAR(255),
                             PRIMARY KEY (id)
);

CREATE TABLE book_entity (
                             id BIGINT NOT NULL AUTO_INCREMENT,
                             title VARCHAR(255),
                             author VARCHAR(255),
                             status VARCHAR(255),
                             published_year BIGINT,
                             stock INT NOT NULL,
                             category_id BIGINT,
                             PRIMARY KEY (id),
                             CONSTRAINT fk_book_category
                                 FOREIGN KEY (category_id)
                                     REFERENCES category_entity(id)
);

CREATE TABLE loan_entity (
                             id BIGINT NOT NULL AUTO_INCREMENT,
                             user_id BIGINT,
                             book_id BIGINT,
                             borrow_date DATE,
                             return_date DATE,
                             PRIMARY KEY (id),
                             CONSTRAINT fk_loan_user
                                 FOREIGN KEY (user_id)
                                     REFERENCES user_entity(id),
                             CONSTRAINT fk_loan_book
                                 FOREIGN KEY (book_id)
                                     REFERENCES book_entity(id)
);