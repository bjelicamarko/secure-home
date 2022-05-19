insert into role (name) VALUES ('ROLE_ADMIN');
insert into role (name) VALUES ('ROLE_OWNER');
insert into role (name) VALUES ('ROLE_TENANT');
insert into role (name) VALUES ('ROLE_UNASSIGNED');


INSERT INTO privilege (name) VALUES ('READ_USERS_WITHOUT_ADMIN');
INSERT INTO privilege (name) VALUES ('SEARCH_USERS');
INSERT INTO privilege (name) VALUES ('DELETE_USER');
INSERT INTO privilege (name) VALUES ('GET_REAL_ESTATE_BY_ID');
INSERT INTO privilege (name) VALUES ('SAVE_REAL_ESTATE');
INSERT INTO privilege (name) VALUES ('SAVE_USER_REAL_ESTATE');
INSERT INTO privilege (name) VALUES ('CHANGE_ROLE_USER_REAL_ESTATE');
INSERT INTO privilege (name) VALUES ('GET_USER_REAL_ESTATE_TO_ASSIGN');
INSERT INTO privilege (name) VALUES ('GET_USER_REAL_ESTATES');

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo, verified) values ('admin', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Admin', 'Admin', 'admin@maildrop.cc', false, true, 0, null, 'ROLE_ADMIN',
                                                                                                              '/user_profile_photos/default.jpg', true);

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo, verified) values ('beli', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Marko', 'Bjelica', 'beli@maildrop.cc', false, true, 0, null, 'ROLE_OWNER',
                                                                                                              '/user_profile_photos/default.jpg', true);

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo, verified) values ('bojan', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Bojan', 'Baskalo', 'bojan@maildrop.cc', false, true, 0, null, 'ROLE_OWNER',
                                                                                                              '/user_profile_photos/default.jpg', true);

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo, verified) values ('veljko', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Veljko', 'Tosic', 'veljko@maildrop.cc', false, true, 0, null, 'ROLE_TENANT',
                                                                                                              '/user_profile_photos/default.jpg', true);

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo, verified) values ('ptica', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Darko', 'Tica', 'ptica@maildrop.cc', false, true, 0, null, 'ROLE_TENANT',
                                                                                                              '/user_profile_photos/default.jpg', true);

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo, verified) values ('dunjica', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Dunja', 'Dunjica', 'dunja@maildrop.cc', false, true, 0, null, 'ROLE_OWNER',
                                                                                                              '/user_profile_photos/default.jpg', true);

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo, verified) values ('mico', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Mico', 'Bradina', 'mico@maildrop.cc', false, true, 0, null, 'ROLE_OWNER',
                                                                                                              '/user_profile_photos/default.jpg', true);

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo, verified) values ('charlie', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Charlie', 'Sheen', 'charlie@maildrop.cc', false, true, 0, null, 'ROLE_TENANT',
                                                                                                              '/user_profile_photos/default.jpg', true);

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo, verified) values ('visnjica', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Visnja', 'Visnjica', 'visnja@maildrop.cc', false, true, 0, null,  'ROLE_TENANT',
                                                                                                              '/user_profile_photos/default.jpg', true);
insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo, verified) values ('admin1', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Admin', 'Admin', 'admin1@maildrop.cc', false, true, 0, null,  'ROLE_TENANT',
                                                                                                               '/user_profile_photos/default.jpg', true);

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo, verified) values ('beli1', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Marko', 'Bjelica', 'beli1@maildrop.cc', false, true, 0, null,  'ROLE_BOTH',
                                                                                                              '/user_profile_photos/default.jpg', true);

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo, verified) values ('bojan1', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Bojan', 'Baskalo', 'bojan1@maildrop.cc', false, true, 0, null,  'ROLE_BOTH',
                                                                                                              '/user_profile_photos/default.jpg', true);

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo, verified) values ('veljko1', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Veljko', 'Tosic', 'veljko1@maildrop.cc', false, true, 0, null,  'ROLE_UNASSIGNED',
                                                                                                              '/user_profile_photos/default.jpg', true);

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo, verified) values ('ptica1', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Darko', 'Tica', 'ptica1@maildrop.cc', false, true, 0, null,  'ROLE_UNASSIGNED',
                                                                                                              '/user_profile_photos/default.jpg', true);

INSERT INTO user_role (user_id, role_id) VALUES (1, 1); --  ROLE_ADMIN
INSERT INTO user_role (user_id, role_id) VALUES (2, 2); --  ROLE_OWNER
INSERT INTO user_role (user_id, role_id) VALUES (3, 2); --  ROLE_OWNER
INSERT INTO user_role (user_id, role_id) VALUES (4, 3); --  ROLE_TENANT
INSERT INTO user_role (user_id, role_id) VALUES (5, 3); --  ROLE_TENANT
INSERT INTO user_role (user_id, role_id) VALUES (6, 2); --  ROLE_OWNER
INSERT INTO user_role (user_id, role_id) VALUES (7, 2); --  ROLE_OWNER
INSERT INTO user_role (user_id, role_id) VALUES (8, 3); --  ROLE_TENANT
INSERT INTO user_role (user_id, role_id) VALUES (9, 3); --  ROLE_TENANT
INSERT INTO user_role (user_id, role_id) VALUES (10, 3); --  ROLE_TENANT

INSERT INTO user_role (user_id, role_id) VALUES (11, 2); --  ROLE_BOTH
INSERT INTO user_role (user_id, role_id) VALUES (12, 2); --  ROLE_BOTH
INSERT INTO user_role (user_id, role_id) VALUES (11, 3); --  ROLE_BOTH
INSERT INTO user_role (user_id, role_id) VALUES (12, 3); --  ROLE_BOTH

INSERT INTO user_role (user_id, role_id) VALUES (13, 4); --  ROLE_UNASSIGNED
INSERT INTO user_role (user_id, role_id) VALUES (14, 4); --  ROLE_UNASSIGNED

INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 1);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 2);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 3);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 4);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 5);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 6);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 7);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 8);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 9);

INSERT INTO real_estate (name) VALUES ('Kuca 1');
INSERT INTO real_estate (name) VALUES ('Kuca 2');
INSERT INTO real_estate (name) VALUES ('Stan 1');
INSERT INTO real_estate (name) VALUES ('Stan 2');
INSERT INTO real_estate (name) VALUES ('Vikendica 1');
INSERT INTO real_estate (name) VALUES ('Vikendica 2');
INSERT INTO real_estate (name) VALUES ('Vikendica 3');
INSERT INTO real_estate (name) VALUES ('Vikendica 4');

INSERT INTO user_real_estate (user_id, real_estate_id, role) VALUES (2, 1, 'OWNER');
INSERT INTO user_real_estate (user_id, real_estate_id, role) VALUES (2, 2, 'OWNER');
INSERT INTO user_real_estate (user_id, real_estate_id, role) VALUES (3, 3, 'OWNER');
INSERT INTO user_real_estate (user_id, real_estate_id, role) VALUES (4, 3, 'TENANT');
INSERT INTO user_real_estate (user_id, real_estate_id, role) VALUES (5, 3, 'TENANT');
INSERT INTO user_real_estate (user_id, real_estate_id, role) VALUES (11, 5, 'OWNER');
INSERT INTO user_real_estate (user_id, real_estate_id, role) VALUES (11, 7, 'TENANT');
INSERT INTO user_real_estate (user_id, real_estate_id, role) VALUES (12, 6, 'OWNER');
INSERT INTO user_real_estate (user_id, real_estate_id, role) VALUES (12, 8, 'TENANT');