use javaBook;
create table Babyname
(
    year   integer,
    name   varchar(50),
    gender char(1),
    count  integer,
    constraint pkBabyName primary key (year, name, gender)
);