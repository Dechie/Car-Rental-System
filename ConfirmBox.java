package com.example.rentalsystem;

import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.stage.Stage;

public class ConfirmBox {

    public static boolean answer;
    static boolean answer2 = false;

    public static boolean confirmExit (String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle(title);
        window.setMinWidth(250);
        Label label = new Label(message);

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });

        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(30);
        grid.setVgap(20);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Text dialogTitle = new Text(message);
        grid.add(dialogTitle, 0, 0, 2, 1);

        grid.add(yesButton, 0, 1);
        grid.add(noButton, 1, 1);

        Scene scene = new Scene(grid, 250, 100);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }

    public static boolean adminPass() {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Warning!");
        window.setMinWidth(250);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15, 15, 15, 15));

        Text enterKey = new Text("Enter Admin Key to Proceed.");
        grid.add(enterKey, 0, 0, 2, 1);

        Label keyLabel = new Label("Admin Key:");
        grid.add(keyLabel, 0, 1);

        TextField adminKey = new TextField();
        grid.add(adminKey, 1, 1);

        Button okButton = new Button("Go");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(okButton);
        grid.add(hbBtn, 1, 4);

        okButton.setOnAction(e -> {
            String passKey = adminKey.getText().toString();

            if (passKey.equals("1234"))
            {
                answer2 = true;
                window.close();
            }
        });

        Scene scene = new Scene(grid, 250, 150);
        window.setScene(scene);
        window.showAndWait();

        return answer2;

    }

    public static void alertBox(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle(title);
        window.setMinWidth(250);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15, 15, 15, 15));

        Text showMessage = new Text(message);
        grid.add(showMessage, 0, 0, 2, 1);

        Button okButton = new Button("OK");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_CENTER);
        hbBtn.getChildren().add(okButton);
        grid.add(hbBtn, 0, 1);

        okButton.setOnAction(e -> window.close());

        Scene scene = new Scene(grid, 250, 150);
        window.setScene(scene);
        window.showAndWait();

    }
}
