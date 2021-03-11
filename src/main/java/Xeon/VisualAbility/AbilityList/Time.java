package Xeon.VisualAbility.AbilityList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MainModule.EventManager;
import Xeon.VisualAbility.MinerModule.ACC;
import Xeon.VisualAbility.MinerModule.EventData;


public class Time extends AbilityBase {
	public Time(){
		InitAbility("타임", Type.Active_Continue, Rank.B,
			"자신을 제외한 모든 능력자의 이동을 일정 시간동안",
			"차단합니다. 단, 직접적인 이동만 불가능합니다.",
			"명심하세요. 사용 순간부터 능력이 드러나버립니다.",
			"몬스터들의 움직임은 멈추지 않으며 오직 사람들만 멈춥니다.",
			"능력이 없는 사람도 다 멈춥니다.");
		InitAbility(85, 4, true);
		RegisterLeftClickEvent();
		EventManager.onPlayerMoveEvent.add(new EventData(this));
	}
	
	@Override
	public int A_Condition(Event event, int CustomData){
		PlayerInteractEvent Event = (PlayerInteractEvent)event;
		if(PlayerCheck(Event.getPlayer()) && ItemCheck(ACC.DefaultItem)){
			return 0;
		}
		return -1;
	}
	
	@Override
	public void A_Effect(Event event, int CustomData){
		PlayerMoveEvent Event = (PlayerMoveEvent)event;
		if(!PlayerCheck(Event.getPlayer()))
			Event.setCancelled(true);
	}
	
	@Override
	public void A_DurationStart(){
		Bukkit.broadcastMessage(String.format("%s"+ChatColor.RED+"님이 Time 능력을 사용했습니다.", GetPlayer().getName()));
	}
	
	@Override
	public void A_DurationEnd(){
		Bukkit.broadcastMessage(String.format(ChatColor.GREEN+"Time 능력이 해제되었습니다."));
	}
}
