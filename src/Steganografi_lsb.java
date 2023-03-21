import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Steganografi_lsb {
    
    public void encode_steganografi(String url_cover, String url_secret, String url_file_output){
        try{
            //baca file cover image
            BufferedImage image_origin = ImageIO.read(new File(url_cover));
            
            //konversi secret image ke byte
            File file_hidden = new File(url_secret);
            byte[] byte_file_hidden = new byte[(int)file_hidden.length()];
            FileInputStream fis = new FileInputStream(file_hidden);
            fis.read(byte_file_hidden);
            
            //konversi informasi panjang file menjadi array byte
            byte file_length[] = bit_conversion(byte_file_hidden.length);
            
            //sisipkan informasi panjang file ke cover image
            int idx=0;
            int idx_b=7;
            int x_last=0;
            int y_last=0;       
            for(int y=0; y<image_origin.getHeight(); y++){
                for(int x=0; x<image_origin.getWidth(); x++){
                    //ambil nilai RGB cover image
                    Color c = new Color(image_origin.getRGB(x,y));
                    int red = c.getRed();
                    int green = c.getGreen();
                    byte blue = (byte)c.getBlue();
                    
                    //sisipkan bit secret image ke nilai blue
                    int bitVal = (file_length[idx] >>> idx_b) & 1;  
                    blue = (byte)((blue & 0xFE)| bitVal);
                    
                    //gantikan nilai blue lama dengan nilai blue yang baru
                    Color newColor = new Color(red,green,(blue & 0xFF));
                    image_origin.setRGB(x,y,newColor.getRGB());
                    
                    idx_b--;
                    if(idx_b == -1){
                        idx_b = 7;
                        idx++;
                    }
                    
                    if(idx == file_length.length){
                        x_last = x;
                        y_last = y;
                        break;
                    }
                }
                if(idx == file_length.length)
                        break;
            }
            
            //sisipkan bit secret image ke cover image
            idx=0;
            idx_b=7;
            for(int y=y_last; y<image_origin.getHeight(); y++){
                for(int x=x_last; x<image_origin.getWidth(); x++){
                    Color c = new Color(image_origin.getRGB(x,y));
                    int red = c.getRed();
                    int green = c.getGreen();
                    byte blue = (byte)c.getBlue();
                    
                    int bitVal = (byte_file_hidden[idx] >>> idx_b) & 1;  
                    blue = (byte)((blue & 0xFE)| bitVal);
                    
                    Color newColor = new Color(red,green,(blue & 0xFF));
                    image_origin.setRGB(x,y,newColor.getRGB());
                    
                    idx_b--;
                    
                    if(idx_b == -1){
                        idx_b = 7;
                        idx++;
                    }
                    
                    if(idx == byte_file_hidden.length)
                        break;
                }
                if(idx == byte_file_hidden.length)
                        break;
            }
            //tulis byte steganografi menjadi file gambar png
            File ouptut = new File(url_file_output);
            ImageIO.write(image_origin, "png", ouptut);
            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan : "+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void extract_file(String url_stego, String url_output){
        try{
            BufferedImage image_stego = ImageIO.read(new File(url_stego));
            
            //extract informasi panjang file dari image steganografi
            int panjang_file=0;
            int loop=0;
            int x_last = 0;
            int y_last = 0;
            for(int y=0; y<image_stego.getHeight(); y++){
                for(int x=0; x<image_stego.getWidth(); x++){
                    //ambil nilai biru dari image steganografi
                    Color c = new Color(image_stego.getRGB(x,y));
                    byte blue = (byte)c.getBlue();
                    
                    panjang_file = (panjang_file << 1)|(blue & 1);
                    loop++;
                    if(loop == 32){
                        x_last = x;
                        y_last = y;
                        break;
                    }
                }
                if(loop == 32)
                    break;
            }
            
            byte[] extract = new byte[panjang_file];
            
            //extract byte file dari image steganografi
            int idx=0;
            int idx_b=0;
            for(int y=y_last; y<image_stego.getHeight(); y++){
                for(int x=x_last; x<image_stego.getWidth(); x++){
                    Color c = new Color(image_stego.getRGB(x,y));
                    byte blue = (byte)c.getBlue();
                    
                    extract[idx] = (byte) ((extract[idx]<<1)|(blue&1));
                    
                    idx_b++;
                    if(idx_b == 8){
                        idx_b = 0;
                        idx++;
                    }
                    
                    if(idx == panjang_file)
                        break;
                }
                if(idx == panjang_file)
                        break;
            }
            //tulis byte file secret menjadi file gambar
            FileOutputStream fos = new FileOutputStream(url_output);
            fos.write(extract);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Terjadi Kesalahan : "+ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private byte[] bit_conversion(int i){
       
        byte byte3 = (byte)((i & 0xFF000000) >>> 24); //0
        byte byte2 = (byte)((i & 0x00FF0000) >>> 16); //0
        byte byte1 = (byte)((i & 0x0000FF00) >>> 8 ); //0
        byte byte0 = (byte)((i & 0x000000FF) );
       
        return(new byte[]{byte3,byte2,byte1,byte0});
    }
    
    
}
