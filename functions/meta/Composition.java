package functions.meta;

import functions.Function;

public class Composition implements Function {

    private Function firstFunc;
    private Function secondFunc;

    /**
     * Конструктор класса Composition
     * @param firstFunc первая функция
     * @param secondFunc вторая функция
     * @throws IllegalArgumentException
     */
    public Composition(Function firstFunc, Function secondFunc) throws IllegalArgumentException {
        if (firstFunc == null || secondFunc == null)
            throw new IllegalArgumentException("Function cannot be null");
        this.firstFunc = firstFunc;
        this.secondFunc = secondFunc;
    }

    @Override
    public double getLeftDomainBorder() {
        return secondFunc.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return firstFunc.getRightDomainBorder(); // Могу взять разные так как области совпадают

    }

    @Override
    // Применение к одной функции результата другой
    public double getFunctionValue(double x) {

        double secondVal = secondFunc.getFunctionValue(x);
        return firstFunc.getFunctionValue(secondVal);
    }
}
