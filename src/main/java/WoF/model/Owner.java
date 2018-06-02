package WoF.model;

import WoF.model.vehicle.Vehicle;
import java.util.HashSet;
import java.util.Set;

/**
 * Owner class
 * @author Adam Ross
 */
public class Owner {

    private final String email;
    private String password;
    private final Set<Vehicle> registeredVehicles;
    private String forename;
    private String surname;
    private String addressLineOne;
    private String addressLineTwo;
    private String phone;

    /**
     * Owner constructor
     * @param forename the owner's forename
     * @param surname  the owner's surname
     * @param email    the owner's email
     * @param password the owner's password
     */
    public Owner(String forename, String surname, String email, String password) {
        this.forename = forename.toUpperCase();
        this.surname = surname.toUpperCase();
        this.email = email;
        this.password = password;
        this.registeredVehicles = new HashSet<>();
    }

    /**
     * Gets an owner's forename
     * @return the owner's forename
     */
    public String getForename() {
        return this.forename;
    }

    /**
     * Sets an owner's forename
     * @param forename the owner's forename
     */
    public void setForename(String forename) {
        this.forename = forename.toUpperCase();
    }

    /**
     * Gets an owner's surname
     * @return the owner's surname
     */
    public String getSurname() {
        return this.surname;
    }

    /**
     * Sets an owner's surname
     * @param surname the owner's surname
     */
    public void setSurname(String surname) {
        this.surname = surname.toUpperCase();
    }

    /**
     * Gets an owner's email
     * @return the owner's email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Gets an owner's password
     * @return the owner's password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets an owner's password
     * @param password the owner's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets an owner's registered vehicles
     * @return the owner's registered vehicles
     */
    public HashSet<Vehicle> getVehicles() {
        return (HashSet<Vehicle>) this.registeredVehicles;
    }

    /**
     * Sets an owner's address line one
     * @param address the owner's address line one
     */
    public void setAddressLineOne(String address) {
        this.addressLineOne = address.toUpperCase();
    }

    /**
     * Gets an owner's address line one
     * @return the owner's address line one
     */
    public String getAddressLineOne() {
        return this.addressLineOne;
    }

    /**
     * Sets an owner's address line two
     * @param address the owner's address line two
     */
    public void setAddressLineTwo(String address) {
        this.addressLineTwo = address.toUpperCase();
    }

    /**
     * Gets an owner's address line two
     * @return the owner's address line two
     */
    public String getAddressLineTwo() {
        return this.addressLineTwo;
    }

    /**
     * Sets an owner's phone number
     * @param phone the owner's phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets an owner's phone number
     * @return the owner's phone number
     */
    public String getPhone() {
        return this.phone;
    }
}
