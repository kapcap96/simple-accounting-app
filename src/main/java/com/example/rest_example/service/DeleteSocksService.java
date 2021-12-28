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

@RequiredArgsConstructor
@Service
public class DeleteSocksService implements IDeleteSocksService {

    private final SocksRepository repository;

    /**
     * Сервис по удалению носков из БД
     * 1) елси количество носков в запросе  меньше чем в бд,то вычитаем разницу.
     * 2) если количество насков в запросе == количеству вносков в БД, удаляем обьект из БД.
     *
     * @param dto дто
     */
    @Override
    @Transactional
    public void deleteSocks(SocksIncomeDTO dto) {


        repository.findByColorAndCottonPart(SocksColor.getColorByName(dto.getColor()), dto.getCottonPart())
                .ifPresentOrElse(socks ->
                {
                    if (dto.getQuantity() < socks.getQuantity()) {
                        socks.setQuantity(socks.getQuantity() - dto.getQuantity());
                    } else if (dto.getQuantity() == socks.getQuantity()) {
                        repository.delete(socks);
                    }
                    throw new NoSuchElementException();


                }, () -> {
                    throw new NoSuchElementException();
                });


    }
}
