package task3;

import task3.utils.SignatureUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Blockchain implements Serializable {
    @Serial
    private static final long serialVersionUID = 6L;
    private static final int NEW_NUMBER_ZERO = 0;
    private static final int MAX_NUMBER_BLOCKS = Main.getNumberBlocks();
    private static final int AMOUNT = 100;
    private static final long MIN_TIME = 20;
    private static final long MAX_TIME = 100;
    private static boolean receivingTransactionsOn = true;
    private static final TreeSet<Transaction> receivedTransactionsSet = new TreeSet<>();
    private MinersActions minersActions;
    private final ArrayList<Block> blocks = new ArrayList<>();
    private String blockchainHashCode = "0";
    private int newNumberZero;
    private Block currentLastBlock;
    private long currentLastTransactionID;
    private static final Blockchain blockchainInstance = new Blockchain(NEW_NUMBER_ZERO);

    public Blockchain(int newNumberZero) {
        this.newNumberZero = newNumberZero;
    }

    public void setMinersActions(MinersActions minersActions) {
        this.minersActions = minersActions;
    }

    public Block getCurrentLastBlock() {
        return currentLastBlock;
    }

    public int getNewNumberZero() {
        return newNumberZero;
    }

    public String getBlockchainHashCode() {
        return blockchainHashCode;
    }

    public synchronized ArrayList<Block> getBlocks() {
        return blocks;
    }

    public static synchronized Blockchain getInstance() {
        return blockchainInstance;
    }

    public static int getAmount() {
        return AMOUNT;
    }

    public static boolean isReceivingTransactionsOn() {
        return receivingTransactionsOn;
    }

    public void getTransactionToReceive() {
        int timeSleep = 20;

        while (receivingTransactionsOn) {
            try {
                Thread.sleep(timeSleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Transaction performedTransaction = minersActions.createTransaction();

            boolean isValidID = isTransactionIDValid(performedTransaction);

            if (!isValidID) {
                return;
            }

            boolean isSignatureValid = false;

            try {
                isSignatureValid = SignatureUtils
                        .verifySignature(performedTransaction.getTransactionStringAsByteArr(), performedTransaction.getSignature());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!isSignatureValid) {
                System.out.println("Signature is not valid, block is rejected");
                return;
            }

            performedTransaction.setTransactionTime(LocalDateTime.now());

            synchronized (Blockchain.class) {
                receivedTransactionsSet.add(performedTransaction);
            }
        }
    }

    public synchronized void addBlockIfValid(Block block) {
        if (validateHashcodeOfNewBlock(block)
                && validateDifficultyOfNewBlock(block)
                && validateBlockchainMaxLength()
                && validateNewBlockID(block)) {
            synchronized (this) {
                blockchainHashCode = block.getHashBlock();
                blocks.add(block);
                saveTransactionsToBlockData(block);
                newStatusNumberZero(block);
                setCurrentLastTransactionID();
                currentLastBlock = block;
            }
        } else if (!validateBlockchainMaxLength()) {
            receivingTransactionsOn = false;
        }
    }

    private void setCurrentLastTransactionID() {
        if (blocks.size() < 2) {
            currentLastTransactionID = 0;
        }

        currentLastTransactionID = blocks.stream()
                .map(Block::getBlockTransactions)
                .filter(set -> !set.isEmpty())
                .flatMap(List::stream)
                .map(Transaction::getTransactionID)
                .max(Long::compare)
                .orElse(0L);
    }

    private synchronized boolean validateHashcodeOfNewBlock(Block block) {
        return blockchainHashCode.equals(block.getPreviousHashBlock());
    }

    private synchronized boolean validateDifficultyOfNewBlock(Block block) {
        for (int i = 0; i < newNumberZero; i++) {
            if (block.getHashBlock().charAt(i) != '0') {
                return false;
            }
        }
        return block.getHashBlock().charAt(newNumberZero + 1) != '0';
    }

    private synchronized boolean validateBlockchainMaxLength() {
        return this.blocks.size() + 1 <= MAX_NUMBER_BLOCKS;
    }

    private synchronized boolean validateNewBlockID(Block block) {
        if (blocks.isEmpty()) {
            return block.getId() == 1;
        } else {
            return block.getId() == currentLastBlock.getId() + 1;
        }
    }

    private synchronized void newStatusNumberZero(Block block) {
        if (block.getGenerationTime() < MIN_TIME) {
            newNumberZero++;
            block.setNewN(String.format("N was increased to %d", newNumberZero));
        } else if (block.getGenerationTime() > MAX_TIME) {
            if (newNumberZero > 0) {
                newNumberZero--;
            }

            block.setNewN("N was decreased by 1");
        } else {
            block.setNewN("N stays the same");
        }
    }

    private void saveTransactionsToBlockData(Block block) {
        if (block.getId() != 1) {
            block.setBlockTransactions(geTransactionsToSave());
        }
    }

    private List<Transaction> geTransactionsToSave() {
        List<Transaction> transactionsToSave = receivedTransactionsSet.stream().filter(tr -> tr.getTransactionTime().compareTo(LocalDateTime.now()) < 0).collect(Collectors.toCollection(ArrayList::new));
        transactionsToSave.forEach(receivedTransactionsSet::remove);
        return transactionsToSave;
    }

    private boolean isTransactionIDValid(Transaction transaction) {
        return transaction.getTransactionID() > currentLastTransactionID;
    }

    @Override
    public String toString() {
        return blocks.stream()
                .map(Block::toString)
                .collect(Collectors.joining("\n"));
    }
}