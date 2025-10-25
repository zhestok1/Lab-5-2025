package functions.meta;

import functions.Function;

public class Scale implements Function {

    private Function func;
    private double xCoefficient;
    private double yCoefficient;


    /**
     * Конструктор класса Scale
     * @param func функция
     * @param xCoefficient коэффициент по x
     * @param yCoefficient коэффицициент по y
     * @throws IllegalArgumentException
     */
    public Scale(Function func, double xCoefficient, double yCoefficient)
            throws IllegalArgumentException {
        if (func == null) throw new IllegalArgumentException("Function cannot be null");
        this.func = func;
        this.xCoefficient = xCoefficient;
        this.yCoefficient = yCoefficient;
    }

    public double getLeftDomainBorder() {
        if (xCoefficient > 0) {
            return func.getLeftDomainBorder() * xCoefficient;
        } else if (xCoefficient < 0) {
            return func.getRightDomainBorder() * xCoefficient;
        } else {
            return Double.NEGATIVE_INFINITY;
        }
    }

    public double getRightDomainBorder() {
        if (xCoefficient > 0) {
            return func.getRightDomainBorder() * xCoefficient;
        } else if (xCoefficient < 0) {
            return func.getLeftDomainBorder() * xCoefficient;
        } else {
            return Double.POSITIVE_INFINITY;
        }
    }

    @Override
    // Масштабирование вдоль осей
    public double getFunctionValue(double x) {
        // Проверяем, что x принадлежит области определения
        double leftBorder = getLeftDomainBorder();
        double rightBorder = getRightDomainBorder();

        if (x < leftBorder || x > rightBorder) {
            return Double.NaN;
        }

        // Масштабируем аргумент и результат
        return func.getFunctionValue(x * xCoefficient) * yCoefficient; // Получаю новые значения
    }
}
