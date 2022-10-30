package com.example.easyqueue.ShedController;

import com.google.gson.annotations.SerializedName;

public class Shed {
    private String Id,OwnerID, Name, Address, Status, PetrolStatus, PetrolQueueStartTime, PetrolQueueEndTime,PetrolArrivedime,PetrolFinishedTime;
    private String DieselStatus, DieselQueueStartTime, DieselQueueEndTime,DieselArrivedime,DieselFinishedTime,LastUpdate;
    private int DieselVehicleCount,PetrolVehicleCount;
    private double PetrolLiter,DieselLiter;

    public Shed() {
    }

    public Shed(String id,String ownerID, String name, String address, String status, String petrolStatus, double petrolLiter, String petrolQueueStartTime, String petrolQueueEndTime, int petrolVehicleCount, String dieselStatus,double dieselLiter, String dieselQueueStartTime, String dieselQueueEndTime, int dieselVehicleCount,
                String petrolArrivedime,String petrolFinishedTime,String dieselArrivedime,String dieselFinishedTime,String lastUpdate) {
        Id = id;
        OwnerID = ownerID;
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
        PetrolArrivedime = petrolArrivedime;
        PetrolFinishedTime = petrolFinishedTime;
        DieselArrivedime = dieselArrivedime;
        DieselFinishedTime = dieselFinishedTime;
        LastUpdate = lastUpdate;


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

    public void setPetrolLiter(double petrolLiter) {
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

    public void setDieselLiter(double dieselLiter) {
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

    public String getOwnerID() {
        return OwnerID;
    }

    public void setOwnerID(String ownerID) {
        OwnerID = ownerID;
    }

    public String getPetrolArrivedime() {
        return PetrolArrivedime;
    }

    public void setPetrolArrivedime(String petrolArrivedime) {
        PetrolArrivedime = petrolArrivedime;
    }

    public String getPetrolFinishedTime() {
        return PetrolFinishedTime;
    }

    public void setPetrolFinishedTime(String petrolFinishedTime) {
        PetrolFinishedTime = petrolFinishedTime;
    }

    public String getDieselArrivedime() {
        return DieselArrivedime;
    }

    public void setDieselArrivedime(String dieselArrivedime) {
        DieselArrivedime = dieselArrivedime;
    }

    public String getDieselFinishedTime() {
        return DieselFinishedTime;
    }

    public void setDieselFinishedTime(String dieselFinishedTime) {
        DieselFinishedTime = dieselFinishedTime;
    }

    public String getLastUpdate() {
        return LastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        LastUpdate = lastUpdate;
    }
}

