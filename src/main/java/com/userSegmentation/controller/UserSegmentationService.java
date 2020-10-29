package com.userSegmentation.controller;

import com.userSegmentation.evaluate.AssignGroups;
import com.userSegmentation.exception.RecordNotFoundException;
import com.userSegmentation.pojo.UserPreferenceAttributes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.commons.compiler.CompileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserSegmentationService {

    static Logger log = LogManager.getLogger(UserSegmentationService.class);
    @Autowired
    AssignGroups assignGroups;

    @RequestMapping(value = "/v1/matchUserToGroups", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getUserSegmentation(@RequestBody UserPreferenceAttributes userPreferenceAttributes){
        List<String> userGroups = new ArrayList<>();
        try {
            userGroups = assignGroups.evaluateRules(userPreferenceAttributes);
        } catch (InvocationTargetException e) {
            log.error("Error while parsing boolean expression, {}", e.getMessage());
            e.printStackTrace();
        } catch (CompileException e) {
            log.error("Error while parsing boolean expression, {}", e.getMessage());
            e.printStackTrace();
        } catch (RecordNotFoundException e) {
            log.error("Error while fetching user-group data, {}", e.getMessage());
            e.printStackTrace();
        }
        return userGroups;
    }

}
