/**
 * 
 * Copyright (C) 2017 Emmanuel DESMONTILS
 * 
 * This library is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of the
 * License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 * 
 * 
 * 
 * E-mail:
 * Emmanuel.Desmontils@univ-nantes.fr
 * 
 * 
 **/

package JFSM ;

import java.util.ArrayList;

/**
 * Etat.java
 *
 *
 * Created: 2017-08-25
 *
 * @author Emmanuel Desmontils
 * @version 1.0
 */

public class Etat  implements Cloneable {
	static public int nb = 0;

	public int no;
	public String name;

	public Etat(String n) {
		this.name = n;
		Etat.nb++;
		this.no = Etat.nb;
	}

	public String toString() {
		return this.name;//+'('+this.no+')';
	}

	public void rename(String newName) {
		this.name = newName ;
	}
	
	public Object clone() {
		Etat o = null;
		try {
			o = (Etat)super.clone();
			Etat.nb++;
			o.no = Etat.nb;
		} catch(CloneNotSupportedException cnse) {
			cnse.printStackTrace(System.err);
		}
		return o;
	}
	
	 /**
	  * Method that verifies whether the state is utile or not
	  * @return (true or false)
	  */
	public boolean estUtile(Automate af) {
		
		
		return (af.isAccessible(this.name) && af.isCoaccessible(this.name));
	}
	
	/**
	 * Method that delete a state after deleting all it's transition
	 */
	public void removeEtat(Automate afn) {
		//making an ArrayList of all the transitions
		ArrayList<Transition> l_trans = new ArrayList<Transition>();
		l_trans.addAll(afn.mu);
		
		//looping all of the transitions and deleting the transition that include that state that will be removed
		for(int i=0 ; i < l_trans.size() ; i++) {
			Transition temp = l_trans.get(i);
			if(temp.source.equals(this.toString())  || temp.cible.equals(this.toString())){
				afn.mu.remove(temp);
			}
			
		}
		
		afn.Q.remove(this.toString());
		
		if(afn.F.contains(this.toString())) { afn.F.remove(this.toString());}
		if(afn.I.contains(this.toString())) { afn.I.remove(this.toString());}

		
	}
	
	/**
	 * Method to check whether this state is the source of any transition
	 * @param afn the automate of the state
	 * @return true if the state is a source or not if it's isn't
	 */
	public boolean isSource(Automate afn) {
		boolean ok = false;
		//making an ArrayList of all the transitions
		ArrayList<Transition> l_trans = new ArrayList<Transition>();
		l_trans.addAll(afn.mu);
		
		int i =0;
		while(i < l_trans.size() && !ok) {
			if(l_trans.get(i).source.equals(this.toString())) {
				ok = true;
			}
			i++;
		}
		return ok;
	}
	
	/**
	 * Method  to check whether this state is the cible of any transition 
	 * @param afn  the automate of the state
	 * @return   true if the state is a cible or not if it's isn't
	 */
	public boolean isCible(Automate afn) {
		boolean ok = false;
		//making an ArrayList of all the transitions
		ArrayList<Transition> l_trans = new ArrayList<Transition>();
		l_trans.addAll(afn.mu);
		
		int i =0;
		while(i < l_trans.size() && !ok) {
			if(l_trans.get(i).cible.equals(this.toString())) {
				ok = true;
			}
			i++;
		}
		return ok;
	}
}