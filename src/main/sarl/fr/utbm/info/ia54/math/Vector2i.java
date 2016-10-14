/* 
 * $Id$
 * 
 * Copyright (c) 2015 Stephane GALLAND <stephane.galland@utbm.fr>.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */
package fr.utbm.info.ia54.math;

import org.eclipse.xtext.xbase.lib.Inline;
import org.eclipse.xtext.xbase.lib.Pure;

/** 2D Vector with 2 integer values.
 *
 * Copied from {@link https://github.com/gallandarakhneorg/afc/}.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class Vector2i extends Tuple2i<Vector2i> {

	private static final long serialVersionUID = -2062941509400877679L;

	/**
	 */
	public Vector2i() {
		//
	}

	/**
	 * @param tuple is the tuple to copy.
	 */
	public Vector2i(Tuple2i<?> tuple) {
		this(tuple, false);
	}

	/**
	 * @param tuple is the tuple to copy.
	 * @param normalize
	 */
	public Vector2i(Tuple2i<?> tuple, boolean normalize) {
		super(tuple);
		if (normalize) {
			try {
				normalize();
			} catch (Throwable ex) {
				//
			}
		}
	}

	/**
	 * @param tuple is the tuple to copy.
	 */
	public Vector2i(int[] tuple) {
		this(tuple, false);
	}

	/**
	 * @param tuple is the tuple to copy.
	 * @param normalize
	 */
	public Vector2i(int[] tuple, boolean normalize) {
		super(tuple);
		if (normalize) {
			try {
				normalize();
			} catch (Throwable ex) {
				//
			}
		}
	}

	/**
	 * @param x
	 * @param y
	 */
	public Vector2i(int x, int y) {
		this(x,y,false);
	}

	/**
	 * @param x
	 * @param y
	 * @param normalize
	 */
	public Vector2i(int x, int y, boolean normalize) {
		super(x,y);
		if (normalize) {
			try {
				normalize();
			} catch (Throwable ex) {
				//
			}
		}
	}

	/**
	 * Sets the value of this tuple to the sum of tuples t1 and t2.
	 * @param t1 the first tuple
	 * @param t2 the second tuple
	 */
	public void add(Vector2i t1, Vector2i t2) {
		this.x = t1.getX() + t2.getX();
		this.y = t1.getY() + t2.getY();
	}

	/**
	 * Sets the value of this tuple to the sum of itself and t1.
	 * @param t1 the other tuple
	 */
	public void add(Vector2i t1) {
		this.x += t1.getX();
		this.y += t1.getY();
	}

	/**
	 * Sets the value of this tuple to the scalar multiplication
	 * of tuple t1 plus tuple t2 (this = s*t1 + t2).
	 * @param s the scalar value
	 * @param t1 the tuple to be multipled
	 * @param t2 the tuple to be added
	 */
	public void scaleAdd(int s, Vector2i t1, Vector2i t2) {
		this.x = s * t1.getX() + t2.getX();
		this.y = s * t1.getY() + t2.getY();
	}

	/**
	 * Sets the value of this tuple to the scalar multiplication
	 * of itself and then adds tuple t1 (this = s*this + t1).
	 * @param s the scalar value
	 * @param t1 the tuple to be added
	 */
	public void scaleAdd(int s, Vector2i t1) {
		this.x = s * this.x + t1.getX();
		this.y = s * this.y + t1.getY();
	}

	/**
	 * Sets the value of this tuple to the difference
	 * of tuples t1 and t2 (this = t1 - t2).
	 * @param t1 the first tuple
	 * @param t2 the second tuple
	 */
	public void sub(Vector2i t1, Vector2i t2) {
		this.x = t1.getX() - t2.getX();
		this.y = t1.getY() - t2.getY();
	}

	/**
	 * Sets the value of this tuple to the difference
	 * of tuples t1 and t2 (this = t1 - t2).
	 * @param t1 the first tuple
	 * @param t2 the second tuple
	 */
	public void sub(Point2i t1, Point2i t2) {
		this.x = t1.getX() - t2.getX();
		this.y = t1.getY() - t2.getY();
	}

	/**
	 * Sets the value of this tuple to the difference
	 * of itself and t1 (this = this - t1).
	 * @param t1 the other tuple
	 */
	public void sub(Vector2i t1) {
		this.x -= t1.getX();
		this.y -= t1.getY();
	}

	/**
	 * Computes the dot product of the this vector and vector v1.
	 * @param v1 the other vector
	 * @return the dot product.
	 */
	@Pure
	public float dot(Vector2i v1) {
		return (this.x*v1.getX() + this.y*v1.getY());
	}

	/** Change the coordinates of this vector to make it a perpendicular
	 * vector to the original coordinates.
	 */
	public void perpendicularize() {
		// Based on the cross product in 3D of (vx,vy,0)x(0,0,1), right-handed
		//set(getY(), -getX());
		// Based on the cross product in 3D of (vx,vy,0)x(0,0,1), left-handed
		set(-getY(), getX());
	}

	/**  
	 * Returns the length of this vector.
	 * @return the length of this vector
	 */  
	@Pure
	public float length() {
		return (float) Math.sqrt(this.x*this.x + this.y*this.y);
	}

	/**  
	 * Returns the squared length of this vector.
	 * @return the squared length of this vector
	 */  
	@Pure
	public float lengthSquared() {
		return (this.x*this.x + this.y*this.y);
	}
	
	/**  
	 * Set the length of this vector.
	 * @param length - the length of this vector
	 */  
	public void setLength(float length) {
		if (length >= 0f) {
			float norm = this.x*this.x + this.y*this.y;
			if (norm != 0f) {
				norm = (float)Math.sqrt(norm);
				norm = length / norm;
				this.x *= norm;
				this.y *= norm;
			} else {
				this.x = (int) length;
				this.y = 0;
			}
		} else {
			this.x = this.y = 0;
		}
	}

	/**
	 * Sets the value of this vector to the normalization of vector v1.
	 * @param v1 the un-normalized vector
	 */  
	public void normalize(Vector2i v1) {
		float norm = 1f / v1.length();
		this.x = (int)(v1.getX()*norm);
		this.y = (int)(v1.getY()*norm);
	}

	/**
	 * Normalizes this vector in place.
	 */  
	public void normalize() {
		float norm;
		norm = (float)(1./Math.sqrt(this.x*this.x + this.y*this.y));
		this.x *= norm;
		this.y *= norm;
	}


	@Override
	@Pure
	public Vector2i clone() {
		return (Vector2i) super.clone();
	}
	
	/** Sum of vectors: r = this + v.
	 * 
	 * @param v the vector
	 * @return the result.
	 * @see #add(Vector2i, Vector2i)
	 */
	@Pure
	public Vector2i operator_plus(Vector2i v) {
		Vector2i r = new Vector2i();
		r.add(this, v);
		return r;
	}

	/** Add vector to a point: r = this + p.
	 * 
	 * @param p the point
	 * @return the result.
	 * @see Point2i#add(Point2i, Vector2i)
	 */
	@Pure
	public Point2i operator_plus(Point2i p) {
		Point2i r = new Point2i();
		r.add(p, this);
		return r;
	}

	/** Sum of vectors: this += v.
	 * It is equivalent to {@code this.add(v)}.
	 * 
	 * @param v the vector
	 * @return the result.
	 * @see #add(Vector2i)
	 */
	@Inline(value = "$1.add($2)")
	public void operator_add(Vector2i v) {
		add(v);
	}

	/** Subtract of vectors: r = this - v.
	 * 
	 * @param v the vector
	 * @return the result.
	 * @see #sub(Vector2i, Vector2i)
	 */
	@Pure
	public Vector2i operator_minus(Vector2i v) {
		Vector2i r = new Vector2i();
		r.sub(this, v);
		return r;
	}

	/** Subtract of vectors: this -= v.
	 * It is equivalent to {@code this.sub(v)}.
	 * 
	 * @param v the vector
	 * @return the result.
	 * @see #sub(Vector2i)
	 */
	@Inline(value = "$1.sub($2)")
	public void operator_remove(Vector2i v) {
		sub(v);
	}

	/** Negation: r = -this.
	 * It is equivalent to {@code this.negate(r)}
	 * 
	 * @param v the vector
	 * @return the result.
	 * @see #negate(Vector2i)
	 */
	public Vector2i operator_minus() {
		Vector2i r = new Vector2i();
		negate(r);
		return r;
	}

	/** Dot product: r = this.v.
	 * 
	 * @param v the vector
	 * @return the result.
	 * @see #dot(Vector2i)
	 */
	@Pure
	@Inline(value = "$1.dot($2)")
	public float operator_multiply(Vector2i v) {
		return dot(v);
	}

	/** Scale a vector: r = this * f.
	 * 
	 * @param v the vector
	 * @return the scaled vector.
	 */
	@Pure
	public Vector2i operator_multiply(int f) {
		Vector2i r = new Vector2i(this);
		r.scale(f);
		return r;
	}

	/** Scale a vector: r = this / f.
	 * 
	 * @param v the vector
	 * @return the scaled vector.
	 */
	@Pure
	public Vector2i operator_divide(int f) {
		Vector2i r = new Vector2i(this);
		r.scale(1/f);
		return r;
	}

	/** Replies if the vectors are equal.
	 * 
	 * @param v the vector.
	 * @return test result.
	 */
	@Pure
	@Inline(value = "$1.equals($2)")
	public boolean operator_equals(Vector2i v) {
		return equals(v);
	}

	/** Replies if the vectors are not equal.
	 * 
	 * @param v the vector.
	 * @return test result.
	 */
	@Pure
	@Inline(value = "!$1.equals($2)")
	public boolean operator_notEquals(Vector2i v) {
		return !equals(v);
	}

	/** Replies if the vectors are equal.
	 * 
	 * @param v the vector.
	 * @return test result.
	 */
	@Pure
	@Inline(value = "$1.equals($2)")
	public boolean operator_tripleEquals(Vector2i v) {
		return equals(v);
	}

	/** Replies if the vectors are not equal.
	 * 
	 * @param v the vector.
	 * @return test result.
	 */
	@Pure
	@Inline(value = "!$1.equals($2)")
	public boolean operator_tripleNotEquals(Vector2i v) {
		return !equals(v);
	}

	/** If the tuple is nul then b else a.
	 * 
	 * @param v the vector.
	 * @return the vector.
	 * @see #epsilonNul(float)
	 * @see MathUtil#EPSILON
	 */
	@Pure
	public Vector2i operator_elvis(Vector2i v) {
		if (epsilonNul(MathUtil.EPSILON)) {
			return v;
		}
		return this;
	}
	
}