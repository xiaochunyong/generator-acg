create table user
(
	id int auto_increment
		primary key,
	username varchar(50) null,
	password varchar(50) null,
	nickname varchar(50) null,
	mobile varchar(20) null,
	status int not null,
	create_dt datetime null,
	update_dt datetime not null
);