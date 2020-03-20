package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Main extends Application {

    public static LineChart<String, Number> generalHistogram;
    public static LineChart<String, Number> redHistogram;
    public static LineChart<String, Number> greenHistogram;
    public static LineChart<String, Number> blueHistogram;

    public static Label generalMELabel;
    public static Label redMELabel;
    public static Label greenMELabel;
    public static Label blueMELabel;

    @Override
    public void start(Stage primaryStage) throws Exception{
        ImageProcessor.grayScaleTransform("F:\\Code\\labsIIPY\\SA3\\src\\sample\\romashki.jpg");
        ImageProcessor.getDistributionArray("F:\\Code\\labsIIPY\\SA3\\src\\sample\\romashki.jpg");

        FileInputStream primaryImageFileStream = new FileInputStream("F:\\Code\\labsIIPY\\SA3\\src\\sample\\romashki.jpg");
        FileInputStream outputImageFileStream = new FileInputStream("F:\\Code\\labsIIPY\\SA3\\src\\sample\\grayscaled.jpg");
        Text primaryImageText = new Text("Оригинал");
        Text outputImageText = new Text("Черно-белое");
        primaryImageText.setFont(new Font(20));
        outputImageText.setFont(new Font(20));

        generalHistogram.setCreateSymbols(false);
        redHistogram.setCreateSymbols(false);
        greenHistogram.setCreateSymbols(false);
        blueHistogram.setCreateSymbols(false);

        generalHistogram.setTitle("Гистограмма изображения");
        redHistogram.setTitle("Гистограмма красной составляющей");
        greenHistogram.setTitle("Гистограмма зеленой составляющей");
        blueHistogram.setTitle("Гистограмма голубой составляющей");

        generalHistogram.setMinSize(600,200);
        redHistogram.setMinSize(600,200);
        greenHistogram.setMinSize(600, 200);
        blueHistogram.setMinSize(600, 200);

        Image primaryImage = new Image(primaryImageFileStream);
        ImageView primaryImageView = new ImageView(primaryImage);
        Image outputImage = new Image(outputImageFileStream);
        ImageView outputImageView = new ImageView(outputImage);

        primaryImageView.setFitWidth(400);
        primaryImageView.setFitHeight(300);
        outputImageView.setFitWidth(400);
        outputImageView.setFitHeight(300);


        VBox valuesVBox = new VBox(50);
        VBox generalValuesVBox = new VBox(10);
        VBox redValuesVBox = new VBox(10);
        VBox greenValuesVBox = new VBox(10);
        VBox blueValuesVBox = new VBox(10);

        VBox primaryImageBox = new VBox(10);
        VBox outputImageBox = new VBox(10);
        VBox imagesVBox = new VBox(50);
        VBox histogramsVBox = new VBox(30);
        HBox mainLayout = new HBox(50);


        primaryImageBox.setAlignment(Pos.CENTER);
        outputImageBox.setAlignment(Pos.CENTER);

        mainLayout.setPadding(new Insets(30,30,30,30));

        primaryImageBox.getChildren().addAll(primaryImageView, primaryImageText);
        outputImageBox.getChildren().addAll(outputImageView, outputImageText);
        imagesVBox.getChildren().addAll(primaryImageBox, outputImageBox);

        generalValuesVBox.getChildren().addAll(generalMELabel);
        redValuesVBox.getChildren().addAll(redMELabel);
        greenValuesVBox.getChildren().addAll(greenMELabel);
        blueValuesVBox.getChildren().addAll(blueMELabel);
        valuesVBox.getChildren().addAll(generalValuesVBox, redValuesVBox,greenValuesVBox,blueValuesVBox);

        histogramsVBox.getChildren().addAll(generalHistogram, redHistogram, greenHistogram, blueHistogram);
        mainLayout.getChildren().addAll(histogramsVBox,valuesVBox, imagesVBox);

        primaryStage.setTitle("SA lab 3");
        primaryStage.setScene(new Scene(mainLayout, 1280, 720));
        primaryStage.show();
        primaryStage.setMaximized(true);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
