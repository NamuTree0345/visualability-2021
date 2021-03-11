package Xeon.VisualAbility.AbilityList;


import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MainModule.EventManager;
import Xeon.VisualAbility.MinerModule.EventData;

public class Feather extends AbilityBase {
	public Feather(){
		InitAbility("깃털", Type.Passive_AutoMatic, Rank.C,
			"낙하 데미지와 물속에서의 질식 데미지를 받지 않습니다.",
			"40% 확률로 데미지를 1로 줄여받으며 같은 확률로",
			"Mirroring 능력을 회피할 수 있습니다.");
		InitAbility(0, 0, true);
		EventManager.onEntityDamage.add(new EventData(this));
	}
	
	@Override
	public int A_Condition(Event event, int CustomData){
		EntityDamageEvent Event = (EntityDamageEvent)event;
		if(PlayerCheck(Event.getEntity())){
			if(Event.getCause() == DamageCause.FALL || Event.getCause() == DamageCause.DROWNING){
				return 0;
			}
			else if(Math.random() <= 0.4){
				return 1;
			}
		}
		return -1;
	}
	
	@Override
	public void A_Effect(Event event, int CustomData){
		EntityDamageEvent Event = (EntityDamageEvent)event;
		switch(CustomData){
		case 0:
			Event.setCancelled(true);
			break;
		case 1:
			Event.setDamage(1);
			break;
		}
	}
}
