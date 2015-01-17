package OptionPricing;

import org.apache.commons.math3.util.Pair;
import java.util.List;
import org.joda.time.DateTime;
import org.apache.commons.math3.util.Pair;

public interface StockPath {
	public List<Pair<DateTime, Double>> getPrices();
}
