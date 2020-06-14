package me.evisual.divinevouchers.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(args.length == 0)
            CommandHandler.getInstance().sendHelpMessage(sender);


        else if(args.length == 1)
        {
            if(args[0].equalsIgnoreCase("help"))
                CommandHandler.getInstance().sendHelpMessage(sender);
            else if(args[0].equalsIgnoreCase("list"))
                CommandHandler.getInstance().listVouchers(sender);
            else
                CommandHandler.getInstance().sendHelpMessage(sender);
        }

        else if(args.length == 2)
        {
            if(args[0].equalsIgnoreCase("create"))
                CommandHandler.getInstance().createVoucher(args[1], sender);
            else if(args[0].equalsIgnoreCase("give"))
                CommandHandler.getInstance().giveVoucherHandler(args[1], sender);
            else if(args[0].equalsIgnoreCase("additem"))
                CommandHandler.getInstance().addItemHandler(args[1], sender);
            else if(args[0].equalsIgnoreCase("remove"))
                CommandHandler.getInstance().voucherRemoveHandler(args[1], sender);
            else if(args[0].equalsIgnoreCase("prefix"))
                CommandHandler.getInstance().prefixHandler(sender, args[1]);
            else if(args[0].equalsIgnoreCase("listcmds"))
                CommandHandler.getInstance().listCommands(sender, args[1]);
            else if(args[0].equalsIgnoreCase("listitems"))
                CommandHandler.getInstance().listItems(sender, args[1]);
            else
                CommandHandler.getInstance().sendHelpMessage(sender);
        }

        else if(args.length == 3)
        {
            if(args[0].equalsIgnoreCase("give"))
                CommandHandler.getInstance().giveVoucherHandler(args[2], sender, args[1]);
            else if(args[0].equalsIgnoreCase("addcommand"))
                CommandHandler.getInstance().addCommandHandler(args[1], sender, args[2]);
            else if (args[0].equalsIgnoreCase("setname"))
                CommandHandler.getInstance().setVoucherNameHandler(args[1], sender, args);
            else if (args[0].equalsIgnoreCase("setlore"))
                CommandHandler.getInstance().setVoucherLoreHandler(args[1], sender, args);
            else if(args[0].equalsIgnoreCase("removeitem"))
                CommandHandler.getInstance().removeItem(sender, args[1], args[2]);
            else if(args[0].equalsIgnoreCase("removecmd"))
                CommandHandler.getInstance().removeCommand(sender, args[1], args[2]);
            else
                CommandHandler.getInstance().sendHelpMessage(sender);
        }

        else if(args.length > 3) {
            if (args[0].equalsIgnoreCase("setname"))
                CommandHandler.getInstance().setVoucherNameHandler(args[1], sender, args);
            else if (args[0].equalsIgnoreCase("setlore"))
                CommandHandler.getInstance().setVoucherLoreHandler(args[1], sender, args);
            else
                CommandHandler.getInstance().sendHelpMessage(sender);
        }



        return true;
    }

}
