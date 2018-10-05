
package hhs.lingo;

import javax.swing.*;

/**
 *
 * @author Falco
 */
public class HHSLingo {

    private int pogingen = 0;
    private final int maxAantalPogingen = 5;

    private JTextField[] textFields;
    private boolean isSpelActief;
    
    private String hetWoord;


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new HHSLingo().run();
    }

    public void run(){
        // Alles opzetten
    }

    public void StartSpel(){
        isSpelActief = true;
    }

    // Hier woord het woord uit een array gehaalt en dat woord moet geraden worden
    public String VerkrijgNieuwWoord(){ 

        return "";
    }
    
    // Hier zit de logica dat de gui invult
    public void VulInWoord(int poging, String woord){ 

    }

    public boolean IsCheat(String input){
        char start = input.charAt(0);
        int teller = 0;

        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == start) {
                teller++;
            }
        }

        return teller == input.length();
    }

    // Als er vals gespeelt wordt, wat moet er gedaan worden
    public void EindSpelValsSpel(){ 

    }

    // Als de speler gewonnen heeft
    public void EindSpelWin(){ 

    }
    
    // Als de speler geen kansen meer heeft
    public void EindSpelGeenKansenOver(){ 

    }
}
