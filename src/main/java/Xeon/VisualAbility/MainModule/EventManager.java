package Xeon.VisualAbility.MainModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import Xeon.VisualAbility.VisualAbility;
import Xeon.VisualAbility.MainModule.AbilityBase.Type;
import Xeon.VisualAbility.MajorModule.AbilityList;
import Xeon.VisualAbility.MinerModule.EventData;
import Xeon.VisualAbility.Script.MainScripter;
import Xeon.VisualAbility.Script.MainScripter.ScriptStatus;
import org.bukkit.inventory.meta.Damageable;

public class EventManager implements Listener {
	public static ArrayList<AbilityBase> LeftHandEvent = new ArrayList<>();
	public static ArrayList<AbilityBase> RightHandEvent = new ArrayList<>();
	public static boolean DamageGuard = false;
	
	public static HashMap<Player, ItemStack[]> invsave = new HashMap<>();
	public static HashMap<Player, ItemStack[]> arsave = new HashMap<>();
	
	//onEntityDamage, onEntityDamageByEntity
	public static ArrayList<EventData> onEntityDamage = new ArrayList<>();
	public static ArrayList<EventData> onEntityDamageByEntity = new ArrayList<>();
	@EventHandler
	public static void onEntityDamage(EntityDamageEvent event){
		if(event.getEntity() instanceof Player){
			if(DamageGuard){
				event.setCancelled(true);
				event.getEntity().setFireTicks(0);
			}
			if(VisualAbility.InfinityDur){
				try {
					Player p = (Player) event.getEntity();
					PlayerInventory inv = p.getInventory();
					Damageable chestPlate = (Damageable) Objects.requireNonNull(inv.getChestplate()).getItemMeta();
					Damageable helmet = (Damageable) Objects.requireNonNull(inv.getHelmet()).getItemMeta();
					Damageable leggings = (Damageable) Objects.requireNonNull(inv.getLeggings()).getItemMeta();
					Damageable boots = (Damageable) Objects.requireNonNull(inv.getBoots()).getItemMeta();
					if (chestPlate != null)
						chestPlate.setDamage((short) 0);

					if (helmet != null)
						helmet.setDamage((short) 0);

					if (leggings != null)
						leggings.setDamage((short) 0);

					if (boots != null)
						boots.setDamage((short) 0);
				} catch (ClassCastException ignored) {}
			}
		}
		AbilityExcuter(onEntityDamage, event);
		
		if(event instanceof EntityDamageByEntityEvent){
			EntityDamageByEntityEvent Event = (EntityDamageByEntityEvent)event;
			if(VisualAbility.HalfMonsterDamage && !(Event.getDamager() instanceof Player))
				Event.setDamage(Event.getDamage() / 2);
			AbilityExcuter(onEntityDamageByEntity, event);
		}
	}
	
	
	//onEntityTarget
	public static ArrayList<EventData> onEntityTarget = new ArrayList<>();
	@EventHandler
	public static void onEntityTarget(EntityTargetEvent event){
		AbilityExcuter(onEntityTarget, event);
	}
	
	
	//onFoodLevelChange
	public static ArrayList<EventData> onFoodLevelChange = new ArrayList<>();
	@EventHandler
	public static void onFoodLevelChange(FoodLevelChangeEvent event){
		if(VisualAbility.NoFoodMode){
			event.setFoodLevel(20);
			event.setCancelled(true);
			return;
		}
		AbilityExcuter(onFoodLevelChange, event);
	}
	
	
	//onEntityRegainHealth
	public static ArrayList<EventData> onEntityRegainHealth = new ArrayList<>();
	@EventHandler
	public static void onEntityRegainHealth(EntityRegainHealthEvent event){
		AbilityExcuter(onEntityRegainHealth, event);
	}
	
	
	//onPlayerDropItem
	public static ArrayList<EventData> onPlayerDropItem = new ArrayList<>();
	@EventHandler
	public static void onPlayerDropItem(PlayerDropItemEvent event){
		if(VisualAbility.IngyoeMan_Patch){
			if(event.getItemDrop().getItemStack().getType() == Material.SLIME_BALL){
				event.getPlayer().sendMessage(ChatColor.RED+"슬라임볼은 버릴 수 없습니다.");
				event.setCancelled(true);
				return;
			}
		}
		AbilityExcuter(onPlayerDropItem, event);
	}
	
	
	//onPlayerRespawn
	public static ArrayList<EventData> onPlayerRespawn = new ArrayList<>();
	@EventHandler
	public static void onPlayerRespawn(PlayerRespawnEvent event){
		if(VisualAbility.InventorySave && !AbilityList.phoenix.PlayerCheck(event.getPlayer())){
			ItemStack[] ar = arsave.get(event.getPlayer());
			ItemStack[] inv = invsave.get(event.getPlayer());
			
			if(ar != null)
				event.getPlayer().getInventory().setArmorContents(ar);
			
			if(inv != null)
				event.getPlayer().getInventory().setContents(inv);
			
			arsave.remove(event.getPlayer());
			invsave.remove(event.getPlayer());
		}
		
		if(VisualAbility.IngyoeMan_Patch)
			event.getPlayer().getInventory().addItem(new ItemStack(Material.SLIME_BALL, 1));
		
		AbilityExcuter(onPlayerRespawn, event);
	}
	
	
	//onEntityDeathEvent
	public static ArrayList<EventData> onEntityDeath = new ArrayList<>();
	@EventHandler
	public static void onEntityDeath(EntityDeathEvent event){
		AbilityExcuter(onEntityDeath, event);
		
		if(MainScripter.Scenario == ScriptStatus.GameStart && event instanceof PlayerDeathEvent){
			PlayerDeathEvent pde = (PlayerDeathEvent)event;
			Player killed = (Player)event.getEntity();
			Player killerP = killed.getKiller();

			if(VisualAbility.IngyoeMan_Patch){
				List<ItemStack> itemlist = pde.getDrops();
				int size = itemlist.size();
				for(int i=0; i<itemlist.size(); ++i){
					if(itemlist.get(i).getType() == Material.SLIME_BALL){
						itemlist.remove(i);
						size--;
						i--;
					}
				}
			}
			
			if(VisualAbility.InventorySave && !AbilityList.phoenix.PlayerCheck(killed)){
				invsave.put(killed, killed.getInventory().getContents());
				arsave.put(killed, killed.getInventory().getArmorContents());
				pde.getDrops().clear();
			}
			
			if (event.getEntity().getKiller() != null){
				
				killed.getInventory().setHelmet(null);
				killed.getInventory().setChestplate(null);
				killed.getInventory().setLeggings(null);
				killed.getInventory().setBoots(null);
				killed.getInventory().clear();
				
				if(VisualAbility.AutoKick && !AbilityList.phoenix.PlayerCheck(killed)){
					if(VisualAbility.AutoBan){
						Bukkit.getBanList(BanList.Type.NAME).addBan(killed.getName(), "당신은 죽었습니다. 다시 들어오실 수 없습니다.", null, killed.getName());
						killed.kickPlayer("당신은 죽었습니다. 다시 들어오실 수 없습니다.");
					}
					else{
						killed.kickPlayer("당신은 죽었습니다. 게임에서 퇴장합니다.");
					}
				}
				
				VisualAbility.log.info(pde.getDeathMessage());
				if(VisualAbility.KillerOutput){
					assert killerP != null;
					pde.setDeathMessage(String.format
						(ChatColor.GREEN+"%s"+ChatColor.WHITE+"님이 "+
							ChatColor.RED+"%s"+ChatColor.WHITE+"님을 죽였습니다.",
								killerP.getName(), killed.getName()));}
				else{
					pde.setDeathMessage(String.format
							(ChatColor.RED+"%s"+ChatColor.WHITE+"님이 누군가에게 살해당했습니다.",
									killed.getName()));
				}
			}
		}
	}

	//onPlayerInteract
	public static ArrayList<EventData> onPlayerInteract = new ArrayList<>();
	@EventHandler
	public static void onPlayerInteract(PlayerInteractEvent event){
		_AbilityEventFilter(event);
		try {
			if (VisualAbility.InfinityDur)
				((Damageable) Objects.requireNonNull(event.getPlayer().getInventory().getItemInMainHand().getItemMeta())).setDamage((short) 0);
		} catch (ClassCastException ignored) {}
		AbilityExcuter(onPlayerInteract, event);
	}
	
	//onPlayerMove
	public static ArrayList<EventData> onPlayerMoveEvent = new ArrayList<>();
	@EventHandler
	public static void onPlayerMove(PlayerMoveEvent event){
		AbilityExcuter(onPlayerMoveEvent, event);
	}
	
	
	//onBlockBreakEvent
	public static ArrayList<EventData> onBlockBreakEvent = new ArrayList<>();
	@EventHandler
	public static void onBlockBreakEvent(BlockBreakEvent event){
		AbilityExcuter(onBlockBreakEvent, event);
	}
	
	
	//onProjectileHit
	public static ArrayList<EventData> onProjectileHitEvent = new ArrayList<>();
	@EventHandler
	public static void onProjectileHit(ProjectileHitEvent event){
		AbilityExcuter(onProjectileHitEvent, event);
	}
	
	
	private static void AbilityExcuter(ArrayList<EventData> ED, Event event){
		for(EventData ed : ED){
			if(ed.ab.GetAbilityType() == Type.Active_Continue){
				if(ed.ab.AbilityDuratinEffect(event, ed.parameter)){
					return;}
			}
			else{
				if(ed.ab.AbilityExcute(event, ed.parameter)){
					return;
				}
			}
		}
	}
	
	
	private static void _AbilityEventFilter(PlayerInteractEvent event){
		int i=0;
		if(event.getAction().equals(Action.LEFT_CLICK_AIR) || 
				event.getAction().equals(Action.LEFT_CLICK_BLOCK)){
			while(i<LeftHandEvent.size() && LeftHandEvent.size() != 0){
				if(LeftHandEvent.get(i).AbilityExcute(event, 0)){
					return;
				}
				++i;
			}
		}
		
		else if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || 
				event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			while(i<RightHandEvent.size() && RightHandEvent.size() != 0){
				if(RightHandEvent.get(i).AbilityExcute(event, 1)){
					return;
				}
				++i;
			}
		}
	}
}
