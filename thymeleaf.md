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

