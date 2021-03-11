package Xeon.VisualAbility.AbilityList;

import Xeon.VisualAbility.VisualAbility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MinerModule.ACC;

public class Clocking extends AbilityBase{
	public Clocking(){
		InitAbility("클로킹", Type.Active_Continue, Rank.A,
			"능력 사용시 일정시간동안 다른 사람에게 보이지 않습니다.",
			"클로킹 상태에서는 타인에게 공격 받지 않습니다.");
		InitAbility(35, 5, true);
		RegisterLeftClickEvent();
	}
	
	@Override
	public int A_Condition(Event event, int CustomData){
		PlayerInteractEvent Event = (PlayerInteractEvent)event;
		if(PlayerCheck(Event.getPlayer()) && ItemCheck(ACC.DefaultItem))
			return 0;
		return -1;
	}
	
	@Override
	public void A_DurationStart(){
		Player[] List = Bukkit.getOnlinePlayers().toArray(new Player[0]);
		for(Player p : List){
			p.hidePlayer(VisualAbility.getPlugin(VisualAbility.class), GetPlayer());
		}
	}
	
	@Override
	public void A_FinalDurationEnd(){
		if(GetPlayer() != null){
			Player[] List = Bukkit.getOnlinePlayers().toArray(new Player[0]);
			if(List != null && List.length != 0){
				for(Player p : List){
					p.showPlayer(VisualAbility.getPlugin(VisualAbility.class), GetPlayer());
				}
			}
		}
	}

	@Override
	public void A_Effect(Event event, int CustomData){
	}

}
