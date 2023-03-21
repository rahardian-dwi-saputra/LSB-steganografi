import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JTabbedPane;

public class Main extends JFrame{
    
    public Main(){
        Gui_encode gui_encode = new Gui_encode();
        Gui_decode gui_decode = new Gui_decode();
        
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.addTab("Encode", gui_encode.create_gui()); 
        tabbedPane.addTab("Decode", gui_decode.create_gui()); 
        
        super.getContentPane().add(tabbedPane);
        super.setTitle("LSB Steganografi"); 
        super.setSize(615, 680);
        super.setLocationRelativeTo(null); 
        super.setResizable(false); 
        super.setDefaultCloseOperation(EXIT_ON_CLOSE); 
        super.setVisible(true); 
    }
    public static void main(String[] args) {
        Main main = new Main();
    }
    
}