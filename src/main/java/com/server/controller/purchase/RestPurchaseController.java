package com.server.controller.purchase;

import com.server.dto.purchase.RequestPurchaseDto;
import com.server.entity.payment.PaymentMethod;
import com.server.service.purchase.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class RestPurchaseController implements IPurchaseController {


    private final PurchaseService purchaseService;


    @Autowired
    public RestPurchaseController(PurchaseService purchaseService){
        this.purchaseService = purchaseService;
    }


    @Override
    public ResponseEntity<RequestPurchaseDto> create(int[] goodsId, int[] servicesId){

        RequestPurchaseDto purchase = purchaseService.serialisInDtoObject(
                purchaseService.create(goodsId, servicesId)
        );

        return new ResponseEntity<>(purchase, HttpStatus.CREATED);

    }


    @Override
    public ResponseEntity<RequestPurchaseDto> manualCreate(int[] goodsId, int[] servicesId, String phone){

        RequestPurchaseDto purchase = purchaseService.serialisInDtoObject(
                purchaseService.manualCreate(goodsId, servicesId, phone)
        );

        return new ResponseEntity<>(purchase, HttpStatus.CREATED);

    }


    @Override
    public ResponseEntity<List<RequestPurchaseDto>> readAll() {

        final List<RequestPurchaseDto> purchases = purchaseService.serialisInListDtoObject(
                purchaseService.readAll()
        );

        return purchases != null &&  !purchases.isEmpty()
                ? new ResponseEntity<>(purchases, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


    @Override
    public ResponseEntity<RequestPurchaseDto> readId(int id) {

        final RequestPurchaseDto purchase = purchaseService.serialisInDtoObject(
                purchaseService.readId(id)
        );

        return purchase != null
                ? new ResponseEntity<>(purchase, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


    @Override
    public ResponseEntity<RequestPurchaseDto> update(int id, int[] goodsId, int[] servicesId){

        final RequestPurchaseDto purchase = purchaseService.serialisInDtoObject(
                purchaseService.update(id, goodsId, servicesId)
        );

        return purchase != null
                ? new ResponseEntity<>(purchase, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    }


    @Override
    public ResponseEntity<?> delete(int id) {

        final boolean deleted = purchaseService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    }

    @Override
    public ResponseEntity<?> pay(int id, PaymentMethod paymentMethod) {

        final boolean pay = purchaseService.pay(id, paymentMethod);

        return pay
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);

    }

}
