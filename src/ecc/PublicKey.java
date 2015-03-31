package ecc;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * The public key for El Gamal Elliptic Curve Cryptography.
 * 
 * The key consists of:
 * c, the elliptic curve used in the calculations,
 * P_K, the point obtained from k * G, where k is the corresponding private key
 * and G is the base point of c.
 * 
 * @author Ahmad Zaky
 */
public class PublicKey {
    private EllipticCurve c;
    private ECPoint P_K;

    public PublicKey(EllipticCurve c, ECPoint P_K) {
        this.c = c;
        this.P_K = P_K;
    }
    
    public PublicKey(String pathFile){
        try {
            List<String> lines = Files.readAllLines(Paths.get(pathFile), StandardCharsets.UTF_8);
            BigInteger a = new BigInteger(lines.get(0),16);
            BigInteger b = new BigInteger(lines.get(1),16);
            BigInteger p = new BigInteger(lines.get(2),16);
            BigInteger g1 = new BigInteger(lines.get(3),16);
            BigInteger g2 = new BigInteger(lines.get(4),16);
            BigInteger p_k1 = new BigInteger(lines.get(5),16);
            BigInteger p_k2 = new BigInteger(lines.get(6),16);
            EllipticCurve eC = new EllipticCurve(a, b, p, new ECPoint(g1,g2));
            ECPoint eCP = new ECPoint(p_k1,p_k2);
            this.c = eC;
            this.P_K = eCP;
        } catch (Exception e){
            
        } 
    }
    
    public EllipticCurve getCurve() {
        return c;
    }
    
    public void setCurve(EllipticCurve c) {
        this.c = c;
    }

    public ECPoint getKey() {
        return P_K;
    }

    public void setKey(ECPoint P_K) {
        this.P_K = P_K;
    }
    
    public ECPoint getBasePoint() {
        return c.getBasePoint();
    }
    
    /**
     * Save the current key to a *.pub file.
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
        BigInteger p_k1 = P_K.x;
        BigInteger p_k2 = P_K.y;
        try {
            PrintStream ps = new PrintStream(new File(path));
            ps.println(a.toString(16));
            ps.println(b.toString(16));
            ps.println(p.toString(16));
            ps.println(g1.toString(16));
            ps.println(g2.toString(16));
            ps.println(p_k1.toString(16));
            ps.println(p_k2.toString(16));
            ps.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
