package com.example.rentalsystem;

import com.example.rentalsystem.*;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class RentalSystem {
    public Customer registerNewCustomer(String name, String password) {
        Customer customer = new Customer(name, password);
        Main.customers.add(customer);
        return customer;
    }
    public void registerNewAdmin(String name, String password) {
        Admin admin = new Admin(name, password);
        Main.admins.add(admin);
    }
    public void registerNewCar(String make, String model, boolean available) {
        Car car = new Car(make, model, available);
        Main.cars.add(car);
    }
    public boolean validateAdmin(ArrayList<Admin> array, String name, String password) {
        
        String compareName, comparePassword;

        boolean isValid = false;
        for (Admin admin : array) {

            compareName = admin.getName();
            comparePassword = admin.getPassword();

            if (compareName.equals(name) && comparePassword.equals(password)) {
                isValid = true;
                break;
            }
        }
        return isValid;
    }                                                                                 

    public Customer validateCustomer(ArrayList<Customer> array, String name, String password) {

        String compareName, comparePassword;

        for (Customer customer : array) {

            compareName = customer.getName();
            comparePassword = customer.getPassword();

            if (compareName.equals(name) && comparePassword.equals(password)) {
                return customer;
            }
        }

        return null;
    }

public boolean rentCarToCustomer(Customer customer, String make, String model, String date1, String date2) {

    Date startDate, dueDate;
    boolean isAvailable = false, succeed = false;
    Car rentedCar = null;

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    try {
        startDate = formatter.parse(date1);
        dueDate = formatter.parse(date2);
        for (Car car : Main.cars) {
            if (car.getMake().equals(make) && car.getModel().equals(model)) {
                isAvailable = car.isAvailable();
                rentedCar = car;
                break;
            }
        }

        if (isAvailable) {
            rentedCar.setAvailable(false);
            customer.setRented(rentedCar);
            customer.setRentStartDate(startDate);
            customer.setRentDueDate(dueDate);
            succeed = true;
        }

    } catch (ParseException e) {
        e.printStackTrace();
    }

    return succeed;
}

    public boolean returnCar(Customer customer) {

    boolean result = false;

    Car rentedCar = customer.getRented();
        if (rentedCar != null) {
            rentedCar.setAvailable(true);
            customer.setRented(null);
            customer.setRentStartDate(null);
            customer.setRentDueDate(null);
            result = true;
        } else {
            result = false;
        }

        return result;
    }

}
