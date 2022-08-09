package com.example.rest_example.controller;

import com.example.rest_example.model.dto.AllSocksResponse;
import com.example.rest_example.model.dto.SocksIncomeDTO;
import com.example.rest_example.model.dto.SocksQuantityResponse;
import com.example.rest_example.model.jpa.OperationType;
import com.example.rest_example.model.jpa.Socks;
import com.example.rest_example.model.jpa.SocksColor;
import com.example.rest_example.repository.SocksRepository;
import com.example.rest_example.service.IDeleteSocksService;
import com.example.rest_example.service.ISaveSocksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * Класс позволяет осуществлять различные методы
 * Например: сортировка носков по цвету и количеству хлопка
 * добавление носков в базу данных и их удаление.
 */
@Validated
@RestController
@RequiredArgsConstructor
public class SocksController {

    private final ISaveSocksService saveService;
    private final SocksRepository repository;
    private final IDeleteSocksService deleteService;

    /**
     * ПОЛУЧЕНИЕ КОЛИЧЕСТВО НОСКОВ ПО ПАРАМЕТРАМ
     *
     * @param color      цвет
     * @param operation  тип операции
     * @param cottonPart количество хлопка
     * @return возвращает данные с заданными параметрами + суммирует quantity
     */
    @GetMapping(value = "/api/socks")
    public ResponseEntity<SocksQuantityResponse> getSocksQuantity(
            @com.example.rest_example.utils.SocksColor @RequestParam("color") String color,
            @com.example.rest_example.utils.OperationType @RequestParam("operation") String operation,
            @Max(100) @Min(0) @RequestParam("cottonPart") Integer cottonPart
    ) {
        // в зависимости от типа сортируем носки (больше, меньше, равно)
        List<Socks> socksList;
        switch (OperationType.getTypeByName(operation)) {
            case MORE_THAN:
                socksList = repository.findByColorAndCottonPartIsGreaterThan(SocksColor.getColorByName(color), cottonPart);
                break;
            case LESS_THAN:
                socksList = repository.findByColorAndCottonPartLessThan(SocksColor.getColorByName(color), cottonPart);
                break;
            case EQUAL:
                socksList = repository.findByColorAndCottonPartEquals(SocksColor.getColorByName(color), cottonPart);
                break;
            default:
                socksList = Collections.emptyList();
        }

        return ResponseEntity.ok(
                new SocksQuantityResponse(
                        socksList.stream().mapToInt(Socks::getQuantity).sum()
                )
        );
    }

    /**
     * ДОБАВЛЕНИЕ НОВЫ ИЛИ УЖЕ СУЩЕСТВУЮЩИХ НОСКОВ ПО ТИПУ ( ЦВЕТ, КОЛ-ВО ХЛОПКА, КОЛИЧЕСТВО )
     *
     * @param body     входящий запрос ДТО (JSON)
     * @param response возвращение HTTP ответов (в зависимости от входящих параметров)
     */
    @PostMapping(value = "/api/socks/income")
    public void addIncomeSocks(
            @Valid @RequestBody SocksIncomeDTO body,
            HttpServletResponse response
    ) {
        saveService.saveSocks(body);
        response.setStatus(HttpStatus.OK.value()); //HTTP ответ 200
    }

    /**
     * УДАЛЯЕТ НОСКИ ИЗ БД ИЛИ УМЕНЬШАЕТ ИХ КОЛИЧЕСТВО ЕСЛИ КОЛИЧЕСТВО НОСКОВ В ЗАПРОСЕ < ЧЕМ В БД
     *
     * @param body     входящий запрос ДТО (JSON)
     * @param response возвращение HTTP ответов (в зависимости от входящих параметров)
     */
    @PostMapping(value = "/api/socks/outcome")
    public void addOutcomeSocks(
            @Valid @RequestBody SocksIncomeDTO body,
            HttpServletResponse response
    ) {
        try {
            deleteService.deleteSocks(body);
        } catch (NoSuchElementException e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return;
        }
        response.setStatus(HttpStatus.OK.value()); //HTTP ответ 200
    }

    /**
     * Данный клас позваляет находить в БД носки по запросу ID
     *
     * @param id номер ID
     * @return Если обьект найден воозврашает его (сконвертировав все данные)если нет выдаёт статус ошибки 400.
     */
    @GetMapping(value = "/api/socks/{id}")
    public ResponseEntity<AllSocksResponse> getSocksQuantity(@PathVariable(name = "id") long id) {
        return repository.findById(id)
                .map(socks -> ResponseEntity.ok(convert(socks)))
                .orElse(ResponseEntity.badRequest().build());
    }

    /**
     * Данный класс занимается обработкой ошибок при наличии проверки.
     *
     * @param e ValidationException
     * @return Возаращает getMessage (причину ошибки)
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleIllegalArguments(ValidationException e) {
        return ResponseEntity
                .badRequest()
                .body(e.getMessage());
    }

    /**
     * Класс конвертирует данные о Носках
     *
     * @param socks Носки
     * @return возвращает все параметры Socks
     */
    private static AllSocksResponse convert(Socks socks) {
        return new AllSocksResponse(
                socks.getId(),
                socks.getColor().getName(),
                socks.getCottonPart(),
                socks.getQuantity()
        );
    }

}