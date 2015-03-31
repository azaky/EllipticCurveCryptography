package ecc;

/**
 * This class implements El Gamal Public-Key Cryptography using Elliptic Curve.
 * 
 * This class also implements key-pair generation.
 * 
 * @author Ahmad Zaky
 */
public class ECC {
    /**
     * The main encryption function of ECC.
     * 
     * @param plainText
     * @param key
     * @return 
     */
    public static byte[] encrypt(byte[] plainText, PublicKey key) {
        // TODO: chunk the plainText into blocks.
        
        // TODO: encode each block into unique point.
        
        // TODO: encrypt each encoded point into a pair of points:
        // [C_1, C_2] = s[kG, P_m + kP_G], where:
        // k is a randomly generated integer such that 1 <= k < p-1,
        // G is the base point (provided in the key),
        // P_m is the encoded point from the plain text,
        // P_G is the point provided in the public key.
        
        // TODO: represent the ciphertext as an array of bytes
        
        return null;
    }
    
    /**
     * The main decryption function of ECC.
     * 
     * @param cipherText
     * @param key
     * @return 
     */
    public static byte[] decrypt(byte[] cipherText, PrivateKey key) {
        // TODO: chunk the cipherText into blocks.
        
        // TODO: calculate the encoded point
        // P_m = C_2 - kC_1, where:
        // [C_1, C_2] is the ciphertext,
        // k is the private key.
        
        // TODO: decode the encoded point
        
        return null;
    }
    
    /**
     * Generate a random key-pair, given the elliptic curve being used.
     * 
     * @param c
     * @return
     */
    public static KeyPair generateKeyPair(EllipticCurve c) {
        return null;
    }
    
    /**
     * Return the encoded point from a block of byte.
     * 
     * @param block
     * @param c
     * @return 
     */
    private static ECPoint encode(byte[] block, EllipticCurve c) {
        return null;
    }
    
    /**
     * Return the encoded block from a point.
     * 
     * @param point
     * @param c
     * @return 
     */
    private static byte[] decode(ECPoint point, EllipticCurve c) {
        return null;
    }
}
