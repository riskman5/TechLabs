INSERT INTO users (id, username, password, owner_id)
VALUES (2, 'user', '$2a$10$nvAMLzkwJYn5VnQTpzwDyuVXT/bL0/FquKP1mluCBYxWG5BS6XLtS', 1);


INSERT INTO user_authorities (user_id, authorities)
VALUES ((SELECT id FROM users WHERE username = 'user'), 'USER');

INSERT INTO user_authorities (user_id, authorities)
VALUES ((SELECT id FROM users WHERE username = 'user'), 'OWNER');