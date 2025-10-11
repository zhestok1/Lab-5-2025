import functions.*;

public class Main {
    public static void main(String[] args) throws InappropriateFunctionPointException {
        // Демонстрация линейной функции y = 2x + 1
        System.out.println("=== Линейная функция y = 2x + 1 ===");
        TabulatedFunction linearFunc = new ArrayTabulatedFunction(0, 10, 6);
        for (int i = 0; i < linearFunc.getPointsCount(); i++){
            double x = linearFunc.getPointX(i);
            linearFunc.setPointY(i, 2 * x + 1);
        }

        System.out.println("Исходная функция:");
        for (int i = 0; i < linearFunc.getPointsCount(); i++) {
            System.out.printf("Точка %d: x=%.2f, y=%.2f%n",
                    i, linearFunc.getPointX(i), linearFunc.getPointY(i));
        }

        // Демонстрация добавления точки
        System.out.println("\n=== Добавление точки (5.5, 12.0) ===");
        linearFunc.addPoint(new FunctionPoint(5.5, 12.0));
        System.out.println("После добавления:");
        for (int i = 0; i < linearFunc.getPointsCount(); i++) {
            System.out.printf("Точка %d: x=%.2f, y=%.2f%n",
                    i, linearFunc.getPointX(i), linearFunc.getPointY(i));
        }

        // Демонстрация удаления точки
        System.out.println("\n=== Удаление точки с индексом 2 ===");
        linearFunc.deletePoint(2);
        System.out.println("После удаления:");
        for (int i = 0; i < linearFunc.getPointsCount(); i++) {
            System.out.printf("Точка %d: x=%.2f, y=%.2f%n",
                    i, linearFunc.getPointX(i), linearFunc.getPointY(i));
        }

        // Демонстрация изменения точки
        System.out.println("\n=== Изменение точки с индексом 1 ===");
        linearFunc.setPoint(1, new FunctionPoint(3.0, 7.0));
        System.out.println("После изменения:");
        for (int i = 0; i < linearFunc.getPointsCount(); i++) {
            System.out.printf("Точка %d: x=%.2f, y=%.2f%n",
                    i, linearFunc.getPointX(i), linearFunc.getPointY(i));
        }

        // Демонстрация интерполяции
        System.out.println("\n=== Интерполяция значений ===");
        System.out.printf("f(%.2f) = 2*%.2f + 1 ≈ %.4f%n", 2.5, 2.5, linearFunc.getFunctionValue(2.5));
        System.out.printf("f(%.2f) = 2*%.2f + 1 ≈ %.4f%n", 7.2, 7.2, linearFunc.getFunctionValue(7.2));
        System.out.printf("f(%.2f) = %.4f (вне области определения)%n", 12.0, linearFunc.getFunctionValue(12.0));

        // Демонстрация границ области определения
        System.out.println("\n=== Границы области определения ===");
        System.out.printf("Левая граница: %.2f%n", linearFunc.getLeftDomainBorder());
        System.out.printf("Правая граница: %.2f%n", linearFunc.getRightDomainBorder());

        // Демонстрация работы с отдельными координатами
        System.out.println("\n=== Изменение координат по отдельности ===");
        System.out.println("До изменения:");
        System.out.printf("Точка 0: x=%.2f, y=%.2f%n", linearFunc.getPointX(0), linearFunc.getPointY(0));

        linearFunc.setPointX(0, 0.5);
        linearFunc.setPointY(0, 2.0);

        System.out.println("После изменения:");
        System.out.printf("Точка 0: x=%.2f, y=%.2f%n", linearFunc.getPointX(0), linearFunc.getPointY(0));

        // Итоговое состояние функции
        System.out.println("\n=== Итоговое состояние функции ===");
        for (int i = 0; i < linearFunc.getPointsCount(); i++) {
            System.out.printf("Точка %d: x=%.2f, y=%.2f%n",
                    i, linearFunc.getPointX(i), linearFunc.getPointY(i));
        }
        System.out.println("Всего точек: " + linearFunc.getPointsCount());

        // ДЕМОНСТРАЦИЯ ИСКЛЮЧЕНИЙ
        System.out.println("\n\n=== ДЕМОНСТРАЦИЯ ИСКЛЮЧЕНИЙ ===");

        // 1. Некорректный конструктор
        System.out.println("\n1. Тест некорректного конструктора:");
        try {
            TabulatedFunction badFunc = new ArrayTabulatedFunction(10, 5, 5);
            System.out.println("ОШИБКА: Исключение не было выброшено!");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Поймано исключение: " + e.getMessage());
        }

        // 2. Выход за границы индекса
        System.out.println("\n2. Тест выхода за границы индекса:");
        try {
            linearFunc.getPoint(100);
            System.out.println("ОШИБКА: Исключение не было выброшено!");
        } catch (FunctionPointIndexOutOfBoundsException e) {
            System.out.println("✓ Поймано исключение: FunctionPointIndexOutOfBoundsException");
        }

        // 3. Нарушение упорядоченности при установке точки
        System.out.println("\n3. Тест нарушения упорядоченности:");
        try {
            linearFunc.setPoint(2, new FunctionPoint(1.0, 5.0)); // Должно быть между 1 и 3
            System.out.println("ОШИБКА: Исключение не было выброшено!");
        } catch (InappropriateFunctionPointException e) {
            System.out.println("✓ Поймано исключение: InappropriateFunctionPointException");
        }

        // 4. Добавление точки с существующей X координатой
        System.out.println("\n4. Тест добавления точки с существующим X:");
        try {
            linearFunc.addPoint(new FunctionPoint(3.0, 10.0)); // X=3.0 уже существует
            System.out.println("ОШИБКА: Исключение не было выброшено!");
        } catch (InappropriateFunctionPointException e) {
            System.out.println("✓ Поймано исключение: InappropriateFunctionPointException");
        }

        // 5. Удаление точки при недостаточном количестве точек
        System.out.println("\n5. Тест удаления при малом количестве точек:");
        try {
            TabulatedFunction smallFunc = new ArrayTabulatedFunction(0, 2, 3);
            smallFunc.deletePoint(0);
            smallFunc.deletePoint(0);
            smallFunc.deletePoint(0); // Должно вызвать исключение
            System.out.println("ОШИБКА: Исключение не было выброшено!");
        } catch (IllegalStateException e) {
            System.out.println("✓ Поймано исключение: " + e.getMessage());
        }

        // 6. Некорректный индекс при удалении
        System.out.println("\n6. Тест некорректного индекса при удалении:");
        try {
            linearFunc.deletePoint(-1);
            System.out.println("ОШИБКА: Исключение не было выброшено!");
        } catch (FunctionPointIndexOutOfBoundsException e) {
            System.out.println("✓ Поймано исключение: FunctionPointIndexOutOfBoundsException");
        }

        // 7. Нарушение упорядоченности при изменении X
        System.out.println("\n7. Тест нарушения упорядоченности при setPointX:");
        try {
            linearFunc.setPointX(1, 8.0); // Должно быть между 0 и 2
            System.out.println("ОШИБКА: Исключение не было выброшено!");
        } catch (InappropriateFunctionPointException e) {
            System.out.println("✓ Поймано исключение: InappropriateFunctionPointException");
        }

        // 8. Создание функции с недостаточным количеством точек
        System.out.println("\n8. Тест создания функции с 1 точкой:");
        try {
            TabulatedFunction onePointFunc = new LinkedListTabulatedFunction(0, 5, 1);
            System.out.println("ОШИБКА: Исключение не было выброшено!");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Поймано исключение: " + e.getMessage());
        }

        // 9. Некорректный индекс при получении Y
        System.out.println("\n9. Тест некорректного индекса при getPointY:");
        try {
            linearFunc.getPointY(100);
            System.out.println("ОШИБКА: Исключение не было выброшено!");
        } catch (FunctionPointIndexOutOfBoundsException e) {
            System.out.println("✓ Поймано исключение: FunctionPointIndexOutOfBoundsException");
        }

        // 10. Демонстрация работы LinkedListTabulatedFunction
        System.out.println("\n\n=== LinkedListTabulatedFunction ===");
        TabulatedFunction linkedListFunc = new LinkedListTabulatedFunction(0, 4, new double[]{1, 3, 5, 7, 9});
        System.out.println("Функция создана через LinkedListTabulatedFunction:");
        for (int i = 0; i < linkedListFunc.getPointsCount(); i++) {
            System.out.printf("Точка %d: x=%.2f, y=%.2f%n",
                    i, linkedListFunc.getPointX(i), linkedListFunc.getPointY(i));
        }

        // Проверка интерполяции для LinkedList
        System.out.println("\nИнтерполяция для LinkedListTabulatedFunction:");
        System.out.printf("f(%.2f) ≈ %.4f%n", 1.5, linkedListFunc.getFunctionValue(1.5));
        System.out.printf("f(%.2f) ≈ %.4f%n", 2.8, linkedListFunc.getFunctionValue(2.8));

        System.out.println("\n=== ВСЕ ТЕСТЫ ЗАВЕРШЕНЫ ===");
    }
}