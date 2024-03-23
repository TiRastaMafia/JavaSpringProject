package com.server.controller.product;

import com.server.dto.product.GoodsDto;
import com.server.entity.product.Goods;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/v1")
@Tag(
        name = "Товары",
        description = "Все методы для работы с товарами"
)
public interface IGoodsController {

    /**
     * Метод для создания нового товара
     * @param goodsDto характеристики товара
     * @return информация о добавленном товаре
     */
    @RequestMapping(value = "/goods", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<?> create(@RequestBody GoodsDto goodsDto);



    /**
     * Метод получения списка всех товаров
     * @return Список всех добавленных товаров
     */
    @RequestMapping(value = "/goods", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT', 'LEAD')")
    ResponseEntity<List<Goods>> readAll();



    /**
     * Метод для получения одного конкретного товара
     * @param id идентификатор товара
     * @return информация об указанном товаре
     */
    @RequestMapping(value = "/goods/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT', 'LEAD')")
    ResponseEntity<Goods> readId(@PathVariable(name = "id") int id);



    /**
     * Метод для обновления товара
     * @param id идентификатор обновляемого товара
     * @param goodsDto обновленные характеристики товара
     * @return обновленная информация об указанном товаре
     */
    @RequestMapping(value = "/goods/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody GoodsDto goodsDto);



    /**
     * Метод удаления товара
     * @param id идентификатор товара
     * @return статус код, соответствующий результату выполнения операции
     */
    @RequestMapping(value = "/goods/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<?> delete(@PathVariable(name = "id") int id);
}
