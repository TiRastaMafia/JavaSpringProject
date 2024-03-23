package com.server.controller.user;

import com.server.dto.user.ClientDto;
import com.server.dto.user.RequestClientDto;
import com.server.dto.user.UserRegistrationDto;
import com.server.entity.user.Gender;
import com.server.service.user.userServiceFacade.ClientServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class RestClientController implements IClientController{

    private final ClientServiceFacade clientService;

    @Autowired
    public RestClientController(ClientServiceFacade clientService) {
        this.clientService = clientService;
    }


    @Override
    public ResponseEntity<RequestClientDto> create(UserRegistrationDto userRegistrationDto) {

        RequestClientDto client = clientService.serialisInDtoObject(
                clientService.create(userRegistrationDto)
        );

        return new ResponseEntity<>(client, HttpStatus.CREATED);

    }


    @Override
    public ResponseEntity<List<RequestClientDto>> readAll() {

        final List<RequestClientDto> clients = clientService.serialisInListDtoObject(
                clientService.readAll()
        );

        return clients != null &&  !clients.isEmpty()
                ? new ResponseEntity<>(clients, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


    @Override
    public ResponseEntity<RequestClientDto> readId(int id) {

        final RequestClientDto client = clientService.serialisInDtoObject(
                clientService.read(id)
        );

        return client != null
                ? new ResponseEntity<>(client, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


    @Override
    public ResponseEntity<RequestClientDto> update(int id, ClientDto clientDto) {

        final RequestClientDto client = clientService.serialisInDtoObject(
                clientService.update(clientDto, id)
        );

        return client != null
                ? new ResponseEntity<>(client, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    }


    @Override
    public ResponseEntity<?> delete(int id) {

        final boolean deleted = clientService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    }


    @Override
    public ResponseEntity<List<RequestClientDto>> filterByGender(Gender gender){

        final List<RequestClientDto> clients = clientService.serialisInListDtoObject(
                clientService.filterByGender(gender)
        );

        return clients != null &&  !clients.isEmpty()
                ? new ResponseEntity<>(clients, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @Override
    public ResponseEntity<RequestClientDto> filterByPhone(String phone) {

        final RequestClientDto client = clientService.serialisInDtoObject(
                clientService.findByPhone(phone)
        );

        return client != null
                ? new ResponseEntity<>(client, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}