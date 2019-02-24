-- Table: accountNumber
CREATE TABLE account_numbers (
  id       bigint NOT NULL,
  user_id  INT NOT NULL,
  number   VARCHAR(255) NOT NULL,
  amount   INT    NOT NULL,
  CONSTRAINT acc_pkey PRIMARY KEY (id),

  FOREIGN KEY (user_id) REFERENCES users (id)
);


-- Table: Info Table
CREATE TABLE info_table (
  id          bigint NOT NULL,
  acc_id      INT NOT NULL,
  tdate       VARCHAR(255) NOT NULL,
  code        INT NOT NULL,
  description VARCHAR(255) NOT NULL,
  credit      INT NOT NULL,
  debit       INT NOT NULL,
  CONSTRAINT inf_pkey PRIMARY KEY (id),

  FOREIGN KEY (acc_id) REFERENCES account_numbers (id)
);

-- Sequence Acc
CREATE SEQUENCE seq_acc
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE account_numbers ALTER COLUMN id SET DEFAULT nextval('seq_acc');

-- Sequence Info
CREATE SEQUENCE seq_info
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

ALTER TABLE info_table ALTER COLUMN id SET DEFAULT nextval('seq_info');