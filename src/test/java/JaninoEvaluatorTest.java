import com.google.gson.Gson;
import com.userSegmentation.utils.FileResourceLoader;
import com.userSegmentation.utils.JaninoExpressionEvaluator;
import org.codehaus.commons.compiler.CompileException;
import org.junit.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class JaninoEvaluatorTest {

    @Test
    public void testEmbeddedExpression() {
        String testFileName = "JaninoTest.json";
        String janinoTestString  = new FileResourceLoader().getResourceData(testFileName);
        List<Map> testData = new Gson().fromJson(janinoTestString, List.class);
        testData.stream().forEach(testSample -> {
            try {
                String rulePath = String.valueOf(testSample.get("rule_path"));
                Map<String,Boolean> resultPerRule = (Map<String, Boolean>) testSample.get("map");
                Boolean expectedResult = Boolean.valueOf(String.valueOf(testSample.get("expectedResult")));
                Boolean actualResult = JaninoExpressionEvaluator.evaluateBooleanExpression(rulePath,resultPerRule);
                Assert.assertEquals(expectedResult,actualResult);
            } catch (CompileException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }
}
