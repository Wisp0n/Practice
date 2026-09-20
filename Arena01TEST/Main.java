import java.util.Locale;
import java.util.Scanner;

public class Main {

    static final int MAX_HP = 40;
    static final int HEAL_AMOUNT = 8;
    static final int XP_REWARD = 20;
    static final int HERO_POWER = 10;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String[] enemyNames = {"Крыса", "Скелет", "Страж"};
        int[] enemyHp = {12, 18, 24};
        int[] enemyDamage = {4, 6, 8};

        System.out.println("=== Арена новичка · Вариант А (Руины) ===");

        String name = readName(scanner);
        String heroClass = readClass(scanner);
        int level = readLevel(scanner);
        boolean hasShield = readShield(scanner);

        String refusalReason = checkAdmission(level, hasShield, heroClass);
        if (refusalReason != null) {
            System.out.println("Отказ: " + refusalReason);
            scanner.close();
            return;
        }
        System.out.println("Допущен: " + name + ", " + heroClass);

        int heroHp = MAX_HP;
        int xp = 0;
        int wins = 0;
        boolean gameInterrupted = false;

        for (int i = 0; i < enemyNames.length && !gameInterrupted && heroHp > 0; i++) {
            System.out.println();
            System.out.println("--- Раунд " + (i + 1) + ": " + enemyNames[i] + " ---");

            int currentEnemyHp = enemyHp[i];
            boolean healUsed = false;
            boolean enemyDefeated = false;

            while (!gameInterrupted && !enemyDefeated && heroHp > 0) {
                System.out.println("HP героя: " + heroHp + " | HP " + enemyNames[i] + ": " + currentEnemyHp);
                int command = readCommand(scanner);

                if (command == 0) {
                    gameInterrupted = true;
                } else if (command == 1) {
                    currentEnemyHp = applyDamage(currentEnemyHp, HERO_POWER);
                    if (currentEnemyHp == 0) {
                        enemyDefeated = true;
                        xp += XP_REWARD;
                        System.out.println(enemyNames[i] + " побеждён! +" + XP_REWARD + " XP");
                    } else {
                        heroHp = applyDamage(heroHp, enemyDamage[i]);
                    }
                } else if (command == 2) {
                    if (healUsed) {
                        System.out.println("Лечение уже использовано в этом бою.");
                    } else {
                        heroHp = applyHeal(heroHp);
                        healUsed = true;
                        System.out.println("Герой лечится. HP героя: " + heroHp);
                        if (heroHp > 0) {
                            heroHp = applyDamage(heroHp, enemyDamage[i]);
                        }
                    }
                } else {
                    System.out.println("Неизвестная команда.");
                }
            }

            if (!gameInterrupted && heroHp > 0) {
                wins++;
                System.out.println("Раунд " + (i + 1) + ": победа, HP героя " + heroHp + ", XP " + xp);
            }
        }

        scanner.close();
        printSummary(name, wins, xp, heroHp, gameInterrupted);
    }


    static String readName(Scanner scanner) {
        while (true) {
            System.out.print("Имя героя: ");
            String input = scanner.nextLine().strip();
            if (input.length() >= 1 && input.length() <= 30) {
                return input;
            }
            System.out.println("Имя должно содержать от 1 до 30 символов. Повторите ввод.");
        }
    }

    static String readClass(Scanner scanner) {
        while (true) {
            System.out.print("Класс (воин/маг/лучник): ");
            String input = scanner.nextLine().strip().toLowerCase(Locale.ROOT);
            if (input.equals("воин") || input.equals("маг") || input.equals("лучник")) {
                return input;
            }
            System.out.println("Неизвестный класс. Повторите ввод.");
        }
    }

    static int readLevel(Scanner scanner) {
        while (true) {
            System.out.print("Уровень (1-80): ");
            if (scanner.hasNextInt()) {
                int level = scanner.nextInt();
                scanner.nextLine();
                if (level >= 1 && level <= 80) {
                    return level;
                }
                System.out.println("Уровень должен быть от 1 до 80. Повторите ввод.");
            } else {
                scanner.nextLine();
                System.out.println("Введите целое число. Повторите ввод.");
            }
        }
    }

    static boolean readShield(Scanner scanner) {
        while (true) {
            System.out.print("Щит есть? (да/нет): ");
            String input = scanner.nextLine().strip().toLowerCase(Locale.ROOT);
            if (input.equals("да")) {
                return true;
            } else if (input.equals("нет")) {
                return false;
            }
            System.out.println("Ответьте 'да' или 'нет'.");
        }
    }

    static int readCommand(Scanner scanner) {
        while (true) {
            System.out.print("Команда (1-атака, 2-лечение, 0-выход): ");
            if (scanner.hasNextInt()) {
                int command = scanner.nextInt();
                scanner.nextLine();
                return command;
            } else if (scanner.hasNext()) {
                scanner.nextLine();
                System.out.println("Введите число команды.");
            } else {
                System.out.println("Ввод исчерпан. Завершение игры.");
                return 0;
            }
        }
    }


    static String checkAdmission(int level, boolean hasShield, String heroClass) {
        if (level < 10) {
            return "недостаточный уровень";
        }
        if (!hasShield && !heroClass.equals("маг")) {
            return "отсутствие нужной защиты";
        }
        return null;
    }

    static int applyDamage(int currentHp, int damage) {
        return Math.max(0, currentHp - damage);
    }

    static int applyHeal(int currentHp) {
        return Math.min(MAX_HP, currentHp + HEAL_AMOUNT);
    }

    static void printSummary(String name, int wins, int xp, int heroHp, boolean interrupted) {
        System.out.println();
        System.out.println("=== Итоги ===");
        System.out.println("Герой: " + name);
        System.out.println("Вариант: А (Руины)");
        System.out.println("Побед: " + wins);
        System.out.println("XP: " + xp);
        System.out.println("HP героя: " + heroHp);

        if (interrupted) {
            System.out.println("Итог: Игра прервана");
        } else if (heroHp == 0) {
            System.out.println("Итог: Поражение");
        } else if (wins == 3) {
            System.out.println("Итог: Арена пройдена, побед 3");
        }
    }
}