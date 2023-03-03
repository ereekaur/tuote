/**
 * 
 */
package tuote;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Comparator;

import fi.jyu.mit.ohj2.Mjonot;


/**
 * Tietää tilaajan kentät. Osaa tarkistaa tiettyjen kenttien oikeellisuuden.
 * Tunnusnumeron ylläpito
 * @author Joonas Hartikainen ja Erno Kauranen
 * @version 17.3.2019
 * @version 1.1 12.12.2020
 *
 */
public class Tilaaja implements Cloneable {
    
    private int tunnusNro;
    private String etuNimi = "";
    private String sukuNimi = "";
    private String puhelinNumero;
    private String osoite = "";
    private int tilauksenHinta = 54;
    private double maksettu;
    private String toimituspaiva= "";
    
    private static int seuraavaNro = 1;
    
    

    private Tilaukset tilaukset = new Tilaukset();
    /**
     * Antaa jäsenelle seuraavan rekisterinumeron.
     * @return jäsenen uusi tunnusNro
     * @example
     * <pre name="test">
     *    Tilaaja pena1 = new Tilaaja();
     *    pena1.getTunnusNro() === 0;
     *    pena1.rekisteroi();
     *    pena1.rekisteroi();
     *    pena1.rekisteroi();
     *    pena1.rekisteroi();
     *    Tilaaja pena2 = new Tilaaja();
     *    pena2.rekisteroi();
     *    int n1 = pena1.getTunnusNro();
     *    int n2 = pena2.getTunnusNro();
     *    n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        if ( tunnusNro != 0) return tunnusNro;
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    /**
     * @return Kenttien lukumäärä.
     */
    public int getKenttia() {
        return 8;
    }

    /**
     * @return Ensimmäinen kenttä mistä tieto otetaan.
     */
    public int ekaKentta() {
        return 1;
    }

    /**
     * Palauttaa k:tta tilaajan kenttää vastaavan kysymyksen
     * @param k kuinka monennen kentän kysymys palautetaan
     * @return k:netta kenttää vastaava kysymys
     */
    public String getKysymys(int k) {
        switch ( k ) {
        case 0: return "Tunnus nro";
        case 1: return "Etunimi";
        case 2: return "Sukunimi";
        case 3: return "Puhelinnumero";
        case 4: return "Osoite";
        case 5: return "Tilauksen hinta";
        case 6: return "Maksettu";
        case 7: return "Toimituspäivä";
        default: return "Äääliö";
        }


    }
    
    /**
     * Antaa k:nnen alkion kentän tiedot
     * @param k kentän indeksi
     * @return kentän arvo
     */
    public String anna(int k ) {
        switch (k ) {
        case 0: return "" + tunnusNro;
        case 1: return "" + etuNimi;
        case 2: return "" + sukuNimi;
        case 3: return "" + puhelinNumero;
        case 4: return "" + osoite;
        case 5: return "" + tilauksenHinta;
        case 6: return "" + maksettu;
        case 7: return "" + toimituspaiva;
        default: return "Ääliö";
        }
    }
    
    
    /**
     * Vertailee
     *
     */
    public static class Vertailija implements Comparator<Tilaaja> { 
                  private int k;  
                  
                  @SuppressWarnings("javadoc") 
                  public Vertailija(int k) { 
                      this.k = k; 
                  } 
                   
                  @Override 
                  public int compare(Tilaaja tilaaja1, Tilaaja tilaaja2) { 
                      return tilaaja1.anna(k).compareToIgnoreCase(tilaaja2.anna(k)); 
                  } 
              } 

  
       
    
    /**
     * Asettaa tilaajan tiedot tolppaerotellusta merkkijonosta
     * @param k casen numero jota käsitellään
     * @param jono jono joka trimmataan 
     * @return null jos ei virhettä
     */
    public String aseta(int k, String jono) {
        String tjono = jono.trim();
        StringBuffer sb = new StringBuffer(tjono);
        switch (k) {
        case 0:
            setTunnusNro(Mjonot.erota(sb,  '|', getTunnusNro()));
            return null;
        case 1:
            etuNimi = tjono;
            return null;
        case 2: 
            sukuNimi = tjono;
            return null;
        case 3:
            if (tjono.length() != 10) return "Puhelinnumero liian pitkä/lyhyt";
            puhelinNumero = tjono;
            return null;
        case 4: 
            osoite = tjono;
            return null;
        case 5:
            setTilauksenHinta(Mjonot.erota(sb,  '|', getTunnusNro()));
            return null;
            
        case 6: try {
                    maksettu = Mjonot.erotaEx(sb, '|', maksettu);
        } catch (NumberFormatException ex) {
            return "Maksettu hinta väärin  " + ex.getMessage();
        }
        return null;
        
        case 7: 
            toimituspaiva = tjono;
            return null;
        default : return "";
        }
    }
    
    /**
     * Haetaan tilaajan nimi
     * @return tilaajan etunimi ja sukunimi
     */
    public String getNimi() {
        return etuNimi + " " + sukuNimi;
    }
    

    
    @Override
    public Tilaaja clone() throws CloneNotSupportedException {
        Tilaaja uusi = (Tilaaja) super.clone();
        return uusi;
    }
    
    
    /**
     *  Selvittää jäsenen tiedot tolppaerotellusta merkkijonosta
     * 
     * 
     * @param rivi josta jäsenen tiedot otetaan
     * 
     * @example
     * <pre name="test">
     *  Tilaaja tilaaja = new Tilaaja();
     *  tilaaja.parse("256 | Seppo Taalasmaa | 040333333|");
     *  tilaaja.getTunnusNro() === 256;
     *  
     *  </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
       for ( int k = 0; k < getKenttia(); k++)
           aseta(k, Mjonot.erota(sb, '|'));
        
        
    }
    
    
    /**
     * Vertailee kahta tilaajaa.
     * @param tilaaja vertailtava tilaaja
     * @return onko tilaajat samat
     */
    public boolean equals(Tilaaja tilaaja) {
        if (tilaaja == null ) return false;
        for (int k = 0; k < getKenttia(); k++) 
            if ( !anna(k).equals(tilaaja.anna(k))) return false;
        return true;
    }
    
    @Override
    public boolean equals(Object tilaaja) {
        if ( tilaaja instanceof Tilaaja) return equals((Tilaaja) tilaaja);
        return false;
    }
    
    /**
     * Asettaa tunnusnumeron tilaajalle
     * @param nr tunnusnumero, mitä tarjotaan asetettavaksi
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
        
    }
    /**
     * asettaa tilauksen hinnan tietyn tilaajan tuotteille
     * @param henkiloTunnusNro tilaajan tunnusnro
     */
    private void setTilauksenHinta(int henkiloTunnusNro) {
        tilauksenHinta = tilaukset.tilaustenHinta(henkiloTunnusNro);
    }
    
   
    @Override
    public int hashCode() {
        return tunnusNro;
    }

    /**
     * Palauttaa tilaajan tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return tilaaja tolppaeroteltuna merkkijonona
     * @example
     * <pre name="test">
     *  Tilaaja tilaaja = new Tilaaja();
     *  tilaaja.parse("1337|matti|000");
     *  tilaaja.toString().startsWith("1337|matti|000|") === true; 
     *  tilaaja.parse("4|Seppo Taalasmaa|0401234567");
     *  tilaaja.toString().startsWith("4|Seppo Taalasmaa|0401234567|") === true; 
     *  </pre>
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("");
        String erotin = "";
        for ( int k = 0; k < getKenttia(); k++) {
          sb.append(erotin);
          sb.append(anna(k));
          erotin = "|" ;
    }
    return sb.toString();
   }
    
    
    /**
     * Tulostetaan henkilön tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro, 3) + " " + etuNimi + " " + sukuNimi);
        out.println(" " + puhelinNumero + " " + osoite );
        out.println( " Tilauksen hinta: " + tilauksenHinta + ". Maksettu " + maksettu + ". ");
        out.println(toimituspaiva);
    }
    
    /**
     * Tulostetaan henkilön tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
   
    
    /**
     * Palauttaa tilaajan tunnusnumeron
     * @return tunnusnumero
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    

    /**
     * @return Palauttaa etunimen
     */
    public String getEtunimi() {
        
        return etuNimi;
    }

    /**
     * @return Palauttaa sukunimen
     */
    public String getSukunimi() {
        return sukuNimi;
      
    }

    /**
     * @return Palauttaa puhelinnumeron
     */
    public String getPuhelinnumero() {
        return puhelinNumero;
    }

    /**
     * @return Palauttaa osoitteen
     */
    public String getOsoite() {
        return osoite;
    }

     /**
     * @return Palauttaa toimituspäivän
     */
    public String getToimituspaiva() {
        return toimituspaiva;
    }
    

    /** 
     * @param s tilaajalle laitettava etunimi
     * @return virheilmoitus, null jos ok.
     */
    public String setEtunimi(String s) {
        etuNimi =  s;
        return null;
    }
    
    
    /**
     * @param s tilaajalle laitettava sukunimi
     * @return virheilmoitus, null jos ok.
     */
    public String setSukunimi(String s) {
        sukuNimi = s;
        return null;
    }
    
    /**
     * @param s tilaajalle laitettava puhelinnumero
     * @return virheilmoitus, null jos ok.
     */
    public String setPuhelinnumero (String s) {
        if (s.length() != 10) return "Puhelinnumero liian pitkä/lyhyt";
        puhelinNumero = s;
        return null;
    }
    
    
   /**
    * @param s tilaajalle laitettava osoite
    * @return virheilmoitus, null jos ok.
    */
    public String setOsoite (String s) {
        osoite = s;
        return null;
    }
    
    
    /**
     * @param s Tilaajalle annettava toimituspäivä
     * @return virheilmoitus, null jos ok.
     */
    public String setToimituspaiva (String s) {
        toimituspaiva = s;
        return null;
    }
    
    
    /**
     * Apumetodi jolla saadaan täytettyä testiarvo tilaajalle.
     */
    public void taytaPenaPenttila() {
        etuNimi = "Pena";
        sukuNimi = "Penttila " +rand(1000, 9999);
        puhelinNumero = "0400123123";
        osoite = "Puistonpenkki 4";
        tilauksenHinta = 54;
        maksettu = 54;
        toimituspaiva = "Sama";
    }
    
    /**
     * Arvotaan satunnainen kokonaisluku välille [ala,yla]
     * @param ala arvonnan alaraja
     * @param yla arvonnan ylaraja
     * @return satunnainen luku väliltä [ala,yla]
     */
    public static int rand(int ala, int yla) {
        double n = (yla-ala)*Math.random() + ala;
        return (int)Math.round(n);
    }
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Tilaaja pena = new Tilaaja();
        Tilaaja pena2 = new Tilaaja();
        pena.rekisteroi();
        pena2.rekisteroi();
        
        pena.tulosta(System.out);
        pena2.tulosta(System.out);
        
       
        pena.taytaPenaPenttila();
        pena2.taytaPenaPenttila();
        
        pena.tulosta(System.out);
        pena2.tulosta(System.out);
        
    }

  
    
}


