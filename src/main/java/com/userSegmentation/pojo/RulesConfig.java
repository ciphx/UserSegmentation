package com.userSegmentation.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RulesConfig {

    Map<String,Rule> rules;
    @SerializedName("rule_path")
    String rulePath;
}
