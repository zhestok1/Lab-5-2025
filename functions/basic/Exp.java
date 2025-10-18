package functions.basic;

import functions.Function;

public class Exp implements Function {

    @Override
    // Если хотим использовать левую границу этого типа, то подойдет и MIN_EXPONENT()
    // Но более математически точным будет возврат NEGATIVE_INFINITY
    public double getLeftDomainBorder() {
        return Double.NEGATIVE_INFINITY;
    }

    @Override
    // Если хотим использовать правую границу этого типа, то подойдет и MAX_EXPONENT()
    // Но более математически точным будет возврат POSITIVE_INFINITY
    public double getRightDomainBorder() {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public double getFunctionValue(double x) {
        return Math.exp(x);
    }
}
