/* 'infinite scroll' test data */
drop table infinite_test;

create table infinite_test (id integer,value varchar(500));

alter table infinite_test add constraint pk_ini primary key (id);

\echo "Warn,the next command to add about 150 megabytes of test data"
\echo "Do not forget to remove them through the 'delete from infinite_test'"

insert into infinite_test 
    select s.a,'biiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiig value_'||s.a::varchar 
    from generate_series(0,999999) as s(a);


create or replace view v_test_infi
as select max(id) from infinite_test
union select min(id) from infinite_test
order by 1;

delete from infinite_test;
