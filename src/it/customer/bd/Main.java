package it.customer.bd;

import it.customer.bd.model.Customer;
import it.customer.bd.dao.CustomerDAO;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Clienti che hanno noleggiato più della media
            List<Customer> customersAboveAverage = CustomerDAO.getCustomersWithAboveAverageRentals();
            System.out.println("Clienti con noleggi sopra la media:");
            for (Customer customer : customersAboveAverage) {
                System.out.println(customer);
            }

            // Clienti che hanno noleggiato in tutte le categorie
            List<String> customersInAllCategories = CustomerDAO.getCustomersWhoRentedInAllCategories();
            System.out.println("\nClienti che hanno noleggiato in tutte le categorie:");
            for (String customerName : customersInAllCategories) {
                System.out.println(customerName);
            }
        } catch (Exception e) {
            // BEGIN CODE SMELL: Generic exception handling, not ideal
            e.printStackTrace();
            // END CODE SMELL
        }
    }
}
