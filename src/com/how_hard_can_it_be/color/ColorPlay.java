package com.how_hard_can_it_be.color;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class ColorPlay extends Application
{
    private static double SPACING = 20;
    private static double THUMB_PANE_SIZE_OFFSET = 10;
    
    PlanetHeightColorGradient planetGradient;

    private HBox gradientHBox;

    private AnchorPane thumbPane;
    
    /**
     * Provided by {@link Application} -- must override.  Stage is top-level container.  Scene contains content.
     */
    @Override
    public void start(Stage primaryStage)
    {
        // Scene scene = makeButtonScene();
        Scene scene = makeGradientEditorScene();

        primaryStage.setTitle("ColorPlay");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Make a scene containing a single "hello" button.
     * @return
     */
    private Scene makeButtonScene()
    {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event)
            {
                System.out.println("Hello World!");
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);
        return scene;
    }
    
    /**
     * Make a scene containing a gradient editor.
     */
    private Scene makeGradientEditorScene()
    {
        Scene retval;
        
        BorderPane borderPane = new BorderPane();
        
        MenuBar menuBar = makeMenuBar();
        borderPane.setTop(menuBar);
        
        VBox vbox = new VBox( SPACING);
        borderPane.setCenter(vbox);
        
        vbox.setFillWidth(false);
        vbox.setAlignment(Pos.BASELINE_CENTER);
//        vbox.setHgap(SPACING);
//        vbox.setVgap(SPACING);
        vbox.setPadding( new Insets(SPACING));
        
        gradientHBox = new HBox();
        vbox.getChildren().add(gradientHBox); // , 0, 0, 2, 1);
        gradientHBox.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, 
                BorderWidths.DEFAULT)));
        
        Rectangle waterGradientRect = new Rectangle(300, 70);
        gradientHBox.getChildren().add(waterGradientRect);
//        waterGradientRect.setStroke(Color.BLACK);
//        waterGradientRect.setStrokeWidth(1);
        // gradientRect.setFill(Color.TRANSPARENT);
        waterGradientRect.setOnMouseClicked((MouseEvent t) -> handleGradientMouseClick(t));

        Rectangle landGradientRect = new Rectangle(300, 70);
        gradientHBox.getChildren().add(landGradientRect);
        waterGradientRect.setOnMouseClicked((MouseEvent t) -> handleGradientMouseClick(t));
        
        makePlanetGradient();
        
        waterGradientRect.setFill(planetGradient.getWaterGradient());
        landGradientRect.setFill(planetGradient.getLandGradient());
        
        thumbPane = new AnchorPane();
        vbox.getChildren().add( thumbPane);
        thumbPane.setPrefHeight(20); // Max. height of a thumb.
        thumbPane.setMinWidth( 600 + 2 * THUMB_PANE_SIZE_OFFSET); // Add max. width of a thumb.
        thumbPane.setBorder( new Border( new BorderStroke(Color.SILVER, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, 
                BorderWidths.DEFAULT)));
        
        makeThumbs();
        
        // Properties of the current color stop.
        HBox propsBox = new HBox(SPACING);
        vbox.getChildren().add(propsBox);
        
        ColorPicker cp = new ColorPicker();
        propsBox.getChildren().add(cp); //, 0, 1);
        
        GridPane rgbGrid = new GridPane();
        rgbGrid.setHgap(SPACING / 2);
        rgbGrid.setVgap(SPACING / 2);
//        rgbGrid.setPadding(new Insets(SPACING));
        
        ColumnConstraints cc1 = new ColumnConstraints(60);
        ColumnConstraints cc2 = new ColumnConstraints(50);
        rgbGrid.getColumnConstraints().addAll(cc1, cc2);
        
        propsBox.getChildren().add(rgbGrid); //, 1, 1);
        
        rgbGrid.add( new Label("Red"), 0, 0);
        rgbGrid.add( new Label("Green"), 0, 1);
        rgbGrid.add( new Label("Blue"), 0, 2);
        
        TextField redTF = new TextField();
        TextField greenTF = new TextField();
        TextField blueTF = new TextField();

        rgbGrid.add( redTF, 1, 0);
        rgbGrid.add( greenTF, 1, 1);
        rgbGrid.add( blueTF, 1, 2);
        
        
        retval = new Scene( borderPane);
        
        return retval;
    }

    private void makePlanetGradient()
    {
        planetGradient = new PlanetHeightColorGradient();
        planetGradient.addWaterColorStop(Color.BLUE, 0.0);
        planetGradient.addWaterColorStop(Color.ALICEBLUE, 1.0);
        
        planetGradient.addLandColorStop(Color.GREEN, 0.0);
        planetGradient.addLandColorStop(Color.GOLDENROD, 0.25);
        planetGradient.addLandColorStop(Color.SADDLEBROWN, 0.5);
        planetGradient.addLandColorStop(Color.SLATEGRAY, 0.75);
        planetGradient.addLandColorStop(Color.WHITE, 1.0);
    }

    private void makeThumbs()
    {
        for (ColorStop stop : planetGradient.getWaterColorStops())
        {
            
        }
    }

    /**
     * User click on the gradient rectangle.  Either select an existing gradient point, or create a new one.
     * @param anEvent
     * @return
     */
    private Object handleGradientMouseClick(MouseEvent anEvent)
    {
        Object srcObj = anEvent.getSource();
        try
        {
            Shape srcShape = (Shape) srcObj;
            System.out.println(String.format("mouse event at (%g, %g), which is %5.1f%% from the left", anEvent.getX(),
                    anEvent.getY(), anEvent.getX() / srcShape.getBoundsInLocal().getWidth() * 100));
            double x = anEvent.getX();
        }
        catch (ClassCastException exc)
        {
            System.out.println("Unexpected source: " + srcObj);
        }
        return null;
    }

    private MenuBar makeMenuBar()
    {
        MenuBar retval = new MenuBar();
        Menu fileMenu = new Menu("File");
        retval.getMenus().add( fileMenu);
        
        MenuItem loadMI = new MenuItem("Load");
        MenuItem saveMI = new MenuItem("Save");
        MenuItem saveAsMI = new MenuItem("Save As");
        MenuItem quitMI = new MenuItem("Quit");
        fileMenu.getItems().addAll(loadMI, saveMI, saveAsMI, quitMI);
        
        loadMI.setOnAction((ActionEvent t)-> { loadFile();});
        saveMI.setOnAction((ActionEvent t)->{saveFile();});
        saveAsMI.setOnAction((ActionEvent t)->{saveFileAs();});
        quitMI.setOnAction((ActionEvent t) -> { quit(); /* Platform.exit(); */ });
        
        return retval;
    }

    private void loadFile()
    {
        // TODO Auto-generated method stub
        
    }

    private void saveFile()
    {
        // TODO Auto-generated method stub
        
    }

    private void saveFileAs()
    {
        // TODO Auto-generated method stub
        
    }

    private void quit()
    {
        // TODO Auto-generated method stub
        Platform.exit();
    }

    /**
     * Not strictly required, since the JavaFX packager will add something.  But, still, useful in case the packager
     * doesn't get run or some other tool runs the created jar file.
     */
    public static void main(String[] args)
    {
        launch(args);
    }
}
