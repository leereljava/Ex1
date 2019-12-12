package Ex1;

import java.util.Comparator;

/**
 * This class represents a simple "Monom" of shape ax^b, where a is a real number and b
 *  is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
public class Monom implements function{
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {return _Comp;}
	private static final String LEGAL_CHARS = "x.^0123456789-+";
	private static final String Numbers = "0123456789";

	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}

	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}

	public double get_coefficient() {
		return this._coefficient;
	}
	public int get_power() {
		return this._power;
	}
	/*
	 * @author leefingerhut
	 */

	/** 
	 * this method returns the derivative monom of this.
	 * @return derivatived Monom.
	 */
	public Monom derivative() {
		if(this.get_power()==0) {return new Monom(ZERO);}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}
	/**
	 * this method returns the value of the function on x.
	 * @return f(x).
	 */
	public double f(double x) {
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	} 
	/**
	 * this method return true if Monom's coefficient equals 0.
	 */
	public boolean isZero() {return this.get_coefficient() == 0;}

	// ***************** add your code below **********************
	/**
	 * this method is a constructor that convert a string to new Monom.
	 * @param s
	 */
	public Monom(String s) { 

		if (s.length()==0) {throw new RuntimeException("String is empty");}

		String t=s.toLowerCase();
		if (t.charAt(0)=='+') { t=t.substring(1);}
		boolean legal;

		for (int i=0;i<t.length(); i++) {
			legal = false;
			for (int j=0;j<LEGAL_CHARS.length(); j++) {
				if (t.charAt(i)==LEGAL_CHARS.charAt(j)) {legal=true;}
			}
			if (legal==false) {throw new RuntimeException("String is not in the form of ax^b");}
		}
		// if start with an 'x'.
		if (t.charAt(0)=='x') {
			if (t.length()==1) {
				this._coefficient=1;
				this._power=1;
				return;
			}
			if (t.length()<3) {throw new RuntimeException("String is not in the form of ax^b");}
			if (t.charAt(1)!='^') {throw new RuntimeException("String is not in the form of ax^b");}
			try {
				this._power=Integer.parseInt(t.substring(2));
				this._coefficient=1;
				return;
			}
			catch (Exception e) {throw new RuntimeException("String is not in the form of ax^b");}
		}
		//if not contain 'x'.
		if (!t.contains("x")) {
			try {
				this._coefficient=Double.parseDouble(t);
				this._power=0;
				return;
			}
			catch (Exception e) {throw new RuntimeException("String is not in the form of ax^b");}
		}
		//if starts with a '-'.
		if (t.charAt(0)=='-') {
			if (t.length()<2)  {throw new RuntimeException("String is not in the form of ax^b");}
			if (t.length()==2) {
				if (t.charAt(1)=='x') {
					this._coefficient=-1;
					this._power=1;
					return;
				}
				{throw new RuntimeException("String is not in the form of ax^b");}
			}
			if (t.charAt(1)=='x') {
				if (t.length()<4) {throw new RuntimeException("String is not in the form of ax^b");}
				if (t.charAt(2)!='^') {throw new RuntimeException("String is not in the form of ax^b");}
				try {
					this._power=Integer.parseInt(t.substring(3));
					this._coefficient=-1;
					return;
				}
				catch (Exception e) {throw new RuntimeException("String is not in the form of ax^b");}
			}
		}

		//has coefficient, power=1.
		if (t.charAt(t.length()-1)=='x') {
			try {
				this._coefficient=Double.parseDouble(t.substring(0,t.length()-1));
				this._power=1;
				return;
			}
			catch (Exception e) {throw new RuntimeException("String is not in the form of ax^b");}
		}

		//normal case => ax^b, full values.
		int x_=0;
		for (int i=0;i<t.length();i++) { if (t.charAt(i)=='x') {x_=i;} }
		if (t.length()<x_+3) {throw new RuntimeException("String is not in the form of ax^b");}
		else {
			try {
				this._coefficient=Double.parseDouble(t.substring(0,x_));
				this._power=Integer.parseInt(t.substring(x_+2));
				return;
			}
			catch (Exception e) {throw new RuntimeException("String is not in the form of ax^b");}
		}
	}
	/**
	 * this method add the Monom m to our Monom.
	 * only if possible, otherwise throws exception.
	 * @param m
	 */
	public void add(Monom m) {
		if (this.get_power()==m.get_power()) {this._coefficient+=m._coefficient;}
		else {throw new RuntimeException("Can't add, Monom's power unequal.");}
	}

	/**
	 * this method subtract the Monom m from our Monom.
	 * only if possible, otherwise throws exception.
	 * @param m
	 */
	public void subtract(Monom m) {
		if (this.get_power()==m.get_power()) {this._coefficient-=m._coefficient;}
		else {throw new RuntimeException("Can't subtarct, Monom's power unequal.");}
	}

	/**
	 * this method multiply 2 Monoms.
	 * @param d
	 */
	public void multipy(Monom d) {
		this._coefficient*=d._coefficient;
		this._power+=d._power;
	}

	/**
	 * this method convert a Monom to a string in form of "ax^b"
	 */
	@Override
	public String toString() {
		if (this.get_coefficient()==0) {
			return "0";
		}
		if (this.get_power()==0) {
			return ""+this.get_coefficient();
		}
		if (this.get_power()==1) {
			return ""+this.get_coefficient()+"x";
		}
		return ""+this.get_coefficient()+"x^"+this.get_power();
	}
	/**
	 * this method checks if the 2 Monoms are equal.
	 * @param other
	 * @return true if equal, false otherwise.
	 */


	public boolean equals(Object obj)
	{
		if (!(obj instanceof Monom))
		{
			return false;
		}
		Monom temp = (Monom)obj;
		//	System.out.println(temp.toString());
		return ((this.get_coefficient() == temp.get_coefficient()) && (this.get_power() == temp.get_power()));		
	}
	/*public boolean equals(Monom other) {
		if(this.get_coefficient()==0 && other.get_coefficient()==0) {return true;}
		return (this.get_coefficient()-other.get_coefficient()<EPSILON && this.get_power()==other.get_power());
	}*/

	/**
	 * this method make a Monom to be equal to 0.
	 * Assistant for Monom sort.
	 */
	public void makeZero () {
		this.set_coefficient(0);
		this.set_power(0);
	}
	//****************** Private Methods and Data *****************

	private void set_coefficient(double a){
		this._coefficient = a;
	}
	private void set_power(int p) {
		if(p<0) {throw new RuntimeException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}

	private double _coefficient; 
	private int _power;
	/*
	 * takes the string function and turns it to a monom.
	 */
	@Override
	public function initFromString(String s) 
	{
		Monom p = new Monom (s);
		return p;
	}
	/*
	 * deep copy 
	 */
	@Override
	public function copy() 
	{
		Polynom p = new Polynom();
		p.add(this);
		return p;
	}
}

