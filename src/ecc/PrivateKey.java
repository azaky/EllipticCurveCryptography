package ecc;

import java.io.File;
import java.io.PrintStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * The private key of the El Gamal Elliptic Curve Cryptography.
 * 
 * The key consists of:
 * c, the elliptic curve used in the calculations,
 * k is the private key, a randomly-generated integer, satisfying 1 <= k < p-1.
 * 
 * @author Ahmad Zaky & Alif Raditya Rochman
 */
public class PrivateKey {
    private EllipticCurve c;
    private BigInteger k;
    
    public PrivateKey(EllipticCurve c, BigInteger k) {
        this.c = c;
        this.k = k;
    }
    
    public PrivateKey(String pathFile){
        try {
            List<String> lines = Files.readAllLines(Paths.get(pathFile), StandardCharsets.UTF_8);
            BigInteger a = new BigInteger(lines.get(0),16);
            BigInteger b = new BigInteger(lines.get(1),16);
            BigInteger p = new BigInteger(lines.get(2),16);
            BigInteger g1 = new BigInteger(lines.get(3),16);
            BigInteger g2 = new BigInteger(lines.get(4),16);
            BigInteger k = new BigInteger(lines.get(5),16);
            EllipticCurve eC = new EllipticCurve(a, b, p, new ECPoint(g1,g2));
            this.c = eC;
            this.k = k;
        } catch (Exception e){
            
        }
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
        BigInteger a = c.getA();
        BigInteger b = c.getB();
        BigInteger p = c.getP();
        BigInteger g1 = c.getBasePoint().x;
        BigInteger g2 = c.getBasePoint().y;
        BigInteger k = this.k;
        try {
            PrintStream ps = new PrintStream(new File(path));
            ps.println(a.toString(16));
            ps.println(b.toString(16));
            ps.println(p.toString(16));
            ps.println(g1.toString(16));
            ps.println(g2.toString(16));
            ps.println(k.toString(16));
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
