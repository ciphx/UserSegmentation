package com.userSegmentation.service;

import com.userSegmentation.dao.UserRulesEvaluationData;
import com.userSegmentation.repository.UserRulesEvaluationDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRulesEvaluationDataService {

    @Autowired
    UserRulesEvaluationDataRepository userRulesEvaluationDataRepository;

    @Transactional
    public void insert(UserRulesEvaluationData userRulesEvaluationData) {
        this.userRulesEvaluationDataRepository.save(userRulesEvaluationData);
    }

    public List<UserRulesEvaluationData> fetchAllRules(){
        List<UserRulesEvaluationData> userRulesEvaluationData =  this.userRulesEvaluationDataRepository.
                findAll();
        if(userRulesEvaluationData.size() != 0) {
            return userRulesEvaluationData;
        } else {
            return null;
        }
    }
}
