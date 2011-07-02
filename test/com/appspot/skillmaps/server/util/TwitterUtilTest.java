package com.appspot.skillmaps.server.util;

import org.junit.Assert;
import org.junit.Test;

public class TwitterUtilTest {
    @Test
    public void test() {
        String url = "http://twitter.com/#!/masayang/status/87043498330693632";
        String url2 =
            "https://twitter.com/#!/masayang/status/87043498330693632";
        String url3 =
            "https://twitter.com2/#!/masayang/status/87043498330693632";

        // System.out.println(pattern.matcher(url2).matches());
        Assert.assertTrue(TwitterUtil.isTwitterTimeline(url));
        Assert.assertTrue(TwitterUtil.isTwitterTimeline(url2));
        Assert.assertFalse(TwitterUtil.isTwitterTimeline(url3));
        Assert.assertEquals(
            87043498330693632L,
            (long) TwitterUtil.getTimelineId(url2));
        Assert.assertEquals(null, TwitterUtil.getTimelineId(url3));

    }
}
