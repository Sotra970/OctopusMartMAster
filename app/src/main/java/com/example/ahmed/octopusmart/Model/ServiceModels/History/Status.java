
package com.example.ahmed.octopusmart.Model.ServiceModels.History;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status implements Serializable
{

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    private final static long serialVersionUID = 7108003723654909891L;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

}
