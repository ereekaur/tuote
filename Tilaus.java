/**
 * 
 */
package tuote;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Tietää tilausten kentät. Tunnusnumeron ylläpito. Osaa täydentää tilausta ja antaa sen kenttiä.
 * @author Joonas Hartikainen ja Erno Kauranen
 * @version 25.11.2020
 * @1.1 12.12.2020
 *
 */
public class Tilaus {

    private int tilausNro;
    private int henkiloTunnusNro;
    private String tuotteenNimi = "";
    private int tuotteenHinta = 0;
    private int maara = 0;
    
    
    private static int seuraavaTilausNro = 1;
    
    /**
     * Alustetaan tilaus. Toistaiseksi ei tarvitse tehdä mitään
     */
    public Tilaus() {
        //vielä ei tarvita mitään
    }
    
    
    /**
     * Palauttaa kysyttävän tilauksen tietyn kentän arvon
     * @param k halutun kentän indeksi
     * @return palauttaa kentän tiedot
     */
    public String anna(int k) {
        switch (k) {
        case 0: return "" + tilausNro;
        case 1: return "" + henkiloTunnusNro;
        case 2: return "" + tuotteenNimi;
        case 3: return "" + tuotteenHinta;
        case 4: return "" + maara;
        default: return "Ääliö";
        }
    }
    
    

    
    
    /**
     * Alustetaan tietylle henkilölle tilaus ilman tuotea
     * @param tunnusNro henkilön tunnusnro
     */
    public Tilaus(int tunnusNro) {
        henkiloTunnusNro = tunnusNro;
    }
    
    /**
     * Antaa tilaukselle rekisterinumeron.
     * @return Tilauksen uusi tunnusNro
     * <pre name="test">
     *   Tilaus tilaus1 = new Tilaus();
     *   tilaus1.getTilausNro() === 0;
     *   tilaus1.rekisteroiTilaus();
     *   Tilaus tilaus2 = new Tilaus();
     *   tilaus2.rekisteroiTilaus();
     *   int n1 = tilaus1.getTilausNro();
     *   int n2 = tilaus2.getTilausNro();
     *   n1 + 1 === n2;
     * </pre>
     */
    public int rekisteroiTilaus() {
        if ( tilausNro != 0) return tilausNro;
        tilausNro = seuraavaTilausNro;
        seuraavaTilausNro++;
        return tilausNro;
    }
    
    
    /**
     * @return palauttaa ensimmäisen "oikean kentän" indeksin
     */
    public int ekaKentta() {
        return 2;
    }


    /**
     * @return palauttaa kenttien lukumäärän
     */
    public int getKenttia() {
        return 5;
    }

    /**
     * antaa tuotteen hinnan
     * @return tuotteen hinta
     */
    public int gettuotteenHinta() {
        return tuotteenHinta;
    }

    
    
    /**
     * antaa tuotteen määrän
     * @return tuotteen määrä
     */
    public int gettuotteenMaara() {
        return maara;
    }
    
    /**
     * antaa tekstit kysymysboksiin.
     * @param k kentän numero
     * @return palauttaa kysymyksen.
     */
    public String getKysymys(int k) {
        switch (k) {
        case 0: return "Tilaus nro";
        case 1: return "Henkilön tunnusnro";
        case 2: return "tuotteen nimi";
        case 3: return "tuotteen hinta";
        case 4: return "tuotteen määrä";
        default: return "Ääliöt";
        }
    }

    
    /**
     * @param Asettaa tilausnumeron
     */
    private void setTilausNro(int nr) {
        tilausNro = nr;
        if(tilausNro >= seuraavaTilausNro) seuraavaTilausNro = tilausNro +1;
    }
    
    /** Asettaa tilauksen tiedot
     * 
     * @param k kokonaisluku
     * @param jono josta haluttu tieto erotellaan
     * @return todo
     */
    public String aseta(int k, String jono) {
        String tjono = jono.trim();
        StringBuffer sb = new StringBuffer(tjono);
        switch (k) {
        case 0: 
            setTilausNro(Mjonot.erota(sb, '|', getTilausNro()));
            return null;
        case 1: 
          henkiloTunnusNro = Mjonot.erota(sb, '|', henkiloTunnusNro);
        return null;
        case 2:
                 tuotteenNimi = tjono;
             return null;
        case 3:
            try {
                tuotteenHinta = Mjonot.erotaEx(sb, '|', tuotteenHinta);
            } catch (NumberFormatException ex) {
                return "tuotteen hinta " + ex.getMessage();
            }
            return null;
        case 4:
            try {
                maara = Mjonot.erotaEx(sb, '|', maara);
            } catch (NumberFormatException ex) {
                return "tuotteen määrä " + ex.getMessage();
            }
            return null;
        default : return "";
        }
        
    }

       
    /**
     * Tulostaa tilauksen tiedot
     * @param out tietovirta mihin tulostetaan
     */
    public void tulostaTilaus(PrintStream out) {
        out.println(String.format("%03d", tilausNro, 3) + " " + henkiloTunnusNro + " " + tuotteenNimi + " " + tuotteenHinta + " " + maara );
    }
    
    
    /**
     * Tulostaa tilauksen tiedot
     * @param os tietovirta mihin tulostetaan
     */
    public void tulostaTilaus(OutputStream os) {
        tulostaTilaus(new PrintStream(os));
    }
    

    /**
     * Apumetodi, jolla täytetään testiarvot tilaukselle.
     * @param tunnus annettava henkilö id
     */
    public void taytaHenkiloTunnusSatTilaus(int tunnus) {
        henkiloTunnusNro =  tunnus;
        tuotteenNimi = "OdensColdDry";
        tuotteenHinta = 54;
        maara = 2;
        
    }
    
    
    
    /**
     * tolppaerottelee tekstin sopivaksi
     * @param rivi eroteltava teksti.
     * @example
     * <pre name="test">
     *  Tilaus tilaus = new Tilaus();
     *  tilaus.parse("01 | Seppo Taalasmaa | 040333333|");
     *  tilaus.getTilausNro() === 01;
     *  
     *  </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        for ( int k = 0; k < getKenttia(); k++)
            aseta(k, Mjonot.erota(sb, '|'));
    }
    
    
 
    /**
     * vertailee tilauksien kenttiä
     * @param tilaus vertailtava tilaus
     * @return onko tilauksissa eroa vai ei.
     */
    public boolean equals(Tilaus tilaus) {
        if ( tilaus == null ) return false;
        for (int k = 0; k < getKenttia(); k++)
            if ( !anna(k).equals(tilaus.anna(k))) return false;
        return true;
    }
    
    @Override
    public boolean equals(Object tilaus) {
        if (tilaus instanceof Tilaus) return equals((Tilaus) tilaus);
        return false;
    }
    
    /**
     * laittaa tiedot tilauksista tallennettavaan tekstimuotoon.
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("");
        String erotin = "";
        for ( int k = 0; k < getKenttia(); k++) {
            sb.append(erotin);
            sb.append(anna(k));
            erotin ="|" ;
        }
        return sb.toString();
    }
    
    
    
    /**
     * Palauttaa tilauksen tilausnumeron
     * @return tilauksen tilausnumero
     */
    public int getTilausNro() {
        return tilausNro;
    }
    
    
    /**
     * Palauttaa tilaukseen tehneen henkilön tunnusnumeron
     * @return tilauksen tehneen henkilön tunnusnumero.
     */
    public int getHenkiloTunnusNro () {
        return henkiloTunnusNro;
    }
    
    /**
     * palauttaa tilauksessa olevan tuotteen numeron
     * @return tuotean id numero
     */
    public String gettuotteenimi() {
        return tuotteenNimi;
    }
    
    @Override
    public int hashCode() {
     return tilausNro;
    }


    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Tilaus tilaus = new Tilaus();
        Tilaus tilaus2 = new Tilaus(); 
    
        tilaus.rekisteroiTilaus();
        tilaus2.rekisteroiTilaus();
        
        tilaus.tulostaTilaus(System.out);
        tilaus2.tulostaTilaus(System.out);
        
        
        tilaus.taytaHenkiloTunnusSatTilaus(2);
        tilaus2.taytaHenkiloTunnusSatTilaus(1);
        
        tilaus.tulostaTilaus(System.out);
        tilaus2.tulostaTilaus(System.out);
    
    }


   




}
