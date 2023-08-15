package deadlock;

public class Transfer implements Runnable {
    Account accDonor;
    Account accRecipient;
    int sum;

    public Transfer(Account accDonor, Account accRecipient, int sum) {
        this.accDonor = accDonor;
        this.accRecipient = accRecipient;
        this.sum = sum;
    }

    @Override
    public void run() {
        Account firstLock, secondLock;

        if (accDonor.hashCode() < accRecipient.hashCode()) {
            firstLock = accDonor;
            secondLock = accRecipient;
        } else {
            firstLock = accRecipient;
            secondLock = accDonor;
        }
        synchronized (firstLock) {
            synchronized (secondLock) {
                transferMoney(accDonor, accRecipient, sum);
            }
        }
    }

    private void transferMoney(Account accFrom, Account accTo, int sum) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (accFrom.getBalance() >= sum) {
            accFrom.debit(sum);
            accTo.credit(sum);
        }
    }
}
