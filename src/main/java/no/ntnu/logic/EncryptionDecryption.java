package no.ntnu.logic;


import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;


public class EncryptionDecryption {
    public static final String algorithm = "AES/CBC/PKCS5Padding";

    /**
     *  Test to see if the encryption and decryption work thourougly
     *
     *
   * public static void main(String[] args)
   *         throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException,
   *         NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
   *     String input = "2164512";
    *
    *    SecretKey key = EncryptionDecryption.getKeyFromPassword("idataproject", "password");
    *    IvParameterSpec ivP = EncryptionDecryption.generateIvp();
    *
    *    String algorithm = EncryptionDecryption.algorithm;
    *    String cipherText = EncryptionDecryption.encrypt(algorithm, input, key, ivP);
    *    String plainText = EncryptionDecryption.decrypt(algorithm, cipherText, key,
    *            ivP);
    *    System.out.println(cipherText);
    *    System.out.println(plainText);
     *}
    */

    /**
     * Takes a String and encrypts it using an byte array.
     * @param algorithm String value defined earlier
     * @param input gets bytes from defined input
     * @param key secretkey derived from PBKDF2
     * @param iv a pseudo random value
     * @return the encrypted String
     */
    public static String encrypt(String algorithm, String input, SecretKey key, IvParameterSpec iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    /**
     * decrypt the encrypted String back into readable format
     * @param algorithm String value defined earlier
     * @param cipherText encrypted text from encryption
     * @param key secretkey needed for decryption using symmetric key
     * @param iv a pseudo random value
     * @return the decrypted String
     */
    public static String decrypt(String algorithm, String cipherText, SecretKey key,
                                 IvParameterSpec iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
        return new String(plainText);
    }

    /**
     * Derives the AES secret key from a given password-based key derivation function (PBKDF2)
     * Uses secretfactory class with set algorithm for generating a key from a given password
     * @param password string input for password
     * @param salt turns the password into a secret key
     * @return the secret key
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static SecretKey getKeyFromPassword(String password, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");

        return secret;
    }

    /**
     * IV is a pseudo-random value with the same block size as the encrypted block
     * @return
     */
    public static IvParameterSpec generateIvp() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }
}
