package qrcodeapp;

import com.google.zxing.WriterException;
import java.io.File;
import java.io.IOException;

public class Main {
    
    public static void main(String[] args) {
        String QR_CODE_PATH = "./MyQRCode.png";
        
        QRCodeHandler qrh = new QRCodeHandler();
        
        try {
            File file = new File("MyQRCode.png");
            file.delete();
            
            qrh.generateQRCodeImage("Hello world!", 350, 350, QR_CODE_PATH);
            String decodedText = qrh.decodeQRCode(file);
            
            if(decodedText == null) {
                System.out.println("No QR Code found in the image");
            } else {
                System.out.println("Decoded text: " + decodedText);
            }
            
        } catch (IOException e) {
            System.out.println("Could not decode QR Code, IOException :: " + e.getMessage());
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        }
    }
}
