/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package advancedprogramming;

import static advancedprogramming.Encryption.GenerateKey;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;

/**
 *
 * @author barnabe
 */
public class Encryption {

    static KeyPairGenerator GenerateKey;
    static byte[] KeyByte;

    static KeyPair GenerateKey1() throws Exception {

        GenerateKey = KeyPairGenerator.getInstance("RSA");

        //generating keypair
        KeyPair pair = GenerateKey.generateKeyPair();

        //selecting the keys from the keys pair
        PrivateKey priv = pair.getPrivate();
        PublicKey pub = pair.getPublic();

        //System.out.println("Private key: "+priv+"\n");
        //System.out.println("Public key: "+pub+"\n");
        String PairString = pair.toString();
        byte[] PairInByte = PairString.getBytes();
        KeyByte = PairInByte;

        PrintWriter MkFile0 = new PrintWriter("PairInByte.txt", "UTF-8");

        FileOutputStream Write0 = new FileOutputStream("PuK.txt");
        Write0.write(PairInByte);
        Write0.close();

        MkFile0.close();

        String PK = pub.toString();

        return pair;
    }

    //PRIVATE KEY
    static PrivateKey PrK(KeyPair A) throws IOException {
        PrivateKey B = A.getPrivate();

        PrintWriter MkFile1 = new PrintWriter("PrK.txt", "UTF-8");

        byte[] PrKB = B.getEncoded();

        FileOutputStream Write1 = new FileOutputStream("PrK.txt");
        Write1.write(PrKB);
        Write1.close();

        MkFile1.close();

        return B;
    }

    //PUBLIC KEY
    static PublicKey PuK(KeyPair A) throws FileNotFoundException, IOException {
        PublicKey B = A.getPublic();

        PrintWriter MkFile2 = new PrintWriter("PuK.txt", "UTF-8");

        byte[] PuKB = B.getEncoded();

        FileOutputStream Write2 = new FileOutputStream("PuK.txt");
        Write2.write(PuKB);
        Write2.close();

        MkFile2.close();

        return B;
    }

    static byte[] EncryptionPart(String message, PublicKey a) throws Exception {

        Cipher cipher1 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher1.init(Cipher.PUBLIC_KEY, a);

        // encrypt a string and store it into a byte array
        //if you want to return bytep[]
        byte[] encryptedText = cipher1.doFinal(message.getBytes());
        //System.out.println("encrypted array: "+Arrays.toString(encryptedText));

        //If you want to return String 
        String encryptedString = ByteToStr(encryptedText);
        //System.out.println("encrypted String: "+Arrays.toString(encryptedText));

        return encryptedText;
    }

    static String DecryptionPart(byte[] message, PrivateKey b) throws Exception {
        Cipher cipher2 = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher2.init(Cipher.PRIVATE_KEY, b);

        //byte[] try1 = message.getBytes();
        //System.out.println("encrypted text in decryption: "+Arrays.toString(message));
        //System.out.println("encrypted text in decryption: "+message);
        //text decrypted in byte array
        byte[] decryptedText = cipher2.doFinal(message);
        //System.out.println("decrypted array: "+Arrays.toString(decryptedText));

        String textfinal = new String(decryptedText); //text in string decrypted
        //System.out.println("final message " + textfinal);

        return textfinal;

    }

    static String Signature(String message) throws NoSuchAlgorithmException {
        String Tmessage = message;

        MessageDigest HashTmessage = MessageDigest.getInstance("SHA-256");
        byte[] HashSHA2 = HashTmessage.digest(Tmessage.getBytes(StandardCharsets.UTF_8));

        //System.out.println("hash-byte[]: "+Arrays.toString(HashSHA2));
        int lengthHash = HashSHA2.length;

        String HashStr = "";

        /* loop that turn the byte array into hex and store it as a string
        to be used as comparaison to check if the message have not been taempered
        with */
        for (int i = 0; i < lengthHash; i++) {
            String hex = Integer.toHexString(0xff & HashSHA2[i]);
            HashStr = HashStr + hex;
        }

        /* to put it in string i had no choice but to  put it in hexadecimal*/
        //System.out.println("HashStr: "+HashStr);
        return HashStr;
    }

    private static byte[] StrToByte(String message) {
        byte[] messageByte = message.getBytes();

        return messageByte;
    }

    private static String ByteToStr(byte[] message) {
        String messageByte = new String(message);

        return messageByte;
    }

    public static void main(String[] args) throws Exception {
        String text = "Sentence to encrypt and create signature of";

        KeyPair A = GenerateKey1();//method to generate key

        //separation of keys
        PrivateKey PrivaKey = Encryption.PrK(A);
        PublicKey PubliKey = Encryption.PuK(A);

        //creation of encrypted text stored in byte array
        byte[] textEnc = Encryption.EncryptionPart(text, PubliKey);

        //byte[] textEncByte = StrToByte(textEnc);
        // decryption of textEnc
        Encryption.DecryptionPart(textEnc, PrivaKey);

        // signature of the message to be sent and compared with
        Encryption.Signature(text);

    }

}
