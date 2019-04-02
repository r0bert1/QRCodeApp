
import com.google.zxing.WriterException;
import java.io.File;
import java.io.IOException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import qrcodeapp.QRCodeHandler;

public class QRCodeHandlerTest {
    
    QRCodeHandler qrh = new QRCodeHandler();
    
    @Before
    public void setUp() {
        try {
            qrh.generateQRCodeImage("Testing...", 350, 350, "./TestQRCode.png");
        } catch (IOException e) {
            System.out.println("Could not decode QR Code, IOException :: " + e.getMessage());
        } catch (WriterException e) {
            System.out.println("Could not generate QR Code, WriterException :: " + e.getMessage());
        }
    }
    
    @After
    public void tearDown() {
        File file = new File("TestQRCode.png");
        file.delete();
    }
    
    @Test
    public void QRCodeIsCreated() {
        File file = new File("TestQRCode.png");
        assertTrue(file.exists());
    }
    
    @Test
    public void imageWithoutQRCodeFailsToDecode() {
        try {
            File file = new File("BlankTestImage.png");
            assertEquals(null, qrh.decodeQRCode(file));
        } catch (IOException e) {
            System.out.println("Could not decode QR Code, IOException :: " + e.getMessage());
        }
    }

    @Test
    public void imageWithQRCodeDecodesProperly() {
        try {
            File file = new File("TestQRCode.png");
            assertEquals("Testing...", qrh.decodeQRCode(file));
        } catch (IOException e) {
            System.out.println("Could not decode QR Code, IOException :: " + e.getMessage());
        }
    }
    
}
