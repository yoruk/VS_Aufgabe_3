package bank_access;

public class AccountImpl extends AccountImplBase {
	private double account;
	
	public AccountImpl() {
		account = 0;
	}
	
	@Override
	public void transfer(double amount) throws OverdraftException {
		account +=amount;
		
		if(account < 0) {
			throw new OverdraftException("Kontostand im negativen Bereich: " + account);
		}
	}

	@Override
	public double getBalance() {
		return account;
	}

}
