package monte_carlo;

import org.apache.commons.math3.distribution.NormalDistribution;

import java.time.LocalTime;

public class Estadistica {
    NormalDistribution distribucion;

    public Estadistica(LocalTime mean, LocalTime sd) {
        distribucion = new NormalDistribution(
                mean.toSecondOfDay(),
                sd.toSecondOfDay());
    }

    public LocalTime sample() {
        int seconds = (int) Math.round(distribucion.sample());

        if (LocalTime.MAX.toSecondOfDay() < seconds)
            seconds = seconds % LocalTime.MAX.toSecondOfDay();
        return LocalTime.ofSecondOfDay(seconds);
    }
}
