package Xeon.VisualAbility.AbilityList;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MainModule.EventManager;
import Xeon.VisualAbility.MinerModule.ACC;
import Xeon.VisualAbility.MinerModule.EventData;

public class NuclearPunch extends AbilityBase {
	public NuclearPunch(){
		InitAbility("핵 펀치", Type.Active_Immediately, Rank.B,
			"철괴로 타격을 당한 상대가 멀리 넉백당합니다.",
			"동시에 데미지 20을 받습니다. 타격을 받지 않으면 넉백도",
			"되지 않습니다. 상대가 칼로 방어하고 있다면 넉백 강도가",
			"절반으로 떨어집니다. 발사 방향에 따라 날아가는 정도가 다르며",
			"아래에서 위로 쓸때 가장 효율적으로 넉백됩니다.");
		InitAbility(45, 0, true);
		EventManager.onEntityDamageByEntity.add(new EventData(this));
	}

	@Override
	public int A_Condition(Event event, int CustomData) {
		EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
		if(PlayerCheck(Event.getDamager()) && ItemCheck(ACC.DefaultItem)){
			return 0;
		}
		return -1;
	}

	@Override
	public void A_Effect(Event event, int CustomData) {
		EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
		Event.setDamage(20);
		Event.getEntity().getWorld().createExplosion(Event.getEntity().getLocation(), 0F);
		int knockback = -16;
		if(Event.getEntity() instanceof Player){
			Player p = (Player)Event.getEntity();
			if(p.isBlocking()){
				knockback = -8;
			}
		}
		Event.getEntity().setVelocity(
				Event.getEntity().getVelocity().add(
						Event.getDamager().getLocation().toVector().subtract(
								Event.getEntity().getLocation().toVector()).normalize().multiply(knockback)));
	}

}
