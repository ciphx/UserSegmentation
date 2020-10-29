package com.userSegmentation.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPreferenceAttributes {
    @SerializedName("user_id")
    @JsonProperty("user_id")
    private String userId;
    @SerializedName("attempt_id")
    @JsonProperty("attempt_id")
    private String attemptId;
    private String gender;
    @SerializedName("preference.food")
    @JsonProperty("preference.food")
    private String foodPreference;
    @SerializedName("order_count")
    @JsonProperty("order_count")
    private String orderCount;
    private String location;
    private String quantity;

    @Override
    public String toString() {
        return "UserPreferenceAttributes{" +
                "userId='" + userId + '\'' +
                ", gender='" + gender + '\'' +
                ", foodPreference='" + foodPreference + '\'' +
                ", orderCount='" + orderCount + '\'' +
                ", location='" + location + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}
