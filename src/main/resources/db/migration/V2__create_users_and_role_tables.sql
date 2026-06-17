CREATE TABLE roles (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE app_users (
                           id BIGSERIAL PRIMARY KEY,
                           username VARCHAR(100) NOT NULL UNIQUE,
                           email VARCHAR(255) NOT NULL UNIQUE,
                           password VARCHAR(255) NOT NULL,
                           role_id BIGINT NOT NULL,
                           CONSTRAINT fk_app_users_role
                               FOREIGN KEY (role_id)
                                   REFERENCES roles(id)
);

INSERT INTO roles (name) VALUES ('ROLE_USER');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');