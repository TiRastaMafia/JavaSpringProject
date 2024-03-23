package com.server.service.user.moderatorService;


import com.server.dto.user.ModeratorDto;
import com.server.entity.user.ApiUsers;
import com.server.entity.user.Moderator;
import com.server.repository.user.ModeratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Сервис для работы с администраторами
 */
@Service
public class ModeratorService{

    @Autowired
    private ModeratorRepository moderatorRepository;


    /**
     * Метод для создания/регистрации нового администратора
     * @param moderator данные администратора
     */
    public void create(Moderator moderator) {

        moderatorRepository.save(moderator);

    }

    /**
     * Метод для получения списка всех администраторов
     * @return список объектор типа Moderator
     */
    public List<Moderator> readAll() {

        return moderatorRepository.findAll();

    }

    /**
     * Метод для получения одного конкретного администратора
     * @param id идентификатор администратора
     * @return объект типа Moderator
     */
    public Moderator readId(Integer id) {

        return moderatorRepository.getReferenceById(id);

    }

    /**
     * Метод для обновления данных администратора
     * @param moderatorDto новые данные администратора
     * @param id идентификатор обновляемого администратора
     * @return объект Moderator с обновленными данными
     */
    public Moderator update(ModeratorDto moderatorDto, Integer id) {

        if (moderatorRepository.existsById(id)) {

            Moderator moderator = moderatorRepository.getReferenceById(id);

            if (!moderatorDto.name().isBlank()){

                moderator.setName(moderatorDto.name());

            }

            moderatorRepository.save(moderator);

            return moderator;

        } else {

            return null;

        }

    }

    /**
     * Метод для удаления администратора
     * @param id идентификатор администратора
     * @return true - при успешном удалении, false - в противном случае
     */
    public boolean delete(Integer id) {

        if (moderatorRepository.existsById(id)) {

            moderatorRepository.deleteById(id);

            return true;

        } else {

            return false;

        }

    }

    /**
     * Метод для поиска администратора по его данным из api_users
     * @param apiUsers данные api_users администратора
     * @return объект типа Moderator
     */
    public Moderator findByApiUsers(ApiUsers apiUsers){

        return moderatorRepository.findModeratorByApiUsers(apiUsers);

    }

}
