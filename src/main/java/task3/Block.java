package task3;

import task3.utils.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Block implements Serializable {
    @Serial
    private static final long serialVersionUID = 7L;
    private static final int GET_AMOUNT = Blockchain.getAmount();
    private final long id;
    private final long timeStamp;
    private String previousHashBlock;
    private String hashBlock;
    private long magicNumber;
    private long generationTime;
    private String newN;
    private List<Transaction> blockTransactions = new ArrayList<>();
    private String getsInfo;
    private final Miner createdByMiner;

    private Block(long id, Miner miner) {
        this.id = id;
        this.timeStamp = new Date().getTime();
        this.createdByMiner = miner;
        setGetsInfo();
    }

    public void setGetsInfo() {
        getsInfo = String.format("%s gets %d VC", createdByMiner.getMinerName(), GET_AMOUNT);
    }

    public String getGetsInfo() {
        return getsInfo;
    }

    public void setNewN(String newN) {
        this.newN = newN;
    }

    public List<Transaction> getBlockTransactions() {
        return blockTransactions;
    }

    public void setBlockTransactions(List<Transaction> blockTransactions) {
        this.blockTransactions = blockTransactions;
    }

    public long getGenerationTime() {
        return generationTime;
    }

    public void setPreviousHashBlock(String previousHashBlock) {
        this.previousHashBlock = previousHashBlock;
    }

    public String getHashBlock() {
        return hashBlock;
    }

    public String getPreviousHashBlock() {
        return previousHashBlock;
    }

    public long getId() {
        return id;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setMagicNumber(long magicNumber) {
        this.magicNumber = magicNumber;
    }

    public void setHashBlock(String hashBlock) {
        this.hashBlock = hashBlock;
    }

    public void generateHashCodeWithPrefix(int numOfZeroes) {
        StringUtils.setSha256WithLeadingZeroes(numOfZeroes, this);
    }

    private String getBlockTransactionsToPrint() {
        if (blockTransactions.isEmpty()) {
            return "no data";
        } else {
            return blockTransactions.stream().map(blockData -> "\n" + blockData).collect(Collectors.joining());
        }
    }

    public static Block createBlock(long id, String hashBlock, int numberZero, Miner miner) {
        Block block = new Block(id, miner);
        block.setPreviousHashBlock(hashBlock);
        block.generateHashCodeWithPrefix(numberZero);
        block.generationTime = (new Date().getTime() - block.timeStamp);
        return block;
    }

    @Override
    public String toString() {
        return "Block: \n" +
                "Created by: " + createdByMiner.getMinerName() + "\n" +
                getsInfo + "\n" +
                "Id: " + id + "\n" +
                "Timestamp: " + timeStamp + "\n" +
                "Magic number: " + magicNumber + "\n" +
                "Hash of the previous block:\n" +
                previousHashBlock + "\n" +
                "Hash of the block:\n" +
                hashBlock + "\n" +
                "Block data: " +
                getBlockTransactionsToPrint() + "\n" +
                "Block was generating for " + generationTime/1000 + " seconds\n" +
                newN + "\n";
    }
}