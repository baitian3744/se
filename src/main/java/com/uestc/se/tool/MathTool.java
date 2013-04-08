package com.uestc.se.tool;


import java.util.Arrays;

public class MathTool {
	
	public static final float PI = (float)Math.PI;
	
	/**
	 * This method returns the modulo of a vector
	 * <ul><li>If null, throw NullPointerException //TODO
	 * <li>If the res</ul>
	 * 
	 * @param vector
	 * @return	the modulo of the given vector {@code vector}.
	 * 			
	 */
	public static float modulo( float[] vector ){
		float result = 0;
		
		// |vector|^2
		for(float tmp : vector){
			result += tmp*tmp;
		}
		
		// Return the |vector|
		return (float)Math.sqrt(result);
	}
	
	/**
	 * This method calculate the dot product (inner product) of two vectors
	 * <ul><li>If two vectors are not null and of same dimension, it returns the dot product
	 * <li>If not, returns NAN.</ul>
	 * @param v1 a vector of float[] type.
	 * @param v2 a vector of float[] type.
	 * @return the dot product of given two vectors.
	 */
	public static float dotProduct(float[] v1, float[] v2){
		float result = 0;
		
		// The vectors shouldn't be null, and the dimension should be same
		if((v1 == null) || (v2 == null) || (v1.length != v2.length)){
			return Float.NaN;
		}
		for(int i = 0; i < v1.length; i++){
			result += v1[i]*v2[i];
		}
		return result;
	}
	
	/**
	 * This method calculate the outer product of two 3-dimensional vectors
	 * <ul><li>If one of the vector is null, return null.
	 * <li>If one of the vector isn't 3-dimensional, return null.</ul>
	 * Otherwise return the closet float[] result outer product of the two vectors.
	 * @param v1 one 3-dimensional vector of type float[].
	 * @param v2 one 3-dimensional vector of type float[].
	 * @return the outer product of the given two vectors.
	 */
	public static float[] outerProduct(float[] v1, float[] v2){
		float[] result = {0.0f, 0.0f, 0.0f};
		
		// The vectors shouldn't be null, and the dimension should be 3
		if((v1 == null) || (v2 == null) || (v1.length != 3) || (v2.length != 3)){
			System.out.println("Err @MathTool.angle(): Vectors null or isn't 3-dimensional");
			System.out.println("	Info: vFrom = " + Arrays.toString(v1));
			System.out.println("	Info: vTo = " + Arrays.toString(v1));
			return null;
		}
		
		// result = v1 x v2
		result[0] = v1[1]*v2[2] - v1[2]*v2[1];
		result[1] = -(v1[0]*v2[2] - v1[2]*v2[0]);
		result[2] = v1[0]*v2[1] - v1[1]*v2[0];
		
		// return v1 x v2
		return result;
	}
	
	public static float acos(float r){
		return (float)Math.acos(r);
	}
	
	public static float cos(float r) {
		return (float)Math.cos(r);
	}
	
	public static float sin(float r) {
		return (float)Math.sin(r);
	}
	
	
	/**
	 * This method calculate the angle between two vectors, the given order is unrelated.
	 * <p>Algorithm: angle = theta>0 ? theta : 180+theta, 
	 * where theta = acos(a.*b/(|a|*|b|)), a & b are vectors.
	 * 
	 * <ul><li>If one of the vectors is null, return NAN.
	 * <li>If one of the vectors is zero vector, return NAN.
	 * <li>If the dimension of the two vectors are different, return NAN.</ul>
	 * Otherwise, the result is the float value closest to the true mathematical angle 
	 * between the two vectors.
	 * @param vFrom one vector of type float[].
	 * @param vTo one vector of type float[].
	 * @return the angle between given two vectors, in degree. The range is [0, 180].
	 */
	public static float angle(float[] vFrom, float[] vTo){
		float angle;
		float theta;
		
		// Vectors shouldn't be null 
		// & the dimension should be same
		if((vFrom == null) || (vTo == null) || (vFrom.length != vTo.length)){
			System.out.println("Err @MathTool.angle(): Vectors null or of different dimension");
			System.out.println("	Info: vFrom = " + Arrays.toString(vFrom));
			System.out.println("	Info: vTo = " + Arrays.toString(vTo));
			return Float.NaN;
		}
		
		// Get the modulo of two vectors
		float moduloFrom = modulo(vFrom);					// |vFrom|
		float moduloTo = modulo(vTo);						// |vTo|
		
		// Neither of the vectors should be zero vector
		if((moduloFrom == 0.0f) || (moduloTo == 0.0f)){
			System.out.println("Err @MathTool.angle(): Zero vector");
			System.out.println("	Info: vFrom = " + Arrays.toString(vFrom));
			System.out.println("	Info: vTo = " + Arrays.toString(vTo));
			return Float.NaN;
		}
		
		// Calculate the angle
		float dotProduct = dotProduct(vFrom, vTo);			// vFrom .* vTo
		theta = acos(dotProduct/(moduloFrom*moduloTo));
		theta = theta>0 ? theta : PI+theta;
		angle = theta*180.0f/PI;
		
		// Return the angle between the two vectors
		return angle;
	}
	
}
