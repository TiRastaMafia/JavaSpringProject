package com.server.service.product;

import com.server.dto.product.GoodsDto;
import com.server.entity.product.Goods;
import com.server.repository.product.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * Сервис для работы с товарами
 */
@Service
public class GoodsService{

    @Autowired
    private final GoodsRepository goodsRepository;

    public GoodsService(GoodsRepository goodsRepository) {

        this.goodsRepository = goodsRepository;

    }


    /**
     * Метод для создания товара
     * @param goodsDto характеристики товара
     * @return объект товара
     */
    public Goods create(GoodsDto goodsDto) {

        Goods goods = new Goods();
        goods.setProductName(goodsDto.productName());
        goods.setCategory(goodsDto.category());
        goods.setAmount(goodsDto.amount());

        goodsRepository.save(goods);

        return goods;
    }


    /**
     * Метод для получения списка всех товаров
     * @return список объектов товара
     */
    public List<Goods> readAll() {
        return goodsRepository.findAll();
    }


    /**
     * Метод для получения одного конкретного товара
     * @param id идентификатор товара
     * @return объект товара
     */
    public Goods readId(Integer id) {
        return goodsRepository.getReferenceById(id);
    }


    /**
     * Метод для обновления оодного конкретного товара
     * @param goodsDto новые характеристики товара
     * @param id идентификатор редактируемого товара
     * @return обновленный объект товара
     */
    public Goods update(GoodsDto goodsDto, Integer id) {

        // Если товар с таким идентификатором есть в базе, то выполняем обновление

        if (goodsRepository.existsById(id)) {

            Goods goods = goodsRepository.getReferenceById(id);

            // Если не передано новое наименование товара, то оставляем без изменений

            if (!goodsDto.productName().isBlank()){

                goods.setProductName(goodsDto.productName());

            }

            goods.setCategory(goodsDto.category());
            goods.setAmount(goodsDto.amount());

            goodsRepository.save(goods);

            return goods;

        } else {

            return null;

        }
    }

    /**
     * Метод удаления товара
     * @param id иентификатор товара
     * @return true - при успешном удалении, false -  противном случае
     */
    public boolean delete(Integer id) {

        if (goodsRepository.existsById(id)) {

            goodsRepository.deleteById(id);

            return true;

        } else {

            return false;

        }
    }
}
