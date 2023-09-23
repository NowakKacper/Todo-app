DROP TABLE IF EXISTS Task_events;

CREATE TABLE Task_events(
    id int PRIMARY KEY auto_increment,
    task_id int,
    occurrence datetime,
    name varchar(30)
)