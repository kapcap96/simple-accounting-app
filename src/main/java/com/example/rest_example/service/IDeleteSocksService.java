package com.example.rest_example.service;

import com.example.rest_example.model.dto.SocksIncomeDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

/**
 * интерфейс с методом удаления носков
 */
public interface IDeleteSocksService {

    /**
     * Сервис по удалению носков из БД
     * 1) если количество носков в запросе меньше чем в бд, то вычитаем разницу.
     * 2) если количество носков в запросе == количеству носков в БД, удаляем обьект из БД.
     *
     * @param dto дто
     */
    @Transactional
    void deleteSocks(SocksIncomeDTO dto) throws NoSuchElementException;
}
