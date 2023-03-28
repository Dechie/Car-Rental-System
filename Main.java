package com.example.rentalsystem;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    protected static ArrayList<Customer> customers = new ArrayList<Customer>();
    protected static ArrayList<Admin> admins = new ArrayList<Admin>();

    protected static ArrayList<Car> cars = new ArrayList<Car>();

    static Stage window;
    static Scene mainPage, adminMain, customerMain, adminMenuScene, customerMenuScene;
    static RentalSystem rs = new RentalSystem();


    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("this system");
        window = primaryStage;

        Button mainMenu1 = new Button("Main Menu");
        mainMenu1.setOnAction(e -> window.setScene(mainPage));
        Button mainMenu2 = new Button("Main Menu");
        mainMenu2.setOnAction(e -> window.setScene(mainPage));

        Button mainPageAdmin = new Button("Admin");
        Button mainPageCustomer = new Button("Customer");
        Button exitButton = new Button("Exit Program");

        mainPageAdmin.setOnAction(e ->
        {
            boolean pass = ConfirmBox.adminPass();
            if (pass)
                window.setScene(adminMain);
            else
                window.setScene(mainPage);
        });

        mainPageCustomer.setOnAction(e -> window.setScene(customerMain));
        exitButton.setOnAction(e -> closeProgram());

        VBox mainPageLayout = new VBox(20);
        mainPageLayout.getChildren().addAll(mainPageAdmin, mainPageCustomer, exitButton);
        mainPageLayout.setAlignment(Pos.CENTER);
        mainPage = new Scene(mainPageLayout, 640, 480);

        Button customerSignIn = new Button("Sign in");
        Button customerSignUp = new Button("Sign up");
        customerSignIn.setOnAction(e -> login( 0));
        customerSignUp.setOnAction(e -> register(0));

        VBox customerMainLayout = new VBox(20);
        customerMainLayout.getChildren().addAll(customerSignIn, customerSignUp, mainMenu1);
        customerMainLayout.setAlignment(Pos.CENTER);

        customerMain = new Scene(customerMainLayout, 640, 480);

        Button adminSignIn = new Button("Sign in");
        Button adminSignUp = new Button("Sign up");
        adminSignIn.setOnAction(e -> login(1));
        adminSignUp.setOnAction(e -> register( 1));

        VBox adminMainLayout = new VBox(20);
        adminMainLayout.getChildren().addAll(adminSignIn, adminSignUp, mainMenu2);
        adminMainLayout.setAlignment(Pos.CENTER);
        adminMain = new Scene(adminMainLayout, 640, 480);

        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        window.setScene(mainPage);
        window.setTitle("Rental System");
        window.show();
    }

    void closeProgram() {
        boolean answer = ConfirmBox.confirmExit("Exit System", "Are You Sure You Want To Exit?");

        if (answer)
            window.close();
    }

    void login(int whichPage)
    {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        String[] choices = {"Welcome, Dear Customer", "Welcome, Dear Admin"};
        String titleText = choices[whichPage];

        Scene scene = new Scene(grid, 300, 275);
        Text scenetitle = new Text(titleText);
        grid.add(scenetitle, 0, 0, 2, 1);

        Label userName = new Label("User Name:");
        grid.add(userName, 0, 1);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 1);

        Label pw = new Label("Password:");
        grid.add(pw, 0, 2);

        PasswordField pwBox = new PasswordField();
        grid.add(pwBox, 1, 2);

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);

        btn.setOnAction(e ->
        {
            boolean check;
            String pass = pwBox.getText().toString();
            String nm = userTextField.getText().toString();

            if (whichPage == 1)
            {
                check = rs.validateAdmin(admins, nm, pass);

                if (check)
                    adminMenu();
                else
                    window.setScene(mainPage);
            }

            else if (whichPage == 0)
            {
                Customer currentCustomer = rs.validateCustomer(customers, nm, pass);

                if (currentCustomer != null) {
                    customerMenu(currentCustomer);
                }
                else
                    window.setScene(mainPage);

            }

        });

        window.setScene(scene);
    }

    void register(int whichPage)
    {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        String[] choices = {"Welcome, Dear Customer", "Welcome, Dear Admin"};
        String titleText = choices[whichPage];

        Scene scene = new Scene(grid, 300, 275);
        Text scenetitle = new Text(titleText);
        Label userName = new Label("Username:");
        TextField userTextField = new TextField();
        Label pw = new Label("Password:");
        PasswordField pwBox = new PasswordField();
        Button btn = new Button("Sign Up");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);

        grid.add(scenetitle, 0, 0, 2, 1);
        grid.add(userName, 0, 1);
        grid.add(userTextField, 1, 1);
        grid.add(pw, 0, 2);
        grid.add(pwBox, 1, 2);
        grid.add(hbBtn, 1, 4);

        btn.setOnAction(e ->
        {
            String pass = pwBox.getText().toString();
            String nm = userTextField.getText().toString();

            if (whichPage == 0)
            {
                if (  !pass.equals("") && !nm.equals("") )
                {
                    Customer newCustomer = rs.registerNewCustomer(nm, pass);
                    customerMenu(newCustomer);
                }

            }

            else if (whichPage == 1)
            {
                if (! (pass.equals("") | nm.equals("") ) )
                {
                    rs.registerNewAdmin(nm, pass);
                    adminMenu();
                }

            }
        });

        window.setScene(scene);
    }

    void adminMenu() {
        VBox adminBox= new VBox(10);
        Button listCar = new Button("List Cars");
        Button addCar = new Button("Add Car");
        Button logout = new Button("Logout");

        addCar.setOnAction(e -> carAdd());
        listCar.setOnAction(e-> carList(0));
        logout.setOnAction(e -> window.setScene(adminMain));

        adminBox.getChildren().addAll(listCar, addCar, logout);
        adminBox.setAlignment(Pos.BASELINE_CENTER);
        adminMenuScene = new Scene(adminBox, 640, 480);

        window.setScene(adminMenuScene);

    }

    void customerMenu(Customer current) {
        VBox customBox = new VBox(10);
        Button listCar = new Button("List Cars");
        Button rent = new Button("Rent Car");
        Button returnCar = new Button("Return Car");
        Button logout = new Button("Logout");

        listCar.setOnAction(e -> carList(1));
        rent.setOnAction(e -> rentCar(current));
        returnCar.setOnAction(e -> {
            boolean check = rs.returnCar(current);

            if (check)
                ConfirmBox.alertBox("SUCCESS", "Thank you for returning the car.");
            else
                ConfirmBox.alertBox("FAIL", "No car was rented by this customer.");
        });
        logout.setOnAction(e -> window.setScene(customerMain));

        customBox.getChildren().addAll(listCar, rent, returnCar, logout);
        customBox.setAlignment(Pos.BASELINE_CENTER);
        customerMenuScene = new Scene(customBox, 640, 480);
        window.setScene(customerMenuScene);

    }

    void rentCar(Customer currentCustomer) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 640, 320);
        Text scenetitle = new Text("Rent Car");

        Label carMake = new Label("Car Make:");
        TextField makeTF = new TextField();
        Label carModel = new Label("Car Model:");
        TextField modelTF = new TextField();

        HBox makeRow = new HBox();
        makeRow.getChildren().addAll(carMake, makeTF);
        HBox modelRow = new HBox();
        modelRow.getChildren().addAll(carModel, modelTF);

        Label startDate = new Label("Start Date:");
        TextField stDate = new TextField();
        Label dueDate = new Label("Due Date: ");
        TextField duDate = new TextField();

        HBox dates1 = new HBox();
        dates1.getChildren().addAll(startDate, stDate);
        HBox dates2 = new HBox();
        dates2.getChildren().addAll(dueDate, duDate);


        Button rentBtn = new Button("Rent");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(rentBtn);

        grid.add(scenetitle, 0, 0, 2, 1);
        grid.add(makeRow, 0, 1, 2, 1);
        grid.add(modelRow, 0, 2, 2, 1);
        grid.add(dates1, 0, 3, 2, 1);
        grid.add(dates2, 0, 4, 2, 1);
        grid.add(hbBtn, 1, 5);

        rentBtn.setOnAction(e -> {
            String mk = makeTF.getText().toString();
            String mdl = modelTF.getText().toString();
            String date1 = stDate.getText().toString();
            String date2 = duDate.getText().toString();

            if (!date1.equals("") && !date2.equals(""))
            {
                boolean checkRent = rs.rentCarToCustomer(currentCustomer, mk, mdl, date1, date2);

                if (checkRent)
                    ConfirmBox.alertBox("SUCCESS", "Car Rented Successfully!");
                else
                    ConfirmBox.alertBox("FAILED", "Car not available!");
                customerMenu(currentCustomer);
            }
        } );

        window.setScene(scene);
    }

    void carList(int whichPage) {
            ObservableList<Car> carData = FXCollections.observableArrayList(cars);
            TableView<Car> table = new TableView<Car>();
            Scene scene = new Scene(new Group(),480, 640);
            window.setTitle("List Of Cars");

            TableColumn<Car, String> makeColumn = new TableColumn<Car, String>("Make");
            makeColumn.setMinWidth(100);
            makeColumn.setCellValueFactory(
                    new PropertyValueFactory<Car, String>("Make"));

            TableColumn<Car, String> modelColumn = new TableColumn<>("Model");
            modelColumn.setMinWidth(100);
            modelColumn.setCellValueFactory(
                    new PropertyValueFactory<Car, String>("Model"));

            TableColumn<Car, String> availColumn = new TableColumn<Car, String>("Available");
            availColumn.setMinWidth(200);
            availColumn.setCellValueFactory(
                    new PropertyValueFactory<Car, String>("Available"));

            Button mainmenu = new Button("Back To Menu");
            HBox buttonBox = new HBox();
            buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
            buttonBox.getChildren().add(mainmenu);

            mainmenu.setOnAction(e ->
            {
                if (whichPage == 0)
                    window.setScene(adminMenuScene);
                else if (whichPage == 1)
                    window.setScene(customerMenuScene);
            });

            table.setItems(carData);
            table.getColumns().addAll(makeColumn, modelColumn, availColumn);

            VBox tableBox = new VBox();
            tableBox.setSpacing(5);
            tableBox.setPadding(new Insets(10, 0, 0, 10));
            tableBox.getChildren().addAll(table, buttonBox);

            ((Group) scene.getRoot()).getChildren().addAll(tableBox);

            window.setScene(scene);
    }
    void carAdd() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        Scene scene = new Scene(grid, 300, 275);
        Text scenetitle = new Text("Register New Car");
        Label carMake = new Label("Car Make:");
        TextField makeTF = new TextField();
        Label carModel = new Label("Car Model:");
        TextField modelTF = new TextField();

        Button btn = new Button("Register");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);

        grid.add(scenetitle, 0, 0, 2, 1);
        grid.add(carMake, 0, 1);
        grid.add(makeTF, 1, 1);
        grid.add(carModel, 0, 2);
        grid.add(modelTF, 1, 2);
        grid.add(hbBtn, 1, 4);

        btn.setOnAction(e ->
        {
            String make = makeTF.getText().toString();
            String model = modelTF.getText().toString();
            rs.registerNewCar(make, model, true);
            adminMenu();
        });

        window.setScene(scene);
    }

}

