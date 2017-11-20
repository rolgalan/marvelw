package me.rolgalan.marvelw.server;

import android.util.Pair;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Roldán Galán on 20/11/2017.
 */

class ServerUtils {

    static TimestampHash getTimestampHash() {

        String timestamp = String.valueOf(System.currentTimeMillis());
        String hash = generateHash(timestamp);
        return new TimestampHash(timestamp, hash);
    }

    private static String generateHash(String timestamp) {

        String privateKey = getPrivateKey();
        String publicKey = getPublicKey();
        return md5(timestamp + privateKey + publicKey);
    }

    private static String md5(final String s) {

        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2) {
                    h = "0" + h;
                }
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    static String getPublicKey() {

        return BuildConfig.PUBLIC_KEY;
    }

    private static String getPrivateKey() {

        return BuildConfig.PRIVATE_KEY;
    }

    final static class TimestampHash extends Pair<String, String> {

        /**
         * Constructor for a Pair.
         *
         * @param timestamp the first object in the Pair
         * @param hash      the second object in the pair
         */
        TimestampHash(String timestamp, String hash) {

            super(timestamp, hash);
        }

        String getTimestamp() {

            return first;
        }

        String getHash() {

            return second;
        }
    }
}
