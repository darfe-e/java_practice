package org.example;

import org.example.mainAuto.Auto;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Auto auto;

    public static void main(String[] args) {
        initializeAuto();
        addInitialModels();
        runMainMenu();
    }

    // Инициализация автомобиля
    private static void initializeAuto() {
        System.out.print("Введите марку автомобиля: ");
        String mark = scanner.nextLine();
        int size = getValidIntInput("Введите начальный размер модельного ряда: ");
        auto = new Auto(mark, size);
    }

    // Добавление начальных моделей
    private static void addInitialModels() {
        System.out.println("Опишите эти " + auto.getSizeOfModels() + " модели(-ей): ");
        for (int i = 0; i < auto.getSizeOfModels(); i++) {
            System.out.println("\nДобавьте " + (i+1) + "-ю модель: ");
            addNewModel();
        }
    }

    // Главное меню
    private static void runMainMenu() {
        while (true) {
            printMenu();
            int choice = getValidIntInput("Выберите действие: ");
            handleMenuChoice(choice);
        }
    }

    // Вывод меню
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

    // Обработка выбора в меню
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

    // Методы для операций с моделями
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
        auto.changeNameOfModel(oldName, newName);
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
        auto.deleteByNameAndCoast(name, price);
        System.out.println("Модель удалена!");
    }

    private static void changeModelPrice() {
        System.out.print("Введите название модели: ");
        String name = scanner.nextLine();
        float newPrice = getValidFloatInput("Введите новую цену: ");
        auto.changeCostByName(name, newPrice);
        System.out.println("Цена изменена!");
    }

    private static void getModelPrice() {
        System.out.print("Введите название модели: ");
        String name = scanner.nextLine();
        float price = auto.getCoastByName(name);
        if (price != 0) {
            System.out.println("Цена модели: " + price);
        }
    }

    private static void exitProgram() {
        System.out.println("Выход из программы...");
        scanner.close();
        System.exit(0);
    }

    // Вспомогательные методы для ввода
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
}