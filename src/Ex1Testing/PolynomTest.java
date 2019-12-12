/**
 * 
 */
package Ex1Testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

import Ex1.Monom;
import Ex1.Polynom;

/**
 * @author leefingerhut
 *
 */
class PolynomTest 
{

	/**
	 * Test method for Constructor
	 */
	@Test
	void testPolynom() 
	{
		Monom m0 = new Monom(5.9,1);
		Monom m1 = new Monom(3.5,3);
		Monom m2 = new Monom(9,9);
		double arr_coeff[] = {9,3.5,5.9};
		int arr_power[] = {9,3,1};
		Polynom p1 = new Polynom();
		p1.add(m0); p1.add(m1); p1.add(m2);
		Iterator<Monom> it = p1.iteretor();
		for(int i=0; it.hasNext(); i++) {
			Monom m = it.next();
			assertEquals(arr_coeff [i], m.get_coefficient(), 0.0001);
			assertEquals(arr_power[i],m.get_power());
		}
	}

	/**
	 * Test method for String constructor.
	 */
	@Test
	void testPolynomString() 
	{
		Polynom p1 = new Polynom("5.6x^2");
		Monom m = new Monom(5.6,2);
		Polynom p2 = new Polynom();
		p2.add(m);
		if(!p1.equals(p2))
		{
			fail("fail: Something is wrong with this fonction");
		}
	}

	/**
	 * Test method for function toString.
	 */
	@Test
	void testToString()
	{
		Monom m = new Monom(5.9,2);
		Monom m1 = new Monom(3.5,3);
		Polynom p = new Polynom();
		p.add(m1);
		p.add(m);
		String s1 = p.toString();
		String s2 = "3.5x^3+5.9x^2";
		assertTrue(!(s1==s2));
	}

	/**
	 * Test method for function F(x).
	 */
	@Test
	void testF() 
	{
		double x = 1;
		double y = 7;
		Polynom pf = new Polynom("4x^2+3x^1");
		assertEquals(y ,pf.f(x) , 0.0001);
	}
	
	/**
	 * Test method for Invalidity function F(x).
	 */
	@Test
	void testBadF() 
	{
		double x = 5;
		double y = 76;
		Polynom pf = new Polynom("2x^4+6x");
		assertNotEquals(y ,pf.f(x) , 0.0001);
	}

	/**
	 * Test method for adding anthoer polynom .
	 */
	@Test
	void testAddPolynom_able() 
	{
		Polynom p1 = new Polynom("3x^5+2x");
		Polynom p2 = new Polynom("3x+1x^5");
		Polynom result = new Polynom("4x^5+5x");
		p1.add(p2);
		assertEquals(result, p1);
	}

	/**
	 * Test method for adding monom to polynom.
	 */
	@Test
	void testAddMonom() 
	{
		Polynom p = new Polynom("4x^3+3x+1");
		Monom m = new Monom(3,3);
		Polynom result = new Polynom("7x^3+3x+1");
		p.add(m);
		assertEquals(result, p);
	}
	/**
	 * Test method for invalidity function adding monom .
	 */
	@Test
	void testBadAddMonom() 
	{
		Polynom p = new Polynom("6x^2-5");
		Monom m = new Monom(5,7);
		Polynom result = new Polynom("8x^6+3x");
		p.add(m);
		assertNotEquals(result, p);
	}

	/**
	 * Test method for subtract monom from polynom.
	 */
	@Test
	void testMonomSubtract() 
	{
		Polynom p = new Polynom("4x^2+3x+1");
		Monom m = new Monom(2,2);
		Polynom result = new Polynom("2x^2+3x+1");
		p.subtract(m);
		assertEquals(result, p);
	}

	/**
	 * Test method for subtract polynom from polynom.
	 */
	@Test
	void testPolynomSubstract() 
	{
		Polynom p = new Polynom("4x^2+3x+1");
		Polynom p1 = new Polynom("2x^2-5x");
		Polynom result = new Polynom("2x^2+8x+1");
		p.substract(p1);
		assertEquals(result, p);
	}

	/**
	 * Test method for multiply 2 polynons.
	 */
	@Test
	void testMultiplyPolynom_able()
	{

		Polynom p = new Polynom("4x^2+1");
		Polynom p1 = new Polynom("2x^2-1");
		Polynom result = new Polynom("8x^4-2x^2-1");
		p.multiply(p1);
		assertEquals(result, p);
	}

	/**
	 * Test method for equalty between polynom to object.
	 */
	@Test
	void testEqualsObject()
	{
		Polynom p = new Polynom("9x^6+7+8x^2");
		Polynom pEqual = new Polynom("9x^6+8x^2+7");
		assertTrue(p.equals(pEqual));
	}

	/**
	 * Test method for if the polynom equal to 0.
	 */
	@Test
	void testIsZero()
	{
		Polynom p = new Polynom();
		Monom m = new Monom (0,0);
		p.add(m);
		assertTrue(p.isZero());
	}

	/**
	 * Test method for root of the polynom in range [x0,x1].
	 */
	@Test
	void testRoot() 
	{
		Polynom p = new Polynom("1x^1+1");
		int root=-1;
		int result=(int)(p.root(-2, 5, 0.0000001));
		assertTrue(root==result);
	}

	/**
	 * Test method for deep copy.
	 */
	@Test
	void testCopy() 
	{
		Polynom p1 = new Polynom("7x^4+6x^3+7x^5+7x^8-9");
		Polynom p2 = (Polynom) p1.copy();
		if(!p1.equals(p2)) {
			fail("fail: something is wrong with the copy function");
		}
	}

	/**
	 * Test method for derivative of the polynom.
	 */
	@Test
	void testDerivative()
	{
		Polynom p = new Polynom("9x^3+5x^4+4x^6+2");
		Polynom y= new Polynom("27x^2+20x^3+24x^5");
		assertTrue(p.derivative().equals(y));
	}

	/**
	 * Test method for Riman's integral from x0 to x1 in eps steps.
	 */
	@Test
	void testArea() 
	{
		Polynom p = new Polynom("5x^1+2");
		assertTrue((int)(p.area(1, 10, 0.001))==265);
	}

	
	/**
	 * Test method for multiply polynom in anthoer monom.
	 */
	@Test
	void testMultiplyMonom() 
	{
		Polynom p = new Polynom("4x^2+1");
		Monom m = new Monom(2,2);
		Polynom result = new Polynom("8x^4+2x^2");
		p.multiply(m);
		assertEquals(result, p);
	}

}
