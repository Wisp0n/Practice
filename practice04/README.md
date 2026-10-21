# Практическая работа №4 — Наследование, интерфейсы и полиморфизм

**Вариант:** А · Воин (лимит массы 6000 г, здоровье Алии 120)

## Структура
- `src/kz/javaworkshop/Item.java` — абстрактный предмет (имя, масса, стоимость, `describe()`, `attackBonus()`, `defenceBonus()`)
- `Weapon`, `Armor`, `Potion`, `Food` — виды предметов; `Usable` — интерфейс «можно использовать» (`Potion`, `Food`)
- `Inventory` — рюкзак (без изменений по сравнению с №3), `Hero` — герой со здоровьем
- `Main` — сценарии шагов 6 и 7

## Сборка и запуск (из папки practice04)
PowerShell:
    javac -encoding UTF-8 -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
    java -cp out kz.javaworkshop.Main

Linux/macOS:
    javac -encoding UTF-8 -d out $(find src -name "*.java")
    java -cp out kz.javaworkshop.Main

Если русский текст искажён: `chcp 65001` перед запуском.

## Фактический вывод контрольного прохода
Вставьте сюда вывод вашего запуска (раздел «Шаг 6»).

## Три проверки из шага 7
1. Удар 1000: здоровье 0, возвращена фактическая потеря 100 — вставьте свой вывод.
2. Зелье при полном здоровье: `true`, зелье удалено, HP = максимум — вставьте свой вывод.
3. `new Item("Камень", 100, 0)` не компилируется: `Item is abstract; cannot be instantiated` — вставьте свой вывод.
