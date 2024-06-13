package org.example;

public enum Messages {
    HELLO_MESSAGE("""
            0 - Выход;
            1 - Создание нового товара;
            2 - Вывести все товары;
            3 - Создание нового пользователя.
            4 - Показать всех пользователей.
            5 - Найти все товары пользователя.
            6 - Найти всех пользователей товара.
            7 - Удалить пользователя.
            8 - Удалить товар"""),
    CHOOSE_ACTION("Выберите действие: "),
    BAD_OPERATION("Неверная операция!"),
    INPUT_PRODUCT_TITLE("Введите название товара: "),
    INPUT_PRODUCT_COST("Введите стоимость товара: "),
    INPUT_CUSTOMER_NAME("Введите имя покупателя: "),
    INPUT_PRODUCT_ID("Введите id товара или ! для завершения");

    private final String message;

    Messages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

}
