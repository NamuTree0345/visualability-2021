package Xeon.VisualAbility.AbilityList;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MainModule.EventManager;
import Xeon.VisualAbility.MinerModule.EventData;
import Xeon.VisualAbility.MinerModule.TimerBase;

public class HyperVisor extends AbilityBase {
	HyperVisorTimer HVT = new HyperVisorTimer(this);
	
	public HyperVisor(){
		InitAbility("하이퍼 바이저", Type.Passive_AutoMatic, Rank.B,
			"밤에도 낮처럼 환하게 볼 수 있습니다.",
			"철과 금을 제련된 상태로 채집할 수 있습니다.",
			"단, 철원석과 금원석으로부터 경험치를 얻지 못합니다.",
			"칼로 막기를 사용하면 모든 종류의 데미지를",
			"75% 줄여받습니다.");
		InitAbility(0, 0, true);
		EventManager.onEntityDamage.add(new EventData(this, 5));
		EventManager.onBlockBreakEvent.add(new EventData(this, 6));
	}

	@Override
	public int A_Condition(Event event, int CustomData) {
		switch(CustomData){
		
		case 5:
			EntityDamageEvent Event5 = (EntityDamageEvent)event;
			if(PlayerCheck(Event5.getEntity())){
				Player p = (Player)Event5.getEntity();
				if(p.isBlocking())
					return 5;
			}
			break;
			
		case 6:
			BlockBreakEvent Event6 = (BlockBreakEvent)event;
			if(PlayerCheck(Event6.getPlayer())){
				if(Event6.getBlock().getType() == Material.IRON_ORE ||
					Event6.getBlock().getType() == Material.GOLD_ORE){
						return 6;
				}
			}
			break;
		}
		return -1;
	}

	@Override
	public void A_Effect(Event event, int CustomData) {
		switch(CustomData){
		case 5:
			EntityDamageEvent Event5 = (EntityDamageEvent)event;
			Event5.setDamage(Event5.getDamage() / 4);
			AbilityBase.ShowEffect(Event5.getEntity().getLocation(), Material.DIAMOND_BLOCK, Effect.POTION_BREAK);
			break;
			
		case 6:
			BlockBreakEvent Event6 = (BlockBreakEvent)event;
			if(Event6.getBlock().getType() == Material.IRON_ORE){
				Event6.getBlock().setType(Material.AIR);
				Event6.getBlock().getWorld().dropItem(Event6.getBlock().getLocation(), new ItemStack(Material.IRON_INGOT, 1))
					.setVelocity(new Vector(0, 0, 0));
				
			} else if (Event6.getBlock().getType() == Material.GOLD_ORE){
				Event6.getBlock().setType(Material.AIR);
				Event6.getBlock().getWorld().dropItem(Event6.getBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, 1))
					.setVelocity(new Vector(0, 0, 0));
			}
			break;
		}
	}
	
	@Override
	public void A_SetEvent(Player p){
		HVT.StartTimer(-1);
	}
	
	@Override
	public void A_ResetEvent(Player p){
		HVT.StopTimer();
		p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 0, 0));
	}
	
	public class HyperVisorTimer extends TimerBase {
		
		HyperVisor HV;
		
		public HyperVisorTimer(HyperVisor hv){
			HV = hv;
		}
		
		@Override
		public void EventStartTimer() {}

		@Override
		public void EventRunningTimer(int count) {
			if(HV.GetPlayer() != null){
				HV.GetPlayer().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 5000, 0));
			}
		}

		@Override
		public void EventEndTimer() {}
	}
}
