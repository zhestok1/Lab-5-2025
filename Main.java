import functions.*;

import java.io.*;

public class Main {
    public static void main(String[] args) throws InappropriateFunctionPointException, IOException {

        // Создаем тестовые данные
        double[] testData = {0.0, 1.0, 4.0, 9.0, 16.0};

        // Создаем объекты
        ArrayTabulatedFunction attf1 = new ArrayTabulatedFunction(0.0, 4.0, testData);
        ArrayTabulatedFunction attf2 = new ArrayTabulatedFunction(0.0, 4.0, testData);
        LinkedListTabulatedFunction lltf1 = new LinkedListTabulatedFunction(0.0, 4.0, testData);
        LinkedListTabulatedFunction lltf2 = new LinkedListTabulatedFunction(0.0, 4.0, testData);

        // 1. Тест toString()
        System.out.println("Тест toString() :");
        System.out.println("Array: " + attf1.toString());
        System.out.println("List: " + lltf1.toString());
        System.out.println();

        // 2. Тест equals()
        System.out.println("Тест equals():");
        System.out.println("array1 == array2: " + attf1.equals(attf2));
        System.out.println("list1 == list2: " + lltf1.equals(lltf2));
        System.out.println("array1 == list1: " + attf1.equals(lltf1));
        System.out.println();

        // 3. Тест hashCode()
        System.out.println("Тест hashCode():");
        System.out.println("array1 hash: " + attf1.hashCode());
        System.out.println("array2 hash: " + attf2.hashCode());
        System.out.println("list1 hash: " + lltf1.hashCode());
        System.out.println("Согласованность: " + (attf1.hashCode() == attf2.hashCode()));
        System.out.println();

        // 4. Тест изменения хеша
        System.out.println("Изменение хеша:");
        int oldHash = lltf1.hashCode();
        FunctionPoint point = new FunctionPoint(1, 999);
        lltf1.setPoint(1, point);// Меняем точку в lltf1
        int newHash = lltf1.hashCode();
        System.out.println("Старый хеш: " + oldHash + ", Новый хеш: " + newHash);
        System.out.println("Хеш изменился: " + (oldHash != newHash));
        System.out.println();

        // 5. Тест clone()
        System.out.println("Тест clone():");
        ArrayTabulatedFunction arrayClone = (ArrayTabulatedFunction) attf1.clone();
        LinkedListTabulatedFunction listClone = (LinkedListTabulatedFunction) lltf1.clone();

        System.out.println("array1 == clone: " + (attf1 == arrayClone));
        System.out.println("list1 == clone: " + (lltf1 == listClone));

        // Проверка глубокого копирования
        double originalY = attf1.getPoint(0).getY();
        attf1.getPoint(0).setY(999.0); // Меняем оригинал

        System.out.println("Глубокое копирование: " + (arrayClone.getPoint(0).getY() != 999.0));
        System.out.println("Клон не изменился: " + (arrayClone.getPoint(0).getY() == originalY));
    }




}