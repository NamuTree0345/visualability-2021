package Xeon.VisualAbility.Script;

import java.util.List;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import Xeon.VisualAbility.VisualAbility;
import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MainModule.EventManager;
import Xeon.VisualAbility.MajorModule.AbilityList;
import Xeon.VisualAbility.MinerModule.TimerBase;
import Xeon.VisualAbility.Script.MainScripter.ScriptStatus;

public final class S_GameStart {
	private MainScripter ms;
	private S_ScriptTimer stimer = new S_ScriptTimer();
	public static int PlayDistanceBuffer = 0;
	
	public S_GameStart(MainScripter ms){
		this.ms = ms;
	}
	
	public void GameStart(){
		stimer.StartTimer(15);
	}
	
	public void GameStartStop(){
		stimer.StopTimer();
		AbilityBase.restrictionTimer.StopTimer();
	}
	
	public final class S_ScriptTimer extends TimerBase {
		@Override
		public void EventStartTimer() {
			MainScripter.Scenario = ScriptStatus.GameStart;
		}

		@Override
		public void EventRunningTimer(int count) {
			switch(count){
			case 0:
				ms.s_GameWarnning.GameWarnningStop();
				break;
				
			case 3:
				Bukkit.broadcastMessage(ChatColor.WHITE+"모든 플레이어의 능력이 확정되었습니다.");
				Bukkit.broadcastMessage(ChatColor.GREEN+"참고로 능력은 중복되지 않습니다.");
				break;
			case 5:
				Bukkit.broadcastMessage(ChatColor.YELLOW+"10초 후 게임이 시작될것입니다. 게임 시작시 능력 활성화 및");
				Bukkit.broadcastMessage(ChatColor.YELLOW+"플레이어 상태가 초기화 되고 옵션이 적용될것입니다.");
				break;
			case 10:
				Bukkit.broadcastMessage(ChatColor.GOLD+"5초 전");
				break;
			case 11:
				Bukkit.broadcastMessage(ChatColor.GOLD+"4초 전");
				break;
			case 12:
				Bukkit.broadcastMessage(ChatColor.GOLD+"3초 전");
				break;
			case 13:
				Bukkit.broadcastMessage(ChatColor.GOLD+"2초 전");
				break;
			case 14:
				Bukkit.broadcastMessage(ChatColor.GOLD+"1초 전");
				break;
			case 15:
				Bukkit.broadcastMessage(ChatColor.GREEN+"게임이 시작되었습니다. 월드 초기화 완료.");
				Bukkit.broadcastMessage(ChatColor.YELLOW+"서버 로그에 모두의 능력 목록이 남습니다.");
				int c = 0;
				VisualAbility.log.info("------ 능력 로그 -------");
				for(AbilityBase a : AbilityList.AbilityList){
					if(a.GetPlayer() != null){
						VisualAbility.log.info(String.format("%d. %s - %s", c,
								a.GetPlayer().getName(), a.GetAbilityName()));
						++c;
					}
				}
				VisualAbility.log.info("-------------------------");
				if(VisualAbility.Invincibility){
					Bukkit.broadcastMessage("시작 직후 "+String.valueOf(VisualAbility.EarlyInvincibleTime)+"분간은 무적입니다.");
					EventManager.DamageGuard = VisualAbility.DebugMode ? false : true;
				}
				else{
					Bukkit.broadcastMessage(ChatColor.RED+"초반 무적은 작동하지 않습니다.");
				}
				
				RespawnTeleport();
				PlayDistanceBuffer = MainScripter.PlayerList.size() * 50;
				List<World> w = Bukkit.getWorlds();
				for(World wl : w){
					wl.setTime(1);
					wl.setStorm(false);
					if(VisualAbility.AutoDifficultySetting)
						wl.setDifficulty(Difficulty.EASY);
					wl.setWeatherDuration(0);
					wl.setSpawnFlags(wl.getAllowMonsters(), !VisualAbility.NoAnimal);
					wl.setPVP(true);
				}
				for(AbilityBase b : AbilityList.AbilityList){
					b.SetRunAbility(true);
					b.SetPlayer(b.GetPlayer(), false);
				}
				ms.s_GameProgress.GameProgress();
				if(VisualAbility.AutoCoordinateOutput)
					ms.s_GameCoordinates.GameCoordinates();
				break;
			}
		}

		@Override
		public void EventEndTimer() {
		}
	}
	
	private void RespawnTeleport(){
		Location l = ms.gameworld.getSpawnLocation();
		l.setY(ms.gameworld.getHighestBlockYAt((int)l.getX(), (int)l.getZ()));
		
		for(Player p : MainScripter.PlayerList){
			p.setFoodLevel(20);
			p.setLevel(0);
			p.setExhaustion(0.0F);
			p.setExp(0.0F);
			p.setHealth(20);
			p.setSaturation(10.0F);
			if(!VisualAbility.NoClearInventory)
				p.getInventory().clear();
			if(VisualAbility.Respawn){
				p.teleport(l);
			}
			if(VisualAbility.DefaultArmed){
				p.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET, 1));
				p.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE, 1));
				p.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS, 1));
				p.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS, 1));
				p.getInventory().setItem(0, new ItemStack(Material.WOODEN_SWORD, 1));
			}
			else if(!VisualAbility.NoClearInventory){
				p.getInventory().setHelmet(null);
				p.getInventory().setChestplate(null);
				p.getInventory().setLeggings(null);
				p.getInventory().setBoots(null);
			}
			if(VisualAbility.MaxLevelSurvival){
				p.getInventory().addItem(new ItemStack(Material.ENCHANTED_BOOK, 1));
				p.getInventory().addItem(new ItemStack(Material.BOOKSHELF, 64));
				p.setLevel(105);
			}
		}
		if(VisualAbility.DefaultArmed){
			Bukkit.broadcastMessage(ChatColor.GREEN+"기본 무장이 제공됩니다.");
		}
		else{
			Bukkit.broadcastMessage(ChatColor.RED+"기본 무장이 제공되지 않습니다.");
		}
		if(VisualAbility.MaxLevelSurvival){
			Bukkit.broadcastMessage(ChatColor.GREEN+"만렙 서바이벌 모드입니다. 아이템 제공.");
		}
			
		for(Player p : ms.ExceptionList)
			p.teleport(l);
	}
}
