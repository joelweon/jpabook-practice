# 자바 ORM 표준 JPA 프로그래밍
- 실전 예제를 따라하며 JPA를 학습한다.
- [스프링 데이터 예제 프로젝트로 배우는 전자정부 표준 데이터베이스 프레임워크- 김영한](https://book.naver.com/bookdb/book_detail.nhn?bid=9252528)


## v0.0.1
- 요구사항 분석
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
- 클래스 생성
- DDL 파일 생성


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

#### java
```java
    @OneToMany(mappedBy = "member")
    private List<Orders> ordersList = new ArrayList<>();
```

이로써 Member - Order는 양방향 연관관계가 된다.


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



## v0.0.3
- 요구사항 추가
    - 상품을 주문할 때 배송 정보를 입력할 수 있다. 주문과 배송은 일대일 관계.
    - 상품을 카테고리로 구분 가능
- 클래스 생성(배송-delivery, 카테고리-category)
- DDL 파일 생성


    주문(Orders) 1-----------1 배송(Delivery)

객체 관계를 고려할 때 주문이 배송을 자주 접근할 에정이기 때문에 외래키는 주문 테이블에 둔다.
참고로 일대일 관계이므로 Orders 테이블의 DELIVERY_ID 외럐키는 유니크 제약 조건을 거는 것이 좋다.
-> Orders.delivery(연관관계 주인)


    카테고리(Category) *-----------* 상품(Item)

카테고리와 상품은 다대다 관계이다.  
주인은 카테고리로 정했다.

참고로 다대다 관계는 테이블 연결을 JPA가 알아서 처리해주지만 연결테이블(CATEGORY_ITEM)에
새로운 필드를 추가 할 수 없기 때문에 실무에서는 거의 쓰이지않고 1대다 다대1 관계로 CategoryItem
이라는 엔티티를 추가하여 연결관계를 매핑하는 것을 권장한다.


## v0.0.4
- 요구사항 추가
    - 상품의 종류는 음반, 도서, 영화가 있고 이후 더 확장될 수 있다. 
    - 모든 데이터는 등록일과 수정일이 있어야한다.
- 클래스 생성(도서, 음반, 영화)
- DDL 파일 생성

### 상속관계 매핑(3가지 방법)
1) 각각의 테이블로 변환 : 테이블을 각자 만들고 조회할 때 조인을 사용한다. JPA에서는 조인전략(Joined Strategy)이라함
- 장점
    - 테이블이 정규화됨
    - 외래키 참조 무결성 제약조건 활용할 수 있음
    - 저장공간을 효율적으로 사용가능
- 단점
    - 조회할 때 조인이 많이 사용되므로 성능이 저하 될 수 있음
    - 조회 쿼리가 복잡
    - 데이터를 등록할 때 INSERT SQL을 두번 실행
- 특징
    - JPA 표준 명세는 구분 컬럼을 사용하도록 하지만 하이버네이트를 포함한 몇몇 구현체는 구분컬럼(@DiscriminatorColumn) 없이도 동작됨
    - @PrimaryKeyJoinColumn(name="재정의할 키명") -> 생략하면 부모 키 그대로 사용함
    
2) 통합 테이블로 변환 : 테이블 하나에 데이터를 다 담는다. JPA에서는 단일 테이블 전략(Single-Table Strategy)이라함
- 장점
    - 조인이 필요없어 조회 성능이 빠름
    - 조회 쿼리가 단순
- 단점
    - 자식엔티티가 매핑한 컬럼은 모두 null 허용해야함.
    - 단일 테이블이 모든 것을 저장하다보니 테이블이 커질 수 있음. 상황에 따라 조회 성능이 느릴 수 있음.
- 특징
    - 구분 컬럼 필수 사용. @DiscriminatorColumn("이름") 설정
    - @DiscriminatorColumn 지정하지 않으면 기본 엔티티이름을 사용(Movie, Album)


3) 서브타입 테이블로 변환 : 서브 타입마다 하나의 테이블을 만든다. JPA에서는 구현클래스마다 테이블 전략(Table-per-Concrete-Class Strategy)
- 장점
    - 서브 타입을 구분해서 처리할 때 효과적
    - not null 제약조건 사용가능
- 단점
    - 여러 자식테이블을 함께 조회할 때 성능이 느림(SQL UNION 사용해야함)
    - 자식 테이블을 통합해서 쿼리하기 어려움
-> DB설계자 & ORM전문가 둘다 추천하지 않는 전략.

> 책에서는 단일 테이블 전략을 사용했고 본 예제에서는 조인 전력을 사용했다.

### 공통된 컬럼 클래스 분리
새로 생성한 클래스에 @MappedSuperclass 선언해 주고 다른 클래스에서 새로 생성한 클래스를 상속받으면 된다.

## v0.0.5
- 글로벌 페치 전략 설정
- 영속성 전이 설정

### 글로벌 페지 전략 기본값
LAZE : @OneToMany, @ManyToMany
EAGER: @OneToOne, @ManyToOne

### 영속성 전이
데이터베이스에 저장할 때 연관된 엔티티들은 모두 영속상태여야 한다.
연관된 엔티티 중 영속상태가 아닌 엔티티가 있으면 에러가 발생한다.(정확히는 플러시 시점에서 오류)

영속성 전이를 사용하면 편리하게 엔티티를 영속상태로 만들 수 있다.
(cascade - CascadeType.ALL)

영속성 전이는 연관관계 매핑하는 것과는 아무 관련이 없다.
단지 엔티티를 영속화할 때 연관된 엔티티를 같이 영속화하는 편리함을 제공할 뿐이다.

#### java
```java
/* 영속성 전이 사용 전 */
...
em.persist(delivery);
em.persist(orderItem1);
em.persist(orderItem2);
em.persist(order);


/* 영속성 전이 사용 후 */
em.persist(order); // delivery, orderItems 플러시 시점에 영속성 전이
```