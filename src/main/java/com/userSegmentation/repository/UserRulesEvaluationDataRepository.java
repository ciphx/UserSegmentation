package com.userSegmentation.repository;

import com.userSegmentation.dao.UserRulesEvaluationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRulesEvaluationDataRepository  extends JpaRepository<UserRulesEvaluationData, String> {
}
