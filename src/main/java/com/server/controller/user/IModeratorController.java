package com.server.controller.user;

import com.server.dto.user.ModeratorDto;
import com.server.dto.user.RequestModeratorDto;
import com.server.dto.user.UserRegistrationDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/v1")
@Tag(
        name = "Администраторы",
        description = "Все методы для работы с администраторами системы"
)
public interface IModeratorController {

    /**
     * Метод для регистрации нового администратора
     * @param userRegistrationDto регистрацинные данные администратора
     * @return информация по зарегистрированному администратору
     */
    @RequestMapping(value = "/new-moderator", method = RequestMethod.POST)
    ResponseEntity<RequestModeratorDto> create(@RequestBody UserRegistrationDto userRegistrationDto);


    /**
     * Метод для получения списка всех администраторов
     * @return информация по всем зарегистрированным в системе администраторам
     */
    @RequestMapping(value = "/moderator", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<List<RequestModeratorDto>> readAll();


    /**
     * Метод для получения информации об одном конкретном администраторе
     * @param id идентификатор аминистратора
     * @return информация по указанному администратору
     */
    @RequestMapping(value = "/moserator/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<RequestModeratorDto> readId(@PathVariable(name = "id") int id);


    /**
     * Метод для обновления информации по администратору
     * @param id идентификатор администратора
     * @param moderatorDto обновленная информация администратора
     * @return обновленная информация по указанному администратору
     */
    @RequestMapping(value = "/moderator/{id}", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<RequestModeratorDto> update(
            @PathVariable(name = "id") int id,
            @RequestBody ModeratorDto moderatorDto
    );


    /**
     * Метод для удаления администратора
     * @param id идентификатор администратора
     * @return статус код, соответствующий результату выполнения операции
     */
    @RequestMapping(value = "/moderator/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<?> delete(@PathVariable(name = "id") int id);


    /**
     * Метод для поиска администратора по номеру телефона
     * @param phone номер телефона
     * @return информация по администратору с указанным номером телефона
     */
    @RequestMapping(value = "moderator/filter-by-phone",method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    ResponseEntity<RequestModeratorDto> filterByPhone(@RequestParam("phone") String phone);


}
