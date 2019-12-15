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

package JFSM;

/**
 * Transition.java
 *
 *
 * Created: 2017-08-25
 *
 * @author Emmanuel Desmontils
 * @version 1.0
 */

public class Transition implements Cloneable {
	static public int nb = 0;

	public int no;
	public String source, cible;
	public String symbol;

	public Transition(String s, String symbol, String c) throws JFSMException {
		if (symbol==null) throw new JFSMException("Un symbole ne peut pas �tre absent");
		if ((symbol.equals(""))||(symbol.equals("\u03b5"))) 
			throw new JFSMException("Un symbole ne peut pas �tre vide ou \u03b5");

		Transition.nb++;
		this.no = Transition.nb;

		this.source = s;
		this.cible = c;
		this.symbol = symbol;
	}

	public Transition(Etat s, String symbol, Etat c) throws JFSMException {
		this(s.name,symbol,c.name);
	}	

	public Transition(Transition t) throws JFSMException {
		this(t.source,t.symbol,t.cible);
	}	

	public Object clone() {
		Transition o = null;
		try {
			o = (Transition)super.clone();
			Transition.nb++;
			o.no = Transition.nb;
		} catch(CloneNotSupportedException cnse) {
			cnse.printStackTrace(System.err);
		}
		return o;
	}

	// public String toString(){return "("+no+") "+source+"-"+symbol+"->"+cible;}
	public String toString(){return source+" -"+symbol+"-> "+cible;}

	/** 
	* Modidifie l'�tat source de la transition  
	* @param s Le nom de l'�tat source
	*/
	public void changeSource(String s) {
		this.source = s;
	}
	/**
	 * changing the cible state of a transition
	 * @param s the name of new cible state
	 */
	public void changeCible(String s) {
		this.cible = s;
	}

	/** 
	* Indique si la transition peut �tre appliqu�e depuis cet �tat sur ce symbole.  
	* @param etat L'�tat courant
	* @param symbol Le symbol courant
	* @return bool�en � vrai si la transition est applicable, faux sinon
	*/
	public boolean candidate(String etat, String symbol) {
		return etat.equals(source) && symbol.equals(this.symbol);
	}

	/** 
	* Applique la transition.  
	* @return le nouvel �tat
	*/
	public String appliquer() {
		return cible ;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cible == null) ? 0 : cible.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Transition))
			return false;
		Transition other = (Transition) obj;
		if (cible == null) {
			if (other.cible != null)
				return false;
		} else if (!cible.equals(other.cible))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}
	

}