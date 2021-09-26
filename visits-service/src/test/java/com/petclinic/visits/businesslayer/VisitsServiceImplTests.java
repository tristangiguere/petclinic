package com.petclinic.visits.businesslayer;

import com.petclinic.visits.datalayer.Visit;
import com.petclinic.visits.datalayer.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.petclinic.visits.datalayer.Visit.visit;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.when;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.text.html.Option;
import java.sql.Time;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@SpringBootTest
@ExtendWith(SpringExtension.class)
public class VisitsServiceImplTests {

    @MockBean
    VisitRepository repo;

    @Autowired
    VisitsService visitsService;

    private Visit visit;

    @BeforeEach
    public void setupDb(){
        repo.deleteAll();

        // add setup data here
        visit = Visit.visit()
                .id(1)
                .petId(1)
                .build();
        Visit visit1 = Visit.visit()
                .id(2)
                .petId(1)
                .build();

        List<Visit> list = Arrays.asList(visit, visit1);
        repo.saveAll(list);
    }


    @Test
    public void whenValidPetIdThenShouldReturnVisitsForPet(){
        when(repo.findByPetId(1)).thenReturn(
                asList(
                        visit()
                                .id(1)
                                .petId(1)
                                .build(),
                        visit()
                                .id(2)
                                .petId(1)
                                .build()
                )
        );

        List<Visit> serviceResponse = visitsService.getVisitsForPet(1);

        assertThat(serviceResponse, hasSize(2));
        assertThat(serviceResponse.get(1).getPetId(), equalTo(1));
    }

    @Test
    public void whenValidPetIdThenCreateConfirmedVisitForPet(){
        Visit visit = visit().id(2).petId(1).status(true).build();

        when(repo.save(visit)).thenReturn(visit);

        Visit serviceResponse = repo.save(visit);

        assertThat(serviceResponse.getPetId(), equalTo(1));
        assertThat(serviceResponse.isStatus(), equalTo(true));
    }
  
    @Test
    public void whenValidPetIdThenShouldCreateVisitForPet() {
        Visit visit = visit().petId(1).date(new Date()).description("").practitionerId(123456).build();
        
        when(repo.save(visit)).thenReturn(visit);
        
        Visit serviceResponse = repo.save(visit);
        
        assertThat(serviceResponse.getPetId(), equalTo(1));
    }


    @Test
    public void whenValidPetIdThenCreateCanceledVisitForPet() {
        Visit visit = visit().id(2).petId(1).status(false).build();

        when(repo.save(visit)).thenReturn(visit);

        Visit serviceResponse = repo.save(visit);

        assertThat(serviceResponse.getPetId(), equalTo(1));
        assertThat(serviceResponse.isStatus(), equalTo(false));
    }
    
    @Test
    public void whenValidIdDeleteVisit(){
        Visit vise = new Visit(1, new Date(System.currentTimeMillis()), "Cancer", 1);
        when(repo.findById(1)).thenReturn(Optional.of(vise));
        visitsService.deleteVisit(1);
        verify(repo, times(1)).delete(vise);
    }

    @Test
    public void whenVisitDoNotExist(){
        Visit vise = new Visit(3, new Date(System.currentTimeMillis()), "Cancer", 1);
        visitsService.deleteVisit(3);
        verify(repo, never()).delete(vise);
    }

}
