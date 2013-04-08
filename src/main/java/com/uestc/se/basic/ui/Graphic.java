/**
 * 
 */
package com.uestc.se.basic.ui;

/**
 * @author luchen
 *
 */
public interface Graphic {
	public void draw();
	public void add(Graphic graphic);
	public void remove(Graphic grahpic);
	public Graphic getChild(int index);
}
