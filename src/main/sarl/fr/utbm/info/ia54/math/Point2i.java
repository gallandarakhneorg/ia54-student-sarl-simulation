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

/** 2D Point with 2 integer numbers.
 *
 * Copied from {@link https://github.com/gallandarakhneorg/afc/}.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class Point2i extends Tuple2i<Point2i> {

	private static final long serialVersionUID = 8963319137253544821L;

	/**
	 */
	public Point2i() {
		//
	}

	/**
	 * @param tuple is the tuple to copy.
	 */
	public Point2i(Tuple2i<?> tuple) {
		super(tuple);
	}

	/**
	 * @param tuple is the tuple to copy.
	 */
	public Point2i(int[] tuple) {
		super(tuple);
	}

	/**
	 * @param x
	 * @param y
	 */
	public Point2i(int x, int y) {
		super(x,y);
	}

	/**
	 * Computes the square of the distance between this point and point p1.
	 * @param p1 the other point
	 * @return the distance.
	 */
	@Pure
	public float distanceSquared(Point2i p1) {
	      float dx, dy;
	      dx = this.x-p1.getX();  
	      dy = this.y-p1.getY();
	      return (dx*dx+dy*dy);
	}

	/**
	 * Computes the distance between this point and point p1.
	 * @param p1 the other point
	 * @return the distance. 
	 */    
	@Pure
	public float distance(Point2i p1) {
	      float  dx, dy;
	      dx = this.x-p1.getX();  
	      dy = this.y-p1.getY();
	      return (float)Math.sqrt(dx*dx+dy*dy);
	}

	/**
	 * Computes the L-1 (Manhattan) distance between this point and
	 * point p1.  The L-1 distance is equal to abs(x1-x2) + abs(y1-y2).
	 * @param p1 the other point
	 * @return the distance.
	 */
	@Pure
	public float distanceL1(Point2i p1) {
	      return (Math.abs(this.x-p1.getX()) + Math.abs(this.y-p1.getY()));
	}

	/**
	 * Computes the L-infinite distance between this point and
	 * point p1.  The L-infinite distance is equal to 
	 * MAX[abs(x1-x2), abs(y1-y2)]. 
	 * @param p1 the other point
	 * @return the distance.
	 */
	@Pure
	public float distanceLinf(Point2i p1) {
	      return Math.max( Math.abs(this.x-p1.getX()), Math.abs(this.y-p1.getY()));
	}

	/**
	 * Sets the value of this tuple to the sum of tuples t1 and t2.
	 * @param t1 the first tuple
	 * @param t2 the second tuple
	 */
	public void add(Point2i t1, Vector2i t2) {
		this.x = t1.getX() + t2.getX();
		this.y = t1.getY() + t2.getY();
	}

	/**
	 * Sets the value of this tuple to the sum of tuples t1 and t2.
	 * @param t1 the first tuple
	 * @param t2 the second tuple
	 */
	public void add(Vector2i t1, Point2i t2) {
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
	public void scaleAdd(int s, Vector2i t1, Point2i t2) {
		this.x = s * t1.getX() + t2.getX();
		this.y = s * t1.getY() + t2.getY();
	}

	/**
	 * Sets the value of this tuple to the scalar multiplication
	 * of tuple t1 plus tuple t2 (this = s*t1 + t2).
	 * @param s the scalar value
	 * @param t1 the tuple to be multipled
	 * @param t2 the tuple to be added
	 */
	public void scaleAdd(float s, Vector2i t1, Point2i t2) {
		this.x = (int) (s * t1.getX() + t2.getX());
		this.y = (int) (s * t1.getY() + t2.getY());
	}

	/**
	 * Sets the value of this tuple to the scalar multiplication
	 * of tuple t1 plus tuple t2 (this = s*t1 + t2).
	 * @param s the scalar value
	 * @param t1 the tuple to be multipled
	 * @param t2 the tuple to be added
	 */
	public void scaleAdd(int s, Point2i t1, Vector2i t2) {
		this.x = s * t1.getX() + t2.getX();
		this.y = s * t1.getY() + t2.getY();
	}

	/**
	 * Sets the value of this tuple to the scalar multiplication
	 * of tuple t1 plus tuple t2 (this = s*t1 + t2).
	 * @param s the scalar value
	 * @param t1 the tuple to be multipled
	 * @param t2 the tuple to be added
	 */
	public void scaleAdd(float s, Point2i t1, Vector2i t2) {
		this.x = (int) (s * t1.getX() + t2.getX());
		this.y = (int) (s * t1.getY() + t2.getY());
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
	 * Sets the value of this tuple to the scalar multiplication
	 * of itself and then adds tuple t1 (this = s*this + t1).
	 * @param s the scalar value
	 * @param t1 the tuple to be added
	 */
	public void scaleAdd(float s, Vector2i t1) {
		this.x = (int) (s * this.x + t1.getX());
		this.y = (int) (s * this.y + t1.getY());
	}

	
	/**
	 * Sets the value of this tuple to the difference
	 * of tuples t1 and t2 (this = t1 - t2).
	 * @param t1 the first tuple
	 * @param t2 the second tuple
	 */
	public void sub(Point2i t1, Vector2i t2) {
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
	
	/** {@inheritDoc}
	 */
	@Override
	@Pure
	public Point2i clone() {
		return (Point2i)super.clone();
	}
	
	/** Sum of vectors: r = this + v.
	 * 
	 * @param v the vector
	 * @return the result.
	 * @see #add(Point2i, Vector2f)
	 */
	@Pure
	public Point2i operator_plus(Vector2i v) {
		Point2i r = new Point2i();
		r.add(this, v);
		return r;
	}

	/** Sum of vectors: this += v.
	 * It is equivalent to {@code this.add(v)}.
	 * 
	 * @param v the vector
	 * @return the result.
	 * @see #add(Vector2f)
	 */
	@Inline(value = "$1.add($2)")
	public void operator_add(Vector2i v) {
		add(v);
	}

	/** Subtract of vectors: r = this - v.
	 * 
	 * @param v the vector
	 * @return the result.
	 * @see #sub(Point2i, Vector2f)
	 */
	@Pure
	public Point2i operator_minus(Vector2i v) {
		Point2i r = new Point2i();
		r.sub(this, v);
		return r;
	}

	/** Compute a vectors: r = this - p.
	 * 
	 * @param p the point
	 * @return the vector from the p to this.
	 * @see Vector2f#sub(Point2i, Point2i)
	 */
	@Pure
	public Vector2i operator_minus(Point2i p) {
		Vector2i r = new Vector2i();
		r.sub(this, p);
		return r;
	}

	/** Subtract of vectors: this -= v.
	 * It is equivalent to {@code this.sub(v)}.
	 * 
	 * @param v the vector
	 * @return the result.
	 * @see #sub(Vector2f)
	 */
	@Inline(value = "$1.sub($2)")
	public void operator_remove(Vector2i v) {
		sub(v);
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

	/** Replies if the distance between this and v
	 * 
	 * @param v the vector.
	 * @return the distance.
	 * @see #distance(Point2i)
	 */
	@Pure
	@Inline(value = "$1.distance($2)")
	public float operator_upTo(Point2i v) {
		return distance(v);
	}

	/** If the tuple is nul then b else a.
	 * 
	 * @param v the vector.
	 * @return the vector.
	 * @see #epsilonNul(float)
	 * @see MathUtil#EPSILON
	 */
	@Pure
	public Point2i operator_elvis(Point2i v) {
		if (epsilonNul(MathUtil.EPSILON)) {
			return v;
		}
		return this;
	}

}