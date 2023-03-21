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

public class Gui_encode {
    
    public JPanel create_gui(){
        //label cover image
        JLabel label1 = new JLabel("Cover image ");
        label1.setFont(new Font("Times New Roman",Font.PLAIN,16)); //font label
        label1.setBounds(10, 20, 100, 30); //koordinat label (x, y, panjang, lebar)
        
        //text field cover image
        JTextField tf1 = new JTextField();
        tf1.setFont(new Font("Times New Roman",Font.PLAIN,14)); 
        tf1.setEditable(false); 
        tf1.setBounds(110, 20, 370, 30);
        
        //tombol buka file cover image
        JButton btn1 = new JButton("Browse");
        btn1.setFont(new Font("Times New Roman",Font.BOLD,14));
        btn1.setBounds(500, 20, 100, 30);
        
        JLabel label2 = new JLabel("Secret image ");
        label2.setFont(new Font("Times New Roman",Font.PLAIN,16)); 
        label2.setBounds(10, 60, 100, 30);
        
        JTextField tf2 = new JTextField();
        tf2.setFont(new Font("Times New Roman",Font.PLAIN,14)); 
        tf2.setEditable(false); 
        tf2.setBounds(110, 60, 370, 30);
        
        JButton btn2 = new JButton("Browse");
        btn2.setFont(new Font("Times New Roman",Font.BOLD,14));
        btn2.setBounds(500, 60, 100, 30);
        
        JLabel label3 = new JLabel("File output ");
        label3.setFont(new Font("Times New Roman",Font.PLAIN,16)); 
        label3.setBounds(10, 100, 100, 30);
        
        JTextField tf3 = new JTextField();
        tf3.setFont(new Font("Times New Roman",Font.PLAIN,14)); 
        tf3.setEditable(false); 
        tf3.setBounds(110, 100, 370, 30);
        
        JButton btn3 = new JButton("Browse");
        btn3.setFont(new Font("Times New Roman",Font.BOLD,14));
        btn3.setBounds(500, 100, 100, 30);
        
        JButton btn4 = new JButton("Embeded Image");
        btn4.setFont(new Font("Times New Roman",Font.BOLD,16)); 
        btn4.setBounds(10, 150, 150, 30);
        
        JButton btn5 = new JButton("Clear");
        btn5.setFont(new Font("Times New Roman",Font.BOLD,16)); 
        btn5.setBounds(170, 150, 100, 30);
        
        JLabel label4 = new JLabel();
        label4.setBounds(10, 190, 300, 300);
        
        JLabel label5 = new JLabel();
        label5.setBounds(320, 190, 300, 300);
        
        //browse file cover image
        btn1.addActionListener((ActionEvent e) -> {
            JFileChooser fc = new JFileChooser();
            //pengaturan agar hanya file gambar yang dapat dipilih
            FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
            fc.setFileFilter(imageFilter); 
            fc.setAcceptAllFileFilterUsed(false);
            fc.setDialogTitle("Pilih Cover Image");
            //buka kotak dialog file
            int pilih_file = fc.showOpenDialog(null);
            if(pilih_file == JFileChooser.APPROVE_OPTION){
                //tulis lokasi file yang dipilih di text field
                tf1.setText(fc.getSelectedFile().getAbsolutePath()); 
            }    
        });
        
        //browse secret image
        btn2.addActionListener((ActionEvent e) -> {
            JFileChooser fc = new JFileChooser();
            FileFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
            fc.setFileFilter(imageFilter); 
            fc.setAcceptAllFileFilterUsed(false);
            fc.setDialogTitle("Pilih Secret Image"); 
            int pilih_file = fc.showOpenDialog(null);
            if(pilih_file == JFileChooser.APPROVE_OPTION){
                tf2.setText(fc.getSelectedFile().getAbsolutePath()); 
            }    
        });
        
        //pilih lokasi file output steaganografi
        btn3.addActionListener((ActionEvent e) -> {
            if(tf1.getText().equals(""))
                JOptionPane.showMessageDialog(null, "Input File terlebih dahulu", "Peringatan", JOptionPane.ERROR_MESSAGE);
            else{
                JFileChooser fc = new JFileChooser();
                fc.setDialogTitle("Pilih lokasi penyimpanan file"); 
                int returnVal = fc.showSaveDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION){   
                    tf3.setText(fc.getSelectedFile().getAbsolutePath()+".png"); 
                }    
            } 
        });
        
        //tombol encode
        btn4.addActionListener((ActionEvent e) -> {
            //tampilkan gambar asli
            ImageIcon image_icon = new ImageIcon(tf1.getText());
            Image image = image_icon.getImage(); 
            Image newimg = image.getScaledInstance(280, 280,  java.awt.Image.SCALE_SMOOTH);
            image_icon = new ImageIcon(newimg); 
            label4.setIcon(image_icon); 
            
            //proses pembuatan steganografi
            Steganografi_lsb stego = new Steganografi_lsb();
            stego.encode_steganografi(tf1.getText(), tf2.getText(), tf3.getText());
            
            //tampilkan gambar hasil steganografi
            ImageIcon image_icon2 = new ImageIcon(tf3.getText());
            Image image2 = image_icon2.getImage(); 
            Image newimg2 = image2.getScaledInstance(280, 280,  java.awt.Image.SCALE_SMOOTH);
            image_icon2 = new ImageIcon(newimg2); 
            label5.setIcon(image_icon2); 
        });
        
        //bersihkan form
        btn5.addActionListener((ActionEvent e) -> {
            tf1.setText("");
            tf2.setText("");
            tf3.setText("");
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
        panel.add(label3);
        panel.add(tf3);
        panel.add(btn3);
        panel.add(btn4);
        panel.add(btn5);
        panel.add(label4);
        panel.add(label5);
        
        return panel;
    }
}
