package Xeon.VisualAbility.AbilityList;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityTargetEvent;
import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MainModule.EventManager;
import Xeon.VisualAbility.MinerModule.EventData;


public class Shadow extends AbilityBase {
	public Shadow(){
		InitAbility("그림자", Type.Passive_AutoMatic, Rank.C,
			"몬스터가 절대로 공격을 하지 않습니다. 생명체로부터",
			"공격받을시 30% 확률로 데미지를 50% 줄여받고",
			"체력을 2 회복합니다.");
		InitAbility(0, 0, true);
		EventManager.onEntityTarget.add(new EventData(this, 0));
		EventManager.onEntityDamage.add(new EventData(this, 1));
	}

	@Override
	public int A_Condition(Event event, int CustomData){
		switch(CustomData){
		case 0:
			EntityTargetEvent Event0 = (EntityTargetEvent)event;
			if(PlayerCheck(Event0.getTarget())){
				return 0;
			}
			break;
			
		case 1:
			EntityDamageEvent Event1 = (EntityDamageEvent)event;
			if(PlayerCheck(Event1.getEntity())){
				if(Event1.getCause() == DamageCause.ENTITY_ATTACK && Math.random() <= 0.3){
					return 1;
				}
			}
			break;
		}
		return -1;
	}

	@Override
	public void A_Effect(Event event, int CustomData){
		switch(CustomData){
		case 0:
			EntityTargetEvent Event0 = (EntityTargetEvent)event;
			Event0.setTarget(null);
			Event0.setCancelled(true);
			break;
			
		case 1:
			EntityDamageEvent Event1 = (EntityDamageEvent)event;
			Event1.setDamage(Event1.getDamage()/2);
			if(GetPlayer().getHealth() <= 18)
				GetPlayer().setHealth(GetPlayer().getHealth() + 2);
			AbilityBase.ShowEffect(Event1.getEntity().getLocation(), Material.STONE, Effect.MOBSPAWNER_FLAMES);
			break;
		}
	}
}
