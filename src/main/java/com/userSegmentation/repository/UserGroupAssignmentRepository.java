package com.userSegmentation.repository;

import com.userSegmentation.dao.UserGroupAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupAssignmentRepository extends JpaRepository<UserGroupAssignment, Long> {

}
