package com.userSegmentation.config;


import lombok.Getter;
import lombok.Setter;

public enum UserGroup {
   GROUP_A("GROUP_A.json"),
   GROUP_B("GROUP_B.json");

   @Getter
    @Setter
    private String resourceKey;

   private UserGroup(String resourceKey){
       this.resourceKey = resourceKey;
   }
}
