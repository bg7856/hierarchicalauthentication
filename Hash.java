package com.company;

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    public static String getSHA(int input) throws NoSuchAlgorithmException
    {
// Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

// digest() method called
// to calculate message digest of an input
// and return array of byte
        String out = Integer.toString(input);
        return DatatypeConverter.printHexBinary
                (md.digest(out.getBytes(StandardCharsets.UTF_8)));
    }
}
