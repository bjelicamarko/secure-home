insert into role (name) VALUES ('ROLE_ADMIN');
insert into role (name) VALUES ('ROLE_OWNER');
insert into role (name) VALUES ('ROLE_TENANT');
insert into role (name) VALUES ('ROLE_UNASSIGNED');

insert into system_user (username, password, firstname, lastname, email, deleted, verified, blocked_until_date) values ('admin', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Admin', 'Admin', 'admin@maildrop.cc', false, true, null);

insert into system_user (username, password, firstname, lastname, email, deleted, verified, blocked_until_date) values ('beli', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Marko', 'Bjelica', 'beli@maildrop.cc', false, true, null);

insert into system_user (username, password, firstname, lastname, email, deleted, verified, blocked_until_date) values ('bojan', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Bojan', 'Baskalo', 'bojan@maildrop.cc', false, true, null);

insert into system_user (username, password, firstname, lastname, email, deleted, verified, blocked_until_date) values ('veljko', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Veljko', 'Tosic', 'veljko@maildrop.cc', false, true, null);

insert into system_user (username, password, firstname, lastname, email, deleted, verified, blocked_until_date) values ('ptica', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Darko', 'Tica', 'ptica@maildrop.cc', false, true, null);

insert into system_user (username, password, firstname, lastname, email, deleted, verified, blocked_until_date) values ('marija', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W',
                                                                                                              'Marija', 'Kovacevic', ',marija@maildrop.cc', false, true, null);


INSERT INTO user_role (user_id, role_id) VALUES (1, 1); --  ROLE_ADMIN
INSERT INTO user_role (user_id, role_id) VALUES (2, 2); --  ROLE_OWNER
INSERT INTO user_role (user_id, role_id) VALUES (3, 2); --  ROLE_OWNER
INSERT INTO user_role (user_id, role_id) VALUES (4, 3); --  ROLE_TENANT
INSERT INTO user_role (user_id, role_id) VALUES (5, 3); --  ROLE_TENANT
INSERT INTO user_role (user_id, role_id) VALUES (6, 4); --  ROLE_UNASSIGNED