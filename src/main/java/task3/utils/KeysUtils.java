package task3.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class KeysUtils {

    private static PrivateKey privateKey;
    private static PublicKey publicKey;
    private static final String PATH_TO_PRIVATE_KEY = "KeyPair/privateKey";
    private static final String PATH_TO_PUBLIC_KEY = "KeyPair/publicKey";

    public static PrivateKey getPrivateKeyFromFile() throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException {
        byte[] keyBytes = Files.readAllBytes(Path.of(PATH_TO_PRIVATE_KEY));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    public static PublicKey getPublicKeyFromFile() throws Exception {
        byte[] keyBytes = Files.readAllBytes(Path.of(PATH_TO_PUBLIC_KEY));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    public static void writeToFile(File file, byte[] key) throws IOException {
        file.getParentFile().mkdirs();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(key);
        fos.flush();
        fos.close();
    }

    public static void createKeys() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(1024);
        KeyPair pair = keyGen.generateKeyPair();
        privateKey = pair.getPrivate();
        publicKey = pair.getPublic();
    }

    public static void initializeKeys() {
        File publicKeyFile = new File(PATH_TO_PUBLIC_KEY);
        File privateKeyFile = new File(PATH_TO_PRIVATE_KEY);

        if (!publicKeyFile.exists() || !privateKeyFile.exists()) {
            try {
                createKeys();
                writeToFile(publicKeyFile, publicKey.getEncoded());
                writeToFile(privateKeyFile, privateKey.getEncoded());
            } catch (IOException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }
}
