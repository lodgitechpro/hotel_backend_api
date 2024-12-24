CREATE TABLE role (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  type VARCHAR(255) NOT NULL,
  active boolean default true not null,
  created_by VARCHAR(255),
  created_at TIMESTAMP,
  last_modified_by VARCHAR(255),
  last_modified_at TIMESTAMP
);

CREATE TABLE employee_role (
  id SERIAL PRIMARY KEY,
  employee_id INT NOT NULL,
  role_id INT NOT NULL,
  created_by VARCHAR(255),
  created_at TIMESTAMP,
  last_modified_by VARCHAR(255),
  last_modified_at TIMESTAMP,
  CONSTRAINT fk_employee_roles_employee FOREIGN KEY (employee_id) REFERENCES employee(id),
  CONSTRAINT fk_employee_roles_role FOREIGN KEY (role_id) REFERENCES role(id)
);

CREATE TABLE IF NOT EXISTS permission (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    active BOOLEAN default true not null,
    created_by VARCHAR(255),
    created_at TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS role_permission (
    id SERIAL PRIMARY KEY,
    role_id BIGINT,
    permission_id BIGINT,
    created_by VARCHAR(255),
    created_at TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_at TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES role(id),
    FOREIGN KEY (permission_id) REFERENCES permission(id)
);

CREATE TABLE MENU (
  id        SERIAL PRIMARY KEY,
  name      VARCHAR(50),
  position  INTEGER not null,
  active    BOOLEAN default false,
  menu_name VARCHAR(50),
  mnemonic  CHAR(1) default 'P' not null,
  created_by VARCHAR(255),
  created_at TIMESTAMP,
  last_modified_by VARCHAR(255),
  last_modified_at TIMESTAMP
);

CREATE TABLE FORM (
  id              SERIAL PRIMARY KEY,
  name            VARCHAR(50) not null,
  menu_id         INTEGER,
  form_name       VARCHAR(100) not null,
  position        INTEGER not null,
  active          BOOLEAN default false,
  description     VARCHAR(500),
  web_url         VARCHAR(500),
  web_enabled     BOOLEAN default true,
  desktop_enabled BOOLEAN default false,
  created_by VARCHAR(255),
  created_at TIMESTAMP,
  last_modified_by VARCHAR(255),
  last_modified_at TIMESTAMP,
  CONSTRAINT fk_form_menu FOREIGN KEY (menu_id) REFERENCES menu(id)
);

CREATE TABLE ROLE_FORM (
  id SERIAL PRIMARY KEY,
  role_id          INTEGER not null,
  form_id          INTEGER not null,
  active           BOOLEAN default true,
  description      VARCHAR(500),
  created_by VARCHAR(255),
  created_at TIMESTAMP,
  last_modified_by VARCHAR(255),
  last_modified_at TIMESTAMP,
  FOREIGN KEY (role_id) REFERENCES role(id),
  FOREIGN KEY (form_id) REFERENCES form(id)
);


CREATE SEQUENCE permission_seq START 1001;
CREATE SEQUENCE role_seq START 1001;
CREATE SEQUENCE organization_seq START 1001;
CREATE SEQUENCE menu_seq START 1001;
CREATE SEQUENCE form_seq START 1001;
CREATE SEQUENCE employee_role_seq START 1;
CREATE SEQUENCE role_permission_seq START 1;
