package Ex1;

import java.util.*;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Ex1.StdDraw;

/*
* @author leefingerhut
*/

public class Functions_GUI implements functions
{
	ArrayList<function> funcs = new ArrayList<function>();

	@Override
	public int size() 
	{
		return this.funcs.size();
	}

	@Override
	public boolean isEmpty() 
	{
		return this.funcs.isEmpty();
	}

	@Override
	public boolean contains(Object o)
	{ 
		return funcs.contains(o);
	}


	@Override
	public Iterator<function> iterator() 
	{
		return this.funcs.iterator();
	}

	@Override
	public Object[] toArray() { // fix me
		return this.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) { // fix me
		return funcs.toArray(a);
	}

	@Override
	public boolean add(function e)
	{
		return this.funcs.add(e);
	}

	@Override
	public boolean remove(Object o)
	{ 
		return funcs.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) 
	{ 
		return funcs.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends function> c) { // fix me
		return funcs.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return this.funcs.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) { // fix me
		return funcs.retainAll(c);
	}

	@Override
	public void clear() {
		this.funcs.clear();
	}

	@Override
	public void initFromFile(String file) throws IOException 
	{
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			
			while ((line = reader.readLine()) != null) {
				ComplexFunction fc = new ComplexFunction(Operation.None);
				fc.initFromString(line.trim());
		        this.funcs.add(fc);      
		    }
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void saveToFile(String file) throws IOException 
	{
		try 
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			Iterator<function> it = this.funcs.iterator();
			while(it.hasNext())
			{
				String s = it.next().toString();
				writer.write(s);
				writer.write("\n");
			}
			writer.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();

		}
	}

	@Override
	public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) 
	{
		StdDraw.setCanvasSize(width, height);

		double[][] x = new double[this.size()][resolution+1];
		double[][] y = new double[this.size()][resolution+1];
		double r = rx.get_max() - rx.get_min();
		//System.out.println(this.size());
		for (int i = 0; i < this.size(); i++)
		{
			for (int j = 0; j <= resolution; j++) 
			{
				x[i][j] = rx.get_min() + r * j / resolution;
				y[i][j] = this.funcs.get(i).f(x[i][j]);
			}	
		}
		// rescale the coordinate system
		StdDraw.setXscale(rx.get_min(), rx.get_max());
		StdDraw.setYscale(ry.get_min(), ry.get_max());

		//////// vertical lines
		StdDraw.setPenColor(Color.LIGHT_GRAY);
		for (double i = Math.ceil(rx.get_min()); i <= Math.floor(rx.get_max()); i++) 
		{
			StdDraw.line(i, ry.get_min(), i, ry.get_max());
		}
		//////// horizontal  lines
		for (double i = Math.ceil(ry.get_min()); i <= Math.floor(ry.get_max()); i++) 
		{
			StdDraw.line(rx.get_min(), i, rx.get_max(), i);
		}
		//////// y axis		
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.setPenRadius(0.005);
		StdDraw.line(0, ry.get_min(), 0, ry.get_max());
		for (double k = Math.ceil(ry.get_min()); k <= Math.floor(ry.get_max()); k++) 
		{
			if(k==0)
			{continue;}
			StdDraw.text(-0.25, k, Integer.toString((int)k));
		}

		//////// x axis	
		StdDraw.line(rx.get_min(), 0, rx.get_max(), 0);
		for (double k = Math.ceil(rx.get_min()); k <= Math.floor(rx.get_max()); k++) 
		{
			if(k==0)
			{continue;}
			StdDraw.text(k, -0.4, Integer.toString((int)k));
		}
		// plot the approximation to the function
		StdDraw.setPenRadius(0.005);
		Color[] Colors = {Color.blue, Color.cyan,
				Color.MAGENTA, Color.ORANGE, Color.red, Color.GREEN, Color.PINK};

		for (int i = 0; i < this.size(); i++)
		{
			StdDraw.setPenColor(Colors[i%(Colors.length)]);
			for (int j = 0; j < resolution-1; j++) {
				StdDraw.line(x[i][j], y[i][j], x[i][j+1], y[i][j+1]);
			}
		}
	}


	@Override
	public void drawFunctions(String json_file)
	{
		JSONParser parser = new JSONParser();
		
		try {
			Object obj = parser.parse(new FileReader(json_file));
			JSONObject jsonObject = (JSONObject) obj;

			long W      = (long) jsonObject.get("Width");
			long H      = (long) jsonObject.get("Height");
			long Res    = (long) jsonObject.get("Resolution");
			String rangex = (String) jsonObject.get("Range_X").toString();
			String rangey = (String) jsonObject.get("Range_Y").toString();

		 	int width = (int)W;
			int height = (int)H;
			int resolution = (int)Res;

			rangex = rangex.substring(1, rangex.length()-1);
			String[] p = rangex.split("\\,");
			Range rx = new Range(Double.valueOf(p[0]),Double.valueOf(p[1]));
			rangey = rangey.substring(1, rangey.length()-1);
			p = rangey.split("\\,");
			Range ry = new Range(Double.valueOf(p[0]),Double.valueOf(p[1]));

			this.drawFunctions(width, height, rx, ry, resolution);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
