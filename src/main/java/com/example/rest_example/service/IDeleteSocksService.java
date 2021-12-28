package com.example.rest_example.service;

import com.example.rest_example.model.dto.SocksIncomeDTO;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
public interface IDeleteSocksService {

    /**
     *
     * @param dto
     */
    @Transactional
    void deleteSocks(SocksIncomeDTO dto);
}
