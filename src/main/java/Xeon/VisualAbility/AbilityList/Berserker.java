package Xeon.VisualAbility.AbilityList;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MainModule.EventManager;
import Xeon.VisualAbility.MinerModule.EventData;


public class Berserker extends AbilityBase {
	public Berserker(){
		InitAbility("광전사", Type.Passive_Manual, Rank.A,
			"체력이 70% 이하로 떨어지면 데미지가 1.5배로 증폭되며",
			"체력이 40% 이하로 떨어지면 데미지가 2배로 증폭됩니다.");
		InitAbility(0, 0, true);
		EventManager.onEntityDamageByEntity.add(new EventData(this));
	}

	@Override
	public int A_Condition(Event event, int CustomData){
		EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
		if(PlayerCheck(Event.getDamager())){
			Player p = (Player) Event.getDamager();
			if(p.getHealth() <= 14 && p.getHealth() > 8)
				return 0;
			else if(p.getHealth() <= 8)
				return 1;
		}
		return -1;
	}

	@Override
	public void A_Effect(Event event, int CustomData){
		EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
		AbilityBase.ShowEffect(Event.getDamager().getLocation(), Material.LAVA, Effect.BLAZE_SHOOT);
		switch(CustomData){
		case 0:
			Event.setDamage((int)(Event.getDamage()*1.5));
			break;
		case 1:
			Event.setDamage(Event.getDamage()*2);
			break;
		}
	}
}
