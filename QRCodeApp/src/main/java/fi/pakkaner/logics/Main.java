package fi.pakkaner.logics;

import java.io.IOException;

public class Main {
    
    public static void main(String[] args) {
        
        QRCodeHandler qrch = new QRCodeHandler();
        
        try {
            String text = qrch.decodeQRCodeFromWebcam();
            System.out.println("Decoded text: " + text);
        } catch (IOException e) {
            System.out.println("Could not decode QR Code, IOException :: " + e.getMessage());
        } /*catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        }*/
    }
}
