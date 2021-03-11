package Xeon.VisualAbility.Script;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import Xeon.VisualAbility.MinerModule.TimerBase;

public class S_GameCoordinates {
	private S_ScriptTimer stimer = new S_ScriptTimer();
	private int CumulativeCount = 10;
	
	public void GameCoordinates(){
		stimer.StartTimer(99999999);
	}
	
	public void GameCoordinatesStop(){
		stimer.StopTimer();
		CumulativeCount = 10;
	}
	
	public final class S_ScriptTimer extends TimerBase {
		@Override
		public void EventStartTimer(){
		}

		@Override
		public void EventRunningTimer(int count){
			if(count == CumulativeCount * 60){
				Bukkit.broadcastMessage(ChatColor.GOLD+"- 플레이어 위치 -");
				Bukkit.broadcastMessage(ChatColor.GREEN+"---------------");
				List<Player> pl = MainScripter.PlayerList;
				int i=0;
				for(Player p : pl){
					if(p != null){
						Player temp = Bukkit.getServer().getPlayer(p.getName());
						if(temp != null){
							int x = temp.getLocation().getBlockX();
							int y = temp.getLocation().getBlockY();
							int z = temp.getLocation().getBlockZ();
							Bukkit.broadcastMessage(String.format(
									ChatColor.GREEN+"%d. "+ChatColor.WHITE+"%s : "
									+ChatColor.RED+"X"+ChatColor.WHITE+" %d, "
									+ChatColor.RED+"Y"+ChatColor.WHITE+" %d, "
									+ChatColor.RED+"Z"+ChatColor.WHITE+" %d",
									i, temp.getName(), x, y, z));
							++i;
						}
					}
				}
				Bukkit.broadcastMessage(ChatColor.GREEN+"---------------");
				this.SetCount(0);
				if(CumulativeCount > 3){
					--CumulativeCount;
					Bukkit.broadcastMessage(String.format(ChatColor.YELLOW+"다음 좌표는 %d분 후에 뜹니다.", CumulativeCount));
				}
			}
		}
		
		@Override
		public void EventEndTimer() {
		}
	}
}
