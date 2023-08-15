package deadlock;

import java.util.concurrent.Semaphore;

public class Transfer implements Runnable {
    Account accDonor;
    Account accRecipient;
    int sum;
    Semaphore semaphore;

    public Transfer(Account accDonor, Account accRecipient, int sum, Semaphore semaphore) {
        this.accDonor = accDonor;
        this.accRecipient = accRecipient;
        this.sum = sum;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {

        transferMoney(accDonor, accRecipient, sum);

    }

    private void transferMoney(Account accFrom, Account accTo, int sum) {
        try {
            semaphore.acquire();
            synchronized (accFrom) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (accTo) {
                    if (accFrom.getBalance() >= sum) {
                        accFrom.debit(sum);
                        accTo.credit(sum);
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
}