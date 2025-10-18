package functions.meta;

import functions.Function;

public class Shift implements Function {

    private Function func;
    private double xCoefficient;
    private double yCoefficient;

    /**
     * Конструктор класса Shift
     * @param func функция
     * @param xCoefficient коэффициент по х
     * @param yCoefficient коэффициент по y
     * @throws IllegalArgumentException
     */
    public Shift(Function func, double xCoefficient, double yCoefficient)
            throws IllegalArgumentException {
        if (func == null) throw new IllegalArgumentException("Function cannot be null");
        this.func = func;
        this.xCoefficient = xCoefficient;
        this.yCoefficient = yCoefficient;
    }

    @Override
    public double getLeftDomainBorder() {
        return func.getLeftDomainBorder() - xCoefficient; // Если + то влево, если минус то вправо
    }

    @Override
    public double getRightDomainBorder() {
        return func.getRightDomainBorder() - xCoefficient;
    }

    @Override
    public double getFunctionValue(double x) {
        return func.getFunctionValue(x + xCoefficient) + yCoefficient; // Сдвиг по y
    }
}
