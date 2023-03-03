package fxTuote;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import Tuote.Tilaaja;
import Tuote.Tilaajat;
import Tuote.Tilaukset;
import Tuote.Tilaus;
import Tuote.Ostoslista;
import Tuote.SailoException; 

/**
 * Luokka ostoslistan käyttöliittymän tapahtumien hoitamiseksi.   Tuote ja Tuotet luokat ovat jääneet vähän "kellumaan". Ne on toteutettu, kyllä mutta niitä ei juuri käytetä.
 * @author Joonas Hartikainen ja Erno Kauranen
 * @version 15.2.2019
 * @version  8.12.2020
 */
@SuppressWarnings("unused")  //tulostus laitettu kommentteihin ja tästä johtuen herjasi siitä, kun tätä ei käytetty.
public class TuoteGUIController implements Initializable {
    
    
    @FXML ListChooser<Tilaaja> chooserTilaajat;
    @FXML private ScrollPane panelTilaaja;
    @FXML private TextField editEtunimi;
    @FXML private TextField editSukunimi;
    @FXML private TextField editOsoite;
    @FXML private TextField editPuhelinnumero;
    @FXML private TextField editMaksettu;
    @FXML private TextField editTilauksenhinta;
    @FXML private TextField editToimituspaiva;
    @FXML private GridPane gridTilaaja;
    
    @FXML private StringGrid<Tilaus> tableTilaukset;
    
  
    /**
     * peruuttaa ohjelman käynnistämisen avausikkunassa
     */
    @FXML private void handlePeruutaAvaus() {
        Dialogs.showMessageDialog("Vielä ei osata peruuttaa");
    }
    
    /**
     * Avaa tiedot ikkunan.
     */
    @FXML private void handleTietoja() {
        ModalController.showModal(TuoteGUIController.class.getResource("TuoteTietojaView.fxml"), "Tietoja", null, "");    }
    
    /**
     * Avaa selaimella ht sivun
     */
    @FXML private void handleApua() {
        avustus();
    }
    
    /**
     * Avaa uuden tuotteen lisäys ikkunan
     */
    @FXML private void handleLisaaTilaus() {
       uusiTuote(); 
    }
    
    /**
     * Poistaa tuotteen.
     */
    @FXML private void handlePoistaTilaus() {
        poistaTilaus();
    }
    
    /**
     * Sulkee ohjelman.
     */
    @FXML private void handleLopeta() {
        boolean vastaus = Dialogs.showQuestionDialog("Lopetus", "Haluatko varmasti lopettaa?", "Kyllä", "Ei");
        if (vastaus == true) {
        System.exit(0);
        }
        Dialogs.showMessageDialog("vielä ei osata palata takaisin");
    }
    
    /**
     * Avaa tulostus ikkunan.
     * @throws IOException 
     */
    @FXML private void handleTulostaApu() throws IOException {
       // ModalController.showModal(TuoteGUIController.class.getResource("TuoteTulostaView.fxml"), "Tulosta", null, "");
        TulostaController tulostaCtrl = TulostaController.tulosta(null);
        tulostaValitut(tulostaCtrl.textAlue);
    }
    
    /**
     * Tulostaa ostoslistan.
     */
    @FXML private void handleTulosta() {
        Dialogs.showMessageDialog("vielä ei osata tulostaa");
    }
    
    /**
     * Avaa tyhjän tiedot-ikkunan keskelle käyttöliittymää
     */
    @FXML private void handleLisaaTilaaja() {
      
      uusiTilaaja();  
    }
    

    
    /**
     * Aktivoi tilaajantietojen muokkauksen
     */
    @FXML private void handleMuokkaa() {
        muokkaa();
    }
    
    /**
     * Poista tilaajan
     */
    @FXML private void handlePoistaTilaaja() {
        poistaTilaaja();
    }
    
    /**
     * Tallentaa tehdyt muutokset
     */
    @FXML private void handleTallenna() {
       tallenna();
    }

    /**
     * Tarkistetaan on tallennus tehty
     * @return true jos saa sulkea sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
    
    // ---------------------------------------------------------------------
     private TextField[] edits;
    
    /**
     * Alustaa ostoslistan tallennetuilla tiedoilla.
     */
    protected void lueTiedosto() {
        try {
            ostoslista.lueTiedosto();
            hae(0);
        } catch (SailoException e) {
           Dialogs.showMessageDialog(e.getMessage());
        }
    }

    /**
     * Tietojen tallennus
     */
    private void tallenna() {
        
        try {
            ostoslista.tallenna();
        } catch (SailoException e) {
            //
            Dialogs.showMessageDialog("Tuli virhe: " + e.getMessage());
        }
    }
    
    /**
     * Avaa aiemmin tallennetun ostoslistan.
     */
    public  void avaa() {
        lueTiedosto();
    }
    
    /**
     * Näytetään ohjelman suunnitelma erillisessä selaimessa.
     */
    private void avustus() {
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2019k/ht/johartik");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }

    private Ostoslista ostoslista = new Ostoslista();

   
    /**
     *  Alustaa käyttöliittymän.
     */
    protected void alusta() {
        
        chooserTilaajat.clear();
        chooserTilaajat.addSelectionListener(e -> naytaTilaaja());
        edits = TilaajaDialogController.luoKentat(gridTilaaja);
        
    }
    
    /*
     * 
     *  Muokataan tilaajaa
     */
    
    private void muokkaa() {
        Tilaaja tilaajaKohdalla = chooserTilaajat.getSelectedObject();
        if ( tilaajaKohdalla == null) return;
        try {
            Tilaaja tilaaja = TilaajaDialogController.kysyTilaaja(null, tilaajaKohdalla.clone());
            if(tilaaja == null) return;
            ostoslista.korvaaTaiLisaa(tilaaja);
            hae(tilaaja.getTunnusNro());
        } catch (CloneNotSupportedException e) {
            //
        } catch (SailoException e) {
            Dialogs.showMessageDialog(e.getMessage());
      }
        
    }
    
    /**
     * 
     */
    private void naytaTilaaja() {
        Tilaaja tilaajaKohdalla = chooserTilaajat.getSelectedObject();
        
        if(tilaajaKohdalla == null ) { 
            return;
        }    
        
        TilaajaDialogController.naytaTilaaja(edits, tilaajaKohdalla);
        naytaTilaukset(tilaajaKohdalla);
        
    }
    
    private void naytaTilaukset(Tilaaja tilaaja) {
        tableTilaukset.clear();
        if ( tilaaja == null ) return;
        
        Tilaukset tilaukset = ostoslista.annaTilaukset(tilaaja);
           if ( tilaukset.getTilaustenLkm() == 0) return; 
           for(int i = 0; i < tilaukset.getTilaustenLkm(); i++) {
               Tilaus tilaus = tilaukset.annaTilaus(i);
               naytaTilaus(tilaus);
           }
        
    }
    
    private void naytaTilaus(Tilaus tilaus) {
        String[] rivi = tilaus.toString().split("\\|");  
        tableTilaukset.add(tilaus, rivi[2], rivi[4], rivi[3]);
    }
    
    
/*
    private void tulosta(PrintStream os, Tilaaja tilaaja) {
        os.println("-----------------------------------------------------------------");
        tilaaja.tulosta(os);
        os.println("-----------------------------------------------------------------");
        Tilaukset loytyneet = ostoslista.annaTilaukset(tilaaja);
        for (int i = 0; i < loytyneet.getTilaustenLkm(); i++) {
            Tilaus tilaus6 = loytyneet.annaTilaus(i);
            System.out.print(tilaus6.getHenkiloTunnusNro() + " ");        
            tilaus6.tulostaTilaus(os);
        }
    }
*/
    /**
     * Lisää uuden tilaajan tietorakenteeseen
     */
    protected void uusiTilaaja() {
        Tilaaja uusi = new Tilaaja();
        uusi = TilaajaDialogController.kysyTilaaja(null, uusi);
        if (uusi == null) return;
        uusi.rekisteroi();
       // uusi.taytaPenaPenttila();
        ostoslista.lisaaTilaaja(uusi);
        
        hae(uusi.getTunnusNro());
    }
    
    
    private void hae(int tnro) {
        chooserTilaajat.clear();
        
        int index = 0;
        for (int i = 0; i < ostoslista.getTilaajia(); i++) {
            Tilaaja tilaaja = ostoslista.annaTilaaja(i);
            if (tilaaja.getTunnusNro() == tnro) index = i;
            chooserTilaajat.add(tilaaja.getNimi(), tilaaja);
        }
        chooserTilaajat.setSelectedIndex(index);
    }
    
    

    private void uusiTuote() {
        Tilaaja tilaajaKohdalla = chooserTilaajat.getSelectedObject();
        Tilaus uusi = new Tilaus(tilaajaKohdalla.getTunnusNro());
        uusi = TilausDialogController.kysyTilaus(null, uusi);
        if (uusi == null) return;
        uusi.rekisteroiTilaus();
        ostoslista.lisaaTilaus(uusi);
        hae(uusi.getTilausNro());
        
   }

    private void poistaTilaus() {
        Tilaaja tilaajaKohdalla = chooserTilaajat.getSelectedObject();
        int rivi = tableTilaukset.getRowNr();
        if ( rivi < 0) return;
        Tilaus tilaus = tableTilaukset.getObject();
        if ( tilaus == null) return;
        ostoslista.poistaTilaus(tilaus);
        naytaTilaukset(tilaajaKohdalla);
        int tilauksia = tableTilaukset.getItems().size();
        if(rivi >= tilauksia ) rivi = tilauksia -1;
        tableTilaukset.getFocusModel().focus(rivi);
        tableTilaukset.getSelectionModel().select(rivi);
        
    }
    
    /**
     * Tulostaa valitut 
     * @param text tulostettava paikka
     */
    public void tulostaValitut(TextArea text) {
        
       try(PrintStream os = TextAreaOutputStream.getTextPrintStream(text)){
           os.println("Tulostetaan kaikki tilaajat");
           Collection<Tilaaja> tilaajat = ostoslista.etsi();
           for(Tilaaja tilaaja:tilaajat) {
               if(tilaaja != null) {
               tulosta(os, tilaaja);
               os.println("\n\n");
               }
               }
               
       }
        
         
        
     
        
        
        
        
        
        /*   byte[] data = Files.readAllBytes(Paths.get("Tuotet/tilaajat.dat"));
        String myString = new String(data, StandardCharsets.UTF_8);
        
        byte[] data1 = Files.readAllBytes(Paths.get("Tuotet/tilaukset.dat"));
        String myString1 = new String(data1, StandardCharsets.UTF_8);
        myString = myString + myString1;
        text.setText(myString);
      */  
    }
    
    /**
     * Tulostaa tilaajat ja tilaukset tilaajan perusteella
     * @param os tulostettava virta
     * @param tilaaja tilaaja
     */
    public void tulosta(PrintStream os, final Tilaaja tilaaja) {
        os.println("---------------------------------------");
        tilaaja.tulosta(os);
        os.println("---------------------------------------");
            Tilaukset tilaukset = ostoslista.annaTilaukset(tilaaja);
            for (int i = 0; i < tilaukset.getTilaustenLkm(); i++) {
                Tilaus tilaus6 = tilaukset.annaTilaus(i);
                tilaus6.tulostaTilaus(os);
        }
    }

    private void poistaTilaaja() {
        Tilaaja tilaajaKohdalla = chooserTilaajat.getSelectedObject();
        if ( tilaajaKohdalla == null) return;
        if (!Dialogs.showQuestionDialog("Poisto", "Poistetaanko tilaaja: " + tilaajaKohdalla.getNimi(), "Kyllä", "Ei"))
            return;
        ostoslista.poista(tilaajaKohdalla);
        int index = chooserTilaajat.getSelectedIndex();
        hae(0);
        chooserTilaajat.setSelectedIndex(index);
    }
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
        avaa();
        
    }
    
 }