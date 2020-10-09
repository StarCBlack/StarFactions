package me.starcrazzy.factions.utils.inventory;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import me.starcrazzy.factions.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class InventoryBuilder implements Listener {

    private Inventory inventory;
    private Boolean cancelled = false;
    private HashMap<Integer, Item> items = Maps.newHashMap();
    private List<Character> design;
    private Consumer<InventoryCloseEvent> closeEventConsumer;
    private Consumer<InventoryClickEvent> clickEventConsumer;

    /**
     * @param name
     * @param rows
     */
    public InventoryBuilder(String name, Integer rows) {
        this.inventory = Bukkit.createInventory(null, rows*9, name);

        this.register();
    }

    /**
     * @param name
     * @param type
     */
    public InventoryBuilder(String name, InventoryType type) {
        this.inventory = Bukkit.createInventory(null, type, name);

        this.register();
    }

    /**
     * Register listeners in the class
     */
    private void register() {
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    /**
     * @param slot
     * @param item
     * @return InventoryBuilder
     */
    public InventoryBuilder setItem(Integer slot, Item item) {
        this.items.put(slot, item);

        return this;
    }

    /**
     * @param item
     * @return InventoryBuilder
     */
    public InventoryBuilder addItem(Item item) {
        this.items.put(this.items.values().size()+1, item);

        return this;
    }

    /**
     * @param cancelled
     * @return InventoryBuilder
     */
    public InventoryBuilder setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;

        return this;
    }

    /**
     * @param designs
     * @return InventoryBuilder
     */
    public InventoryBuilder design(String... designs) {
        this.design = Lists.newArrayList();

        Integer slot = 0;

        for (String design : designs) {
            char[] chars = design.toCharArray();
            for (char letter : chars) {
                if (letter == 'O') this.design.set(slot, letter);
            }
        }

        return this;
    }

    /**
     * Organize all items in inventory
     */
    public void organize() {
        if (this.design == null) return;

        int i = 0;

        for (Character character : this.design) {
            Integer slot = this.design.indexOf(character);

            Item item = this.items.get(i);

            if (item != null && item.isEditable()) {
                Item cloned = item.clone();

                this.items.remove(i);

                this.items.put(slot, cloned);
            }

            i++;
        }
    }

    /**
     * @param event
     * @return InventoryBuilder
     */
    public InventoryBuilder onClose(Consumer<InventoryCloseEvent> event) {
        this.closeEventConsumer = event;

        return this;
    }

    /**
     * @param event
     * @return InventoryBuilder
     */
    public InventoryBuilder onClick(Consumer<InventoryClickEvent> event) {
        this.clickEventConsumer = event;

        return this;
    }

    /**
     * @param slot
     * @return Item
     */
    public Item getItem(Integer slot) {
        return this.items.getOrDefault(slot, null);
    }

    /**
     * @param player
     * @return InventoryView
     */
    public InventoryView open(Player player) {
        //this.organize();

        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            this.items.forEach((slot, item) -> {
                this.inventory.setItem(slot, item.build());
            });
        });

        return player.openInventory(this.inventory);
    }

    /**
     * @param event
     */
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if (this.inventory != null && this.inventory.equals(inventory)) {
            if (this.clickEventConsumer != null) this.clickEventConsumer.accept(event);

            event.setCancelled(this.cancelled);

            Integer slot = event.getSlot();

            Item item = this.items.get(slot);

            if (item == null) return;

            if (item.getConsumer() != null) item.getConsumer().accept(event);
        }
    }

    /**
     * @param event
     */
    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        if (this.inventory != null && this.inventory.equals(inventory)) {
            if (this.closeEventConsumer != null) this.closeEventConsumer.accept(event);
        }
    }
}