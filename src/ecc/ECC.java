package ecc;

import java.math.BigInteger;
import java.util.Random;

/**
 * This class implements El Gamal Public-Key Cryptography using Elliptic Curve.
 * 
 * This class also implements key-pair generation.
 * 
 * @author Ahmad Zaky
 */
public class ECC {
    public static final long AUXILIARY_CONSTANT_LONG = 1000;
    public static final BigInteger AUXILIARY_CONSTANT = BigInteger.valueOf(AUXILIARY_CONSTANT_LONG);
    
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
    public static KeyPair generateKeyPair(EllipticCurve c, Random rnd) throws Exception {
        // Randomly select the private key, such that it is relatively
        // prime to p
        BigInteger p = c.getP();
        BigInteger privateKey;
        do {
            privateKey = new BigInteger(p.bitLength(), rnd);
        } while (privateKey.mod(p).compareTo(BigInteger.ZERO) == 0);
        
        // Calculate the public key, k * g.
        // First, randomly generate g if it is not present in the curve.
        ECPoint g = c.getBasePoint();
        if (g == null) {
            // Randomly generate g using Koblits method.
            // The starting value of x should be random.
            BigInteger x = new BigInteger(p.bitLength(), rnd);
            g = koblitzProbabilistic(c, x);
            c.setBasePoint(g);
        }
        ECPoint publicKey = c.multiply(g, privateKey);
        
        return new KeyPair(
                new PublicKey(c, publicKey),
                new PrivateKey(c, privateKey)
        );
    }
    
    /**
     * Return the encoded point from a block of byte.
     * 
     * @param block
     * @param c
     * @return 
     */
    private static ECPoint encode(byte[] block, EllipticCurve c) throws Exception {
        return koblitzProbabilistic(c, new BigInteger(block));
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
    
    /**
     * Find a point inside the curve with the x-coordinate equals
     * x * AUXILIARY_CONSTANT + k, where k is as small as possible.
     * 
     * There is a very small probability that k will be as large as the
     * AUXILIARY_CONSTANT, and this method relies on the fact. If k exceeds
     * the constant, an exception will be thrown.
     * 
     * This method works only for p = 3 (mod 4), as finding the solution to
     * the quadratic congruence is non-deterministic for p = 1 (mod 4). If
     * p equals 1 (mod 4), an exception will also be thrown.
     * 
     * @param c
     * @param x
     * @return 
     */
    private static ECPoint koblitzProbabilistic(EllipticCurve c, BigInteger x) throws Exception {
        BigInteger p = c.getP();
        
        // throw an exception if p != 3 (mod 4)
        if (!p.testBit(0) || !p.testBit(1)) {
            throw new Exception("P should be 3 (mod 4)");
        }
        BigInteger pMinusOnePerTwo = p.subtract(BigInteger.ONE).shiftRight(1);
        
        BigInteger tempX = x.multiply(AUXILIARY_CONSTANT);
        for (long k = 0; k < AUXILIARY_CONSTANT_LONG; ++k) {
            BigInteger newX = tempX.add(BigInteger.valueOf(k));
            
            // Calculates the rhs of the elliptic curve equation, call it a
            BigInteger a = c.calculateRhs(newX);
            
            // Determine whether this value is a quadratic residue modulo p
            // It is if and only if a ^ ((p - 1) / 2) = 1 (mod p)
            if (a.modPow(pMinusOnePerTwo, p).mod(p).compareTo(BigInteger.ONE) == 0) {
                // We found it! Now, the solution is y = a ^ ((p + 1) / 4)
                BigInteger y = a.modPow(p.add(BigInteger.ONE).shiftRight(2), p);
                return new ECPoint(newX, y);
            }
        }
        
        // If we reach this point, then no point are found within the limit.
        throw new Exception("No Point found within the auxiliary constant");
    }
    
    public static void main(String[] args) {
        // using NIST_P_192 to test
        EllipticCurve c = EllipticCurve.NIST_P_192;
        byte[] test = new byte[1];
        
        for (int i = 0; i < 256; ++i) {
            test[0] = (byte)i;
            
            try {
                ECPoint point = encode(test, c);
                System.out.println("test " + i + " = " + point.toString(16) + " " + c.isPointInsideCurve(point));
            } catch (Exception ex) {
                System.out.println("test " + i + " failed");
            }
        }
    }
}
