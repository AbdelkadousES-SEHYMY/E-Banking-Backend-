package ma.abdelkadous.ebanking_backend.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import ma.abdelkadous.ebanking_backend.dtos.AccountHistoryDTO;
import ma.abdelkadous.ebanking_backend.dtos.AccountOperationDTO;
import ma.abdelkadous.ebanking_backend.dtos.BankAccountDTO;
import ma.abdelkadous.ebanking_backend.exceptions.BankAccountNotFoundException;
import ma.abdelkadous.ebanking_backend.services.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@AllArgsConstructor
@RequestMapping("/accounts")

public class BankAccountRestController {
   private BankAccountService bankAccountService;
   @GetMapping("{accountId}")
   public BankAccountDTO getBankAccountDTO(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);
   }
   @GetMapping
    public List<BankAccountDTO> bankAccountList(){
      return bankAccountService.bankAccountList();
   }
   @GetMapping("{accountId}/operations")
   public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
       return bankAccountService.accountHistory(accountId);
   }
    @GetMapping("{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(
            @PathVariable String accountId,
            @RequestParam(name = "page",defaultValue ="0" ) int page,
            @RequestParam(name = "page",defaultValue ="5" ) int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId,page,size);
    }


}
