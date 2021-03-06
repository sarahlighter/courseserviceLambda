package com.csye6225.spring2020.courseservice;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class sendRegisteredMessageTest {

    private static Object input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = "csye6225";
    }

    private Context createContext() {
        TestContext ctx = new TestContext();
        String topicArn="arn:aws:sns:us-west-2:556058823566:CourseRegistrar";
        // TODO: customize your context here if needed.
        ctx.setFunctionName("subscribeWebsiteDealsSubsystem");
        ctx.setInvokedFunctionArn(topicArn);
        return ctx;
    }

    @Test
    public void testsendDealMessage() {
    	sendRegisteredMessage handler = new sendRegisteredMessage();
        Context ctx = createContext();

        String output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        Assert.assertEquals("Hello from Lambda!", output);
    }
}
