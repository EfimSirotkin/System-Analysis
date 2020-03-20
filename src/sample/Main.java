package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

    public static Label generalRMSLabel;
    public static Label redRMSLabel;
    public static Label greenRMSLabel;
    public static Label blueRMSLabel;

    public static Label generalModaLabel;
    public static Label redModaLabel;
    public static Label greenModaLabel;
    public static Label blueModaLabel;

    public static Label generalMedianLabel;
    public static Label redMedianLabel;
    public static Label greenMedianLabel;
    public static Label blueMedianLabel;

    @Override
    public void start(Stage primaryStage) throws Exception{
        ImageProcessor.grayScaleTransform("src/sample/romashki.jpg");
        ImageProcessor.getDistributionArray("src/sample/romashki.jpg");

        FileInputStream primaryImageFileStream = new FileInputStream("src/sample/romashki.jpg");
        FileInputStream outputImageFileStream = new FileInputStream("src/sample/grayscaled.jpg");
        Text primaryImageText = new Text("Оригинал");
        Text outputImageText = new Text("Черно-белое");
        primaryImageText.setFont(new Font(20));
        outputImageText.setFont(new Font(20));

        generalHistogram.setCreateSymbols(false);
        redHistogram.setCreateSymbols(false);
        greenHistogram.setCreateSymbols(false);
        blueHistogram.setCreateSymbols(false);

        generalHistogram.setTitle("Гистограмма");
        redHistogram.setTitle("Гистограмма(R)");
        greenHistogram.setTitle("Гистограмма(G)");
        blueHistogram.setTitle("Гистограмма(B)");

        generalHistogram.setMinSize(800,200);
        redHistogram.setMinSize(800,200);
        greenHistogram.setMinSize(800, 200);
        blueHistogram.setMinSize(800, 200);

        generalMELabel.setFont(new Font(14));
        redMELabel.setFont(new Font(14));
        greenMELabel.setFont(new Font(14));
        blueMELabel.setFont(new Font(14));

        generalMELabel.setStyle("-fx-font-weight: bold");
        redMELabel.setStyle("-fx-font-weight: bold");
        greenMELabel.setStyle("-fx-font-weight: bold");
        blueMELabel.setStyle("-fx-font-weight: bold");

        generalRMSLabel.setStyle("-fx-font-weight: bold");
        redRMSLabel.setStyle("-fx-font-weight: bold");
        greenRMSLabel.setStyle("-fx-font-weight: bold");
        blueRMSLabel.setStyle("-fx-font-weight: bold");

        generalModaLabel.setStyle("-fx-font-weight: bold");
        redModaLabel.setStyle("-fx-font-weight: bold");
        greenModaLabel.setStyle("-fx-font-weight: bold");
        blueModaLabel.setStyle("-fx-font-weight: bold");

        generalMedianLabel.setStyle("-fx-font-weight: bold");
        redMedianLabel.setStyle("-fx-font-weight: bold");
        greenMedianLabel.setStyle("-fx-font-weight: bold");
        blueMedianLabel.setStyle("-fx-font-weight: bold");


        Image primaryImage = new Image(primaryImageFileStream);
        ImageView primaryImageView = new ImageView(primaryImage);
        Image outputImage = new Image(outputImageFileStream);
        ImageView outputImageView = new ImageView(outputImage);

        primaryImageView.setFitWidth(400);
        primaryImageView.setFitHeight(300);
        outputImageView.setFitWidth(400);
        outputImageView.setFitHeight(300);


        VBox valuesVBox = new VBox(200);
        VBox generalValuesVBox = new VBox(10);
        VBox redValuesVBox = new VBox(10);
        VBox greenValuesVBox = new VBox(10);
        VBox blueValuesVBox = new VBox(10);

        VBox primaryImageBox = new VBox(10);
        VBox outputImageBox = new VBox(10);
        VBox imagesVBox = new VBox(50);
        VBox histogramsVBox = new VBox(30);
        HBox mainLayout = new HBox(100);


        primaryImageBox.setAlignment(Pos.CENTER);
        outputImageBox.setAlignment(Pos.CENTER);
        mainLayout.setAlignment(Pos.CENTER);

        mainLayout.setPadding(new Insets(30,30,30,30));

        primaryImageBox.getChildren().addAll(primaryImageView, primaryImageText);
        outputImageBox.getChildren().addAll(outputImageView, outputImageText);
        imagesVBox.getChildren().addAll(primaryImageBox, outputImageBox);

        generalValuesVBox.getChildren().addAll(generalMELabel, generalRMSLabel, generalModaLabel, generalMedianLabel);
        redValuesVBox.getChildren().addAll(redMELabel, redRMSLabel, redModaLabel, redMedianLabel);
        greenValuesVBox.getChildren().addAll(greenMELabel, greenRMSLabel, greenModaLabel, greenMedianLabel);
        blueValuesVBox.getChildren().addAll(blueMELabel, blueRMSLabel, blueModaLabel, blueMedianLabel);
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
