DROP TABLE IF EXISTS Tasks;

CREATE TABLE Tasks(
    id int PRIMARY KEY auto_increment,
    description varchar(100) not null,
    done bit
)