package Xeon.VisualAbility.AbilityList;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import Xeon.VisualAbility.VisualAbility;
import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MinerModule.CommandInterface;

public class DeathNote extends AbilityBase implements CommandInterface {
	private boolean LockAbility=false;
	private AbilityBase dead;
	private DeadTimer deadtimer;
	
	private CommandSender sender;
	private String[] data;
	
	public DeathNote(){
		if(VisualAbility.SRankUsed && !VisualAbility.NoDeathNote){
			InitAbility("데스 노트", Type.Passive_Manual, Rank.S,
				"1회에 한하여 한 사람의 능력을 무시하고 죽입니다.",
				"손에 팻말을 든 채로 \"/va dt 닉네임\" 명령을",
				"사용하십시오. 닉네임은 대소문자를 구분하지 않습니다.",
				"30초후 죽게되며 일단 데스노트가 작동하면 취소가 불가능합니다.",
				"능력 제한 시간동안은 이 능력을 사용할 수 없습니다.",
				"사망자에겐 엄청난 폭발이 발생하며 아이템은 모두 증발합니다.");
			InitAbility(0, 0, true);
			cm.RegisterCommand(this);
		}
	}

	@Override
	public int A_Condition(Event event, int CustomData){
		Player p = (Player)sender;
		if(PlayerCheck(p)){
			if(ItemCheck(Material.OAK_SIGN)){
				if(Bukkit.getServer().getPlayerExact(data[1]) != null){
					Player pn = Bukkit.getServer().getPlayerExact(data[1]);
					assert pn != null;
					if(p.getName().equals(pn.getName())){
						p.sendMessage(ChatColor.RED+"자기 자신에게 능력을 사용할수 없습니다.");
						data = null;
						return -1;
					}
					if(AbilityBase.FindAbility(pn) != null){
						if(!AbilityBase.restrictionTimer.GetTimerRunning()){
							if(!LockAbility){
								dead = AbilityBase.FindAbility(pn);
								p.sendMessage(ChatColor.GREEN+"능력이 작동되었습니다.");
								p.sendMessage(ChatColor.WHITE+"30초 후 사망할것입니다.");
								AbilityBase.ShowEffect(p.getLocation(), Material.WATER, Effect.POTION_BREAK);
								data = null;
								return 0;
							}
							else
								p.sendMessage(ChatColor.RED+"데스노트를 이미 사용했습니다.");
						}
						else
							p.sendMessage(ChatColor.RED+"아직 능력을 사용할 수 없습니다.");
					}
					else
						p.sendMessage(ChatColor.RED+"옵저버에게는 사용할 수 없습니다.");
				}
				else
					p.sendMessage(ChatColor.RED+"존재하지 않는 플레이어 입니다.");
			}
			else
				p.sendMessage(ChatColor.RED+"손에 팻말을 들고 있어야 사용할수 있습니다.");
		}
		data = null;
		return -1;
	}

	@Override
	public void A_Effect(Event event, int CustomData){
		LockAbility = true;
		dead.GetPlayer().sendMessage(ChatColor.RED+"당신에게 검은 기운이 느껴집니다.");
		deadtimer = new DeadTimer(this, dead);
		deadtimer.DeadTimerStart();
	}
	
	@Override
	public void A_SetEvent(Player p){
		if(deadtimer != null)
			deadtimer.DeadTimerEnd();
		LockAbility = false;
	}
	
	@Override
	public void A_ResetEvent(Player p){
		if(deadtimer != null)
			deadtimer.DeadTimerEnd();
		LockAbility = false;
	}
	
	private class DeadTimer{
		private Timer timer;
		private AbilityBase deathnote;
		private AbilityBase Dead;
		private int Count = 0;
		
		public DeadTimer(AbilityBase deathnote, AbilityBase Dead){
			this.deathnote = deathnote;
			this.Dead = Dead;
		}
		
		public void DeadTimerStart(){
			Count = 30;
			timer = new Timer();
			timer.schedule(new DeatTimerTask(deathnote, Dead), 0, 1000);
		}
		
		public void DeadTimerEnd(){
			timer.cancel();
			Count = 0;
		}
		
		private class DeatTimerTask extends TimerTask{
			private AbilityBase deathnote;
			private AbilityBase Dead;
			
			public DeatTimerTask(AbilityBase deathnote, AbilityBase Dead){
				this.deathnote = deathnote;
				this.Dead = Dead;
			}
			@Override
			public void run(){
				--Count;
				if(Count <= 0){
					DeadTimerEnd();
					Dead.AbilityDTimerCancel();
					Dead.AbilityCTimerCancel();
					Dead.SetRunAbility(false);
					Dead.SetRunAbility(true);
					Bukkit.broadcastMessage(String.format(ChatColor.RED+
							"%s님이 %s님의 DeathNote 능력에 의해 사망했습니다.", Dead.GetPlayer().getName(), deathnote.GetPlayer().getName()));
					
					Player p = Bukkit.getPlayerExact(Dead.GetPlayer().getName());
					if(p == null){
						Bukkit.broadcastMessage("능력 회피로 간주하여 피해자는 밴 처리 됩니다.");
						OfflinePlayer op = Bukkit.getOfflinePlayer(Dead.GetPlayer().getUniqueId());
						Bukkit.getBanList(BanList.Type.NAME).addBan(Objects.requireNonNull(op.getName()), "능력 회피", null, op.getName());
					} else {
						
						p.getInventory().clear();
						p.getInventory().setHelmet(null);
						p.getInventory().setChestplate(null);
						p.getInventory().setLeggings(null);
						p.getInventory().setBoots(null);
						p.getWorld().createExplosion(p.getLocation(), 10.0F);
						p.getWorld().strikeLightning(p.getLocation());
						p.getWorld().strikeLightning(p.getLocation());
						p.getWorld().strikeLightning(p.getLocation());
						p.getWorld().strikeLightning(p.getLocation());
						p.damage(5000);
						
						if(VisualAbility.AutoKick){
							Dead.GetPlayer().kickPlayer("데스노트에 의해 사망했습니다.");
							if(VisualAbility.AutoBan)
								Bukkit.getBanList(BanList.Type.NAME).addBan(Objects.requireNonNull(Dead.GetPlayer().getName()), "데스노트에 의해 사망했습니다.", null, Dead.GetPlayer().getName());
						}
					}
				}
			}

		}
	}

	@Override
	public boolean onCommandEvent(CommandSender sender, Command command,
			String label, String[] data) {
		this.sender = sender;
		this.data = data;
		if(data[0].equalsIgnoreCase("dt") && data.length == 2){
			this.AbilityExcute(null);
			return true;
		}
		return false;
	}
}
