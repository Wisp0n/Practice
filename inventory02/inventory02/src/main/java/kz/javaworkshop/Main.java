package kz.javaworkshop;

import java.util.Scanner;

public final class Main {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {

        Inventory inventory = new Inventory(4, 6000);

        Item sword = new Item("Меч", "weapon", 2500, 100);
        Item shield = new Item("Щит", "armor", 3000, 80);
        Item potion = new Item("Зелье", "potion", 500, 20);

        System.out.println("Начальные предметы варианта:");
        System.out.println(sword);
        System.out.println(shield);
        System.out.println(potion);
        System.out.println();

        inventory.add(sword);
        inventory.add(shield);
        inventory.add(potion);

        demonstrateIndependence();

        boolean running = true;
        while (running) {
            printMenu();
            String choice = SCANNER.nextLine().strip();
            switch (choice) {
                case "1" -> handleAdd(inventory);
                case "2" -> handleShow(inventory);
                case "3" -> handleRemove(inventory);
                case "4" -> handleUsePotion(inventory);
                case "5" -> handleSummary(inventory);
                case "6" -> handleSearch(inventory);
                case "0" -> running = false;
                default -> System.out.println("Неизвестный пункт меню");
            }
            System.out.println();
        }

        System.out.println("Выход из программы.");
    }

    private static void printMenu() {
        System.out.println("1 добавить, 2 показать, 3 удалить, 4 использовать зелье, "
                + "5 сводка, 6 поиск по имени, 0 выйти");
        System.out.print("> ");
    }

    private static void handleAdd(Inventory inventory) {
        System.out.print("Имя предмета: ");
        String name = SCANNER.nextLine();

        System.out.print("Тип (weapon/armor/potion): ");
        String type = SCANNER.nextLine().strip();

        Integer grams = readInt("Масса, г: ");
        if (grams == null) return;

        Integer value = readInt("Стоимость, монет: ");
        if (value == null) return;

        try {
            Item item = new Item(name, type, grams, value);
            boolean added = inventory.add(item);
            System.out.println(added ? "Добавлено" : "Нет места или массы");
        } catch (IllegalArgumentException ex) {
            System.out.println("Данные предмета: " + ex.getMessage());
        }
    }

    private static void handleShow(Inventory inventory) {
        int size = inventory.size();
        if (size == 0) {
            System.out.println("Инвентарь пуст");
            return;
        }
        for (int i = 0; i < size; i++) {
            Item item = inventory.get(i);
            System.out.println((i + 1) + ". " + item);
        }
    }

    private static void handleRemove(Inventory inventory) {
        Integer number = readInt("Номер предмета для удаления: ");
        if (number == null) return;
        int index = number - 1;
        Item removed = inventory.remove(index);
        if (removed == null) {
            System.out.println("Неверный номер");
        } else {
            System.out.println("Удалено: " + removed);
        }
    }

    private static void handleUsePotion(Inventory inventory) {
        Integer number = readInt("Номер предмета для использования: ");
        if (number == null) return;
        int index = number - 1;
        Item item = inventory.get(index);
        if (item == null) {
            System.out.println("Неверный номер");
            return;
        }
        if (item.getType().equals("potion")) {
            inventory.remove(index);
            System.out.println("Зелье использовано");
        } else {
            System.out.println("Этот предмет нельзя использовать как зелье");
        }
    }

    private static void handleSummary(Inventory inventory) {
        System.out.println("Ячейки: " + inventory.size() + "/" + inventory.capacitySlots()
                + "; масса: " + inventory.totalWeightGrams() + "/" + inventory.maxWeightGrams()
                + "; стоимость: " + inventory.totalValue());
    }

    private static void handleSearch(Inventory inventory) {
        System.out.print("Подстрока имени: ");
        String query = SCANNER.nextLine();
        Item[] found = inventory.findByName(query);
        if (found.length == 0) {
            System.out.println("Ничего не найдено");
            return;
        }
        for (Item item : found) {
            System.out.println(item);
        }
    }

    private static void demonstrateIndependence() {
        Inventory first = new Inventory(2, 1000);
        Inventory second = new Inventory(2, 1000);
        first.add(new Item("Кинжал", "weapon", 500, 10));

        Item[] snapshot = first.snapshot();
        snapshot[0] = null;

        assert first.size() == 1 : "Изменение снимка не должно влиять на инвентарь";
        assert second.size() == 0 : "Второй инвентарь должен остаться пустым";
    }

    private static Integer readInt(String prompt) {
        System.out.print(prompt);
        String raw = SCANNER.nextLine().strip();
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException ex) {
            System.out.println("Нужно ввести целое число");
            return null;
        }
    }
}