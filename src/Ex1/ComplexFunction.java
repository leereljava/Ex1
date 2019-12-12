package Ex1;
import java.util.*;

/*
 *complex function An object that consists of 2 functions (polynom type, monom and complexfunction) 
 *and Operation.
 *
 * @author leefingerhut
 */

public class ComplexFunction implements complex_function
{
	private Operation op;
	private function left, right;
	/*
	 * all the type of ComplexFunction Constractors.
	 */
	public ComplexFunction (Operation op, function left,  function right)
	{
		this.op = op;
		this.left = left;
		this.right = right;
	}
	public ComplexFunction () {;}

	public ComplexFunction (String op, function left,  function right)
	{
		this(getOpFromString(op), left, right);
	}

	public ComplexFunction (Operation op)
	{
		this(op, null, null);
	}

	public ComplexFunction (function left)
	{
		this(Operation.None, left, null);
	}


	public ComplexFunction (function left ,function right)
	{
		this(Operation.Error, left, right);
	}

	public ComplexFunction (Operation op, function left)
	{
		this(op, left, null);
	}

	public ComplexFunction (ComplexFunction CF)
	{
		this.left = CF.left();
		this.right = CF.right();
		this.op = CF.getOp();
	}

	/*
	 * this method convert a complexfunction into a String and return the String.
	 */

	public String toString()
	{
		String s = new String();
		if (this.op == Operation.None)
			return this.left.toString();
		s = s + getStringFromOp(this.op) + '(' + this.left.toString() +  ',' + this.right.toString() + ')';
		return s;
	}
	/***
	 * x - the number we will check the function value.
	 * return - Recursion - The last value of the function in the order of constructing the complex function.
	 * throws - if there is an attempt to divide it when the value of the function is 0.
	 */

	@Override
	public double f(double x)
	{
		switch (this.op)
		{
		case Plus:
			return this.left.f(x) + this.right.f(x);
		case Times:
			return this.left.f(x) * this.right.f(x);
		case Divid:
			if(this.right.f(x) == 0)
			{return 1e9;}
			return this.left.f(x)/this.right.f(x);
		case Max:
			return Math.max(this.left.f(x), this.right.f(x));
		case Min:
			return Math.min(this.left.f(x), this.right.f(x));
		case Comp:
			return this.left.f(this.right.f(x));
		case None:
			return this.left.f(x);
		default:
		{throw new IllegalArgumentException("Unexpected value: " + op);}
		}
	}
	/*
	 * Receives String and returns complex function:
	 *If no parentheses - polynomial is returned
	 *If there are any brackets, break the string with balanced brackets and return:
	 *From the first bracket to the comma - the left function
	 *From the comma to the second bracket - the right function
	 *I used 3 private functions, an explanation of each function.
	 */

	@Override
	public function initFromString(String s)
	{
		String[] p1 = s.split("[\\((||\\)]");
		if (p1.length == 1)
		{
			this.left  = new ComplexFunction(Operation.None, null, null);
			this.left  = new Polynom(p1[0]);
			this.right = null;
			this.op    = Operation.None;
			return this;
		}
		this.left  = new ComplexFunction(Operation.None, null, null);
		this.right = new ComplexFunction(Operation.None, null, null);
		this.op    = getOpFromString(p1[0]);
		if (p1.length == 2)
		{
			String[] p2 = p1[1].split("\\,");
			if (p2.length != 2)
			{throw new RuntimeException("init string isn't valid!");}
			this.left.initFromString(p2[0]);
			this.right.initFromString(p2[1]);
			return this;
		}
		ArrayList<String> sal = parseInternal(s);
		this.left.initFromString(sal.get(0).trim());
		this.right.initFromString(sal.get(1).trim());
		return this;
	}
	/*
	 * trim operation and outside parentheses
	 */

	private static ArrayList<String> parseInternal(String s)
	{
		int i = 0;
		while (s.charAt(i) != '(')
			i++;
		s = s.substring(i+1, s.length());
		i = 0;
		int comma = 0;
		int paren = 0;
		while(i <= s.length()) {
			switch (s.charAt(i)) {
			case '(':
				paren = i;
				break;
			case ',':
				comma = i;
				break;
			default:
				break;
			}
			if (comma > 0 || paren > 0)
				break;
			i++;
		}
		ArrayList<String> sal = new ArrayList<String>();
		if (comma > 0) {
			sal.add(s.substring(0, comma));
			sal.add(s.substring(comma+1, s.length()-1));
		}
		if (paren > 0) {
			i = paren;
			int count = 1;
			while(i <= s.length()) {
				if (count == 0)
					break;
				i++;
				if (s.charAt(i) == '(')
					count++;
				if (s.charAt(i) == ')')
					count--;
			}
			sal.add(s.substring(0, i+1));
			sal.add(s.substring(i+2, s.length()-1));
		}
		return sal;
	}
	/*
	 * Gets the operation from the string.
	 */

	private static Operation getOpFromString(String s)
	{
		switch (s.trim())
		{
		case "plus":
			return Operation.Plus;
		case "Plus":
			return Operation.Plus;
		case "mul":
			return Operation.Times;
		case "Times":
			return Operation.Times;
		case "div":
			return Operation.Divid;
		case "Divid":
			return Operation.Divid;
		case "max":
			return Operation.Max;
		case "Max":
			return Operation.Max;
		case "min":
			return Operation.Min;
		case "Min":
			return Operation.Min;
		case "comp":
			return Operation.Comp;
		case "Comp":
			return Operation.Comp;
		default:
			return Operation.Error;
		}
	}
	/*
	 * Gets String from the operation.
	 */

	private static String getStringFromOp(Operation oper)
	{
		switch (oper)
		{
		case Plus:
			return "plus";
		case Times:
			return "mul";
		case Divid:
			return "div";
		case Max:
			return "max";
		case Min:
			return "min";
		case Comp:
			return "comp";
		default:
			return "Error";
		}
	}
	/*
	 * Deep Copy from other ComplexFunction.
	 */

	@Override
	public function copy()
	{
		ComplexFunction Cfc = new ComplexFunction(op ,left,right);
		return Cfc;
	}
	/* 
     * adding this complex_function with the f1 complex_function
	 */

	@Override
	public void plus(function f1)
	{
		left = this.copy();
		right = f1;
		op = Operation.Plus;
	}
	/*
	 * Multiplier this complex function with the f1 complex function.
	 */

	@Override
	public void mul(function f1)
	{
		left = this.copy();
		right = f1;
		op = Operation.Times;
	}
	/*
	 * divide this complex function with the f1 complex function.
	 */

	@Override
	public void div(function f1)
	{
		left = this.copy();
		right = f1;
		op = Operation.Divid;
	}
	/*
	 * Compares the maximum this complex function with the f1 complex function.
	 */
	@Override
	public void max(function f1)
	{
		left = this.copy();
		right = f1;
		op = Operation.Max;
	}
	/*
	 * Compares the minimum this complex function with the f1 complex function.
	 */

	@Override
	public void min(function f1)
	{
		left = this.copy();
		right = f1;
		op = Operation.Min;
	}
	/*
	 * Poses in the complex function: where it has the variable X
	 * the f1 complex function.
	 * this.f(f1(x))
	 */
	@Override
	public void comp(function f1)
	{
		left = this.copy();
		right = f1;
		op = Operation.Comp;
	}
	/*
	 * Returns the left function of the complex function
	 */

	@Override
	public function left()
	{
		return this.left;
	}
	/*
	 * Returns the right function of the complex function
	 */
	@Override
	public function right()
	{
		return this.right;
	}
	/*
	 * Returns the Operation of the complex function
	 */

	@Override
	public Operation getOp()
	{
		return this.op;
	}
	/**
	 * check Equine between 2 objects of the same type
	 *Compare the object to be equal to a complex function
	 *The test is performed within a range of certain values
	 *There is a problem with this function, extension wiki.
	 *if they are equals - return true
	 *else, false.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof ComplexFunction))
		{
			return false;
		}
		ComplexFunction cf = (ComplexFunction)obj;
		Range r = new Range(-5, 5);
		double r_size = r.get_max() - r.get_min();
		int res = 200;
		for (int i=0; i<res+1; i++) 
		{
			double x = r.get_min() + i * r_size / res;
			if (Math.abs(this.f(x)-cf.f(x)) > 0.0000001)
			{
				return false;
			}
		}
		return true;
	}
}
