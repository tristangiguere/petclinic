package com.petclinic.bffapigateway.domainclientlayer;

import com.petclinic.bffapigateway.dtos.OwnerDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author Maciej Szarlinski
 * @author Christine Gerard
 * Copied from https://github.com/spring-petclinic/spring-petclinic-microservices
 * Modified to remove circuitbreaker
 */

@Component
public class CustomersServiceClient {

    private final WebClient.Builder webClientBuilder;
    private final String customersServiceUrl;

    public CustomersServiceClient(
            WebClient.Builder webClientBuilder,
            @Value("${app.customers-service.host}") String customersServiceHost,
            @Value("${app.customers-service.port}") String customersServicePort
    ) {
        this.webClientBuilder = webClientBuilder;
        customersServiceUrl = "http://" + customersServiceHost + ":" + customersServicePort + "/owners";
    }


    public Mono<OwnerDetails> getOwner(final int ownerId) {
        return webClientBuilder.build().get()
                .uri(customersServiceUrl + "/{ownerId}", ownerId)
                .retrieve()
                //.exchange()
                //.flatMap(clientResponse -> clientResponse.bodyToMono(OwnerDetails.class));
                .bodyToMono(OwnerDetails.class);
    }
}
