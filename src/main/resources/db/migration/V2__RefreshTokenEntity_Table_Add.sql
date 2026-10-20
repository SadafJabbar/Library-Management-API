CREATE TABLE RefreshToken (
                              refresh_token_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              refresh_token VARCHAR(255) NOT NULL UNIQUE,
                              validAccessToken VARCHAR(255) NOT NULL UNIQUE,
                              expires_at TIMESTAMP(6) NOT NULL,
                              is_revoked BOOLEAN NOT NULL,
                              user_Id BIGINT NOT NULL UNIQUE,

                              CONSTRAINT fk_refresh_token_user
                                  FOREIGN KEY (user_Id)
                                      REFERENCES user_entity(id)
);