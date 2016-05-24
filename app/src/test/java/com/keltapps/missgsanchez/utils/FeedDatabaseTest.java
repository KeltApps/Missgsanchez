package com.keltapps.missgsanchez.utils;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * Created by sergio on 9/04/16 for KelpApps.
 */


@RunWith(MockitoJUnitRunner.class)
public class FeedDatabaseTest {
    @Mock
    Context mMockContext;


    @Test
    public void testSynchronizeEntries() throws Exception {
        assertThat(FeedDatabase.getInstance(mMockContext).synchronizeEntries("[]"), is(equalTo(FeedDatabase.RETURN_EMPTY_PAGE)));

    }
}