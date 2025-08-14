package org.example;

import org.example.mainAuto.Auto;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите марку автомобиля: ");
        String mark = scanner.nextLine();
        System.out.print("Введите начальный размер модельного ряда: ");
        int size = scanner.nextInt();
        scanner.nextLine(); // Очистка буфера
        Auto auto = new Auto(mark, size);

        System.out.print("Опишите эти" + size + "модели(-ей): ");
        for (int i = 0; i < size; i++){
            System.out.print("Добавьте" + i+1 + "модель: ");
            System.out.print("Введите название модели: ");
            String name = scanner.nextLine();
            System.out.print("Введите цену модели: ");
            float price = scanner.nextFloat();
            scanner.nextLine();
            auto.addModel(name, price);
        }

        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Добавить модель");
            System.out.println("2. Изменить название модели");
            System.out.println("3. Показать все названия моделей");
            System.out.println("4. Показать все цены моделей");
            System.out.println("5. Удалить модель по имени и цене");
            System.out.println("6. Изменить цену модели");
            System.out.println("7. Получить цену модели по имени");
            System.out.println("8. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера

            switch (choice) {
                case 1:
                    System.out.print("Введите название модели: ");
                    String name = scanner.nextLine();
                    System.out.print("Введите цену модели: ");
                    float price = scanner.nextFloat();
                    scanner.nextLine();
                    auto.addModel(name, price);
                    System.out.println("Модель добавлена!");
                    break;

                case 2:
                    System.out.print("Введите текущее название модели: ");
                    String oldName = scanner.nextLine();
                    System.out.print("Введите новое название: ");
                    String newName = scanner.nextLine();
                    auto.changeNameOfModel(oldName, newName);
                    System.out.println("Название изменено!");
                    break;

                case 3:
                    System.out.println("Названия моделей: " +
                            Arrays.toString(auto.returnAllModelNames()));
                    break;

                case 4:
                    System.out.println("Цены моделей: " +
                            Arrays.toString(auto.returnAllModelCoast()));
                    break;

                case 5:
                    System.out.print("Введите название модели для удаления: ");
                    String delName = scanner.nextLine();
                    System.out.print("Введите цену модели: ");
                    float delPrice = scanner.nextFloat();
                    scanner.nextLine();
                    auto.deleteByNameAndCoast(delName, delPrice);
                    System.out.println("Модель удалена!");
                    break;

                case 6:
                    System.out.print("Введите название модели: ");
                    String changeName = scanner.nextLine();
                    System.out.print("Введите новую цену: ");
                    float newPrice = scanner.nextFloat();
                    scanner.nextLine();
                    auto.changeCostByName(changeName, newPrice);
                    System.out.println("Цена изменена!");
                    break;

                case 7:
                    System.out.print("Введите название модели: ");
                    String getName = scanner.nextLine();
                    float modelPrice = auto.getCoastByName(getName);
                    if (modelPrice != 0) {
                        System.out.println("Цена модели: " + modelPrice);
                    }
                    break;

                case 8:
                    System.out.println("Выход из программы...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Неверный выбор!");
            }
        }
    }

}