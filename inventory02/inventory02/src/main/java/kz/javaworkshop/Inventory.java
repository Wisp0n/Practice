package kz.javaworkshop;

import java.util.Arrays;
import java.util.Locale;


public final class Inventory {

    private static final int SLOTS_MIN = 1;
    private static final int SLOTS_MAX = 20;
    private static final int WEIGHT_LIMIT_MIN = 1;
    private static final int WEIGHT_LIMIT_MAX = 100_000;

    private final Item[] items;
    private int size;
    private final int maxWeightGrams;

    public Inventory(int slots, int maxWeightGrams) {
        if (slots < SLOTS_MIN || slots > SLOTS_MAX) {
            throw new IllegalArgumentException(
                    "Число ячеек должно быть от " + SLOTS_MIN + " до " + SLOTS_MAX);
        }
        if (maxWeightGrams < WEIGHT_LIMIT_MIN || maxWeightGrams > WEIGHT_LIMIT_MAX) {
            throw new IllegalArgumentException(
                    "Лимит массы должен быть от " + WEIGHT_LIMIT_MIN + " до " + WEIGHT_LIMIT_MAX);
        }
        this.items = new Item[slots];
        this.size = 0;
        this.maxWeightGrams = maxWeightGrams;
    }

    
    public boolean add(Item item) {
        if (item == null) {
            return false;
        }
        if (size >= items.length) {
            return false;
        }
        if (totalWeightGrams() + item.getWeightGrams() > maxWeightGrams) {
            return false;
        }
        items[size] = item;
        size++;
        return true;
    }

    
    public Item remove(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Item removed = items[index];
        for (int i = index; i < size - 1; i++) {
            items[i] = items[i + 1];
        }
        items[size - 1] = null;
        size--;
        return removed;
    }

    public Item get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return items[index];
    }

    public int size() {
        return size;
    }

    public int totalWeightGrams() {
        int sum = 0;
        for (int i = 0; i < size; i++) {
            sum += items[i].getWeightGrams();
        }
        return sum;
    }

    public long totalValue() {
        long sum = 0L;
        for (int i = 0; i < size; i++) {
            sum += items[i].getValue();
        }
        return sum;
    }

    public int capacitySlots() {
        return items.length;
    }

    public int maxWeightGrams() {
        return maxWeightGrams;
    }

    
    public Item[] snapshot() {
        return Arrays.copyOf(items, size);
    }

    
    public Item[] findByName(String query) {
        if (query == null || query.isBlank()) {
            return new Item[0];
        }
        String needle = query.strip().toLowerCase(Locale.ROOT);
        Item[] buffer = new Item[size];
        int found = 0;
        for (int i = 0; i < size; i++) {
            if (items[i].getName().toLowerCase(Locale.ROOT).contains(needle)) {
                buffer[found] = items[i];
                found++;
            }
        }
        return Arrays.copyOf(buffer, found);
    }

    
    public boolean transferTo(Inventory target, int index) {
        if (target == null) {
            return false;
        }
        if (index < 0 || index >= size) {
            return false;
        }
        Item item = items[index];
        boolean accepted = target.add(item);
        if (!accepted) {
            return false;
        }
        remove(index);
        return true;
    }
}