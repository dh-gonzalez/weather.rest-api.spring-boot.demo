package weather.rest_api.spring_boot.demo.common.utils;

import java.util.List;
import java.util.stream.IntStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Usefull methods for maths
 */
public final class MathUtils {

    /** Logger for this class */
    private static final Logger LOGGER = LoggerFactory.getLogger(MathUtils.class);

    /**
     * Compute the average difference (delta) between values of the list
     * 
     * @param values the values
     * @return the average difference
     */
    public static double computeAverageDelta(List<Double> values) {

        double averageDelta = IntStream.range(1, values.size())
                .mapToDouble(i -> values.get(i) - values.get(i - 1)).average().orElseThrow();

        LOGGER.debug("Average delta for {} is {}", values, averageDelta);

        return averageDelta;
    }
}
