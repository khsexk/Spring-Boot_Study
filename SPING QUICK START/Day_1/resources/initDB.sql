create table `SQS`.users(
	id 			varchar(8) primary key,
    password 	varchar(8),
    name 		varchar(20),
    role 		varchar(5)
);

insert into `SQS`.users values('test', 'test123', '관리자', 'admin');
insert into `SQS`.users values('user1', 'user1', '홍길동', 'user');

create table `SQS`.board(
	SEQ int(5) primary key,
	title varchar(200),
    writer varchar(20),
    content varchar(2000),
    regdate timestamp DEFAULT now(),
    cnt int(5) default 0
);

insert into `SQS`.board(SEQ, TITLE, WRITER, CONTENT) values(1, '가입인사', '관리자', '잘 부탁드립니다....');