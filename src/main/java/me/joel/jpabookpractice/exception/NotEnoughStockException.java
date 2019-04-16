package me.joel.jpabookpractice.exception;

/**
 * Date : 19. 4. 16
 * author : joel
 */

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException() {}

    public NotEnoughStockException(String message) {
        super(message);
    }
}
