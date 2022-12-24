CREATE TABLE candidate(
   id SERIAL PRIMARY KEY,
   name TEXT,
   city_id INT,
   description text,
   created timestamp without time zone,
   photo bytea
);
