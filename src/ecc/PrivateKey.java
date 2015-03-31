package ecc;

import java.math.BigInteger;

/**
 * The private key of the El Gamal Elliptic Curve Cryptography.
 * 
 * The key consists of:
 * c, the elliptic curve used in the calculations,
 * k is the private key, a randomly-generated integer, satisfying 1 <= k < p-1.
 * 
 * @author Ahmad Zaky
 */
public class PrivateKey {
    private EllipticCurve c;
    private BigInteger k;
    
    public PrivateKey(EllipticCurve c, BigInteger k) {
        this.c = c;
        this.k = k;
    }
    
    public void setCurve(EllipticCurve c) {
        this.c = c;
    }
    
    public EllipticCurve getCurve() {
        return c;
    }
    
    public void setKey(BigInteger k) {
        this.k = k;
    }
    
    public BigInteger getKey() {
        return k;
    }
    
    public ECPoint getBasePoint() {
        return c.getBasePoint();
    }
    
    /**
     * Save the current key to a *.pri file.
     * TODO: design the representation of the key inside a binary file
     * 
     * @param path
     */
    public void saveToFile(String path) {
        
    }
    
    /**
     * Load the current key from a *.pri file.
     * TODO: design the representation of the key inside a binary file
     * 
     * @param path
     */
    public void loadFromFile(String path) {
        
    }
}
