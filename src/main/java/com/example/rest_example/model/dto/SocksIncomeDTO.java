package com.example.rest_example.model.dto;

import com.example.rest_example.utils.SocksColor;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;


/**
 * ДТО модель для сохранения носков в БД (как новый объект или изменение существующего)
 */
@AllArgsConstructor
@Getter
public class SocksIncomeDTO implements Serializable {

    @SocksColor
    @JsonProperty(required = true)
    private final String color;

    @Min(value = 0, message = "Процент хлопка должен быть больше нуля")
    @Max(value = 100, message = "Процент хлопка должен быть меньше ста")
    @JsonProperty(required = true)
    private final int cottonPart;

    @Min(value = 1, message = "Кол-во должно быть больше единицы")
    @JsonProperty(required = true)
    private final int quantity;
}
