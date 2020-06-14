package me.evisual.divinevouchers;

import me.evisual.divinevouchers.commands.Commands;
import me.evisual.divinevouchers.listeners.OnClick;
import me.evisual.divinevouchers.vouchers.VoucherManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class DivineVouchers extends JavaPlugin {

    private static String prefix;

    private static DivineVouchers instance;

    private File vouchersfile = new File(getDataFolder(), "vouchers.yml");
    private YamlConfiguration vouchersconfig = YamlConfiguration.loadConfiguration(vouchersfile);

    public static DivineVouchers getInstance()
    {
        return instance;
    }

    public DivineVouchers()
    {
        instance = this;
    }

    public void onEnable()
    {
        createFiles();
        registerCommands();
        registerEvents();
        VoucherManager.getInstance().loadVouchers();
        loadPrefix();
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new OnClick(), this);
    }

    public void loadPrefix() {
        if(vouchersconfig.contains("prefix"))
            prefix = ChatColor.translateAlternateColorCodes('&', vouchersconfig.getString("prefix") + " ");
        else
            prefix = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "DivineVouchers " + ChatColor.DARK_PURPLE + "» ";
    }

    public void onDisable()
    {
        VoucherManager.getInstance().saveVouchers();
        saveFiles();
    }

    public void saveFiles()
    {
        try {
            vouchersconfig.save(vouchersfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void registerCommands()
    {
        this.getCommand("divinevoucher").setExecutor(new Commands());
        this.getCommand("divinevoucher").setAliases(Arrays.asList("dv", "voucher", "vouchers", "divinevouchers"));
    }

    public void createFiles()
    {
        if(!vouchersfile.exists())
        {
            try {
                vouchersfile.createNewFile();
            } catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public File getVouchersfile() {
        return vouchersfile;
    }

    public YamlConfiguration getVouchersconfig() {
        return vouchersconfig;
    }

    public void setVouchersconfig(YamlConfiguration vouchersconfig) {
        this.vouchersconfig = vouchersconfig;
    }

    public void setVouchersfile(File vouchersfile) {
        this.vouchersfile = vouchersfile;
    }

    public static String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = ChatColor.translateAlternateColorCodes('&', prefix + " ");
    }
}
