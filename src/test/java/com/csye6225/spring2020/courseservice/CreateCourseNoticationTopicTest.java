package com.csye6225.spring2020.courseservice;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

public class CreateCourseNoticationTopicTest {
	
	private static Object input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input object here.
        input = "{courseId=csye6100}";
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
    	CreateCourseNoticationTopic handler = new CreateCourseNoticationTopic();
        Context ctx = createContext();

        String output = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        Assert.assertEquals("arn:aws:sns:us-west-2:556058823566:"+input, output);
    }
}
