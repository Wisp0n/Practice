package kz.javaworkshop;

public abstract class Item {

    private final String name;
    private final int weightGrams;
    private final int value;

    protected Item(String name, int weightGrams, int value) {
        if (name == null || name.strip().isEmpty()) {
            throw new IllegalArgumentException("Имя предмета пусто");
        }
        String normalizedName = name.strip();
        if (normalizedName.length() > 40) {
            throw new IllegalArgumentException("Имя предмета длиннее 40 символов");
        }
        if (weightGrams < 1 || weightGrams > 100000) {
            throw new IllegalArgumentException("Масса должна быть от 1 до 100000 граммов");
        }
        if (value < 0 || value > 1000000) {
            throw new IllegalArgumentException("Стоимость должна быть от 0 до 1000000 монет");
        }
        this.name = normalizedName;
        this.weightGrams = weightGrams;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getWeightGrams() {
        return weightGrams;
    }

    public int getValue() {
        return value;
    }

    public abstract String describe();

    public int attackBonus() {
        return 0;
    }

    public int defenceBonus() {
        return 0;
    }
}
