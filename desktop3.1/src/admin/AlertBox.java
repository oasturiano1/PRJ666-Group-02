package admin;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class AlertBox {
    public static Stage window2;
    static boolean qry;


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


    public  static boolean checkForDelete(String title,String msg){
        window2 = new Stage();
        window2.initModality(Modality.APPLICATION_MODAL); //block other window
        window2.setTitle(title);
        window2.setWidth(350);
        window2.setHeight(150);
        window2.setResizable(false);


        Label label = new Label();
        label.setText(msg);

        Button canbtn = new Button("CANCELL");
        canbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                qry = false;
                window2.close();
            }
        });

        Button okbtn = new Button("OK");
        okbtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                qry = true;
                window2.close();
            }
        });


        VBox layout = new VBox(20);
        layout.getChildren().addAll(label,canbtn,okbtn);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);

        window2.setScene(scene);
        window2.showAndWait();

        return qry;
    }

}
