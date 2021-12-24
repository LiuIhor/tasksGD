package task3;

import task3.utils.KeysUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final int NUMBER_BLOCKS = 15;
    private static final int NUMBER_MINERS = 5;

    public static int getNumberBlocks() {
        return NUMBER_BLOCKS;
    }

    public static void main(String[] args) {
        KeysUtils.initializeKeys();
        ExecutorService executor;

        MinersActions minersActions = new MinersActions(NUMBER_MINERS);
        Blockchain.getInstance().setMinersActions(minersActions);

        minersActions.initializeMinerTasks();
        List<MinerTask> minerTasks = minersActions.getMinerTasks();

        Thread transactionsExecutor = new TransactionExecutor();
        transactionsExecutor.start();

        while (Blockchain.getInstance().getBlocks().size() < NUMBER_BLOCKS) {
            try {
                executor = Executors.newFixedThreadPool(NUMBER_MINERS);
                executor.invokeAny(minerTasks);
                executor.shutdownNow();
                executor.awaitTermination(1, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            transactionsExecutor.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Blockchain.getInstance());

    }


}