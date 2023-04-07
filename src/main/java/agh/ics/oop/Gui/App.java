package agh.ics.oop.Gui;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application {
//to jest glówna classa funckji

    public void start(Stage primayStage) {
        Scene scene = new Scene(new appMenu().menuVBox, 300, 250); // tutaj jest obiegk appMenu
        primayStage.setScene(scene);
        primayStage.setTitle("Main Menu");


        //close program when main menu x clicked
        //kills all thread
        primayStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        primayStage.show();
    }


}
