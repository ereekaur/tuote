/**
 * 
 */
package tuote;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Tietää eri nuuskien nimet ja osaa antaa i:n kentän tuoten tiedoista.
 * 
 * @author Joonas Hartikainen ja Erno Kauranen
 * @version 1.12.2020
 *
 */
public class Tuote {
    
    private int tuoteTunnusNro;
    private String nimiTuote;
    private int tuoteHinta = 0;
    
    private static int seuraavaTuoteNro = 1;
    
    

    
    /**
     * Antaa tuoten rekisterinumeron.
     * @return Tuoten uusi  tunnusnumero
     * <pre name="test">
     *   Tuote tuote1 = new Tuote();
     *   tuote1.getTuoteTunnusNro() === 0;
     *   tuote1.rekisteroiTuote();
     *   Tuote tuote2 = new Tuote();
     *   tuote2.rekisteroiTuote();
     *   int n1 = tuote1.getTuoteTunnusNro();
     *   int n2 = tuote2.getTuoteTunnusNro();
     *   n1 + 1 === n2;
     * </pre>
     */
    public int rekisteroiTuote() {
        if ( tuoteTunnusNro != 0) return tuoteTunnusNro;
        tuoteTunnusNro = seuraavaTuoteNro;
        seuraavaTuoteNro++;
        return tuoteTunnusNro;
    }
    
    /**
     * alustetaan tuote. Toistaiseksi ei tarvitse tehdä mitään.
     */
    public Tuote() {
        // Vielä ei tarvita mitään
    }
    
    /**
     * Apumetodi, jolla täytetään testiarvo tuotelle
     */
    public void taytaOdenColdDry() {
        nimiTuote = "Odens Cold Dry";
        tuoteHinta = 27;
    }
    
   
    /**
     * Laitetaan tiedot nuuskista tallennettavaan tekstimuotoon.
     */
    @Override
    public String toString() {
        return "" + 
    getTuoteTunnusNro() + "|" +
    nimiTuote + "|" +
    tuoteHinta;
    
    }
    
    /**
     * Palauttaa tietyn Id numeron tuoten nimen
     * @return tuoten nimi, tai teksti Ei löydy.
     */
    public String getTuoteNimi() {
        return nimiTuote;
    }
    /**
     * Tulostetaan tuoten tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulostaTuote(PrintStream out) {
        out.println("Tuoten nimi: " + nimiTuote + ". " + "Tuoten hinta: " + tuoteHinta +"€");
    }
    
    
    /**
     * Tulostetaan Tuoten tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulostaTuote(OutputStream os) {
        tulostaTuote(new PrintStream(os));
    }

    /**
     * Palauttaa tuoten tunnusnumeron
     * @return tuoten tunnusnumero
     */
    public int getTuoteTunnusNro() {
        return tuoteTunnusNro;
    }
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Tuote tuote = new Tuote();
        tuote.rekisteroiTuote();
        tuote.taytaOdenColdDry();
        tuote.tulostaTuote(System.out);
    
    }

}
