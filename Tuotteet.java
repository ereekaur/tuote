/**
 * 
 */
package tuote;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Pitää yllä tuote rekisteriä osaa lisätä nuuskia listaan ja luke ja kirjoittaa tiedostoon.
 * @author Joonas Hartikainen ja Erno Kauranen
 * @version 1.12.2020
 *@version 1.1 12.12.2020
 */
public class Tuotet implements Iterable<Tuote> {
    
    private String                              tiedostonNimi="tuotteet\tuotteet.dat";
    
    private final ArrayList<Tuote> alkiot = new ArrayList<Tuote>();
    
    /**
     * Tuotteiden alustaminen
     */
    public Tuotet() {
        //Toistaiseksi ei tarvitse tehdä mitään
    }
    
    /**
     * Lisää uuden Tuoten tietorakenteeseen. Omistus muuttuu tietorakenteelle
     * @param tuote lisättävä tuote.
     */
    public void lisaaTuote(Tuote tuote) {
        alkiot.add(tuote);
    }

    /**
     * Tekee jotain mitä ei välttämättä tarvita
     * @param hakemisto joka random hakemisto
     * @throws SailoException jos talletus epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "-tuote";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }
    
    /**
     * Palauttaa ostoslistan tuotteiden määrän.
     * @return tuotteiden lukumäärä.
     */
    public int getTuotetLkm() {
        return alkiot.size();
    }
    
    /**
     * Haetaan kaikki tuotteet, mitä listassa on
     * @return Tuotet mitä listassa on
     */
    public List<Tuote> annaTuote() {
        List<Tuote> loydetyt = new ArrayList<Tuote>();
        for (Tuote tuote : alkiot)
             loydetyt.add(tuote);
        return loydetyt;
    }
    
    /**
     * Vipellin, joka vipeltää tietorakenteen läpi.
     */
    @Override
    public Iterator<Tuote> iterator() {
        return alkiot.iterator();
    }
    
    
    /**
     * Tallentaa tiedot nuuskista tuotteet.dat nimiseen tiedostoon.
     * @throws SailoException d
     */
    public void tallenna() throws SailoException {

        
         File ftied = new File("tuotteet/tuotteet.dat");
        try( PrintWriter fo = new PrintWriter (new FileWriter(ftied.getCanonicalPath()))){
        //
           fo.println("Tuotelista");
           fo.println(alkiot.size());
           for (int i = 0; i < getTuotetLkm(); i++) {
               Tuote tuote = alkiot.get(i);
                fo.println(tuote.toString());
           }
        }
           
        catch (FileNotFoundException ex) {
            
            throw new SailoException("Tiedosto " + ftied.getName() + "ei aukea");
        } catch (IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + "ei aukea");
            
            
        }
         
         
        
        
        
        
        
        
    }
    
    
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
       Tuotet tuotteet = new Tuotet();
       Tuote tuote1 = new Tuote();
       tuote1.taytaOdenColdDry();
      
       Tuote tuote2 = new Tuote();
       tuote2.taytaOdenColdDry();
      
       Tuote tuote3 = new Tuote();
       tuote3.taytaOdenColdDry();
       
       Tuote tuote4 = new Tuote();
       tuote4.taytaOdenColdDry();
       
       tuotteet.lisaaTuote(tuote1);
       tuotteet.lisaaTuote(tuote2);
       tuotteet.lisaaTuote(tuote3);
       tuotteet.lisaaTuote(tuote4);
       
       System.out.println("=================== Tuotet testi ===================");
       
       List<Tuote> tuotteet2 = tuotteet.annaTuote();
       for(Tuote tuote : tuotteet2) {
           System.out.print(" ");
           tuote.tulostaTuote(System.out);
       }
    
    }



 

}
