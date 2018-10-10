
package hhs.lingo;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Falco
 */
public class HHSLingo {

    private int pogingen = 0; // Hoeveelste poging we zijn
    private final int maxAantalPogingen = 5; // Spel instelling, max aantal pogingen

    private JTextField[][] textFields;
    private boolean isSpelActief = true; // Deze boolean gebruiken om te zien of het spel actief is (ervoor zorgen dat het spel niet verder gaat als het spel klaar is (gewonnen/verloren/cheat))
    
    private String hetWoord; // Het woord dat geraden moet worden

    private LingoGui lGui; // lGui -> Lingo Gui

    private String SoundFout;
    private String SoundHalf;
    private String SoundGoed;

    private String SoundGewonnen;
    private String SoundGameover;
    private String SoundCheat;

    private final Color ColorFout = Color.RED;
    private final Color ColorHalf = Color.YELLOW;
    private final Color ColorGoed = Color.GREEN;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new HHSLingo().run(); // Dit is mijn manier van werken atm

        // Static dinge zijn niet slecht voor kleine dingen, maar voor grotere projecten zijn ze kut.
        // Beter beginnen we al zo veel zonder static, Object Orientated Programming.
    }

    public void run(){
        // Alles opzetten
        lGui = new LingoGui();
        lGui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        // Textfield matrix
        textFields = new JTextField[5][5];
        
        // Rij 1
        textFields[0][0] = lGui.getTxt11();
        textFields[0][1] = lGui.getTxt12();
        textFields[0][2] = lGui.getTxt13();
        textFields[0][3] = lGui.getTxt14();
        textFields[0][4] = lGui.getTxt15();
        
        // Rij 2
        textFields[1][0] = lGui.getTxt21();
        textFields[1][1] = lGui.getTxt22();
        textFields[1][2] = lGui.getTxt23();
        textFields[1][3] = lGui.getTxt24();
        textFields[1][4] = lGui.getTxt25();
        
        // Rij 3
        textFields[2][0] = lGui.getTxt31();
        textFields[2][1] = lGui.getTxt32();
        textFields[2][2] = lGui.getTxt33();
        textFields[2][3] = lGui.getTxt34();
        textFields[2][4] = lGui.getTxt35();
        
        // Rij 4
        textFields[3][0] = lGui.getTxt41();
        textFields[3][1] = lGui.getTxt42();
        textFields[3][2] = lGui.getTxt43();
        textFields[3][3] = lGui.getTxt44();
        textFields[3][4] = lGui.getTxt45();
        
        // Rij 5
        textFields[4][0] = lGui.getTxt51();
        textFields[4][1] = lGui.getTxt52();
        textFields[4][2] = lGui.getTxt53();
        textFields[4][3] = lGui.getTxt54();
        textFields[4][4] = lGui.getTxt55();
        
        
        JTextField txtInput = lGui.getTxtInput();
        txtInput.addActionListener((l) -> { 
            if(isSpelActief){
                VulInWoord(pogingen++, txtInput.getText()); // de ++ wordt pas na de functie uitgevoerd !
                txtInput.setText("");
            }
        });
        txtInput.requestFocusInWindow();

        // Setup sound files
        SoundFout = getClass().getClassLoader().getResource("fout.wav").getFile();
        SoundHalf = getClass().getClassLoader().getResource("half.wav").getFile();
        SoundGoed = getClass().getClassLoader().getResource("goed.wav").getFile();

        SoundGewonnen = getClass().getClassLoader().getResource("win.wav").getFile();
        SoundGameover = getClass().getClassLoader().getResource("gameover.wav").getFile();
        SoundCheat = getClass().getClassLoader().getResource("cheat.wav").getFile();

        hetWoord = VerkrijgNieuwWoord();

        lGui.setVisible(true);
    }


    // Hier woord het woord uit een array gehaalt en dat woord moet geraden worden
    public String VerkrijgNieuwWoord(){ 
        //TODO: Maak het random
        
        return "toast";
    }
    
    // Hier kijken we of het woord goed is, zoniet dan komen er streepjes 
    public void VulInWoord(int poging, String woord){ 
        //TODO: Check het woord, is het te lang, te kort en check of er niet gecheat wordt
        // Set er wordt vals gespeelt, dan kan je deze code uitvoeren:
//         woord = "-----";

         // finalWoord is hetzelfde als de ingevoerde woord, maar om dat ik alles in een bestand heb moet ik het zo doen
        String finalWoord = woord;
        VulWoordInGui(poging, finalWoord.toLowerCase(), () -> {
            //TODO: Maak jou eigen variant
//            UpdateStatusText("Dit was poging " + (poging + 1));

            //TODO: Ook de logica voor eind spel (heeft gebruiker gewonnen of zijn er 5 pogingen geweest)
//             if(finalWoord == hetWoord){}
        });
    }
    
    // Hier zit de logica dat de gui invult  
    public void VulWoordInGui(int poging, String woord, ILambda callback){
        for(int i = 0; i < 5; i++){
            int finalI = i;
            Timer timer = new Timer(200 * i, (l) -> {
                JTextField txt = textFields[poging][finalI];
                char character = woord.charAt(finalI);
                
                txt.setText(character + "");

                Color kleur = GetKleurVanLetter(finalI, character);
                txt.setBackground(kleur);

                if(kleur == ColorGoed){
                    PlaySound(SoundGoed);
                }else if(kleur == ColorHalf){
                    PlaySound(SoundHalf);
                }else{
                    PlaySound(SoundFout);
                }
            });
            timer.setRepeats(false);
            timer.start();
        }

        Timer callbackTimer = new Timer(200 * 5, (l) -> {
            callback.run();
        });

        callbackTimer.setRepeats(false);
        callbackTimer.start();
    }

    // Kijk of er alleen maar dezelfde letter in zitten, een hele simpele anti-cheat
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
    
    // Verkrijg de bijbehoorende kleur van de opgegeven letter
    // Rood - Fout
    // Geel - Fout maar de letter zit wel in het woord
    // Groen - Goed
    public Color GetKleurVanLetter(int index, char input){
        char charWoord = hetWoord.charAt(index);
        
        //TODO: Check of de ingegeven character hetzelfde is van het woord (ook van de index)
        // Als dat niet zo is, kijk of de character wel in het woord zit !!
        //      hint: gebruik hetWoord.contains en maak de char een string met + "" (input + "")

        // Gebruik de values ColorGoed, ColorHalf, ColorFout

        return ColorFout;
    }

    // Als er vals gespeelt wordt, wat moet er gedaan worden
    public void EindSpelValsSpel(){ 
        //TODO: spel uitschakelen, gameover muziekje afspelen (kijk bij geenkansover)
        // isSpelActief = false
    }

    // Als de speler gewonnen heeft
    public void EindSpelWin(){
        //TODO: spel uitschakelen, overwinning muziekje afspelen (kijk bij geenkansover)

    }
    
    // Als de speler geen kansen meer heeft
    public void EindSpelGeenKansenOver(){
        //TODO: spel uitschakelen, gameover muziekje afspelen

        new Thread(() -> PlaySound(SoundGameover)).start();
    }
    
    // Gui element, update status textbox
    public void UpdateStatusText(String status){
        lGui.getTxtStatus().setText(status);
    }


    /** Ja dit is van het internet gehaald */

    // size of the byte buffer used to read/write the audio stream
    private static final int BUFFER_SIZE = 4096;

    // Play sound file
    public void PlaySound(String audioFilePath) {
        File audioFile = new File(audioFilePath);
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);

            audioLine.open(format);

            audioLine.start();

//            System.out.println("Playback started.");

            byte[] bytesBuffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;

            while ((bytesRead = audioStream.read(bytesBuffer)) != -1) {
                audioLine.write(bytesBuffer, 0, bytesRead);
            }

            audioLine.drain();
            audioLine.close();
            audioStream.close();

//            System.out.println("Playback completed.");

        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
    }
}
