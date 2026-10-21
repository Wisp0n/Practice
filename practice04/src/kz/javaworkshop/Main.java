package kz.javaworkshop;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        System.out.println("=== Шаг 6. Контрольный проход (вариант А) ===");
        runControlScenario();

        System.out.println();
        System.out.println("=== Шаг 7. Проверка границ ===");
        scenarioUseItemOnEmptyBackpack();
        scenarioPotionAtFullHealth();
        scenarioHitWeakerThanDefence();
        scenarioHitThousand();
        scenarioHealFromZero();
        scenarioUseArmor();
        scenarioInvalidSubclassValues();
        scenarioInvalidHeroAndHit();
        scenarioFoodBlankName();
    }


    private static void runControlScenario() {
        Hero aliya = new Hero("Алия", 4, 6000, 120);
        Hero daniyar = new Hero("Данияр", 2, 6000, 100);

        pickUpAndReport(aliya, new Weapon("Меч", 2500, 100, 12));
        pickUpAndReport(aliya, new Armor("Щит", 3000, 80, 8));
        pickUpAndReport(aliya, new Potion("Зелье", 500, 20, 30));
        printContents(aliya);
        printHero(aliya);

        hitAndReport(aliya, 50);
        printHero(aliya);

        useAndReport(aliya, 0);

        useAndReport(aliya, 2);
        useAndReport(aliya, 2);
        printHero(aliya);

        pickUpAndReport(daniyar, new Weapon("Факел", 200, 10, 3));
        pickUpAndReport(daniyar, new Food("Хлеб", 300, 5, 10));
        printContents(daniyar);
        hitAndReport(daniyar, 5);
        useAndReport(daniyar, 1);
        printHero(daniyar);
    }


    private static void scenarioUseItemOnEmptyBackpack() {
        System.out.println("-- useItem(0) и useItem(-1) при пустом рюкзаке --");
        Hero hero = new Hero("Герой", 4, 6000, 100);
        System.out.println("useItem(0): " + hero.useItem(0));
        System.out.println("useItem(-1): " + hero.useItem(-1));
        printHero(hero);
    }

    private static void scenarioPotionAtFullHealth() {
        System.out.println("-- Зелье при полном здоровье --");
        Hero hero = new Hero("Герой", 4, 6000, 100);
        hero.pickUp(new Potion("Зелье", 500, 20, 30));
        System.out.println("useItem(0): " + hero.useItem(0));
        printHero(hero);
    }

    private static void scenarioHitWeakerThanDefence() {
        System.out.println("-- Удар 5 при защите 8 --");
        Hero hero = new Hero("Герой", 4, 6000, 100);
        hero.pickUp(new Armor("Щит", 3000, 80, 8));
        System.out.println("Потеря HP: " + hero.takeHit(5));
        printHero(hero);
    }

    private static void scenarioHitThousand() {
        System.out.println("-- Удар 1000 --");
        Hero hero = new Hero("Герой", 4, 6000, 100);
        System.out.println("Потеря HP: " + hero.takeHit(1000));
        printHero(hero);
    }

    private static void scenarioHealFromZero() {
        System.out.println("-- Лечение героя с нулевым здоровьем --");
        Hero hero = new Hero("Герой", 4, 6000, 100);
        hero.takeHit(1000);
        System.out.println("HP до лечения: " + hero.getHp());
        hero.heal(30);
        System.out.println("HP после лечения: " + hero.getHp());
    }

    private static void scenarioUseArmor() {
        System.out.println("-- useItem() для брони --");
        Hero hero = new Hero("Герой", 4, 6000, 100);
        hero.pickUp(new Armor("Щит", 3000, 80, 8));
        Item[] before = hero.items();
        System.out.println("useItem(0): " + hero.useItem(0));
        Item[] after = hero.items();
        System.out.println("Снимок совпадает с прежним: " + sameReferences(before, after));
        printHero(hero);
    }

    private static void scenarioInvalidSubclassValues() {
        System.out.println("-- Значения подклассов 0 и 101 --");
        expectFailure("Weapon урон 0", () -> new Weapon("Меч", 2500, 100, 0));
        expectFailure("Weapon урон 101", () -> new Weapon("Меч", 2500, 100, 101));
        expectFailure("Armor защита 0", () -> new Armor("Щит", 3000, 80, 0));
        expectFailure("Armor защита 101", () -> new Armor("Щит", 3000, 80, 101));
        expectFailure("Potion лечение 0", () -> new Potion("Зелье", 500, 20, 0));
        expectFailure("Potion лечение 101", () -> new Potion("Зелье", 500, 20, 101));
        expectFailure("Food лечение 0", () -> new Food("Хлеб", 300, 5, 0));
        expectFailure("Food лечение 101", () -> new Food("Хлеб", 300, 5, 101));
    }

    private static void scenarioInvalidHeroAndHit() {
        System.out.println("-- maxHp 0 / 1001 и неверный удар --");
        expectFailure("maxHp 0", () -> new Hero("Герой", 4, 6000, 0));
        expectFailure("maxHp 1001", () -> new Hero("Герой", 4, 6000, 1001));
        Hero hero = new Hero("Герой", 4, 6000, 100);
        expectFailure("удар -1", () -> hero.takeHit(-1));
        expectFailure("удар 1001", () -> hero.takeHit(1001));
    }

    private static void scenarioFoodBlankName() {
        System.out.println("-- Food с именем из пробелов --");
        expectFailure("имя из пробелов", () -> new Food("   ", 300, 5, 10));
    }


    private static void pickUpAndReport(Hero hero, Item item) {
        boolean result = hero.pickUp(item);
        System.out.println(hero.getName() + " подбирает " + item.getName() + ": " + result);
    }

    private static void hitAndReport(Hero hero, int damage) {
        int loss = hero.takeHit(damage);
        System.out.println(hero.getName() + " получает удар " + damage + ", потеряно HP: " + loss);
    }

    private static void useAndReport(Hero hero, int index) {
        boolean result = hero.useItem(index);
        System.out.println(hero.getName() + " использует предмет " + index + ": " + result);
    }

    private static void printContents(Hero hero) {
        Item[] items = hero.items();
        for (int i = 0; i < items.length; i++) {
            System.out.println(" " + i + ". " + items[i].describe());
        }
    }

    private static void printHero(Hero hero) {
        int count = hero.itemCount();
        System.out.println(hero.getName() + ": HP " + hero.getHp() + "/" + hero.getMaxHp()
                + ", атака " + hero.attackPower()
                + ", защита " + hero.defencePower()
                + ", " + count + " " + itemsWord(count)
                + ", " + hero.totalWeightGrams() + " г"
                + ", " + hero.totalValue() + " монет");
    }

    private static String itemsWord(int n) {
        int lastTwo = n % 100;
        int last = n % 10;
        if (lastTwo >= 11 && lastTwo <= 14) {
            return "предметов";
        }
        if (last == 1) {
            return "предмет";
        }
        if (last >= 2 && last <= 4) {
            return "предмета";
        }
        return "предметов";
    }

    private static boolean sameReferences(Item[] a, Item[] b) {
        if (a.length != b.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i]) {
                return false;
            }
        }
        return true;
    }

    private static void expectFailure(String label, Runnable action) {
        try {
            action.run();
            System.out.println("НЕОЖИДАННО без ошибки (" + label + ")");
        } catch (IllegalArgumentException ex) {
            System.out.println("Отклонено (" + label + "): " + ex.getMessage());
        }
    }
}
