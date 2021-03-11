package Xeon.VisualAbility.AbilityList;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MainModule.EventManager;
import Xeon.VisualAbility.MinerModule.EventData;

import java.util.Objects;

public class Counter extends AbilityBase {
	public Counter(){
		InitAbility("카운터", Type.Passive_AutoMatic, Rank.B,
			"자신에게 공격받은 사람은 방어구의 내구도가",
			"급격하게 떨어지게 되며 0.5초간 이동속도가 크게",
			"감소합니다. 10% 확률로 3의 추가 데미지를 줄 수 있고",
			"10% 확률로 상대의 체력을 4 회복시킵니다.");
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
		p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,  10, 3));
		
		for(ItemStack is : p.getInventory().getArmorContents())
			((Damageable) Objects.requireNonNull(is.getItemMeta())).setDamage((int) ((short) ((Damageable)is.getItemMeta()).getDamage() + Event.getDamage() + 4));
		
		double rand = Math.random();
		if(rand >= 0.0 && rand <= 0.2){
			if(Math.random() >= 0.5){
				Event.setDamage(Event.getDamage() + 3);
				AbilityBase.ShowEffect(p.getLocation(), Material.LAVA, Effect.POTION_BREAK);
			}else if(p.getHealth() <= 16){
				p.setHealth(p.getHealth() + 4);
				AbilityBase.ShowEffect(p.getLocation(), Material.WATER, Effect.POTION_BREAK);
			}else{
				p.setHealth(20);
				AbilityBase.ShowEffect(p.getLocation(), Material.WATER, Effect.POTION_BREAK);
			}
		}
	}
}
