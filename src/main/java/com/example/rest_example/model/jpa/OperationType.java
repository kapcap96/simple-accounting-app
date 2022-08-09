package com.example.rest_example.model.jpa;

import lombok.AllArgsConstructor;

import javax.validation.ValidationException;

/**
 * Модель для выбора типа сортировки
 */
@AllArgsConstructor
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
     * @param name наименование цвета
     * @return тип сортировкт 'operationType'
     * @throws ValidationException бросается исключение, если не удалось найти тип по имени `name`
     */
    public static OperationType getTypeByName(String name) throws ValidationException {
        //TODO() полностью дописать документацию данного перечисления
        //TODO() переписать с помощью Stream API
        for (OperationType operationType : OperationType.values()) {
            if (operationType.name.equals(name)) {
                return operationType;
            }
        }
        throw new ValidationException();
    }
}
