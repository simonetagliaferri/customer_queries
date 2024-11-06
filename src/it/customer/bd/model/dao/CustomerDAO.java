package it.customer.bd.dao;
import it.customer.bd.model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    // BEGIN CODE SMELL: Hardcoded credentials
    private static final String USER = "username";
    private static final String PASS = "password";
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/sakila";
    // END CODE SMELL

    public static List<Customer> getCustomersWithAboveAverageRentals() throws Exception {
        List<Customer> customers = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;

        try {
            // BEGIN CODE SMELL
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            // END CODE SMELL

            String query = """
                SELECT first_name, last_name, COUNT(rental_id) AS num_of_rentals
                FROM customer
                INNER JOIN rental ON customer.customer_id = rental.customer_id
                GROUP BY customer.customer_id, first_name, last_name
                HAVING num_of_rentals > 
                    (SELECT AVG(rental_count)
                     FROM (SELECT COUNT(rental_id) AS rental_count
                           FROM rental
                           GROUP BY customer_id) AS avg_rentals)
                ORDER BY num_of_rentals;
            """;

            // BEGIN CODE SMELL
            ResultSet rs = stmt.executeQuery(query);
            // END CODE SMELL

            while (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int rentalCount = rs.getInt("num_of_rentals");
                customers.add(new Customer(0, firstName, lastName, rentalCount));  // customerId not needed here
            }

            rs.close();
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }

        return customers;
    }

    public static List<String> getCustomersWhoRentedInAllCategories() throws Exception {
        List<String> customers = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;

        try {
            // BEGIN CODE SMELL: New connection just after I closed one
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            // END CODE SMELL

            String query = """
                SELECT name
                FROM customer_list
                INNER JOIN rental ON customer_list.ID = rental.customer_id
                INNER JOIN inventory ON rental.inventory_id = inventory.inventory_id
                INNER JOIN film_category ON inventory.film_id = film_category.film_id
                GROUP BY customer_list.ID, name
                HAVING COUNT(DISTINCT category_id) = 16;
            """;

            // BEGIN CODE SMELL
            ResultSet rs = stmt.executeQuery(query);
            // END CODE SMELL

            while (rs.next()) {
                String name = rs.getString("name");
                customers.add(name);
            }

            rs.close();
        } finally {
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        }

        return customers;
    }
}

