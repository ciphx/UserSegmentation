DROP TABLE IF EXISTS User_Group_Assignment;
DROP TABLE IF EXISTS  User_Rules_Evaluation_Data;

CREATE TABLE User_Group_Assignment(
    user_id INTEGER PRIMARY KEY,
    group_names clob NOT  NULL,
    created_at timestamp  NOT NULL,
    updated_at timestamp  NOT NULL
);

CREATE TABLE User_Rules_Evaluation_Data(
    attempt_id varchar(512) PRIMARY KEY,
    user_id integer NOT NULL ,
    group_name clob NOT NULL,
    user_preference_attributes clob NOT NULL,
    rules_evlauation_result clob NOT NULL,
    created_at timestamp  NOT NULL
);

