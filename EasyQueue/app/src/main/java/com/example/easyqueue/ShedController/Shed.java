package com.example.easyqueue.ShedController;

import com.google.gson.annotations.SerializedName;

public class Shed {
    @SerializedName("_id")
    private int Id;
    @SerializedName("Name")
    private int Image;
    @SerializedName("Name")
    private String Name;
    @SerializedName("Address")
    private String Address;
    @SerializedName("Status")
    private String Status;
    @SerializedName("QueueStartTime")
    private String QueueStartTime;
    @SerializedName("QueueEndTime")
    private String QueueEndTime;

    public Shed() {
    }

    public Shed(int image,int id, String name, String address, String status, String queueStartTime, String queueEndTime) {
        Image=image;
        Id = id;
        Name = name;
        Address = address;
        Status = status;
        QueueStartTime = queueStartTime;
        QueueEndTime = queueEndTime;
    }

    public Shed(String name, String address, String status, String queueStartTime, String queueEndTime) {
        Name = name;
        Address = address;
        Status = status;
        QueueStartTime = queueStartTime;
        QueueEndTime = queueEndTime;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
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

    public String getQueueStartTime() {
        return QueueStartTime;
    }

    public void setQueueStartTime(String queueStartTime) {
        QueueStartTime = queueStartTime;
    }

    public String getQueueEndTime() {
        return QueueEndTime;
    }

    public void setQueueEndTime(String queueEndTime) {
        QueueEndTime = queueEndTime;
    }
}
