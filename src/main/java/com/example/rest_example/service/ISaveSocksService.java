package com.example.rest_example.service;

import com.example.rest_example.model.dto.SocksIncomeDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 *
 */
public interface ISaveSocksService {

    /**
     * Метод сохраняет или изменяет уже существующие SocKs по параметрам а именно quantity.
     * ели Socks в БД найден, то складывает количество носков.
     * ели Socks в БД не найден,то создает новый обект в БД с заданными параметрами.
     * @param dto
     */

    @Transactional
    void saveSocks(SocksIncomeDTO dto);
}
