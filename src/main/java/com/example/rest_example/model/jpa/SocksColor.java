package com.example.rest_example.model.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.ValidationException;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Перечисление возможных цветов носков
 */
@AllArgsConstructor // конструктор включающий все возможные поля
public enum SocksColor {
    /**
     * ЧЕРНЫЙ ЦВЕТ
     */
    BLACK("black"),

    /**
     * КРАСНЫЙ ЦВЕТ
     */
    RED("red"),

    /**
     * ГОЛУБОЙ ЦВЕТ
     */
    BLUE("blue");

    @Getter
    private String name;

    /**
     * Получить цвет носков `SocksColor` по названию
     *
     * @param name наименование цвета
     * @return цвет носков типа `SocksColor`
     * @throws ValidationException бросается исключение, если не удалось найти цвет по имени `name`
     */
    public static SocksColor getColorByName(String name) throws ValidationException {

        return Arrays.stream(SocksColor.values())
                .filter(socksColor -> socksColor.name.equals(name))
                .findFirst()
                .orElseThrow(ValidationException::new);
    }
}
