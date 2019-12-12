/**
 * 
 */
package Ex1Testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Ex1.Monom;

/**
 * @author leefingerhut
 *
 */
class MonomTest {

	
	/**
	 * Test method for Constructor.
	 */
	@Test
	void testMonomConstructor()
	{
		Monom m = new Monom(3.15,9);
		if(m.get_coefficient()==0 || m.get_power()<0) 
		{
			fail("fail: Something is wrong with this Monom");
		}
	}

	/**
	 * Test method for Comparison of coefficients.
	 */
	@Test
	void testGet_coefficient() 
	{
		double coeff = 5;
		Monom m = new Monom(coeff , 1);
		assertEquals(coeff, m.get_coefficient(), 0.00001);
		if(coeff!=m.get_coefficient())
		{
			fail("fail: Something is wrong with this Monom");
		}
	}

	/**
	 * Test method for Comparison of powers.
	 */
	@Test
	void testGet_power() 
	{
		int pow = 5;
		Monom m = new Monom(5.8 ,pow);
		assertEquals(pow, m.get_power());
	}

	/**
	 * Test method for derivative of the polynom.
	 */
	@Test
	void testDerivative() 
	{
		Monom m = new Monom(3, 2);
		Monom m1 = m.derivative();
		assertEquals(m.get_coefficient()*m.get_power(), m1.get_coefficient(), 0.00001);
		assertEquals(m.get_power()-1, m1.get_power());
		}

	/**
	 * Test method for function F(x).
	 */
	@Test
	void testF() 
	{
		int pow = 2;
		double coeff = 6.4;
		int x = 4;
		double result = coeff*(Math.pow(x,pow));
		Monom m = new Monom(coeff ,pow);
		assertEquals(result, m.f(x), 0.00001);
	}

	/**
	 * Test method for if the monom equal to 0
	 */
	@Test
	void testIsZero()
	{
		Monom m = new Monom (7, 4);
		m.makeZero();
		assertEquals(0, m.get_coefficient(), 0.0001);
		assertEquals(0, m.get_power());
		if(m.get_coefficient()!=0 && m.get_power()==0)
		{
			
		}
		
	}

	/**
	 * Test method for String constructor.
	 */
	@Test
	void testMonomString()
	{
		Monom m = new Monom("8x^3");
		Monom m1 = new Monom(7,3);
		Monom m2 = new Monom(1,3);
		m2.add(m1);
		assertEquals(m, m2);
	}

	/**
	 * Test method for adding monom to this monom..
	 */
	@Test
	void testAdd()
	{
		Monom m = new Monom (3, 2);
		Monom m1 = new Monom (2, 2);
		double cresult = m.get_coefficient() + m1.get_coefficient();
		m.add(m1);
		assertEquals(cresult, m.get_coefficient(), 0.00001);
	}

	/**
	 * Test method for  subtract monom from monom.
	 */
	@Test
	void testSubtract()
	{
		Monom m = new Monom (3,2);
		Monom m1 = new Monom (2, 2);
		double cresult = m.get_coefficient() - m1.get_coefficient();
		m.subtract(m1);
		assertEquals(cresult, m.get_coefficient(), 0.00001);
	}

	/**
	 * Test method for multiply monom in anthoer monom.
	 */
	@Test
	void testMultipy() 
	{
		Monom m1 = new Monom (4, 9);
		Monom m2 = new Monom (2.3, 2);
		double cresult = m1.get_coefficient()*m2.get_coefficient();
		int presult= m1.get_power()+m2.get_power();
		m1.multipy(m2);
		assertEquals(cresult, m1.get_coefficient(), 0.00001);
		assertEquals(presult, m1.get_power());
	}

	/**
	 * Test method for function toString.
	 */
	@Test
	void testToString() 
	{
		Monom m = new Monom("8x^3");
		Monom m1 = new Monom(7,3);
		Monom m2 = new Monom(1,3);
		m2.add(m1);
		assertEquals(m, m2);
	}

	/**
	 * Test method for equalty between monom to object.
	 */
	@Test
	void testEqualsObject() 
	{
		Monom m1 = new Monom (4, 3);
		Monom m2 = new Monom (4, 3);
		assertTrue(m1.equals(m2));
	}

	/**
	 * Test method for make the monom equal to 0.
	 */
	@Test
	void testMakeZero() 
	{
		Monom m = new Monom (7, 4);
		m.makeZero();
		assertEquals(0, m.get_coefficient(), 0.0001);
		assertEquals(0, m.get_power());
	}

}
