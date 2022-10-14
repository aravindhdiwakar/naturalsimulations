package com.example.naturalsimulations;

//JavaFX imports
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.*;

//the entire window is called the stage
//content inside the stage is called the scene
//stuff inside the scene is called a group
public class Main extends Application {
    public static void main(String[] args) {
        //launches JavaFX
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //creates a new window called Natural Simulations and prevents resizing
        primaryStage.setTitle("Natural Simulations");
        primaryStage.setResizable(false);

        //creates a new Group home
        //will contain all buttons and text
        Group home = new Group();
        //creates a new 1000 x 800 Scene scene with home as the "root"
        Scene scene = new Scene(home, 1000, 600, Color.web("#9fcae3"));

        //makes primaryStage and scene public to the myButton class
        //myButton plays an extremely important role in my project
        //handles all button functions (back and forth) and is the main basis of the home scene
        myButton.stage = primaryStage;
        myButton.home = scene;

        //sets scene as the "root" of primaryStage
        primaryStage.setScene(scene);

        //text and font stuff
        String font = "file:src/resources/fonts/MantiSansDemo-ywLjq.ttf"; //loads font from file fonts
        Text title = new Text(160, 75,"Natural Simulations");
        title.setFont(Font.loadFont(font, 80));
        home.getChildren().add(title); //"nodes" as JavaFX must always be added as children to the group, which appears on the scene

        //more text and font stuff
        Text name = new Text(275, 150, "Aravindh Diwakar");
        name.setFont(Font.loadFont(font, 60));
        home.getChildren().add(name);

        //vectors button and scene
        myButton vectors = new myButton(200, 200, 200, 75);
        home.getChildren().add(vectors);
        vectors.setText("Vectors", 213, 255, 50, home);
        Vectors sceneVectors = new Vectors();
        vectors.setScene(sceneVectors.returnScene());

        //forces button and scene
        myButton forces = new myButton(600, 200, 200, 75);
        home.getChildren().add(forces);
        forces.setText("Forces", 627, 255, 50, home);
        Forces sceneForces = new Forces();
        forces.setScene(sceneForces.returnScene());

        //fluids button and scene
        myButton fluids = new myButton(200, 325, 200, 75);
        home.getChildren().add(fluids);
        fluids.setText("Fluids", 238, 380, 50, home);
        Fluids sceneFluids = new Fluids();
        fluids.setScene(sceneFluids.returnScene());

        //attraction button and scene
        myButton attraction = new myButton(600, 325, 200, 75);
        home.getChildren().add(attraction);
        attraction.setText("Attraction", 602, 378, 43, home);
        Attraction sceneAttraction = new Attraction();
        attraction.setScene(sceneAttraction.returnScene());

        //waves button and scene
        myButton waves = new myButton(200, 450, 200, 75);
        home.getChildren().add(waves);
        waves.setText("Waves", 227, 505, 50, home);
        Waves sceneWaves = new Waves();
        waves.setScene(sceneWaves.returnScene());

        //pendulum button and scene
        myButton pendulum = new myButton(600, 450, 200, 75);
        home.getChildren().add(pendulum);
        pendulum.setText("Pendulum", 606, 503, 45, home);
        Pendulum scenePendulum = new Pendulum();
        pendulum.setScene(scenePendulum.returnScene());

        //calls the static method buttonFunction from myButton
        //handles all button interactions and functions
        myButton.buttonFunction();

        //show the stage
        primaryStage.show();
    }
}
