package kz.javaworkshop;

public final class Hero {

    private final String name;
    private final Inventory inventory;
    private final int maxHp;
    private int hp;

    public Hero(String name, int capacity, int maxWeightGrams, int maxHp) {
        if (name == null || name.strip().isEmpty()) {
            throw new IllegalArgumentException("Имя героя пусто");
        }
        String normalizedName = name.strip();
        if (normalizedName.length() > 40) {
            throw new IllegalArgumentException("Имя героя длиннее 40 символов");
        }
        if (maxHp < 1 || maxHp > 1000) {
            throw new IllegalArgumentException("Максимальное здоровье должно быть от 1 до 1000");
        }
        this.name = normalizedName;
        this.inventory = new Inventory(capacity, maxWeightGrams);
        this.maxHp = maxHp;
        this.hp = maxHp;
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

    public int attackPower() {
        int sum = 0;
        for (Item item : inventory.snapshot()) {
            sum += item.attackBonus();
        }
        return sum;
    }

    public int defencePower() {
        int sum = 0;
        for (Item item : inventory.snapshot()) {
            sum += item.defenceBonus();
        }
        return sum;
    }

    public int takeHit(int damage) {
        if (damage < 0 || damage > 1000) {
            throw new IllegalArgumentException("Удар должен быть от 0 до 1000");
        }
        int loss = Math.max(0, damage - defencePower());
        loss = Math.min(loss, hp);
        hp -= loss;
        return loss;
    }

    public void heal(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Лечение не может быть отрицательным");
        }
        hp += Math.min(amount, maxHp - hp);
    }

    public boolean useItem(int index) {
        Item item = inventory.get(index);
        if (item instanceof Usable) {
            Usable usable = (Usable) item;
            usable.use(this);
            inventory.remove(index);
            return true;
        }
        return false;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public String getName() {
        return name;
    }
}
