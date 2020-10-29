import com.google.gson.Gson;
import com.userSegmentation.controller.UserSegmentationService;
import com.userSegmentation.dao.UserRulesEvaluationData;
import com.userSegmentation.pojo.UserPreferenceAttributes;
import com.userSegmentation.service.UserGroupAssignmentService;
import com.userSegmentation.service.UserRulesEvaluationDataService;
import com.userSegmentation.utils.IResourceLoader;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;


@Test
@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = Application.class)
public class UserSegmentationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    @Qualifier("File")
    private IResourceLoader resourceLoader;

    Gson gson = new Gson();

    @Autowired
    UserSegmentationService segmentationService;

    @Autowired
    UserRulesEvaluationDataService userRulesEvaluationDataService;

    @Autowired
    UserGroupAssignmentService userGroupAssignmentService;

    @Test
    public void testUserSegmentationService(){
        String testFileName = "UserPreferences.json";
        String userPreferncesTestFile  = resourceLoader.getResourceData(testFileName);
        UserPreferenceAttributes userPreferenceAttributes = gson.fromJson(userPreferncesTestFile, UserPreferenceAttributes.class);
        List<String> actualQualifyingGroups = segmentationService.getUserSegmentation(userPreferenceAttributes);
        Assert.assertThat(actualQualifyingGroups, CoreMatchers.hasItems("GROUP_A", "GROUP_B"));
        // checking database results
        String groupResultsFromDatabase = userGroupAssignmentService.
                fetchUserGroupAssignmentByUserId(userPreferenceAttributes).getGroupNames();
        List<UserRulesEvaluationData> rulesEvaluationData = userRulesEvaluationDataService.fetchAllRules();
        Assert.assertTrue(groupResultsFromDatabase.contains("GROUP_A") &&
                groupResultsFromDatabase.contains("GROUP_B"));
        Assert.assertTrue(rulesEvaluationData.size() == 2);

    }

}
