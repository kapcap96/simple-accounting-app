package com.example.rest_example.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 *
 */
@Data
@AllArgsConstructor
public class AllSocksResponse implements Serializable {

    private final Long id;

    private final String color;

    private final int cottonPart;

    private final int quantity;
}


