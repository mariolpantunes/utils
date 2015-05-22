package pt.it.av.atnog.utils.bla;

import org.junit.Test;
import pt.it.av.atnog.utils.MathUtils;
import pt.it.av.atnog.utils.StatisticsUtils;

import static org.junit.Assert.assertTrue;

/**
 * Created by mantunes on 4/22/15.
 */
public class StatisticUtilsTest {

    @Test
    public void test_averagePrecision() {
        boolean v[] = new boolean[6];
        v[0] = true;
        v[1] = false;
        v[2] = false;
        v[3] = true;
        v[4] = true;
        v[5] = true;
        double ap = StatisticsUtils.averagePrecision(v);
        assertTrue(MathUtils.equals(ap, 0.6917, 0.0001));
    }
}
