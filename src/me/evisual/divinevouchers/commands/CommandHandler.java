package me.evisual.divinevouchers.commands;

import me.evisual.divinevouchers.DivineVouchers;
import me.evisual.divinevouchers.vouchers.Voucher;
import me.evisual.divinevouchers.vouchers.VoucherManager;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler {

    private static CommandHandler instance;

    public static CommandHandler getInstance()
    {
        if(instance == null)
            instance = new CommandHandler();
        return instance;
    }

    private CommandHandler()
    {

    }

    public void sendHelpMessage(CommandSender sender)
    {
        if(!sender.hasPermission("dv.*") && !sender.hasPermission("dv.help"))
        { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "You do not have permission to run this command!"); return; }

        String line = ChatColor.DARK_PURPLE + "" + ChatColor.STRIKETHROUGH + "----------------------------------------------";
        sender.sendMessage(line);

        sender.sendMessage(ChatColor.DARK_PURPLE + "/dv help" + ChatColor.AQUA + " - Display this help message");
        sender.sendMessage(ChatColor.DARK_PURPLE + "/dv list" + ChatColor.AQUA + " - List all the vouchers currently existing.");
        sender.sendMessage(ChatColor.DARK_PURPLE + "/dv listcmds <voucher>" + ChatColor.AQUA + " - List all the vouchers currently existing.");
        sender.sendMessage(ChatColor.DARK_PURPLE + "/dv listitems <voucher>" + ChatColor.AQUA + " - List all the vouchers currently existing.");
        sender.sendMessage(ChatColor.DARK_PURPLE + "/dv removeitem <voucher> <itemnum>" + ChatColor.AQUA + " - List all the vouchers currently existing.");
        sender.sendMessage(ChatColor.DARK_PURPLE + "/dv removecmd <voucher> <commandnum>" + ChatColor.AQUA + " - List all the vouchers currently existing.");
        sender.sendMessage(ChatColor.DARK_PURPLE + "/dv create <voucher>" + ChatColor.AQUA + " - Create a voucher with the name <name>.");
        sender.sendMessage(ChatColor.DARK_PURPLE + "/dv give [player] <voucher>" + ChatColor.AQUA + " - Create a voucher with the name <name>.");
        sender.sendMessage(ChatColor.DARK_PURPLE + "/dv additem <voucher>" + ChatColor.AQUA + " - Add the item you are holding to voucher.");
        sender.sendMessage(ChatColor.DARK_PURPLE + "/dv addcommand <voucher> <command>" + ChatColor.AQUA + " - Add a command to be executed with your voucher.");
        sender.sendMessage(ChatColor.DARK_PURPLE + "/dv setname <voucher> <name>" + ChatColor.AQUA + " - Set the displayname of the voucher.");
        sender.sendMessage(ChatColor.DARK_PURPLE + "/dv setlore <voucher> <lore>" + ChatColor.AQUA + " - Set the lore of the voucher.");
        sender.sendMessage(ChatColor.DARK_PURPLE + "/dv remove <voucher>" + ChatColor.AQUA + " - Delete a voucher.");
        sender.sendMessage(ChatColor.DARK_PURPLE + "/dv prefix <message>" + ChatColor.AQUA + " - Set the prefix for DivineVouchers.");

        sender.sendMessage(line);
    }

    public void listVouchers(CommandSender sender)
    {
        if(!sender.hasPermission("dv.*") && !sender.hasPermission("dv.list"))
        { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "You do not have permission to run this command!"); return; }
        ArrayList<String> vnames = VoucherManager.getInstance().getVoucherNames();
        String line = ChatColor.DARK_PURPLE + "" + ChatColor.STRIKETHROUGH + "----------------------------------------------";
        sender.sendMessage(line);
        for(String s : vnames)
        {
            sender.sendMessage(ChatColor.AQUA + s);
        }
        sender.sendMessage(line);
    }

    public void createVoucher(String name, CommandSender sender)
    {
        if(!sender.hasPermission("dv.*") && !sender.hasPermission("dv.create"))
        { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "You do not have permission to run this command!"); return; }

        if(VoucherManager.getInstance().doesVoucherExist(name))
            { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "That voucher already exists!"); return; }

        Voucher voucher = new Voucher(name);

        voucher.addItem(new ItemStack(Material.DIAMOND));
        voucher.addCommand("broadcast %player% has redeemed a %voucher% voucher!");

        VoucherManager.getInstance().addVoucher(voucher);

        sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.AQUA + "You have successfully created your voucher!");
    }

    public void giveVoucherHandler(String name, CommandSender sender)
    {
        if(!sender.hasPermission("dv.*") && !sender.hasPermission("dv.give"))
        { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "You do not have permission to run this command!"); return; }

        if(!(sender instanceof Player))
        { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "You must be a player in order to run this command!"); return; }

        Player p = (Player) sender;

        if(!VoucherManager.getInstance().doesVoucherExist(name))
        { p.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "That voucher does not exist!"); return; }

        VoucherManager.getInstance().giveVoucher(p, name);
        p.sendMessage(DivineVouchers.getPrefix() + ChatColor.AQUA + "You have been given a voucher!");
    }

    public void giveVoucherHandler(String name, CommandSender sender, String targetname)
    {
        if(!sender.hasPermission("dv.*") && !sender.hasPermission("dv.give"))
        { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "You do not have permission to run this command!"); return; }

        Player p = Bukkit.getPlayer(targetname);
        if(p == null)
        {   sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "That player does not exist!"); return; }

        if(!VoucherManager.getInstance().doesVoucherExist(name))
        { p.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "That voucher does not exist!"); return; }

        VoucherManager.getInstance().giveVoucher(p, name);
        p.sendMessage(DivineVouchers.getPrefix() + ChatColor.AQUA + "You have been given a voucher!");
    }

    public void addItemHandler(String voucher, CommandSender sender) {

        if(!sender.hasPermission("dv.*") && !sender.hasPermission("dv.additem"))
        { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "You do not have permission to run this command!"); return; }

        if(!(sender instanceof Player))
        { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "You must be a player to run this command!"); return; }

        Player p = (Player) sender;

        if(!VoucherManager.getInstance().doesVoucherExist(voucher))
        { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "That voucher does not exist!"); return; }

        Voucher v = VoucherManager.getInstance().getVoucherByName(voucher);

        if(p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR)
        { p.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "You must be holding an item order to run this command!"); return; }

        v.addItem(p.getItemInHand());

        p.sendMessage(DivineVouchers.getPrefix() + ChatColor.AQUA + "Item added!");
    }

    public void addCommandHandler(String voucher, CommandSender sender, String command) {

        if(!sender.hasPermission("dv.*") && !sender.hasPermission("dv.addcommand"))
        { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "You do not have permission to run this command!"); return; }

        if(!VoucherManager.getInstance().doesVoucherExist(voucher))
        { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "That voucher does not exist!"); return; }

        Voucher v = VoucherManager.getInstance().getVoucherByName(voucher);

        v.addCommand(command);

        sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.AQUA + "Command added!");
    }

    public void setVoucherNameHandler(String voucher, CommandSender sender, String[] displayname) {
        if(!sender.hasPermission("dv.*") && !sender.hasPermission("dv.setname"))
        { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "You do not have permission to run this command!"); return; }

        if(!VoucherManager.getInstance().doesVoucherExist(voucher))
        { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "That voucher does not exist!"); return; }

        Voucher v = VoucherManager.getInstance().getVoucherByName(voucher);

        String message = StringUtils.join(displayname, " ", 2, displayname.length);

        v.setDisplayname(message);

        sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.AQUA + "Voucher displayname changed to: " + ChatColor.translateAlternateColorCodes('&', message));
    }

    public void setVoucherLoreHandler(String voucher, CommandSender sender, String[] lore) {

        if(!sender.hasPermission("dv.*") && !sender.hasPermission("dv.setlore"))
        { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "You do not have permission to run this command!"); return; }

        if(!VoucherManager.getInstance().doesVoucherExist(voucher))
        { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "That voucher does not exist!"); return; }

        Voucher v = VoucherManager.getInstance().getVoucherByName(voucher);

        String message = StringUtils.join(lore, " ", 2, lore.length);

        v.setLore(message);

        sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.AQUA + "Voucher lore changed to: " + ChatColor.translateAlternateColorCodes('&', message));
    }

    public void voucherRemoveHandler(String voucher, CommandSender sender) {

        if(!sender.hasPermission("dv.*") && !sender.hasPermission("dv.remove"))
        { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "You do not have permission to run this command!"); return; }

        if(!VoucherManager.getInstance().doesVoucherExist(voucher))
        { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "That voucher does not exist!"); return; }

        Voucher v = VoucherManager.getInstance().getVoucherByName(voucher);

        VoucherManager.getInstance().removeVoucher(v);

        sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.AQUA + "Voucher removed!");
    }

    public void prefixHandler(CommandSender sender, String prefix) {

        if(!sender.hasPermission("dv.*") && !sender.hasPermission("dv.prefix"))
        { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "You do not have permission to run this command!"); return; }

        DivineVouchers.getInstance().getVouchersconfig().set("prefix", prefix);
        DivineVouchers.getInstance().setPrefix(prefix);

        sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.AQUA + "Prefix changed!");
    }

    public void listCommands(CommandSender sender, String voucher)
    {
        if(!sender.hasPermission("dv.*") && !sender.hasPermission("dv.listcommands"))
        { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "You do not have permission to run this command!"); return; }

        if(!VoucherManager.getInstance().doesVoucherExist(voucher))
        { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "That voucher does not exist!"); return; }

        Voucher v = VoucherManager.getInstance().getVoucherByName(voucher);
        String line = ChatColor.DARK_PURPLE + "" + ChatColor.STRIKETHROUGH + "----------------------------------------------";
        sender.sendMessage(line);
        int i = 1;
        for(String s : v.getCommands())
        {   sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.AQUA + "#" + i + ": " + "/" + s); i++; }

        sender.sendMessage(line);
    }

    public void listItems(CommandSender sender, String voucher) {

        if(!sender.hasPermission("dv.*") && !sender.hasPermission("dv.listitems"))
        { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "You do not have permission to run this command!"); return; }

        if(!VoucherManager.getInstance().doesVoucherExist(voucher))
        { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "That voucher does not exist!"); return; }

        Voucher v = VoucherManager.getInstance().getVoucherByName(voucher);

        int i = 1;
        for(ItemStack item : v.getItems()) {
            if(item.hasItemMeta() && item.getItemMeta().hasDisplayName())
                sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.AQUA + "#" + i + ": " + item.getItemMeta().getDisplayName());
            else
                sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.AQUA + "#" + i + ": " + item.getType().toString());
            i++;
        }


    }

    public void removeItem(CommandSender sender, String voucher, String number)
    {

        if(!sender.hasPermission("dv.*") && !sender.hasPermission("dv.removeitem"))
        { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "You do not have permission to run this command!"); return; }

        if(!VoucherManager.getInstance().doesVoucherExist(voucher))
        { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "That voucher does not exist!"); return; }

        Voucher v = VoucherManager.getInstance().getVoucherByName(voucher);

        int num;

        try {
            num = Integer.parseInt(number);
        } catch(Exception e)
        {
            sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "Please enter a valid number!");
            return;
        }

        num--;

        List<ItemStack> items = v.getItems();
        items.remove(num);
        v.setItems(items);

        sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.AQUA + "Item removed!");
    }

    public void removeCommand(CommandSender sender, String voucher, String number)
    {
        if(!sender.hasPermission("dv.*") && !sender.hasPermission("dv.removecommand"))
        { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "You do not have permission to run this command!"); return; }

        if(!VoucherManager.getInstance().doesVoucherExist(voucher))
        { sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "That voucher does not exist!"); return; }

        Voucher v = VoucherManager.getInstance().getVoucherByName(voucher);

        int num;

        try {
            num = Integer.parseInt(number);
        } catch(Exception e)
        {
            sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.RED + "Please enter a valid number!");
            return;
        }

        num--;

        List<String> commands = v.getCommands();
        commands.remove(num);
        v.setCommands(commands);

        sender.sendMessage(DivineVouchers.getPrefix() + ChatColor.AQUA + "Command removed!");
    }
}
