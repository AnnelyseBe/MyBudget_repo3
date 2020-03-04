create table account (id bigint not null, active bit, currency varchar(255), description longtext, name varchar(255), number varchar(255), primary key (id)) engine=InnoDB;
create table category (id bigint not null, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB;
create table costpost (id bigint not null, active bit, description varchar(255), flowtype varchar(255), name varchar(255), category_id bigint, primary key (id)) engine=InnoDB;
create table hibernate_sequence (next_val bigint) engine=InnoDB;
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
insert into hibernate_sequence values ( 1 );
create table tag (id bigint not null, name varchar(255), primary key (id)) engine=InnoDB;
create table transaction (id bigint not null, date date, description varchar(255), extra varchar(255), inflow decimal(19,2), notes varchar(255), outflow decimal(19,2), recurring varchar(255), validated bit, account_id bigint, costpost_id bigint, primary key (id)) engine=InnoDB;
create table transaction_tags (transaction_id bigint not null, tag_id bigint not null) engine=InnoDB;
alter table costpost add constraint FKao2lklc9s7vum94womwyigrm0 foreign key (category_id) references category (id);
alter table transaction add constraint FK6g20fcr3bhr6bihgy24rq1r1b foreign key (account_id) references account (id);
alter table transaction add constraint FK4rff3qjksyjqsn45w32df9uv3 foreign key (costpost_id) references costpost (id);
alter table transaction_tags add constraint FKjcsl3xvcgdithk402pdyvpa7f foreign key (tag_id) references tag (id);
alter table transaction_tags add constraint FKdrcktor3s64lgwkbo3m47abj4 foreign key (transaction_id) references transaction (id);