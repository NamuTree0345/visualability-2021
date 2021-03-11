package Xeon.VisualAbility.MainModule;

import java.util.LinkedList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import Xeon.VisualAbility.VisualAbility;
import Xeon.VisualAbility.MinerModule.CommandInterface;
import Xeon.VisualAbility.Script.MainScripter;
import Xeon.VisualAbility.Script.MainScripter.ScriptStatus;

public class CommandManager implements CommandExecutor  {
	private LinkedList<CommandInterface> CommandEventHandler = new LinkedList<CommandInterface>();
	
	public CommandManager(VisualAbility va) {
	    va.getCommand("va").setExecutor(this);
	    va.getCommand("bjstart").setExecutor(this);
	 }
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] data){
		if(command.getName().equals("va")){
			if(data.length != 0){
				for(CommandInterface handler : CommandEventHandler){
					if(handler.onCommandEvent(sender, command, label, data)){
						return true;
					}
				}
			}
			else{
				sender.sendMessage(ChatColor.GREEN+"VisualAbility 도움말");
				sender.sendMessage(ChatColor.YELLOW+"/va start : "+ChatColor.WHITE+"게임을 시작시킵니다. 옵 전용.");
				sender.sendMessage(ChatColor.YELLOW+"/va stop : "+ChatColor.WHITE+"게임을 중지시킵니다. 옵 전용.");
				sender.sendMessage(ChatColor.YELLOW+"/va help : "+ChatColor.WHITE+"능력을 확인합니다.");
				sender.sendMessage(ChatColor.YELLOW+"/va ob : "+ChatColor.WHITE+"옵저버 설정을 합니다.");
				sender.sendMessage(ChatColor.YELLOW+"/va uti : "+ChatColor.WHITE+"유틸리티 명령 목록을 보여줍니다.");
				sender.sendMessage(ChatColor.YELLOW+"/va debug : "+ChatColor.WHITE+"오류 방어 명령 목록을 보여줍니다. 옵 전용.");
				return true;
			}
		}
		else if(command.getName().equals("bjstart") && sender.isOp()){
			if(MainScripter.Scenario == ScriptStatus.NoPlay){
				sender.sendMessage("네. 결국 이 코드가 실행되게 만드시네요. 축하드립니다.");
				sender.sendMessage(ChatColor.GREEN+"명령어가 업데이트 되었으며 /va로 도움말을 보실수 있습니다.");
				sender.sendMessage(ChatColor.AQUA+"다음부터는 업데이트 내역도 꼼꼼히 읽어주세요.");
			}
			return true;
		}
		return false;
	}
	
	public void RegisterCommand(CommandInterface EventHandler){
		CommandEventHandler.add(EventHandler);
	}
}
