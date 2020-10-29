package com.userSegmentation.utils;

import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.ExpressionEvaluator;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JaninoExpressionEvaluator {

    public static Boolean evaluateBooleanExpression(String rulePath, Map<String,Boolean> resultPerRule) throws CompileException, InvocationTargetException {
        // Now here's where the story begins...
        ExpressionEvaluator ee = new ExpressionEvaluator();
        List<String> paramsInRulePath = new ArrayList<>();
        List<Object> classForParams = new ArrayList<>();
        List<Boolean> resultForRules = new ArrayList<Boolean>(resultPerRule.values());
        for(char variable : rulePath.toCharArray()){
            if(Character.isLetter(variable)){
                paramsInRulePath.add(String.valueOf(variable));
                classForParams.add(Boolean.class);
            }
        }
        // The expression will have parameters for variables in rule path , like  "a", "b".
        ee.setParameters(paramsInRulePath.toArray(new String[paramsInRulePath.size()]),
                classForParams.toArray(new Class[classForParams.size()]));

        // And the expression (i.e. "result") type is also "boolean".
        ee.setExpressionType(Boolean.class);

        // And now we "cook" (scan, parse, compile and load) the fabulous expression.
        ee.cook(rulePath);

        // Eventually we evaluate the expression.
        return (Boolean) ee.evaluate(resultForRules.toArray(new Object[resultForRules.size()]));
    }

}
