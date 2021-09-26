package com.petclinic.vets.presentationlayer;

import com.petclinic.vets.datalayer.Vet;
import com.petclinic.vets.datalayer.VetRepository;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
@RequiredArgsConstructor
class VetResource {

    private final VetRepository vetRepository;

    VetResource(VetRepository vetRepository){this.vetRepository = vetRepository;}

    @GetMapping
    public List<Vet> showResourcesVetList() {
        return vetRepository.findAll();
    }
    @PutMapping(path = "/{vetId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<Vet> disableVet(@PathVariable("vetId") int vetId) {
        var vet = findVet(vetId);
        vet.get().setIsActive(1);
        return vetRepository.findById(vetId);
    }

    @GetMapping(value = "/{vetId}")
    public Optional<Vet> findVet(@PathVariable("vetId") int vetId) {
        return vetRepository.findById(vetId);
    }

}
