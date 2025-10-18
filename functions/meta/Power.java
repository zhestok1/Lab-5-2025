package functions.meta;

import functions.Function;

public class Power implements Function{

    private Function funcBase;
    private double power;

    /**
     * Конструктор класса Power
     * @param funcBase
     * @param power
     */
    public Power(Function funcBase, double power) {
        if (funcBase == null) throw new IllegalArgumentException("Function cannot be a null!");

        this.funcBase = funcBase;
        this.power = power;
    }

    @Override
    public double getLeftDomainBorder() {
        return funcBase.getLeftDomainBorder();
    }

    @Override
    public double getRightDomainBorder() {
        return funcBase.getRightDomainBorder();
    }

    @Override
    public double getFunctionValue(double x) {
        return Math.pow(funcBase.getFunctionValue(x), power);
    }


}
