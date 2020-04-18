package com.csye6225.spring2020.courseservice;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class subscribeToCourseServiceTest {

    private static Object input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = "0b564f79-0779-4ac5-b324-c492a162e8f7";
    }

    private Context createContext() {
        TestContext ctx = new TestContext();
        String topicArn="arn:aws:sns:us-west-2:556058823566:csye6225";
        // TODO: customize your context here if needed.
        ctx.setFunctionName("123456");
        ctx.setInvokedFunctionArn(topicArn);
        return ctx;
    }

    @Test
    public void testsubscribeWebsiteDealsSubsystem() {
    	subscribeToCourseService handler = new subscribeToCourseService();
        Context ctx = createContext();

        String output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        Assert.assertEquals("Hello from Lambda!", output);
    }
}
