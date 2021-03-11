package Xeon.VisualAbility.AbilityList;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MainModule.EventManager;
import Xeon.VisualAbility.MinerModule.EventData;

public class Blind extends AbilityBase {
	public Blind(){
		InitAbility("블라인드", Type.Passive_AutoMatic, Rank.C,
			"자신에게 공격받은 사람은 3초간 시야를 잃습니다.",
			"실제로 데미지가 들어가야 타격으로 인정됩니다.",
			"블라인드 시간은 누적되지는 않으나 블라인드가",
			"해제되기 전에 타격을 받으면 지속 시간이 계속",
			"3초로 회복됩니다.");
		InitAbility(0, 0, true);
		EventManager.onEntityDamageByEntity.add(new EventData(this));
	}

	@Override
	public int A_Condition(Event event, int CustomData) {
		EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
		if(PlayerCheck(Event.getDamager()) && Event.getEntity() instanceof Player)
			return 0;
		
		return -1;
	}

	@Override
	public void A_Effect(Event event, int CustomData) {
		EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
		Player p = (Player)Event.getEntity();
		p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 0));
		AbilityBase.ShowEffect(p.getLocation(), Material.SOUL_SAND, Effect.POTION_BREAK);
	}
}
