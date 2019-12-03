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


/**
 * Automate.java
 * 
 *
 * Created: 2017-08-25
 *
 * @author Emmanuel Desmontils
 * @version 1.0
 */

package JFSM;

import java.util.Set;
import java.util.HashSet;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;

import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Stack;

import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import javax.xml.parsers.SAXParserFactory; 
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.File;

public class Automate implements Cloneable {
	public Map<String,Etat> Q;
	public Set<String> F, I;
	public Set<String> A;
	public Stack<Transition> histo;
	public Set<Transition> mu;
	protected String current;

	/** 
	* Constructeur de l'automate {A,Q,I,F,mu}
	* @param A l'alphabet de l'automate (toute cha�ne de carat�res non vide et diff�rente de \u03b5)
	* @param Q l'ensemble des �tats de l'automate
	* @param I l'ensemble des �tats initiaux de l'automate
	* @param F l'ensemble des �tats finaux de l'automate
	* @param mu la fonction de transition de l'automate
	* @exception JFSMException Exception si un �tat qui n'existe pas est ajout� comme �tat initial ou final
	*/
	public Automate(Set<String> A, Set<Etat> Q, Set<String> I, Set<String> F, Set<Transition> mu) throws JFSMException {
		// Ajout de l'alphabet
		assert A.size()>0 : "A ne peut pas �tre vide" ;
		for(String a : A) {
			if ((a=="")||(a=="\u03b5")) throw new JFSMException("Un symbole ne peut pas �tre vide ou \u03b5");
		}
		this.A = A;
		this.mu = new HashSet<Transition>();

		// Ajout des �tats
		assert Q.size()>0 : "Q ne peut pas �tre vide" ;
		this.Q = new HashMap<String,Etat>();

		for (Etat e : Q)
			if (this.Q.containsKey(e.name)) System.out.println("Etat dupliqu� ! Seule une version sera conserv�e.");
			else this.Q.put(e.name,e); 
		
		// Cr�ation de l'historique (chemin)
		this.histo = new Stack<Transition>();

		// Ajout des transitions
		this.mu.addAll(mu);

		// On collecte les �tats initiaux, on les positionne comme tel. S'il n'existe pas, il est oubli�.
		// assert I.size()>0 : "I ne peut pas �tre vide" ;
		this.I = new HashSet<String>();
		for (String i : I) setInitial(i);

		// On collecte les �tats finaux, on les positionne comme tel. S'il n'existe pas, il est oubli�.
		this.F = new HashSet<String>();
		for(String f : F) setFinal(f);
	}

	public Object clone() {
		Automate o = null;
		try {
			o = (Automate)super.clone();
			// o.Q = (Map<String,Etat>) ((HashMap<String,Etat>)Q).clone() ;
			o.Q = new HashMap<String,Etat>();
			for(Etat e : this.Q.values()) {
				o.addEtat((Etat)e.clone());
			}
			o.F = (Set<String>)  ((HashSet<String>)F).clone();
			o.I = (Set<String>)  ((HashSet<String>)I).clone();
			o.A = (Set<String>)  ((HashSet<String>)A).clone();
			o.histo = (Stack<Transition>) ((Stack<Transition>)histo).clone();
			//o.mu = (Set<Transition>) ((HashSet<Transition>)mu).clone();
			o.mu = new HashSet<Transition>();
			for(Transition t : this.mu) {
				o.addTransition((Transition)t.clone());
			}
		} catch(CloneNotSupportedException cnse) {
			cnse.printStackTrace(System.err);
		}
		return o;
	}

	public String toString() {
		String s = "{ A={ ";
		for(String a : A ) s = s + a + " ";
		s = s + "} Q={ ";
		for(Etat q : Q.values() ) s = s + q + " ";
		s = s + "} I={ " ;
		for(String q : I ) s = s + q + " ";
		s = s + "} F={ " ;
		for(String q : F ) s = s + q + " ";
		s = s + "} \n   mu={ \n" ;
		for(Transition t : mu ) s = s + "\t"+ t + "\n";
		s = s + "   }\n}" ;

		return s ;
	}

	/** 
	* Ajoute une transition � mu.  
	* @param t transition � ajouter
	*/
	public void addTransition(Transition t) {
		mu.add(t);
	}

	/** 
	* Ajoute un �tat � Q.  
	* @param e L'�tat
	*/
	public void addEtat(Etat e){
		if (!Q.containsKey(e.name))
			Q.put(e.name,e);
	}

	/** 
	* Retrouve un �tat par son nom.  
	* @param n Le nom de l'�tat 
	* @return l'�tat retrouv�, null sinon
	*/
	public Etat getEtat(String n) {
		if (Q.containsKey(n))
			return Q.get(n);
		else return null;
	}

	/** 
	* Fixe le vocabulaire de l'automate.  
	* @param A la vocabulaire 
	*/
	public void setA(Set<String> A){
		this.A = A;
	}

	/** 
	* Indique qu'un �tat (par son nom) est un �tat initial.  
	* @param e Le nom de l'�tat
	* @exception JFSMException Si l'�tat est absent
	*/
	public void setInitial(String e) throws JFSMException {	
		if (Q.containsKey(e)) {
			I.add(e);
		} else throw new JFSMException("Etat absent:"+e);
	}

	/** 
	* Indique qu'un �tat est un �tat initial.  
	* @param e L'�tat
	* @exception JFSMException Si l'�tat est absent
	*/
	public void setInitial(Etat e) throws JFSMException {	
		setInitial(e.name);
	}

	/** 
	* Indique qu'un �tat (par son nom) est un �tat final.  
	* @param e Le nom de l'�tat
	* @exception JFSMException Si l'�tat est absent
	*/
	public void setFinal(String e) throws JFSMException {	
		if (Q.containsKey(e)) {
			F.add(e);
		} else throw new JFSMException("Etat absent:"+e);
	}

	/** 
	* Indique qu'un �tat est un �tat final.  
	* @param e L'�tat
	* @exception JFSMException Si l'�tat est absent
	*/
	public void setFinal(Etat e) throws JFSMException {	
		setFinal(e.name);
	}

	/** 
	* D�termine si un �tat (par son nom) est un �tat initial.  
	* @param e Le nom de l'�tat
	* @return vrai si initial, faux sinon
	*/
	public boolean isInitial(String e){
		assert Q.containsKey(e) : "isInitial : l'�tat doit �tre un �tat de l'automate." ;
		return I.contains(e);
	}

	/** 
	* D�termine si un �tat est un �tat initial.  
	* @param e L'�tat
	* @return vrai si initial, faux sinon
	*/
	public boolean isInitial(Etat e){
		return isInitial(e.name);
	}

	/** 
	* D�termine si un �tat (par son nom) est un �tat final.  
	* @param e Le nom de l'�tat
	* @return vrai si final, faux sinon
	*/
	public boolean isFinal(String e){
		assert Q.containsKey(e) : "isFinal : l'�tat doit �tre un �tat de l'automate." ;
		return F.contains(e);
	}

	/** 
	* D�termine si un �tat est un �tat final.  
	* @param e L'�tat
	* @return vrai si final, faux sinon
	*/
	public boolean isFinal(Etat e){
		return isFinal(e.name);
	}

	/** 
	* Initialise l'ex�cution de l'automate.  
	*/
	public void init() {
		histo.clear();
	}

	/** 
	* Indique si l'automate est dans un �tat final.  
	* @return vrai si final, faux sinon
	*/
	public boolean accepte(){return isFinal(current);}

	/** 
	* Indique si l'automate est epsilon-libre.  
	* @return vrai si e-libre, faux sinon
	*/
	public boolean epsilonLibre(){
		boolean ok = true ;
		for(Transition t : mu) {
			if (t instanceof EpsilonTransition) {
				ok = false;
				break;
			}
		}
		return ok;
	}

	/** 
	* Supprime les �tats qui ne sont pas utiles (accessible et co-accessible)  
	* @return un automate �quivalent utile (tous les �tats sont utiles)
	*/
	public Automate emonder() {
		
		Automate afn = (Automate) this.clone();
		
		//Getting a Collection of values from Map 
		Collection<Etat> values = this.Q.values();
		
		//Creating an ArrayList of values 
		ArrayList<Etat> l_etat = new ArrayList<Etat>(values);
		
		for(int i=0 ; i < l_etat.size(); i++) {

			if(!(l_etat.get(i).estUtile(this))){
				l_etat.get(i).removeEtat(this);

			}
		}
		
		

		// A compl�ter (complete the Method delete() in Etat)

		return afn;
	}

	/** 
	* D�termine si l'automate est utile  
	* @return bool�en
	*/
	public boolean estUtile() {
		System.out.println("estUtile() ");
		boolean ok = true;
		
		//Getting a Collection of values from Map 
		Collection<Etat> values = this.Q.values();
		
		//Creating an ArrayList of values 
		ArrayList<Etat> l_etat = new ArrayList<Etat>(values);
		
		int i = 0;
		
		//checking if all states are utile
		while( i < l_etat.size() && ok) {
			
			ok = l_etat.get(i).estUtile(this);
			i += 1;
		}
		System.out.println("the automate is utile : ");
		return ok;
	}
	

	// new methode that returns true if the state is accessible

	
	/**
	 * Method that verifies whether the state is Accessible or not
	 * @param name (the name of state)
	 * @return  (true or false)
	 */

	public boolean isAccessible(String name) {
		
		if(this.isInitial(name)) {
			return true;
		}else{
		
			//creating a list of all the state that are accessible from the initial state
			ArrayList<String> l_etatSuivant = new ArrayList<String>();
		
			//creating a list of all the transitions
			ArrayList<Transition> l_trans = new ArrayList<Transition>();
			l_trans.addAll(mu);
		
			//while to add all the cibles to the list l_cible
			for(int h=0 ; h <= l_trans.size() ; h++) {
				for(int i=0 ; i < l_trans.size() ; i++) {
					Transition temp = l_trans.get(i);
					if((this.isInitial(temp.source))) {   //adding the state if the source is initial 
						
						if(!(l_etatSuivant.contains(temp.cible))){ // and if the state does not already exist in l_etatSuivant
							l_etatSuivant.add(temp.cible);
						}
																						
						l_trans.remove(i); //deleting the transitions that have been treated

					}
				}
				for(int i=0 ; i < l_trans.size() ; i++) {
					Transition temp = l_trans.get(i);
					if(l_etatSuivant.contains(temp.source)){  // adding the state if we can access it from the initial state through 
												              // another state(s) and if the state does not already exist in l_etatSuivant
						if(!(l_etatSuivant.contains(temp.cible))) {
							l_etatSuivant.add(temp.cible);
						}
						l_trans.remove(i);  //deleting the transitions that have been treated
					}
				}
			}

			
			//return true if the state is accessible 	
			return(l_etatSuivant.contains(name));

		}

		}
	
	
	/**
	 * a method that checks if a given state is CoAccessible by giving it it's name .
	 * @param name
	 * @return boolean
	 */
	public boolean isCoaccessible(String name) {
		
		if(this.isFinal(name)) {
			return true;
		}else {
		
			//list containing all the states in the automata that are connected to the final state
			ArrayList<String> l_etatprec = new ArrayList<String>();
			
			//list of all the transitions in the automate
			ArrayList<Transition> l_trans = new ArrayList<Transition>();
			l_trans.addAll(mu);
			
			
			while(!(l_trans.isEmpty())){
				
				for(int i = 0; i < l_trans.size(); i++) {
					Transition temp = l_trans.get(i);
					
					//adds the source of the transition that has a final state as it's cible. the second condition is to avoid repetition. 
					if((this.isFinal(temp.cible) && !(l_etatprec.contains(temp.source)))){
						l_etatprec.add(temp.source);
					}
					
					//adds the source of the transition if it's cible is in the list. the second condition is to avoid repetition. 
					if(l_etatprec.contains(temp.cible) && !(l_etatprec.contains(temp.source))){
						l_etatprec.add(temp.source);
					}
					
					l_trans.remove(i);
					
				}
			}
			
			return l_etatprec.contains(name);
		}
	}

	/** 
	* Permet de transformer l'automate en un automate standard  
	* @return un automate �quivalent standard
	*/
	public Automate standardiser() {
		
		Automate afn = (Automate) this.clone();
		
		if (!(afn.estStandard())) {
			
			//adding the new initial state
			afn.addEtat(new Etat("initial"));
			
			
			
			ArrayList<Transition> l_t = new ArrayList<Transition>();
			l_t.addAll(afn.mu);
			
			for(int i = 0; i < l_t.size(); i++) {
				String source = l_t.get(i).source;
				String cible = l_t.get(i).cible;
				
				if(afn.isInitial(source)) {		
					try {
						//created a temporary transition with the source as the new initial state which will be added to the set of transitions
						if(!(afn.isInitial(cible))){
							Transition temp;
							temp = new Transition("initial", l_t.get(i).symbol, l_t.get(i).cible);
							
							//XXX : there are duplicates in the print of the set of transitions for some reason (maybe try addTransition)
							
							//adding the temporary transition if not already present 
							if(!(afn.mu.contains(temp))) {
								afn.mu.add(temp);
							}
						
						}
						
						//removing the original transition with the old initial state as source.
						afn.mu.remove(l_t.get(i));
						
						
						
						
					} catch (JFSMException e) {
							System.out.println("error while adding a new transition with the new initial state error : " + e);
					}
					
				}
				
				if(afn.isInitial(cible)) {
					
					//removing the original transition with the old initial state as cible.
					afn.mu.remove(l_t.get(i));
				}
				
			}
			
			//removing all the old initial states from the set Q
			ArrayList<String> l_initial = new ArrayList<String>();
			l_initial.addAll(afn.I);
			
			for(int i = 0; i < l_initial.size(); i++) {
				afn.Q.remove(l_initial.get(i));
			}
			
			//clearing the old initial states set
			afn.I.clear();
			
			try {
				afn.setInitial("initial");
			} catch (JFSMException e) {
				System.out.println("failed to set the new initial state as initial : " + e);
			
				
		}
		
		
		
		}
		return afn;
	}
		
		

		
	

	/** 
	* D�termine si l'automate est standard  
	* @return bool�en
	*/
	public boolean estStandard() {
		
		boolean ok = true;
		
		//case where there is more than one initial state
		if(this.I.size() > 1) {
			ok = false;
		}
		
		int i = 0;
		ArrayList<Transition> l_trans = new ArrayList<Transition>() ;
		l_trans.addAll(this.mu);
		ArrayList<String> l_initial = new ArrayList<String>();
		l_initial.addAll(this.I);
		
		//loop to check if there is any transitions in which the target is the initial state.
		while( i < this.mu.size() && ok ) {
			String cible = l_trans.get(i).cible; 
			if(cible.equals(l_initial.get(0))) {
				ok = false;
			}
			i += 1;
		}

		// A compl�ter
		
		return ok;
	}

	/** 
	* Permet de transformer l'automate en un automate normalis�  
	* @return un automate �quivalent normalis�
	*/
	public Automate normaliser() {
		
		Automate afn = (Automate) this.clone();
		
		if(!(afn.estNormalise())) {
			System.out.println("before");
			// making the automate standard
			afn.standardiser();
			System.out.println("after");
			
			//creating a new  state
			Etat finale = new Etat("finale");
			this.addEtat(finale);
			
			// changing every cible of each transition from the old final states to the new state
			// making an ArrayList of all the transition
			ArrayList<Transition> l_t = new ArrayList<Transition>();
			l_t.addAll(this.mu);
			
			for(int i = 0 ; i < l_t.size() ; i++) {
				if(this.F.contains(l_t.get(i).cible)) {
					this.mu.remove(l_t.get(i));
					if(l_t.get(i) instanceof EpsilonTransition) {
						try {
							EpsilonTransition temp = new EpsilonTransition(l_t.get(i).source , finale.toString());
							this.mu.add(temp);
						} catch (JFSMException e) {
							System.out.println("Can not make the new transition + e");
						}
					}else {
						try {
							Transition temp = new Transition(l_t.get(i).source , l_t.get(i).symbol , finale.toString());
							this.mu.add(temp);
						} catch (JFSMException e) {
							System.out.println("Can not make the new transition + e");
						}
					}
				}
			}
			/*
			//making a temporary set of transition
			Set<Transition> temp = new HashSet<Transition>(l_t);
			
			//Updating mu with the new values from temp
			this.mu = temp;
			*/
			//Deleting the old final states 
			this.F.clear();
			try {
				this.setFinal(finale);
			} catch (JFSMException e) {
				System.out.println("Can not make the new final state (finale a finale because it's missing) + e");
			}
			
			//Making the afn emonder
			
			afn.emonder();
		}
		return afn;
	}

	/** 
	* D�termine si l'automate est normalis�  
	* @return bool�en
	*/
	public boolean estNormalise() {
		
		if(this.estStandard()) {
			if(this.F.size() == 1) {
				//making an ArrayList of the set F
				ArrayList<String> l_finale = new ArrayList<String>();
				l_finale.addAll(this.F);
				
				if(!(this.getEtat(l_finale.get(0)).isSource(this))) {
					System.out.println("This automate is normalise");
					return true;
				}else {
					System.out.println("the automate is not normalise because the finale state is a source of a transition");
					return false;
				}
				
			}else {
				System.out.println("the automate is not normalise because it has more then one state finale");
				return false;
			}
			
		}else {
			System.out.println("the automate is not normalise because it's not standard");
			return false;
		}
	}

	/** 
	* Construit un automate reconnaissant le produit du langage de l'automate avec celui de "a" : L(this)xL(a)
	* @param a un Automate
	* @return un automate reconnaissant le produit
	*/
	public Automate produit(Automate a) {
		System.out.println("produit() : m�thode non impl�ment�e");
		return a;
	}

	/** 
	* Construit un automate reconnaissant le langage de l'automate � l'�toile : L(this)*
	* @return un automate reconnaissant la mise � l'�toile
	*/
	public Automate etoile() {
		System.out.println("etoile() : m�thode non impl�ment�e");
		Automate afn = (Automate) this.clone();
		
		//making the automate standard
		afn = afn.standardiser();
		
		//Copying the transition of the initial state to the final state
		
		ArrayList<String> l_initial = new ArrayList<String>(afn.I); //making an ArrayList of the initial state
		ArrayList<Transition> l_t = new ArrayList<Transition>(afn.mu); //making an ArrayList of all the transition
		ArrayList<String> l_final = new ArrayList<String>(afn.F); //making an ArrayList of the final states
		
		for(int t=0 ; t < l_t.size() ; t++) {
			if(l_initial.contains(l_t.get(t).source)) {
				for(int f=0 ; f < l_final.size() ; f++) {
					try {
						if(l_t.get(t) instanceof EpsilonTransition) {
							EpsilonTransition temp = new EpsilonTransition(l_final.get(f),l_t.get(t).cible);
							afn.addTransition(temp);
						}else {
						Transition temp = new Transition(l_final.get(f),l_t.get(t).symbol,l_t.get(t).cible);
						afn.addTransition(temp);
						}
						
					} catch (JFSMException e) {
						System.out.println("Can't make the new transition" + e);
					}
				}
				
			}
		}
		
		//Making the initial state into a final state
		try {
			afn.setFinal(l_initial.get(0));
		} catch (JFSMException e) {
			System.out.println("Can't make the initial state to a final state" + e);
		}
		return afn;
	}
	
	/**
	 * a method that gives back the cible when it is given a symbol and a source. if the cible is composed of several states it will return a string of all states ex: [1, 4, 2].
	 * @param source str
	 * @param symbol str
	 * @return ArrayList<String>
	 */
	public ArrayList<String> get_cible(String source, String symbol){
		ArrayList<String> l_cible = new ArrayList<String>();
		
		ArrayList<Transition> l_trans = new ArrayList<Transition>();
		l_trans.addAll(this.mu);
		
		for(int i =0; i < l_trans.size(); i++) {
			if(l_trans.get(i).source.equals(source) && l_trans.get(i).symbol.equals(symbol)) {
				l_cible.add(l_trans.get(i).cible);
			}
		}
		
		if(l_cible.size() > 1) {
			String A = l_cible.toString();
			l_cible.clear();
			l_cible.add(A);
		}
		
		return l_cible;
		
	}

	/** 
	* Construit un automate reconnaissant l'union du langage de l'automate avec celui de "a" : L(this) U L(a)
	* @param a un Automate
	* @return un automate reconnaissant l'union
	*/
	public Automate union(Automate a) {
		System.out.println("union() : m�thode non impl�ment�e");
		return a;
	}

	/** 
	* Construit un automate reconnaissant l'intersection du langage de l'automate avec celui de "a" 
	* @param a un Automate
	* @return un automate reconnaissant l'intersection
	*/
	public Automate intersection(Automate a) {
		System.out.println("intersection() : m�thode non impl�ment�e");
		
		return a;
	}

	/** 
	* Construit un automate reconnaissant le compl�mentaire du langage 
	* @return un automate reconnaissant le compl�mentaire
	*/
	public Automate complementaire() {
		System.out.println("compl�mentaire() : m�thode non impl�ment�e");
		//making the automate complet
		this.complet();
		
		//the final states become normal and making every state that is not originally final into a final state
		ArrayList<String> l_finale = new ArrayList<String>(); //making an ArrayList of the set F
		l_finale.addAll(this.F);
		
		this.F.clear();  //Emptying the set F
		
		ArrayList<Etat> l_etat = new ArrayList<Etat>(this.Q.values()); //making an ArrayList of all the  states
		
		for(int i=0 ; i < l_etat.size() ; i++) {
			if(!(l_finale.contains(l_etat.get(i).toString()))) {
				try {
					this.setFinal(l_etat.get(i));
				} catch (JFSMException e) {
					System.out.println("Can not make the new final state (finale) because it's missing + e");
				}
			}
		}
		
		
		return this;
	}

	/** 
	* Construit un automate complet
	* @return l'automate complet
	*/
	public Automate complet() {
		
		if(this.estComplet()) {
		return this;
		}else {
			// adding new state
			Etat puit = new Etat("puit");
			this.addEtat(puit);
			
			ArrayList<Etat> l_etat = new ArrayList<Etat>(this.Q.values()); //making an ArrayList of all the  states
			
			//making an ArrayList of all the transitions
			ArrayList<Transition> l_t = new ArrayList<Transition>();
			l_t.addAll(this.mu);
			
			//making an ArrayList of all the alphabetic 
			ArrayList<String> l_alpha = new ArrayList<String>();
			l_alpha.addAll(this.A);
			
			//creating an ArrayList of the symbol used by a state
			ArrayList<String> l_s = new ArrayList<String>();
			
			for(int i=0; i < l_etat.size() ; i++) {
				
				for(int h=0; h < l_t.size(); h++) {
					//filling l_s
					if(l_t.get(h).source.equals(l_etat.get(i).toString())) {
						l_s.add(l_t.get(h).symbol);
						
					}	
				}
				for(int alpha = 0 ; alpha < l_alpha.size() ; alpha++) {  //adding the missing transition whit the cible "comp"
					if(!(l_s.contains(l_alpha.get(alpha)))) {            
						try {
							this.addTransition(new Transition(l_etat.get(i).toString() , l_alpha.get(alpha) , "puit"));
						} catch (JFSMException e) {
							System.out.println("Can not make the new transition + e");
						}
					}
				}
				
				l_s.clear();  //clearing the list of symbols
				
				
			}
			
		
			
			return this;
		}
		
	}

	/** 
	* Teste si un automate est complet
	* @return bool�en
	*/
	public boolean estComplet() {
		boolean ok = true;
		//Getting a Collection of values from Map 
		Collection<Etat> values = this.Q.values();
		
		//Creating an ArrayList of values 
		ArrayList<Etat> l_etat = new ArrayList<Etat>(values);
		
		//list of transitions
		ArrayList<Transition> trans = new ArrayList<Transition>();
		trans.addAll(mu);
		
		int i = 0;
		while(i < l_etat.size() && ok) {
			Set<String> langCandidate =  new HashSet<String>();
			Etat state = l_etat.get(i);
			//going through all the transitions to check what symbols a state consumes
			for(int j = 0; j < trans.size(); j++) {
				Transition t = trans.get(j);
				if(t.source.equals(state.name)) {
					langCandidate.add(trans.get(j).symbol);
				}
			}
			if (!(langCandidate.equals(this.A))) {
				ok =  false;
			}
			i += 1;
		}
		
		
		return ok;

	}

	/** 
	* Construit un automate reconnaissant le langage transpos�
	* @return l'automate complet
	*/
	public Automate transpose() {
		System.out.println("transpose() : m�thode non impl�ment�e");
		
		//Swapping the source and the cible of each transition
		ArrayList<Transition> l_t = new ArrayList<Transition>(); //making an ArrayList of all the transitions
		l_t.addAll(this.mu);
		
		for(int i = 0 ; i < l_t.size() ; i++) {
			String tempEtat = l_t.get(i).source;
			l_t.get(i).changeSource(l_t.get(i).cible);
			l_t.get(i).changeCible(tempEtat);
		}
		
		//making a temporary set of transition
		Set<Transition> temp = new HashSet<Transition>(l_t);
				
		//Updating this.mu with the new values from temp
		this.mu = temp;
		
		//the final states become initial states
		this.I.clear(); //Clearing the initial states
		ArrayList<String> l_etatF = new ArrayList<String>(); //making an ArrayList of all the final states
		l_etatF.addAll(this.F);
		for(int i = 0 ; i < l_etatF.size() ; i++) {
			try {
				this.setInitial(l_etatF.get(i));
			} catch (JFSMException e) {
				System.out.println("Can not make the new final state (finale a finale because it's missing) + e");
			}
		}
		
		//Making every state that isn't an initial state a final state
		this.F.clear();
		ArrayList<Etat> l_etat = new ArrayList<Etat>(this.Q.values()); //making an ArrayList of all the  states
		
		for(int i=0 ; i < l_etat.size() ; i++) {
			if(!(this.I.contains(l_etat.get(i).toString()))) {
				try {
					this.setFinal(l_etat.get(i));
				} catch (JFSMException e) {
					System.out.println("Can not make the new final state (finale a finale because it's missing) + e");
				}
			}
		}
		return this;
	}

	/** 
	* D�termine des transitions possibles que peut emprunter l'automate en fonction de l'�tat courant et du symbole courant
	* @param symbol le symbole
	* @exception JFSMException Exception lev�e si la m�thode n'est pas impl�ment�e
	* @return la liste des transitions possibles 
	*/
	public Queue<Transition> next(String symbol) throws JFSMException  {
		throw new JFSMException("M�thode next non impl�ment�e");
	}

	/** 
	* Ex�cute l'automate sur un mot (une liste de symboles)
	* @param l la liste de symboles
	* @return un bool�en indiquant sur le mot est reconnu 
	* @exception JFSMException Exception lev�e si la m�thode n'est pas impl�ment�e
	*/
	public boolean run(List<String> l) throws JFSMException  {
		throw new JFSMException("M�thode run non impl�ment�e");
	}

	/** 
	* Enregistre un automate sous le format XML "JFLAP 4".
	* @param file le nom du fichier
	*/
	public void save(String file) {
		try{
			File ff=new File(file); 
			ff.createNewFile();
			FileWriter ffw=new FileWriter(ff);
			ffw.write("<?xml version='1.0' encoding='UTF-8' standalone='no'?><!--Created with JFSM.--><structure>\n");  
			ffw.write("\t<type>fa</type>\n"); 
			ffw.write("\t<automaton>\n");
			for(Etat e : Q.values()) {
				ffw.write("\t\t<state id='"+e.no+"' name='"+e.name+"'>\n\t\t\t<x>0</x>\n\t\t\t<y>0</y>\n");
				if (isInitial(e.name)) ffw.write("\t\t\t<initial/>\n");
				if (isFinal(e.name)) ffw.write("\t\t\t<final/>\n");
				ffw.write("\t\t</state>\n");
			}
			for(Transition t : mu){
				ffw.write("\t\t<transition>\n");
				Etat from = getEtat(t.source);
				ffw.write("\t\t\t<from>"+from.no+"</from>\n");
				Etat to = getEtat(t.cible);
				ffw.write("\t\t\t<to>"+to.no+"</to>\n");
				if (!(t instanceof EpsilonTransition)) ffw.write("\t\t\t<read>"+t.symbol+"</read>\n");
				else ffw.write("\t\t\t<read/>\n");
				ffw.write("\t\t</transition>\n");
			}
			ffw.write("\t</automaton>\n");
			ffw.write("</structure>\n"); 
			ffw.close(); 
		} catch (Exception e) {}
	}

	/** 
	* Charge un automate construit avec JFLAP au format XML "JFLAP 4".
	* @param file le nom du fichier
	* @return un automate 
	*/
	static public Automate load(String file) {
		JFLAPHandler handler = new JFLAPHandler();
		try {
			XMLReader saxParser = XMLReaderFactory.createXMLReader();
			saxParser.setContentHandler(handler);
			saxParser.setErrorHandler(handler);
			saxParser.parse( file ); 
		} catch (Exception e) {
			System.out.println("Exception captur�e : ");
			e.printStackTrace(System.out);
			return null;
		}
		try {
			if (AFD.testDeterminisme(handler.res) ) 
				return new AFD(handler.res) ;
				else return new AFN(handler.res) ;
		} catch (JFSMException e) {return null;}
	}

}

class JFLAPHandler extends DefaultHandler {
	String cdc ;
	public Set<Etat> Q;
	public Set<String> F, I;
	public Set<String> A;
	public Set<Transition> mu;
	private Etat e;
	private Transition t;
	private String from, to, read, state_name, state_id;
	private boolean final_state, initial_state;
	public Automate res ;

	public JFLAPHandler() {super();}

	public void characters(char[] caracteres, int debut, int longueur) {
		cdc = new String(caracteres,debut,longueur);
	}

	public void startDocument() {
		cdc="";
		A = new HashSet<String>();
		I = new HashSet<String>();
		F = new HashSet<String>();
		Q = new HashSet<Etat>();
		mu = new HashSet<Transition>();
		res = null;
	}

	public void endDocument() {
		try{
			res = new Automate(A,Q,I,F,mu);
		} catch (JFSMException e) {
				System.out.println("Erreur:"+e);
		}
	}

	public void startElement(String namespaceURI, String sName, String name, Attributes attrs) {
		if (name=="state") {
			state_name = attrs.getValue("name");
			state_id = attrs.getValue("id");
			final_state = false;
			initial_state = false;
		} else if (name=="initial") {
			initial_state = true;
		} else if (name=="final") {
			final_state = true;
		}
		cdc="";
	}

	public void endElement(String uri, String localName, String name) {
		if (name=="state") {
			e = new Etat(state_id);
			Q.add(e);
			if (initial_state) I.add(state_id);
			if (final_state) F.add(state_id);
		} else if (name=="transition") {
			try{
				if (read!="") {
					A.add(read);
					Transition t = new Transition(from,read,to);
					mu.add(t);
				} else {
					EpsilonTransition t = new EpsilonTransition(from,to);
					mu.add(t);
				}
			} catch (JFSMException e) {
				System.out.println("Erreur:"+e);
			}
		} else if (name=="type") {
		
		} else if (name=="from") {
			from = cdc;
		} else if (name=="to") {
			to = cdc;
		} else if (name=="read") {
			read = cdc;
		}
	}
	
    /**
     * a method to 
     * @param <T>
     * @param list
     * @return <T> ArrayList<T>
     */
    public static <T> ArrayList<T> remove_duplicates(ArrayList<T> list) { 
    
        Set<T> set = new LinkedHashSet<>(); 
        set.addAll(list); 
        list.clear(); 
        list.addAll(set); 

        return list; 
    } 
}


