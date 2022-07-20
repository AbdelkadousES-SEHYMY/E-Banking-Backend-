package ma.abdelkadous.ebanking_backend.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.abdelkadous.ebanking_backend.dtos.CustomerDTO;
import ma.abdelkadous.ebanking_backend.entities.Customer;
import ma.abdelkadous.ebanking_backend.exceptions.CustomerNotFoundException;
import ma.abdelkadous.ebanking_backend.mappers.DtoMapper;
import ma.abdelkadous.ebanking_backend.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CutomerService implements ICustomerService{

    private CustomerRepository customerRepository;
    private DtoMapper dtoMapper;

    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer= customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFoundException("Customer Not Found"));
        return dtoMapper.fromCustomer(customer);
    }
    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Customer");
        Customer customer=dtoMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(savedCustomer);
    }
    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("Updating customer");
        Customer customer=dtoMapper.fromCustomerDTO(customerDTO);
        Customer updatedCustomer = customerRepository.save(customer);
        return dtoMapper.fromCustomer(updatedCustomer);
    }
    @Override
    public void deleteCustomer(Long customerId){
        customerRepository.deleteById(customerId);
    }
    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customers=customerRepository.findAll();
        List<CustomerDTO> listCustomers =customers.stream().map(customer -> dtoMapper.fromCustomer(customer)).collect(Collectors.toList());
        return listCustomers;
    }

}
