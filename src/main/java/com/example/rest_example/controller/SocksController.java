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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collections;
import java.util.List;


/**
 *
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
     * @param operation  тип опирации
     * @param cottonPart количество хлопка
     * @return
     */
    @GetMapping(value = "/api/socks") //возвращает количество носков по параметра
    public ResponseEntity<SocksQuantityResponse> getSocksQuantity(
            @com.example.rest_example.utils.SocksColor @RequestParam("color") String color,              // связывает значение параметра шаблона URI или сегмент пути,
            @com.example.rest_example.utils.OperationType @RequestParam("operation") String operation,      //  содержащий пара
            @Max(100) @Min(0) @RequestParam("cottonPart") Integer cottonPart
    ) {
        // в зависимости от типа сортируем носки ( больше, меньше, равно)
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
                socksList = Collections.emptyList(); // Collection.emptyList () создает новый экземпляр пустого списка
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
     * @param body     входящий запрос ДТО ( JSON)
     * @param response возвращение HTTP ответов ( в зависимости от взодящих параметров )
     */
    @PostMapping(value = "/api/socks/income")
    public void addIncomeSocks(
            @Valid @RequestBody SocksIncomeDTO body,
            HttpServletResponse response, // возвращает ответ в JSON  о статусах
            BindingResult result
    ) {
        if (result.hasErrors()) {
            response.setStatus(HttpStatus.BAD_REQUEST.value()); // HTTP ответ 400 ошибка запроса
        }
        try {
            saveService.saveSocks(body);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value()); // HTTP ответ 400 ошибка запроса
            return;
        }
        response.setStatus(HttpStatus.OK.value()); //HTTP ответ 200
    }

    @PostMapping(value = "/api/socks/outcome")
    public void addOutcomeSocks(
            @Valid @RequestBody SocksIncomeDTO body,
            HttpServletResponse response,
            BindingResult result// возвращает ответ в JSON  о статусах
    ) {
        if (result.hasErrors()) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        try {
            deleteService.deleteSocks(body);
        } catch (RuntimeException e) {
            response.setStatus(HttpStatus.BAD_REQUEST.value()); // HTTP ответ 400 ошибка запроса
            return;
        }
        response.setStatus(HttpStatus.OK.value()); //HTTP ответ 200
    }

    /**
     * @param id
     * @return
     */
    @GetMapping(value = "/api/socks/{id}")
    public ResponseEntity<AllSocksResponse> getSocksQuantity(@PathVariable(name = "id") long id) {
        return repository.findById(id)
                .map(socks -> ResponseEntity.ok(convert(socks)))
                .orElse(ResponseEntity.badRequest().build());
    }

    private static AllSocksResponse convert(Socks socks) {
        return new AllSocksResponse(
                socks.getId(),
                socks.getColor().getName(),
                socks.getCottonPart(),
                socks.getQuantity()
        );
    }

}