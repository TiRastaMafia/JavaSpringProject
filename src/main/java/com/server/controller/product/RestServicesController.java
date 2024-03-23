package com.server.controller.product;

import com.server.dto.product.ServicesDto;
import com.server.entity.product.Services;
import com.server.service.product.ServiceForServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class RestServicesController implements IServicesController{

    private final ServiceForServices serviceForServices;


    @Autowired
    public RestServicesController(ServiceForServices serviceForServices) {

        this.serviceForServices = serviceForServices;

    }


    @Override
    public ResponseEntity<?> create(ServicesDto servicesDto) {

        Services services = serviceForServices.create(servicesDto);

        return new ResponseEntity<>(services, HttpStatus.CREATED);
    }


    @Override
    public ResponseEntity<List<Services>> readAll() {

        final List<Services> servicesList = serviceForServices.readAll();

        return servicesList != null && !servicesList.isEmpty() ?
                new ResponseEntity<>(servicesList, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @Override
    public ResponseEntity<Services> readId(int id) {

        final Services services = serviceForServices.readId(id);

        return services != null ?
                new ResponseEntity<>(services, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @Override
    public ResponseEntity<?> update(int id, ServicesDto servicesDto) {

        final Services services = serviceForServices.update(servicesDto, id);

        return services != null ?
                new ResponseEntity<>(services, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


    @Override
    public ResponseEntity<?> delete(int id) {
        final boolean deleted = serviceForServices.delete(id);

        return deleted ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
