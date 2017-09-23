package yuudaari.souls.common.util;

import java.util.Random;
import yuudaari.souls.common.config.Serializer;

public class Range {

	public Double min;
	public Double max;

	public Range() {
	}

	public Range(Number min, Number max) {
		this.min = min.doubleValue();
		this.max = max.doubleValue();
	}

	public Double get(Random random) {
		return random.nextDouble() * (max - min) + min;
	}

	public static final Serializer<Range> serializer = new Serializer<>(Range.class, "min", "max");

	@Override
	public String toString() {
		return "{Range: " + min + " - " + max + "}";
	}
}
