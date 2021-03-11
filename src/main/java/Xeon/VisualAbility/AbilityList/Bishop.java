package Xeon.VisualAbility.AbilityList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MainModule.EventManager;
import Xeon.VisualAbility.MinerModule.ACC;
import Xeon.VisualAbility.MinerModule.EventData;

public class Bishop extends AbilityBase{
	public Bishop(){
		InitAbility("비숍", Type.Active_Immediately, Rank.B,
			"철괴 왼클릭시 맞은 사람에게 각종 축복을 겁니다.",
			"철괴 오른클릭시 자신에게 각종 축복을 겁니다.",
			"금괴를 적에게 왼클릭시 각종 저주를 겁니다.",
			"세 기능은 쿨타임을 공유하며 모든 효과 지속시간은",
			"15초입니다.");
		InitAbility(40, 0, true);
		EventManager.onEntityDamageByEntity.add(new EventData(this));
		RegisterRightClickEvent();
	}
	
	@Override
	public int A_Condition(Event event, int CustomData){
		switch(CustomData){
		case 0:
			EntityDamageByEntityEvent Event1 = (EntityDamageByEntityEvent)event;
			if(Event1.getEntity() instanceof Player){
				if(PlayerCheck(Event1.getDamager())){
					if(ItemCheck(ACC.DefaultItem))
						return 0;
					else if(ItemCheck(Material.GOLD_INGOT))
						return 2;
				}
			}
			break;
		case 1:
			PlayerInteractEvent Event2 = (PlayerInteractEvent)event;
			if(PlayerCheck(Event2.getPlayer())){
				if(ItemCheck(ACC.DefaultItem))
					return 1;
			}
			break;
		}
		return -1;
	}

	@Override
	public void A_Effect(Event event, int CustomData){
		int durationTime = 15 * 20;
		switch(CustomData){
		case 0:
			EntityDamageByEntityEvent Event0 = (EntityDamageByEntityEvent)event;
			Player p0 = (Player)Event0.getEntity();
			p0.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, durationTime, 1));
			p0.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, durationTime, 1));
			p0.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, durationTime, 1));
			p0.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, durationTime, 1));
			p0.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, durationTime, 1));
			p0.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, durationTime, 1));
			p0.sendMessage(ChatColor.GREEN+"비숍이 당신에게 축복을 걸었습니다. 15초 지속.");
			Event0.setCancelled(true);
			break;
			
		case 1:
			PlayerInteractEvent Event1 = (PlayerInteractEvent)event;
			Player p1 = Event1.getPlayer();
			p1.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, durationTime, 1));
			p1.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, durationTime, 1));
			p1.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, durationTime, 1));
			p1.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, durationTime, 1));
			p1.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, durationTime, 1));
			p1.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, durationTime, 1));
			p1.sendMessage(ChatColor.GREEN+"자신에게 축복을 걸었습니다. 15초 지속.");
			break;
			
		case 2:
			EntityDamageByEntityEvent Event2 = (EntityDamageByEntityEvent)event;
			Player p2 = (Player)Event2.getEntity();
			p2.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, durationTime, 1));
			p2.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, durationTime, 1));
			p2.addPotionEffect(new PotionEffect(PotionEffectType.POISON, durationTime, 1));
			p2.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, durationTime, 1));
			p2.sendMessage(ChatColor.RED+"비숍이 당신에게 저주를 걸었습니다. 15초 지속.");
			break;
		}
	}
}
