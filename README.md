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
RDB(관계형 데이터베이스) 에서는 외래키를 이용해 조인이 가능 하지만 객체에서는 해당하는 기능이 없기 때문에
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


## v0.0.6
- 값 타입 매핑

Member, Delivery에 동일한 주소 정보를 Address라는 값 타입을 만들어서 사용한다.

#### java
```java
@Embeddable
public class Address {
    private String city;

    private String street;

    private String zipcode;
}
```

#### java
```java
public class Member {
    @Embedded
    private Address address;
}
```

***

## v0.1.1
- 회원 패키지 별도 구성
- 회원 등록
- 회원 목록 조회
Service, Repository 구현

***
### 간단한 웹 애플리케이션 제작
- 애플리케이션 기능 분석
    1. 회원기능
        - 회원 등록
        - 회원 목록 조회
    2. 상품기능
        - 상품 등록
        - 상품 목록 조회
        - 상품 수정
    3. 주문기능
        - 상품 주문
        - 주문 내역 조회
        - 주문 취소
        
- 개발 순서
    1. 비즈니스 로직을 수행하는 서비스와 리파지토리 계층을 개발
    2. 테스트 케이스 작성 후 검증
    3. 컨트롤러와 뷰 개발       
    
- 사용기술
    - 뷰: thymeleaf
    - 웹 계층 : 스프링 MVC
    - 데이터 저장 계층 : JPA, 하이버네이트
    - 데이터베이스 : H2 인메모리
    - 기반 프레임워크 : 스프링 프레임워크 기반의 스프링부트
    - 빌드 : 메이븐
***

### 엔티티메니저 주입
```java
@PersistenceContext
EntityManager em;
```

### 엔티티 매니저 팩토리 주입
```java
@PersistenceUnit
EntityManagerFactory emf;
// @PersistenceContext를 사용해서 컨테이너가 관리하는 엔티티 매니저를 주입받아
// 사용해서 엔티티 매니저 팩토매를 직접 사용하는 경우는 거의 없다.
```

### @Transactional
@Transactional은 RuntimeException과 그 자식들인 Unchecked(언체크) 예외만 롤백한다.
만약 체크 예외가 발생해도 롤백하고 싶다면
`@Transactional(rollbackOn = Exception.class)` 이전 버전은 `@Transactional(rollbackFor = Exception.class)`
를 명시하여 롤백할 예외를 정해야한다.  
@Transactional의 위치가 클래스에 위치하면 모든 메서드에 적용하는 것이고,
메서드에도 추가하면 메서드에 설정한 @Transactional 속성이 우선시 된다.


## v0.1.2
- 회원 기능 테스트
    - 회원가입 성공
    - 회원가입 할 때 같은 이름 있을 경우 예외

### @Transactional
test에서 @Transactional 선언 안해주면 같지 않다고 나온다.(assertEquals -> false)
@Transactional을 선언하면 영속성 컨텍스트 내의 1차캐시에 저장된다.
그래서 findOne(saveId)을 해도 하이버네이트 1 레벨 캐시에 있는 member가 조회 되기 때문에(DB조회X) 같다.
그 반대의 경우는 메서드마다(join, findOne) 각각의 트랜잭션으로 처리된다.
findOne(saveId)을 하면 DB에서 쿼리를 가져와서 둘은 다르다고 나온다.
참고 : https://stackoverflow.com/questions/26597440/how-do-you-test-spring-transactional-without-just-hitting-hibernate-level-1-cac

확실하진 않아서 자세하고 정확한 건 더 공부 해야 할 듯 하다.

## v0.1.3
- 상품기능
    - 상품 등록
    - 상품 목록 조회
    - 상품 수정
Service, Repository 구현
간단한 테스트 진행

## v0.1.4
- 주문기능
    - 상품 주문
    - 주문 내역 조회
    - 주문 취소
- 도메인 패턴 분석
    
### 비즈니스 로직을 처리하는 두가지 패턴
- 책임지는 쪽이 Domain Level 이거나 Script Level인 패턴

### 1) 트랜잭션 스크립트 패턴(절차지향)
장점 :
- 단순성
- 쉽게 개발 가능
- 초기에는 로직 이해 쉬움(직관적)

단점 :
- 시간이 지날 수록 로직이 복잡
- 코드 중복
- 좋은 설계를 유지하기 힘듦
- 비즈니스 로직이 복잡해질수록 난잡한 코드를 만들게 된다.
특히, 트랜잭션 스크립트 코드는 애초에 도메인에 대한 분석/설계 개념이 약하기 때문에
코드의 중복 발생을 막기 어려워진다. 또한, 트랜잭션 스크립트를 사용하게 되면
쉬운 개발에 익숙해지기 때문에 공통된 코드를 공통 모듈로 분리하지 않고 복사&붙이기 방식으로
중복된 코드를 만드는 유혹에 빠지기 쉽다.

특징 :
- 데이터와 프로세스를 다른 모듈에 두는 것.
- 프로세스 저장 클래스, 데이터 저장 클래스

### 2) 도메인 모델 패턴(객체지향)
장점 :
- 코드 중복 방지
- 재사용성
- 확장성
- 유지보수 편리함
- 복잡한 로직에 적합

단점 :
- 테이블과 매핑하기가 어려움
- 많은 노력이 필요(객체판별, 객체관계정의, 테이블매핑)
- 역량있고 경험많은 개발자가 함께 개발해야 함 

특징 :
- 데이터화 프로세스가 혼합
- 데이터가 있는 곳에 프로세를 넣음
- 객체간의 복잡한 연관 관계를 갖고 있고, 상속 등을 통해서 객체의 기능을 확장할 수 있다.
- 도메인 모델에서는 비즈니스 영역에서 사용되는 객체를 판별하고, 객체가 제공해야 할 목록을 추출한다.

단순한 도메인 모델은 (도메인 객체 - 데이블)  
풍부한 도메인 모델은 상속, 패턴, 연관관계가 복잡한 구조다.  
서비스 계층은 그저 엔티티에 필요한 요청을 위임하는 역할이고 도메인 모델이 풍부해야한다.  

> 우리가 짜는 프로그램은 두가지 요구 사항을 만족시켜야한다. 
  우리는 오늘 완성해야 하는 기능을 구현하는 코드를 짜야하는 동시에 내일 쉽게 변경할 수 있는 코드를 짜야한다.
  샌디 메츠
  

## v0.1.5
- 주문 검색 기능
    - 검색조건 : 회원이름, 주문상태
- 주문 검색 테스트
    - 상품 주문이 성공해야 한다.
    - 상품을 주문할 때 재고 수량을 초과하면 안 된다.
    - 주문 취소가 성공해야 한다.
    