package qrcodeapp.domain;

import com.github.sarxos.webcam.Webcam;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * This class offers methods for both reading and writing QR codes.
 */
public class QRCodeHandler {

    /**
     * This method creates a QR code image based on the given parameters 
     * and then saves it on the system.
     * 
     * @param text Text to be encoded in QR code
     * @param width Width of QR code image
     * @param height Height of QR code image
     * @param filePath Path of the generated image
     * 
     * @throws WriterException
     * @throws IOException
     */
    public void generateQRCode(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }
    
    /**
     * This method decodes the data encoded in a QR code image.
     * 
     * @param image A BufferedImage object generated from QR code image
     * 
     * @return Decoded data as a string
     * 
     * @throws IOException
     */
    public String decodeQRCode(BufferedImage image) throws IOException {
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            // No QR code is detected
            return null;
        }
    }
    
    /**
     * This method scans an QR code image from the system 
     * and decodes any detected QR code using the decodeQRCode method.
     * 
     * @param qrCode Image file to be decoded
     * 
     * @return Decoded data as a string
     * 
     * @throws IOException
     */
    public String decodeQRCodeFromStorage(File qrCode) throws IOException {
        BufferedImage image = ImageIO.read(qrCode);
        String decodedText = decodeQRCode(image);
        return decodedText;
    }
    
    /**
     * This method scans the webcam image
     * and decodes any detected QR code using the decodeQRCode method.
     * 
     * @param webcam Webcam that will be used for scanning
     * 
     * @return Decoded data as a string
     * 
     * @throws IOException
     */
    public String decodeQRCodeFromWebcam(Webcam webcam) throws IOException {
        BufferedImage image = webcam.getImage();
        String decodedText = decodeQRCode(image);
        return decodedText;
    }
    
}