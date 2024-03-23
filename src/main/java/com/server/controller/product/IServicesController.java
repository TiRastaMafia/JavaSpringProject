package com.server.controller.product;

import com.server.dto.product.ServicesDto;
import com.server.entity.product.Services;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1")
@Tag(
        name = "Услуги",
        description = "Все методы для работы с услугами")
public interface IServicesController {


    /**
     * Метод добавления новой услуги
     * @param servicesDto характеристики услуги
     * @return информация о добавленной услуге
     */
    @RequestMapping(value = "/services", method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<?> create(@RequestBody ServicesDto servicesDto);



    /**
     * Метод для получения списка всех имеющихся услуг
     * @return список добавленных услуг
     */
    @RequestMapping(value = "/services", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT', 'LEAD')")
    ResponseEntity<List<Services>> readAll();



    /**
     * Метод для получения одной конкретной услуги
     * @param id идентификатор услуги
     * @return информация по указанной услуге
     */
    @RequestMapping(value = "/services/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT', 'LEAD')")
    ResponseEntity<Services> readId(@PathVariable(name = "id") int id);



    /**
     * Метод для обновления характеристик услуги
     * @param id идентификатор обновляемой услуги
     * @param servicesDto обновленные характеристики услуги
     * @return информация об обновленной услуге
     */
    @RequestMapping(value = "/services/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<?> update(@PathVariable(name = "id") int id, @RequestBody ServicesDto servicesDto);



    /**
     * Метод для удаления услуги
     * @param id идентификатор услуги
     * @return статус код, соответствующий результату выполнения операции
     */
    @RequestMapping(value = "/services/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<?> delete(@PathVariable(name = "id") int id);

}
