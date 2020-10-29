package com.userSegmentation.dao;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "User_Rules_Evaluation_Data")
public class UserRulesEvaluationData {
    @Id @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "attempt_id")
    private String attemptId; //since we can evaluate results for a user multiple times
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "user_preference_attributes")
    private String userPreferenceAttributes;
    @Column(name = "rules_evlauation_result")
    private String rulesEvaluationResult;
    @Column(name = "created_at")
    private Timestamp createdAt;

}
