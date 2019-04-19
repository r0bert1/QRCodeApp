package fi.pakkaner.gui;

import fi.pakkaner.logics.QRCodeHandler;

import java.awt.image.BufferedImage;
import java.util.concurrent.atomic.AtomicReference;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
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
import javafx.scene.layout.Region;

public class QRCodeApplication extends Application {

    private QRCodeHandler qrch = new QRCodeHandler();
    private BorderPane root;
    private ImageView imgWebCamCapturedImage;
    private Label decodedText;
    private Webcam webCam = null;
    private boolean stopCamera = false;
    private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<>();

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("QRCodeApplication");

        // initial scene
        
        BorderPane root1 = new BorderPane();
        root1.setStyle("-fx-background-color: #ccc;");
        Insets insets = new Insets(10);
        
        GridPane menuPane = new GridPane();
        menuPane.setHgap(10);
        menuPane.setVgap(12);
        
        Label scanLbl = new Label("Scan a QR code");
        BorderPane.setMargin(scanLbl, insets);
        Button goScanBtn = new Button("Scan");
        
        Label generateLbl = new Label("Generate your own QR code");
        Button goGenerateBtn = new Button("Generate");

        menuPane.add(scanLbl, 0, 0);
        menuPane.add(goScanBtn, 0, 1);
        menuPane.add(generateLbl, 2, 0);
        menuPane.add(goGenerateBtn, 2, 1);
        
        BorderPane.setAlignment(menuPane, Pos.CENTER);
        
        menuPane.setAlignment(Pos.CENTER);
        
        root1.setCenter(menuPane);

        // scan scene
        
        root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));
        
        
        GridPane scanPane = new GridPane();
        scanPane.setHgap(10);
        scanPane.setVgap(12);
        
        root.setStyle("-fx-background-color: #ccc;");
        imgWebCamCapturedImage = new ImageView();
        Label infoLbl = new Label("Decoded text: ");
        decodedText = new Label("No QR code detected yet.");
        BorderPane.setAlignment(decodedText, Pos.CENTER);
        
        Button backBtn1 = new Button("Back");
        
        BorderPane pane = new BorderPane();
        BorderPane.setAlignment(backBtn1, Pos.BOTTOM_RIGHT);
        
        scanPane.add(infoLbl, 0, 0);
        scanPane.add(decodedText, 1, 0);
        
        pane.setCenter(scanPane);
        pane.setBottom(backBtn1);
        
        
        
        
        //scanPane.setAlignment(Pos.CENTER);
        
        root.setCenter(imgWebCamCapturedImage);
        root.setBottom(backBtn1);
        root.setTop(decodedText);

        // generate scene
        
        BorderPane root2 = new BorderPane();
        root2.setPadding(new Insets(10));
        
        
        root2.setStyle("-fx-background-color: #ccc;");
        ImageView generatedImgView = new ImageView();
        Label imageName = new Label();
        
        BorderPane.setAlignment(imageName, Pos.CENTER);
        
        GridPane imagePropertyPane = new GridPane();
        imagePropertyPane.setHgap(10);
        imagePropertyPane.setVgap(12);
        
        Label textLabel = new Label("Text to encode:");
        TextField textInput = new TextField();
        
        Label nameLabel = new Label("Filename:");
        TextField nameInput = new TextField();
        
        Button generateBtn = new Button("Generate");
        Button backBtn2 = new Button("Back");
        BorderPane.setAlignment(backBtn2, Pos.BOTTOM_RIGHT);
        
        BorderPane base = new BorderPane();

        imagePropertyPane.add(textLabel, 0, 0);
        imagePropertyPane.add(textInput, 1, 0);
        imagePropertyPane.add(nameLabel, 0, 1);
        imagePropertyPane.add(nameInput, 1, 1);
        imagePropertyPane.add(generateBtn, 1, 2);
        
        
        
        imagePropertyPane.setAlignment(Pos.CENTER);
        base.setCenter(imagePropertyPane);
        base.setBottom(backBtn2);
        
        root2.setTop(imageName);
        root2.setCenter(generatedImgView);
        root2.setBottom(base);
        //root.setBottom(imagePropertyPane);
        
        // scenes
        
        Scene initial = new Scene(root1);
        Scene generate = new Scene(root2);
        Scene scan = new Scene(root);
        
        // button click handlers
        
        goScanBtn.setOnAction(e -> {
            initializeWebCam(0);
            primaryStage.setScene(scan);
        });
        
        goGenerateBtn.setOnAction(e -> {
            primaryStage.setScene(generate);
        });
        
        generateBtn.setOnAction(e -> {
            try {
                String text = textInput.getText();
                String name = nameInput.getText();
                
                qrch.generateQRCode(text, 400, 400, "./images/" + name);
                File file = new File("./images/" + name);
                Image generatedImg = new Image(file.toURI().toString());
                generatedImgView.setImage(generatedImg);
                imageName.setText(name);
            } catch (Exception ex) {
                System.out.println("Failed to generate qr code");
            }
        });
        
        backBtn1.setOnAction(e -> {
            primaryStage.setScene(initial);
            webCam.close();
        });
        
        backBtn2.setOnAction(e -> {
            primaryStage.setScene(initial);
        });
        
        // Setting initial scene

        primaryStage.setScene(initial);
        primaryStage.setHeight(600);
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
                BufferedImage img = null;

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
                    try {
                        if ((webCam.getImage()) != null) {

                            BufferedImage image = webCam.getImage();
                            LuminanceSource source = new BufferedImageLuminanceSource(image);
                            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                            Result result = new MultiFormatReader().decode(bitmap);

                            if (result != null) {
                                Platform.runLater(() -> {
                                    decodedText.setText(result.getText());
                                });
                            }
                        }
                    } catch (NotFoundException e) {
                        // No qr code was found in image
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
