package com.example.rest_example.repository;

import com.example.rest_example.model.jpa.Socks;
import com.example.rest_example.model.jpa.SocksColor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

/**
 * репозиторий носков
 */
@Repository
public interface SocksRepository extends CrudRepository<Socks, Long> {

    /**
     * сортирует носки по цвету и содержанию хлопка (больше веденного значения)
     * @param color цвет носков
     * @param cottonPart количество хлопка
     * @return возвращает носки по цвету и содержанию хлопка (больше чем)
     */
    List<Socks> findByColorAndCottonPartIsGreaterThan(SocksColor color, @Min(0) @Max(100) int cottonPart);

    /**
     сортирует носки по цвету и содержанию хлопка (больше веденного значения)
     * @param color цвет носков
     * @param cottonPart количество хлопка
     * @return возвращает носки по цвету и содержанию хлопка (Меньше чем)
     */
    List<Socks> findByColorAndCottonPartLessThan(SocksColor color, @Min(0) @Max(100) int cottonPart);

    /**
     * сортирует носки по цвету и содержанию хлопка (больше веденного значения)
     * @param color цвет носков
     * @param cottonPart количество хлопка
     * @return возвращает носки по цвету и содержанию хлопка (РАВНО)
     */
    List<Socks> findByColorAndCottonPartEquals(SocksColor color, int cottonPart);

    /**
     сортирует носки по цвету и содержанию хлопка (больше веденного значения)
     * @param color цвет носков
     * @param cottonPart количество хлопка
     * @return возвращает носки по цвету и содержанию хлопка
     */
    Optional <Socks> findByColorAndCottonPart(@NonNull SocksColor color, @NonNull int cottonPart);
}
