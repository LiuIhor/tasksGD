package task3.utils;

import task3.Block;
import task3.Blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Random;

public class StringUtils {
    public static String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte elem : hash) {
                String hex = Integer.toHexString(0xff & elem);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setSha256WithLeadingZeroes(int numOfZeroes, Block block) {
        String hashCode;
        StringBuilder zeroes = new StringBuilder();
        zeroes.append("0".repeat(Math.max(0, numOfZeroes)));
        long magicNumber;
        Random random = new Random();
        String startOfHashcode;

        do {
            Block currentLastBlock = Blockchain.getInstance().getCurrentLastBlock();

            if (currentLastBlock != null && block.getId() <= currentLastBlock.getId()) {
                return;
            }

            magicNumber = random.nextLong(Integer.MAX_VALUE);
            hashCode = applySha256(block.getPreviousHashBlock()
                    + block.getId()
                    + block.getTimeStamp()
                    + block.getGetsInfo()
                    + magicNumber);
            startOfHashcode = hashCode.substring(0, numOfZeroes);
        } while (!zeroes.toString().equals(startOfHashcode));

        block.setMagicNumber(magicNumber);
        block.setHashBlock(hashCode);
    }
}
