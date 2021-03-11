package Xeon.VisualAbility.AbilityList;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import Xeon.VisualAbility.VisualAbility;
import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MainModule.EventManager;
import Xeon.VisualAbility.MinerModule.ACC;
import Xeon.VisualAbility.MinerModule.EventData;

public class Aegis extends AbilityBase {
	public Aegis(){
		if(!VisualAbility.ReverseMode){
			InitAbility("이지스", Type.Active_Continue, Rank.A,
				"능력 사용시 일정시간동안 무적이 됩니다. 무적은 대부분의",
				"데미지를 무력화시키며 능력 사용중엔 Mirroring 능력도 ",
				"무력화됩니다.");
			InitAbility(28, 6, true);
			RegisterLeftClickEvent();
			EventManager.onEntityDamage.add(new EventData(this));
		}
		else{
			InitAbility("이지스", Type.Passive_AutoMatic, Rank.A,
				"모든 종류의 데미지를 1000배로 받습니다.",
				"스쳐도 죽는 스릴을 맛보시라.");
			InitAbility(0, 0, true);
			EventManager.onEntityDamage.add(new EventData(this));
		}
	}

	@Override
	public int A_Condition(Event event, int CustomData){
		if(!VisualAbility.ReverseMode){
			PlayerInteractEvent Event = (PlayerInteractEvent)event;
			if(PlayerCheck(Event.getPlayer()) && ItemCheck(ACC.DefaultItem))
				return 0;
		}
		else{
			EntityDamageEvent Event = (EntityDamageEvent)event;
			if(PlayerCheck(Event.getEntity()))
				return 0;
		}
		return -1;
	}

	@Override
	public void A_Effect(Event event, int CustomData){
		if(!VisualAbility.ReverseMode){
			EntityDamageEvent Event = (EntityDamageEvent)event;
			if(PlayerCheck(Event.getEntity())){
				Player p = (Player) Event.getEntity();
				p.setFireTicks(0);
				Event.setCancelled(true);
				AbilityBase.ShowEffect(p.getLocation(), Material.IRON_BLOCK, Effect.ZOMBIE_CHEW_IRON_DOOR);
			}
		}
		else{
			EntityDamageEvent Event = (EntityDamageEvent)event;
			Event.setDamage(Event.getDamage()*1000);
		}
	}
}
