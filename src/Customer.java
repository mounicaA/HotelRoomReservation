/**
 * Created by Mounica on 12/10/14.
 */
public class Customer {
    private String firstName, lastName, emailID, streetAddress, city, state;
    private int customerID;
    private String phoneNumber, zip;

    public Customer(String firstName, String lastName, String emailID, String streetAddress, String city,
                    String state, String zip, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailID = emailID;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getZip() {
        return zip;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailID() {
        return emailID;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }
}
