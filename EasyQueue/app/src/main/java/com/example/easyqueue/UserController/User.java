package com.example.easyqueue.UserController;

public class User {
    private String Id, Name,Email, Address, MobileNo, Role,Password,FuelType,ShedId;

    public User(){

    }

    public User(String id, String name, String email, String address, String mobileNo, String role, String password, String fuelType, String shedId) {
        Id = id;
        Name = name;
        Email = email;
        Address = address;
        MobileNo = mobileNo;
        Role = role;
        Password = password;
        FuelType = fuelType;
        ShedId = shedId;
    }

    public User(String id, String name, String email, String address, String mobileNo, String role, String fuelType, String shedId) {
        Id = id;
        Name = name;
        Email = email;
        Address = address;
        MobileNo = mobileNo;
        Role = role;
        FuelType = fuelType;
        ShedId = shedId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFuelType() {
        return FuelType;
    }

    public void setFuelType(String fuelType) {
        FuelType = fuelType;
    }

    public String getShedId() {
        return ShedId;
    }

    public void setShedId(String shedId) {
        ShedId = shedId;
    }
}
