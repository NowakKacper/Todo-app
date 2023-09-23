CREATE TABLE Task_groups(
    id int PRIMARY KEY auto_increment,
    description varchar(100) not null,
    done bit
);

ALTER TABLE Tasks ADD COLUMN task_group_id INT NULL;
ALTER TABLE Tasks ADD FOREIGN KEY (task_group_id) REFERENCES task_groups(id);