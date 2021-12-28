package com.example.rest_example.model.jpa;

import lombok.AllArgsConstructor;

import java.util.NoSuchElementException;

/**
 *
 */
@AllArgsConstructor // конструктор включающий все возможные поля
public enum OperationType {
    /**
     * БОЛЬШЕ ЧЕМ
     */
    MORE_THAN("moreThan"),

    /**
     * МЕНЬШЕ ЧЕМ
     */
    LESS_THAN("lessThan"),

    /**
     * РАВНО
     */
    EQUAL("equal");

    private String name;

    /**
     * Получить тип сортировки `getTypeByName` по названию
     *
     * @param name
     * @return
     * @throws NoSuchElementException
     */
    public static OperationType getTypeByName(String name) throws NoSuchElementException {
        //TODO() переписать с помощью Stream API
        for (OperationType operationType : OperationType.values()) { //values возвращает массив со всеми значениями перечисления
            if (operationType.name.equals(name)) {
                return operationType;
            }
        }
        throw new NoSuchElementException();
    }
}
