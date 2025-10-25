package functions.basic;

import functions.Function;

public class Log implements Function {

    private double logBase; // Основание логарифма

    /**
     * Конструктор класса Log
     * @param logBase основание логарифма
     * @throws IllegalArgumentException некорректное основание логарифма
     */
    public Log(double logBase) throws IllegalArgumentException {
        if (logBase <= 0 || logBase == 1) {
            throw new IllegalArgumentException("Incorrect base of log!");
        }
        this.logBase = logBase;
    }

    @Override
    // Логарифм определен для x > 0
    public double getLeftDomainBorder() {
        return 0;
    }

    @Override
    // Логарифм устремляется к бесконечности
    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double getFunctionValue(double x) {
        if (x < 0) return Double.NaN;
        return Math.log(x) / Math.log(logBase);
    }
}
