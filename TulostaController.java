package fxTuote;

import javafx.fxml.FXML;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.scene.control.TextArea;


/**
 * @author harti
 * @version 16.12.2020
 *
 */
public class TulostaController implements ModalControllerInterface<String> {
    @FXML TextArea textAlue;
    
    
    @FXML private void handleDefaultOK() {
        Dialogs.showMessageDialog("Vielä ei osata tulostaa");
    }
    
    
    @FXML private void handleDefaultCancel() {
       ModalController.closeStage(textAlue);
    }


    @Override
    public String getResult() {
        return null;
    }


    @Override
    public void handleShown() {
            //
    }
    
    /**
     * @return alueen
     */
    public TextArea getTextArea() {
        return textAlue;
    }


    @Override
    public void setDefault(String oletus) {
        textAlue.setText(oletus);
        
    }
/**
 * tulostaa
 * @param tulostus tulostus
 * @return tulostusctrolleri
 */
    public static TulostaController tulosta(String tulostus) {
                 TulostaController tulostusCtrl = 
                    ModalController.showModeless(TulostaController.class.getResource("TuoteTulostaView.fxml"),
                                              "Tulostus", tulostus);
                  return tulostusCtrl;
              }
    
}
