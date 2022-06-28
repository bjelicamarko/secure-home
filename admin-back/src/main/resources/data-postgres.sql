insert into role (name) VALUES ('ROLE_ADMIN');

insert into system_user (username, password, account_non_locked, failed_attempt, lock_time) values ('admin', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W', true, 0, null);

INSERT INTO privilege (name) VALUES ('READ_KEYSTORE');
INSERT INTO privilege (name) VALUES ('READ_CERTIFICATE');
INSERT INTO privilege (name) VALUES ('SAVE_CERTIFICATE');
INSERT INTO privilege (name) VALUES ('REVOKE_CERTIFICATE');
INSERT INTO privilege (name) VALUES ('VALIDATE_CERTIFICATE');
INSERT INTO privilege (name) VALUES ('READ_VERIFIED_CSRS');
INSERT INTO privilege (name) VALUES ('FIND_CSR');
INSERT INTO privilege (name) VALUES ('SAVE_CSR');
INSERT INTO privilege (name) VALUES ('VERIFY_CSR');
INSERT INTO privilege (name) VALUES ('UNLOCK_USER');
INSERT INTO privilege (name) VALUES ('READ_KEYSTORE');
INSERT INTO privilege (name) VALUES ('READ_CERTIFICATE');
INSERT INTO privilege (name) VALUES ('SAVE_CERTIFICATE');
INSERT INTO privilege (name) VALUES ('REVOKE_CERTIFICATE');
INSERT INTO privilege (name) VALUES ('VALIDATE_CERTIFICATE');
INSERT INTO privilege (name) VALUES ('READ_VERIFIED_CSRS');

INSERT INTO user_role (user_id, role_id) VALUES (1, 1); --  ROLE_ADMIN

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
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 12);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 13);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 14);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 15);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 16);

insert into csr (email, common_name, organization, organization_unit, city, state, country, security_code, verified)
values('csr1@maildrop.cc', 'csr1name', 'csr1organization', 'csr1organizationunit', 'csr1city', 'csr1state', 'c1', '23i5yg23b5i23vA', true);

insert into csr (email, common_name, organization, organization_unit, city, state, country, security_code, verified)
values('csr2@maildrop.cc', 'csr2name', 'csr2organization', 'csr2organizationunit', 'csr2city', 'csr2state', 'c2', 'Aii5yg24b5i23yf', true);

insert into csr (email, common_name, organization, organization_unit, city, state, country, security_code, verified)
values('csr3@maildrop.cc', 'csr3name', 'csr3organization', 'csr3organizationunit', 'csr3city', 'csr3state', 'c3', '4uo5yg23b5i23vC', true);

insert into csr (email, common_name, organization, organization_unit, city, state, country, security_code, verified)
values('csr4@maildrop.cc', 'csr4name', 'csr4organization', 'csr4organizationunit', 'csr4city', 'csr4state', 'c4', '13i5yg23b5i73vA', false);

insert into csr (email, common_name, organization, organization_unit, city, state, country, security_code, verified)
values('csr5@maildrop.cc', 'csr5name', 'csr5organization', 'csr5organizationunit', 'csr5city', 'csr5state', 'c5', 'BBi5yg23b5i28vA', false);

insert into csr (email, common_name, organization, organization_unit, city, state, country, security_code, verified)
values('csr6@maildrop.cc', 'csr6name', 'csr6organization', 'csr6organizationunit', 'csr6city', 'csr6state', 'c6', 'hri5yu27b8928vy', false);
