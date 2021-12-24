package task3;

import task3.utils.SignatureUtils;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class Transaction implements Comparable<Transaction> {
    private static final String[] SHOPS = {"CITRUS", "CACTUS", "APPLE MANIA", "COMFY"};
    private static final int MAX_NUMBER_TRANSACTION = 100;
    private static final Random random = new Random();

    private Miner sender = null;
    private String receiverName;

    private int transactionAmount = 0;
    private long transactionID = 0;
    private LocalDateTime transactionTime = null;
    private byte[] signature = new byte[0];

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public void setSender(Miner sender) {
        this.sender = sender;
    }

    public void setSignature() {
        try {
            this.signature = SignatureUtils.sign(this.toString());
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException
                | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
    }

    public void setTransactionAmount(int transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public long getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(long transactionID) {
        this.transactionID = transactionID;
    }

    public byte[] getSignature() {
        return signature;
    }

    public byte[] getTransactionStringAsByteArr() {
        return this.toString().getBytes();
    }

    public void setTransactionTime(LocalDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    public static Transaction generateRandomTransaction(Miner miner, List<Miner> activeMiners) {
        Transaction transaction = new Transaction();

        transaction.setSender(miner);

        int transactionAmount = random.nextInt(MAX_NUMBER_TRANSACTION - 1) + 1;
        transaction.setTransactionAmount(transactionAmount);

        boolean isExternal = random.nextBoolean();

        int receiverIndex;
        if (isExternal) {
            receiverIndex = random.nextInt(SHOPS.length);
            transaction.setReceiverName(SHOPS[receiverIndex]);
        } else {
            receiverIndex = random.nextInt(activeMiners.size());
            Miner receiver = activeMiners.get(receiverIndex);
            transaction.setReceiverName(receiver.getMinerName());
        }

        transaction.setSignature();
        return transaction;
    }

    @Override
    public String toString() {
        return String.format("%s sent %d VC to %s", sender.getMinerName(), transactionAmount, receiverName);
    }

    @Override
    public int compareTo(Transaction otherTransaction) {
        return Long.compare(this.transactionID, otherTransaction.getTransactionID());
    }
}
