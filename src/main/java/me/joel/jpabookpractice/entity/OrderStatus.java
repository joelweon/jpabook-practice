package me.joel.jpabookpractice.entity;

/**
 * Date : 19. 4. 2
 * author : joel
 */

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
