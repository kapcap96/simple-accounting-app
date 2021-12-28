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
 * ррепозиторий  носкоов
 */
@Repository
public interface SocksRepository extends CrudRepository<Socks, Long> {

    /**
     * сортерует носки по цвету и содержанию хлопка ( больше веденного значения)
     */

    /**
     *
     * @param color
     * @param cottonPart
     * @return
     */
    List<Socks> findByColorAndCottonPartIsGreaterThan(SocksColor color, @Min(0) @Max(100) int cottonPart);

    /**
     * сортерует носки по цвету и содержанию хлопка ( меньше веденного значения)
     */

    /**
     *
     * @param color
     * @param cottonPart
     * @return
     */
    List<Socks> findByColorAndCottonPartLessThan(SocksColor color, @Min(0) @Max(100) int cottonPart);

    /**
     * сортерует носки по цвету и содержанию хлопка ( равный веденному  значению)
     */
    List<Socks> findByColorAndCottonPartEquals(SocksColor color, int cottonPart);

    /**
     * находит носки по цвету и количеству хлопка в БД
     * @return
     */
    Optional <Socks> findByColorAndCottonPart(@NonNull SocksColor color, @NonNull int cottonPart);
}
