import main.TimeSeries;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(1991);
        expectedYears.add(1992);
        expectedYears.add(1994);
        expectedYears.add(1995);

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>();
        expectedTotal.add(0.0);
        expectedTotal.add(100.0);
        expectedTotal.add(600.0);
        expectedTotal.add(500.0);

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    @Test
    public void testEmptyBasic() {
        TimeSeries catPopulation = new TimeSeries();
        TimeSeries dogPopulation = new TimeSeries();

        assertThat(catPopulation.years()).isEmpty();
        assertThat(catPopulation.data()).isEmpty();

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);

        assertThat(totalPopulation.years()).isEmpty();
        assertThat(totalPopulation.data()).isEmpty();
    }

    @Test
    public void testDivisionBasic() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1992, 50.0);
        dogPopulation.put(1994, 400.0);

        TimeSeries quotient = catPopulation.dividedBy(dogPopulation);
        // expected: 1992: 2.0
        //           1994: 0.5

        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(1992);
        expectedYears.add(1994);

        assertThat(quotient.years()).isEqualTo(expectedYears);

        List<Double> expectedQuotient = new ArrayList<>();
        expectedQuotient.add(2.0);
        expectedQuotient.add(0.5);

        for (int i = 0; i < expectedQuotient.size(); i += 1) {
            assertThat(quotient.data().get(i)).isWithin(1E-10).of(expectedQuotient.get(i));
        }
    }

    // By gemini
    @Test
    public void testDividedByException() {
        TimeSeries ts1 = new TimeSeries();
        ts1.put(2000, 50.0);
        TimeSeries ts2 = new TimeSeries();

        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> ts1.dividedBy(ts2)
        );
    }

    @Test
    public void testDividedByIgnoreExtraYears() {
        TimeSeries ts1 = new TimeSeries();
        ts1.put(2000, 50.0);

        TimeSeries ts2 = new TimeSeries();
        ts2.put(2000, 2.0);
        ts2.put(2005, 999.0);

        TimeSeries quotient = ts1.dividedBy(ts2);

        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(2000);

        assertThat(quotient.years()).isEqualTo(expectedYears);
        assertThat(quotient.get(2000)).isWithin(1E-10).of(25.0);

        assertThat(quotient.containsKey(2005)).isFalse();
    }
} 