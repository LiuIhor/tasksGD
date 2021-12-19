package task3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MinersActions {
    private static final Random random = new Random();
    private final List<Miner> miners = new ArrayList<>();
    private List<MinerTask> minerTasks = new ArrayList<>();
    public static long transactionNr = 0;

    public MinersActions(int numberOfMiners) {
        String minerName;
        for (int i = 0; i < numberOfMiners; i++) {
            minerName = "miner" + i;
            miners.add(new Miner(minerName));
        }
    }

    public List<MinerTask> getMinerTasks() {
        return minerTasks;
    }

    public void initializeMinerTasks() {
        minerTasks = miners.stream().map(MinerTask::new).collect(Collectors.toList());
    }

    public Transaction createTransaction() {
        int randomMinerIndex = random.nextInt(miners.size());
        Transaction transaction = Transaction.generateRandomTransaction(miners.get(randomMinerIndex), miners);
        transactionNr++;
        transaction.setTransactionID(transactionNr);
        return transaction;
    }
}