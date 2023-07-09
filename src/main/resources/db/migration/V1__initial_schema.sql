CREATE TYPE academic_year AS ENUM ('I', 'II', 'III', 'IV');

CREATE TABLE branch
(
    b_id         uuid,
    b_short_name VARCHAR(10),
    b_name       VARCHAR(100) NOT NULL,
    b_created_at TIMESTAMP    NOT NULL DEFAULT now(),
    b_updated_at TIMESTAMP             DEFAULT now(),
    CONSTRAINT branch_pk PRIMARY KEY (b_id),
    CONSTRAINT branch_short_name_unique_key UNIQUE (b_short_name)
);

CREATE TABLE student
(
    s_id         uuid,
    s_name       VARCHAR(100)  NOT NULL,
    s_branch     uuid          NOT NULL,
    s_year       academic_year NOT NULL,
    s_dob        DATE          NOT NULL,
    s_email      VARCHAR(100)  NOT NULL,
    s_phone      VARCHAR(20)   NOT NULL,
    s_created_at TIMESTAMP     NOT NULL DEFAULT now(),
    s_updated_at TIMESTAMP              DEFAULT now(),
    CONSTRAINT student_pk PRIMARY KEY (s_id),
    CONSTRAINT student_branch_fk FOREIGN KEY (s_branch) REFERENCES branch
);

CREATE INDEX ix__student__branch__year ON student (s_branch, s_year);