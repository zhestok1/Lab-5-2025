package functions.meta;

import functions.Function;

public class Mult implements Function {

    private Function firstFunc;
    private Function secondFunc;

    /**
     * Конструктор класса Mult
     * @param firstFunc первая функция
     * @param secondFunc вторая функция
     * @throws IllegalArgumentException обработка null
     */
    public Mult(Function firstFunc, Function secondFunc) throws IllegalArgumentException {
        if (firstFunc == null || secondFunc == null) throw new IllegalArgumentException("Function cannot be null!");

        this.firstFunc = firstFunc;
        this.secondFunc = secondFunc;
    }

    @Override
    public double getLeftDomainBorder() {

        double leftBorder1 = firstFunc.getLeftDomainBorder();
        double leftBorder2 = secondFunc.getLeftDomainBorder();

        return Math.max(leftBorder1, leftBorder2);
    }

    @Override
    public double getRightDomainBorder() {

        double rightBorder1 = firstFunc.getRightDomainBorder();
        double rightBorder2 = firstFunc.getRightDomainBorder();

        return Math.min(rightBorder1, rightBorder2);
    }

    @Override
    // Объекты - произведение первой и второй
    public double getFunctionValue(double x) {
        // Проверяем, что x принадлежит пересечению областей определения
        double leftBorder = getLeftDomainBorder();
        double rightBorder = getRightDomainBorder();

        if (x < leftBorder || x > rightBorder) {
            return Double.NaN;
        }

        // Умножаем значения функций
        return firstFunc.getFunctionValue(x) * secondFunc.getFunctionValue(x);
    }
}
