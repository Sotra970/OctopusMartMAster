
package com.example.ahmed.octopusmart.Model.ServiceModels;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang.builder.ToStringBuilder;

public class UserRateModel implements Serializable
{

    @SerializedName("rate")
    @Expose
    private double rate;
    @SerializedName("user_count")
    @Expose
    private Long userCount;
    private final static long serialVersionUID = 2585601833525808412L;

    public double getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("rate", rate).append("userCount", userCount).toString();
    }

}
