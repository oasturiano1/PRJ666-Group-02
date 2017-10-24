package admin;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class AlertBox {

    public static void display(String title,String msg){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL); //block other window
        window.setTitle(title);
        window.setWidth(350);
        window.setHeight(150);
        window.setResizable(false);


        Label label = new Label();
        label.setText(msg);

        Button closebtn = new Button("CLOSE");
        closebtn.setOnAction(e->window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,closebtn);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();
    }
}
