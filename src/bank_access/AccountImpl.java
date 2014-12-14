package bank_access;

public class AccountImpl extends AccountImplBase {
	private double account;
	
	public AccountImpl() {
		account = 0;
	}
	
	@Override
	public void transfer(double amount) throws OverdraftException {
		if((account + amount) < 0) {
			throw new OverdraftException("ERROR, not enough money in account!: " + account);
		} else {
			account +=amount;
		}
	}

	@Override
	public double getBalance() {
		return account;
	}

}
