import functions.*;
import functions.basic.Cos;
import functions.basic.Exp;
import functions.basic.Log;
import functions.basic.Sin;
import functions.meta.Composition;
import functions.meta.Power;
import functions.meta.Sum;

import java.io.*;

public class Main {
    public static void main(String[] args) throws InappropriateFunctionPointException, IOException {

        Function sin = new Sin();
        Function cos = new Cos();

        double from = 0;
        double to = Math.PI;
        double step = 0.1;

        System.out.println("Sin values:");
        for (double x = from; x <= to + 1e-10; x += step) {
            System.out.printf("sin(%.1f) = %.6f\n", x, sin.getFunctionValue(x));
        }

        System.out.println("\nCos values:");
        for (double x = from; x <= to + 1e-10; x += step) {
            System.out.printf("cos(%.1f) = %.6f\n", x, cos.getFunctionValue(x));
        }

        System.out.println("\n=== Табулированные аналоги ===");
        int pointsCount = 10;
        TabulatedFunction tabulatedSin = TabulatedFunctions.tabulate(sin, from, to, pointsCount);
        TabulatedFunction tabulatedCos = TabulatedFunctions.tabulate(cos, from, to, pointsCount);

        System.out.println("Табулированный Sin:");
        for (double x = from; x <= to + 1e-10; x += step) {
            double original = sin.getFunctionValue(x);
            double tabulated = tabulatedSin.getFunctionValue(x);
            System.out.printf("x=%.1f: original=%.6f, tabulated=%.6f, diff=%.6f\n",
                    x, original, tabulated, Math.abs(original - tabulated));
        }

        System.out.println("\n=== Сумма квадратов ===");

        // Квадраты функций
        Power sinSquared = new Power(tabulatedSin, 2);
        Power cosSquared = new Power(tabulatedCos, 2);

        // Сумма квадратов
        Sum sumOfSquares = new Sum(sinSquared, cosSquared);

        System.out.println("Сумма квадратов с " + pointsCount + " разными точками:");
        for (double x = from; x <= to + 1e-10; x += step) {
            System.out.printf("x=%.1f: result=%.6f\n", x, sumOfSquares.getFunctionValue(x));
        }

        // Исследуем влияние количества точек
        System.out.println("\n=== Влияние количества точек ===");
        int[] pointCounts = {5, 10, 20, 50};
        for (int count : pointCounts) {
            TabulatedFunction sinTab = TabulatedFunctions.tabulate(sin, from, to, count);
            TabulatedFunction cosTab = TabulatedFunctions.tabulate(cos, from, to, count);
            Sum sum = new Sum(new Power(sinTab, 2), new Power(cosTab, 2));

            double error = 0;
            for (double x = from; x <= to + 1e-10; x += step) {
                double actual = sum.getFunctionValue(x);
                double expected = 1.0; // sin² + cos² = 1
                error += Math.abs(actual - expected);
            }
            System.out.printf("Точек: %d, средняя ошибка: %.6f\n", count, error/((to-from)/step + 1));
        }

        // 4. Работа с экспонентой (бинарный формат)
        System.out.println("\n=== Экспонента (бинарный формат) ===");
        Function exp = new Exp();
        TabulatedFunction tabulatedExp = TabulatedFunctions.tabulate(exp, 0, 10, 11);

        // Запись в файл
        try (FileOutputStream fos = new FileOutputStream("exp.txt")) {
            TabulatedFunctions.outputTabulatedFunction(tabulatedExp, fos);
        }

        // Чтение из файла
        TabulatedFunction readExp;
        try (FileInputStream fis = new FileInputStream("exp.txt")) {
            readExp = TabulatedFunctions.inputTabulatedFunction(fis);
        }

        System.out.println("Сравнение экспоненты:");
        for (double x = 0; x <= 10; x += 1) {
            double original = tabulatedExp.getFunctionValue(x);
            double fromFile = readExp.getFunctionValue(x);
            System.out.printf("x=%.0f: original=%.6f, fromFile=%.6f, equal=%b\n",
                    x, original, fromFile, Math.abs(original - fromFile) < 1e-10);
        }

        // 5. Работа с логарифмом (текстовый формат)
        System.out.println("\n=== Логарифм (текстовый формат) ===");
        Function ln = new Log(Math.E);
        TabulatedFunction tabulatedLn = TabulatedFunctions.tabulate(ln, 1, 10, 10); // от 1 т.к. ln(0) не определен

        // Запись в файл
        try (FileWriter fw = new FileWriter("ln.txt")) {
            TabulatedFunctions.writeTabulatedFunction(tabulatedLn, fw);
        }

        // Чтение из файла
        TabulatedFunction readLn;
        try (FileReader fr = new FileReader("ln.txt")) {
            readLn = TabulatedFunctions.readTabulatedFunction(fr);
        }

        System.out.println("Сравнение логарифма:");
        for (double x = 1; x <= 10; x += 1) {
            double original = tabulatedLn.getFunctionValue(x);
            double fromFile = readLn.getFunctionValue(x);
            System.out.printf("x=%.0f: original=%.6f, fromFile=%.6f, equal=%b\n",
                    x, original, fromFile, Math.abs(original - fromFile) < 1e-10);
        }
        // Бинарный формат
        // + Меньший размер файла
        // + Точное представление чисел
        // + Быстрая запись/чтение
        // - Не читаем для человека

        // Текстовый формат
        // + Читаемость для человека
        // + Легко редактировать
        // - Больший размер файла

        System.out.println("=== Сериализация композиции функций ===");

        Function LN = new Log(Math.E);
        Function EXP = new Exp();

        // Создаем композицию: ln(exp(x))
        Composition lnOfExp = new Composition(LN, EXP);

        // Табулируем композицию на отрезке [0, 10] с 11 точками
        TabulatedFunction tabulatedLnOfExp = TabulatedFunctions.tabulate(lnOfExp, 0, 10, 11);

        // Сериализуем в файл
        String filename = "ln_of_exp_serialized.txtcd ";
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(tabulatedLnOfExp);
            System.out.println("Табулированная функция сериализована в файл: " + filename);
        }

        // Десериализуем из файла
        TabulatedFunction deserializedFunction;
        try (FileInputStream fileIn = new FileInputStream(filename);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            deserializedFunction = (TabulatedFunction) in.readObject();
            System.out.println("✓ Функция десериализована из файла: " + filename);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Выводим значения исходной и десериализованной функции
        System.out.println("\nСравнение исходной и десериализованной функции:");
        System.out.println("x\t\tИсходная\tДесериализованная\tРазница");
        System.out.println("------------------------------------------------------------");

        for (double x = 0; x <= 10; x += 1) {
            double originalValue = tabulatedLnOfExp.getFunctionValue(x);
            double deserializedValue = deserializedFunction.getFunctionValue(x);
            double difference = Math.abs(originalValue - deserializedValue);

            System.out.printf("%.1f\t\t%.6f\t%.6f\t\t%.6f\n",
                    x, originalValue, deserializedValue, difference);
        }

    }
}