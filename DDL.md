## v0.0.2

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
        member_id bigint,
        primary key (order_id)
    )
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
       add constraint FKpktxwhj3x9m4gth5ff6bkqgeb 
       foreign key (member_id) 
       references member
```