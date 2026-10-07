package kz.javaworkshop;

import java.util.Arrays;


public final class Inventory {

    private final Item[] items;
    private int size;
    private final int maxWeightGrams;

    public Inventory(int capacity, int maxWeightGrams) {
        if (capacity < 1 || capacity > 20) {
            throw new IllegalArgumentException("Число ячеек должно быть от 1 до 20");
        }
        if (maxWeightGrams < 1 || maxWeightGrams > 100000) {
            throw new IllegalArgumentException("Лимит массы должен быть от 1 до 100000 граммов");
        }
        this.items = new Item[capacity];
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

    public Item[] findByName(String query) {
        if (query == null) {
            return new Item[0];
        }
        String needle = query.strip().toLowerCase();
        Item[] buffer = new Item[size];
        int found = 0;
        for (int i = 0; i < size; i++) {
            if (items[i].getName().toLowerCase().contains(needle)) {
                buffer[found] = items[i];
                found++;
            }
        }
        return Arrays.copyOf(buffer, found);
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return items.length;
    }

    public int getMaxWeightGrams() {
        return maxWeightGrams;
    }

    public int totalWeightGrams() {
        int total = 0;
        for (int i = 0; i < size; i++) {
            total += items[i].getWeightGrams();
        }
        return total;
    }

    public long totalValue() {
        long total = 0L;
        for (int i = 0; i < size; i++) {
            total += items[i].getValue();
        }
        return total;
    }

    public Item[] snapshot() {
        return Arrays.copyOf(items, size);
    }
}