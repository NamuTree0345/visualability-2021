package Xeon.VisualAbility.AbilityList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDeathEvent;

import Xeon.VisualAbility.VisualAbility;
import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MainModule.EventManager;
import Xeon.VisualAbility.MajorModule.AbilityList;
import Xeon.VisualAbility.MinerModule.EventData;


public class Mirroring extends AbilityBase {
	public Mirroring(){
		if(!VisualAbility.NoMirroring){
			InitAbility("미러링", Type.Passive_Manual, Rank.C,
				"당신을 죽인 사람을 함께 저승으로 끌고갑니다.",
				"자신이 죽을경우 죽인 사람 역시 죽게됩니다.",
				"그렇다고 자기가 살아나는것은 아니므로 주의.",
				"데스노트는 이 능력에 죽지 않습니다.");
			InitAbility(0, 0, true);
			EventManager.onEntityDeath.add(new EventData(this));
		}
	}

	@Override
	public int A_Condition(Event event, int CustomData){
		EntityDeathEvent Event = (EntityDeathEvent)event;
		if(Event.getEntity().getKiller() instanceof Player && PlayerCheck(Event.getEntity()))
			return 0;
		return -1;
	}

	@Override
	public void A_Effect(Event event, int CustomData){
		EntityDeathEvent Event = (EntityDeathEvent)event;
		Player p = (Player)Event.getEntity();
		Bukkit.broadcastMessage(
				String.format(ChatColor.RED+"%s님의 미러링 능력이 발동되었습니다.", p.getName()));
		if(AbilityList.assimilation.GetPlayer() == p.getKiller()){
			AbilityList.assimilation.A_Effect(Event, 1);
			Bukkit.broadcastMessage(ChatColor.GREEN+"미러링 능력이 무력화 되었습니다.");
			return;}
		
		if(AbilityList.aegis.GetPlayer() == p.getKiller() &&
				AbilityList.aegis.GetDurationState()){
			Bukkit.broadcastMessage(ChatColor.GREEN+"미러링 능력이 무력화 되었습니다.");
			return;
		}
		
		Bukkit.broadcastMessage(
				String.format(ChatColor.RED+"%s님의 능력에 의해 %s님이 죽었습니다.", p.getName(), p.getKiller().getName()));
		p.getKiller().damage(5000);
	}
}
