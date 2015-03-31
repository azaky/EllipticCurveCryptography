package ellipticcurve;

import java.math.BigInteger;

/**
 * This class will represent a point inside an elliptic curve.
 * 
 * @author Ahmad Zaky
 */
public class EllipticCurvePoint {
    public BigInteger x;
    public BigInteger y;
    private boolean pointOfInfinity;
    
    public EllipticCurvePoint() {
        this.x = this.y = BigInteger.ZERO;
        this.pointOfInfinity = false;
    }
    
    public EllipticCurvePoint(BigInteger x, BigInteger y) {
        this.x = x;
        this.y = y;
        this.pointOfInfinity = false;
    }
    
    public EllipticCurvePoint(long x, long y) {
        this.x = BigInteger.valueOf(x);
        this.y = BigInteger.valueOf(y);
        this.pointOfInfinity = false;
    }
    
    public EllipticCurvePoint(EllipticCurvePoint p) {
        this.x = p.x;
        this.y = p.y;
        this.pointOfInfinity = p.pointOfInfinity;
    }
    
    public boolean equals(EllipticCurvePoint point) {
        if (point == null) return false;
        
        if (this.pointOfInfinity == point.pointOfInfinity) return true;
        
        return (this.x.compareTo(point.x) | this.y.compareTo(point.y)) == 0;
    }
    
    public boolean isPointOfInfinity() {
        return pointOfInfinity;
    }
    
    public EllipticCurvePoint negate() {
        if (isPointOfInfinity()) {
            return INFINTIY;
        } else {
            return new EllipticCurvePoint(x, y.negate());
        }
    }
    
    private static EllipticCurvePoint infinity() {
        EllipticCurvePoint point = new EllipticCurvePoint();
        point.pointOfInfinity = true;
        return point;
    }
    
    public static final EllipticCurvePoint INFINTIY = infinity();
    
    @Override
    public String toString() {
        if (isPointOfInfinity()) {
            return "INFINITY";
        } else {
            return "(" + x.toString() + ", " + y.toString() + ")";
        }
    }
}
