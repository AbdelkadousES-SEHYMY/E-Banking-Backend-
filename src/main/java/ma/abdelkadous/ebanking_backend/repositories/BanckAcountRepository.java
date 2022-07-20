package ma.abdelkadous.ebanking_backend.repositories;

import ma.abdelkadous.ebanking_backend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BanckAcountRepository extends JpaRepository<BankAccount,String> {
}
