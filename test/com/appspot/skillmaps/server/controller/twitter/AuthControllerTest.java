package com.appspot.skillmaps.server.controller.twitter;

import org.slim3.tester.ControllerTestCase;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class AuthControllerTest extends ControllerTestCase {

    @Test
    public void run() throws Exception {
        tester.start("/twitter/auth");
        AuthController controller = tester.getController();
        assertThat(controller, is(notNullValue()));
        assertThat(tester.isRedirect(), is(false));
        assertThat(tester.getDestinationPath(), is(nullValue()));
    }
}
