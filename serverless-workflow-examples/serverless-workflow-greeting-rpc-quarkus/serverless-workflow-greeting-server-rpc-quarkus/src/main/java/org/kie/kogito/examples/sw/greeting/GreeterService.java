/*
 * Copyright 2022 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.kogito.examples.sw.greeting;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.kie.kogito.examples.sw.greeting.Greeting.HelloReply;
import org.kie.kogito.examples.sw.greeting.Greeting.HelloReply.Builder;
import org.kie.kogito.examples.sw.greeting.Greeting.HelloReply.State;
import org.kie.kogito.examples.sw.greeting.Greeting.HelloRequest;
import org.kie.kogito.examples.sw.greeting.Greeting.InnerMessage;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

public class GreeterService extends GreeterGrpc.GreeterImplBase {

    private static final Logger logger = Logger.getLogger(GreeterService.class.getName());

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = buildServer(Integer.getInteger("grpc.port", 50051));
        server.start();
        server.awaitTermination();
    }

    public static Server buildServer(int port) {
        return ServerBuilder.forPort(port).addService(new GreeterService()).build();
    }

    @Override
    public void sayHello(HelloRequest request,
            StreamObserver<HelloReply> responseObserver) {
        String message;
        switch (request.getLanguage().toLowerCase()) {
            case "spanish":
                message = "Saludos desde gRPC service " + request.getName();
                break;
            case "english":
            default:
                message = "Hello from gRPC service " + request.getName();
        }
        Builder builder = HelloReply.newBuilder().setMessage(message);
        if (request.getInnerHello().getUnknown()) {
            logger.log(Level.INFO, "unknown received");
            builder.setState(State.UNKNOWN);
        } else {
            logger.log(Level.INFO, "unknown not received");
            builder.setState(State.SUCCESS);
        }
        builder.setInnerMessage(InnerMessage.newBuilder().setNumber(23).build());
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
