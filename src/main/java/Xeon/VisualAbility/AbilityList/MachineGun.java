package Xeon.VisualAbility.AbilityList;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;

import Xeon.VisualAbility.VisualAbility;
import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MainModule.EventManager;
import Xeon.VisualAbility.MinerModule.EventData;

public class MachineGun extends AbilityBase {
	private int bullet = 0;
	private Material item;
	
	public MachineGun(){
		InitAbility("기관총", Type.Active_Immediately, Rank.A,
			"고속으로 화살을 발사합니다. 금괴를 들고 오른클릭을 누르면 연사가",
			"가능합니다. 철괴를 탄창으로 사용하며 한 탄창은 20발입니다.",
			"20발을 쏠때마다 철괴를 소모하여 자동으로 재장전 합니다.",
			"피격시 10% 확률로 방어력을 무시하고 체력을 2 감소시키는",
			"크리티컬이 발생합니다. 기본 탄환 데미지는 3입니다.",
			"마크는 공격받을시 1초간 무적이므로 감안하고 사용하세요.");
		InitAbility(0, 0, true, ShowText.Custom_Text);
		
		RegisterRightClickEvent();
		EventManager.onEntityDamageByEntity.add(new EventData(this, 3));
		EventManager.onProjectileHitEvent.add(new EventData(this, 5));
		if(VisualAbility.IngyoeMan_Patch)
			item = Material.SLIME_BALL;
		else
			item = Material.GOLD_INGOT;
	}

	@Override
	public int A_Condition(Event event, int CustomData){
		switch(CustomData){
		case 1:
			PlayerInteractEvent Event0 = (PlayerInteractEvent)event;
			if(PlayerCheck(Event0.getPlayer()) && ItemCheck(item)){
				if(bullet != 0){
					return 10;
				}
				else{
					if(GetPlayer().getInventory().contains(Material.IRON_INGOT)){
						return 20;
					}
					else{
						GetPlayer().sendMessage
						(ChatColor.RED+"탄창이 없습니다.");
					}
				}
				break;
			}
			break;
			
		case 3:
			EntityDamageByEntityEvent Event1 = (EntityDamageByEntityEvent)event;
			if(Event1.getDamager() instanceof Arrow){
	    		Arrow a = (Arrow) Event1.getDamager();
	    		if(PlayerCheck((Entity) a.getShooter())){
	    			if(Event1.getEntity() instanceof Player && (Player)a.getShooter() == (Player)Event1.getEntity())
	    				return -1;
	    			return 3;
	    		}
	    	}
			break;
			
		case 5:
			ProjectileHitEvent Event2 =  (ProjectileHitEvent)event;
			if(Event2.getEntity() instanceof Arrow){
				Arrow a = (Arrow)Event2.getEntity();
				if(a.getShooter() instanceof Player){
					if(PlayerCheck((Player)a.getShooter())){
						a.remove();
						AbilityBase.ShowEffect(a.getLocation(), Material.LAVA, null);
						return -2;
					}
				}
			}
			break;
		}
		return -1;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void A_Effect(Event event, int CustomData){
		switch(CustomData){
		case 3:
			EntityDamageByEntityEvent Event0 = (EntityDamageByEntityEvent)event;
			Event0.setDamage(3);
			if(Event0.getEntity() instanceof Player && Math.random() <= 0.1){
				Player p = (Player)Event0.getEntity();
				p.getWorld().createExplosion(Event0.getEntity().getLocation(), 0F);
				if(p.getHealth() > 2){
					p.setHealth(p.getHealth() - 2);}
				else{
					Arrow a = (Arrow)Event0.getDamager();
					p.damage(5000, (Entity) a.getShooter());}
			}
			break;
			
		case 10:
			PlayerInteractEvent Event1 = (PlayerInteractEvent)event;
			if(bullet % 5 == 0){
				Event1.getPlayer().sendMessage(
						String.format(ChatColor.AQUA+"남은 탄환 : "+ChatColor.WHITE+"%d개", bullet));
			}
			--bullet;
			Arrow arrow = Event1.getPlayer().launchProjectile(Arrow.class);
			AbilityBase.ShowEffect(Event1.getPlayer().getLocation(), null, Effect.BOW_FIRE);
			break;
		
		case 20:
			PlayerInteractEvent Event2 = (PlayerInteractEvent)event;
			PlayerInventory inv = Event2.getPlayer().getInventory();
			int sell = inv.first(Material.IRON_INGOT);
			if(inv.getItem(sell).getAmount() == 1){
				inv.clear(sell);
			}
			else{
				inv.getItem(sell).setAmount(inv.getItem(sell).getAmount() - 1);
			}
			Event2.getPlayer().updateInventory();
			bullet = 20;
			Event2.getPlayer().sendMessage(ChatColor.GREEN+"재장전 완료");
			AbilityBase.ShowEffect(Event2.getPlayer().getLocation(), null, Effect.BLAZE_SHOOT);
			break;
		}
	}
}
