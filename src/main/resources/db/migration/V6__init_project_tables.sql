CREATE TABLE Projects(
                            id int PRIMARY KEY auto_increment,
                            description varchar(100) not null
);

ALTER TABLE Task_groups ADD COLUMN project_id INT NULL;
ALTER TABLE Task_groups ADD FOREIGN KEY (project_id) REFERENCES Projects(id);

CREATE TABLE Project_steps(
                            id int PRIMARY KEY auto_increment,
                            description varchar(100) not null,
                            days_to_deadline int not NULL,
                            project_id int not null,
                            FOREIGN KEY (project_id) REFERENCES Projects(id)
);