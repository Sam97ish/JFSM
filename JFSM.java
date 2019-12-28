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
      
      //afn3.run(l2);
      
      //GABUZOMEU
      List<String> l3 = new ArrayList<String>();
      l3.add("GA");l3.add("BU");l3.add("ZO");l3.add("MEU");
      
      //afn3.run(l3);
      
      //ZOZOGAZOGAGAZO
      List<String> l4 = new ArrayList<String>();
      l4.add("ZO");l4.add("ZO");l4.add("GA");l4.add("ZO");
      l4.add("GA");l4.add("GA");l4.add("ZO");
      
     //afn3.run(l4);
      
      //BUGAZOMEU
      List<String> l5 = new ArrayList<String>();
      l5.add("BU");l5.add("GA");l5.add("ZO");l5.add("MEU");
      
     afn3.run(l5);
      
      //---------------------------------------------------------------------------
        
       /*
        // test de BUGA Standard DISTANCIEL
        System.out.println(afn3);
        System.out.println("is it standard ?");
        System.out.println(afn3.estStandard());
        System.out.println("what about now ?");
        System.out.println(afn3.standardiser());
        System.out.println(afn3.standardiser().estStandard());
        */
        
        /*
        //test de BUGA Normal DISTANCIEL
        System.out.println("is it normal ?");
        System.out.println(afn3.standardiser().estNormalise());
        System.out.println("what about now ?");
        System.out.println(afn3.normaliser());
        System.out.println(afn3.normaliser().estNormalise());
        
        
        */
        
        //----------- Jeux d’essai tp--------------//
        
        //------------Standardisation------------//
        
        
        //-------cas général ---------//
        
        /*
        Set<String> As1 = new HashSet<String>();      
        As1.add("a");As1.add("b");As1.add("c");

        Set<Etat> Qs1 = new HashSet<Etat>();
        Qs1.add(new Etat("1"));Qs1.add(new Etat("2"));
        Qs1.add(new Etat("3"));Qs1.add(new Etat("4"));

        Set<Transition> mus1 = new HashSet<Transition>();
        mus1.add(new Transition("1","a","2"));
        mus1.add(new Transition("2","b","1"));
        mus1.add(new Transition("2","a","3"));
        mus1.add(new Transition("3","c","4"));
        
        Set<String> Is1 = new HashSet<String>();
        Is1.add("1");

        Set<String> Fs1 = new HashSet<String>();
        Fs1.add("4");

        AFN afns1 = new AFN(As1, Qs1, Is1, Fs1, mus1);
        
        System.out.println(afns1);
        System.out.println("is it standard ?");
        System.out.println(afns1.estStandard());
        System.out.println("what about now ?");
        System.out.println(afns1.standardiser());
        System.out.println(afns1.standardiser().estStandard());
        */
        
        //----------cas avec plusieurs états initiaux----------//
        /*
        Set<String> As2 = new HashSet<String>();      
        As2.add("a");As2.add("b");As2.add("c");

        Set<Etat> Qs2 = new HashSet<Etat>();
        Qs2.add(new Etat("1"));Qs2.add(new Etat("2"));
        Qs2.add(new Etat("3"));Qs2.add(new Etat("4"));

        Set<Transition> mus2 = new HashSet<Transition>();
        mus2.add(new Transition("1","c","3"));
        mus2.add(new Transition("1","a","2"));
        mus2.add(new Transition("2","c","3"));
        mus2.add(new Transition("3","b","4"));
        
        Set<String> Is2 = new HashSet<String>();
        Is2.add("1");Is2.add("2");

        Set<String> Fs2 = new HashSet<String>();
        Fs2.add("4");

        AFN afns2 = new AFN(As2, Qs2, Is2, Fs2, mus2);
        
        System.out.println(afns2);
        System.out.println("is it standard ?");
        System.out.println(afns2.estStandard());
        System.out.println("what about now ?");
        System.out.println(afns2.standardiser());
        System.out.println(afns2.standardiser().estStandard());
        */
        
        //------------cas avec des epsilon transitions ---------//
        /*
        
        Set<String> As3 = new HashSet<String>();      
        As3.add("a");As3.add("b");As3.add("c");

        Set<Etat> Qs3 = new HashSet<Etat>();
        Qs3.add(new Etat("1"));Qs3.add(new Etat("2"));
        Qs3.add(new Etat("3"));

        Set<Transition> mus3 = new HashSet<Transition>();
        mus3.add(new Transition("1","b","2"));
        mus3.add(new Transition("2","c","3"));
        mus3.add(new EpsilonTransition("2","1"));
        
        Set<String> Is3 = new HashSet<String>();
        Is3.add("1");

        Set<String> Fs3 = new HashSet<String>();
        Fs3.add("3");

        AFN afns3 = new AFN(As3, Qs3, Is3, Fs3, mus3);
        
        System.out.println(afns3);
        System.out.println("is it standard ?");
        System.out.println(afns3.estStandard());
        System.out.println("what about now ?");
        System.out.println(afns3.standardiser());
        System.out.println(afns3.standardiser().estStandard());
        
        */
        
        //-------------normalisation---------------------//
        
        //----------cas général------------------//
        /*
        
        Set<String> An1 = new HashSet<String>();      
        An1.add("a");An1.add("b");An1.add("c");

        Set<Etat> Qn1 = new HashSet<Etat>();
        Qn1.add(new Etat("1"));Qn1.add(new Etat("2"));
        Qn1.add(new Etat("3"));Qn1.add(new Etat("4"));

        Set<Transition> mun1 = new HashSet<Transition>();
        mun1.add(new Transition("1","a","2"));
        mun1.add(new Transition("2","a","3"));
        mun1.add(new Transition("2","b","1"));
        mun1.add(new Transition("3","c","4"));
        
        Set<String> In1 = new HashSet<String>();
        In1.add("1");

        Set<String> Fn1 = new HashSet<String>();
        Fn1.add("4");

        AFN afnn1 = new AFN(An1, Qn1, In1, Fn1, mun1);
        
        System.out.println(afnn1);
        System.out.println("is it normal ?");
        System.out.println(afnn1.standardiser().estNormalise());
        System.out.println("what about now ?");
        System.out.println(afnn1.normaliser());
        System.out.println(afnn1.normaliser().estNormalise());
        */
        
        //----------cas d’un automate non unitaire ou non standard------//
        
        //1
        /*
        Set<String> An2 = new HashSet<String>();      
        An2.add("a");An2.add("b");An2.add("c");

        Set<Etat> Qn2 = new HashSet<Etat>();
        Qn2.add(new Etat("1"));Qn2.add(new Etat("2"));
        Qn2.add(new Etat("3"));Qn2.add(new Etat("4"));
        Qn2.add(new Etat("5"));

        Set<Transition> mun2 = new HashSet<Transition>();
        mun2.add(new Transition("1","c","1"));
        mun2.add(new Transition("1","a","2"));
        mun2.add(new Transition("1","b","3"));
        mun2.add(new Transition("2","b","2"));
        mun2.add(new Transition("2","c","4"));
        mun2.add(new Transition("3","b","5"));
        
        Set<String> In2 = new HashSet<String>();
        In2.add("1");

        Set<String> Fn2 = new HashSet<String>();
        Fn2.add("4");Fn2.add("5");

        AFN afnn2 = new AFN(An2, Qn2, In2, Fn2, mun2);
        
        System.out.println(afnn2);
        System.out.println("is it normal ?");
        System.out.println(afnn2.standardiser().estNormalise());
        System.out.println("what about now ?");
        System.out.println(afnn2.normaliser());
        System.out.println(afnn2.normaliser().estNormalise());
        */
        
        //2
        /*
        Set<String> An3 = new HashSet<String>();      
        An3.add("a");An3.add("b");An3.add("c");

        Set<Etat> Qn3 = new HashSet<Etat>();
        Qn3.add(new Etat("1"));Qn3.add(new Etat("2"));
        Qn3.add(new Etat("3"));Qn3.add(new Etat("4"));

        Set<Transition> mun3 = new HashSet<Transition>();
        mun3.add(new Transition("1","a","2"));
        mun3.add(new Transition("1","c","3"));
        mun3.add(new Transition("2","c","3"));
        mun3.add(new Transition("3","b","4"));

        
        Set<String> In3 = new HashSet<String>();
        In3.add("1");In3.add("2");

        Set<String> Fn3 = new HashSet<String>();
        Fn3.add("4");

        AFN afnn3 = new AFN(An3, Qn3, In3, Fn3, mun3);
        
        System.out.println(afnn3);
        System.out.println("is it normal ?");
        System.out.println(afnn3.standardiser().estNormalise());
        System.out.println("what about now ?");
        System.out.println(afnn3.normaliser());
        System.out.println(afnn3.normaliser().estNormalise());
        
        */
        
        //-------cas de plusieurs etat finaux et des epsilon transitions--//
        /*
        Set<String> An4 = new HashSet<String>();      
        An4.add("a");An4.add("b");An4.add("c");

        Set<Etat> Qn4 = new HashSet<Etat>();
        Qn4.add(new Etat("1"));Qn4.add(new Etat("2"));
        Qn4.add(new Etat("3"));Qn4.add(new Etat("4"));
        Qn4.add(new Etat("5"));

        Set<Transition> mun4 = new HashSet<Transition>();
        mun4.add(new EpsilonTransition("1","1"));
        mun4.add(new Transition("1","a","2"));
        mun4.add(new Transition("1","b","3"));
        mun4.add(new Transition("2","b","2"));
        mun4.add(new EpsilonTransition("2","4"));
        mun4.add(new EpsilonTransition("3","5"));

        
        Set<String> In4 = new HashSet<String>();
        In4.add("1");

        Set<String> Fn4 = new HashSet<String>();
        Fn4.add("4");Fn4.add("5");

        AFN afnn4 = new AFN(An4, Qn4, In4, Fn4, mun4);
        
        System.out.println(afnn4);
        System.out.println("is it normal ?");
        System.out.println(afnn4.standardiser().estNormalise());
        System.out.println("what about now ?");
        System.out.println(afnn4.normaliser());
        System.out.println(afnn4.normaliser().estNormalise());
        */
        
        
        //-------------Jeux d’essai **Tache 5,6,7**  -------//
        
        //*****Mise à l’etoile *******//
        
        //cas avec automate standard:
        /*
        Set<String> AM1 = new HashSet<String>();      
        AM1.add("a");AM1.add("b");AM1.add("c");

        Set<Etat> QM1 = new HashSet<Etat>();
        QM1.add(new Etat("1"));QM1.add(new Etat("2"));
        QM1.add(new Etat("3"));QM1.add(new Etat("4"));
        QM1.add(new Etat("5"));

        Set<Transition> muM1 = new HashSet<Transition>();
        muM1.add(new Transition("1","a","2"));
        muM1.add(new Transition("1","b","3"));
        muM1.add(new Transition("2","b","2"));
        muM1.add(new Transition("2","c","4"));
        muM1.add(new Transition("3","a","5"));


        Set<String> IM1 = new HashSet<String>();
        IM1.add("1");

        Set<String> FM1 = new HashSet<String>();
        FM1.add("4");FM1.add("5");

        AFN afnM1 = new AFN(AM1, QM1, IM1, FM1, muM1);
        
        System.out.println("afficher L’automate");
        System.out.println(afnM1);
        System.out.println("afficher l'automate résultant de la mise à l'étoile");
        System.out.println(afnM1.etoile());
        
        */
        
        //cas avec automate standard
        /*
        
        Set<String> AM2 = new HashSet<String>();      
        AM2.add("a");AM2.add("b");AM2.add("c");

        Set<Etat> QM2 = new HashSet<Etat>();
        QM2.add(new Etat("1"));QM2.add(new Etat("2"));
        QM2.add(new Etat("3"));QM2.add(new Etat("4"));
        QM2.add(new Etat("5"));

        Set<Transition> muM2 = new HashSet<Transition>();
        muM2.add(new Transition("1","b","2"));
        muM2.add(new Transition("2","a","1"));
        muM2.add(new Transition("2","c","2"));
        muM2.add(new Transition("2","b","3"));
        muM2.add(new Transition("3","a","5"));
        muM2.add(new Transition("3","c","4"));


        Set<String> IM2 = new HashSet<String>();
        IM2.add("1");

        Set<String> FM2 = new HashSet<String>();
        FM2.add("4");FM2.add("5");

        AFN afnM2 = new AFN(AM2, QM2, IM2, FM2, muM2);
        
        System.out.println("afficher L’automate");
        System.out.println(afnM2);
        System.out.println("afficher L’automate après Standardisation");
        System.out.println(afnM2.standardiser());
        System.out.println("afficher l'automate résultant de la mise à l'étoile");
        System.out.println(afnM2.etoile());
        
        */
        
        //cas avec automate avec epsilon transition:
        /*
        
        Set<String> AM3 = new HashSet<String>();      
        AM3.add("a");AM3.add("b");AM3.add("c");

        Set<Etat> QM3 = new HashSet<Etat>();
        QM3.add(new Etat("1"));QM3.add(new Etat("2"));
        QM3.add(new Etat("3"));

        Set<Transition> muM3 = new HashSet<Transition>();
        muM3.add(new Transition("1","a","2"));
        muM3.add(new Transition("3","c","3"));
        muM3.add(new EpsilonTransition("2","3"));


        Set<String> IM3 = new HashSet<String>();
        IM3.add("1");

        Set<String> FM3 = new HashSet<String>();
        FM3.add("3");;

        AFN afnM3 = new AFN(AM3, QM3, IM3, FM3, muM3);
        
        
        System.out.println("afficher L’automate");
        System.out.println(afnM3);
        System.out.println("afficher l'automate résultant de la mise à l'étoile");
        System.out.println(afnM3.etoile());
        */
        
        //*****Transposé********//
        
        //cas automate avec un seul état initial et un seul était finale
        /*
        Set<String> AT1 = new HashSet<String>();      
        AT1.add("a");AT1.add("b");AT1.add("c");

        Set<Etat> QT1 = new HashSet<Etat>();
        QT1.add(new Etat("1"));QT1.add(new Etat("2"));
        QT1.add(new Etat("3"));QT1.add(new Etat("4"));

        Set<Transition> muT1 = new HashSet<Transition>();
        muT1.add(new Transition("1","a","2"));
        muT1.add(new Transition("2","b","1"));
        muT1.add(new Transition("2","a","3"));
        muT1.add(new Transition("3","c","4"));


        Set<String> IT1 = new HashSet<String>();
        IT1.add("1");

        Set<String> FT1 = new HashSet<String>();
        FT1.add("4");;

        AFN afnT1 = new AFN(AT1, QT1, IT1, FT1, muT1);
        
        System.out.println("afficher L’automate");
        System.out.println(afnT1);
        System.out.println("afficher l'automate résultant de la Transposé");
        System.out.println(afnT1.transpose());
        */
        
        //cas avec un automate avec plusieur états initiaux ou finales:
        /*
        
        Set<String> AT2 = new HashSet<String>();      
        AT2.add("a");AT2.add("b");AT2.add("c");

        Set<Etat> QT2 = new HashSet<Etat>();
        QT2.add(new Etat("1"));QT2.add(new Etat("2"));
        QT2.add(new Etat("3"));QT2.add(new Etat("4"));
        QT2.add(new Etat("5"));

        Set<Transition> muT2 = new HashSet<Transition>();
        muT2.add(new Transition("1","a","2"));
        muT2.add(new Transition("1","b","3"));
        muT2.add(new Transition("2","c","4"));
        muT2.add(new Transition("3","a","5"));


        Set<String> IT2 = new HashSet<String>();
        IT2.add("1");IT2.add("3");

        Set<String> FT2 = new HashSet<String>();
        FT2.add("4");FT2.add("5");

        AFN afnT2 = new AFN(AT2, QT2, IT2, FT2, muT2);
        
        
        System.out.println("afficher L’automate");
        System.out.println(afnT2);
        System.out.println("afficher l'automate résultant de la Transposé");
        System.out.println(afnT2.transpose());
        
        */
        
        //cas avec des epsilon de transition:
        /*
        Set<String> AT3 = new HashSet<String>();      
        AT3.add("a");AT3.add("b");AT3.add("c");

        Set<Etat> QT3 = new HashSet<Etat>();
        QT3.add(new Etat("1"));QT3.add(new Etat("2"));
        QT3.add(new Etat("3"));
        

        Set<Transition> muT3 = new HashSet<Transition>();
        muT3.add(new Transition("1","a","2"));
        muT3.add(new Transition("2","b","1"));
        muT3.add(new EpsilonTransition("2","3"));
        muT3.add(new Transition("3","c","3"));



        Set<String> IT3 = new HashSet<String>();
        IT3.add("1");

        Set<String> FT3 = new HashSet<String>();
        FT3.add("3");

        AFN afnT3 = new AFN(AT3, QT3, IT3, FT3, muT3);
        
        
        System.out.println("afficher L’automate");
        System.out.println(afnT3);
        System.out.println("afficher l'automate résultant de la Transposé");
        System.out.println(afnT3.transpose());
        */
     
     //***************Méthode supplémentaire*********//
     
     //isAccessible
     /*
     Set<String> A_1 = new HashSet<String>();      
     A_1.add("a");A_1.add("b");A_1.add("c");

     Set<Etat> Q_1 = new HashSet<Etat>();
     Q_1.add(new Etat("1"));Q_1.add(new Etat("2"));
     Q_1.add(new Etat("3"));Q_1.add(new Etat("4"));
     Q_1.add(new Etat("5"));

     Set<Transition> mu_1 = new HashSet<Transition>();
     mu_1.add(new Transition("1","a","2"));
     mu_1.add(new Transition("2","c","4"));
     mu_1.add(new Transition("3","a","5"));


     Set<String> I_1 = new HashSet<String>();
     I_1.add("1");

     Set<String> F_1 = new HashSet<String>();
     F_1.add("4");F_1.add("5");

     AFN afn_1 = new AFN(A_1, Q_1, I_1, F_1, mu_1);
     
     System.out.println(afn_1);
     System.out.println("l'etat 3 accessible ou non?");
     System.out.println(afn_1.isAccessible("3"));  //expect false got false
     System.out.println("l'etat 4 accessible ou non?");
     System.out.println(afn_1.isAccessible("4")); // expect true got true
     */
     
     //isCoaccessible
     /*
     Set<String> A_2 = new HashSet<String>();      
     A_2.add("a");A_2.add("b");A_2.add("c");

     Set<Etat> Q_2 = new HashSet<Etat>();
     Q_2.add(new Etat("1"));Q_2.add(new Etat("2"));
     Q_2.add(new Etat("3"));Q_2.add(new Etat("4"));
     Q_2.add(new Etat("5"));

     Set<Transition> mu_2 = new HashSet<Transition>();
     mu_2.add(new Transition("1","a","2"));
     mu_2.add(new Transition("1","a","3"));
     mu_2.add(new Transition("3","a","5"));


     Set<String> I_2 = new HashSet<String>();
     I_2.add("1");

     Set<String> F_2 = new HashSet<String>();
     F_2.add("4");F_2.add("5");

     AFN afn_2 = new AFN(A_2, Q_2, I_2, F_2, mu_2);
     
     
     System.out.println(afn_2);
     System.out.println("l'etat 3 coaccessible ou non?");
     System.out.println(afn_2.isCoaccessible("3"));  //expect true got true
     System.out.println("l'etat 2 accessible ou non?");
     System.out.println(afn_2.isCoaccessible("2")); // expect false got false
     */
     
     //removeEtat
     /*
     
     Set<String> A_3 = new HashSet<String>();      
     A_3.add("a");A_3.add("b");A_3.add("c");

     Set<Etat> Q_3 = new HashSet<Etat>();
     Q_3.add(new Etat("1"));Q_3.add(new Etat("2"));
     Q_3.add(new Etat("3"));Q_3.add(new Etat("4"));
     Q_3.add(new Etat("5"));

     Set<Transition> mu_3 = new HashSet<Transition>();
     mu_3.add(new Transition("1","a","2"));
     mu_3.add(new Transition("1","a","3"));
     mu_3.add(new Transition("3","a","5"));
     mu_3.add(new Transition("2","a","4"));



     Set<String> I_3 = new HashSet<String>();
     I_3.add("1");

     Set<String> F_3 = new HashSet<String>();
     F_3.add("4");F_3.add("5");

     AFN afn_3 = new AFN(A_3, Q_3, I_3, F_3, mu_3);
     
     
     System.out.println(afn_3);
     
     System.out.println("supprimer l'etat 3");
     afn_3.getEtat("3").removeEtat(afn_3);
     
     System.out.println(afn_3);
     */
        
   }

}