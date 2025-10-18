package functions;

import java.io.*;

public class TabulatedFunctions {

    /**
     * Приватный конструктор класса TabulatedFunctions
     */
    private TabulatedFunctions() {}

    /**
     * Табулирует функцию на заданном отрезке
     * @param function исходная функция
     * @param leftX левая граница табулирования
     * @param rightX правая граница табулирования
     * @param pointsCount количество точек
     * @return табулированная функция
     * @throws IllegalArgumentException если границы некорректны или выходят за область определения
     */
    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount) {
        // Проверка входных параметров
        if (function == null) {
            throw new IllegalArgumentException("Function cannot be null");
        }
        if (leftX >= rightX) {
            throw new IllegalArgumentException("LeftX cannot be greater or equal to rightX");
        }
        if (pointsCount < 2) {
            throw new IllegalArgumentException("Points count must be greater than 1");
        }

        // Проверка, что отрезок табулирования принадлежит области определения
        if (leftX < function.getLeftDomainBorder() || rightX > function.getRightDomainBorder()) {
            throw new IllegalArgumentException("Tabulation interval is outside function domain");
        }

        // Создание табулированной функции
        TabulatedFunction tabulatedFunc = new ArrayTabulatedFunction(leftX, rightX, pointsCount);

        // Заполнение значений функции
        for (int i = 0; i < pointsCount; i++) {
            double x = tabulatedFunc.getPointX(i);
            double y = function.getFunctionValue(x);
            tabulatedFunc.setPointY(i, y);
        }

        return tabulatedFunc;
    }

    /**
     * Записывает табулированную функцию
     * @param function функция
     * @param out поток
     * @throws IOException если есть проблемы с потоком
     */
    public static void outputTabulatedFunction(TabulatedFunction function, OutputStream out)
    throws IOException {

        // Закрытие потоков это ответственность вызывающего кода
        DataOutputStream data = new DataOutputStream(out); // DataOutputStream - поток обёртка
        try {
            int pCount = function.getPointsCount();
            data.writeInt(pCount);

            for (int i = 0; i < pCount; i++) {
                double x = function.getPointX(i);
                double y = function.getPointY(i);
                data.writeDouble(x);
                data.writeDouble(y);
            }

            data.flush(); // сбрасываем буфер
        } catch (IOException e) {
            throw new IOException("Have a problem with your flow!", e); // Пробрасываем исключение
        }
    }

    /**
     * Записывает табулированную функцию
     * @param in поток
     * @return ArrayTabulatedFunction из точек
     * @throws IOException если проблемы с файлом
     */
    public static TabulatedFunction inputTabulatedFunction(InputStream in) throws IOException, InappropriateFunctionPointException
    {
        DataInputStream data = new DataInputStream(in);

        FunctionPoint[] points;
        try {
            int pCount = data.readInt();
            points = new FunctionPoint[pCount];
            for (int i = 0; i < pCount; i++) {
                points[i] = new FunctionPoint(data.readDouble(), data.readDouble());
            }
        } catch (IOException e) {
            throw new IOException("Have a problem with your flow!", e);
        }

        return new ArrayTabulatedFunction(points);

    }

    /**
     * Запись функции в поток
     * @param function функция
     * @param out Поток
     * @throws IOException при некорректных данных в потоке
     */
    public static void writeTabulatedFunction(TabulatedFunction function, Writer out)
            throws IOException {

        BufferedWriter bfWriter = new BufferedWriter(out);

        try {
            int pCount = function.getPointsCount();

            // ПРАВИЛЬНАЯ запись числа как строки
            bfWriter.write(String.valueOf(pCount));
            bfWriter.write(" ");

            for (int i = 0; i < pCount; i++) {
                double x = function.getPointX(i);
                double y = function.getPointY(i);

                // Записываем каждое значение отдельно с пробелами
                bfWriter.write(String.valueOf(x));
                bfWriter.write(" ");
                bfWriter.write(String.valueOf(y));

                // Добавляем пробел между точками (кроме последней)
                if (i < pCount - 1) {
                    bfWriter.write(" ");
                }
            }

            bfWriter.flush();

        } catch (IOException e) {
            throw new IOException("Error writing tabulated function to writer", e);
        }
    }

    /**
     *
     * @param in Поток
     * @return Табулированную функцию
     * @throws IOException если проблемы с потоком
     */
    public static TabulatedFunction readTabulatedFunction(Reader in) throws IOException, InappropriateFunctionPointException {
        StreamTokenizer tokenizer = new StreamTokenizer(in);

        try {
            if (tokenizer.nextToken() != StreamTokenizer.TT_NUMBER) { // TT_NUMBER - токен являктся числом
                throw new IOException("Expected points count number");
            }
            int pointsCount = (int) tokenizer.nval;

            FunctionPoint[] points = new FunctionPoint[pointsCount];
            for (int i = 0; i < pointsCount; i++) {
                // Читаем x
                if (tokenizer.nextToken() != StreamTokenizer.TT_NUMBER) {
                    throw new IOException("Expected x coordinate at point " + i);
                }
                double x = tokenizer.nval;

                // Читаем y
                if (tokenizer.nextToken() != StreamTokenizer.TT_NUMBER) {
                    throw new IOException("Expected y coordinate at point " + i);
                }
                double y = tokenizer.nval;

                points[i] = new FunctionPoint(x, y);
            }

            return new ArrayTabulatedFunction(points);

        } catch (IOException e) {
            throw new IOException("Have a problem with your flow!", e);
        } catch (InappropriateFunctionPointException e) {
            throw new RuntimeException(e);
        }
    }
}