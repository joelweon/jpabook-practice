## thymeleaf

[Tutorial: Thymeleaf + Spring](https://www.thymeleaf.org/doc/tutorials/2.1/thymeleafspring.html)


### 중간에 변수 넣기(/item/1/edit)
```html
<a th:href="@{/item/{id}/edit(id=${item.itemId})}"></a>
```

### 파라미터로 넣기 -> /item/id/edit?id=1
```html
<a th:href="@{/item/id/edit(id=${item.itemId})}"></a>
```

## if / else
```html
<td>
    <span th:if="${teacher.gender == 'F'}">Female</span>
    <span th:unless="${teacher.gender == 'F'}">Male</span>
</td>
```
## Switch - Case
```html
<td th:switch="${#lists.size(teacher.courses)}">
    <span th:case="'0'">NO COURSES YET!</span>
    <span th:case="'1'" th:text="${teacher.courses[0]}"></span>
    <div th:case="*">
        <div th:each="course:${teacher.courses}" th:text="${course}"/>
    </div>
</td>
```

## Enum 을 활용하여 option 태그 설정하기
#### java
```java
public enum OrderStatus {
    ORDER("주문"), CANCEL("취소");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```
#### html
```html
<option
        th:each="status : ${T(me.joel.jpabookpractice.entity.OrderStatus).values()}"
        th:value="${status}"
        th:text="${status.name}"
        th:selected="${status == orderSearch.orderStatus}">
</option>
```

## if (Enum - String) 비교
```html
<!--1번째 방법-->
<a 
    th:if="${item.status == T(me.joel.jpabookpractice.entity.OrderStatus).ORDER}"
    th:href="@{/orders/{id}/cancel(id=${item.orderId})}" class="btn btn-danger">주문취소</a>

<!--2번째 방법- 일반 string은 이거로 비교하면 된다.-->
<a
    th:if="${#strings.toString(item.status)} == 'ORDER'"
    th:href="@{/orders/{id}/cancel(id=${item.orderId})}" class="btn btn-danger">주문취소</a>
```
