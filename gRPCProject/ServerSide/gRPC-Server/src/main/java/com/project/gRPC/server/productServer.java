package com.project.gRPC.server;

import com.project.gRPC.database.databaseConfig;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import product.ProductServiceGrpc;
import product.Schema;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class productServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8081)
                .addService(new ProductServiceImpl())
                .build();

        server.start();
        System.out.println("SERVER STARTED ON PORT: " + server.getPort());

        server.awaitTermination();
    }

    static class ProductServiceImpl extends ProductServiceGrpc.ProductServiceImplBase {
        @Override
        public void addProduct(Schema.ProductRequest request, StreamObserver<Schema.ProductResponse> responseObserver) {
            try (Connection connection = databaseConfig.getConnection()) {
                String query = "INSERT INTO products (name, description, price, stock_quantity) VALUES (?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setString(1, request.getName());
                    statement.setString(2, request.getDescription());
                    statement.setFloat(3, request.getPrice());
                    statement.setInt(4, request.getStockQuantity());
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("A new product was inserted successfully.");
                        Schema.ProductResponse response = Schema.ProductResponse.newBuilder()
                                .setMessage("Product added successfully into the database")
                                .build();
                        responseObserver.onNext(response);
                        responseObserver.onCompleted();
                    } else {
                        System.out.println("Failed to insert the new product.");
                        Schema.ProductResponse response = Schema.ProductResponse.newBuilder()
                                .setMessage("Product failed to add into the database")
                                .build();
                        responseObserver.onNext(response);
                        responseObserver.onCompleted();
                    }
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
                Schema.ProductResponse response = Schema.ProductResponse.newBuilder()
                        .setMessage("A database error occurred")
                        .build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
            }
        }
    }
}
