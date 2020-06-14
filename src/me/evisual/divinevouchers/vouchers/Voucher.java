package me.evisual.divinevouchers.vouchers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Voucher {

    private String name;
    private String displayname;
    private String lore;
    private List<String> commands = new ArrayList<>();
    private List<ItemStack> items = new ArrayList<>();
    private Material material;

    public Voucher(String name)
    {
        this.name = name;
        this.material = Material.PAPER;
        this.displayname = ChatColor.AQUA + name;
        this.lore = ChatColor.AQUA + "" + ChatColor.ITALIC + "This is a " + name + " voucher!";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public void addCommand(String command) {
        commands.add(command);
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public void setItems(List<ItemStack> items) {
        this.items = items;
    }

    public void addItem(ItemStack item) {
         items.add(item);
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}

