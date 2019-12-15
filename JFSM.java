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
import java.util.LinkedHashSet;

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
      
      
      //---------------------------------------------------------------------------
      //adding new automate afn4 
      
      Set<String> A4 = new HashSet<String>();      
      A4.add("a");A4.add("b");A4.add("c");

      Set<Etat> Q4 = new HashSet<Etat>();
      Q4.add(new Etat("1"));Q4.add(new Etat("2"));
      Q4.add(new Etat("3"));Q4.add(new Etat("4"));Q4.add(new Etat("5"));

      Set<Transition> mu4 = new HashSet<Transition>();
      mu4.add(new Transition("1","a","2"));
      mu4.add(new Transition("1","b","4"));
      mu4.add(new Transition("2","b","3"));
      mu4.add(new Transition("2","c","4"));
      mu4.add(new Transition("3","a","2"));
      mu4.add(new Transition("3","b","4"));
      mu4.add(new Transition("4","a","5"));
      


      Set<String> F4 = new HashSet<String>();
      F4.add("5");
      
      Automate afn4 = new AFD(A4, Q4, "1", F4, mu4);

      //new complete automate ------------------------------------------------------------
      Set<String> A5 = new HashSet<String>();      
      A5.add("a");A5.add("b");A5.add("c");

      Set<Etat> Q5 = new HashSet<Etat>();
      Q5.add(new Etat("1"));Q5.add(new Etat("2"));
      Q5.add(new Etat("3"));Q5.add(new Etat("4"));Q5.add(new Etat("5"));

      Set<Transition> mu5 = new HashSet<Transition>();
      mu5.add(new Transition("1","a","2"));
      mu5.add(new Transition("1","b","4"));
      mu5.add(new Transition("1","c","4"));
      mu5.add(new Transition("2","a","3"));
      mu5.add(new Transition("2","b","3"));
      mu5.add(new Transition("2","c","4"));
      mu5.add(new Transition("3","a","2"));
      mu5.add(new Transition("3","b","4"));
      mu5.add(new Transition("3","c","4"));
      mu5.add(new Transition("4","a","5"));
      mu5.add(new Transition("4","b","4"));
      mu5.add(new Transition("4","c","4"));
      mu5.add(new Transition("5","a","5"));
      mu5.add(new Transition("5","b","5"));
      mu5.add(new Transition("5","c","5"));


      Set<String> F5 = new HashSet<String>();
      F5.add("5");
      F5.add("4");
      F5.add("1");
      Automate afnComplet = new AFD(A5, Q5, "1", F5, mu5);
      
      //---------------------------------------------------------------------------
      //adding new automate afn6 
      
      Set<String> A6 = new HashSet<String>();      
      A6.add("a");A6.add("b");A6.add("c");

      Set<Etat> Q6 = new HashSet<Etat>();
      Q6.add(new Etat("1"));Q6.add(new Etat("2"));
      Q6.add(new Etat("3"));Q6.add(new Etat("4"));Q6.add(new Etat("5"));

      Set<Transition> mu6 = new HashSet<Transition>();
      mu6.add(new Transition("1","a","2"));
      mu6.add(new Transition("1","b","4"));
      mu6.add(new Transition("2","b","3"));
      mu6.add(new Transition("2","c","4"));
      mu6.add(new Transition("3","a","2"));
      mu6.add(new Transition("3","b","4"));
      mu6.add(new Transition("4","a","5"));
      


      Set<String> F6 = new HashSet<String>();
      F6.add("5");
      
      Automate afn6 = new AFD(A6, Q6, "1", F6, mu6);
      
      //---------------------------------------------------------------------------
      //adding new automate afn8
      /*
      Set<String> A8 = new HashSet<String>();      
      A8.add("a");A8.add("b");A8.add("c");

      Set<Etat> Q8 = new HashSet<Etat>();
      Q8.add(new Etat("1"));Q8.add(new Etat("2"));
      Q8.add(new Etat("3"));Q8.add(new Etat("4"));Q8.add(new Etat("5"));

      Set<Transition> mu8 = new HashSet<Transition>();
      mu8.add(new Transition("1","a","2"));
      mu8.add(new Transition("1","b","4"));
      mu8.add(new Transition("2","b","3"));
      mu8.add(new Transition("2","c","4"));
      mu8.add(new Transition("3","a","2"));
      mu8.add(new Transition("3","b","4"));
      mu8.add(new Transition("4","a","5"));
      mu8.add(new EpsilonTransition("1","2"));
      


      Set<String> F8 = new HashSet<String>();
      F8.add("5");F8.add("6");
      
      Automate afn8 = new AFD(A8, Q8, "1", F8, mu8);
      */
      Set<String> A7 = new HashSet<String>();      
      A7.add("a");A7.add("b");A7.add("c");

      Set<Etat> Q7 = new HashSet<Etat>();
      Q7.add(new Etat("1"));Q7.add(new Etat("2"));
      Q7.add(new Etat("3"));Q7.add(new Etat("4"));
      Q7.add(new Etat("5"));

      Set<Transition> mu7 = new HashSet<Transition>();
      mu7.add(new Transition("1","b","1"));
      mu7.add(new Transition("1","b","2"));
      mu7.add(new Transition("1","a","2"));
      mu7.add(new Transition("2","a","1"));
      mu7.add(new Transition("2","b","2"));
      mu7.add(new Transition("2","c","3"));
      mu7.add(new Transition("3","c","3"));
      mu7.add(new Transition("4","c","2"));
      mu7.add(new Transition("5","c","3"));
      mu7.add(new EpsilonTransition("5","2"));
      mu7.add(new EpsilonTransition("1","2"));
      mu7.add(new EpsilonTransition("4","2"));
      mu7.add(new EpsilonTransition("1","4"));

      Set<String> I7 = new HashSet<String>();
      I7.add("1");I7.add("2");

      Set<String> F7 = new HashSet<String>();
      F7.add("3");F7.add("5");

      AFN afn7 = new AFN(A7, Q7, I7, F7, mu7);

      List<String> l7 = new ArrayList<String>();
      l.add("a");l.add("b");l.add("a");l.add("c");
      
      //----------------------------------------------------------------------------
    //adding new automate afn9
      
      Set<String> A9 = new HashSet<String>();      
      A9.add("a");A9.add("b");A9.add("c");

      Set<Etat> Q9 = new HashSet<Etat>();
      Q9.add(new Etat("1"));Q9.add(new Etat("2"));
      Q9.add(new Etat("3"));

      Set<Transition> mu9 = new HashSet<Transition>();
      
      mu9.add(new Transition("1","b","1"));
      mu9.add(new EpsilonTransition("1","2"));
      mu9.add(new EpsilonTransition("2","1"));
      mu9.add(new Transition("2","b","2"));
      mu9.add(new EpsilonTransition("2","3"));
      mu9.add(new Transition("3","c","3"));

      Set<String> I9 = new HashSet<String>();
      I9.add("1");

      Set<String> F9 = new HashSet<String>();
      F9.add("3");

      AFN afn9 = new AFN(A9, Q9, I9, F9, mu9);

      List<String> l9 = new ArrayList<String>();
      l9.add("a");l9.add("b");l9.add("a");l9.add("c");
      
      //----------------------------------------------------------------------------
      //adding new automate afn10 (state 2 is not util)
        
        Set<String> A10 = new HashSet<String>();      
        A10.add("a");A10.add("b");A10.add("c");

        Set<Etat> Q10 = new HashSet<Etat>();
        Q10.add(new Etat("1"));Q10.add(new Etat("2"));
        Q10.add(new Etat("3"));

        Set<Transition> mu10 = new HashSet<Transition>();
        
        mu10.add(new Transition("1","b","1"));
        //mu10.add(new EpsilonTransition("1","2"));  to make 2 not util
        mu10.add(new EpsilonTransition("2","1"));
        mu10.add(new Transition("2","b","2"));
        mu10.add(new Transition("1","b","3"));
        mu10.add(new EpsilonTransition("1","3"));

        Set<String> I10 = new HashSet<String>();
        I10.add("1");

        Set<String> F10 = new HashSet<String>();
        F10.add("3");

        AFN afn10 = new AFN(A10, Q10, I10, F10, mu10);

        List<String> l10 = new ArrayList<String>();
        l9.add("a");l9.add("b");l9.add("a");l9.add("c");


      
      
      //affichage
      
      //test de afn (estStandard)
      //System.out.println(afn.estStandard()); expect true
      //System.out.println(afn3.estStandard()); expect false
      
     /* //test de afn (Standardiser)
      System.out.println(afn3.estStandard());
      System.out.println("after Standardiser");
      System.out.println(afn3.standardiser());
      Automate afn3_standarised = afn3.standardiser();
      System.out.println(afn3_standarised .estStandard());
     */

      /*
      //test isAccessible avec afn and the state 4
      System.out.println("test isAccessible avec afn and the state 4");
      System.out.println(afn.isAccessible("4"));
      */

      
     
     /* 
      //test isAccessible avec afn and the state 4
      System.out.println("test isAccessible avec afn and the state 3");
      //state 3,5 are missing from the list
      System.out.println(afn.isAccessible("3"));
     
      
      //state 7 and 5 are missing from the list
      System.out.println(afn3.isAccessible("5"));
      
      
      //test isCoaccessible avec afn and the state 4
      System.out.println("test isCoaccessible avec afn and the state 3");
      System.out.println(afn.isCoaccessible("3"));
      */
      
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
      
    //testing the removeEtat and isAccessible
     /* System.out.println(afn);
      afn.getEtat("4").removeEtat(afn);
      System.out.println(afn);
      
      System.out.println(afn.isAccessible("5")); // expected false because we deleted state 4 got false
      */
      
      //testing emonder
     /*
      System.out.println(afn3);
      System.out.println(afn3.estUtile()); //expected true
      
      System.out.println("removing state");
      afn3.getEtat("5").removeEtat(afn3);
      afn3.getEtat("6").removeEtat(afn3);
      System.out.println(afn3.estUtile()); //expected false
      
      afn3.emonder();
      System.out.println(afn3.estUtile()); //expected true
      */
      
      //testing isSource
      /*
      System.out.println(afn);
      System.out.println(afn.getEtat("4").isSource(afn)); //expect true  got true
      
      System.out.println("removing state 5");
      afn.getEtat("5").removeEtat(afn);
      
      System.out.println(afn);
      
      System.out.println(afn.getEtat("4").isSource(afn)); //
      */
      
      
      //testing isCible
      /*
      System.out.println(afn);
      System.out.println(afn.getEtat("3").isCible(afn)); //expect true  got true
      
      System.out.println("removing state 2");
      afn.getEtat("2").removeEtat(afn);
      
      System.out.println(afn);
      
      System.out.println(afn.getEtat("3").isCible(afn)); // expect false got false
      */
      
      
      //testing estnormalise
      /*
      System.out.println(afn.estNormalise());  //expect false got false
      System.out.println(afn4.estNormalise()); //expect true got true
      */

      /*
      //testing normalise 
      System.out.println(afn);
      System.out.println(afn.estNormalise());  //expect false got false
      System.out.println("normalising the automate");
      afn.normaliser();
      System.out.println(afn.estNormalise());  //expect true got true
      System.out.println(afn);
      */
      

      //testing transpose
      /*
      System.out.println(afn);
      System.out.println("Making the transpose of the automate");
      System.out.println(afn.transpose());
		*/
      //testing estComplet
      /*System.out.println(afnComplet.estComplet()); //should be true
      System.out.println(afn.estComplet());// should be false
      */
      //testing complet
     /*
      System.out.println(afn.estComplet());// should be false got false
      System.out.println("making the automate compllet");
      System.out.println(afn.complet());
      System.out.println(afn.estComplet());  // should be true got true
      
      
      //testing complementaire
     
      System.out.println(afn);
      System.out.println("Making the complementaire of the automate");
      System.out.println(afn.complementaire());
     
      //testing etoile 
      System.out.println(afn6);
      System.out.println("Making an etoile from the automate");
      System.out.println(afn6.etoile());
      
      
      ArrayList<String> l_test = new ArrayList<String>();
      l_test.add("a"); l_test.add("b"); l_test.add("c");
      System.out.println(l_test.toString());
      System.out.println(String.valueOf('A'));
      */
      
      //---------------------------------------------------------------------------
      //adding new automate afn7 non deterministe
      /*
      Set<String> A7 = new HashSet<String>();      
      A7.add("a");A7.add("b");A7.add("c");

      Set<Etat> Q7 = new HashSet<Etat>();
      Q7.add(new Etat("1"));Q7.add(new Etat("2"));
      Q7.add(new Etat("3"));

      Set<Transition> mu7 = new HashSet<Transition>();
      mu7.add(new Transition("1","b","1"));
      mu7.add(new Transition("1","b","2"));
      mu7.add(new Transition("1","a","2"));
      mu7.add(new Transition("2","a","1"));
      mu7.add(new Transition("2","b","2"));
      mu7.add(new Transition("2","c","3"));
      mu7.add(new Transition("3","c","3"));

      Set<String> I7 = new HashSet<String>();
      I7.add("1");

      Set<String> F7 = new HashSet<String>();
      F7.add("3");

      AFN afn7 = new AFN(A7, Q7, I7, F7, mu7);

      List<String> l7 = new ArrayList<String>();
      l.add("a");l.add("b");l.add("a");l.add("c");
      
      System.out.println(afn7);
      System.out.println(afn7.determiniser());
      
      */
      
      //testing normaliser with epsilon
      /*
        System.out.println(afn8);
      System.out.println(afn8.etoile());
      
      */
      
      //test normaliser with epsilon
      /*
      System.out.println(afn7);
      System.out.println(afn7.normaliser());
      */
      /*
      System.out.println(afn7);
      afn7.getEtat("5").removeEtat(afn7);
      System.out.println(afn7);
      */
      //System.out.println(afn9);
      //System.out.println(afn9.estUtile());
      
      //System.out.println(afn9.isAccessible("1"));
      //System.out.println(afn9.isCoaccessible("1"));
      //System.out.println(afn9.getEtat("1").estUtile(afn9));
      /*  
        System.out.println(afn10);
        System.out.println(afn10.isCoaccessible("2"));
        System.out.println(afn10.isAccessible("2"));
        System.out.println(afn10.estUtile());
        System.out.println(afn10.getEtat("2").estUtile(afn10));
        afn10 = (AFN) afn10.emonder();
        System.out.println(afn10);
      
      */
      /*
        //test of standariser with epsilon transition
       System.out.println(afn10.standardiser()); 
      
      marche bien
      
      */
        // test de BUGA Standard DISTANCIEL
        System.out.println("is it standard ?");
        System.out.println(afn3.estStandard());
        System.out.println("what about now ?");
        System.out.println(afn3.standardiser());
        System.out.println(afn3.standardiser().estStandard());
        
        //test de BUGA Normal DISTANCIEL
        System.out.println("is it normal ?");
        System.out.println(afn3.standardiser().estNormalise());
        System.out.println("what about now ?");
        System.out.println(afn3.normaliser());
        System.out.println(afn3.normaliser().estNormalise());
        
   }

}