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
 * JFSM.java
 *
 *
 * Created: 2017-08-25
 *
 * @author Emmanuel Desmontils
 * @version 1.0
 */

import java.util.Set;
import java.util.HashSet;

import java.util.List;
import java.util.ArrayList;

import java.util.Map;
import java.util.HashMap;

import java.util.Iterator;

import JFSM.*;
import JFSM.Transducteur.*;

public class JFSM {
    public static void main(String argv []) throws JFSMException {

      Set<String> A = new HashSet<String>();      
      A.add("a");A.add("b");A.add("c");

      Set<Etat> Q = new HashSet<Etat>();
      Q.add(new Etat("1"));Q.add(new Etat("2"));
      Q.add(new Etat("3"));Q.add(new Etat("4"));Q.add(new Etat("5"));

      Set<Transition> mu = new HashSet<Transition>();
      mu.add(new Transition("1","a","2"));
      mu.add(new Transition("1","b","4"));
      mu.add(new Transition("2","b","3"));
      mu.add(new Transition("2","c","4"));
      mu.add(new Transition("3","a","2"));
      mu.add(new Transition("3","b","4"));
      mu.add(new Transition("4","a","5"));
      mu.add(new Transition("5","c","5"));


      Set<String> F = new HashSet<String>();
      F.add("5");
      F.add("4");
      F.add("1");
      Automate afn = new AFD(A, Q, "1", F, mu);

      List<String> l = new ArrayList<String>();
      l.add("a");l.add("b");l.add("a");l.add("c");
/*
      System.out.println(afn);
      System.out.println(afn.run(l));
      System.out.println(afn.emonder());

      System.out.println(afn);
      System.out.println("Epsilon Libre ? "+afn.epsilonLibre());

      afn.save("test.jff");

      Automate afn2 = Automate.load("essai.jff");
      System.out.println(afn2.getClass().getName());
      System.out.println(afn2);
      */
      
      //TP4 1.b
      Set<String> A2 = new HashSet<String>();      
      A2.add("ZO");A2.add("GA");A2.add("MEU");
      A2.add("BU");

      Set<Etat> Q2 = new HashSet<Etat>();
      Q2.add(new Etat("1"));Q2.add(new Etat("2"));
      Q2.add(new Etat("3"));Q2.add(new Etat("4"));Q2.add(new Etat("5"));
      Q2.add(new Etat("6"));Q2.add(new Etat("7"));Q2.add(new Etat("8"));
      Q2.add(new Etat("9"));

      Set<Transition> mu2 = new HashSet<Transition>();
      mu2.add(new Transition("1","ZO","1"));
      mu2.add(new Transition("1","GA","4"));
      mu2.add(new Transition("1","BU","5"));
      mu2.add(new Transition("2","MEU","1"));
      mu2.add(new Transition("2","BU","5"));
      mu2.add(new Transition("2","ZO","6"));
      mu2.add(new Transition("3","MEU","2"));
      mu2.add(new Transition("3","ZO","6"));
      mu2.add(new Transition("3","GA","3"));
      mu2.add(new Transition("4","ZO","5"));
      mu2.add(new Transition("4","GA","7"));
      mu2.add(new Transition("4","BU","8"));
      mu2.add(new Transition("5","BU","8"));
      mu2.add(new Transition("5","GA","6"));
      mu2.add(new Transition("5","ZO","9"));
      mu2.add(new Transition("6","GA","6"));
      mu2.add(new Transition("6","ZO","9"));
      mu2.add(new Transition("7","MEU","7"));
      mu2.add(new Transition("8","MEU","7"));
      mu2.add(new Transition("9","MEU","8"));
      mu2.add(new Transition("9","BU","9"));


      Set<String> F2 = new HashSet<String>();
      F2.add("7");
      F2.add("8");
      F2.add("9");
      
      Set<String> I2 = new HashSet<String>();
      I2.add("1");
      I2.add("2");
      I2.add("3");
      
      Automate afn3 = new AFN(A2, Q2, I2, F2, mu2);
      
      //chaine (mots) a utiliser
      
      //MEUMEUBUZOBUMEU
      List<String> l2 = new ArrayList<String>();
      l2.add("MEU");l2.add("MEU");l2.add("BU");l2.add("ZO");
      l2.add("BU");l2.add("MEU");
      
      //GABUZOMEU
      List<String> l3 = new ArrayList<String>();
      l3.add("GA");l3.add("BU");l3.add("ZO");l3.add("MEU");
      
      //ZOZOGAZOGAGAZO
      List<String> l4 = new ArrayList<String>();
      l4.add("ZO");l4.add("ZO");l4.add("GA");l4.add("ZO");
      l4.add("GA");l4.add("GA");l4.add("ZO");
      
      //BUGAZOMEU
      List<String> l5 = new ArrayList<String>();
      l5.add("BU");l5.add("GA");l5.add("ZO");l5.add("MEU");
      
      
      //affichage
      
      //test de afn (estStandard)
      //System.out.println(afn.estStandard()); expect true
      //System.out.println(afn3.estStandard()); expect false
      
      //test de afn (Standardiser)
     /* System.out.println(afn3.estStandard());
      System.out.println("after Standardiser");
      System.out.println(afn3.standardiser());
      Automate afn3_standarised = afn3.standardiser();
      System.out.println(afn3_standarised .estStandard());
      */
      
      //FIXME : the methode isAccessible for the state 3 should return true but is returing false.
     /* 
      //test isAccessible avec afn and the state 4
      System.out.println("test isAccessible avec afn and the state 3");
      //state 3,5 are missing from the list
      System.out.println(afn.isAccessible("3"));
      */
      
      //state 7 and 5 are missing from the list
      System.out.println(afn3.isAccessible("5"));
      
      
      //test isCoaccessible avec afn and the state 4
      System.out.println("test isCoaccessible avec afn and the state 3");
      System.out.println(afn.isCoaccessible("3"));
      
      /*
      //test estUtile (automate) avec afn and the state 4
      System.out.println("test if afn is Utile");
      System.out.println(afn.estUtile());
      */
      
      //finally got it to work
      /*
      System.out.println(afn3);
      System.out.println(afn3.run(l2));
      System.out.println(afn3.run(l3));
      System.out.println(afn3.run(l4));
      System.out.println(afn3.run(l5));
      System.out.println(afn3.emonder());
      

      System.out.println(afn3);
      System.out.println("Epsilon Libre ? "+afn3.epsilonLibre());
*/
   }
}
