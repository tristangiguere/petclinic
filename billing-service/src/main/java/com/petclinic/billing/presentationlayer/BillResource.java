package com.petclinic.billing.presentationlayer;

import com.petclinic.billing.businesslayer.BillMapper;
import com.petclinic.billing.businesslayer.BillService;
import com.petclinic.billing.datalayer.Bill;
import com.petclinic.billing.datalayer.BillDTO;
import com.petclinic.billing.datalayer.BillRepository;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class BillResource {
    private final BillService SERVICE;

    BillResource(BillService service){
        this.SERVICE = service;
    }

    // Create Bill //
    @PostMapping("/bills")
    @ResponseStatus(HttpStatus.CREATED)
    public BillDTO createBill(@Valid @RequestBody BillDTO billDTO){
        return SERVICE.CreateBill(billDTO);
    }

    // Read Bill //
    @GetMapping(value = "/{billId}")
    public BillDTO findBill(@PathVariable("billId") int billId){
        return SERVICE.GetBill(billId);
    }

    // Delete Bill //
    @DeleteMapping(value = "/{billId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBill(@PathVariable("billId") int billId){
        SERVICE.DeleteBill(billId);
    }
}
