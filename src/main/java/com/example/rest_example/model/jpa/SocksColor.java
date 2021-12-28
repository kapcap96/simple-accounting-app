package com.example.rest_example.model.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Перечелсление возможных цветов носков
 */
@AllArgsConstructor // конструктор включающий все возможные поля
public enum SocksColor {
    /**
     *
     */
    BLACK("black"),

    /**
     *
     */
    RED("red"),

    /**
     *
     */
    BLUE("blue");

    @Getter
    private String name;

    /**
     * Получить цвет носков `SocksColor` по названию
     *
     * @param name наименование цвета
     * @return цвет носков типа `SocksColor`
     * @throws NoSuchElementException бросается исключение, если не удалось найти цвет по имени `name`
     */
    public static SocksColor getColorByName(String name) throws NoSuchElementException {
        //предупреждаем с помощью throws,
        // что метод может выбросить если не удалось найти цвет по имени `name`
        return Arrays.stream(SocksColor.values())
                .filter(socksColor -> socksColor.name.equals(name))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }
}
