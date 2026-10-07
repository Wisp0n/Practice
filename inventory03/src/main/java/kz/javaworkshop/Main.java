package kz.javaworkshop;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Шаг 4. Контрольный проход (вариант А) ===");
        runMainScenario();

        System.out.println();
        System.out.println("=== Шаг 5. Проверка ссылок и границ ===");
        scenarioSecondHeroStaysEmpty();
        scenarioPickUpNull();
        scenarioExactWeightAccepted();
        scenarioOverweightRejected();
        scenarioSnapshotMutationIsSafe();
        scenarioTwoSnapshotsAreIndependentArrays();
        scenarioAliasVsNewHero();
        scenarioSharedItemBetweenTwoHeroes();
        scenarioInvalidHeroNames();
        scenarioBoundaryHeroNames();
        scenarioInvalidCapacityOrWeightLimit();
    }

    // ---------- Шаг 4 ----------

    private static void runMainScenario() {
        Hero aliya = new Hero("Алия", 4, 6000);
        Hero daniyar = new Hero("Данияр", 2, 6000);

        printHero(aliya);
        printHero(daniyar);

        Item sword = new Item("Меч", "weapon", 2500, 100);
        Item shield = new Item("Щит", "armor", 3000, 80);
        Item potion = new Item("Зелье", "potion", 500, 20);

        pickUpAndReport(aliya, "Меч", sword);
        pickUpAndReport(aliya, "Щит", shield);
        pickUpAndReport(aliya, "Зелье", potion);
        printHero(aliya);

        Item feather = new Item("Перо", "potion", 1, 0);
        pickUpAndReport(aliya, "Перо", feather);
        printHero(daniyar);

        Item map = new Item("Карту", "armor", 100, 5);
        Item torch = new Item("Факел", "weapon", 200, 10);
        pickUpAndReport(daniyar, "Карту", map);
        pickUpAndReport(daniyar, "Факел", torch);
        pickUpAndReport(daniyar, "Перо", feather);

        printHero(aliya);
        printHero(daniyar);
    }

    // ---------- Шаг 5 ----------

    private static void scenarioSecondHeroStaysEmpty() {
        System.out.println("-- Два новых героя, подбор только первым --");
        Hero first = new Hero("Герой1", 4, 6000);
        Hero second = new Hero("Герой2", 4, 6000);
        pickUpAndReport(first, "Меч", new Item("Меч", "weapon", 2500, 100));
        printHero(first);
        printHero(second);
    }

    private static void scenarioPickUpNull() {
        System.out.println("-- pickUp(null) --");
        Hero hero = new Hero("Герой", 4, 6000);
        boolean result = hero.pickUp(null);
        System.out.println("pickUp(null): " + result);
        printHero(hero);
    }

    private static void scenarioExactWeightAccepted() {
        System.out.println("-- 1 ячейка, лимит 100 г; предмет ровно 100 г --");
        Hero hero = new Hero("Герой", 1, 100);
        boolean result = hero.pickUp(new Item("Камень", "weapon", 100, 1));
        System.out.println("Подбор ровно по лимиту: " + result);
        printHero(hero);
    }

    private static void scenarioOverweightRejected() {
        System.out.println("-- 2 ячейки, лимит 100 г; предмет 101 г --");
        Hero hero = new Hero("Герой", 2, 100);
        boolean result = hero.pickUp(new Item("Камень", "weapon", 101, 1));
        System.out.println("Подбор сверх лимита: " + result);
        printHero(hero);
    }

    private static void scenarioSnapshotMutationIsSafe() {
        System.out.println("-- Изменение снимка не влияет на рюкзак --");
        Hero hero = new Hero("Герой", 4, 6000);
        hero.pickUp(new Item("Меч", "weapon", 2500, 100));

        Item[] snapshot = hero.items();
        snapshot[0] = null;
        System.out.println("Изменили snapshot[0] на null у локальной копии.");

        Item[] freshSnapshot = hero.items();
        System.out.println("Новый снимок после изменения старого: " + Arrays.toString(freshSnapshot));
        printHero(hero);
    }

    private static void scenarioTwoSnapshotsAreIndependentArrays() {
        System.out.println("-- Два снимка без изменения героя --");
        Hero hero = new Hero("Герой", 4, 6000);
        hero.pickUp(new Item("Меч", "weapon", 2500, 100));
        hero.pickUp(new Item("Щит", "armor", 3000, 80));

        Item[] snapshotA = hero.items();
        Item[] snapshotB = hero.items();
        System.out.println("Разные массивы: " + (snapshotA != snapshotB));
        boolean sameOrderAndRefs = snapshotA.length == snapshotB.length;
        for (int i = 0; i < snapshotA.length && sameOrderAndRefs; i++) {
            sameOrderAndRefs = snapshotA[i] == snapshotB[i];
        }
        System.out.println("Те же ссылки и порядок: " + sameOrderAndRefs);
    }

    private static void scenarioAliasVsNewHero() {
        System.out.println("-- alias = first против нового героя --");
        Hero first = new Hero("Герой", 4, 6000);
        Hero alias = first;
        alias.pickUp(new Item("Меч", "weapon", 2500, 100));
        System.out.println("Изменение видно через first: " + (first.itemCount() == 1));
    }

    private static void scenarioSharedItemBetweenTwoHeroes() {
        System.out.println("-- Общий неизменяемый предмет у двух героев --");
        Item sharedSword = new Item("Общий меч", "weapon", 2500, 100);
        Hero first = new Hero("Герой1", 4, 6000);
        Hero second = new Hero("Герой2", 4, 6000);

        boolean firstResult = first.pickUp(sharedSword);
        boolean secondResult = second.pickUp(sharedSword);
        System.out.println("Оба подбора успешны: " + (firstResult && secondResult));

        Item[] snapshot1 = first.items();
        Item[] snapshot2 = second.items();
        System.out.println("Снимки — разные массивы: " + (snapshot1 != snapshot2));
        System.out.println("Ссылка на предмет совпадает: " + (snapshot1[0] == snapshot2[0]));
    }

    private static void scenarioInvalidHeroNames() {
        System.out.println("-- Недопустимое имя героя --");
        tryCreateInvalidHero(null, 4, 6000, "null");
        tryCreateInvalidHero("   ", 4, 6000, "из пробелов");
        tryCreateInvalidHero("Х".repeat(41), 4, 6000, "длиной 41");
    }

    private static void scenarioBoundaryHeroNames() {
        System.out.println("-- Граничная длина имени героя --");
        Hero shortName = new Hero("Х", 4, 6000);
        Hero longName = new Hero("Х".repeat(40), 4, 6000);
        System.out.println("Имя длиной 1 принято: " + shortName.getName().length());
        System.out.println("Имя длиной 40 принято: " + longName.getName().length());
    }

    private static void scenarioInvalidCapacityOrWeightLimit() {
        System.out.println("-- Недопустимая ёмкость или лимит массы --");
        tryCreateInvalidHero("Герой", 0, 6000, "ёмкость 0");
        tryCreateInvalidHero("Герой", 21, 6000, "ёмкость 21");
        tryCreateInvalidHero("Герой", 4, 0, "лимит 0");
        tryCreateInvalidHero("Герой", 4, 100001, "лимит 100001");
    }

    private static void tryCreateInvalidHero(String name, int capacity, int maxWeightGrams, String caseLabel) {
        try {
            new Hero(name, capacity, maxWeightGrams);
            System.out.println("Неожиданно создан герой (" + caseLabel + ")");
        } catch (IllegalArgumentException ex) {
            System.out.println("Отклонено (" + caseLabel + "): " + ex.getMessage());
        }
    }


    private static void pickUpAndReport(Hero hero, String itemLabel, Item item) {
        boolean result = hero.pickUp(item);
        System.out.println(hero.getName() + " подбирает " + itemLabel + ": " + result);
    }

    private static void printHero(Hero hero) {
        System.out.println(hero.getName() + ": " + hero.itemCount() + " предметов, "
                + hero.totalWeightGrams() + " г, " + hero.totalValue() + " монет");
    }
}