package com.userSegmentation.dao;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "User_Group_Assignment")
public class UserGroupAssignment {

    @Id
    @Column(name = "user_id")
    private Long userId; //since we can evaluate results for a user multiple times
    @Column(name = "group_names")
    private String groupNames;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
