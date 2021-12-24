package task3;

import java.util.concurrent.Callable;

public class MinerTask implements Callable<Blockchain> {
    private final Miner miner;

    public MinerTask(Miner miner) {
        this.miner = miner;
    }

    @Override
    public Blockchain call() {
        return miner.miningBlockToBlockchain(miner);
    }
}
