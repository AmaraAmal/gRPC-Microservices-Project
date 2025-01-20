package com.project.gRPC.client;

import com.project.gRPC.entity.productEntity;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import product.ProductServiceGrpc;
import product.Schema;

public class productClient {
    public void notifyServer(productEntity productEntity){
        System.out.println("NOTIFYING SERVER...");
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8081)
                .usePlaintext()
                .build();

        ProductServiceGrpc.ProductServiceBlockingStub stub = ProductServiceGrpc.newBlockingStub(channel);

        Schema.ProductRequest request = Schema.ProductRequest.newBuilder()
                .setName(productEntity.getName())
                .setDescription(productEntity.getDescription())
                .setPrice(productEntity.getPrice())
                .setStockQuantity(productEntity.getStock_quantity())
                .build();

        Schema.ProductResponse response = stub.addProduct(request);

        System.out.println("Response from server: " + response.getMessage());

        channel.shutdown();
    }
}
