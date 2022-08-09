package com.example.rest_example.service;

import com.example.rest_example.model.dto.SocksIncomeDTO;
import com.example.rest_example.model.jpa.Socks;
import com.example.rest_example.model.jpa.SocksColor;
import com.example.rest_example.repository.SocksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * класс для определения бизнес-логики
 */
@RequiredArgsConstructor // генерирует конструктор
@Service //Мы помечаем bean-компоненты с помощью @Service, чтобы указать, что они содержат бизнес-логику
public class SaveSocksService implements ISaveSocksService {

    private final SocksRepository repository;

    /**
     * Метод сохраняет или изменяет уже существующие SocKs по параметрам а именно quantity.
     * ели Socks в БД найден, то складывает количество носков.
     * ели Socks в БД не найден,то создает новый обект в БД с заданными параметрами.
     *
     * @param dto дто
     */
    @Override //Переопределение означает, что подкласс заменяет унаследованное поведение
    @Transactional
    public void saveSocks(SocksIncomeDTO dto) {
        // проверить в БД на существование таких носков
        repository.findByColorAndCottonPart(SocksColor.getColorByName(dto.getColor()), dto.getCottonPart())
                .ifPresentOrElse(
                        socks -> socks.setQuantity(socks.getQuantity() + dto.getQuantity()),
                        () -> repository.save(
                                new Socks(
                                        SocksColor.getColorByName(dto.getColor()),
                                        dto.getCottonPart(),
                                        dto.getQuantity()
                                )
                        )
                );
    }
}