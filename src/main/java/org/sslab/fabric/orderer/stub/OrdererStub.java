package org.sslab.fabric.orderer.stub;///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.sslb.fabric.stub.orderer;
//
//import io.grpc.Server;
//import io.grpc.ServerBuilder;
//
//import java.io.IOException;
//
///**
// *
// * @author jeonghma
// */
//
//public class OrdererStub {
//    private final int port;
//    private final Server server;
//
//    public OrdererStub(int _port) throws IOException {
//        port = _port;
//        // io.grpc.ServerBuilder를 사용하여 코드 설정 및 stubService 추가
//        server = ServerBuilder.forPort(port)
//            .addService(new StubService())
//            .build();
//    }
//
//    public void start() throws IOException {
//        server.start();     // 서버를 시작합니다.
//        // 서버가 SIGTERM을 받았을때 종료하기 위한 shutdown hook을 추가합니다.
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//            @Override
//            public void run() {
//                OrdererStub.this.stop();   // SIGTERM을 받으면 서버를 종료합니다.
//            }
//        });
//    }
//
//    public void stop() {
//        if (server != null) {
//            server.shutdown();      // 서버를 종료합니다.
//        }
//    }
//
//    public void blockUntilShutdown() throws InterruptedException {
//        if (server != null) {
//            server.awaitTermination();  // 서버가 SIGTERM을 받아서 종료될 수 있도록 await 합니다.
//        }
//    }
//}
