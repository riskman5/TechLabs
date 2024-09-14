INSERT INTO users (username, password)
VALUES ('admin', '$2a$10$nvAMLzkwJYn5VnQTpzwDyuVXT/bL0/FquKP1mluCBYxWG5BS6XLtS');


INSERT INTO user_authorities (user_id, authorities)
VALUES ((SELECT id FROM users WHERE username = 'admin'), 'ADMIN');