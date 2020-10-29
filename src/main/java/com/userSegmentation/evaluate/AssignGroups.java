package com.userSegmentation.evaluate;

import com.userSegmentation.config.DecisionConfigTranslator;
import com.userSegmentation.config.UserGroup;
import com.userSegmentation.dao.UserGroupAssignment;
import com.userSegmentation.dao.UserRulesEvaluationData;
import com.userSegmentation.exception.RecordNotFoundException;
import com.userSegmentation.pojo.Rule;
import com.userSegmentation.pojo.RulesConfig;
import com.userSegmentation.pojo.UserPreferenceAttributes;
import com.userSegmentation.service.UserGroupAssignmentService;
import com.userSegmentation.service.UserRulesEvaluationDataService;
import com.userSegmentation.utils.JaninoExpressionEvaluator;
import com.userSegmentation.utils.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.commons.compiler.CompileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AssignGroups {

    static Logger log = LogManager.getLogger(AssignGroups.class);

    @Autowired
    UserGroupAssignmentService userGroupAssignmentService;

    @Autowired
    UserRulesEvaluationDataService userRulesEvaluationDataService;

    /**
     * This function evaluates the rules given in each grouping file for a user and returns all the groups the user can be a part of
     * @param userPreferenceAttributes
     * @return
     * @throws InvocationTargetException
     * @throws CompileException
     */
    public List<String> evaluateRules(UserPreferenceAttributes userPreferenceAttributes) throws InvocationTargetException, CompileException, RecordNotFoundException {
        List<String> qualifyingGroups = new ArrayList<>();
        Map<UserGroup, RulesConfig> groupRulesConfigMap = DecisionConfigTranslator.getSegmentationRulesConfig();
        for(UserGroup group : groupRulesConfigMap.keySet()){
            RulesConfig rulesConfig = groupRulesConfigMap.get(group);
            log.info("Evaluating result for group {} ", group);
            Boolean resultForGroup = evaluateResultForGroup(group, userPreferenceAttributes, rulesConfig);
            log.info("Result for the group is {}", resultForGroup);
            if(resultForGroup == true)
                qualifyingGroups.add(group.name());
        }
        persistGroupsPerUser(userPreferenceAttributes, qualifyingGroups);
        return  qualifyingGroups;
    }

    /**
     * This function upserts user and the groups the user is a part of in the database
     * @param userPreferenceAttributes
     * @param qualifyingGroups
     */
    private void persistGroupsPerUser(UserPreferenceAttributes userPreferenceAttributes, List<String> qualifyingGroups) throws RecordNotFoundException {
        UserGroupAssignment userGroupAssignment = userGroupAssignmentService.fetchUserGroupAssignmentByUserId(userPreferenceAttributes);

        if( userGroupAssignment == null){
            userGroupAssignment = new UserGroupAssignment();
            userGroupAssignment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            userGroupAssignment.setUserId(Long.valueOf(userPreferenceAttributes.getUserId()));
        }
        userGroupAssignment.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        userGroupAssignment.setGroupNames(qualifyingGroups.toString());
        userGroupAssignmentService.upsert(userGroupAssignment);
    }

    /**
     * This function returns the result for a single group
     * @param group
     * @param userPreferenceAttributes
     * @param rulesConfig
     * @return
     * @throws InvocationTargetException
     * @throws CompileException
     */
    private Boolean evaluateResultForGroup(UserGroup group, UserPreferenceAttributes userPreferenceAttributes,
                                           RulesConfig rulesConfig) throws InvocationTargetException, CompileException {
        Map<String, Boolean> resultPerRule = evaluateIndividualRules(userPreferenceAttributes,rulesConfig);
        String rulePath = rulesConfig.getRulePath();
        Boolean ruleResult = JaninoExpressionEvaluator.evaluateBooleanExpression(rulePath,resultPerRule);
        persistIndividualGroupResultForUser(group,userPreferenceAttributes, resultPerRule);
        return ruleResult;
    }

    /**
     * This function saves the result for a single group for a user. We will save every evaluation for a user,
     * for audit purpose. Thus the key in this table is the "attempt_id"
     * @param group
     * @param userPreferenceAttributes
     * @param resultPerRule
     */
    private void persistIndividualGroupResultForUser(UserGroup group, UserPreferenceAttributes userPreferenceAttributes,
                                           Map<String, Boolean> resultPerRule) {
        UserRulesEvaluationData userRulesEvaluationData = new UserRulesEvaluationData();
        userRulesEvaluationData.setAttemptId(userPreferenceAttributes.getAttemptId());
        userRulesEvaluationData.setGroupName(group.name());
        userRulesEvaluationData.setUserId(Long.valueOf(userPreferenceAttributes.getUserId()));
        userRulesEvaluationData.setRulesEvaluationResult(StringUtils.formatMap(resultPerRule));
        userRulesEvaluationData.setUserPreferenceAttributes(userPreferenceAttributes.toString());
        userRulesEvaluationData.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        userRulesEvaluationDataService.insert(userRulesEvaluationData);
    }

    /**
     * this function is used to evaluate individual rules given in a group
     * @param userPreferenceAttributes
     * @param rulesConfig
     * @return
     */
    private Map<String, Boolean> evaluateIndividualRules(UserPreferenceAttributes userPreferenceAttributes, RulesConfig rulesConfig){
        Map<String, Boolean> resultPerRule = new HashMap<>();
        rulesConfig.getRules().keySet().forEach( ruleIdentifier -> {
            Rule rule = rulesConfig.getRules().get(ruleIdentifier);
            try {
                Boolean result = rule.evaluate(userPreferenceAttributes);
                log.info("Individual result for the rule {} is {}",rule.getAttribute(), result);
                resultPerRule.put(ruleIdentifier,result);
            } catch (Exception e) {
                log.error("Unable to fetch user attribute value, {}",e.getMessage());
                e.printStackTrace();
            }
        });
        return resultPerRule;
    }
}
