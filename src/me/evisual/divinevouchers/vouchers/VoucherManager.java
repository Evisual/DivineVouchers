package me.evisual.divinevouchers.vouchers;

import me.evisual.divinevouchers.DivineVouchers;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VoucherManager {

    private static VoucherManager instance;

    private ArrayList<Voucher> vouchers = new ArrayList<>();

    public static VoucherManager getInstance()
    {
        if(instance == null)
            instance = new VoucherManager();
        return instance;
    }

    private VoucherManager()
    {

    }

    public Voucher getVoucherByName(String name)
    {
        for(Voucher v : vouchers)
        {
            if(v.getName().equals(name))
                return v;
        }

        return null;
    }

    public boolean doesVoucherExist(String name)
    {
        for(Voucher v : vouchers)
        {
            if(v.getName().equals(name))
                return true;
        }

        return false;
    }

    public ArrayList<String> getVoucherNames()
    {
        ArrayList<String> vnames = new ArrayList<>();

        for(Voucher v : vouchers)
        {
            vnames.add(v.getName());
        }

        return vnames;
    }

    public void saveVouchers()
    {
        YamlConfiguration vfile = DivineVouchers.getInstance().getVouchersconfig();
        for(Voucher v : vouchers)
        {
            vfile.set("vouchers." + v.getName() + ".displayname", v.getDisplayname());
            vfile.set("vouchers." + v.getName() + ".lore", v.getLore());
            vfile.set("vouchers." + v.getName() + ".commands", v.getCommands());
            vfile.set("vouchers." + v.getName() + ".items", v.getItems());
            vfile.set("vouchers." + v.getName() + ".material", new ItemStack(v.getMaterial()));
        }

        DivineVouchers.getInstance().saveFiles();
    }

    public void loadVouchers()
    {
        YamlConfiguration vfile = DivineVouchers.getInstance().getVouchersconfig();
        for(String s : vfile.getConfigurationSection("vouchers").getKeys(false))
        {
            Voucher v = new Voucher(s);
            v.setDisplayname(vfile.getString("vouchers." + s + ".displayname"));
            v.setLore(vfile.getString("vouchers." + s + ".lore"));
            v.setCommands(vfile.getStringList("vouchers." + s + ".commands"));
            v.setItems((List<ItemStack>) vfile.getList("vouchers." + s + ".items"));
            v.setMaterial(vfile.getItemStack("vouchers." + s + ".material").getType());

            vouchers.add(v);
        }
    }

    public void addVoucher(Voucher v)
    {
        vouchers.add(v);
    }

    public Voucher getVoucherByDisplayName(String name)
    {
        for(Voucher voucher : vouchers)
        {
            if(voucher.getDisplayname().equals(name))
                return voucher;

        }
        return null;
    }

    public void removeVoucher(Voucher v)
    {
        vouchers.removeIf(voucher -> voucher.getName().equals(v.getName()));
        YamlConfiguration vfile = DivineVouchers.getInstance().getVouchersconfig();
        vfile.set("vouchers." + v.getName(), "");
    }

    public boolean doesVoucherDisplayNameExist(String name)
    {
        for(Voucher voucher : vouchers)
        {
            if(voucher.getDisplayname().equals(name))
                return true;

        }
        return false;
    }

    public void giveVoucher(Player p, String name)
    {
        for(Voucher v : vouchers)
        {
            if(v.getName().equals(name))
            {
               p.getInventory().addItem(createVoucher(v));
               return;
            }
        }
    }

    public ItemStack createVoucher(Voucher v)
    {
        ItemStack item = new ItemStack(v.getMaterial());
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', v.getDisplayname()));
        meta.setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', v.getLore())));

        item.setItemMeta(meta);

        return item;
    }
}
