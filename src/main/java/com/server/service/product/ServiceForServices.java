package com.server.service.product;

import com.server.dto.product.ServicesDto;
import com.server.entity.product.Services;
import com.server.repository.product.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * Сервис для работы с услугами
 */
@Service
public class ServiceForServices {

    @Autowired
    private final ServicesRepository servicesRepository;


    public ServiceForServices(ServicesRepository servicesRepository) {

        this.servicesRepository = servicesRepository;

    }


    /**
     * Медот для создания услуги
     * @param servicesDto характеристики услуги
     * @return объект услуги
     */
    public Services create(ServicesDto servicesDto) {

        Services services = new Services();
        services.setProductName(servicesDto.productName());
        services.setAmount(servicesDto.amount());

        servicesRepository.save(services);

        return services;

    }


    /**
     * Метод для получения списка всех услуг
     * @return список объектов услуги
     */
    public List<Services> readAll() {

        return servicesRepository.findAll();

    }


    /**
     * Метод для получения одной конкретной услуги
     * @param id идентификатор услуги
     * @return объект услуги
     */
    public Services readId(Integer id) {

        return servicesRepository.getReferenceById(id);

    }

    /**
     * Метод для обновления информации по услуге
     * @param servicesDto - новые характеристики услуги
     * @param id идентификатор редактируемой услуги
     * @return обновленный объект услуги
     */
    public Services update(ServicesDto servicesDto, Integer id) {

        // Если услуга с таким идентификатором есть в базе выполняем обновление

        if (servicesRepository.existsById(id)) {

            Services services = servicesRepository.getReferenceById(id);

            // Если не передано новое название, то название не меняем

            if (!servicesDto.productName().isBlank()){

                services.setProductName(servicesDto.productName());

            }

            services.setAmount(servicesDto.amount());

            servicesRepository.save(services);

            return services;

        } else {

            return null;

        }

    }


    /**
     * Метод для удаления конкретной услуги
     * @param id идентификатор услуги
     * @return true - при успешном удалении, false - в противном случае
     */
    public boolean delete(Integer id) {

        if (servicesRepository.existsById(id)) {

            servicesRepository.deleteById(id);

            return true;

        } else {

            return false;

        }

    }

}
