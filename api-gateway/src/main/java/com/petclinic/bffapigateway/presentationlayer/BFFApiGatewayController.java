package com.petclinic.bffapigateway.presentationlayer;

import com.petclinic.bffapigateway.domainclientlayer.CustomersServiceClient;
import com.petclinic.bffapigateway.domainclientlayer.VetsServiceClient;
import com.petclinic.bffapigateway.domainclientlayer.VisitsServiceClient;
import com.petclinic.bffapigateway.dtos.OwnerDetails;
import com.petclinic.bffapigateway.dtos.VetDetails;
import com.petclinic.bffapigateway.dtos.Visits;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Maciej Szarlinski
 * @author Christine Gerard
 * Copied from https://github.com/spring-petclinic/spring-petclinic-microservices
 * Modified to remove circuitbreaker
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gateway")
public class BFFApiGatewayController {

    private final CustomersServiceClient customersServiceClient;

    private final VisitsServiceClient visitsServiceClient;

    private final VetsServiceClient vetsServiceClient;

    @GetMapping(value = "owners/{ownerId}")
    public Mono<OwnerDetails> getOwnerDetails(final @PathVariable int ownerId) {
        return customersServiceClient.getOwner(ownerId)
                .flatMap(owner ->
                        visitsServiceClient.getVisitsForPets(owner.getPetIds())
                                .map(addVisitsToOwner(owner))
                );

    }

    @GetMapping(value = "customer/owners")
    public Flux<OwnerDetails> getOwners() {

        return customersServiceClient.getOwners()
                .flatMap(n ->
                        visitsServiceClient.getVisitsForPets(n.getPetIds())
                .map(addVisitsToOwner(n))
                );
    }


    private Function<Visits, OwnerDetails> addVisitsToOwner(OwnerDetails owner) {
        return visits -> {
            owner.getPets()
                    .forEach(pet -> pet.getVisits()
                            .addAll(visits.getItems().stream()
                                    .filter(v -> v.getPetId() == pet.getId())
                                    .collect(Collectors.toList()))
                    );
            return owner;
        };
    }

    @GetMapping(value = "vet/vets")
    public Flux<VetDetails> getVets() {

        return vetsServiceClient.getVets();

        }
}
