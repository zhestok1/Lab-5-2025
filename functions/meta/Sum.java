package functions.meta;

import functions.Function;


public class Sum implements Function {

    private Function firstFunc;
    private Function secondFunc;

    /**
     * Конструктор класса Sum
     * @param firstFunc первая функция
     * @param secondFunc вторая функция
     * @throws IllegalArgumentException обработка null
     */
    public Sum(Function firstFunc, Function secondFunc) throws IllegalArgumentException {
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
        double rightBorder2 = secondFunc.getRightDomainBorder();

        return Math.min(rightBorder1, rightBorder2);
    }

    @Override
    // Результат - пересечение двух областей
    public double getFunctionValue(double x) {
        // Проверяем, что x принадлежит пересечению областей определения
        double leftBorder = getLeftDomainBorder();
        double rightBorder = getRightDomainBorder();

        if (x < leftBorder || x > rightBorder) {
            return Double.NaN;
        }

        // Суммируем значения функций
        return firstFunc.getFunctionValue(x) + secondFunc.getFunctionValue(x);
    }
}
