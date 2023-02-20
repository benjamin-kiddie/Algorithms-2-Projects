/**
 * HeftyInteger for CS1501 Project 5
 * @author	Dr. Farnan, Benjamin Kiddie
 */
package project_5;

public class HeftyInteger {

	// constants used by various methods
	private final byte[] ONE = {(byte) 1};
	private final byte[] TWO = {(byte) 2};
	private final byte[] ZERO = {(byte) 0};

	private byte[] val;

	/**
	 * Construct the HeftyInteger from a given byte array
	 * @param b the byte array that this HeftyInteger should represent
	 */
	public HeftyInteger(byte[] b) {
		val = b;
	}

	/**
	 * Return this HeftyInteger's val
	 * @return val
	 */
	public byte[] getVal() {
		return val;
	}

	/**
	 * Return the number of bytes in val
	 * @return length of the val byte array
	 */
	public int length() {
		return val.length;
	}

	/**
	 * Return whether or no this integer is equal to another
	 * @param other	HeftyInteger to compare this to
	 * @return true if this and other are equal, false otherwise
	 */
	private boolean equals(HeftyInteger other) {
		return (this.compareTo(other) == 0);
	}

	/**
	 * Compare this and other and determine if other is equal to, greater than, 
	 * or less than this.
	 * @param other	HeftyInteger to compare this to
	 * @return 1 if greater than, -1 if less than, 0 if equal to
	 */
	public int compareTo(HeftyInteger other) {
		// base case: one number is positive and the other is negative
		if (this.isNegative() && !other.isNegative())
			return 1;
		else if (!this.isNegative() && other.isNegative())
			return -1;

		// otherwise, compute the difference and use to to determine which is larger
		HeftyInteger diff = this.subtract(other);
		if (diff.isNegative())
			return -1;
		else if (!diff.isNegative())
			return 1;
		else
			return 0;
	}

	/**
	 * Determine if this is 0
	 * @return True if this is 0, false if otherwise
	 */
	private boolean isZero() {
		for (byte b : val) 
			if (b != 0) return false;
		return true;
	}

	/**
	 * If this is negative, most significant bit will be 1 meaning most
	 * significant byte will be a negative signed number
	 * @return true if this is negative, false if positive
	 */
	public boolean isNegative() {
		return (val[0] < 0);
	}

	/**
	 * Add a new byte as the most significant in this
	 * @param extension the byte to place as most significant
	 */
	public void extend(byte extension) {
		byte[] newv = new byte[val.length + 1];
		newv[0] = extension;
		for (int i = 0; i < val.length; i++) {
			newv[i + 1] = val[i];
		}
		val = newv;
	}

	/** 
	 * Zero extend this integer to a given length
	 * @param	newSize Size to be extended to
	 */
	private void zeroExtend(int newSize) {
		// create a new byte array of the requested size
		byte[] extended = new byte[newSize];
		int oldSize = val.length;
		// copy bytes from old array over
		for (int i = 1; i <= oldSize; i++)
			extended[newSize - i] = val[oldSize - i];
		// return a new HeftyInteger with this as its value
		val = extended;
	}

	/**
	 * Truncate leading 0s or 1s if they are unnecessary
	 * @param	isNeg Whether or not this integer is negative
	 */
	private HeftyInteger truncate() {
		// create our new array of bytes
		byte[] newVal;

		// if this number is negative, follow the following algorithm
		if (this.isNegative()) {
			// start at the beginning
			int i = 0;
			// find the first byte where -1s are necessary, if at all
			while (i < val.length - 1) {
				if (val[i] != -1 || (i < (val.length - 1) && val[i + 1] >= 0))
					break;
				i++;
			}
			// make our new byte array reflect this byte and beyond
			newVal = new byte[val.length - i];
			for (int j = 0; j < newVal.length; j++)
				newVal[j] = val[i + j];
		}
		// otherwise, follow a different algorithm
		else {
			// start at the beginning
			int i = 0;
			// find the first point where 0s are necessary, if at all
			while (i < val.length) {
				if (val[i] != 0 || (i < (val.length - 1) && val[i + 1] < 0))
					break;
				i++;
			}
			// make our new byte array reflect this byte and beyond
			if (i == val.length)
				return new HeftyInteger(ZERO);
			newVal = new byte[val.length - i];
			for (int j = 0; j < newVal.length; j++)
				newVal[j] = val[i + j];
		}
		
		// reassign value to our new array
		return new HeftyInteger(newVal);
	}

	/**
	 * Computes the sum of this and other
	 * @param other the other HeftyInteger to sum with this
	 */
	public HeftyInteger add(HeftyInteger other) {
		byte[] a, b;
		// If operands are of different sizes, put larger first ...
		if (val.length < other.length()) {
			a = other.getVal();
			b = val;
		}
		else {
			a = val;
			b = other.getVal();
		}

		// ... and normalize size for convenience
		if (b.length < a.length) {
			int diff = a.length - b.length;

			byte pad = (byte) 0;
			if (b[0] < 0) {
				pad = (byte) 0xFF;
			}

			byte[] newb = new byte[a.length];
			for (int i = 0; i < diff; i++) {
				newb[i] = pad;
			}

			for (int i = 0; i < b.length; i++) {
				newb[i + diff] = b[i];
			}

			b = newb;
		}

		// Actually compute the add
		int carry = 0;
		byte[] res = new byte[a.length];
		for (int i = a.length - 1; i >= 0; i--) {
			// Be sure to bitmask so that cast of negative bytes does not
			//  introduce spurious 1 bits into result of cast
			carry = ((int) a[i] & 0xFF) + ((int) b[i] & 0xFF) + carry;

			// Assign to next byte
			res[i] = (byte) (carry & 0xFF);

			// Carry remainder over to next byte (always want to shift in 0s)
			carry = carry >>> 8;
		}

		HeftyInteger res_li = new HeftyInteger(res);
	
		// If both operands are positive, magnitude could increase as a result
		//  of addition
		if (!this.isNegative() && !other.isNegative()) {
			// If we have either a leftover carry value or we used the last
			//  bit in the most significant byte, we need to extend the result
			if (res_li.isNegative()) {
				res_li.extend((byte) carry);
			}
		}
		// Magnitude could also increase if both operands are negative
		else if (this.isNegative() && other.isNegative()) {
			if (!res_li.isNegative()) {
				res_li.extend((byte) 0xFF);
			}
		}

		// Note that result will always be the same size as biggest input
		//  (e.g., -127 + 128 will use 2 bytes to store the result value 1)
		return res_li;
	}

	/**
	 * Negate val using two's complement representation
	 * @return negation of this
	 */
	public HeftyInteger negate() {
		byte[] neg = new byte[val.length];
		int offset = 0;

		// Check to ensure we can represent negation in same length
		//  (e.g., -128 can be represented in 8 bits using two's
		//  complement, +128 requires 9)
		if (val[0] == (byte) 0x80) { // 0x80 is 10000000
			boolean needs_ex = true;
			for (int i = 1; i < val.length; i++) {
				if (val[i] != (byte) 0) {
					needs_ex = false;
					break;
				}
			}
			// if first byte is 0x80 and all others are 0, must extend
			if (needs_ex) {
				neg = new byte[val.length + 1];
				neg[0] = (byte) 0;
				offset = 1;
			}
		}

		// flip all bits
		for (int i  = 0; i < val.length; i++) {
			neg[i + offset] = (byte) ~val[i];
		}

		HeftyInteger neg_li = new HeftyInteger(neg);

		// add 1 to complete two's complement negation
		return neg_li.add(new HeftyInteger(ONE));
	}

	/**
	 * Implement subtraction as simply negation and addition
	 * @param other HeftyInteger to subtract from this
	 * @return difference of this and other
	 */
	public HeftyInteger subtract(HeftyInteger other) {
		return this.add(other.negate());
	}

	/**
	 * Compute the product of this and other
	 * @param other HeftyInteger to multiply by this
	 * @return product of this and other
	 */
	public HeftyInteger multiply(HeftyInteger other) {
		// base case: one of the inputs is zero
		if (this.getVal() == ZERO || other.getVal() == ZERO)
			return new HeftyInteger(ZERO);
		
		// arbitrate our inputs
		HeftyInteger a = this;
		HeftyInteger b = other;

		// if our result will be negative, denote this
		boolean resNeg = (a.isNegative() ^ b.isNegative());
		// make both inputs positive
		if (a.isNegative())
			a = a.negate();
		if (b.isNegative())
			b = b.negate();
		
		// if our inputs are sufficiently small, use grade school
		// zero extend both x and y to be the size of our result
		int resultSize = a.length() + b.length();
		a.zeroExtend(resultSize);
		b.zeroExtend(resultSize);
		// call grade school
		HeftyInteger result = a.gradeSchool(b, resultSize).truncate();

		// if our result is supposed to be negative, negate it. otherwise, return it as is
		return (resNeg ? result.negate() : result);
	}

	/**
	 * Helper method for multiply(). Runs the grade school multiplication
	 * algorithm
	 * @param x HeftyInteger being multiplied
	 * @param y HeftyInteger being multiplied
	 * @return product of x and y
	 */
	private HeftyInteger gradeSchool(HeftyInteger b, int resultSize) {
		// define our smaller and larger number
		HeftyInteger a = this;
		HeftyInteger x;
		HeftyInteger y;
		if (a.length() > b.length()) {
			x = a;
			y = b;
		}
		else {
			x = b;
			y = a;
		}

		// get the byte arrays for x and y
		byte[] xVal = x.getVal();
		byte[] yVal = y.getVal();

		// define our result and our array of partial products
		HeftyInteger product = new HeftyInteger(ZERO);
		byte[][] partialProducts = new byte[yVal.length][resultSize];

		// generate partial products
		for (int i = 0; i < yVal.length; i++) {
			// initialize carry as 0
			int carry = 0;
			// extract byte at given position in y, convert to unsigned
			int yByte = yVal[yVal.length - (i + 1)] & 0xFF;
			for (int j = 0; (j + i) < resultSize; j++) {
               // extract byte at given position in x, convert to unsigned
			   int xByte = xVal[xVal.length - (j + 1)] & 0xFF;
               // compute result of multiplication
               int resultByte = (xByte * yByte) + carry;
               // capture low order byte and add to result
               partialProducts[i][resultSize - (j + i + 1)] = (byte)(resultByte & 0xFF);
               // get carry (high order byte)
               carry = resultByte >> 8;
           }
		}
		// add partial products
		for (int i = 0; i < yVal.length; i++)
			product = product.add(new HeftyInteger(partialProducts[i]));

		// return our product
		return product;
	}

	/**
	 * Run the extended Euclidean algorithm on this and other
	 * @param other another HeftyInteger
	 * @return an array structured as follows:
	 *   0:  the GCD of this and other
	 *   1:  a valid x value
	 *   2:  a valid y value
	 * such that this * x + other * y == GCD in index 0
	 */
	public HeftyInteger[] XGCD(HeftyInteger other) {
		// prep variables
		HeftyInteger a = this.truncate();
		HeftyInteger b = other.truncate();
		boolean aNeg = false;
		boolean bNeg = false;

		// Determine if either input is negative. If either is, note this and negate it
		if (a.isNegative()) {
			aNeg = true;
			a = a.negate();
		}
		if (other.isNegative()) {
			bNeg = true;
			b = b.negate();
		}

		// find our values
		HeftyInteger[] vals = a.recursiveXGCD(b);
		
		// if we previously negated either value, we'll need to negate its corresponding s/t values
		if (aNeg)
			vals[1] = vals[1].negate();
		if (bNeg)
			vals[2] = vals[2].negate();
		// return our updated values
		return vals;
	}

	/**
	 * Helper method for XGCD(). Runs the extended Ecuclidian algorithm in a
	 * recursive manner.
	 * @param	other Other HeftyInteger being operated on
	 * @return 	Array of Euclidian values in this order: {GCD, s, t} 
	 */
	private HeftyInteger[] recursiveXGCD(HeftyInteger other) {
		// define our values
		HeftyInteger a = this;
		HeftyInteger b = other;

		// base case: b is 0
		if (b.isZero()) 
			return new HeftyInteger[] {a, new HeftyInteger(ONE), new HeftyInteger(ZERO)};
		
		// otherwise, run pre-order operations
		HeftyInteger[] quotAndRem = a.divide(b);
		HeftyInteger quotient = quotAndRem[0];
		HeftyInteger remainder = quotAndRem[1];
		// run recursive call on b and remainder
		HeftyInteger[] prevVals = b.recursiveXGCD(remainder);
		// run post-order ops to determine s and t
		HeftyInteger[] newVals = new HeftyInteger[3];
		newVals[0] = prevVals[0].truncate();
		newVals[1] = prevVals[2];
		newVals[2] = prevVals[1].subtract(quotient.multiply(prevVals[2])).truncate();

		// return our new Euclidian values
		return newVals;
	}

	/**
	 * Divides this by given divisor.
	 * @param	divisor Number this is being divided by
	 * @return	Array of integers in this order: {quotient, remainder}
	 */
	public HeftyInteger[] divide(HeftyInteger divisor) {
		// define one, two, zero, and dividend
		HeftyInteger one = new HeftyInteger(ONE);
		HeftyInteger two = new HeftyInteger(TWO);
		HeftyInteger zero = new HeftyInteger(ZERO);
		HeftyInteger dividend = this;

		// base case: divisor is 0
		if (divisor.isZero())
			return null;
		// base case: dividend equals divisor
		if (divisor.equals(dividend))
			return new HeftyInteger[] {one, zero};
		// base case: dividend is less than divisor
		if (dividend.compareTo(divisor) < 0)
			return new HeftyInteger[] {zero, dividend};
	
		// prep variables
		HeftyInteger remainder = dividend;
		HeftyInteger quotient = zero;
		divisor = divisor.byteShiftLeft(dividend.length());

		// run grade school divison
		for (int i = 0; i <= (dividend.length() * 8); i++) {
			// subtract divisor from remainder
			HeftyInteger sub = remainder.subtract(divisor);
			// if it's negative, shift the quotient left 1 bit
			if (sub.isNegative())
				quotient = quotient.multiply(two);
			// if it's positive, shift the quotient left by 1 bit, add one
			// and make the difference the new remainder
			else {
				quotient = quotient.multiply(two).add(one);
				remainder = sub;
			}
			// shift the divisor right one bit
			divisor = divisor.bitShiftRight();
		}
		
		// return quotient and remainder
		return new HeftyInteger[] {quotient.truncate(), remainder.truncate()};
	 }

	/**
	 * Helper method for division. Shifts this number left by given number of bytes.
	 * @param	numBytes Number of bytes this number is being shifted left by.
	 * @return 	Result of shift.
	 */
	private HeftyInteger byteShiftLeft(int numBytes) {
		byte[] newVal = new byte[numBytes + val.length];
		for (int i = 0; i < val.length; i++)
			newVal[i] = val[i];
		return new HeftyInteger(newVal);
	}

	/**
	 * Shifts this right by one bit.
	 * @return	Result of shit.
	 */
	private HeftyInteger bitShiftRight() {
		// declare a new byte array for the shifted value
		byte[] newVal = new byte[val.length];
		// define the first lsb
		int lsb = val[0] & 1;
		// shift the first byte
		newVal[0] = (byte)(val[0] >> 1);
		// loop the rest
		for (int i = 1; i < val.length; i++) {
			byte current = val[i];
			int newLSB = current & 1;
			// define a mask that will incorporate the lsb
			byte mask = (byte)(lsb == 1 ? 0b10000000 : 0);
			// shift this byte
			newVal[i] = (byte)(((current >> 1) & 0b01111111) | mask);
			// define the new lsb
			lsb = newLSB;
		}
		// return this shifted value
		return new HeftyInteger(newVal);
	}

}
