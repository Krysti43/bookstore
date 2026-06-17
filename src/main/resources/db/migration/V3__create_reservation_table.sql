CREATE TABLE reservations (
                              id BIGSERIAL PRIMARY KEY,
                              reservation_date TIMESTAMP NOT NULL,
                              status VARCHAR(50) NOT NULL,
                              user_id BIGINT NOT NULL,
                              book_id BIGINT NOT NULL,
                              CONSTRAINT fk_reservations_user
                                  FOREIGN KEY (user_id)
                                      REFERENCES app_users(id),
                              CONSTRAINT fk_reservations_book
                                  FOREIGN KEY (book_id)
                                      REFERENCES books(id)
);