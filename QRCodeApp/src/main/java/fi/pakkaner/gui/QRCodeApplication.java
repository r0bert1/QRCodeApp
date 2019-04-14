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

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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

        root = new BorderPane();

        root.setStyle("-fx-background-color: #ccc;");
        imgWebCamCapturedImage = new ImageView();
        decodedText = new Label("Placeholder");
        decodedText.setPrefHeight(40);
        BorderPane.setAlignment(decodedText, Pos.BOTTOM_CENTER);
        root.setCenter(imgWebCamCapturedImage);
        root.setBottom(decodedText);

        initializeWebCam(0);

        primaryStage.setScene(new Scene(root));
        primaryStage.setHeight(700);
        primaryStage.setWidth(600);
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

        stopCamera = false;

        Task<Void> task = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                final AtomicReference<WritableImage> ref = new AtomicReference<>();
                BufferedImage img = null;

                while (!stopCamera) {
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
                while (!stopCamera) {
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
                        // Most likely no qr code was found in image
                        //e.printStackTrace();
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
