-- Table: users
CREATE TABLE users (
  id       bigint NOT NULL,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  CONSTRAINT user_pkey PRIMARY KEY (id)
);
-- Table: roles
CREATE TABLE roles (
  id   INT          NOT NULL,
  name VARCHAR(100) NOT NULL,
  CONSTRAINT role_pkey PRIMARY KEY (id)
);

-- Table for mapping user and roles: user_roles
CREATE TABLE user_roles (
  user_id INT NOT NULL,
  role_id INT NOT NULL,

  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id),

  UNIQUE (user_id, role_id)
);

-- Insert data
 INSERT INTO users VALUES (1, 'admin', 'admin');
--
 INSERT INTO roles VALUES (1, 'ROLE_USER');
 INSERT INTO roles VALUES (2, 'ROLE_ADMIN');

 INSERT INTO user_roles VALUES (1, 2);