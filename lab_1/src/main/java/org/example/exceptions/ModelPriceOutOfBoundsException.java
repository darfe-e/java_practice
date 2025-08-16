package org.example.exceptions;

public class ModelPriceOutOfBoundsException extends RuntimeException{
    public ModelPriceOutOfBoundsException(double price) {
        super("Цена " + price + " недопустима (должна быть > 0)");
    }
}
