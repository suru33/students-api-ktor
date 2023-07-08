CREATE ROLE student_user PASSWORD 'student_password' LOGIN;

CREATE DATABASE students;
GRANT ALL PRIVILEGES ON DATABASE students TO student_user;