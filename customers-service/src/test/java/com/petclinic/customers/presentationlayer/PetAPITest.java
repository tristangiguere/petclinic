package com.petclinic.customers.presentationlayer;

import com.petclinic.customers.businesslayer.OwnerService;
import com.petclinic.customers.businesslayer.PetService;
import com.petclinic.customers.datalayer.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * @author Maciej Szarlinski
 * Copied from https://github.com/spring-petclinic/spring-petclinic-microservices
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(PetResource.class)
@ActiveProfiles("test")
class PetAPITest {

    @Autowired
    MockMvc mvc;

    @MockBean
    OwnerService ownerService;

    @MockBean
    PetService petService;


    public Owner setupOwner()
    {
        Owner owner = new Owner();
        owner.setId(1);
        owner.setFirstName("John");
        owner.setLastName("Wick");
        owner.setTelephone("5144041234");
        owner.setCity("Montreal");
        owner.setAddress("420 Avenue");

        return owner;
    }

    public Pet setupPet() {

        Owner owner = setupOwner();

        Pet pet = new Pet();
        pet.setName("Daisy");
        pet.setId(2);

        PetType petType = new PetType();
        petType.setId(6);
        pet.setType(petType);

        owner.addPet(pet);
        return pet;
    }

    /**
     * ------------------------ FIND_BY_PET_ID_API_TEST ------------------------
     * Test an HTTP Get Request
     */
    @Test
    void findByPetId_API_TEST() throws Exception {

        Pet pet = setupPet();
        given(petService.findByPetId(2)).willReturn(Optional.of(pet));
        mvc.perform(get("/owners/*/pets/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.name").value("Daisy"));
    }


    /**
     * ------------------------ DELETE_PET_API_TEST ------------------------
     * Test an HTTP Delete Request
     */
    @Test
    void deletePet_API_TEST() throws Exception {
        mvc.perform(delete("/owners/1/pets/2").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(petService, times(1)).deletePet(2, 1);
    }

    /**
     * ------------------------ SETUP_OWNER ------------------------
     * Test an HTTP Get Request, but to get all owners
     */
    @Test
    void findAll_API_TEST() throws Exception {

        //TEST DATA
        Pet pet_1 = new Pet();
        pet_1.setId(1);
        pet_1.setName("John");

        Pet pet_2 = new Pet();
        pet_2.setId(2);
        pet_2.setName("John");

        Pet pet_3 = new Pet();
        pet_3.setId(3);
        pet_3.setName("John");

        given(petService.findAll()).willReturn(asList(pet_1, pet_2, pet_3));
        mvc.perform(get("/owners/*/pets").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[2].id").value(3));

    }

    /**
     * ------------------------ CREATE_OWNER ------------------------
     * Test an HTTP POST request
     */

    /*
    @Test
    void createOwner_API_TEST() throws Exception {
        Owner owner = setupOwner();
        when(ownerService.findByOwnerId(owner.getId())).thenReturn(Optional.of(owner));


        Pet pet = setupPet();
        when(petService.CreatePet(any(PetRequest.class), owner.getId())).thenReturn(pet);
        mvc.perform(post("/owners/1/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"John\"," + "\"birthDate\": \"2000-09-09\"," + "\"typeId\": 4}"))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }
    */


}




