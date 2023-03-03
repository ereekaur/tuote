/**
 * 
 */
package tuote;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Ostoslistan tilaajat joka osaa mm. lisätä uuden tilaajan poistaa tilaajan. lukea ja kirjoittaa tilaajien tiedostoon.
 * 
 * @author Joonas Hartikainen ja Erno Kauranen
 * @version 17.3.2019
 * @version 14.12.2020
 *
 */
public class Tilaajat {
    
    private static final int MAX_TILAAJIA = 8;
    private int lkm = 0;
   // private String tiedostonNimi = "tuotteet\tilaajat.dat";
    private Tilaaja[] alkiot = new Tilaaja[MAX_TILAAJIA];
    
    
    /**
     * Oletusmuodostaja
     */
    public Tilaajat() {
        // Attribuuttien oma alustus riittää
    }

    
    /**
     * Aliohjelma, joka kasvattaa taulukon kokoa.
     * @param taulukko jonka kokoa halutaan kasvattaa
     * @return isompi taulukko, joka sisältää alkuperäisen taulukon alkiot
     *
     */
    public Tilaaja[] kasvataKokoa(Tilaaja[] taulukko) {
        Tilaaja Tilaaja2[] = new Tilaaja[taulukko.length * 2];

        for (int i = 0; i < taulukko.length; i++){
           Tilaaja2[i] = taulukko[i];
        }
       
        return Tilaaja2;
     }
    
    /**
     * Lisää uuden tilaajan tietorakenteeseen. Ottaa tilaajan omistukseensa.
     * @param tilaaja lisättävän tilaajan viite. Huom tietorakenne muuttuu omistajaksi.
     * @example
     * <pre name="test">
     *   Tilaajat tilaajat = new Tilaajat();
     *   Tilaaja pena1 = new Tilaaja(), pena2 = new Tilaaja();
     *   tilaajat.getLkm() === 0;
     *   tilaajat.lisaa(pena1); tilaajat.getLkm() === 1;
     *   tilaajat.lisaa(pena2); tilaajat.getLkm() === 2;
     *   tilaajat.lisaa(pena1); tilaajat.getLkm() === 3;
     *   tilaajat.anna(0) === pena1;
     *   tilaajat.anna(1) === pena2;
     *   tilaajat.anna(2) === pena1;
     *   tilaajat.anna(1) == pena1 === false;
     *   tilaajat.anna(1) == pena2 === true;
     *   tilaajat.lisaa(pena1); tilaajat.getLkm() === 4;
     *   tilaajat.lisaa(pena1); tilaajat.getLkm() === 5;
     *   
     * </pre>
     */
    public void lisaa(Tilaaja tilaaja)  {
        if(lkm >= alkiot.length) alkiot = kasvataKokoa(alkiot);
        this.alkiot[lkm] = tilaaja;
        lkm++;
    }
    
    /**
     * Palauttaa ostoslistan tilaajien lukumäärän
     * @return tilaajien lukumäärä
     */
    public int getLkm() {
        return this.lkm;
    }
    
    /**
     * Palauttaa viitteen i:teen tilaajaan.
     * @param i monennenko tilaajan viite halutaan
     * @return viite tilaajaan, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Tilaaja anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    /**
     * Tallennetaan tilaajat
     * @throws SailoException jos joku menee pieleen
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.File;
     * 
     * Tilaajat tilaajat = new Tilaajat();
     * Tilaajat tilaajat1 = new Tilaajat();
     * Tilaaja erno1 = new Tilaaja(), erno2 = new Tilaaja();
     * erno1.taytaPenaPenttila();
     * erno2.taytaPenaPenttila();
     * tilaajat.lisaa(erno1);
     * tilaajat.lisaa(erno2);
     * tilaajat.tallenna();
     * tilaajat1  = tilaajat;
     * tilaajat = new Tilaajat();
     * tilaajat.lueTiedosto();
     * tilaajat.getLkm() === tilaajat1.getLkm();
     *</pre>
     */
    public void tallenna() throws SailoException{
     
        File ftied = new File("tuotteet/tilaajat.dat");
        try( PrintWriter fo = new PrintWriter (new FileWriter(ftied.getCanonicalPath()))){
        //
           fo.println("Haaparannan ostoslista");
           fo.println(alkiot.length);
           for (int i = 0; i < getLkm(); i++) {
                Tilaaja tilaaja = anna(i);
                fo.println(tilaaja.toString());
           }
           
           
        } catch (FileNotFoundException ex) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch (IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjotta");
            
            
        }
        
    }
    
    
    /**
     * Hoitaa tilaajan poistamisen.
     * @param id poistettavan tilaajan id.
     * @return palauttaa 1 jos on poistettu jotain.
     * @example
     * <pre name="test">
     * Tilaajat tilaajat = new Tilaajat();
     * Tilaaja erno1 = new Tilaaja(),  erno2 = new Tilaaja(), erno3 = new Tilaaja();
     * erno1.rekisteroi(); erno2.rekisteroi(); erno3.rekisteroi();
     * int id1 = erno1.getTunnusNro();
     * tilaajat.lisaa(erno1); tilaajat.lisaa(erno2); tilaajat.lisaa(erno3);
     * tilaajat.getLkm() === 3; 
     * tilaajat.poista(id1 +1 ) ===1; tilaajat.getLkm() === 2;
     * tilaajat.poista(id1) === 1; tilaajat.getLkm() === 1;
     * tilaajat.poista(id1+3) === 0; tilaajat.getLkm() === 1;
     * </pre>
     */
    public int poista(int id) {
        int ind = etsiId(id);
        if (ind < 0) return 0;
        lkm --;
        for(int i = ind; i< lkm; i++)
            alkiot[i] = alkiot [i + 1] ;
        alkiot[lkm] = null;
        return 1;
    }
    
    
    /** Etsii annettua id vastaavan indeksin
     * @param id numero jota vastaavaa indeksiä etsitään
     * @return i joka on id:tä vastaava indeksi tai -1, jos ei löydy
     * @example
     * <per name="test">
     * Tilaajat tilaajat = new Tilaajat();
     * Tilaaja erno1 = new Tilaaja(),  erno2 = new Tilaaja(), erno3 = new Tilaaja();
     * erno1.rekisteroi(); erno2.rekisteroi(); erno3.rekisteroi();
     * int id1 = erno1.getTunnusNro();
     * tilaajat.lisaa(erno1); tilaajat.lisaa(erno2); tilaajat.lisaa(erno3);
     * tilaajat.etsiId(id1 +1) === 1;
     * tilaajat.etsiId(id1 + 2) === 2;
     * </pre>
     */
    public int etsiId(int id) {
        for (int i = 0; i < lkm; i++) 
            if (id == alkiot[i].getTunnusNro()) return i;
        return -1;
    }
   

    
    
    /**
     * lisää tilaajan tietoon uuden lisäyksen tai korvaa jo olemassa olevan
     * @param tilaaja Tilaaja
     * @throws SailoException poikkeus jos ei mahu
     * @example
     * <pre name="test">
     *  #THROWS SailoException
     * Tilaajat tilaajat = new Tilaajat();
     * Tilaaja erno1 = new Tilaaja(), erno2 = new Tilaaja();
     * erno1.rekisteroi(); erno2.rekisteroi();
     * tilaajat.getLkm() ===0;
     * tilaajat.korvaaTaiLisaa(erno1); tilaajat.getLkm() === 1;
     * tilaajat.korvaaTaiLisaa(erno2); tilaajat.getLkm() === 2;
     * tilaajat.korvaaTaiLisaa(erno1); tilaajat.getLkm() === 2;
     * </pre>
     */
    public void korvaaTaiLisaa(Tilaaja tilaaja) throws SailoException {
        int id = tilaaja.getTunnusNro();
        for (int i = 0; i < lkm; i++) {
            if (alkiot[i].getTunnusNro() == id) {
            alkiot[i] = tilaaja;
          //  muutettu = true;
            return;
        }
        }
        lisaa(tilaaja);
    }
    
    
    
    /**
     * etsii  tietyt tilaajat ehdoilla
     * @return palauttaa tilaajat
     */
    public Collection<Tilaaja> etsi() { 
              Collection<Tilaaja> loytyneet = new ArrayList<Tilaaja>(); 
               for (Tilaaja tilaaja : this.alkiot ) { 
                   loytyneet.add(tilaaja);  
               } 
                return loytyneet; 
           }
    
    
    /**
     * Luetaan tilaajat -tiedosto
     * @throws SailoException jos vikaa
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.File;
     * 
     * Tilaajat tilaajat = new Tilaajat();
     * Tilaajat tilaajat1 = new Tilaajat();
     * Tilaaja erno1 = new Tilaaja(), erno2 = new Tilaaja();
     * erno1.taytaPenaPenttila();
     * erno2.taytaPenaPenttila();
     * tilaajat.lisaa(erno1);
     * tilaajat.lisaa(erno2);
     * tilaajat.tallenna();
     * tilaajat1  = tilaajat;
     * tilaajat = new Tilaajat();
     * tilaajat.lueTiedosto();
     * tilaajat.getLkm() === tilaajat1.getLkm();
     *</pre>
     * 
     */
    public void lueTiedosto() throws SailoException {
        File ftied = new File("tuotteet/tilaajat.dat");
        try ( BufferedReader fi = new BufferedReader( new FileReader(ftied.getCanonicalPath()))) {
              String kokoNimi = fi.readLine();
              if ( kokoNimi == null) throw new SailoException("Ostoslistan nimi puuttuu");
              String rivi = fi.readLine();
              if( rivi == null) throw new SailoException ("Maksimikoko puuttuu");
              while (true) {
                  rivi = fi.readLine();
                  if (rivi == null) break;
                  rivi = rivi.trim();
                  if ("".equals(rivi) || rivi.charAt(0) == ';') continue;
                  Tilaaja tilaaja = new Tilaaja();
                  tilaaja.parse(rivi);
                  lisaa(tilaaja);
              }
                    } catch (FileNotFoundException e) {
                        throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
                    } catch (IOException e) {
                        throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
                    }
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args)  {
        Tilaajat tilaajat = new Tilaajat();
        
        Tilaaja pena = new Tilaaja(), pena2 = new Tilaaja();
        pena.rekisteroi();
        pena.taytaPenaPenttila();
        pena2.rekisteroi();
        pena2.taytaPenaPenttila();
        
       
            tilaajat.lisaa(pena);
            tilaajat.lisaa(pena2);
            tilaajat.lisaa(pena2);
            tilaajat.lisaa(pena2);
            tilaajat.lisaa(pena2);
            tilaajat.lisaa(pena2);
            tilaajat.lisaa(pena2);
            tilaajat.lisaa(pena2);
            tilaajat.lisaa(pena2);
            tilaajat.lisaa(pena2);
            tilaajat.lisaa(pena2);
            tilaajat.lisaa(pena2);
            
       
       
        
        System.out.println("============= Tilaajat testi ================");
        for(int i = 0; i < tilaajat.getLkm(); i++) {
            Tilaaja tilaaja = tilaajat.anna(i);
            System.out.println("Tilaaja paikassa: " + i);
            tilaaja.tulosta(System.out);
        }
    
    }

    
    
    /**
     * hakee tilaajan 
     * @param i indeksi
     */
   
   
}
