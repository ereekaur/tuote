/**
 * 
 */
package tuote;

import java.util.Collection;

/**
 * Huolehtii tilaukset tuotteet ja tilaajat luokkien välisestä yhteistyöstä. lukee ja kirjoittaa ostoslistan tiedostoon.
 * @author Joonas Hartikainen ja Erno Kauranen
 * @version 25.11.2020
 * @version 12.12.2020
 *
 */
public class Ostoslista {
    
    Tilaajat tilaajat = new Tilaajat ();
    Tilaukset tilaukset = new Tilaukset ();
    Tuotet tuotteet = new Tuotet ();
    
    
    

    
    
    /**
     * Lisätään uusi tilaaja ostoslistaan
     * @param tilaaja lisättävä tilaaja.
     * @example
     * <pre name="test">
     *   Ostoslista ostoslista = new Ostoslista();
     *   Tilaaja pena1 = new Tilaaja(), pena2 = new Tilaaja();
     *   pena1.rekisteroi(); pena2.rekisteroi();
     *   ostoslista.getTilaajia() === 0;
     *   ostoslista.lisaaTilaaja(pena1); ostoslista.getTilaajia() === 1;
     *   ostoslista.lisaaTilaaja(pena2); ostoslista.getTilaajia() === 2;
     *   ostoslista.lisaaTilaaja(pena1); ostoslista.getTilaajia() === 3;
     *   ostoslista.annaTilaaja(0) === pena1;
     *   ostoslista.annaTilaaja(1) === pena2;
     *   ostoslista.annaTilaaja(2) === pena1;
     *   ostoslista.annaTilaaja(1) == pena1 === false;
     *   ostoslista.annaTilaaja(1) == pena2 === true;
     *   ostoslista.lisaaTilaaja(pena1); ostoslista.getTilaajia() === 4;
     *   ostoslista.lisaaTilaaja(pena1); ostoslista.getTilaajia() === 5;

     * </pre>
     */
    public void lisaaTilaaja(Tilaaja tilaaja) {
        tilaajat.lisaa(tilaaja);
    }
    
    /**
     * 
     * @param tilaus lisättävä tilaus
     * @example
     * <pre name="test">
     *   Ostoslista ostoslista = new Ostoslista();
     *   Tilaus tilaus1 = new Tilaus(), tilaus2 = new Tilaus();
     *   tilaus1.rekisteroiTilaus(); tilaus2.rekisteroiTilaus();
     *   ostoslista.getTilauksia() === 0;
     *   ostoslista.lisaaTilaus(tilaus1); ostoslista.getTilauksia() === 1;
     *   ostoslista.lisaaTilaus(tilaus2); ostoslista.getTilauksia() === 2;
     *   ostoslista.lisaaTilaus(tilaus1); ostoslista.getTilauksia() === 3;
     *   ostoslista.annaTilaus(0) === tilaus1;
     *   ostoslista.annaTilaus(1) === tilaus2;
     *   ostoslista.annaTilaus(2) === tilaus1;
     *   ostoslista.annaTilaus(1) == tilaus1 === false;
     *   ostoslista.annaTilaus(1) == tilaus2 === true;
     *   ostoslista.lisaaTilaus(tilaus1); ostoslista.getTilauksia() === 4;
     *   ostoslista.lisaaTilaus(tilaus1); ostoslista.getTilauksia() === 5;
     * </pre>
     */
    public void lisaaTilaus(Tilaus tilaus)  {
        tilaukset.lisaaTilaus(tilaus);
    }
    
    
    
    /**
     * etsii
     * @return palauttaa etsityt tilaajat
     */
    public Collection<Tilaaja> etsi() {
        return tilaajat.etsi();
    }
    
    /**
     * lisätään tuote
     * @param tuote lisättävä tuote
     * @throws SailoException jos ei mahu
     */
    public void lisaaTuote(Tuote tuote) throws SailoException {
        tuotteet.lisaaTuote(tuote);
    }
    
    /**
     * Palauttaa i:n tilaajan.
     * @param i monesko tilaaja palautetaan
     * @return viite i:teen tilaajaan
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Tilaaja annaTilaaja (int i) throws IndexOutOfBoundsException {
        return tilaajat.anna(i);
    }
    
    /**
     * Palauttaa i:n tilauksen
     * @param i monesko tilaus palautetaan
     * @return viite i:teen tilaukseen
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Tilaus annaTilaus (int i) throws IndexOutOfBoundsException {
        return tilaukset.annaTilaus(i);
    }
    
    /**
     * Antaa tilaajien lukumäärän
     * @return tilaajien lukumäärä
     */
    public int getTilaajia() {
        return tilaajat.getLkm();
    }
    
    /**
     * Luetaan tiedostot
     * @throws SailoException jos joku menee pieleen
     */
    public void lueTiedosto() throws SailoException {
        tilaajat.lueTiedosto();
        tilaukset.lueTiedosto();
        // tuotteet.lueTiedosto;
    }
    
    /**
     * 
     * @param tilaaja kenen tilauksia etsitään
     * @return tilaukset
     */
    public Tilaukset annaTilaukset(Tilaaja tilaaja) {
       return tilaukset.annaTilaukset(tilaaja.getTunnusNro());
    }
    
    
    /**
     * Korvaa tilaajan tietorakenteessa. Ottaa Tilaajan omistukseensa
     * Etsitään samalla tunnusnumerolla oleva tilaaja. Jos ei löydy, niin lisätään uutena tilaajana.
     * @param tilaaja lisättävän tilaajan viite. Huom tietorakenne muuttuu omistajaksi.
     * @throws SailoException jos tietorakenne on jo täynnä
     */
    public void korvaaTaiLisaa(Tilaaja tilaaja) throws SailoException {
        tilaajat.korvaaTaiLisaa(tilaaja);
    }
    
    
    /**
     * Tilauksen poistamiseen käytettävä ohjelma, joka ohjaa poiston oikealle aliohjelmalle.
     * @param tilaus poistettava tilaus
     */
    public void poistaTilaus(Tilaus tilaus) {
        tilaukset.poista(tilaus.getTilausNro());
        
    }
    
    
    /**
     * Tilaajan poistamiseen käytettävä ohjelma, joka ohjaa poiston oikeille aliohjelmille.
     * @param tilaaja poistettava tilaaja
     * @return palauttaa 1, jos on poistettu jotain, muuten 0
     */
    public int poista(Tilaaja tilaaja) {
        if ( tilaaja == null) return 0;
        int ret = tilaajat.poista(tilaaja.getTunnusNro());
        tilaukset.poistaTilaajanTilaukset(tilaaja.getTunnusNro());
        return ret;
    }
    
    /**
     * tallentaa molemmat tiedostot
     * @throws SailoException eoak
     */
    public void tallenna()  throws SailoException {
        String virhe = "";
        
        try {
            tilaajat.tallenna();
        } catch (SailoException e) {
            // 
           virhe +=e.getMessage();
        }
        
        
        try {
             tilaukset.tallenna(); 
        } catch (SailoException e) {
            // 
            virhe +=e.getMessage();
        }
        
        try {
            tuotteet.tallenna();
        } catch (SailoException e) {
            // 
            virhe +=e.getMessage();
   }
   
        
         if (virhe.length() > 0 )
             throw new SailoException(virhe);
    
    }
    /**
     * Antaa tilausten lukumäärän
     * @return tilaajien lukumäärä
     */
    public int getTilauksia() {
        return tilaukset.getTilaustenLkm();
    }
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args)  {
        Ostoslista ostoslista = new Ostoslista ();
    
        Tilaaja pena = new Tilaaja(), pena2 = new Tilaaja();
        pena.rekisteroi();
        pena.taytaPenaPenttila();
        pena2.rekisteroi();
        pena2.taytaPenaPenttila();
    

            ostoslista.lisaaTilaaja(pena);
            ostoslista.lisaaTilaaja(pena2);
            ostoslista.lisaaTilaaja(pena2);
            ostoslista.lisaaTilaaja(pena2);
            ostoslista.lisaaTilaaja(pena2);
            ostoslista.lisaaTilaaja(pena2);
            

        
        int id1 = pena.getTunnusNro();
        int id2 = pena2.getTunnusNro();
        
        Tilaus tilaus = new Tilaus(id1),  tilaus2 = new Tilaus(id2);
        tilaus.rekisteroiTilaus();
        tilaus.taytaHenkiloTunnusSatTilaus(1);
        tilaus2.rekisteroiTilaus();
        tilaus2.taytaHenkiloTunnusSatTilaus(2);
        
        

            ostoslista.lisaaTilaus(tilaus);
            ostoslista.lisaaTilaus(tilaus2);


      
        
        System.out.println("============= Ostoslista testi ================");
        for(int i = 0; i < ostoslista.getTilaajia(); i++) {
            Tilaaja tilaaja = ostoslista.annaTilaaja(i);
            System.out.println("Tilaaja paikassa: " + i);
            tilaaja.tulosta(System.out);
        }
        for(int i = 0; i < ostoslista.getTilauksia(); i++) {
            Tilaus tilaus1 = ostoslista.annaTilaus(i);
            System.out.println("Tilaus paikassa " + i);
            tilaus1.tulostaTilaus(System.out);
        }
        
        Tilaukset loytyneet = ostoslista.annaTilaukset(pena2);
        for (int i = 0; i < loytyneet.getTilaustenLkm(); i++) {
            Tilaus tilaus6 = loytyneet.annaTilaus(i);
            System.out.print(tilaus6.getHenkiloTunnusNro() + " ");        
            tilaus6.tulostaTilaus(System.out);
        }
            

}

   
}