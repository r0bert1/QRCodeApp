package qrcodeapp.ui;

import qrcodeapp.domain.QRCodeHandler;

import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicReference;

import com.github.sarxos.webcam.Webcam;
import java.io.File;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;

public class QRCodeApplication extends Application {

    private QRCodeHandler qrch = new QRCodeHandler();
    private BorderPane root;
    private ImageView imgWebCamCapturedImage;
    private Label decodedText;
    private Webcam webCam = null;
    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("QR Code Application");
        
        // DirectoryChooser setup
        
        final DirectoryChooser dirChooser = new DirectoryChooser();
        dirChooser.setTitle("Choose directory");
        dirChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        // Start menu scene
        
        BorderPane root1 = new BorderPane();
        root1.setStyle("-fx-background-color: #ccc;");
        
        GridPane menuPane = new GridPane();
        menuPane.setHgap(10);
        menuPane.setVgap(12);
        menuPane.setAlignment(Pos.CENTER);
        
        Label scanLbl = new Label("Scan a QR code");
        Button goScanBtn = new Button("Scan");
        
        Label generateLbl = new Label("Generate your own QR code");
        Button goGenerateBtn = new Button("Generate");

        menuPane.add(scanLbl, 0, 0);
        menuPane.add(goScanBtn, 0, 1);
        menuPane.add(generateLbl, 2, 0);
        menuPane.add(goGenerateBtn, 2, 1);
        
        root1.setCenter(menuPane);

        // Scan scene
        
        root = new BorderPane();
        root.setPadding(new Insets(10));
        
        root.setStyle("-fx-background-color: #ccc;");
        imgWebCamCapturedImage = new ImageView();
        decodedText = new Label("No QR code detected yet.");
        decodedText.setStyle("fx-font-weight: bold");
        decodedText.setPadding(new Insets(25, 0, 0, 0));
        BorderPane.setAlignment(decodedText, Pos.CENTER);
        
        Button backBtn1 = new Button("Back");
        BorderPane.setAlignment(backBtn1, Pos.BOTTOM_RIGHT);
        
        root.setCenter(imgWebCamCapturedImage);
        root.setBottom(backBtn1);
        root.setTop(decodedText);

        // Generate scene
        
        BorderPane root2 = new BorderPane();
        root2.setPadding(new Insets(10));
        root2.setStyle("-fx-background-color: #ccc;");
        
        ImageView generatedImgView = new ImageView();
        
        Label imageName = new Label();
        BorderPane.setAlignment(imageName, Pos.CENTER);
        
        GridPane imagePropertyPane = new GridPane();
        imagePropertyPane.setHgap(10);
        imagePropertyPane.setVgap(12);
        imagePropertyPane.setAlignment(Pos.CENTER);
        
        Label textLabel = new Label("Text to encode:");
        TextField textInput = new TextField();
        Label nameLabel = new Label("Filename:");
        TextField nameInput = new TextField();  
        Button generateBtn = new Button("Generate");
        
        Button backBtn2 = new Button("Back");
        BorderPane.setAlignment(backBtn2, Pos.BOTTOM_RIGHT);
        
        BorderPane base = new BorderPane();
        
        Label dirLbl = new Label("Target directory:");
        TextField dirFld = new TextField();
        Button browseBtn = new Button("Browse");

        imagePropertyPane.add(textLabel, 0, 0);
        imagePropertyPane.add(textInput, 1, 0);
        imagePropertyPane.add(nameLabel, 0, 1);
        imagePropertyPane.add(nameInput, 1, 1);
        imagePropertyPane.add(generateBtn, 1, 3);
        imagePropertyPane.add(dirLbl, 0, 2);
        imagePropertyPane.add(dirFld, 1, 2);
        imagePropertyPane.add(browseBtn, 3, 2);
         
        base.setCenter(imagePropertyPane);
        base.setBottom(backBtn2);
        
        root2.setTop(imageName);
        root2.setCenter(generatedImgView);
        root2.setBottom(base);
        
        // Scenes
        
        Scene start = new Scene(root1);
        Scene generate = new Scene(root2);
        Scene scan = new Scene(root);
        
        // Button click handlers
        
        goScanBtn.setOnAction(e -> {
            initializeWebCam(0);
            primaryStage.setScene(scan);
        });
        
        goGenerateBtn.setOnAction(e -> {
            primaryStage.setScene(generate);
        });
        
        browseBtn.setOnAction(e -> {
            File dir = dirChooser.showDialog(primaryStage);
            
            if (dir != null) {
                dirFld.setText(dir.getAbsolutePath());
            } else {
                dirFld.setText(null);
            }
        });
        
        generateBtn.setOnAction(e -> {
            try {
                String text = textInput.getText();
                String name = nameInput.getText();
                String targetDir = dirFld.getText();
                
                qrch.generateQRCode(text, 400, 400, targetDir + "/" + name);
                File file = new File(targetDir + "/" + name);
                Image generatedImg = new Image(file.toURI().toString());
                generatedImgView.setImage(generatedImg);
                imageName.setText(name);
                
                textInput.clear();
                nameInput.clear();
                dirFld.clear();
            } catch (Exception ex) {
                System.out.println("Failed to generate qr code");
            }
        });
        
        backBtn1.setOnAction(e -> {
            primaryStage.setScene(start);
            webCam.close();
        });
        
        backBtn2.setOnAction(e -> {
            textInput.clear();
            nameInput.clear();
            dirFld.clear();
            primaryStage.setScene(start);
        });
        
        // Setting initial scene

        primaryStage.setScene(start);
        primaryStage.setHeight(700);
        primaryStage.setWidth(700);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    protected void initializeWebCam(final int webCamIndex) {
        Task<Void> webCamTask = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                webCam = Webcam.getWebcams().get(webCamIndex);
                webCam.open();

                startWebCamStream();
                scan();

                return null;
            }
        };

        Thread webCamThread = new Thread(webCamTask);
        webCamThread.setDaemon(true);
        webCamThread.start();

    }

    protected void startWebCamStream() {
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                final AtomicReference<WritableImage> ref = new AtomicReference<>();
                BufferedImage img;

                while (webCam.isOpen()) {
                    try {
                        if ((img = webCam.getImage()) != null) {

                            ref.set(SwingFXUtils.toFXImage(img, ref.get()));
                            img.flush();

                            Platform.runLater(() -> {
                                imageProperty.set(ref.get());
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                
                return null;
            }
        };

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
        imgWebCamCapturedImage.imageProperty().bind(imageProperty);

    }

    protected void scan() {
        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {
                while (webCam.isOpen()) {
                    if ((webCam.getImage()) != null) {
                        String result = qrch.decodeQRCodeFromWebcam(webCam);

                        if (result != null) {
                            Platform.runLater(() -> {
                                decodedText.setText(result);
                            });
                        }
                    }
                }

                return null;
            }
        };

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
