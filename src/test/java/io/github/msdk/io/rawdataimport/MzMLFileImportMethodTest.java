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

package io.github.msdk.io.rawdataimport;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import io.github.msdk.MSDKException;
import io.github.msdk.datamodel.impl.MSDKObjectBuilder;
import io.github.msdk.datamodel.msspectra.MsSpectrumDataPointList;
import io.github.msdk.datamodel.msspectra.MsSpectrumType;
import io.github.msdk.datamodel.rawdata.MsScan;
import io.github.msdk.datamodel.rawdata.PolarityType;
import io.github.msdk.datamodel.rawdata.RawDataFile;
import io.github.msdk.datamodel.util.MsSpectrumUtil;
import io.github.msdk.io.rawdataimport.mzml.MzMLFileImportMethod;

public class MzMLFileImportMethodTest {

    private static final String TEST_DATA_PATH = "src/test/resources/rawdataimport/mzml/";

    @Test
    public void test5peptideFT() throws MSDKException {

        // Import the file
        File inputFile = new File(TEST_DATA_PATH + "5peptideFT.mzML");
        Assert.assertTrue(inputFile.canRead());
        MzMLFileImportMethod importer = new MzMLFileImportMethod(inputFile);
        RawDataFile rawFile = importer.execute();
        Assert.assertNotNull(rawFile);
        Assert.assertEquals(1.0, importer.getFinishedPercentage(), 0.0001);

        // The file has 7 scans
        List<MsScan> scans = rawFile.getScans();
        Assert.assertNotNull(scans);
        Assert.assertEquals(scans.size(), 7);

        // Create a data point list
        MsSpectrumDataPointList dataPoints = MSDKObjectBuilder
                .getMsSpectrumDataPointList();

        // 2nd scan, #2
        MsScan scan2 = scans.get(1);
        Assert.assertEquals(new Integer(2), scan2.getScanNumber());
        Assert.assertEquals(MsSpectrumType.PROFILE, scan2.getSpectrumType());
        Assert.assertEquals(new Integer(1), scan2.getMsFunction().getMsLevel());
        Assert.assertEquals(PolarityType.POSITIVE, scan2.getPolarity());
        scan2.getDataPoints(dataPoints);
        Assert.assertEquals(19800, dataPoints.getSize());
        Float scan2maxInt = MsSpectrumUtil.getMaxIntensity(dataPoints);
        Assert.assertEquals(1.8E5f, scan2maxInt, 1E4f);

        // 5th scan, #5
        MsScan scan5 = scans.get(4);
        Assert.assertEquals(new Integer(5), scan5.getScanNumber());
        Assert.assertEquals(MsSpectrumType.CENTROIDED, scan5.getSpectrumType());
        Assert.assertEquals(new Integer(2), scan5.getMsFunction().getMsLevel());
        Assert.assertEquals(PolarityType.POSITIVE, scan5.getPolarity());
        scan5.getDataPoints(dataPoints);
        Assert.assertEquals(837, dataPoints.getSize());
        Float scan5maxInt = MsSpectrumUtil.getMaxIntensity(dataPoints);
        Assert.assertEquals(8.6E3f, scan5maxInt, 1E2f);

        rawFile.dispose();

    }

    @Test
    public void testParamGroup() throws MSDKException {

        // Import the file
        File inputFile = new File(TEST_DATA_PATH
                + "RawCentriodCidWithMsLevelInRefParamGroup.mzML");
        Assert.assertTrue(inputFile.canRead());
        MzMLFileImportMethod importer = new MzMLFileImportMethod(inputFile);
        RawDataFile rawFile = importer.execute();
        Assert.assertNotNull(rawFile);
        Assert.assertEquals(1.0, importer.getFinishedPercentage(), 0.0001);

        // The file has 102 scans
        List<MsScan> scans = rawFile.getScans();
        Assert.assertNotNull(scans);
        Assert.assertEquals(scans.size(), 102);

        // Create a data point list
        MsSpectrumDataPointList dataPoints = MSDKObjectBuilder
                .getMsSpectrumDataPointList();

        // 2nd scan, #1001
        MsScan scan2 = scans.get(1);
        Assert.assertEquals(new Integer(1001), scan2.getScanNumber());
        Assert.assertEquals(MsSpectrumType.CENTROIDED, scan2.getSpectrumType());
        Assert.assertEquals(new Integer(2), scan2.getMsFunction().getMsLevel());
        Assert.assertEquals(PolarityType.POSITIVE, scan2.getPolarity());
        scan2.getDataPoints(dataPoints);
        Assert.assertEquals(33, dataPoints.getSize());
        Float scan2maxInt = MsSpectrumUtil.getMaxIntensity(dataPoints);
        Assert.assertEquals(6.8E3f, scan2maxInt, 1E2f);

        // 101th scan, #1100
        MsScan scan101 = scans.get(100);
        Assert.assertEquals(new Integer(1100), scan101.getScanNumber());
        Assert.assertEquals(MsSpectrumType.CENTROIDED,
                scan101.getSpectrumType());
        Assert.assertEquals(new Integer(1),
                scan101.getMsFunction().getMsLevel());
        scan101.getDataPoints(dataPoints);
        Assert.assertEquals(21, dataPoints.getSize());
        Float scan5maxInt = MsSpectrumUtil.getMaxIntensity(dataPoints);
        Assert.assertEquals(1.8E4f, scan5maxInt, 1E2f);

        rawFile.dispose();

    }

}