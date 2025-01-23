package movies;

public class Main {
    public static void main(String[] args) {
        Customer customer = new Customer("Test");
        customer.addRental(new Rental(new Movie("Zack Snyder's Justice League", 1), 5));
        customer.addRental(new Rental(new Movie("Terminator", 0), 1));
        customer.addRental(new Rental(new Movie("Soul", 2), 3));
        System.out.println(customer.statement());
    }
}
