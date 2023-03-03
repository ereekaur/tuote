package tuote;

/**
 * Poikkeusluokka tietorakenteesta aiheutuville poikkeuksille.
 * @author Joonas Hartikainen ja Erno Kauranen
 * @version 17.3.2019
 *
 */
public class SailoException extends Exception {
    private static final long serialVersionUID = 1L;
    
    /**
     * Poikkeuksen muodostaja jolle tuodaan poikkeuksessa käytetty viesti
     * @param viesti Poikkeuksen viesti
     */
    public SailoException(String viesti) {
        super(viesti);
    }
}
