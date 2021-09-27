package com.petclinic.vets.presentationlayer;

import com.petclinic.vets.datalayer.Vet;
import com.petclinic.vets.datalayer.VetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest

public class JpaRepoTest
{
    @Autowired
    private VetRepository vetRepository;

    @BeforeEach
    public void setUpDB()
    {
        Vet vet1 = new Vet(1, 234568, "James", "Carter", "carter.james@email.com", "(514)-634-8276 #2384", "practicing since 3 years", "Monday, Tuesday, Friday", 1, null);
        vetRepository.save(vet1);
        Vet vet2 = new Vet(2, 327874, "Helen", "Leary", "leary.helen@email.com", "(514)-634-8276 #2385", "Practicing since 10 years", "Wednesday, Thursday", 1, null);
        vetRepository.save(vet2);
    }

    @Test
    public void saveVetTest()
    {
        Vet vet1 = new Vet(1, 234568, "James", "Carter", "carter.james@email.com", "(514)-634-8276 #2384", "practicing since 3 years", "Monday, Tuesday, Friday", 1, null);

        vetRepository.save(vet1);
        assertThat(vetRepository.count()).isGreaterThan(0);
    }

    @Test
    public void getVetByVetIdTest()
    {
        Vet vet = vetRepository.findByVetId(234568).get();
        assertEquals(vet.getVetId(), 234568);
    }

}
