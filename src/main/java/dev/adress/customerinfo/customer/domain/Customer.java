package dev.adress.customerinfo.customer.domain;

public class Customer {
    private final String firstName;
    private final String secondName;
    private final String lastName;
    private final String secondLastName;
    private final String phone;
    private final String address;
    private final String city;

    public Customer(String firstName, String secondName, String lastName, String secondLastName,
                    String phone, String address, String city) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.phone = phone;
        this.address = address;
        this.city = city;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }
}
