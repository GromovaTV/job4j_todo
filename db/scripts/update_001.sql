create table if not exists items (
    id serial primary key,
    name text,
    description text,
    created timestamp,
    done boolean,
    user_id int not null references j_user(id)
);

create table j_user (
  id serial primary key,
  name varchar(2000),
  email TEXT UNIQUE,
  password TEXT
);