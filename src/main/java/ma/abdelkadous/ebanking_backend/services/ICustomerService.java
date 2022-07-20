package ma.abdelkadous.ebanking_backend.services;

import ma.abdelkadous.ebanking_backend.dtos.CustomerDTO;
import ma.abdelkadous.ebanking_backend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface ICustomerService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

    List<CustomerDTO> listCustomers();

}
