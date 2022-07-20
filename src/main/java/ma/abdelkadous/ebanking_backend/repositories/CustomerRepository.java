package ma.abdelkadous.ebanking_backend.repositories;

import ma.abdelkadous.ebanking_backend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
}
