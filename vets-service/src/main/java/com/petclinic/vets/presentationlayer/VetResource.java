package com.petclinic.vets.presentationlayer;

import com.petclinic.vets.datalayer.Vet;
import com.petclinic.vets.datalayer.VetRepository;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Maciej Szarlinski
 * Copied from https://github.com/spring-petclinic/spring-petclinic-microservices
 */

@RequestMapping("/vets")
@RestController
@Timed("petclinic.vet")
@RequiredArgsConstructor
class VetResource {
    private final VetRepository vetRepository;

    VetResource(VetRepository vetRepository){this.vetRepository = vetRepository;}

    @GetMapping
    public List<Vet> showResourcesVetList() {
        return vetRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vet addVet(@Valid @RequestBody Vet vet) {
        return vetRepository.save(vet);
    }

    @GetMapping(value = "/{vetId}")
    public Optional<Vet> findVetById(@PathVariable("vetId") int vetId) {
        return vetRepository.findById(vetId);
    }

}
