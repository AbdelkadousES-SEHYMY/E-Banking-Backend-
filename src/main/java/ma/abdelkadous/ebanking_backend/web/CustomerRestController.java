package ma.abdelkadous.ebanking_backend.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.abdelkadous.ebanking_backend.dtos.CustomerDTO;
import ma.abdelkadous.ebanking_backend.exceptions.CustomerNotFoundException;
import ma.abdelkadous.ebanking_backend.services.CutomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/customers")

public class CustomerRestController {
    private CutomerService cutomerService;

    @GetMapping
    public List<CustomerDTO> customers(){
        return cutomerService.listCustomers();
    }
    @GetMapping("{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {

        return cutomerService.getCustomer(customerId);
    }
    @PostMapping
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return cutomerService.saveCustomer(customerDTO);
    }
    @PutMapping("{cutomerId}")
    public CustomerDTO updateCustomer(@PathVariable Long customerId,@RequestBody CustomerDTO customerDTO){
        customerDTO.setId(customerId);
        return cutomerService.updateCustomer(customerDTO);
    }
    @DeleteMapping("{id}")
    public void deleteCustomer(Long id){
        cutomerService.deleteCustomer(id);
    }



}
