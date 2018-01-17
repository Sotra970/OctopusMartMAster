
package com.example.ahmed.octopusmart.Model.ServiceModels.Tracking;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status implements Serializable
{

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    private final static long serialVersionUID = 7108003723654909891L;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
