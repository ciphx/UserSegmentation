package com.userSegmentation.service;

import com.userSegmentation.dao.UserGroupAssignment;
import com.userSegmentation.pojo.UserPreferenceAttributes;
import com.userSegmentation.repository.UserGroupAssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserGroupAssignmentService {

    @Autowired
    UserGroupAssignmentRepository userGroupAssignmentRepository;

    @Transactional
    public void upsert(UserGroupAssignment userGroupAssignment) {
        this.userGroupAssignmentRepository.save(userGroupAssignment);
    }

    public UserGroupAssignment fetchUserGroupAssignmentByUserId(UserPreferenceAttributes userPreferenceAttributes) {
        Optional<UserGroupAssignment> userGroupAssignment =  this.userGroupAssignmentRepository.
                findById(Long.valueOf(userPreferenceAttributes.getUserId()));
        if(userGroupAssignment.isPresent()) {
            return userGroupAssignment.get();
        } else {
            return null;
        }
    }
}
