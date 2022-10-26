package com.example.easyqueue.ShedController;

import com.google.gson.annotations.SerializedName;

public class Shed {
    private String Id, Name, Address, Status, PetrolStatus, PetrolQueueStartTime, PetrolQueueEndTime;
    private String DieselStatus, DieselQueueStartTime, DieselQueueEndTime;
    private int DieselVehicleCount,PetrolVehicleCount;
    private double PetrolLiter,DieselLiter;

    public Shed() {
    }

    public Shed(String id, String name, String address, String status, String petrolStatus, double petrolLiter, String petrolQueueStartTime, String petrolQueueEndTime, int petrolVehicleCount, String dieselStatus,double dieselLiter, String dieselQueueStartTime, String dieselQueueEndTime, int dieselVehicleCount) {
        Id = id;
        Name = name;
        Address = address;
        Status = status;
        PetrolStatus = petrolStatus;
        PetrolLiter = petrolLiter;
        PetrolQueueStartTime = petrolQueueStartTime;
        PetrolQueueEndTime = petrolQueueEndTime;
        DieselStatus = dieselStatus;
        DieselLiter = dieselLiter;
        DieselQueueStartTime = dieselQueueStartTime;
        DieselQueueEndTime = dieselQueueEndTime;
        DieselVehicleCount = dieselVehicleCount;
        PetrolVehicleCount = petrolVehicleCount;
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

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPetrolStatus() {
        return PetrolStatus;
    }

    public void setPetrolStatus(String petrolStatus) {
        PetrolStatus = petrolStatus;
    }

    public double getPetrolLiter() {
        return PetrolLiter;
    }

    public void setPetrolLiter(float petrolLiter) {
        PetrolLiter = petrolLiter;
    }

    public String getPetrolQueueStartTime() {
        return PetrolQueueStartTime;
    }

    public void setPetrolQueueStartTime(String petrolQueueStartTime) {
        PetrolQueueStartTime = petrolQueueStartTime;
    }

    public String getPetrolQueueEndTime() {
        return PetrolQueueEndTime;
    }

    public void setPetrolQueueEndTime(String petrolQueueEndTime) {
        PetrolQueueEndTime = petrolQueueEndTime;
    }

    public String getDieselStatus() {
        return DieselStatus;
    }

    public void setDieselStatus(String dieselStatus) {
        DieselStatus = dieselStatus;
    }

    public double getDieselLiter() {
        return DieselLiter;
    }

    public void setDieselLiter(float dieselLiter) {
        DieselLiter = dieselLiter;
    }

    public String getDieselQueueStartTime() {
        return DieselQueueStartTime;
    }

    public void setDieselQueueStartTime(String dieselQueueStartTime) {
        DieselQueueStartTime = dieselQueueStartTime;
    }

    public String getDieselQueueEndTime() {
        return DieselQueueEndTime;
    }

    public void setDieselQueueEndTime(String dieselQueueEndTime) {
        DieselQueueEndTime = dieselQueueEndTime;
    }

    public int getDieselVehicleCount() {
        return DieselVehicleCount;
    }

    public void setDieselVehicleCount(int dieselVehicleCount) {
        DieselVehicleCount = dieselVehicleCount;
    }

    public int getPetrolVehicleCount() {
        return PetrolVehicleCount;
    }

    public void setPetrolVehicleCount(int petrolVehicleCount) {
        PetrolVehicleCount = petrolVehicleCount;
    }
}

