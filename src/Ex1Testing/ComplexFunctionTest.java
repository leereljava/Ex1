/**
 * 
 */
package Ex1Testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Ex1.ComplexFunction;
import Ex1.Operation;
import Ex1.Polynom;
import Ex1.function;

/**
 * @author leefingerhut
 *
 */
class ComplexFunctionTest {

	/**
	 * Test method for function toString .
	 */
	@Test
	void testToString()
	{
		Polynom p1 = new Polynom("3x-1");
		Polynom p2 = new Polynom("7x^3+4");
		ComplexFunction cf = new ComplexFunction("mul",p1,p2);
		ComplexFunction cf1 = new ComplexFunction();
		String s = "mul(3x-1, 7x^3+4)";
		cf1.initFromString(s);
		assertTrue(cf1.equals(cf));
	}
	/**
	 *  Test method for Invalidity toString.
	 */
	@Test
	void testBadToString()
	{
		Polynom p1 = new Polynom("2x^3");
		Polynom p2 = new Polynom("5x^4");
		ComplexFunction cf = new ComplexFunction("plus",p1,p2);
		ComplexFunction cf1 = new ComplexFunction();
		String s = "plus(9x-1, 6x^3+4)";
		cf1.initFromString(s);
		assertFalse(cf1.equals(cf));
	}

	/**
	 * Test method for function F(x).
	 */
	@Test
	void testF()
	{
		Polynom p1 = new Polynom("3x-1");
		Polynom p2 = new Polynom("7x^3+4");
		ComplexFunction cf = new ComplexFunction("Plus",p1,p2);
		ComplexFunction cf1 = new ComplexFunction(Operation.None, p1, null);
		double result_cf = 13;
		double result_cf1 = 2;
		assertEquals(cf.f(1), result_cf, 0.0001);
		assertEquals(cf1.f(1), result_cf1, 0.0001);
	}
	/**
	 * test method for Invalidity function F(x).
	 */
	@Test
	void testBadF()
	{
		Polynom p1 = new Polynom("3x-1");
		Polynom p2 = new Polynom("7x^3+4");
		ComplexFunction cf = new ComplexFunction("Plus",p1,p2);
		ComplexFunction cf1 = new ComplexFunction(Operation.None, p1, null);
		double result_cf = 4;
		double result_cf1 = 6000;
		assertNotEquals(cf.f(1), result_cf, 0.0001);
		assertNotEquals(cf1.f(1), result_cf1, 0.0001);
	}

	/**
	 * Test method for Get the operation and functions from a string.
	 */
	@Test
	void testInitFromString() 
	{
		String s= "plus(3x^4+1, 5x)";
		ComplexFunction cf = new ComplexFunction("plus" , new Polynom("3x^4+1"), new Polynom("5x"));
		ComplexFunction cf1 = new ComplexFunction();
		cf1.initFromString(s);
		assertTrue(cf.equals(cf1));
	}
	/**
	 * Test method for Invalidity function InitFromString.
	 */
	@Test
	void testBadInitFromString() 
	{
		String s= "plus(3x^4+1, 5x)";
		ComplexFunction cf = new ComplexFunction("plus" , new Polynom("2x^4+1"), new Polynom("5x"));
		ComplexFunction cf1 = new ComplexFunction();
		cf1.initFromString(s);
		assertNotEquals(cf.toString(), cf1.toString());
	}

	/**
	 * Test method for deep copy of complex function.
	 */
	@Test
	void testCopy() 
	{
		Polynom p1 = new Polynom("x^4+5");
		Polynom p2 = new Polynom("x^2");
		ComplexFunction cf = new ComplexFunction("plus", p1, p2);
		ComplexFunction cf1 = new ComplexFunction(cf);
		assertTrue(cf.equals(cf1));
	}

	/**
	 * Test method for adding complex function with antoher complex function.
	 */
	@Test
	void testPlus()
	{
		Polynom p1 = new Polynom("x^3+5");
		Polynom p2 = new Polynom("x");
		ComplexFunction cf = new ComplexFunction("plus",p1,p2);
		ComplexFunction cf1 = new ComplexFunction(new Polynom("x^3+x+5"));
		assertTrue(cf.equals(cf1));
	}

	/**
	 * Test method for multiply complex functoin in antoher complex function.
	 */
	@Test
	void testMul() 
	{
		Polynom p1 = new Polynom("x^2+20x");
		Polynom p2 = new Polynom("x");
		ComplexFunction cf = new ComplexFunction("mul",p1,p2);
		ComplexFunction cf1 = new ComplexFunction(new Polynom("x^3+20x^2"));
		assertTrue(cf.equals(cf1));
	}

	/**
	 * Test method for Divide between complex function to antoher complex function.
	 */
	@Test
	void testDiv() 
	{
		Polynom p1 = new Polynom("x^2+20x+100");
		Polynom p2 = new Polynom("x+10");
		ComplexFunction cf = new ComplexFunction("div",p1,p2);
		ComplexFunction cf1 = new ComplexFunction(p2);
		assertTrue(cf.equals(cf1));
	}

	/**
	 * Test method for the max complex function between 2 complex functions.
	 */
	@Test
	void testMax() 
	{
		Polynom p1 = new Polynom("2x^2");
		Polynom p2 = new Polynom("x-15");
		ComplexFunction cf = new ComplexFunction("max",p1,p2);
		ComplexFunction cf1 = new ComplexFunction(p1);
		assertTrue(cf.equals(cf1));
	}

	/**
	 * Test method for the min complex function between 2 complex functions.
	 */
	@Test
	void testMin() 
	{
		Polynom p1 = new Polynom("x^2");
		Polynom p2 = new Polynom("0");
		ComplexFunction cf = new ComplexFunction("min",p1,p2);
		ComplexFunction cf1 = new ComplexFunction(p2);
		assertTrue(cf.equals(cf1));
	}
	/**
	 * Test method for Invalidity function min.
	 */
	@Test
	void testBadMin() 
	{
		Polynom p1 = new Polynom("x^2");
		Polynom p2 = new Polynom("x^2+9");
		ComplexFunction cf = new ComplexFunction("min",p1,p2);
		ComplexFunction cf1 = new ComplexFunction(p2);
		assertFalse(cf.equals(cf1));
	}
	
	/**
	 * Test method for Assembly of 2 functions into a complex function.
	 */
	@Test
	void testComp()
	{
		Polynom p1 = new Polynom("x");
		Polynom p2 = new Polynom("x^2");
		ComplexFunction cf = new ComplexFunction("comp",p1,p2);
		ComplexFunction cf2 = new ComplexFunction("comp",p2,p1);
		assertTrue(cf.equals(cf2));
	}

	/**
	 * Test method for get the left function in the complex function.
	 */
	@Test
	void testLeft()
	{
		Polynom p1 = new Polynom("x");
		Polynom p2 = new Polynom("3x^2+3x");
		Polynom p3 = new Polynom("5x^5");
		ComplexFunction cf = new ComplexFunction("mul",new ComplexFunction("plus",p2,p3),p1);
		ComplexFunction cfL = new ComplexFunction(cf.left());
		assertTrue(cf.left().toString().equals(cfL.toString()));
	}

	/**
	 * Test method for get the right function in the complex function.
	 */
	@Test
	void testRight() 
	{
		Polynom p1 = new Polynom("x");
		Polynom p2 = new Polynom("3x^2+3x");
		Polynom p3 = new Polynom("5x^5");
		ComplexFunction cf = new ComplexFunction("mul",new ComplexFunction("plus",p2,p3),p1);
		ComplexFunction cfR = new ComplexFunction(cf.right());
		assertTrue(cf.right().toString().equals(cfR.toString()));
	}

	/**
	 * Test method for get the operation of the complex function.
	 */
	@Test
	void testGetOp()
	{
		Polynom p1 = new Polynom("x");
		Polynom p2 = new Polynom("3x^2+3x");
		Operation op=Operation.Plus;
		ComplexFunction cf1 = new ComplexFunction(op,p1,p2);
		ComplexFunction cf2 = new ComplexFunction(cf1);
		assertEquals(cf1.getOp(), cf2.getOp());
	}

	/**
	 * Test method for equalty between complex functiom to object.
	 * There is a problem with this function, wiki extension.
	 */
	@Test
	void testEqualsObject() 
	{
		Polynom p1 = new Polynom("x");
		Polynom p2 = new Polynom("x^2");
		ComplexFunction cf1 = new ComplexFunction("div",p1,p2);
		ComplexFunction cf2 = new ComplexFunction(p1);
		assertFalse(cf1.equals(cf2));
	}

}
