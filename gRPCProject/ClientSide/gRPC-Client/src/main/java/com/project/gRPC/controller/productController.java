package com.project.gRPC.controller;

import com.project.gRPC.client.productClient;
import com.project.gRPC.entity.productEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/add/products")
public class productController {
    productClient productClient = new productClient();
    @PostMapping
    public void requestProductObject(@RequestBody productEntity productEntity){
        System.out.println("A NEW REQUEST RECEIVED..");
        productClient.notifyServer(productEntity);
//
    }
}
