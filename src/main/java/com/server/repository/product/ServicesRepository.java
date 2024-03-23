package com.server.repository.product;

import com.server.entity.product.Services;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с услугами
 */
public interface ServicesRepository  extends JpaRepository<Services, Integer> {
}
