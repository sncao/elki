package de.lmu.ifi.dbs.index.spatial;

/**
 * Defines the requirements for an object that can be used as a data object in a SpatialIndex.
 *
 * @author Elke Achtert (<a href="mailto:achtert@dbs.ifi.lmu.de">achtert@dbs.ifi.lmu.de</a>)
 */
public interface SpatialData extends SpatialObject {
  /**
   * Returns the values of the data object.
   * @return the values of the data object
   */ 
  double[] getValues();

  /**
   * Returns the unique object id if the data object.
   * @return the unique object id if the data object
   */
  int getObjectID();
}
