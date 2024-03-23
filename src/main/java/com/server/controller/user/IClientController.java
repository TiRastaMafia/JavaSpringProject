package com.server.controller.user;

import com.server.dto.user.ClientDto;
import com.server.dto.user.RequestClientDto;
import com.server.dto.user.UserRegistrationDto;
import com.server.entity.user.Gender;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("v1")
@Tag(
        name = "Пользователи",
        description = "Все методы для работы с пользователями системы"
)
public interface IClientController {

    /**
     * Метод для регистрации нового пользователя
     * @param clientRegDto регистрацинные данные пользователя
     * @return информацию о зарегистрированном пользователе
     */
    @RequestMapping(value = "/new-client",method = RequestMethod.POST)
    ResponseEntity<RequestClientDto> create(@RequestBody UserRegistrationDto clientRegDto);


    /**
     * Метод для получения списка всех зарегистрированных пользователей
     * @return список информции по всем пользователям
     */
    @RequestMapping(value = "/client",method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<List<RequestClientDto>> readAll();


    /**
     * Метод для получения информации по одному конкретному пльзователю
     * @param id идентификатор пользователя
     * @return Информация по указанному пользователю
     */
    @RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT', 'LEAD')")
    ResponseEntity<RequestClientDto> readId(@PathVariable(name = "id") int id);


    /**
     * Метод для обновления информации по пользователю
     * @param id идентификатор пользователя
     * @param clientDto обновленная информаци по пользователю
     * @return обновленная информация по указанному пользователю
     */
    @RequestMapping(value = "/client/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CLIENT', 'LEAD')")
    ResponseEntity<RequestClientDto> update(
            @PathVariable(name = "id") int id,
            @RequestBody ClientDto clientDto
    );


    /**
     * Метод для удаления пользователя
     * @param id идентификатор пользователя
     * @return статус код, соответствующий результату выполнения операции
     */
    @RequestMapping(value = "/client/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<?> delete(@PathVariable(name = "id") int id);


    /**
     * Метод для фильтрации пользователей по полу
     * @param gender пол
     * @return список пользователей с указанным полом
     */
    @RequestMapping(value = "client/filter-by-gender",method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<List<RequestClientDto>> filterByGender(@RequestParam("gender") Gender gender);


    /**
     * Метод для поиска пользователя по номеру телефона
     * @param phone номер телефона
     * @return информация по пользователю с указанным номером телефона
     */
    @RequestMapping(value = "client/filter-by-phone",method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<RequestClientDto> filterByPhone(@RequestParam("phone") String phone);


}
