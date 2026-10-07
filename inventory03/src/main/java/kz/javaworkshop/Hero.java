package kz.javaworkshop;

public final class Hero {

    private final String name;
    private final Inventory inventory;

    public Hero(String name, int capacity, int maxWeightGrams) {
        if (name == null || name.strip().isEmpty()) {
            throw new IllegalArgumentException("Имя героя пусто");
        }
        String normalizedName = name.strip();
        if (normalizedName.length() > 40) {
            throw new IllegalArgumentException("Имя героя длиннее 40 символов");
        }

        this.name = normalizedName;
        this.inventory = new Inventory(capacity, maxWeightGrams); 
    }

    public boolean pickUp(Item item) {
        return inventory.add(item);
    }

    public int itemCount() {
        return inventory.size();
    }

    public int totalWeightGrams() {
        return inventory.totalWeightGrams();
    }

    public long totalValue() {
        return inventory.totalValue();
    }

    public Item[] items() {
        return inventory.snapshot();
    }

    public String getName() {
        return name;
    }
}