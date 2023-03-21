
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Gui_decode {
    
    public JPanel create_gui(){
        JLabel label1 = new JLabel("Gambar Steganografi ");
        label1.setFont(new Font("Times New Roman",Font.PLAIN,16)); 
        label1.setBounds(10, 20, 150, 30);
        
        JTextField tf1 = new JTextField();
        tf1.setFont(new Font("Times New Roman",Font.PLAIN,14)); 
        tf1.setEditable(false); 
        tf1.setBounds(160, 20, 300, 30);
        
        JButton btn1 = new JButton("Browse");
        btn1.setFont(new Font("Times New Roman",Font.BOLD,14));
        btn1.setBounds(500, 20, 100, 30);
        
        JLabel label2 = new JLabel("File output ");
        label2.setFont(new Font("Times New Roman",Font.PLAIN,16)); 
        label2.setBounds(10, 60, 100, 30);
        
        JTextField tf2 = new JTextField();
        tf2.setFont(new Font("Times New Roman",Font.PLAIN,14)); 
        tf2.setEditable(false); 
        tf2.setBounds(160, 60, 300, 30);
        
        JButton btn2 = new JButton("Browse");
        btn2.setFont(new Font("Times New Roman",Font.BOLD,14));
        btn2.setBounds(500, 60, 100, 30);
        
        JButton btn3 = new JButton("Decode Image");
        btn3.setFont(new Font("Times New Roman",Font.BOLD,16)); 
        btn3.setBounds(10, 100, 150, 30);
        
        JButton btn4 = new JButton("Clear");
        btn4.setFont(new Font("Times New Roman",Font.BOLD,16)); 
        btn4.setBounds(170, 100, 100, 30);
        
        JLabel label4 = new JLabel();
        label4.setBounds(10, 140, 300, 300);
        
        JLabel label5 = new JLabel();
        label5.setBounds(320, 140, 300, 300);
        
        //browse file image steganografi
        btn1.addActionListener((ActionEvent e) -> {
            JFileChooser fc = new JFileChooser();
            FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
            fc.setFileFilter(imageFilter); 
            fc.setAcceptAllFileFilterUsed(false);
            fc.setDialogTitle("Pilih file steganografi"); 
            int pilih_file = fc.showOpenDialog(null);
            if(pilih_file == JFileChooser.APPROVE_OPTION){
                tf1.setText(fc.getSelectedFile().getAbsolutePath()); 
            }    
        });
        
        //pilih lokasi hasil extract file
        btn2.addActionListener((ActionEvent e) -> {
            if(tf1.getText().equals(""))
                JOptionPane.showMessageDialog(null, "Input File terlebih dahulu", "Peringatan", JOptionPane.ERROR_MESSAGE);
            else{
                JFileChooser fc = new JFileChooser();
                fc.setDialogTitle("Pilih lokasi penyimpanan file"); 
                int returnVal = fc.showSaveDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION){   
                    tf2.setText(fc.getSelectedFile().getAbsolutePath()+".jpg"); 
                }    
            }    
        });
        
        btn3.addActionListener((ActionEvent e) -> { 
            //tampilkan gambar steganografi
            ImageIcon image_icon = new ImageIcon(tf1.getText());
            Image image = image_icon.getImage(); 
            Image newimg = image.getScaledInstance(280, 280,  java.awt.Image.SCALE_SMOOTH);
            image_icon = new ImageIcon(newimg); 
            label4.setIcon(image_icon); 
            
            //extract gambar
            Steganografi_lsb stegano = new Steganografi_lsb();
            stegano.extract_file(tf1.getText(), tf2.getText());
            
            //tampilkan hasil extract steganografi
            ImageIcon image_icon2 = new ImageIcon(tf2.getText());
            Image image2 = image_icon2.getImage(); 
            Image newimg2 = image2.getScaledInstance(280, 280,  java.awt.Image.SCALE_SMOOTH);
            image_icon2 = new ImageIcon(newimg2); 
            label5.setIcon(image_icon2); 
        });
        //bersihkan form
        btn4.addActionListener((ActionEvent e) -> { 
            tf1.setText("");
            tf2.setText("");
            label4.setIcon(null);
            label5.setIcon(null);
        });
        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.add(label1);
        panel.add(tf1);
        panel.add(btn1);
        panel.add(label2);
        panel.add(tf2);
        panel.add(btn2);
        panel.add(btn3);
        panel.add(btn4);
        panel.add(label4);
        panel.add(label5);
        
        return panel;
    }
}
