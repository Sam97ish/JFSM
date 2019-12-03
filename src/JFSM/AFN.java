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
 * AFN.java
 *
 *
 * Created: 2018-09-18
 *
 * @author Emmanuel Desmontils
 * @version 1.0
 */

package JFSM;

import java.util.Set;
import java.util.HashSet;

import java.util.List;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Deque;

public class AFN extends Automate {

	public AFN(Set<String> A, Set<Etat> Q,  Set<String> I,  Set<String> F, Set<Transition> mu) throws JFSMException {
		super(A,Q,I,F,mu);
	}

	public AFN(Automate a)  throws JFSMException {
		this(a.A, new HashSet(a.Q.values()),a.I, a.F, a.mu);
	}

	/** 
	* Permet de transformer l'automate en un automate déterministe  
	* @return un automate déterministe équivalent
	*/
	public Automate determiniser() { 
		System.out.println("determiniser() : case of not epsilon transition is implemented only");
		
		Automate automata = this;
		
		ArrayList<String> l_initial = new ArrayList<String>();
		l_initial.addAll(automata.I);
		
		ArrayList<String> l_lang = new ArrayList<String>();
		l_lang.addAll(automata.A);
		
		//list of list of strings to represent the table of transitions
		ArrayList<ArrayList<String>> table_trans = new ArrayList<ArrayList<String>>();
		
		//the first row corresponds to the initial state
		ArrayList<String> row = new ArrayList<String>();
		for(int i = 0; i < l_lang.size(); i++) {
			row.addAll(automata.get_cible(l_initial.get(0), l_lang.get(i)));
		}
		
		//adding the first row of the initial state to the tansitions table 
		System.out.println("first row " + row);
		table_trans.add(row);
		
		//creating the table of the new states that must be tested
		ArrayList<String> new_states = new ArrayList<String>();
		//we must add all the cibles of the initial state
		new_states.add(l_initial.get(0));
		new_states.addAll(row);
		
		int j = 1; 
		while(j < new_states.size()) {
			
			row.clear();
			
			//if the cible is composed of several states or not, the process will differ
			if(new_states.get(j).length() < 1) {	
				for(int i = 0; i < l_lang.size(); i++) {
					ArrayList<String> cible = automata.get_cible(new_states.get(j), l_lang.get(i));
					
					row.addAll(cible);
					
				}
			}else {
				ArrayList<String> cible = new ArrayList<String>();
				for(int i = 0; i < l_lang.size(); i++) {
					
					String cibleobtenu = "";
					
					for(int k = 0; k < new_states.get(j).length(); k= 2*k + 1) {
						String Letter = String.valueOf(new_states.get(j).charAt(k)) ;
						
						ArrayList<String> potenital = automata.get_cible(Letter, l_lang.get(i));
						if(!(potenital.equals("[] "))) {
							cibleobtenu = cibleobtenu + potenital.toString() + " " ; 
						}
						
					}
					cibleobtenu.concat("");
					
					cible.add(cibleobtenu);
					
					row.addAll(cible);
					
				}
			}
			
	        
			System.out.println("row after the first" + j +" " + row);
			table_trans.add(row);
			
			new_states.addAll(row);
			
			/*remove duplicates from an array list by converting it to a set and then back to an array list
			 * preserves the insertion order
			 */
	        Set<String> set2 = new LinkedHashSet<>(); 
	        set2.addAll(new_states); 
	        new_states.clear(); 
	        new_states.addAll(set2); 
	        j +=1;
		}
		
		//creating the new deterministic automata
		automata.Q.clear();
		automata.A.clear();
		automata.mu.clear();
		Set<String> old_final = automata.F;
		automata.F.clear();
		
		for(int i = 0; i < new_states.size(); i++) {
			//adding the new state
			Etat state = new Etat(new_states.get(i));
			//System.out.println("new states " + new_states.get(i));
			
			automata.Q.put(new_states.get(i),state);
			
			
			if(new_states.get(i).length() > 1) {
				for(int h = 0; h < new_states.get(i).length(); h = 2*h+1) {
					String Letter = String.valueOf(new_states.get(i).charAt(h)) ;
					if(old_final.contains(Letter)) {
						try {
							automata.setFinal(state);
						} catch (JFSMException e) {
							System.out.println("couldnt set the final state " + state.name + e);
						}
					}
				}
			}else {
				if(old_final.contains(new_states.get(i))) {
					try {
						automata.setFinal(state);
					} catch (JFSMException e) {
						System.out.println("couldnt set the final state " + state.name + e);
					}
				}
			}
			//adding the transitions of the new state
			System.out.println("table of transitions ; "+ table_trans.toString());
			ArrayList<String> l_trans = table_trans.get(i);
			
			/*remove duplicates from an array list by converting it to a set and then back to an array list
			 * preserves the insertion order
			 */
	        Set<String> set = new LinkedHashSet<>(); 
	        set.addAll(l_trans); 
	        l_trans.clear(); 
	        l_trans.addAll(set);
	        
			for(int k = 0; k < l_lang.size(); k++) {
				try {
					
					System.out.println("new state " + new_states.get(i));
					System.out.println("lang " + l_lang.get(k));
					System.out.println("l trans " + l_trans.get(k));
					
					Transition temp = new Transition(new_states.get(i), l_lang.get(k), l_trans.get(k));
					automata.addTransition(temp);
				} catch (JFSMException e) {
					System.out.println("Couldnt create the transition " + e);
				}
			}
		}
		
		return automata;
	}

	public Queue<Transition> next(String symbol) {
		assert A.contains(symbol) : "next() : le symbole doit être un symbole de l'alphabet." ;
		Queue<Transition> l = new LinkedList<Transition>();
		for(Transition t : mu) {
			if (t.candidate(current,symbol)) {
				l.add(t) ;
			}
		}
		return l;
	}

	// ATTENTION : Fonctionne pas si l'automate possède un cycle d'espilon-transitions !
	private boolean runAFN(String currentState, Deque<String> evts) {
		boolean ok = false;
		System.out.println("State:"+evts+"/"+currentState);
		current = currentState ;
		if (evts.isEmpty()) {
			ok = isFinal(currentState);
			if (ok) System.out.println("OK");
			else System.out.println("Echec (état non final)");
			return ok;
		} else {
			String symbol = evts.pollFirst();
			Queue<Transition> lt = next(symbol);
			if (lt.size()==0) System.out.println("Echec (pas de transition)");
			else {
				for (Transition t : lt) {
					if (t instanceof EpsilonTransition) {
						evts.addFirst(symbol);
					}
					System.out.println("Transition choisie:"+t+" avec "+evts);
					String next = t.appliquer();
					histo.push(t);
					ok = runAFN(next,evts);
					if (ok) break;
					else {
						System.out.println("Mauvais choix:"+t);
						current = currentState ;
						t = histo.pop();
						if (t instanceof EpsilonTransition) symbol = evts.pollFirst();
					}
				} 
			}
			if (!ok) evts.addFirst(symbol);
			return ok;
		}
	}

	public boolean run(List<String> l) {
        init();
        boolean ok = false;
        for(String i : I) {
        	if (runAFN(i,new LinkedList<String>(l))) {
        		ok = true;
        		break;
        	}
        }
        return ok ;
    }

}