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
     * @param dto
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

//    SocksColor color = SocksColor.getColorByName(dto.getColor());
//// проверить в БД на существование таких носков

//      Socks socks = repository.findByColorAndCottonPart(color, dto.getCottonPart());
//                if (socks != null && dto.getQuantity() >= 1) { // проверяет наличе носков с введеными параметрами в БД
//                socks.setQuantity(socks.getQuantity() + dto.getQuantity()); // если в БД нашел, то просто увеличиваем кол-во
//                } else { // если носки с данным параметром не найдены, то сохраняем новые в БД.
//                //repository.save(socks);
//                repository.save(
//                new Socks(color, dto.getCottonPart(), dto.getQuantity())
//                );
