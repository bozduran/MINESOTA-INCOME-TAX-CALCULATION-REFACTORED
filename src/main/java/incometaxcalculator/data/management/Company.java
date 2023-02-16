package incometaxcalculator.data.management;

public class Company {

  private final String name;
  private final Address address;

  public Company(String name, String country, String city, String street, int number) {
    this.name = name;
    this.address = new Address(country, city, street, number);
  }

  public String getName() {
    return name;
  }

  public Address getAddress() {
    return address;
  }

}