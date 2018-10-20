import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.io.*;
import java.nio.file.Files;
import java.util.List;


public class Main extends Application {
    public static void main(String[] args) { launch(args); }
    public void start(Stage primaryStage){



        //VARIABLES
        primaryStage.setWidth(500);
        primaryStage.setHeight(500);
        primaryStage.setTitle("Graphiques");

        Menu impo = new Menu("Importer");
        Menu expo = new Menu("Exporter");
        MenuItem lig = new MenuItem("Lignes");
        MenuItem reg = new MenuItem("Régions");
        MenuItem bar = new MenuItem("Barres");
        MenuItem png= new MenuItem("PNG");
        MenuItem gif = new MenuItem("GIF");
        impo.getItems().addAll(lig,reg,bar);
        expo.getItems().addAll(png,gif);
        MenuBar menuBar = new MenuBar(impo,expo);

        BorderPane section = new BorderPane();
        section.setTop(menuBar);

        Scene sc1=new Scene(section);


        //ACTIONS

        lig.setOnAction((event) ->{
            String[][] tab=getTab(primaryStage);
            if (tab!=null){
                //defining the axes
                final CategoryAxis xAxis = new CategoryAxis();
                final NumberAxis yAxis = new NumberAxis();
                xAxis.setLabel("Mois");
                yAxis.setLabel("Température");
                //creating the chart
                final LineChart<String,Number> root =
                        new LineChart<String,Number>(xAxis,yAxis);
                root.setTitle("Températures moyennes");
                //adding the data
                XYChart.Series series = new XYChart.Series();
                series.setName("Données");
                for (int i=0;i<tab[0].length;i++){
                    series.getData().add(new XYChart.Data(tab[0][i], Integer.parseInt(tab[1][i].trim())));
                }
                root.getData().addAll(series);
                section.setCenter(root);
            }
        });
        reg.setOnAction((event) ->{
            String[][] tab=getTab(primaryStage);
            if (tab!=null){
                //defining the axes
                final CategoryAxis xAxis = new CategoryAxis();
                final NumberAxis yAxis = new NumberAxis();
                xAxis.setLabel("Mois");
                yAxis.setLabel("Température");
                //creating the chart
                final AreaChart<String,Number> root =
                        new AreaChart<String,Number>(xAxis,yAxis);
                root.setTitle("Températures");
                //adding the data
                XYChart.Series series = new XYChart.Series();
                series.setName("Données") ;
                for (int i=0;i<tab[0].length;i++){
                    series.getData().add(new XYChart.Data(tab[0][i], Integer.parseInt(tab[1][i].trim())));
                }
                root.getData().addAll(series);
                section.setCenter(root);
            }

        });
        bar.setOnAction((event) ->{
            String[][] tab=getTab(primaryStage);
            if (tab!=null){
                //defining the axes
                final CategoryAxis xAxis = new CategoryAxis();
                final NumberAxis yAxis = new NumberAxis();
                xAxis.setLabel("Mois");
                yAxis.setLabel("Température");
                //creating the chart
                final BarChart<String,Number> root =
                        new BarChart<String, Number>(xAxis,yAxis);
                root.setTitle("Températures moyennes");
                //adding the data
                XYChart.Series series = new XYChart.Series();
                series.setName("Données");
                for (int i=0;i<tab[0].length;i++){
                    series.getData().add(new XYChart.Data(tab[0][i], Integer.parseInt(tab[1][i].trim())));
                }
                root.getData().addAll(series);
                section.setCenter(root);
            }

        });
        png.setOnAction((event) ->{
            if (section.getCenter()!=null){
                saveAsPng(section.getCenter(),primaryStage);
            }

        });
        gif.setOnAction((event) ->{
            if (section.getCenter()!=null){
                saveAsGif(section.getCenter(),primaryStage);
            }

        });

        //SHOW
        primaryStage.setScene(sc1);
        primaryStage.show();
    }

    public static File filechoosing(Stage stage){

        FileChooser fc = new FileChooser();
        fc.setTitle("Veuillez sélectionner un fichier");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(
                        "Fichiers texte", "*.dat")
        );
        File fichier = fc.showOpenDialog(stage);
        return fichier;
    }

    public static String[][] getTab(Stage stage){
        try {
            File cela=filechoosing(stage);
            if (cela!=null){
                List<String> toutesLignes = Files.readAllLines(cela.toPath());
                String[] months=toutesLignes.get(0).split(",");
                String[] temp=toutesLignes.get(1).split(",");

                String[][] tab2d={months,temp};
                return tab2d;
            }
            else{
                return null;
            }


        }catch (Exception e){
            System.out.println("Dont work ");
            return null;
        }

    }
    public void saveAsPng(Node chart, Stage stage) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Veuillez sélectionner un fichier");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(
                        "Fichiers png", "*.png")
        );
        File fichier = fc.showSaveDialog(stage);


        WritableImage image = chart.snapshot(new SnapshotParameters(), null);
        if (fichier!=null){
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", fichier);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void saveAsGif(Node chart, Stage stage) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Veuillez sélectionner un fichier");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(
                        "Fichiers gif", "*.gif")
        );
        File fichier = fc.showSaveDialog(stage);


        WritableImage image = chart.snapshot(new SnapshotParameters(), null);
        if (fichier!=null){
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "gif", fichier);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}