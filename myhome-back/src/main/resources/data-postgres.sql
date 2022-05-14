insert into role (name) VALUES ('ROLE_ADMIN');
insert into role (name) VALUES ('ROLE_OWNER');
insert into role (name) VALUES ('ROLE_TENANT');

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo) values ('admin', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Admin', 'Admin', 'admin@maildrop.cc', false, true, 0, null, 'ROLE_ADMIN',
                                                                                                              '/user_profile_photos/default.jpg');

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo) values ('beli', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Marko', 'Bjelica', 'beli@maildrop.cc', false, true, 0, null, 'ROLE_OWNER',
                                                                                                              '/user_profile_photos/default.jpg');

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo) values ('bojan', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Bojan', 'Baskalo', 'bojan@maildrop.cc', false, true, 0, null, 'ROLE_OWNER',
                                                                                                              '/user_profile_photos/default.jpg');

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo) values ('veljko', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Veljko', 'Tosic', 'veljko@maildrop.cc', false, true, 0, null, 'ROLE_TENANT',
                                                                                                              '/user_profile_photos/default.jpg');

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo) values ('ptica', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Darko', 'Tica', 'ptica@maildrop.cc', false, true, 0, null, 'ROLE_TENANT',
                                                                                                              '/user_profile_photos/default.jpg');


insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo) values ('dunjica', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Dunja', 'Dunjica', 'dunja@maildrop.cc', false, true, 0, null, 'ROLE_OWNER',
                                                                                                              '/user_profile_photos/default.jpg');

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo) values ('mico', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Mico', 'Bradina', 'mico@maildrop.cc', false, true, 0, null, 'ROLE_OWNER',
                                                                                                              '/user_profile_photos/default.jpg');

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo) values ('charlie', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Charlie', 'Sheen', 'charlie@maildrop.cc', false, true, 0, null, 'ROLE_TENANT',
                                                                                                              '/user_profile_photos/default.jpg');

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo) values ('visnjica', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Visnja', 'Visnjica', 'visnja@maildrop.cc', false, true, 0, null,  'ROLE_TENANT',
                                                                                                              '/user_profile_photos/default.jpg');
insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo) values ('admin1', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Admin', 'Admin', 'admin1@maildrop.cc', false, true, 0, null,  'ROLE_TENANT',
                                                                                                                                                                  '/user_profile_photos/default.jpg');

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo) values ('beli1', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Marko', 'Bjelica', 'beli1@maildrop.cc', false, true, 0, null,  'ROLE_TENANT',
                                                                                                                                                                  '/user_profile_photos/default.jpg');

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo) values ('bojan1', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Bojan', 'Baskalo', 'bojan1@maildrop.cc', false, true, 0, null,  'ROLE_TENANT',
                                                                                                                                                                  '/user_profile_photos/default.jpg');

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo) values ('veljko1', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Veljko', 'Tosic', 'veljko1@maildrop.cc', false, true, 0, null,  'ROLE_TENANT',
                                                                                                                                                                  '/user_profile_photos/default.jpg');

insert into system_user (username, password, firstname, lastname, email, deleted, account_non_locked, failed_attempt, lock_time, usertype, profile_photo) values ('ptica1', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Darko', 'Tica', 'ptica1@maildrop.cc', false, true, 0, null,  'ROLE_TENANT',
                                                                                                                                                                  '/user_profile_photos/default.jpg');

INSERT INTO user_role (user_id, role_id) VALUES (1, 1); --  ROLE_ADMIN
INSERT INTO user_role (user_id, role_id) VALUES (2, 2); --  ROLE_OWNER
INSERT INTO user_role (user_id, role_id) VALUES (3, 2); --  ROLE_OWNER
INSERT INTO user_role (user_id, role_id) VALUES (4, 3); --  ROLE_TENANT
INSERT INTO user_role (user_id, role_id) VALUES (5, 3); --  ROLE_TENANT
INSERT INTO user_role (user_id, role_id) VALUES (6, 2); --  ROLE_OWNER
INSERT INTO user_role (user_id, role_id) VALUES (7, 2); --  ROLE_OWNER
INSERT INTO user_role (user_id, role_id) VALUES (8, 3); --  ROLE_TENANT
INSERT INTO user_role (user_id, role_id) VALUES (9, 3); --  ROLE_TENANT