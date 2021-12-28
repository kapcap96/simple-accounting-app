package com.example.rest_example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * Дто модель ( возращает количество носков сортеруя по цвету и количеству хлопка)
 */
@Getter
@AllArgsConstructor
public class SocksQuantityResponse implements Serializable {
    private int quantity;
}
