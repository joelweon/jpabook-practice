Hibernate: 
```sql
    drop table item if exists
```
Hibernate: 
```sql
    drop table member if exists
```
Hibernate: 
```sql
    drop table order_item if exists
```
Hibernate: 
```sql
    drop table orders if exists
```
Hibernate: 
```sql
    drop sequence if exists hibernate_sequence
```
Hibernate:
```sql
create sequence hibernate_sequence start with 1 increment by 1
```

Hibernate: 
```sql
    create table item (
       item_id bigint not null,
        name varchar(255),
        price integer not null,
        stock_quantity integer not null,
        primary key (item_id)
    )
```
Hibernate: 
```sql
    create table member (
       member_id bigint not null,
        city varchar(255),
        name varchar(255),
        street varchar(255),
        zipcode varchar(255),
        primary key (member_id)
    )
```
Hibernate: 
```sql
    create table order_item (
       order_item_id bigint not null,
        count integer not null,
        item_id bigint,
        order_id bigint,
        order_price integer not null,
        primary key (order_item_id)
    )
```
Hibernate: 
```sql
    create table orders (
       order_id bigint not null,
        member_id bigint,
        order_date timestamp,
        status varchar(255),
        primary key (order_id)
    )
```