package com.server.service.user.userServiceFacade;

import com.server.dto.user.ModeratorDto;
import com.server.dto.user.RequestModeratorDto;
import com.server.dto.user.UserRegistrationDto;
import com.server.entity.user.ApiUsers;
import com.server.entity.user.Moderator;
import com.server.entity.user.Role;
import com.server.service.user.apiUserService.ApiUsersService;
import com.server.service.user.moderatorService.ModeratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для синхронизации работы всех необходимых сервисов для работы с администраторами
 */
@Service
public class ModeratorServiceFacade implements IUserServiceFacade<Moderator, ModeratorDto, Integer>{

    private final ApiUsersService apiUsersService;

    private final ModeratorService moderatorService;

    @Autowired
    public ModeratorServiceFacade(ApiUsersService apiUsersService, ModeratorService moderatorService) {
        this.apiUsersService = apiUsersService;
        this.moderatorService = moderatorService;
    }


    @Override
    public Moderator create(UserRegistrationDto userRegistrationDto) {

        ApiUsers apiUser = new ApiUsers();
        apiUser.setPhone(userRegistrationDto.phone());
        apiUser.setRole(Role.ADMIN);
        apiUser.setPassword(userRegistrationDto.password());

        apiUsersService.create(apiUser);

        Moderator moderator = new Moderator();
        moderator.setName(userRegistrationDto.name());
        moderator.setApiUsers(apiUser);

        moderatorService.create(moderator);

        return moderator;

    }

    @Override
    public List<Moderator> readAll() {

        return moderatorService.readAll();

    }

    @Override
    public Moderator read(Integer id) {

        return moderatorService.readId(id);

    }

    @Override
    public Moderator update(ModeratorDto moderatorDto, Integer id) {

        return moderatorService.update(moderatorDto, id);

    }

    @Override
    public boolean delete(Integer id) {

        return moderatorService.delete(id);

    }

    /**
     * Метод для поиска одминистратора по номеру телефона
     * @param phone номер телефона
     * @return объект Moderator с указанным номером телефона
     */
    public Moderator findByPhone(String phone){

        return moderatorService.findByApiUsers(apiUsersService.findApiUsersByPhone(phone));

    }


    /**
     * Публичный метод для сериализации данных одного объекта,
     * полученного из базы данных в Dto для передачи на контроллер
     */
    public RequestModeratorDto serialisInDtoObject(Moderator moderator){

        return new RequestModeratorDto(
                moderator.getId(),
                moderator.getName(),
                moderator.getApiUsers().getPhone()
        );

    }


    /**
     * Публичный метод для сериализации данных списка объектов,
     * полученных из базы данных в Dto для передачи на контроллер
     */
    public List<RequestModeratorDto> serialisInListDtoObject(List<Moderator> moderatorList){

        List<RequestModeratorDto> moderatorDtoList = new ArrayList<>();

        for(Moderator moderator : moderatorList){
            moderatorDtoList.add(new RequestModeratorDto(
                    moderator.getId(),
                    moderator.getName(),
                    moderator.getApiUsers().getPhone()
            ));
        }

        return moderatorDtoList;

    }

}
