/* kasowanie bazy danych*/
DROP DATABASE IF EXISTS WeatherDataB;

/*tworzenie nowej bazy danych*/
CREATE DATABASE WeatherDataB
    with owner = postgres
    encoding 'UTF-8'
    lc_collate = 'Polish_Poland.1250'
    lc_ctype = 'Polish_Poland.1250'
    tablespace = pg_default
    connection limit = -1;


drop table if exists temperature;

/*tworzenie podstwowych kolumn*/
create table temperature  (
                           id serial not null,
                           dataTime varchar(30) not null,
                           Temp int,
                           primary key (id)
);

select * from temperature;

insert into temperature (dataTime, Temp)
values ('Testowy czas1', 2),
       ('Testowy czas2 ', 4);
