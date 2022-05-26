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
INSERT INTO privilege (name) VALUES ('UNLOCK_USER');
INSERT INTO privilege (name) VALUES ('DELETE_USER_REAL_ESTATE');
INSERT INTO privilege (name) VALUES ('GET_USER_REAL_ESTATES');
INSERT INTO privilege (name) VALUES ('GET_USER_REAL_ESTATE');
INSERT INTO privilege (name) VALUES ('GET_DEVICES');
INSERT INTO privilege (name) VALUES ('GET_REAL_ESTATES');
INSERT INTO privilege (name) VALUES ('GET_REAL_ESTATE_DEVICES');

insert into public.system_user (account_non_locked, deleted, email, failed_attempt, firstname, lastname, lock_time, password, profile_photo, usertype, username, verified)
    values (true, false, 'admin@maildrop.cc', 0, 'Admin', 'Admin', null, '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W', '/user_profile_photos/default.jpg', 'ROLE_ADMIN', 'admin', true);
insert into public.system_user (account_non_locked, deleted, email, failed_attempt, firstname, lastname, lock_time, password, profile_photo, usertype, username, verified)
    values (true, false, 'dunja@maildrop.cc', 0, 'Dunja', 'Dunjica', null, '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W', '/user_profile_photos/default.jpg', 'ROLE_OWNER', 'dunjica', true);
insert into public.system_user (account_non_locked, deleted, email, failed_attempt, firstname, lastname, lock_time, password, profile_photo, usertype, username, verified)
    values (true, false, 'mico@maildrop.cc', 0, 'Mico', 'Bradina', null, '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W', '/user_profile_photos/default.jpg', 'ROLE_OWNER', 'mico', true);
insert into public.system_user (account_non_locked, deleted, email, failed_attempt, firstname, lastname, lock_time, password, profile_photo, usertype, username, verified)
    values (true, false, 'charlie@maildrop.cc', 0, 'Charlie', 'Sheen', null, '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W', '/user_profile_photos/default.jpg', 'ROLE_TENANT', 'charlie', true);
insert into public.system_user (account_non_locked, deleted, email, failed_attempt, firstname, lastname, lock_time, password, profile_photo, usertype, username, verified)
    values (true, false, 'visnja@maildrop.cc', 0, 'Visnja', 'Visnjica', null, '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W', '/user_profile_photos/visnjica.png', 'ROLE_TENANT', 'visnjica', true);
insert into public.system_user (account_non_locked, deleted, email, failed_attempt, firstname, lastname, lock_time, password, profile_photo, usertype, username, verified)
    values (true, false, 'beli@maildrop.cc', 0, 'Marko', 'Bjelica', null, '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W', '/user_profile_photos/beli.png', 'ROLE_OWNER', 'beli', true);
insert into public.system_user (account_non_locked, deleted, email, failed_attempt, firstname, lastname, lock_time, password, profile_photo, usertype, username, verified)
    values (true, true, 'veljko@maildrop.cc', 0, 'Veljko', 'Tosic', null, '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W', '/user_profile_photos/velja.png', 'ROLE_TENANT', 'veljko', true);
insert into public.system_user (account_non_locked, deleted, email, failed_attempt, firstname, lastname, lock_time, password, profile_photo, usertype, username, verified)
    values (true, false, 'bojan@maildrop.cc', 0, 'Bojan', 'Baskalo', null, '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W', '/user_profile_photos/boki.png', 'ROLE_OWNER', 'bojan', true);
insert into public.system_user (account_non_locked, deleted, email, failed_attempt, firstname, lastname, lock_time, password, profile_photo, usertype, username, verified)
    values (false, false, 'ptica@maildrop.cc', 0, 'Darko', 'Tica', null, '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W', '/user_profile_photos/ptica1.png', 'ROLE_TENANT', 'ptica', true);

insert into public.system_user (account_non_locked, deleted, email, failed_attempt, firstname, lastname, lock_time, password, profile_photo, usertype, username, verified)
    values (true, false, 'mirko@maildrop.cc', 0, 'Mirko', 'Miocic', null, '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W', '/user_profile_photos/default.jpg', 'ROLE_UNASSIGNED', 'mirko', true);

insert into public.system_user (account_non_locked, deleted, email, failed_attempt, firstname, lastname, lock_time, password, profile_photo, usertype, username, verified)
    values (true, false, 'zivko@maildrop.cc', 0, 'Zivko', 'Zivanovic', null, '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W', '/user_profile_photos/default.jpg', 'ROLE_UNASSIGNED', 'zivko', true);

insert into public.system_user (account_non_locked, deleted, email, failed_attempt, firstname, lastname, lock_time, password, profile_photo, usertype, username, verified)
    values (true, false, 'uredjaj@maildrop.cc', 0, 'Uredjaj', 'Uredjajic', null, '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W', '/user_profile_photos/default.jpg', 'ROLE_UNASSIGNED', 'devices', true);

INSERT INTO user_role (user_id, role_id) VALUES (1, 1); --  ROLE_ADMIN
INSERT INTO user_role (user_id, role_id) VALUES (2, 2); --  ROLE_OWNER
INSERT INTO user_role (user_id, role_id) VALUES (3, 2); --  ROLE_OWNER
INSERT INTO user_role (user_id, role_id) VALUES (4, 3); --  ROLE_TENANT
INSERT INTO user_role (user_id, role_id) VALUES (5, 3); --  ROLE_TENANT
INSERT INTO user_role (user_id, role_id) VALUES (6, 2); --  ROLE_OWNER
INSERT INTO user_role (user_id, role_id) VALUES (7, 3); --  ROLE_TENANT
INSERT INTO user_role (user_id, role_id) VALUES (8, 2); --  ROLE_OWNER
INSERT INTO user_role (user_id, role_id) VALUES (8, 3); --  ROLE_OWNER
INSERT INTO user_role (user_id, role_id) VALUES (9, 3); --  ROLE_TENANT
INSERT INTO user_role (user_id, role_id) VALUES (10, 4); --  ROLE_UNASSIGNED
INSERT INTO user_role (user_id, role_id) VALUES (11, 4); --  ROLE_UNASSIGNED

INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 1);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 2);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 3);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 4);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 5);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 6);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 7);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 8);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 9);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 10);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 11);

INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 12);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 12);

INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 13);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 13);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 14);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 15);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 16);

INSERT INTO real_estate (name, photo) VALUES ('Kuca 1', '/real_estates_photos/house.png');
INSERT INTO real_estate (name, photo) VALUES ('Kuca 2', '/real_estates_photos/house.png');
INSERT INTO real_estate (name, photo) VALUES ('Stan 1', '/real_estates_photos/house.png');
INSERT INTO real_estate (name, photo) VALUES ('Stan 2', '/real_estates_photos/apartment.jpg');
INSERT INTO real_estate (name, photo) VALUES ('Vikendica 1', '/real_estates_photos/cottage.jpg');
INSERT INTO real_estate (name, photo) VALUES ('Vikendica 2', '/real_estates_photos/cottage.jpg');
INSERT INTO real_estate (name, photo) VALUES ('Vikendica 3', '/real_estates_photos/cottage.jpg');
INSERT INTO real_estate (name, photo) VALUES ('Vikendica 4', '/real_estates_photos/cottage.jpg');

INSERT INTO user_real_estate (user_id, real_estate_id, role) VALUES (2, 1, 'OWNER');
INSERT INTO user_real_estate (user_id, real_estate_id, role) VALUES (3, 3, 'OWNER');
INSERT INTO user_real_estate (user_id, real_estate_id, role) VALUES (4, 3, 'TENANT');
INSERT INTO user_real_estate (user_id, real_estate_id, role) VALUES (5, 3, 'TENANT');
INSERT INTO user_real_estate (user_id, real_estate_id, role) VALUES (8, 2, 'OWNER');
INSERT INTO user_real_estate (user_id, real_estate_id, role) VALUES (8, 5, 'OWNER');
INSERT INTO user_real_estate (user_id, real_estate_id, role) VALUES (9, 7, 'TENANT');
INSERT INTO user_real_estate (user_id, real_estate_id, role) VALUES (6, 6, 'OWNER');
INSERT INTO user_real_estate (user_id, real_estate_id, role) VALUES (7, 8, 'TENANT');



-- tabela device
INSERT INTO device (name, photo) VALUES ('Air conditioner', '/devices_photos/air_conditioner.png');
INSERT INTO device (name, photo) VALUES ('Fridge', '/devices_photos/fridge.png');
INSERT INTO device (name, photo) VALUES ('Front door', '/devices_photos/front_door.png');
INSERT INTO device (name, photo) VALUES ('Backyard door', '/devices_photos/backyard_doors.jpg');
INSERT INTO device (name, photo) VALUES ('Smoke detector', '/devices_photos/smoke_detector.png');
INSERT INTO device (name, photo) VALUES ('Cooker', '/devices_photos/cooker.png');
INSERT INTO device (name, photo) VALUES ('Water heater', '/devices_photos/water_heater.png');

INSERT INTO real_estate_device (real_estate_id, device_id) VALUES (1, 1);
INSERT INTO real_estate_device (real_estate_id, device_id) VALUES (1, 3);
INSERT INTO real_estate_device (real_estate_id, device_id) VALUES (1, 6);
INSERT INTO real_estate_device (real_estate_id, device_id) VALUES (1, 7);
INSERT INTO real_estate_device (real_estate_id, device_id) VALUES (2, 1);
INSERT INTO real_estate_device (real_estate_id, device_id) VALUES (2, 2);
INSERT INTO real_estate_device (real_estate_id, device_id) VALUES (2, 7);
INSERT INTO real_estate_device (real_estate_id, device_id) VALUES (3, 1);
INSERT INTO real_estate_device (real_estate_id, device_id) VALUES (3, 2);
INSERT INTO real_estate_device (real_estate_id, device_id) VALUES (3, 7);
INSERT INTO real_estate_device (real_estate_id, device_id) VALUES (4, 2);
INSERT INTO real_estate_device (real_estate_id, device_id) VALUES (4, 7);
INSERT INTO real_estate_device (real_estate_id, device_id) VALUES (5, 2);
INSERT INTO real_estate_device (real_estate_id, device_id) VALUES (5, 7);
INSERT INTO real_estate_device (real_estate_id, device_id) VALUES (6, 1);
INSERT INTO real_estate_device (real_estate_id, device_id) VALUES (6, 7);
INSERT INTO real_estate_device (real_estate_id, device_id) VALUES (7, 3);
INSERT INTO real_estate_device (real_estate_id, device_id) VALUES (7, 4);
INSERT INTO real_estate_device (real_estate_id, device_id) VALUES (7, 7);
INSERT INTO real_estate_device (real_estate_id, device_id) VALUES (8, 4);
INSERT INTO real_estate_device (real_estate_id, device_id) VALUES (8, 7);
