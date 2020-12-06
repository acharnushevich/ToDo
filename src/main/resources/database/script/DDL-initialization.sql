create table PROJECTS
(
	ID NUMBER PRIMARY KEY NOT NULL,
	NAME VARCHAR(50) not null,
	DESCRIPTION VARCHAR(50) not null
);

create table USERS
(
	ID NUMBER PRIMARY KEY NOT NULL,
	EMAIL VARCHAR(50) not null,
	PASSWORD VARCHAR(50) not null,
	NAME VARCHAR(50) not null,
	SURNAME VARCHAR(50) not null,
	DESCRIPTION VARCHAR(50) not null,
	PROJECTS_ID VARCHAR(50) not null,
	ROLE VARCHAR(50) not null,
	PROFILE_ENABLE NUMBER not null
);

create table TASKS
(
	ID NUMBER PRIMARY KEY NOT NULL,
	NAME VARCHAR(50) not null,
	DATE DATE(10) not null,
	DESCRIPTION VARCHAR(50) not null,
	PRIORITY VARCHAR(50) not null,
	TASK_STATUS VARCHAR(50) not null,
	PROJECT_ID NUMBER not null,
	USER_ID NUMBER not null
);

create table WORK_LOGS
(
	ID NUMBER PRIMARY KEY NOT NULL,
	TIME NUMBER not null,
	DATE DATE(10) not null,
	DESCRIPTION VARCHAR(50) not null,
	TASK_ID NUMBER not null,
	USER_ID NUMBER not null
);

create table ACTIVITIES
(
	ID NUMBER PRIMARY KEY NOT NULL,
	NAME VARCHAR(50) not null,
	DATE DATETIME not null,
	TASK_ID NUMBER not null,
	USER_ID NUMBER not null
);

create table ATTACHMENTS
(
	ID NUMBER PRIMARY KEY NOT NULL,
	FILE_NAME VARCHAR(50) not null,
	FILE_DATA BLOB not null,
	TASK_ID NUMBER not null
);