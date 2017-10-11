create table distributor
(
       did        number(10)  primary key,
       dname      varchar2(20) not null unique,
       dtel       number(20)   not null unique,
       demail     varchar2(20) unique
);

create table goods
(
       gid          number(13) primary key,
       gname        varchar2(50) not null unique,
       gprice       number(18,2) not null,
       gcost_price  number(18,2) not null,
       gstock       number(18)   not null,
       gdiscount    number(5,2)  not null check (gdiscount between 0.01 and 1),
       gcategory    varchar2(20) not null, 
       gpoint       number(10)   not null,
       gunit        varchar2(20),
       gremark      varchar2(100)
);


create table s_member
(
       mid        number(10) primary key,
       mname      varchar2(50) not null,
       msex       char(2) not null check(msex in ('ÄÐ','Å®'))£¬
       mgrade     varchar2(10) not null references member_grade(mgrade),
       mmonetary  number(10,2) not null,
       mtel       number(20)   not null unique,
       maccount   number(10,2) not null,
       mpassword  varchar2(10) not null	 	
);


create table member_grade
(
       mgrade    varchar2(10) primary key,
       mdiscount  number(3,2)  not null check (mdiscount between 0.01 and 1)

);


create table purchase
(
       pid        	number(18) primary key,
       sid        	number(10) not null references s_user(sid),
       pdate      	date       default sysdate    not null, 
       remark 		varchar2(50)
);

create table purchase_info
(
       pid        number(18) not null references purchase(pid) on delete cascade,
       gid        number(10) not null references goods(gid)on delete set null,
       pnum       number(10) not null
);

create table  s_user
(
       sid             number(10) primary key,
       eid             number(8)  references employees(eid) on delete cascade not null,
       username      varchar2(10) not null unique,
       password  	 varchar2(20) not null,
       usertype      varchar2(10) not null check (usertype in ('admin','employee'))
       
);


create sequence s_user_seq
       start with 10000
       increment by 1
       minvalue 10000
       maxvalue 100000
       nocycle
       cache       10;
      

create table SALES
(
  salesid    NUMBER(18) primary key,
  eid        NUMBER(8) not null references EMPLOYEES (EID) on delete set null,
  mid        NUMBER(10) not null references S_MEMBER (MID) on delete set null,
  sdate      DATE default sysdate not null,
  mgathering NUMBER(10,2) not null,
  mchange    NUMBER(10,2) not null,
  remark 	 varchar2(50)
);

create  table sales_info
(
       salesid      number(18) not null references sales(salesid) on delete cascade,
       gid          number(10) not null references goods(gid) on delete set null,
       snum         number(10) not null
       
);


create table employees
(
       eid       number(8) primary  key,
       ename     varchar2(20) not null,
       esex      char(2) not null check(esex in ('ÄÐ','Å®')),
       eadress   varchar2(100),
       etel      number(20) not null unique,
       email     varchar2(50) unique
);

