INSERT INTO users (
    id,
    created_date,
    created_by,
    email,
    password,
    role
) VALUES (
             gen_random_uuid(),
             CURRENT_TIMESTAMP,
             'system',
             'admin@enigma.com',
             '$2a$10$7QpJqZzK8xw3X8pEwK5XJuq3N5u4c9b8UqzZy0Z2pKq9yXzWw1KQK',
             'ROLE_ADMIN'
         );

INSERT INTO users (
    id,
    created_date,
    created_by,
    email,
    password,
    role
) VALUES (
             gen_random_uuid(),
             CURRENT_TIMESTAMP,
             'system',
             'user@enigma.com',
             '$2a$10$7QpJqZzK8xw3X8pEwK5XJuq3N5u4c9b8UqzZy0Z2pKq9yXzWw1KQK',
             'ROLE_USER'
         );
