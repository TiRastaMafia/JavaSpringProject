package com.server.service.user.apiUserService;

import com.server.entity.user.ApiUsers;
import com.server.repository.user.ApiUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ApiUsersService implements IApiUsersService<ApiUsers, Integer, String> {

    @Autowired
    private ApiUsersRepository apiUsersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void create(ApiUsers apiUsers) {

        // Запись захэшированного пароля

        apiUsers.setPassword(passwordEncoder.encode(apiUsers.getPassword()));

        apiUsersRepository.save(apiUsers);

    }

    @Override
    public List<ApiUsers> readAll() {

        return apiUsersRepository.findAll();

    }

    @Override
    public ApiUsers readId(Integer id) {

        return apiUsersRepository.getReferenceById(id);

    }

    @Override
    public boolean update(ApiUsers apiUsers, Integer id) {

        if (apiUsersRepository.existsById(id)) {

            apiUsers.setId(id);

            apiUsersRepository.save(apiUsers);

            return true;

        } else {

            return false;

        }

    }

    @Override
    public boolean delete(Integer id) {

        if (apiUsersRepository.existsById(id)) {

            apiUsersRepository.deleteById(id);

            return true;

        } else {

            return false;

        }

    }


    @Override
    public ApiUsers findApiUsersByPhone(String phone) {

        Optional<ApiUsers> apiUsersOptional = apiUsersRepository.findApiUsersByPhone(phone);

        return apiUsersOptional.orElse(null);
        
    }

}
