CREATE TABLE public.student (
                              id INT AUTO_INCREMENT  PRIMARY KEY,
                              uuid UUID DEFAULT RANDOM_UUID() NOT NULL,
                              first_name VARCHAR(250) NOT NULL,
                              last_name VARCHAR(250) NOT NULL,
                              age INTEGER
);

CREATE UNIQUE INDEX ix_student_uuid ON public.student(uuid);
