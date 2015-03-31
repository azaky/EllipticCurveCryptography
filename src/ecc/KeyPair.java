package ecc;

/**
 * The class will contain a pair of public key and private key.
 * 
 * @author Ahmad Zaky
 */
public class KeyPair {
    private PublicKey publicKey;
    private PrivateKey privateKey;
    
    public KeyPair(PublicKey publicKey, PrivateKey privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }
}
