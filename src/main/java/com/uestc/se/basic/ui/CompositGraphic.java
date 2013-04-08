/**
 * 
 */
package com.uestc.se.basic.ui;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *
 */
public abstract class CompositGraphic implements Graphic{
	
	private List<Graphic> _children = new ArrayList<Graphic>();
	
//	public CompositGraphic() {
//		_children = new ArrayList<Graphic>();
//	}
	
	@Override
	public void add(Graphic g) {
		_children.add(g);
		
	}
	
	@Override
	public void remove(Graphic g) {
		if(_children.contains(g)){
			_children.remove(g);
		}
		
	}
	
	@Override
	public Graphic getChild(int index) {
		if(index < _children.size()){
			return _children.get(index);
		}
		return null;
	}
	
	@Override
	public void draw() {
		for(Graphic g : _children){
			g.draw();
		}
		
	}
}
