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

package io.github.msdk.datamodel.peaklists;

import javax.annotation.Nonnull;
import javax.swing.table.TableCellRenderer;

/* 
 * WARNING: the interfaces in this package are still under construction
 */

/**
 * 
 *
 * @param <DataType>
 */
public interface PeakListColumn<DataType> {

    /**
     * @return Short descriptive name for the peak list column
     */
    String getName();

    /**
     * Change the name of this peak list column
     */
    void setName(String name);

    /**
     * Returns an information about whether or not the column should be displayed
     * in a table.
     * 
     * @return True or False
     */
    boolean getIsDisplayable();

    /**
     * Change the displayable state of this column.
     */
    void setIsDisplayable(boolean displayable);

    /**
     * @return
     */
    @Nonnull
    Class<DataType> getDataTypeClass();

    /**
     * Returns the value for the column
     * 
     * @return Class<DataType>
     */
    Class<DataType> getValue();
    
    /**
     * Updates the value of the column
     * 
     * @param Class<DataType> data
     *            Data to be stored in column
     */
    void setValue(Class<DataType> data);

    /**
     * 
     */
    @Nonnull
    TableCellRenderer getTableCellRenderer();

}
