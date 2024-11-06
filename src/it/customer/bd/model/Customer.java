package it.customer.bd.model;

public class Customer {
    private final int customerId;
    private String firstName;
    private String lastName;
    private int rentalCount;

    public Customer(int customerId, String firstName, String lastName, int rentalCount) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rentalCount = rentalCount;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getRentalCount() {
        return rentalCount;
    }

    public void setRentalCount(int rentalCount) {
        this.rentalCount = rentalCount;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (Noleggi: " + rentalCount + ")";
    }
}
