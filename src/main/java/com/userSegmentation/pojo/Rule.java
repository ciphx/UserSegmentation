package com.userSegmentation.pojo;

import com.userSegmentation.config.DecisionConfigTranslator;
import com.userSegmentation.config.Operator;
import com.userSegmentation.utils.StringUtils;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;

@Data
public class Rule {

    static Logger log = LogManager.getLogger(Rule.class);

    private String attribute;
    private Operator operator;
    private String  value;

    /**
     * this function uses reflection to get the value of the user attribute corresponding to the attribute mentioned in the rule
     * and then returns the  result of comparison of the two values
     * @param userPreferenceAttributes
     * @return
     * @throws Exception
     */
    public Boolean evaluate(UserPreferenceAttributes userPreferenceAttributes) throws Exception{
        String getterName = DecisionConfigTranslator.getGetterForAttributes().get(attribute);
        Method userAttributeMethod = userPreferenceAttributes.getClass().getDeclaredMethod(getterName);
        String userAttributeValue = String.valueOf(userAttributeMethod.invoke(userPreferenceAttributes, null));
        if(org.springframework.util.StringUtils.isEmpty(userAttributeValue))
            return false;
        Boolean evaluationResult = evaluateExpression(userAttributeValue);
        return evaluationResult;
    }

    /**
     * This function compares the value of the user attribute with the value required in the rule according to the operator specified.
     * The values could be string/double/long, hence we call functions mentioned in EvaluateStringDataType class to evaluate according to the
     * data type of the value within the string represented by "value"
     * @param userAttributeValue
     * @return
     */
    private Boolean evaluateExpression(String userAttributeValue) {
        switch(operator){
            case EQ: return StringUtils.equals(this.value, userAttributeValue);

            case NEQ: return StringUtils.notEquals(this.value, userAttributeValue);

            case LE: return StringUtils.evaluateLE(this.value, userAttributeValue);

            case GE: return StringUtils.evaluateGE(this.value, userAttributeValue);

            case GT: return StringUtils.evaluateGreaterThan(this.value, userAttributeValue);

            case LT: return StringUtils.evaluateLessThan(this.value, userAttributeValue);

        }
        return false;
    }
}
