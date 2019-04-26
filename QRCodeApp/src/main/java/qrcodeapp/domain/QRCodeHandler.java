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

public class QRCodeHandler {

    public void generateQRCode(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }
    
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
    
    public String decodeQRCodeFromStorage(File qrCode) throws IOException {
        BufferedImage image = ImageIO.read(qrCode);
        String decodedText = decodeQRCode(image);
        return decodedText;
    }
    
    public String decodeQRCodeFromWebcam(Webcam webcam) throws IOException {
        BufferedImage image = webcam.getImage();
        String decodedText = decodeQRCode(image);
        return decodedText;
    }
    
}