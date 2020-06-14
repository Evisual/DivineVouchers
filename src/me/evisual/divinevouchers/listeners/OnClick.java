package me.evisual.divinevouchers.listeners;

import me.evisual.divinevouchers.DivineVouchers;
import me.evisual.divinevouchers.vouchers.Voucher;
import me.evisual.divinevouchers.vouchers.VoucherManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class OnClick implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e)
    {
        if(!(e.getAction() == Action.RIGHT_CLICK_AIR) && !(e.getAction() == Action.RIGHT_CLICK_BLOCK))
            return;

        if(e.getPlayer().getItemInHand() == null || e.getPlayer().getItemInHand().getType() == Material.AIR)
            return;

        if(!e.getPlayer().getItemInHand().hasItemMeta() && !e.getPlayer().getItemInHand().getItemMeta().hasLore() && !e.getPlayer().getItemInHand().getItemMeta().hasDisplayName())
            return;

        Voucher v = VoucherManager.getInstance().getVoucherByDisplayName(e.getPlayer().getItemInHand().getItemMeta().getDisplayName());
        if(v == null)
            return;

        Player p = e.getPlayer();
        if(p.getItemInHand().getAmount() == 1)
            p.getInventory().remove(p.getItemInHand());
        else
        {
            ItemStack item = p.getItemInHand();
            item.setAmount(item.getAmount()-1);
            p.setItemInHand(item);
        }
        p.sendMessage(DivineVouchers.getPrefix() + ChatColor.AQUA + "You have redeemed a " +
                ChatColor.translateAlternateColorCodes('&', v.getDisplayname()) + ChatColor.AQUA + " voucher!");

        for(ItemStack item : v.getItems())
            p.getInventory().addItem(item);

        for(String s : v.getCommands())
        {
            s = s.replaceAll("%player%", e.getPlayer().getName().toString());
            s = s.replaceAll("%voucher%", v.getDisplayname().toString());
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), s);
        }

    }
}
