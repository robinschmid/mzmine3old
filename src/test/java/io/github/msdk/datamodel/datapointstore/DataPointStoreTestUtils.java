/* 
 * (C) Copyright 2015 by MSDK Development Team
 *
 * This software is dual-licensed under either
 *
 * (a) the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation
 *
 * or (per the licensee's choosing)
 *
 * (b) the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation.
 */

package io.github.msdk.datamodel.datapointstore;

import org.junit.Assert;

import io.github.msdk.datamodel.datapointstore.DataPointStore;
import io.github.msdk.datamodel.impl.MSDKObjectBuilder;
import io.github.msdk.datamodel.msspectra.MsSpectrumDataPointList;

/**
 * Tests for DataPointStores
 */
public class DataPointStoreTestUtils {

    @SuppressWarnings("null")
    public static void testStoreAndRetrieveReadDataPoints(DataPointStore store) {

        final int numOfGeneratedLists = 3; // TODO
        final Object storageIds[] = new Object[numOfGeneratedLists];

        for (int i = 0; i < numOfGeneratedLists; i++) {
            MsSpectrumDataPointList dataPoints = generateSpectrumDataPoints(i);
            storageIds[i] = store.storeDataPoints(dataPoints);
        }

        MsSpectrumDataPointList retrievedDataPoints = MSDKObjectBuilder.getMsSpectrumDataPointList();

        for (int i = 0; i < numOfGeneratedLists; i++) {

            // Retrieve
            store.readDataPoints(storageIds[i], retrievedDataPoints);

            // Check if the size of the retrieved list matches the initially
            // stored size
            Assert.assertEquals(i, retrievedDataPoints.getSize());
            
            // Check if the retrieved list matches the initially
            // generated data
            Assert.assertEquals(generateSpectrumDataPoints(i), retrievedDataPoints);

            if (i > 0) {
                // Check if the intensity value of the last data point matches
                // the specification of generateDataPoints()
                float intensities[] = retrievedDataPoints.getIntensityBuffer();
                Assert.assertEquals((float) i * 2, intensities[i-1],
                        0.00001);
            }
        }

    }

    @SuppressWarnings("null")
    public static void testRemoveDataPoints(DataPointStore store) {

        MsSpectrumDataPointList dataPoints = generateSpectrumDataPoints(1000);
        Object storageId = store.storeDataPoints(dataPoints);

        store.removeDataPoints(storageId);

        store.readDataPoints(storageId, dataPoints);

    }

    @SuppressWarnings("null")
    public static void testDispose(DataPointStore store) {

        MsSpectrumDataPointList dataPoints = generateSpectrumDataPoints(1000);
        Object storageId = store.storeDataPoints(dataPoints);

        store.dispose();

        store.readDataPoints(storageId, dataPoints);

    }

    /**
     * Generates a DataPointList pre-filled with a given number of data points
     * (count).
     * 
     * To assure that the lists generated by this method are not all the same,
     * the data points have m/z values of
     * 
     * <pre>
     * count / count ( = 1.0)
     * ...
     * count / 3.0
     * count / 2.0
     * count / 1.0
     * </pre>
     * 
     * Their intensity is always equal to m/z * 2.
     */
    public static MsSpectrumDataPointList generateSpectrumDataPoints(int count) {

        final MsSpectrumDataPointList list = MSDKObjectBuilder.getMsSpectrumDataPointList();
        final double mzValues[] = new double[count];
        final float intensityValues[] = new float[count];

        for (int i = 0; i < count; i++) {
            mzValues[i] = (double) count / (double) (count - i);
            intensityValues[i] = (float) mzValues[i] * 2;
        }

        list.setBuffers(mzValues, intensityValues, count);

        return list;
    }

}
