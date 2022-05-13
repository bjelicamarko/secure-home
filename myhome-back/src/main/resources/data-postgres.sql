insert into role (name) VALUES ('ROLE_ADMIN');
insert into role (name) VALUES ('ROLE_OWNER');
insert into role (name) VALUES ('ROLE_TENANT');
insert into role (name) VALUES ('ROLE_UNVERIFIED');

insert into system_user (username, password, firstname, lastname, email, deleted, blocked_until_date, salt) values ('admin', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Admin', 'Admin', 'admin@maildrop.cc', false, null, 'so');

insert into system_user (username, password, firstname, lastname, email, deleted, blocked_until_date, salt) values ('beli', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Marko', 'Bjelica', 'beli@maildrop.cc', false, null, 'so');

insert into system_user (username, password, firstname, lastname, email, deleted, blocked_until_date, salt) values ('bojan', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Bojan', 'Baskalo', 'bojan@maildrop.cc', false, null, 'so');

insert into system_user (username, password, firstname, lastname, email, deleted, blocked_until_date, salt) values ('veljko', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Veljko', 'Tosic', 'veljko@maildrop.cc', false, null, 'so');

insert into system_user (username, password, firstname, lastname, email, deleted, blocked_until_date, salt) values ('ptica', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Darko', 'Tica', 'ptica@maildrop.cc', false, null, 'so');

insert into system_user (username, password, firstname, lastname, email, deleted, blocked_until_date, salt) values ('marija', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Marija', 'Kovacevic', ',marija@maildrop.cc', false, null, 'so');


INSERT INTO user_role (user_id, role_id) VALUES (1, 1); --  ROLE_ADMIN
INSERT INTO user_role (user_id, role_id) VALUES (2, 2); --  ROLE_OWNER
INSERT INTO user_role (user_id, role_id) VALUES (3, 2); --  ROLE_OWNER
INSERT INTO user_role (user_id, role_id) VALUES (4, 3); --  ROLE_TENANT
INSERT INTO user_role (user_id, role_id) VALUES (5, 3); --  ROLE_TENANT
INSERT INTO user_role (user_id, role_id) VALUES (6, 4); --  ROLE_UNVERIFIED