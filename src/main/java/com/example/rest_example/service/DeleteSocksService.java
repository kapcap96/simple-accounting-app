package com.example.rest_example.service;

import com.example.rest_example.model.dto.SocksIncomeDTO;
import com.example.rest_example.model.jpa.Socks;
import com.example.rest_example.model.jpa.SocksColor;
import com.example.rest_example.repository.SocksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;
/**
 * класс для определения бизнес-логики
 */
@RequiredArgsConstructor
@Service
public class DeleteSocksService implements IDeleteSocksService {

    private final SocksRepository repository;

    /**
     * Сервис по удалению носков из БД
     * 1) если количество носков в запросе меньше чем в бд, то вычитаем разницу.
     * 2) если количество носков в запросе == количеству носков в БД, удаляем обьект из БД.
     *
     * @param dto дто
     */
    @Override
    @Transactional
    public void deleteSocks(SocksIncomeDTO dto) throws NoSuchElementException {
        repository.findByColorAndCottonPart(SocksColor.getColorByName(dto.getColor()), dto.getCottonPart())
                .ifPresentOrElse(socks ->
                        {
                            if (dto.getQuantity() < socks.getQuantity()) {
                                socks.setQuantity(socks.getQuantity() - dto.getQuantity());
                            } else if (dto.getQuantity() == socks.getQuantity()) {
                                repository.deleteById(socks.getId());
                            } else {
                                throw new NoSuchElementException();
                            }
                        },
                        () -> {
                            throw new NoSuchElementException();
                        }
                );
    }
}
