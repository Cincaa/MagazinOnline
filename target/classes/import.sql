delete from products;
delete from users;

insert into products (name, price) values('Product 1', 110);
insert into products (name, price) values('Product 2', 50);
insert into products (name, price) values('Product 3', 99.99);
insert into products (name, price) values('Product 4', 0.99);

insert into users (username, password, full_name, user_type, reg_date, enabled) values('user1', '{bcrypt}$2a$16$BTweyIlslOpEyz0HQTS7n.qhpr35TjghrZwwZWv9RtIk.LBqBpH0y', 'John Doe', 'CUSTOMER', now(), 1);
insert into users (username, password, full_name, user_type, reg_date, enabled) values('admin1', '{bcrypt}$2a$16$BTweyIlslOpEyz0HQTS7n.qhpr35TjghrZwwZWv9RtIk.LBqBpH0y', 'Admin', 'ADMIN', now(), 1);

insert into cart (user_id) values(1);
insert into cart (user_id) values(2);
