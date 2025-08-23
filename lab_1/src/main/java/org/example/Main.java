package org.example;

import org.example.exceptions.ModelPriceOutOfBoundsException;
import org.example.exceptions.NoSuchModelNameException;
import org.example.interf.Transport;
import org.example.interf.TransportUtils;
import org.example.mainAuto.Auto;

import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Auto auto;

    public static void main(String[] args) {
//        initializeAuto();
//        addInitialModels();
//        runMainMenu();
        // checkIOOperations();
        Auto myCar = new Auto("Lada", 2);
        myCar.addModel("Granta", 500000f);
        myCar.addModel("Vesta", 1200000f);

        System.out.println("=== Исходный объект ===");
        printAutoInfo(myCar);

        // Сериализация - запись объекта в файл
        String filename = "auto.ser";
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {

            oos.writeObject(myCar);
            System.out.println("\n✅ Объект успешно сериализован в файл: " + filename);

        } catch (IOException e) {
            System.err.println("❌ Ошибка при сериализации: " + e.getMessage());
            return;
        }

        Auto restoredCar = null;

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filename))) {

            restoredCar = (Auto) ois.readObject();
            System.out.println("✅ Объект успешно десериализован из файла: " + filename);

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("❌ Ошибка при десериализации: " + e.getMessage());
            return;
        }

        System.out.println("\n=== Восстановленный объект ===");
        printAutoInfo(restoredCar);

        // Сравнение объектов по сохраненным значениям
        System.out.println("\n=== Сравнение объектов ===");
        compareAutos(myCar, restoredCar);

}

    private static void compareAutos(Auto original, Auto restored) {
        boolean isEqual = true;

        // Сравниваем марки
        if (!original.getMark().equals(restored.getMark())) {
            System.out.println("❌ Марки не совпадают:");
            System.out.println("  Оригинал: " + original.getMark());
            System.out.println("  Восстановленный: " + restored.getMark());
            isEqual = false;
        }

        // Сравниваем количество моделей
        if (original.getSizeOfModels() != restored.getSizeOfModels()) {
            System.out.println("❌ Количество моделей не совпадает:");
            System.out.println("  Оригинал: " + original.getSizeOfModels());
            System.out.println("  Восстановленный: " + restored.getSizeOfModels());
            isEqual = false;
        }

        // Сравниваем модели и цены
        try {
            String[] originalNames = original.returnAllModelNames();
            float[] originalPrices = original.returnAllModelCoast();
            String[] restoredNames = restored.returnAllModelNames();
            float[] restoredPrices = restored.returnAllModelCoast();

            for (int i = 0; i < originalNames.length; i++) {
                if (!originalNames[i].equals(restoredNames[i])) {
                    System.out.println("❌ Названия моделей не совпадают:");
                    System.out.println("  Оригинал: " + originalNames[i]);
                    System.out.println("  Восстановленный: " + restoredNames[i]);
                    isEqual = false;
                }

                if (Float.compare(originalPrices[i], restoredPrices[i]) != 0) {
                    System.out.println("❌ Цены моделей не совпадают:");
                    System.out.printf("  Оригинал: %.2f руб.%n", originalPrices[i]);
                    System.out.printf("  Восстановленный: %.2f руб.%n", restoredPrices[i]);
                    isEqual = false;
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка при сравнении моделей: " + e.getMessage());
            isEqual = false;
        }

        if (isEqual) {
            System.out.println("✅ Объекты идентичны по содержанию!");
        } else {
            System.out.println("❌ Объекты отличаются!");
        }
    }

    private static void printAutoInfo(Auto auto) {
        try {
            System.out.println("Марка: " + auto.getMark());
            System.out.println("Количество моделей: " + auto.getSizeOfModels());

            String[] names = auto.returnAllModelNames();
            float[] prices = auto.returnAllModelCoast();

            for (int i = 0; i < names.length; i++) {
                System.out.printf("Модель: %s, Цена: %.2f руб.%n", names[i], prices[i]);
            }
        } catch (Exception e) {
            System.err.println("Ошибка при выводе информации: " + e.getMessage());
        }
    }

    private static void initializeAuto() {
        System.out.print("Введите марку автомобиля: ");
        String mark = scanner.nextLine();
        int size = getValidIntInput("Введите начальный размер модельного ряда: ");
        auto = new Auto(mark, size);
    }

    private static void addInitialModels() {
        System.out.println("Опишите эти " + auto.getSizeOfModels() + " модели(-ей): ");
        for (int i = 0; i < auto.getSizeOfModels(); i++) {
            System.out.println("\nДобавьте " + (i+1) + "-ю модель: ");
            addNewModel();
        }
    }

    private static void runMainMenu() {
        while (true) {
            printMenu();
            int choice = getValidIntInput("Выберите действие: ");
            handleMenuChoice(choice);
        }
    }

    private static void printMenu() {
        System.out.println("\nМеню:");
        System.out.println("1. Добавить модель");
        System.out.println("2. Изменить название модели");
        System.out.println("3. Показать все названия моделей");
        System.out.println("4. Показать все цены моделей");
        System.out.println("5. Удалить модель по имени и цене");
        System.out.println("6. Изменить цену модели");
        System.out.println("7. Получить цену модели по имени");
        System.out.println("8. Выход");
    }

    private static void handleMenuChoice(int choice) {
        switch (choice) {
            case 1 -> addNewModel();
            case 2 -> changeModelName();
            case 3 -> showAllModelNames();
            case 4 -> showAllModelPrices();
            case 5 -> deleteModel();
            case 6 -> changeModelPrice();
            case 7 -> getModelPrice();
            case 8 -> exitProgram();
            default -> System.out.println("Неверный выбор!");
        }
    }

    private static void addNewModel() {
        System.out.print("Введите название модели: ");
        String name = scanner.nextLine();
        float price = getValidFloatInput("Введите цену модели: ");
        auto.addModel(name, price);
        System.out.println("Модель добавлена!");
    }

    private static void changeModelName() {
        System.out.print("Введите текущее название модели: ");
        String oldName = scanner.nextLine();
        System.out.print("Введите новое название: ");
        String newName = scanner.nextLine();
        try {
            auto.changeNameOfModel(oldName, newName);
        }catch (NoSuchModelNameException ex){
            System.out.println("No such  model:" + ex.getMessage());
        }

        System.out.println("Название изменено!");
    }

    private static void showAllModelNames() {
        System.out.println("Названия моделей: " +
                Arrays.toString(auto.returnAllModelNames()));
    }

    private static void showAllModelPrices() {
        System.out.println("Цены моделей: " +
                Arrays.toString(auto.returnAllModelCoast()));
    }

    private static void deleteModel() {
        System.out.print("Введите название модели для удаления: ");
        String name = scanner.nextLine();
        float price = getValidFloatInput("Введите цену модели: ");
        try {
            auto.deleteByNameAndCoast(name, price);
        }catch (NoSuchModelNameException ex){
            System.out.println("No such  model:" + ex.getMessage());
        }
        System.out.println("Модель удалена!");
    }

    private static void changeModelPrice() {
        System.out.print("Введите название модели: ");
        String name = scanner.nextLine();
        float newPrice = getValidFloatInput("Введите новую цену: ");
        try {
            auto.changeCostByName(name, newPrice);
        } catch (ModelPriceOutOfBoundsException ex) {
            System.err.println("Ошибка цены: " + ex.getMessage());
            System.err.println("Введите положительное значение цены!");
        } catch (NoSuchModelNameException e) {
            System.err.println("Ошибка модели: " + e.getMessage());
            System.err.println("Проверьте правильность названия модели!");
        }

        System.out.println("Цена изменена!");
    }

    private static void getModelPrice() {
        System.out.print("Введите название модели: ");
        String name = scanner.nextLine();
        float price = 0;
        try {
        price = auto.getCoastByName(name);
        }catch (NoSuchModelNameException ex){
            System.out.println("No such  model:" + ex.getMessage());
        }

        if (price != 0) {
            System.out.println("Цена модели: " + price);
        }
    }

    private static void exitProgram() {
        System.out.println("Выход из программы...");
        scanner.close();
        System.exit(0);
    }

    private static int getValidIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Ошибка! Введите целое число.");
            } finally {
                scanner.nextLine();
            }
        }
    }

    private static float getValidFloatInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return scanner.nextFloat();
            } catch (InputMismatchException ex) {
                System.out.println("Ошибка! Введите число.");
            } finally {
                scanner.nextLine();
            }
        }
    }

    public static void checkIOOperations() {

        Transport myCar = new Auto("Lada", 2);
        myCar.addModel("Granta", 500000f);
        myCar.addModel("Vesta", 1200000f);

        System.out.println("=== Тестирование записи/чтения в файлы ===");

        try (FileOutputStream fos = new FileOutputStream("vehicle_binary.dat")) {
            TransportUtils.outputTransport(myCar, fos);
            System.out.println("Данные записаны в бинарный файл");
        } catch (IOException e) {
            System.err.println("Ошибка записи: " + e.getMessage());
        }

        try (FileInputStream fis = new FileInputStream("vehicle_binary.dat")) {
            Transport restoredCar = TransportUtils.inputTransport(fis);
            System.out.println("Прочитано из бинарного файла: " + restoredCar.getMark());
        } catch (IOException e) {
            System.err.println("Ошибка чтения: " + e.getMessage());
        }

        try (FileWriter fw = new FileWriter("vehicle_text.txt")) {

            fw.write(myCar.getMark() + "\n");
            fw.write(myCar.getSizeOfModels() + "\n");
            String[] names = myCar.returnAllModelNames();
            float[] prices = myCar.returnAllModelCoast();
            for (int i = 0; i < names.length; i++) {
                fw.write(names[i] + " " + prices[i] + "\n");
            }
            System.out.println("Данные записаны в текстовый файл");
        } catch (IOException e) {
            System.err.println("Ошибка записи: " + e.getMessage());
        }

        try (FileReader fr = new FileReader("vehicle_text.txt")) {
            Transport restoredCar = TransportUtils.readTransport(fr);
            System.out.println("Прочитано из текстового файла: " + restoredCar.getMark());
        } catch (IOException e) {
            System.err.println("Ошибка чтения: " + e.getMessage());
        }

        System.out.println("\n=== Тестирование с System.in/System.out ===");

        try {
            System.out.println("Введите данные транспортного средства:");
            System.out.println("Формат: марка, количество моделей, пары 'модель цена'");

            Transport fromConsole = TransportUtils.readTransport(
                    new InputStreamReader(System.in));
            System.out.println("Получены данные: " + fromConsole.getMark());

        } catch (IOException e) {
            System.err.println("Ошибка чтения из консоли: " + e.getMessage());
        }

        try {
            System.out.println("\nВывод данных в консоль:");
            TransportUtils.outputTransport(myCar, System.out);
        } catch (IOException e) {
            System.err.println("Ошибка вывода: " + e.getMessage());
        }
    }
}