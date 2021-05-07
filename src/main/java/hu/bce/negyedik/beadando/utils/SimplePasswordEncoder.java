package hu.bce.negyedik.beadando.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SimplePasswordEncoder {
    public String encodePassword(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        String encodedString = Base64.getEncoder().encodeToString(encodedHash);
        return encodedString;
    }
}
