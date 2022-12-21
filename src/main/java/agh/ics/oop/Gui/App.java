package agh.ics.oop.Gui;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class App extends Application {


    public void start(Stage primayStage) {
        Scene scene = new Scene(new appMenu(this).gridPane, 400, 400);
        primayStage.setScene(scene);
        primayStage.setTitle("Main Menu");


        primayStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
            //close program when main menu x clicked
            //kills all thread
        });

        primayStage.show();
    }


}
