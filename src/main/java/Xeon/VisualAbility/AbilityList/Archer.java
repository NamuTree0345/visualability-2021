package Xeon.VisualAbility.AbilityList;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MainModule.EventManager;
import Xeon.VisualAbility.MinerModule.EventData;


public class Archer extends AbilityBase {
	public Archer(){
		InitAbility("아쳐", Type.Passive_Manual, Rank.A,
			"죽거나 게임 시작시 화살 한묶음이 고정적으로 주어집니다.",
			"60% 확률로 불화살을 쏘며 나머지 17% 확률로 약한 TNT",
			"화살을 쏩니다. 불화살의 불은 6초간 지속됩니다.",
			"화살 데미지가 3 상승합니다.");
		InitAbility(0, 0, true);
		EventManager.onEntityDamageByEntity.add(new EventData(this, 0));
		EventManager.onPlayerDropItem.add(new EventData(this, 1));
		EventManager.onPlayerRespawn.add(new EventData(this, 2));
		EventManager.onEntityDeath.add(new EventData(this, 3));
	}

	@Override
	public int A_Condition(Event event, int CustomData){
		switch(CustomData){
		case 0:
			EntityDamageByEntityEvent Event0 = (EntityDamageByEntityEvent)event;
			if(Event0.getDamager() instanceof Arrow){
	    		Arrow a = (Arrow) Event0.getDamager();
	    		if(PlayerCheck((Entity) a.getShooter())){
	    			if(Event0.getEntity() instanceof Player && (Player)a.getShooter() == (Player)Event0.getEntity())
	    				return 9999;
	    			return 0;
	    		}
	    	}
			break;
		case 1:
			PlayerDropItemEvent Event1 = (PlayerDropItemEvent)event;
			if(PlayerCheck(Event1.getPlayer()) &&
					Event1.getItemDrop().getItemStack().getType() == Material.ARROW){
				PlayerInventory inv = Event1.getPlayer().getInventory();
				if(!inv.contains(Material.ARROW, 64)){
					return 1;
				}
			}
			break;
		case 2:
			PlayerRespawnEvent Event2 = (PlayerRespawnEvent)event;
			if(PlayerCheck(Event2.getPlayer()))
				return 2;
			break;
		case 3:
			EntityDeathEvent Event3 = (EntityDeathEvent)event;
			if(PlayerCheck(Event3.getEntity()))
				return 3;
			break;
		}
		return -1;
	}

	@Override
	public void A_Effect(Event event, int CustomData){
		switch(CustomData){
		case 0:
			EntityDamageByEntityEvent Event0 = (EntityDamageByEntityEvent)event;
			Event0.setDamage(Event0.getDamage() + 3);
			if(Math.random() <= 0.6)
				Event0.getEntity().setFireTicks(20*6);
			else if(Math.random() <= 0.4){
				World w = Event0.getEntity().getWorld();
				w.createExplosion(Event0.getEntity().getLocation(), 1.5F);
			}
			break;
		case 1:
			PlayerDropItemEvent Event1 = (PlayerDropItemEvent)event;
			Event1.getPlayer().sendMessage(ChatColor.RED+"소유한 화살이 64개 이하일시 화살을 버릴수 없습니다.");
			Event1.setCancelled(true);
			break;
		case 2:
			PlayerRespawnEvent Event2 = (PlayerRespawnEvent)event;
			Event2.getPlayer().sendMessage(ChatColor.GREEN+"이전에 소유했던 화살은 모두 소멸하며 다시 지급됩니다.");
			PlayerInventory inv = Event2.getPlayer().getInventory();
			inv.remove(new ItemStack(Material.ARROW, 64));
			inv.setItem(8, new ItemStack(Material.ARROW, 64));
			break;
		case 3:
			EntityDeathEvent Event3 = (EntityDeathEvent)event;
			List<ItemStack> itemlist = Event3.getDrops();
			for(int l=0; l<itemlist.size(); ++l){
				if(itemlist.get(l).getType() == Material.ARROW){
					itemlist.remove(l);
				}
			}
			break;
		}
	}
	
	@Override
	public void A_SetEvent(Player p){
		p.getInventory().setItem(8, new ItemStack(Material.ARROW, 64));
	}
	
	@Override
	public void A_ResetEvent(Player p){
		p.getInventory().removeItem(new ItemStack(Material.ARROW, 64));
	}
}
