package task3;

public class Miner {
    private final Blockchain blockchain = Blockchain.getInstance();
    private final String minerName;

    public Miner(String minerName) {
        this.minerName = minerName;
    }

    public String getMinerName() {
        return minerName;
    }

    public Blockchain miningBlockToBlockchain(Miner miner) {
        Block block = mineBlock(miner);
        blockchain.addBlockIfValid(block);
        return blockchain;
    }

    private Block mineBlock(Miner miner) {
        int newBlockId = blockchain.getBlocks().size() + 1;
        String currentBlockchainHashCode = blockchain.getBlockchainHashCode();
        int numberOfZeroes = blockchain.getNewNumberZero();
        return Block.createBlock(newBlockId, currentBlockchainHashCode, numberOfZeroes, miner);
    }

    @Override
    public String toString() {
        return minerName;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Miner)) {
            return false;
        }
        return this.minerName.equals(((Miner) object).getMinerName());
    }
}