package com.userSegmentation.config;

import com.google.gson.Gson;
import com.userSegmentation.pojo.RulesConfig;
import com.userSegmentation.utils.IResourceLoader;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class DecisionConfigTranslator {
    @Autowired
    @Qualifier("File")
    private IResourceLoader resourceLoader;

    @Getter
    private static Map<UserGroup, RulesConfig> segmentationRulesConfig;

    @Getter
    private static Map<String, String> getterForAttributes;

    @PostConstruct
    private void loadDecisionConfig() {
        Gson gson = new Gson();
        segmentationRulesConfig = new HashMap<>();

        for (UserGroup group : UserGroup.values()) {
            String rulesResourceValue = resourceLoader.getResourceData(group.getResourceKey());
            RulesConfig rulesConfig = gson.fromJson(rulesResourceValue, RulesConfig.class);
            segmentationRulesConfig.put(UserGroup.valueOf(group.name()), rulesConfig);
        }
    }

    @PostConstruct
    private void populateGetterForAttributes(){
        getterForAttributes = new HashMap<>();
        for(GetterForAttribute attribute : GetterForAttribute.values()){
            getterForAttributes.put(attribute.name(), attribute.getGetter());
        }
    }

}
