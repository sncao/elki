/*
 * This file is part of ELKI:
 * Environment for Developing KDD-Applications Supported by Index-Structures
 *
 * Copyright (C) 2018
 * ELKI Development Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package de.lmu.ifi.dbs.elki.distance.distancefunction.probabilistic;

import static net.jafama.FastMath.acos;
import static net.jafama.FastMath.sqrt;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import de.lmu.ifi.dbs.elki.data.DoubleVector;
import de.lmu.ifi.dbs.elki.data.HyperBoundingBox;
import de.lmu.ifi.dbs.elki.distance.distancefunction.AbstractDistanceFunctionTest;
import de.lmu.ifi.dbs.elki.utilities.ELKIBuilder;

/**
 * Unit test for Fisher-Rao distance.
 * 
 * @author Erich Schubert
 */
public class FisherRaoDistanceFunctionTest extends AbstractDistanceFunctionTest {
  @Test
  public void testBasic() {
    // Also test the builder - we could have just used .STATIC
    FisherRaoDistanceFunction df = new ELKIBuilder<>(FisherRaoDistanceFunction.class).build();
    basicChecks(df);
    varyingLengthBasic(0, df, Math.PI, Math.PI, Math.PI, Math.PI, Math.PI, Math.PI);
    nonnegativeSpatialConsistency(df);
  }

  @Test
  public void testFisherRaoDistance() {
    double[][] vecs = TOY_VECTORS_VAR;

    // Manual computation of correct distances:
    double d01 = 2 * acos(sqrt(0.08) * 2 + 0.1);
    double d03 = 2 * acos(sqrt(0.8 / 3) + sqrt(0.1 / 3) * 2);
    double d04 = 2 * acos(sqrt(0.48) + sqrt(0.02) * 2);
    double d14 = 2 * acos(sqrt(0.06) + sqrt(0.16) + sqrt(0.02));
    double d34 = 2 * acos(sqrt(0.2) + sqrt(0.2 / 3) * 2);
    double[][] distances = { //
        { 0., d01, d01, d03, d04 }, //
        { d01, 0., d01, d03, d14 }, //
        { d01, d01, 0., d03, d14 }, //
        { d03, d03, d03, 0., d34 }, //
        { d04, d14, d14, d34, 0. }, //
    };
    FisherRaoDistanceFunction df = new ELKIBuilder<>(FisherRaoDistanceFunction.class).build();
    for(int i = 0; i < vecs.length; i++) {
      DoubleVector vi = DoubleVector.wrap(vecs[i]);
      HyperBoundingBox mbri = new HyperBoundingBox(vecs[i], vecs[i]);
      for(int j = 0; j < vecs.length; j++) {
        DoubleVector vj = DoubleVector.wrap(vecs[j]);
        assertEquals("Distance " + i + "," + j, distances[i][j], df.distance(vi, vj), 1e-15);
        compareDistances(vj, vi, mbri, df);
      }
    }
  }
}
