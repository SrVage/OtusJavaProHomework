package org.example;

import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        checkBuilder();

        checkIterator();
    }

    private static void checkIterator() {
        Logger.getLogger(Main.class.getSimpleName()).info("Check iterator");

        Box box = new Box();
        box.addInFirstList("1");
        box.addInFirstList("2");
        box.addInSecondList("3");
        box.addInSecondList("4");
        box.addInThirdList("5");
        box.addInThirdList("6");
        box.addInFourthList("7");
        box.addInFourthList("8");

        print(box);
    }

    private static void checkBuilder() {
        Logger.getLogger(Main.class.getSimpleName()).info("Check builder");

        Product product = Product.builder()
                .id(1)
                .title("title")
                .description("description")
                .cost(2.34d)
                .weight(0.12d)
                .width(10)
                .length(100)
                .height(5)
                .build();

        Logger.getLogger(Main.class.getSimpleName()).info(product.toString());
    }

    private static void print(Box box) {
        for (String string : box) {
            Logger.getLogger(Main.class.getSimpleName()).info(string);
        }
    }
}