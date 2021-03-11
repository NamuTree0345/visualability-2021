package Xeon.VisualAbility.Script;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MajorModule.AbilityList;
import Xeon.VisualAbility.MinerModule.TimerBase;

public class S_GameWarnning {
	private S_ScriptTimer stimer = new S_ScriptTimer();
	private MainScripter ms;
	
	public S_GameWarnning(MainScripter ms){
		this.ms = ms;
	}
	
	public void GameWarnningStart(){
		stimer.StartTimer(99999999);
	}
	
	public void GameWarnningStop(){
		stimer.EndTimer();
	}
	
	public final class S_ScriptTimer extends TimerBase {
		@Override
		public void EventStartTimer() {
		}

		@Override
		public void EventRunningTimer(int count) {
			if(count >= 20 && count % 20 == 0){
				Bukkit.broadcastMessage(ChatColor.RED+"아직 능력을 확정하지 않은 사람이 있습니다.");
				for(int l=0;l<AbilityList.AbilityList.size();++l){
					if(AbilityList.AbilityList.get(l).GetPlayer() != null){
						AbilityBase tempab = AbilityList.AbilityList.get(l);
						if(!ms.OKSign.contains(tempab.GetPlayer())){
							tempab.GetPlayer().sendMessage("당신의 능력이 올바르게 확정되지 않았습니다.");
							tempab.GetPlayer().sendMessage("/va yes나 /va no 명령으로 능력을 확정하세요.");
						}
					}
				}
			}
		}

		@Override
		public void EventEndTimer() {
		}
	}
}
