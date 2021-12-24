package task3;

public class TransactionExecutor extends Thread {
    @Override
    public void run() {
        while (Blockchain.isReceivingTransactionsOn()) {
            Blockchain.getInstance().getTransactionToReceive();
        }
    }
}