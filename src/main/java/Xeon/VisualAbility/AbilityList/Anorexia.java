package Xeon.VisualAbility.AbilityList;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import Xeon.VisualAbility.VisualAbility;
import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MainModule.EventManager;
import Xeon.VisualAbility.MinerModule.EventData;


public class Anorexia extends AbilityBase {
	public Anorexia(){
		InitAbility("거식증", Type.Passive_AutoMatic, Rank.B,
			"캐릭터의 배고픔이 꽉 찬 상태로 고정됩니다.",
			"모든 종류의 체력 회복량을 3배로 받습니다.",
			"배고픔 무한 모드일시 회복량 4배로 상승.");
		InitAbility(0, 0, true);
		EventManager.onFoodLevelChange.add(new EventData(this, 0));
		EventManager.onEntityRegainHealth.add(new EventData(this, 1));
	}

	@Override
	public int A_Condition(Event event, int CustomData){
		Player p = null;
		switch(CustomData){
		case 0:
			FoodLevelChangeEvent Event0 = (FoodLevelChangeEvent)event;
			p = (Player)Event0.getEntity();
			if(PlayerCheck(p))
				return 0;
			break;
			
		case 1:
			EntityRegainHealthEvent Event1 = (EntityRegainHealthEvent)event;
			if(PlayerCheck(Event1.getEntity()))
				return 1;
			break;
		}
		return -1;
	}

	@Override
	public void A_Effect(Event event, int CustomData){
		switch(CustomData){
		case 0:
			FoodLevelChangeEvent Event0 = (FoodLevelChangeEvent)event;
			Player p = (Player)Event0.getEntity();
			p.setFoodLevel(20);
			p.setSaturation(0.0F);
			Event0.setCancelled(true);
			break;
			
		case 1:
			EntityRegainHealthEvent Event1 = (EntityRegainHealthEvent)event;
			if(VisualAbility.NoFoodMode){
				Event1.setAmount(Event1.getAmount()*4);}
			else{
				Event1.setAmount(Event1.getAmount()*3);}
			break;
		}
	}
}
