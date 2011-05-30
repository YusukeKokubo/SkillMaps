package com.appspot.skillmaps.server.controller.twitter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.slim3.tester.ControllerTestCase;

public class RegistrationControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/twitter/registration");
        RegistrationController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is("/twitter/registration.jsp"));
    }
}
