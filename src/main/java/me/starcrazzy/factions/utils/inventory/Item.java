package me.starcrazzy.factions.utils.inventory;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Item {
    private ItemStack itemStack;
    private Consumer<InventoryClickEvent> event;
    private Boolean editable = false;

    /**
     * @param material
     */
    public Item(Material material) {
        this.itemStack = new ItemStack(material);
    }

    /**
     * @param itemStack
     */
    public Item(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * @param material
     * @return Item
     */
    public Item type(Material material) {
        this.itemStack.setType(material);

        return this;
    }

    /**
     * @return Material
     */
    public Material getType() {
        return this.itemStack.getType();
    }

    /**
     * @param name
     * @return Item
     */
    public Item name(String name){
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setDisplayName(name);

        this.setItemMeta(itemMeta);

        return this;
    }

    /**
     * @param lore
     * @return Item
     */
    public Item lore(String... lore) {
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setLore(Arrays.asList(lore));

        this.setItemMeta(itemMeta);

        return this;
    }

    /**
     * @param lore
     * @return Item
     */
    public Item lore(List<String> lore) {
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setLore(lore);

        this.setItemMeta(itemMeta);

        return this;
    }

    /**
     * @return Item
     */
    public Item hideAttributes() {
        ItemMeta itemMeta = this.getItemMeta();

        for (ItemFlag flag : ItemFlag.values()) {
            itemMeta.addItemFlags(flag);
        }

        this.setItemMeta(itemMeta);

        return this;
    }

    /**
     * @param event
     * @return Item
     */
    public Item onClick(Consumer<InventoryClickEvent> event) {
        this.event = event;

        return this;
    }

    /**
     * @param data
     * @return Item
     */
    public Item data(Integer data) {
        this.itemStack.setDurability(data.shortValue());

        return this;
    }

    /**
     * @param amount
     * @return Item
     */
    public Item amount(Integer amount) {
        this.itemStack.setAmount(amount);

        return this;
    }

    /**
     * @param enchantment
     * @param level
     * @return Item
     */
    public Item enchant(Enchantment enchantment, Integer level) {
        this.itemStack.addUnsafeEnchantment(enchantment, level);

        return this;
    }

    /**
     * @param owner
     * @return Item
     */
    public Item owner(String owner) {
        SkullMeta skullMeta = (SkullMeta) this.itemStack.getItemMeta();
        skullMeta.setOwner(owner);
        itemStack.setItemMeta(skullMeta);
        return this;
    }

    /**
     * @param editable
     * @return Item
     */
    public Item setEditable(Boolean editable) {
        this.editable = editable;

        return this;
    }

    /**
     * @return Boolean
     */
    public Boolean isEditable() { return this.editable;}

    /**
     * @return Consumer<InventoryClickEvent>
     */
    public Consumer<InventoryClickEvent> getConsumer() {
        return this.event;
    }

    /**
     * @return ItemStack
     */
    public ItemStack build() {
        return this.itemStack;
    }

    /**
     * @return Item
     */
    public Item clone() {
        return new Item(this.itemStack);
    }

    /**
     * @param itemMeta
     */
    private void setItemMeta(ItemMeta itemMeta) {
        this.itemStack.setItemMeta(itemMeta);
    }

    /**
     * @return ItemMeta
     */
    private ItemMeta getItemMeta() {
        return this.itemStack.getItemMeta();
    }
}