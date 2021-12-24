package task3.utils;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

public class SignatureUtils {
    public static byte[] sign(String data) throws NoSuchAlgorithmException,
            IOException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        Signature rsa = Signature.getInstance("SHA1withRSA");
        rsa.initSign(KeysUtils.getPrivateKeyFromFile());
        rsa.update(data.getBytes());
        return rsa.sign();
    }

    public static boolean verifySignature(byte[] data, byte[] signature) throws Exception {
        Signature sig = Signature.getInstance("SHA1withRSA");
        sig.initVerify(KeysUtils.getPublicKeyFromFile());
        sig.update(data);
        return sig.verify(signature);
    }
}