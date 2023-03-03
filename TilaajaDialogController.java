package fxTuote;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import Tuote.Tilaaja;
/**
 * 
 * Kysytään tilaajan tiedot luomalla sille uusi dialogi.
 * @author Joonas Hartikainen ja Erno Kauranen
 * @version 10.12.2020
 *
 */
public class TilaajaDialogController implements ModalControllerInterface<Tilaaja> , Initializable {

    //@FXML private TextField editEtunimi;
    //@FXML private TextField editSukunimi;
    //@FXML private TextField editOsoite;
    //@FXML private TextField editPuhelinnumero;
    //@FXML private TextField editMaksettu;
   // @FXML private TextField editToimituspaiva;
    private Tilaaja tilaajaKohdalla1;
    @FXML private TextField editTilauksenhinta;
    @FXML private Label labelVirhe;
    @FXML private GridPane gridTilaaja;
    @FXML private ScrollPane panelTilaaja;
    
    private TextField edits[];
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
       
        
    }

    @FXML private void handleOK() {
     if (tilaajaKohdalla1 != null && tilaajaKohdalla1.getNimi().trim().equals("")) {
         naytaVirhe("Nimi ei saa olla tyhjä");
         return;
     }   
        ModalController.closeStage(labelVirhe);  
    }
   
    @FXML private void handleCancel() {
        tilaajaKohdalla1 = null;
        ModalController.closeStage(labelVirhe);
        
    }
    
    
    
    @Override
    public Tilaaja getResult() {
       return tilaajaKohdalla1;
    }

    @Override
    public void handleShown() {
       //
        
    }

    @Override
    public void setDefault(Tilaaja oletus) {
       tilaajaKohdalla1 = oletus;
        naytaTilaaja(tilaajaKohdalla1);
    }

    
/**
 * Näytetään tilaajan tiedot TextField komponentteihin
 * @param edits taulukko jossa tekstikenttiä
 * @param tilaaja näytettävä tilaaja
 */
 public static void naytaTilaaja(TextField[] edits, Tilaaja tilaaja) {       
        if(tilaaja == null ) return;
        for (int k = tilaaja.ekaKentta(); k < tilaaja.getKenttia() ; k++) {
            edits[k].setText(tilaaja.anna(k));
        }
        
    }
    
 private static Tilaaja aputilaaja = new Tilaaja();


 
 
 /**
  * Luodaan GridPaneen tilaajan tiedot.
  * @param gridTilaaja mihin tiedot luodaan
  * @return luodut tekstikentät
  */
 public static TextField[] luoKentat(GridPane gridTilaaja) {
     
     gridTilaaja.getChildren().clear();
     TextField[] edits = new TextField[aputilaaja.getKenttia()];
     
     for(int i=0, k = aputilaaja.ekaKentta(); k < aputilaaja.getKenttia(); k++, i++) {
         
         Label label = new Label(aputilaaja.getKysymys(k));
         gridTilaaja.add(label, 0, i);
         TextField edit = new TextField();
         edits[k] = edit;
         edit.setId("e"+k);
         gridTilaaja.add(edit, 1, i);
         
         
     } 
     
     return edits;
 
 
 }
 
 /**
  * Palautetaan komponentin id:stä saatava luku
  * @param obj tutkittava komponentti
  * @param oletus mikä arvo jos id ei ole kunnollinen
  * @return komponentin id lukuna
  */
 public static int getFieldId (Object obj, int oletus) {
     if ( !( obj instanceof Node)) return oletus;
     Node node = (Node)obj;
     return Mjonot.erotaInt(node.getId().substring(1),oletus);
     
 }
 
     /**
      * Tyhjentää tekstikentät
      * @param edits tyhjennettävät tekstikentät
      */
     public static void tyhjenna(TextField[] edits) {
         for (TextField edit: edits)
             if (edit != null) edit.setText("");
     }
     
     /**
      * Tekee tarvittavat muut alustukset.
      */
     protected void alusta() {
         edits = luoKentat(gridTilaaja);
         for (TextField edit : edits ) {
             if ( edit != null)
             edit.setOnKeyReleased( e -> kasitteleMuutosTilaajaan((TextField)(e.getSource())));
         panelTilaaja.setFitToHeight(true);
         }
     }
    
    
     private void kasitteleMuutosTilaajaan(TextField edit) {
         if (tilaajaKohdalla1 == null) return;
         int k = getFieldId(edit, aputilaaja.ekaKentta());
         String s = edit.getText();
         String virhe = null;
         virhe = tilaajaKohdalla1.aseta(k, s);
         if ( virhe == null) {
             Dialogs.setToolTipText(edit, "");
             edit.getStyleClass().removeAll("virhe");
             naytaVirhe(virhe);
         } else {
             Dialogs.setToolTipText(edit,  virhe);
             edit.getStyleClass().add("virhe");
             naytaVirhe(virhe);  
         }
         
     }
     
     private void naytaVirhe(String virhe) {
         if (virhe == null || virhe.isEmpty()) {
             labelVirhe.setText("");
             labelVirhe.getStyleClass().removeAll("virhe");
             return;
         }
         labelVirhe.setText(virhe);
         labelVirhe.getStyleClass().add("virhe");
     }
 
 
 
    /**
     * Näytetään tilaajan tiedot TextField komponentteihin 
     * @param tilaaja näytettävä tilaaja
     */
    public void naytaTilaaja(Tilaaja tilaaja) {
        naytaTilaaja(edits, tilaaja);
        
    }
    
    
    
    /**
     * 
     * 
     * @param modalityStage s
     * @param oletus s
     * @return s
     */
    public static Tilaaja kysyTilaaja(Stage modalityStage, Tilaaja oletus) {
     
        return ModalController.showModal(
                TilaajaDialogController.class.getResource("TilaajaDialogView.fxml"),
                "Tuote",
                modalityStage,oletus, null
                );
        
        
    }

    
}
