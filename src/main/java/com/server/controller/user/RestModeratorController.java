package com.server.controller.user;

import com.server.dto.user.ModeratorDto;
import com.server.dto.user.RequestModeratorDto;
import com.server.dto.user.UserRegistrationDto;
import com.server.service.user.userServiceFacade.ModeratorServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class RestModeratorController implements IModeratorController{

    private final ModeratorServiceFacade moderatorService;

    @Autowired
    public RestModeratorController(ModeratorServiceFacade moderatorService) {
        this.moderatorService = moderatorService;
    }


    @Override
    public ResponseEntity<RequestModeratorDto> create(UserRegistrationDto userRegistrationDto) {

        RequestModeratorDto moderator = moderatorService.serialisInDtoObject(
                moderatorService.create(userRegistrationDto)
        );

        return new ResponseEntity<>(moderator, HttpStatus.CREATED);

    }

    @Override
    public ResponseEntity<List<RequestModeratorDto>> readAll() {

        final List<RequestModeratorDto> moderators = moderatorService.serialisInListDtoObject(
                moderatorService.readAll()
        );

        return moderators != null &&  !moderators.isEmpty()
                ? new ResponseEntity<>(moderators, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @Override
    public ResponseEntity<RequestModeratorDto> readId(int id) {

        final RequestModeratorDto moderator = moderatorService.serialisInDtoObject(
                moderatorService.read(id)
        );

        return moderator != null
                ? new ResponseEntity<>(moderator, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @Override
    public ResponseEntity<RequestModeratorDto> update(int id, ModeratorDto moderatorDto) {

        final RequestModeratorDto moderator = moderatorService.serialisInDtoObject(
                moderatorService.update(moderatorDto, id)
        );

        return moderator != null
                ? new ResponseEntity<>(moderator, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    }

    @Override
    public ResponseEntity<?> delete(int id) {

        final boolean deleted = moderatorService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    }

    @Override
    public ResponseEntity<RequestModeratorDto> filterByPhone(String phone) {

        final RequestModeratorDto moderator = moderatorService.serialisInDtoObject(
                moderatorService.findByPhone(phone)
        );

        return moderator != null
                ? new ResponseEntity<>(moderator, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
