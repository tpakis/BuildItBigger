package com.udacity.builditbigger.aithanasakis.builditbigger;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;


import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class JokeAsyncTaskTest {

    @Test
    public void testAsyncTask() throws InterruptedException{
        final CountDownLatch count = new CountDownLatch(1);
        JokeTask jokeTask = new JokeTask(null){
            @Override
            protected void onPostExecute(String result) {
                assertNotNull(result);
                //check that the result is not an error message from the asyncTask
                assertTrue(!result.contains("java.net."));
                count.countDown();
            }
        };
        jokeTask.execute();
        count.await();
    }

}
