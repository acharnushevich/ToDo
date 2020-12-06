INSERT INTO PROJECTS (id, name, description) VALUES (1, 'Elementary', 'Description');
INSERT INTO PROJECTS (id, name, description) VALUES (2, 'Advanced', 'Description');

INSERT INTO USERS (id, email, password, name, surname, description, projects_id, role, profile_enable) VALUES (1, 'admin1@gmail.com', '1', 'Administrator', 'IT', 'Description', '1,2', 'Admin', 1);
INSERT INTO USERS (id, email, password, name, surname, description, projects_id, role, profile_enable) VALUES (2, 'admin2@gmail.com', '1', 'Manager', 'Projects', 'Description', '1,2', 'Admin', 1);

INSERT INTO TASKS (id, name, date, description, priority, task_status, project_id, user_id) VALUES (1, 'Study at the IT-Academy', TO_DATE('01.12.2020', 'DD.MM.YYYY'), 'Description', 'Medium', 'Open', 1, 1);
INSERT INTO TASKS (id, name, date, description, priority, task_status, project_id, user_id) VALUES (2, 'Find a job', TO_DATE('01.01.2021', 'DD.MM.YYYY'), 'Description', 'Low', 'Open', 2, 2);

INSERT INTO WORK_LOGS (id, time, date, description, task_id, user_id) VALUES (1, 1, TO_DATE('01.12.2020', 'DD.MM.YYYY'), 'Description Log', 1, 1);
INSERT INTO WORK_LOGS (id, time, date, description, task_id, user_id) VALUES (2, 2, TO_DATE('01.01.2021', 'DD.MM.YYYY'), 'Description Log', 1, 2);