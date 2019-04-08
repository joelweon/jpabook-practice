Hibernate: 
```sql
    create table album (
       artist varchar(255),
        etc varchar(255),
        item_id bigint not null,
        primary key (item_id)
    )
```
Hibernate:
```sql
    
    create table book (
       author varchar(255),
        isbn varchar(255),
        item_id bigint not null,
        primary key (item_id)
    )
```
Hibernate:
```sql
    
    create table category (
       category_id bigint not null,
        name varchar(255),
        category_parent_id bigint,
        primary key (category_id)
    )
```
Hibernate:
```sql
    
    create table category_item (
       category_id bigint not null,
        item_id bigint not null
    )
```
Hibernate:
```sql
    
    create table delivery (
       delivery_id bigint not null,
        city varchar(255),
        delivery_status varchar(255),
        street varchar(255),
        zipcode varchar(255),
        primary key (delivery_id)
    )
```
Hibernate:
```sql
    
    create table item (
       item_type varchar(31) not null,
        item_id bigint not null,
        create_date timestamp,
        last_modified_date timestamp,
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
        create_date timestamp,
        last_modified_date timestamp,
        city varchar(255),
        name varchar(255),
        street varchar(255),
        zipcode varchar(255),
        primary key (member_id)
    )
```
Hibernate:
```sql
    
    create table movie (
       actor varchar(255),
        director varchar(255),
        item_id bigint not null,
        primary key (item_id)
    )
```
Hibernate:
```sql
    
    create table order_item (
       order_item_id bigint not null,
        count integer not null,
        order_price integer not null,
        item_id bigint,
        order_id bigint,
        primary key (order_item_id)
    )
```
Hibernate:
```sql
    
    create table orders (
       order_id bigint not null,
        order_date timestamp,
        status varchar(255),
        delivery_id bigint,
        member_id bigint,
        primary key (order_id)
    )
```
Hibernate:
```sql
    
    alter table album 
       add constraint FKminb9nib0bo4t9c4kpltxtuab 
       foreign key (item_id) 
       references item
```
Hibernate:
```sql
    
    alter table book 
       add constraint FKgohd8evkxf3j9a0p4jk5evpiv 
       foreign key (item_id) 
       references item
```
Hibernate:
```sql
    
    alter table category 
       add constraint FKgbowg38afm73793kwnokn0203 
       foreign key (category_parent_id) 
       references category
```
Hibernate:
```sql
    
    alter table category_item 
       add constraint FKu8b4lwqutcdq3363gf6mlujq 
       foreign key (item_id) 
       references item
```
Hibernate:
```sql
    
    alter table category_item 
       add constraint FKcq2n0opf5shyh84ex1fhukcbh 
       foreign key (category_id) 
       references category
```
Hibernate:
```sql
    
    alter table movie 
       add constraint FKq2rg7rpjfg38px01dhl4qrik6 
       foreign key (item_id) 
       references item
```
Hibernate:
```sql
    
    alter table order_item 
       add constraint FKija6hjjiit8dprnmvtvgdp6ru 
       foreign key (item_id) 
       references item
```
Hibernate:
```sql
    
    alter table order_item 
       add constraint FKt4dc2r9nbvbujrljv3e23iibt 
       foreign key (order_id) 
       references orders
```
Hibernate:
```sql
    
    alter table orders 
       add constraint FKtkrur7wg4d8ax0pwgo0vmy20c 
       foreign key (delivery_id) 
       references delivery
```
Hibernate:
```sql
    alter table orders 
       add constraint FKpktxwhj3x9m4gth5ff6bkqgeb 
       foreign key (member_id) 
       references member
```