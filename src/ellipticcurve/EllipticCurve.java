package ellipticcurve;

import java.math.BigInteger;

/**
 * This class represents Elliptic Curve in Galois Field G(p). The equation will
 * be in form  y^2 = x^3 + ax + b (mod p), for which a and b satisfy
 * 4a^3 + 27b^2 != 0 (mod p).
 * 
 * A point in the elliptic curve will be represented as a pair of BigInteger,
 * which is represented as EllipticCurvePoint class.
 * 
 * This class implements some very basic operations of Points in the elliptic
 * curve, which are addition, multiplication (scalar), and subtraction.
 * 
 * @author Ahmad Zaky
 */
public class EllipticCurve {
    
    private BigInteger a;
    private BigInteger b;
    private BigInteger p;
    
    // some BigInteger constants that might help us in some calculations
    private static BigInteger TWO = new BigInteger("2");
    private static BigInteger THREE = new BigInteger("3");
    
    public EllipticCurve(BigInteger a, BigInteger b, BigInteger p) {
        this.a = a;
        this.b = b;
        this.p = p;
    }
    
    public EllipticCurve(long a, long b, long p) {
        this.a = BigInteger.valueOf(a);
        this.b = BigInteger.valueOf(b);
        this.p = BigInteger.valueOf(p);
    }
    
    /**
     * This method will check whether a point belong to this curve or not.
     */
    public boolean isPointInsideCurve(EllipticCurvePoint point) {
        if (point.isPointOfInfinity()) return true;
        
        return point.x.multiply(point.x).mod(p).add(a).multiply(point.x).add(b)
                .mod(p).subtract(point.y.multiply(point.y)).mod(p)
                .compareTo(BigInteger.ZERO) == 0;
    }
    
    /**
     * Add two points. The result of this addition is the reflection of the
     * intersection of the line formed by the two points to the same curve with
     * respect to the x-axis. The line is the tangent when the two points equal.
     * 
     * The result will be point of infinity when the line is parallel to the
     * y-axis.
     * 
     * If one of them is point of infinity, then the other will be returned.
     * 
     * @param p1
     * @param p2
     * @return 
     */
    public EllipticCurvePoint add(EllipticCurvePoint p1, EllipticCurvePoint p2) {
        if (p1 == null || p2 == null) return null;
        
        if (p1.isPointOfInfinity()) {
            return new EllipticCurvePoint(p2);
        } else if (p2.isPointOfInfinity()) {
            return new EllipticCurvePoint(p1);
        }
        
        // The lambda (the slope of the line formed by the two points) are
        // different when the two points are the same.
        BigInteger lambda;
        if (p1.x.subtract(p2.x).mod(p).compareTo(BigInteger.ZERO) == 0) {
            if (p1.y.subtract(p2.y).mod(p).compareTo(BigInteger.ZERO) == 0) {
                // lambda = (3x1^2 + a) / (2y1)
                BigInteger nom = p1.x.multiply(p1.x).multiply(THREE).add(a);
                BigInteger den = p1.y.add(p1.y);
                lambda = nom.multiply(den.modInverse(p));
            } else {
                // lambda = infinity
                return EllipticCurvePoint.INFINTIY;
            }
        } else {
            // lambda = (y2 - y1) / (x2 - x1)
            BigInteger nom = p2.y.subtract(p1.y);
            BigInteger den = p2.x.subtract(p1.x);
            lambda = nom.multiply(den.modInverse(p));
        }
        
        // Now the easy part:
        // The result is (lambda^2 - x1 - y1, lambda(x2 - xr) - yp)
        BigInteger xr = lambda.multiply(lambda).subtract(p1.x).subtract(p2.x).mod(p);
        BigInteger yr = lambda.multiply(p1.x.subtract(xr)).subtract(p1.y).mod(p);
        return new EllipticCurvePoint(xr, yr);
    }
    
    /**
     * Subtract two points, according to this equation: p1 - p2 = p1 + (-p2),
     * where -p2 is the reflection of p2 with respect to the x-axis.
     * 
     * @param p1
     * @param p2
     * @return 
     */
    public EllipticCurvePoint subtract(EllipticCurvePoint p1, EllipticCurvePoint p2) {
        if (p1 == null || p2 == null) return null;
        
        return add(p1, p2.negate());
    }

    /**
     * Multiply p1 to a scalar n. That is, perform addition n times. The
     * following method implements divide and conquer approach.
     * 
     * @param p1
     * @param n
     * @return 
     */
    public EllipticCurvePoint multiply(EllipticCurvePoint p1, BigInteger n) {
        if (p1.isPointOfInfinity()) {
            return EllipticCurvePoint.INFINTIY;
        }
        
        EllipticCurvePoint result = EllipticCurvePoint.INFINTIY;
        int bitLength = n.bitLength();
        for (int i = bitLength - 1; i >= 0; --i) {
            result = add(result, result);
            if (n.testBit(i)) {
                result = add(result, p1);
            }
        }
        
        return result;
    }
    
    public EllipticCurvePoint multiply(EllipticCurvePoint p1, long n) {
        return multiply(p1, BigInteger.valueOf(n));
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // This computes (2, 4) + (5, 9) in y^2 = x^3 + x + 6 mod 11
        EllipticCurve e = new EllipticCurve(1, 6, 11);
        EllipticCurvePoint p = new EllipticCurvePoint(-9, 4);
        EllipticCurvePoint q = new EllipticCurvePoint(5, 9);
        
        System.out.println(p + " + " + q + " = " + e.add(p, q));
        for (int i = 0; i < 20; ++i) {
            System.out.println(p + " x " + i + " = " + e.multiply(p, i));
        }
    }
    
}
