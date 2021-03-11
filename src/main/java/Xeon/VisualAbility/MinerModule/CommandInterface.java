package Xeon.VisualAbility.MinerModule;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface CommandInterface {
	public boolean onCommandEvent(CommandSender sender, Command command, String label, String[] data);
}
