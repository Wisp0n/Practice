package kz.javaworkshop;


public final class Item {

    private static final int NAME_MIN_LENGTH = 1;
    private static final int NAME_MAX_LENGTH = 40;
    private static final int WEIGHT_MIN = 1;
    private static final int WEIGHT_MAX = 100_000;
    private static final int VALUE_MIN = 0;
    private static final int VALUE_MAX = 1_000_000;

    private final String name;
    private final String type;
    private final int weightGrams;
    private final int value;

    public Item(String name, String type, int weightGrams, int value) {
        if (name == null || name.strip().isEmpty()) {
            throw new IllegalArgumentException("Имя предмета пусто");
        }
        String normalizedName = name.strip();
        if (normalizedName.length() > NAME_MAX_LENGTH || normalizedName.length() < NAME_MIN_LENGTH) {
            throw new IllegalArgumentException(
                    "Имя предмета должно быть длиной от " + NAME_MIN_LENGTH + " до " + NAME_MAX_LENGTH + " символов");
        }

        if (type == null || !(type.equals("weapon") || type.equals("armor") || type.equals("potion"))) {
            throw new IllegalArgumentException("Тип предмета должен быть weapon, armor или potion");
        }

        if (weightGrams < WEIGHT_MIN || weightGrams > WEIGHT_MAX) {
            throw new IllegalArgumentException(
                    "Масса предмета должна быть от " + WEIGHT_MIN + " до " + WEIGHT_MAX + " граммов");
        }

        if (value < VALUE_MIN || value > VALUE_MAX) {
            throw new IllegalArgumentException(
                    "Стоимость предмета должна быть от " + VALUE_MIN + " до " + VALUE_MAX + " монет");
        }

        this.name = normalizedName;
        this.type = type;
        this.weightGrams = weightGrams;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getWeightGrams() {
        return weightGrams;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name + " [" + type + "], " + weightGrams + " г, " + value + " монет";
    }
}