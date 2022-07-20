package ma.abdelkadous.ebanking_backend.exceptions;

//Exception métier
public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException(String message ) {
        super(message);
    }
}
