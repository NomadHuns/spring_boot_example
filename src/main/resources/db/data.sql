insert into roles (role)
values ('ADMIN'),
       ('MEMBER'),
       ('STORE_MANAGER');
-- 비밀번호 1234
insert into users (username, password, email, status, create_date, update_date)
values ('tadmin', '$2a$10$FUXKGejXq4UQY5ADcdDGweh9YJzM3JntrbeDogG/AF.BwnqMNnnCW', 'tadmin@tadmin.com', 'COMPLETE', now(), now()),
       ('tuser', '$2a$10$FUXKGejXq4UQY5ADcdDGweh9YJzM3JntrbeDogG/AF.BwnqMNnnCW', 'tuser@tuser.com', 'COMPLETE', now(), now()),
       ('tmanager', '$2a$10$FUXKGejXq4UQY5ADcdDGweh9YJzM3JntrbeDogG/AF.BwnqMNnnCW', 'tmanager@tmanager.com', 'COMPLETE', now(), now());

insert into user_roles (user_id, role_id)
values (1, 1),
       (2, 2),
       (3, 3);