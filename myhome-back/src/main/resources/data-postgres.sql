insert into role (name) VALUES ('ROLE_ADMIN');
insert into role (name) VALUES ('ROLE_OWNER');
insert into role (name) VALUES ('ROLE_TENANT');
insert into role (name) VALUES ('ROLE_UNASSIGNED');


INSERT INTO privilege (name) VALUES ('READ_USERS_WITHOUT_ADMIN');
INSERT INTO privilege (name) VALUES ('SEARCH_USERS');
INSERT INTO privilege (name) VALUES ('DELETE_USER');

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
                                                                                                              'Marko', 'Bjelica', 'beli1@maildrop.cc', false, true, 0, null,  'ROLE_TENANT',
                                                                                                              '/user_profile_photos/default.jpg', true);

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo, verified) values ('bojan1', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Bojan', 'Baskalo', 'bojan1@maildrop.cc', false, true, 0, null,  'ROLE_TENANT',
                                                                                                              '/user_profile_photos/default.jpg', true);

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo, verified) values ('veljko1', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Veljko', 'Tosic', 'veljko1@maildrop.cc', false, true, 0, null,  'ROLE_TENANT',
                                                                                                              '/user_profile_photos/default.jpg', true);

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo, verified) values ('ptica1', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Darko', 'Tica', 'ptica1@maildrop.cc', false, true, 0, null,  'ROLE_TENANT',
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
INSERT INTO user_role (user_id, role_id) VALUES (11, 3); --  ROLE_TENANT
INSERT INTO user_role (user_id, role_id) VALUES (12, 3); --  ROLE_TENANT
INSERT INTO user_role (user_id, role_id) VALUES (13, 3); --  ROLE_TENANT
INSERT INTO user_role (user_id, role_id) VALUES (14, 3); --  ROLE_TENANT

INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 1);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 2);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 3);