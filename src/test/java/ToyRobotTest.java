import com.robot.ToyRobotException;
import com.robot.model.ToyRobot;
import com.robot.service.ToyRobotService;
import junit.framework.TestCase;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static com.robot.constants.ToyRobotConstants.NORTH;
import static com.robot.constants.ToyRobotConstants.WEST;

public class ToyRobotTest extends TestCase {


    ToyRobotService toyRobotService;
    protected int value1, value2;
    private static final String DATA_STRING = "testData";
    JSONObject testData;


    protected void setUp() throws IOException, ParseException {
        toyRobotService = new ToyRobotService();
        String resourceName = "robotInputCommands.json";

        //get testData from the json file
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(resourceName).getFile());
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(file));
        testData = (JSONObject) obj;

    }

    public void testScenario1() throws ToyRobotException {
        if (isNotEmpty(testData, 1)) {
            ToyRobot robot = new ToyRobot();
            robot.setCommands(testData.get(DATA_STRING + 1).toString());
            toyRobotService.start(robot);
            Assert.assertEquals(robot.getCoordX(), 0);
            Assert.assertEquals(robot.getCoordY(), 1);
            Assert.assertEquals(robot.getRobotDir(), NORTH);
            Assert.assertTrue(robot.getIsActive());
        }

    }

    public void testScenario2() {
        if (isNotEmpty(testData, 2)) {
            ToyRobot robot = new ToyRobot();
            robot.setCommands(testData.get(DATA_STRING + 2).toString());
            try {
                toyRobotService.start(robot);
                Assert.assertEquals(robot.getCoordX(), 0);
                Assert.assertEquals(robot.getCoordY(), 0);
                Assert.assertEquals(robot.getRobotDir(), WEST);
                Assert.assertTrue(robot.getIsActive());
            } catch (ToyRobotException e) {
                e.printStackTrace();
            }
        }

    }


    public void testScenario3() throws ToyRobotException {
        if (isNotEmpty(testData, 3)) {
            ToyRobot robot = new ToyRobot();
            robot.setName("testData3");
            robot.setCommands(testData.get(DATA_STRING + 3).toString());
            toyRobotService.start(robot);
            Assert.assertEquals(robot.getCoordX(), 3);
            Assert.assertEquals(robot.getCoordY(), 3);
            Assert.assertEquals(robot.getRobotDir(), NORTH);
            Assert.assertTrue(robot.getIsActive());
        }

    }

    public void testScenario4() throws ToyRobotException {
        if (isNotEmpty(testData, 4)) {
            ToyRobot robot = new ToyRobot();
            robot.setName("testData4");
            robot.setCommands(testData.get(DATA_STRING + 4).toString());
            toyRobotService.start(robot);
            Assert.assertEquals(robot.getCoordX(), 2);
            Assert.assertEquals(robot.getCoordY(), 1);
            Assert.assertEquals(robot.getRobotDir(), WEST);
            Assert.assertTrue(robot.getIsActive());
        }

    }

    public void testScenario5() {
        if (isNotEmpty(testData, 5)) {
            ToyRobot robot = new ToyRobot();
            robot.setName("testData5");
            robot.setCommands(testData.get(DATA_STRING + 5).toString());
            try {
                toyRobotService.start(robot);
            } catch (ToyRobotException e) {
                Assert.assertTrue(e.getMessage().contains("Coordinates beyond table's limit"));
                Assert.assertTrue(robot.getIsActive());
            }
        }

    }

    public void testScenario6() throws ToyRobotException {
        if (isNotEmpty(testData, 7)) {
            ToyRobot robot = new ToyRobot();
            robot.setName("testData7");
            robot.setCommands(testData.get(DATA_STRING + 7).toString());
            toyRobotService.start(robot);
            Assert.assertFalse(robot.getIsActive());
        }

    }

    public void testMultipleRobots() throws ToyRobotException {
        if (isNotEmpty(testData, 1)) {
            ToyRobot robot1 = new ToyRobot();
            robot1.setName("testData1");
            robot1.setCommands(testData.get(DATA_STRING + 1).toString());
            toyRobotService.start(robot1);

            ToyRobot robot2 = new ToyRobot();
            robot2.setCommands("PLACE 0,1,NORTH REPORT");
            toyRobotService.start(robot2);

            Assert.assertTrue(robot1.getIsActive());
            Assert.assertFalse(robot2.getIsActive());
            robot2.setCommands("PLACE 3,3,SOUTH MOVE RIGHT MOVE REPORT");
            toyRobotService.start(robot2);
            Assert.assertTrue(robot2.getIsActive());
            Assert.assertEquals(robot2.getCoordX(), 2);
            Assert.assertEquals(robot2.getCoordY(), 2);
            Assert.assertEquals(robot2.getRobotDir(), WEST);
        }

    }

    private boolean isNotEmpty(JSONObject testData, int index) {
        return testData != null & testData.size() > 0 && testData.get(DATA_STRING + index) != null;
    }

}
