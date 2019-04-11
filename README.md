# 자바 ORM 표준 JPA 프로그래밍
- 실전 예제를 따라하며 JPA를 학습한다.

## v0.0.1
- 요구사항 분석
- 클래스 생성
- DDL 파일 생성

#### 요구사항 분석
1. 회원 기능
- 회원 등록
- 회원 조회

2. 상품 기능
- 상품 등록
- 상품 수정
- 상품 조회

3. 주문 기능
- 상품 주문
- 주문 내역 조회
- 주문 취소

***

현재 객체 설계 방식은 테이블 설계에 맞춘 것이다.  
객체지향 설계는 객체가 맡은 역할과 책임에 따라 관련 있는 객체끼리 참조하도록 설계해야한다.  
RDB(관계형 데이터베이스) 에서는 외래키를 이용해 조인인이 가능 하지만 객체에서는 해당하는 기능이 없기 때문에
연관된 객체를 참조를 통해 찾아야한다.

어떤 회원이 주문했는지 조회 할 때  
**주문조회 -> 외래키를 이용해 주문과 연관된 회원 조회**
#### java
```java
Order order = em.find(Order.class, orderId);

// 외래키로 다시 조회
Member member = em.find(Member.class, order.getMemberId());
```

객체지향적 방법  
**주문조회 -> 참조를 통해 회원 조회**
#### java
```java
    Order order = em.find(Order.class, orderId);
    Member member = order.getMember(); // 참조 사용
```

## v0.0.2
- 외래키로 사용한 필드 제거, 참조를 사용(연관관계 주인 설정)
- 양방향 관계 설정
- 연관관계 편의 메소드 추가

연관관계의 주인을 설정하는 것이 중요하다.  
연관관계의 주인은 외래키가 있는 객체이다.(외래키를 관리할 주인)    
    
    Member 1-------* Orders
      
이 상황에서는 memberId를 갖는 Orders가 연관관계의 주인이다.  
연관관계의 주인만이 데이터베이스 연관관계와 매핑되고 외래키를 등록,수정,삭제 할 수 있다.

그래서 Order.class에서 다음과 같이 매핑시켜줬다.

#### java
```java
    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;
```

#### sql
```sql
    create table orders (
       order_id bigint not null,
        order_date timestamp,
        status varchar(255),
        member_id bigint,
        primary key (order_id)
    )

    alter table orders 
       add constraint FKpktxwhj3x9m4gth5ff6bkqgeb 
       foreign key (member_id) 
       references member
```

그 반대의 경우(Member)는 @OneToMany 어노테이션과 함께 mappedby 속성을 넣어준다.
Member 의 경우는 주인이 아니기 때문에 읽기만 할 수 있다.

#### sql
```sql
    @OneToMany(mappedBy = "member")
    private List<Orders> ordersList = new ArrayList<>();
```

이로써 Member - Order는 양방향 연관관계가 된다.
(정확하게는 단방향 2개)

### 연관관계 편의 메소드를 통해 간결해진 코드
기존방식
#### java
```java
Member member = new Member();
Orders orders = new Orders();

member.getOrdersList().add(orders);
orders.setMember(member);
```


연관관계 편의메소드 setMember() 를 추가해서 다음과 같이 하면 된다.

#### java
```java
Member member = new Member();
Orders orders = new Orders();

orders.setMember(member);
```

### 연관관계 편의 메소드의 사용 이유 와 설명

#### java
```java
    /*
    -연관관계 메서드-
    회원을 수정하면 회원이 갖고있는 주문내역에서 삭제를하고
    회원을 수정한 뒤
    수정 된 회원에게 주문내역을 넣어준다.
    */
    public void setMember(Member member) {
        // 기존 관계 제거
        if (this.member != null) {
            this.member.getOrdersList().remove(this);
        }
        this.member = member;
        member.getOrdersList().add(this);
    }
```
-> 편의 메소드를 추가하는 이유는 연관관계 주인을 변경하고
   반대의 참조되는 객체를 조회하면 영속성 컨텍스트 내에서 flush가
   이루어 지지 않았기 때문에 변경 전 데이터가 조회되어 문제가 될 수 있다.

#### java
```java
    /*
    Orders는 OrderItem 과의 관계에서 연관관계의 주인이 아니기 때문에
    OrderItem 엔티티를 추가 할 수 없다.
    그래서 이 메서드를 통해 간접적으로 OrderItem 엔티티도 추가가 가능하게 했다.
    */
    public void addOrderItem(OrderItem orderItem) {
        orderItemList.add(orderItem);
        orderItem.setOrders(this);
    }
```

### 다대일 단방향
OrderItem -> Item  
주문상품에서 상품을 참조하는 경우는 많지만 그 반대는 거의 없어 단방향 관계로 설정했다.  
(OrderItem.item) 필드를 사용해서 참조