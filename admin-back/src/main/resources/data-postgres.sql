insert into role (name) VALUES ('ROLE_ADMIN');

insert into system_user (username, password) values ('admin', '$2a$12$jy.nsIUdPsHqa8Hw09ngjuOPHZVCPuF3rpNb7OGfzTqXlQ3uEwI8W');

INSERT INTO user_role (user_id, role_id) VALUES (1, 1); --  ROLE_ADMIN

insert into csr (email, common_name, organization, organization_unit, city, state, country, verified)
values('csr1@maildrop.cc', 'csr1name', 'csr1organization', 'csr1organizationunit', 'csr1city', 'csr1state', 'csr1country', true);

insert into csr (email, common_name, organization, organization_unit, city, state, country, verified)
values('csr2@maildrop.cc', 'csr2name', 'csr2organization', 'csr2organizationunit', 'csr2city', 'csr2state', 'csr2country', true);

insert into csr (email, common_name, organization, organization_unit, city, state, country, verified)
values('csr3@maildrop.cc', 'csr3name', 'csr3organization', 'csr3organizationunit', 'csr3city', 'csr3state', 'csr3country', true);

insert into csr (email, common_name, organization, organization_unit, city, state, country, verified)
values('csr4@maildrop.cc', 'csr4name', 'csr4organization', 'csr4organizationunit', 'csr4city', 'csr4state', 'csr4country', false);

insert into csr (email, common_name, organization, organization_unit, city, state, country, verified)
values('csr5@maildrop.cc', 'csr5name', 'csr5organization', 'csr5organizationunit', 'csr5city', 'csr5state', 'csr5country', false);

insert into csr (email, common_name, organization, organization_unit, city, state, country, verified)
values('csr6@maildrop.cc', 'csr6name', 'csr6organization', 'csr6organizationunit', 'csr6city', 'csr6state', 'csr6country', false);