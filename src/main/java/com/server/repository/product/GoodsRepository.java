package com.server.repository.product;

import com.server.entity.product.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с товарами
 */
public interface GoodsRepository extends JpaRepository<Goods, Integer> {
}
