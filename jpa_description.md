#### @JoinColumn 생략하면?
기본 전략을 사용한다.
```java
예)

@ManyToOne
private Team team;
```
- 필드명_참조하는테이블의 컬럼명 
    ==> team_TEAM_ID 외래키를 사용.