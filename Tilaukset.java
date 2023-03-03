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

/**
 * Hoitaa tilauksten käsittelyn uusien lisäyksen poiston yms. Ylläpitää varsinaista tilausrekisteriä.
 * @author Joonas Hartikainen ja Erno Kauranen
 * @version 29.11.2020
 *@version 14.12.2020
 */
public class Tilaukset {
    private static final int MAX_TILAUKSIA = 8;
    private int              lkm           = 0;
    @SuppressWarnings("unused") // tiedostonavaus toteuteaan ilman, että tätä tiedostonNimi kenttää käytetään suoraan avattavan tiedoston nimellä.
    private String           tiedostonNimi = "tuotteet\tilaukset.dat";
    private Tilaus[]         alkiot        = new Tilaus[MAX_TILAUKSIA];
    
    
    /**
     * Oletusmuodostaja
     */
    public Tilaukset() {
        // Attribuuttien oma alustus riittää
    }
    
    
    
    /**
     * Aliohjelma, joka kasvattaa taulukon kokoa.
     * @param taulukko kasvatettava taulukkoa
     * @return kasvatettu taulukko
     */
    public Tilaus[] kasvataKokoa(Tilaus[] taulukko) {
        Tilaus Tilaaja2[] = new Tilaus[taulukko.length * 2];

        for (int i = 0; i < taulukko.length; i++){
           Tilaaja2[i] = taulukko[i];
        }
       
        return Tilaaja2;
     }
    
    
    
    /**
     * Lisää uuden tilauksen tietorakenteeseen. Ottaa tilauksen omistukseensa
     * @param tilaus lisättävän tilauksen viite. Huom tietorakenne muuttuu omistajaksi
     * @example
     * <pre name="test">
     * Tilaukset tilaukset = new Tilaukset();
     * Tilaus tilaus1 = new Tilaus(), tilaus2 = new Tilaus();
     * tilaukset.getTilaustenLkm() === 0;
     * tilaukset.lisaaTilaus(tilaus1); tilaukset.getTilaustenLkm() === 1;
     * tilaukset.lisaaTilaus(tilaus2); tilaukset.getTilaustenLkm() === 2;
     * tilaukset.lisaaTilaus(tilaus1); tilaukset.getTilaustenLkm() === 3;
     * tilaukset.annaTilaus(0) === tilaus1;
     * tilaukset.annaTilaus(1) === tilaus2;
     * tilaukset.annaTilaus(2) === tilaus1;
     * tilaukset.annaTilaus(1) == tilaus1 === false;
     * tilaukset.annaTilaus(1) == tilaus2 === true;
     * tilaukset.lisaaTilaus(tilaus1); tilaukset.getTilaustenLkm() === 4;
     * tilaukset.lisaaTilaus(tilaus1); tilaukset.getTilaustenLkm() === 5;
     * </pre>
     */
    public void lisaaTilaus(Tilaus tilaus) {
        if (lkm >= alkiot.length) alkiot = kasvataKokoa(alkiot);
        alkiot[lkm] = tilaus;
        lkm++;
    }
    
    /**
     * Palauttaa viitteen i:teen tilaukseen
     * @param i monennko tilauksen viite halutaan
     * @return viite tilaukseen, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Tilaus annaTilaus(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
             throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    /**
     * lukee tiedoston, jossa tiedot tilauksista
     * @throws SailoException jos tiedoston lukeminen epäonnistuu
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.File;
     * 
     * Tilaukset tilaukset = new Tilaukset();
     * Tilaukset tilaukset1 = new Tilaukset();
     * Tilaus tilaus1 = new Tilaus(), tilaus2 = new Tilaus();
     * tilaus1.taytaHenkiloTunnusSatTilaus(1);
     * tilaus2.taytaHenkiloTunnusSatTilaus(2);
     * tilaukset.lisaaTilaus(tilaus1);
     * tilaukset.lisaaTilaus(tilaus2);
     * tilaukset.tallenna();
     * tilaukset1  = tilaukset;
     * tilaukset = new Tilaukset();
     * tilaukset.lueTiedosto();
     * tilaukset.getTilaustenLkm() === tilaukset1.getTilaustenLkm();
     *</pre>
     */
    public void lueTiedosto() throws SailoException {
        File ftied = new File("tuotteet/tilaukset.dat");
        try (BufferedReader fi = new BufferedReader( new FileReader(ftied.getCanonicalPath()))) {
            String kokoNimi = fi.readLine();
            if (kokoNimi == null) throw new SailoException("Tilaukseien nimi puuttuu");
            String rivi = fi.readLine();
            if(rivi == null) throw new SailoException("Maksimikoko puuttuu");
            while (true) {
                rivi = fi.readLine();
                if (rivi == null) break;
                rivi = rivi.trim();
                if ("".equals(rivi) || rivi.charAt(0) == ';') continue;
                Tilaus tilaus = new Tilaus();
                tilaus.parse(rivi);
                lisaaTilaus(tilaus);
        }
        }catch (FileNotFoundException e) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch (IOException e) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        }
    }
    
    
    /**
     * Palauttaa tilausten lukumäärän
     * @return tilausten lukumäärä
     *
     */
    public int getTilaustenLkm() {
        return lkm;
    }
    
    
    
    /**
     * Hakee tietyn henkilön tilaukset
     * @param tunnusnro henkilön tunnusnro
     * @return löydetyt tilaukset
     */
    public Tilaukset annaTilaukset(int tunnusnro) {
        Tilaukset loydetyt = new Tilaukset();
        for(int i = 0; i < lkm; i++) {
            Tilaus tilaus = alkiot[i];
            if(tilaus.getHenkiloTunnusNro() == tunnusnro)
                
                    loydetyt.lisaaTilaus(tilaus);
                
        }
        return loydetyt;
    }
    
    /**
     * Laskee tieytyn tilaajan kokonaistilauksen hinnan
     * @param henkilonTunnusnumero tilaajan tunnusnro
     * @return tilauksen kokonaishinta
     */
    public  int tilaustenHinta(int henkilonTunnusnumero) {
        int TilauksenHinta = 0;
        for(int i = 0; i < lkm; i++) {
            Tilaus tilaus = alkiot[i];
            if (tilaus.getHenkiloTunnusNro() == henkilonTunnusnumero) {
                TilauksenHinta = TilauksenHinta + (tilaus.getTuotenHinta() * tilaus.getTuotenMaara());
            }
        }
            return TilauksenHinta;
                
    }
    /**
     * Tallennetaan tilaajat
     * @throws SailoException jos joku menee pieleen
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * #import java.io.File;
     * 
     * Tilaukset tilaukset = new Tilaukset();
     * Tilaukset tilaukset1 = new Tilaukset();
     * Tilaus tilaus1 = new Tilaus(), tilaus2 = new Tilaus();
     * tilaus1.taytaHenkiloTunnusSatTilaus(1);
     * tilaus2.taytaHenkiloTunnusSatTilaus(2);
     * tilaukset.lisaaTilaus(tilaus1);
     * tilaukset.lisaaTilaus(tilaus2);
     * tilaukset.tallenna();
     * tilaukset1  = tilaukset;
     * tilaukset = new Tilaukset();
     * tilaukset.lueTiedosto();
     * tilaukset.getTilaustenLkm() === tilaukset1.getTilaustenLkm();
     *</pre>
     */
    public void tallenna() throws SailoException{
     
        File ftied = new File("tuotteet/tilaukset.dat");
        try( PrintWriter fo = new PrintWriter (new FileWriter(ftied.getCanonicalPath()))){
        //
           fo.println("Tilaukset");
           fo.println(alkiot.length);
           for (int i = 0; i < getTilaustenLkm(); i++) {
                Tilaus tilaus = annaTilaus(i);
                fo.println(tilaus.toString());
           }
           
           
        } catch (FileNotFoundException ex) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch (IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjotta");
            
            
        }
        
    }
    
    
    /**
     * yhden tilauksen poistamiseen käytettävä ohjelma.
     * @param id poistettavan tilauksen id.
     * @return  palauttaa 1, jos on poistettu jotain.
     * @example
     * <pre name="test">
     * Tilaukset tilaukset = new Tilaukset();
     * Tilaus tilaus1 = new Tilaus(), tilaus2 = new Tilaus(), tilaus3 = new Tilaus();
     * tilaus1.rekisteroiTilaus(); tilaus2.rekisteroiTilaus(); tilaus3.rekisteroiTilaus();
     * int id1 = tilaus1.getTilausNro();
     * tilaukset.lisaaTilaus(tilaus1); tilaukset.lisaaTilaus(tilaus2); tilaukset.lisaaTilaus(tilaus3);
     * tilaukset.getTilaustenLkm() === 3;
     * tilaukset.poista(id1 +1 ) === 1; tilaukset.getTilaustenLkm() === 2;
     * tilaukset.poista(id1) === 1; tilaukset.getTilaustenLkm() === 1;
     * tilaukset.poista(id1+3) === 0; tilaukset.getTilaustenLkm() === 1;
     * </pre>
     */
    public int poista(int id) {
        int ind = etsiId(id);
        if (ind < 0) return 0;
        lkm --;
        for(int i = ind; i < lkm; i++)
            alkiot[i] = alkiot[i + 1] ;
        alkiot[lkm] = null;
        return 1;
    }
    
    
    /**
     * tilaajan kaikkien tilausten poistamiseen käytetty ohjelma.
     * @param henkiloTunnusNro tilaajan tunnusnro jolta tilaukset poistetaan.
     * @return poistettujen tilausten määrän.
     * @example 
     * <pre name="test">
     * Tilaukset tilaukset = new Tilaukset();
     * Tilaus tilaus1 = new Tilaus(), tilaus2 = new Tilaus(), tilaus3 = new Tilaus();
     * tilaus1.taytaHenkiloTunnusSatTilaus(1); tilaus2.taytaHenkiloTunnusSatTilaus(1); tilaus3.taytaHenkiloTunnusSatTilaus(1);
     * tilaukset.lisaaTilaus(tilaus1); tilaukset.lisaaTilaus(tilaus2); tilaukset.lisaaTilaus(tilaus3);
     * tilaukset.getTilaustenLkm() === 3;
     * tilaukset.poistaTilaajanTilaukset(1) === 3;
     * tilaukset.getTilaustenLkm() === 0;
     * </pre>
     */
    public int poistaTilaajanTilaukset(int henkiloTunnusNro) {
        int n = 0;
        int apu = 0;
        for(int i = 0; i < lkm; i++) {
            if(apu == 1) {
                apu--;
                i--;
            }
      
            Tilaus tilaus = alkiot[i];
            while(lkm > 1 && alkiot[lkm-1].getHenkiloTunnusNro() == henkiloTunnusNro ) {
                alkiot[lkm] = null;
                lkm--;
                n++;
            }
        if (tilaus.getHenkiloTunnusNro() == henkiloTunnusNro) {
           lkm--;
           
           for (int k  = i ; k < lkm; k++) 
               alkiot[k] = alkiot[k +1];
           alkiot[lkm] = null;
           n++;
           if(lkm > 1) {
           if( alkiot[i].getHenkiloTunnusNro() == henkiloTunnusNro) apu++;
          
           }    
        }
        }
        return n;
            
        
    }
    
    /**
     * etsii tilauksen tilausnumeroa vastaavan alkion "taulukko"id:n
     * @param id etsittävä tilausnro
     * @return etsittävää tilausnumeroa vastaava taulukkoindeksi
      * @example
     * <pre name="test">
     * Tilaukset tilaukset = new Tilaukset();
     * Tilaus tilaus1 = new Tilaus(), tilaus2 = new Tilaus(), tilaus3 = new Tilaus();
     * tilaus1.rekisteroiTilaus(); tilaus2.rekisteroiTilaus(); tilaus3.rekisteroiTilaus();
     * int id1 = tilaus1.getTilausNro();
     * tilaukset.lisaaTilaus(tilaus1); tilaukset.lisaaTilaus(tilaus2); tilaukset.lisaaTilaus(tilaus3);
     * tilaukset.etsiId(id1 +1) === 1;
     * tilaukset.etsiId(id1 +2) ===2;
     */
    public int etsiId(int id) {
        for (int i = 0; i < lkm; i++) 
            if (id == alkiot[i].getTilausNro()) return i;
        return -1;
    }
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Tilaukset tilaukset = new Tilaukset();
        
        Tilaus tilaus = new Tilaus(),  tilaus2 = new Tilaus();
        tilaus.rekisteroiTilaus();
        tilaus.taytaHenkiloTunnusSatTilaus(1);
        tilaus2.rekisteroiTilaus();
        tilaus2.taytaHenkiloTunnusSatTilaus(2);
        

            tilaukset.lisaaTilaus(tilaus);
            tilaukset.lisaaTilaus(tilaus2);
            tilaukset.lisaaTilaus(tilaus);
            tilaukset.lisaaTilaus(tilaus);
            tilaukset.lisaaTilaus(tilaus2);
            tilaukset.lisaaTilaus(tilaus); 
            tilaukset.lisaaTilaus(tilaus);
            tilaukset.lisaaTilaus(tilaus2);
            tilaukset.lisaaTilaus(tilaus);

        tilaukset.poistaTilaajanTilaukset(1);
        
        int TilaustenHinta = tilaukset.tilaustenHinta(2);

        
        System.out.println("====================== Tilaukset testi ======================");
        System.out.println(TilaustenHinta);
        
        Tilaukset tilaus1 = tilaukset.annaTilaukset(2);
        
        for (int i = 0; i < tilaus1.getTilaustenLkm(); i++) {
            Tilaus tilaus6 = tilaus1.annaTilaus(i);
            System.out.print(tilaus6.getHenkiloTunnusNro() + " ");
            tilaus6.tulostaTilaus(System.out);
        }
        
        
        
        
        
        
        
        for (int i = 0; i < tilaukset.getTilaustenLkm(); i++) {
            Tilaus tilaus7 = tilaukset.annaTilaus(i);
            System.out.println("Tilaus paikassa: " + i);
            tilaus7.tulostaTilaus(System.out);
        }
        
        
        
        
    
    }





}
