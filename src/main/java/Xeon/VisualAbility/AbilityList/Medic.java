package Xeon.VisualAbility.AbilityList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MainModule.EventManager;
import Xeon.VisualAbility.MinerModule.ACC;
import Xeon.VisualAbility.MinerModule.EventData;

public class Medic extends AbilityBase {
	public Medic(){
		InitAbility("메딕", Type.Active_Immediately, Rank.B,
			"철괴 왼클릭시 맞은 사람의 체력이 10 회복됩니다.",
			"철괴 오른클릭시 자신의 체력을 6 회복합니다.",
			"두 기능은 쿨타임을 공유합니다.");
		InitAbility(5, 0, true);
		EventManager.onEntityDamageByEntity.add(new EventData(this));
		RegisterRightClickEvent();
	}
	
	@Override
	public int A_Condition(Event event, int CustomData) {
		switch(CustomData){
		case 0:
			EntityDamageByEntityEvent Event1 = (EntityDamageByEntityEvent)event;
			if(Event1.getEntity() instanceof Player){
				if(PlayerCheck(Event1.getDamager())){
					if(ItemCheck(ACC.DefaultItem)){
						return 0;
					}
				}
			}
			break;
		case 1:
			PlayerInteractEvent Event2 = (PlayerInteractEvent)event;
			if(PlayerCheck(Event2.getPlayer())){
				if(ItemCheck(ACC.DefaultItem)){
					return 1;
				}
			}
			break;
		}
		return -1;
	}

	@Override
	public void A_Effect(Event event, int CustomData) {
		switch(CustomData){
		case 0:
			EntityDamageByEntityEvent Event1 = (EntityDamageByEntityEvent)event;
			Player p1 = (Player)Event1.getEntity();
			if(p1.getHealth() <= 10)
				p1.setHealth(p1.getHealth() + 10);
			else
				p1.setHealth(20);
			
			p1.sendMessage(String.format(ChatColor.GREEN+"%s님의 메딕 능력으로 체력을 10 회복했습니다.", GetPlayer().getName()));
			GetPlayer().sendMessage(String.format(ChatColor.GREEN+"%s님의 체력을 10 회복시켰습니다.", p1.getName()));
			Event1.setCancelled(true);
			break;
		case 1:
			PlayerInteractEvent Event2 = (PlayerInteractEvent)event;
			Player p2 = Event2.getPlayer();
			if(p2.getHealth() <= 14)
				p2.setHealth(p2.getHealth() + 6);
			else
				p2.setHealth(20);
			
			p2.sendMessage(ChatColor.GREEN+"자신의 체력을 6 회복했습니다.");
			break;
		}
	}

}
