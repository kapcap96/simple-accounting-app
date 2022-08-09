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

@Entity
@Table(name = "socks")
@Getter
@NoArgsConstructor
public class Socks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Enumerated(EnumType.STRING)
    @Column(name = "color", nullable = false)
    private SocksColor color;

    @Min(value = 0)
    @Max(value = 100)
    @Column(nullable = false)
    private int cottonPart;

    @Setter
    @Min(value = 1)
    @Column(nullable = false)
    private int quantity;

    /**
     * конструктор Socks
     * @param color цвет носков
     * @param cottonPart количество хлопка
     * @param quantity количество пар
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

