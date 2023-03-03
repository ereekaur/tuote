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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import Tuote.Ostoslista;
import Tuote.SailoException;
import Tuote.Tilaus;


/**
 * Kysyttään tilauksen tiedot luomalla sille uusi dialogi.
 * @author Joonas Hartikainen ja Erno Kauranen
 * @version 13.12.2020
 *
 */
public class TilausDialogController implements ModalControllerInterface<Tilaus> , Initializable{

    private TextField edits[];
    @FXML private Label labelVirhe;
    @FXML private GridPane gridTilaus;
    @FXML private BorderPane panelTilaus;
    private Tilaus tilausKohdalla1;
    private Ostoslista ostoslista = new Ostoslista();
    
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
        
    }

    @Override
    public Tilaus getResult() {
        return tilausKohdalla1;
    }

    @Override
    public void handleShown() {
    //
        
    }

    @FXML private void handleOk() {

        ModalController.closeStage(labelVirhe);
    
    }

    
    @FXML private void handleCancel() {
        tilausKohdalla1 = null;
        ModalController.closeStage(labelVirhe);
    }
    
    
    
    @Override
    public void setDefault(Tilaus oletus) {
        tilausKohdalla1 = oletus;
        naytaTilaus(tilausKohdalla1);
        
    }
    /**
     * Nayttää tilauksen
     * @param edits tekstikenttiä, joihin tilauksen tiedot tulostuu
     * @param tilaus näytettävä tilaus
     */
    public static void naytaTilaus(TextField[] edits, Tilaus tilaus) {
        if(tilaus == null) return;
        for(int k = tilaus.ekaKentta(); k < tilaus.getKenttia(); k++) {
            edits[k].setText(tilaus.anna(k));
        }
    }
    
    
    private static Tilaus apuTilaus = new Tilaus();
    
    
    
    
    /**
     * Luo tekstikentät, joihin tilauksen tiedot voidaan tallentaa.
     * @param gridTilaus GridPane, johon tilauksen kentät luodaan
     * @return palauttaa "Taulukon", johon voidaan laittaa tilauksen kentät
     */
    public static TextField[] luoKentat(GridPane gridTilaus) {
        gridTilaus.getChildren().clear();
        TextField[] edits = new TextField[apuTilaus.getKenttia()];
        
        for (int i=0, k = apuTilaus.ekaKentta(); k < apuTilaus.getKenttia(); k++, i++) {
            
            Label label = new Label(apuTilaus.getKysymys(k));
            gridTilaus.add(label, 0, i);
            TextField edit = new TextField();
            edits[k] = edit;
            edit.setId("e"+k);
            gridTilaus.add(edit, 1 ,i);
        }
        return edits;
    }
    
    /**
     * palauttaa kentän id numeron
     * @param obj haettava kenttä
     * @param oletus id numero, joka palautetaaan, jos kenttä ei ole noden instanssi.
     * @return kentän id numero.
     */
    public static int getFieldId (Object obj, int oletus) {
        if (!(obj instanceof Node)) return oletus;
        Node node = (Node)obj;
        return Mjonot.erotaInt(node.getId().substring(1), oletus);
    }
    
    
    /**
     * tyhjentää tekstikentän
     * @param edits tyhjennettävä kenttä.
     */
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit : edits) 
            if (edit !=null) edit.setText("");
    }
    
    /**
     * alustaa kenttien tiedot
     */
    protected void alusta() {
        edits = luoKentat(gridTilaus);
        for (TextField edit : edits) {
            if (edit != null)
                edit.setOnKeyReleased( e -> kasitteleMuutosTilaukseen((TextField)(e.getSource())));
        }
    }
    
    private void kasitteleMuutosTilaukseen(TextField edit) {
        if (tilausKohdalla1 == null) return;
        int k = getFieldId(edit, apuTilaus.ekaKentta());
        String s = edit.getText();
        String virhe = null;
        virhe = tilausKohdalla1.aseta(k, s);
        if ( virhe == null) {
            Dialogs.setToolTipText(edit, "");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit, virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }
    }
    
    
    
    private void naytaVirhe(String virhe) {
        if ( virhe == null  || virhe.isEmpty()) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    
}    
    
    /**
     * Näytetään tilauksen tiedot TextField komponentteihin
     * @param tilaus näytettävä tilaaja
     */
    public void naytaTilaus(Tilaus tilaus) {
        naytaTilaus(edits, tilaus);
    }
    
    /**
     * Avaa dialogin käyttäjälle johon täytetään tilauksen tiedot
     * @param modalityStage kertoo, että ikkuna on modaalinen
     * @param oletus lisättävä tilaus
     * @return "palauttaa" kyselyn.
     */
    public static Tilaus kysyTilaus(Stage modalityStage, Tilaus oletus) {
        return ModalController.showModal(
                TilausDialogController.class.getResource("TuoteLisaaTuoteView.fxml"),
                "Tilauksen lisäys",
                modalityStage,oletus, null
                );
                
    }
    
    /**
     * Tallentaa tilauksen
     */
    @SuppressWarnings("unused") //tätä ei käytetty lokaalisti ja uikutti siitä.
    private void tallenna() {
        try {
            ostoslista.tallenna();
        } catch (SailoException e) {
              //
            Dialogs.showMessageDialog("Tuli virhe: " + e.getMessage());
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
