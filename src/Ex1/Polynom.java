package Ex1;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Iterator;


import Ex1.Monom;

/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Boaz
 *
 */
public class Polynom implements Polynom_able{

	/*
	 * @author leefingerhut
	 */

	ArrayList<Monom> p = new ArrayList<Monom>();

	/**
	 * Zero (empty polynom)
	 */
	public Polynom() {;}

	/**
	 * init a Polynom from a String such as:
	 *  {"x", "3+1.4x^3-34x", "2x^2-4", "-1.2x-7.1", "3-3.4x+1", "3.1x-1.2", "3X^2-3.1"};
	 * @param s: is a string represents a Polynom
	 */
	public Polynom(String s) { 
		if (s.length()==0) {throw new RuntimeException("String is empty");}
		s = s.replaceAll(" ", "");
		int lastAdded_i=0;
		String temp="";
		if (s.charAt(0)== '+')
		{
			s = s.substring(1, s.length());

		}
		for (int i=0; i<s.length(); i++) {

			if (s.charAt(i)=='+' || s.charAt(i)=='-') {
				if (i==0 && s.charAt(i)=='-') { continue; }
				temp=s.substring(lastAdded_i, i);
				try {
					Monom t = new Monom(temp);
					p.add(t);
				}
				catch (Exception e) { throw new RuntimeException("This is not a legal Polynom.");}
				lastAdded_i=i;

			}
			if (i==s.length()-1) {
				temp=s.substring(lastAdded_i);
				try {
					Monom t = new Monom(temp);
					p.add(t);
				}
				catch (Exception e) { throw new RuntimeException("This is not a legal Polynom.");}
			}
		}
		sortP();

	}

	/**
	 * this method sort the Monoms inside a Polynom.
	 */
	private void sortP() { 
		Collections.sort(this.p, new Monom_Comperator());
		for (int i=0; i<this.p.size();i++) {
			for (int j=i+1; j<this.p.size();j++) {
				if (this.p.get(i).get_power()==this.p.get(j).get_power()) {
					this.p.get(i).add(this.p.get(j));
					this.p.get(j).makeZero();
				}
			}
		}
		Iterator<Monom> it = this.iteretor();
		while (it.hasNext()) {
			if (it.next().isZero()) {it.remove();}
		}
	}

	/**
	 * this method convert a Polynom into a String and return te String.
	 */
	public String toString() { 
		if (this.p.size()==0) {return "0";}
		Iterator<Monom> it = this.iteretor();
		String temp="";
		String ans="";
		ans=it.next().toString();
		if(this.p.size()==1) {return ans;}
		while (it.hasNext()) {
			temp=it.next().toString();
			if (temp.charAt(0)=='-') {ans=ans+""+temp;}
			else {ans=ans+"+"+temp;}
		}
		return ans;
	}

	/**
	 * this method return the value of the polynom at given point x.
	 */
	public double f(double x) 
	{
		double result = 0;
		Iterator<Monom> it = this.iteretor();
		while(it.hasNext()) 
		{
			result += it.next().f(x);
		}
		return result;
	}


	/**
	 * this method add a given polynom.
	 */
	@Override
	public void add(Polynom_able p1) { 
		Monom temp;
		Iterator<Monom> it = p1.iteretor();
		while (it.hasNext()) {
			temp=it.next();
			add(temp);
		}
		this.sortP();
	}
	/**
	 * this method add a given monom to the polynom.
	 */
	@Override
	public void add(Monom m1) { 
		for (int i = 0; i < this.p.size(); i++) {
			if (this.p.get(i).get_power()==m1.get_power()) {
				this.p.get(i).add(m1);
				return;
			}
		}
		this.p.add(m1);
		this.sortP();
	}
	/**
	 * this method subtract a given monom from the polynom.
	 */
	public void subtract(Monom m1) { 
		for (int i = 0; i < this.p.size(); i++) {
			if (this.p.get(i).get_power()==m1.get_power()) {
				this.p.get(i).subtract(m1);
				return;
			}
		}
		Monom temp = new Monom(-1*m1.get_coefficient(),m1.get_power());
		this.p.add(temp);
		this.sortP();
	}
	/**
	 * this method subtract a given polynom. 
	 */
	@Override
	public void substract(Polynom_able p1) { 
		Monom temp;
		Iterator<Monom> it = p1.iteretor();
		while (it.hasNext()) {
			temp=it.next();
			subtract(temp);
		}
		this.sortP();
	}
	/**
	 * this method multiply to polynoms.
	 */
	@Override
	public void multiply(Polynom_able p1) { 

		Iterator<Monom> it = p1.iteretor();
		Polynom p2 = new Polynom();
		while (it.hasNext()) {
			Polynom p3 = new Polynom(this.toString());
			Monom m = it.next();
			p3.multiply(m);
			p2.add(p3);
		}
		this.p = p2.p;
		sortP();
	}
	/**
	 * this method return true if the object is a from type polynon and if is equal to this polynom, otherwise return false.
	 */
	@Override
	public boolean equals(Object p1)
	{
		if (!(p1 instanceof Polynom))
			return false;

		Polynom tempP = (Polynom)p1;
		if (this.p.size() != tempP.p.size())
			return false;

		this.sortP();
		tempP.sortP();
		Iterator<Monom> it_this = this.iteretor();
		Iterator<Monom> it_p1 = tempP.iteretor();
		while (it_this.hasNext()) {
			Monom tempM_this = it_this.next();
			Monom tempM_p1   = it_p1.next();
			if (!(tempM_this.equals(tempM_p1)))
				return false;
		}
		return true;
	}

	/**
	 * this method return's true if polynom equals 0, false otherwise.
	 */
	@Override
	public boolean isZero() {
		if (this.p.size()==0) {return true;}
		return this.p.get(0).get_coefficient()==0;}
	/**
	 * this method return a root of the polynom in range [x0,x1] if there is, 
	 * if there is not, throws an exception.
	 */
	@Override


	public double root(double x0, double x1, double eps) {
		if (eps<=0) { throw new RuntimeException("eps should be positive none zero");}
		if (f(x0)*f(x1)>0) { throw new RuntimeException("Ilegal input");}
		double currX=(x1+x0)/2;

		while (Math.abs(f(x1)-f(x0))>eps) {
			currX=(x1+x0)/2;
			if (f(x1)*f(currX)>0) {x1=currX;}
			else {x0=currX;}
		}
		return currX;
	}
	/**
	 * this method creates a deep copy of the polynom.
	 * @return the created copy.
	 * 
	 */

	@Override
	public Polynom_able copy() 
	{ 
		Polynom newP = new Polynom();
		for (int i=0; i<this.p.size();i++)
		{
			Monom newM = new Monom(this.p.get(i));
			newP.add(newM);
		}
		return newP;
	}

	/**
	 * this method create a polynom which is a derivative of the polynom.
	 */ 

	@Override
	public Polynom_able derivative() { 
		Polynom newP = new Polynom();
		for (int i=0; i<this.p.size();i++) {
			newP.add(this.p.get(i).derivative());
		}
		newP.sortP();
		return newP;
	}

	/**
	 * Compute a Riman's integral from x0 to x1 in eps steps.
	 * @return the approximated area above the polynom, average of lower and upper Riemann's sum.
	 */
	@Override
	public double area(double x0, double x1, double eps) {
		if (eps<=0) {throw new RuntimeException("cant compute area for negative or 0 length steps");}
		if (x1-x0<eps) {throw new RuntimeException("cant compute area for higher eps than x-range");}
		double curr=x0, ans=0;
		while (curr+eps<x1) {
			ans+=Math.abs((f(curr)*eps+f(curr+eps)*eps)/2);
			curr+=eps;
		}
		ans+=Math.abs((f(curr)*eps+f(x1)*eps)/2);
		return ans;
	}

	@Override
	public Iterator<Monom> iteretor() {
		Iterator<Monom> it = p.iterator();
		return it;
	}
	/**
	 * this method multiply the polynom and the given monom.
	 */
	@Override
	public void multiply(Monom m1) {
		Iterator<Monom> it = this.iteretor();
		while(it.hasNext()) {it.next().multipy(m1);}
	}
	/*
	 * takes the string function and turns it to a monom.
	 */

	@Override
	public function initFromString(String s) {
		Polynom p = new Polynom (s);// takes the string function and turns it to a polynom.
		return p;

	}
}