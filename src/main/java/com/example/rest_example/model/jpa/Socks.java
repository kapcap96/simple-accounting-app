package com.example.rest_example.model.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Objects;


/**
 * JPA- модель носков
 */

@Entity                 //указывает на то, что данный класс является сущностью.
@Table(name = "socks") //указывает на имя таблицы, которая будет отображаться в этой сущности
@Getter               // автоматически генерирует гетеры
@NoArgsConstructor   //@NoArgsConstructor создаёт пустой конструктор,
public class Socks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //@Enumerated определяет в какой форме перечисления должны быть сохранены в базе данных.
    // EnumType.STRING указывает на то, что значение перечисления должно быть сохранено стр
    // nullable Атрибут, допускающий значение NULL, указывает, может ли поле иметь значение NULL
    //@Column используется для определения соответствия между атрибутами в классе сущности и полями в таблице данных.
    @Enumerated(EnumType.STRING)
    @Column(name = "color", nullable = false)
    private SocksColor color;

    @Min(value = 0)   //фиксирует минимальное значение
    @Max(value = 100) //фиксирует максимальное значение
    @Column(nullable = false)
    private int cottonPart;

    @Setter
    @Min(value = 1)
    @Column(nullable = false)
    private int quantity;

    /**
     * @param color
     * @param cottonPart
     * @param quantity
     */
    public Socks(SocksColor color, int cottonPart, int quantity) {
        this.color = color;
        this.cottonPart = cottonPart;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Socks socks = (Socks) o;
        return id == socks.id && cottonPart == socks.cottonPart && quantity == socks.quantity && color == socks.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, color, cottonPart, quantity);
    }


}

