insert into role (name) VALUES ('ROLE_ADMIN');
insert into role (name) VALUES ('ROLE_OWNER');
insert into role (name) VALUES ('ROLE_TENANT');
insert into role (name) VALUES ('ROLE_UNASSIGNED');


INSERT INTO privilege (name) VALUES ('READ_USERS_WITHOUT_ADMIN'); -- 1
INSERT INTO privilege (name) VALUES ('SEARCH_USERS'); -- 2
INSERT INTO privilege (name) VALUES ('DELETE_USER'); -- 3
INSERT INTO privilege (name) VALUES ('GET_REAL_ESTATE_BY_ID'); -- 4
INSERT INTO privilege (name) VALUES ('SAVE_REAL_ESTATE'); -- 5
INSERT INTO privilege (name) VALUES ('SAVE_USER_REAL_ESTATE'); -- 6
INSERT INTO privilege (name) VALUES ('CHANGE_ROLE_USER_REAL_ESTATE'); -- 7
INSERT INTO privilege (name) VALUES ('GET_USER_REAL_ESTATE_TO_ASSIGN'); -- 8
INSERT INTO privilege (name) VALUES ('GET_USER_REAL_ESTATES'); -- 9
INSERT INTO privilege (name) VALUES ('UNLOCK_USER'); -- 10
INSERT INTO privilege (name) VALUES ('DELETE_USER_REAL_ESTATE'); -- 11
INSERT INTO privilege (name) VALUES ('GET_USER_REAL_ESTATES'); -- 12
INSERT INTO privilege (name) VALUES ('GET_USER_REAL_ESTATE'); -- 13
INSERT INTO privilege (name) VALUES ('GET_DEVICES'); -- 14
INSERT INTO privilege (name) VALUES ('GET_REAL_ESTATES'); -- 15
INSERT INTO privilege (name) VALUES ('GET_REAL_ESTATE_DEVICES'); -- 16
INSERT INTO privilege (name) VALUES ('GET_ALL_MESSAGES_FROM_DEVICE'); -- 17
INSERT INTO privilege (name) VALUES ('FILTER_ALL_MESSAGES_FROM_DEVICE'); -- 18
INSERT INTO privilege (name) VALUES ('CREATE_REPORT'); -- 19
INSERT INTO privilege (name) VALUES ('READ_LOGS'); -- 20
INSERT INTO privilege (name) VALUES ('FILTER_ALL_LOGS'); -- 21
INSERT INTO privilege (name) VALUES ('CREATE_RULE'); -- 22
INSERT INTO privilege (name) VALUES ('GET_RULES'); -- 23
INSERT INTO privilege (name) VALUES ('DELETE_RULE'); -- 24
INSERT INTO privilege (name) VALUES ('GET_DEVICE'); -- 25
INSERT INTO privilege (name) VALUES ('GET_NOTIFICATIONS'); -- 26
INSERT INTO privilege (name) VALUES ('UPDATE_DEVICE'); -- 27
INSERT INTO privilege (name) VALUES ('GET_ALL_MESSAGES_FROM_REAL_ESTATE'); -- 28
INSERT INTO privilege (name) VALUES ('CREATE_ALL_REPORT'); -- 29
INSERT INTO privilege (name) VALUES ('FILTER_ALL_MESSAGES_FROM_ESTATE'); -- 30
INSERT INTO privilege (name) VALUES ('GET_LOWEST_READ_PERIOD'); -- 31

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
    values (true, false, 'zivko@maildrop.cc', 0, 'Zivko', 'Zivanovic', null, '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W', '/user_profile_photos/default.jpg', 'ROLE_UNASSIGNED', 'zivko', false);

insert into public.system_user (account_non_locked, deleted, email, failed_attempt, firstname, lastname, lock_time, password, profile_photo, usertype, username, verified)
    values (true, false, 'uredjaj@maildrop.cc', 0, 'Uredjaj', 'Uredjajic', null, '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W', '/user_profile_photos/default.jpg', 'ROLE_UNASSIGNED', 'devices', true);

insert into public.system_user (account_non_locked, deleted, email, failed_attempt, firstname, lastname, lock_time, password, profile_photo, usertype, username, verified)
values (true, false, 'uredjaj1@maildrop.cc', 0, 'Zli-Uredjaj', 'Zli-Uredjajic', null, '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W', '/user_profile_photos/default.jpg', 'ROLE_UNASSIGNED', 'panicdev', true);


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

INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 17);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 17);

INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 18);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 18);

INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 19);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 19);

INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 20);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 21);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 22);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 23);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 24);

INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 25);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 25);

INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 22);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 22);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 22);

INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 26);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 26);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 26);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 27);

INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 28);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 28);

INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 29);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 29);

INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 30);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 30);

INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 31);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (3, 31);

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
INSERT INTO device (name, photo, read_period) VALUES ('Air conditioner', '/devices_photos/air_conditioner.png', 10000);
INSERT INTO device (name, photo, read_period) VALUES ('Fridge', '/devices_photos/fridge.png', 10000);
INSERT INTO device (name, photo, read_period) VALUES ('Front door', '/devices_photos/front_door.png', 10000);
INSERT INTO device (name, photo, read_period) VALUES ('Backyard door', '/devices_photos/backyard_doors.jpg', 10000);
INSERT INTO device (name, photo, read_period) VALUES ('Smoke detector', '/devices_photos/smoke_detector.png', 10000);
INSERT INTO device (name, photo, read_period) VALUES ('Cooker', '/devices_photos/cooker.png', 10000);
INSERT INTO device (name, photo, read_period) VALUES ('Water heater', '/devices_photos/water_heater.png', 10000);

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


insert into device_message (device_name, message, message_status, timestamp_value) values
('Air conditioner', 'Temperature is optimal', 'REGULAR', 1653429600000); -- 2022-05-25
insert into device_message (device_name, message, message_status, timestamp_value) values
('Fridge', 'Temperature is optimal', 'REGULAR', 1653429600000); -- 2022-05-25
insert into device_message (device_name, message, message_status, timestamp_value) values
('Air conditioner', 'Temperature is optimal', 'REGULAR', 1653429600000); -- 2022-05-25
insert into device_message (device_name, message, message_status, timestamp_value) values
('Fridge', 'Temperature is optimal', 'REGULAR', 1653429600000); -- 2022-05-25
insert into device_message (device_name, message, message_status, timestamp_value) values
('Air conditioner', 'Temperature is optimal', 'REGULAR', 1653429600000); -- 2022-05-25

insert into device_message (device_name, message, message_status, timestamp_value) values
('Air conditioner', 'Temperature is too high', 'PANIC', 1653516000000); -- 2022-05-26
insert into device_message (device_name, message, message_status, timestamp_value) values
('Air conditioner', 'Temperature is optimal', 'REGULAR', 1653516000000); -- 2022-05-26
insert into device_message (device_name, message, message_status, timestamp_value) values
('Fridge', 'Temperature is optimal', 'REGULAR', 1653516000000); -- 2022-05-26
insert into device_message (device_name, message, message_status, timestamp_value) values
('Air conditioner', 'Temperature is too high', 'PANIC', 1653516000000); -- 2022-05-26
insert into device_message (device_name, message, message_status, timestamp_value) values
('Cooker', 'Stove is on', 'REGULAR', 1653516000000); -- 2022-05-26
insert into device_message (device_name, message, message_status, timestamp_value) values
('Cooker', 'Stove is on', 'REGULAR', 1653516000000); -- 2022-05-26
insert into device_message (device_name, message, message_status, timestamp_value) values
('Cooker', 'Stove is burning out, temperature is too high, please turn device off.', 'PANIC', 1653516000000); -- 2022-05-26
insert into device_message (device_name, message, message_status, timestamp_value) values
('Air conditioner', 'Temperature is optimal', 'REGULAR', 1653516000000); -- 2022-05-26
insert into device_message (device_name, message, message_status, timestamp_value) values
('Air conditioner', 'Temperature is optimal', 'REGULAR', 1653516000000); -- 2022-05-26
insert into device_message (device_name, message, message_status, timestamp_value) values
('Air conditioner', 'Temperature is optimal', 'REGULAR', 1653516000000); -- 2022-05-26

insert into device_message (device_name, message, message_status, timestamp_value) values
('Air conditioner', 'Temperature is too high', 'PANIC', 1653602400000); -- 2022-05-27
insert into device_message (device_name, message, message_status, timestamp_value) values
('Smoke detector', 'No smoke detected', 'REGULAR', 1653602400000); -- 2022-05-27
insert into device_message (device_name, message, message_status, timestamp_value) values
('Smoke detector', 'Smoke concentration is too high, please check the kitchen.', 'PANIC', 1653602400000); -- 2022-05-27
insert into device_message (device_name, message, message_status, timestamp_value) values
('Air conditioner', 'Temperature is optimal', 'REGULAR', 1653602400000); -- 2022-05-27
insert into device_message (device_name, message, message_status, timestamp_value) values
('Air conditioner', 'Temperature is too high', 'PANIC', 1653602400000); -- 2022-05-27
insert into device_message (device_name, message, message_status, timestamp_value) values
('Air conditioner', 'Temperature is optimal', 'REGULAR', 1653602400000); -- 2022-05-27
insert into device_message (device_name, message, message_status, timestamp_value) values
('Smoke detector', 'Smoke concentration is too high, please check the kitchen.', 'PANIC', 1653602400000); -- 2022-05-27
insert into device_message (device_name, message, message_status, timestamp_value) values
('Air conditioner', 'Temperature is optimal', 'REGULAR', 1653602400000); -- 2022-05-27
insert into device_message (device_name, message, message_status, timestamp_value) values
('Air conditioner', 'Temperature is too high', 'PANIC', 1653602400000); -- 2022-05-27
insert into device_message (device_name, message, message_status, timestamp_value) values
('Air conditioner', 'Temperature is optimal', 'REGULAR', 1653602400000); -- 2022-05-27

insert into rule (rule_type, regex_pattern, log_level) values ('LOG', 'locked account', 'ERROR');
insert into rule (rule_type, regex_pattern, log_level) values ('LOG', 'bad credentials', 'ERROR');

insert into rule (rule_type, regex_pattern, device_name) values ('DATABASE', '.*', 'Air conditioner');
insert into rule (rule_type, regex_pattern, device_name) values ('DATABASE', '.*', 'Fridge');
insert into rule (rule_type, regex_pattern, device_name) values ('DATABASE', '.*', 'Front door');
insert into rule (rule_type, regex_pattern, device_name) values ('DATABASE', '.*', 'Backyard door');
insert into rule (rule_type, regex_pattern, device_name) values ('DATABASE', '.*', 'Smoke detector');
insert into rule (rule_type, regex_pattern, device_name) values ('DATABASE', '.*', 'Cooker');
insert into rule (rule_type, regex_pattern, device_name) values ('DATABASE', '.*', 'Water heater');

insert into alarm_notification (message, alarm_type, device_name, timestamp, username, seen) values
    ('Temperature is optimal', 'DEVICE', 'Air conditioner', 1653602400000, 'dunjica', false);
insert into alarm_notification (message, alarm_type, device_name, timestamp, username, seen) values
    ('Temperature is optimal', 'DEVICE', 'Air conditioner', 1653602400000, 'dunjica', false);
insert into alarm_notification (message, alarm_type, device_name, timestamp, username, seen) values
    ('Temperature is optimal', 'DEVICE', 'Air conditioner', 1653602400000, 'dunjica', false);
insert into alarm_notification (message, alarm_type, device_name, timestamp, username, seen) values
    ('Temperature is optimal', 'DEVICE', 'Air conditioner', 1653602400000, 'dunjica', false);
insert into alarm_notification (message, alarm_type, device_name, timestamp, username, seen) values
    ('Temperature is optimal', 'DEVICE', 'Air conditioner', 1653602400000, 'dunjica', false);
insert into alarm_notification (message, alarm_type, device_name, timestamp, username, seen) values
    ('Temperature is optimal', 'DEVICE', 'Air conditioner', 1653602400000, 'dunjica', false);
insert into alarm_notification (message, alarm_type, device_name, timestamp, username, seen) values
    ('Temperature is optimal', 'DEVICE', 'Air conditioner', 1653602400000, 'dunjica', false);
insert into alarm_notification (message, alarm_type, device_name, timestamp, username, seen) values
    ('Temperature is optimal', 'DEVICE', 'Air conditioner', 1653602400000, 'dunjica', false);
insert into alarm_notification (message, alarm_type, device_name, timestamp, username, seen) values
    ('Temperature is optimal', 'DEVICE', 'Air conditioner', 1653602400000, 'dunjica', false);
insert into alarm_notification (message, alarm_type, device_name, timestamp, username, seen) values
    ('Temperature is optimal', 'DEVICE', 'Air conditioner', 1653602400000, 'dunjica', false);
insert into alarm_notification (message, alarm_type, device_name, timestamp, username, seen) values
    ('Temperature is optimal', 'DEVICE', 'Air conditioner', 1653602400000, 'dunjica', false);
insert into alarm_notification (message, alarm_type, device_name, timestamp, username, seen) values
    ('Temperature is optimal', 'DEVICE', 'Air conditioner', 1653602400000, 'dunjica', false);
insert into alarm_notification (message, alarm_type, device_name, timestamp, username, seen) values
    ('Temperature is optimal', 'DEVICE', 'Air conditioner', 1653602400000, 'dunjica', false);
insert into alarm_notification (message, alarm_type, device_name, timestamp, username, seen) values
    ('Temperature is optimal', 'DEVICE', 'Air conditioner', 1653602400000, 'dunjica', false);
insert into alarm_notification (message, alarm_type, device_name, timestamp, username, seen) values
    ('Temperature is optimal', 'DEVICE', 'Air conditioner', 1653602400000, 'dunjica', false);
insert into alarm_notification (message, alarm_type, device_name, timestamp, username, seen) values
    ('Temperature is optimal', 'DEVICE', 'Air conditioner', 1653602400000, 'dunjica', false);
