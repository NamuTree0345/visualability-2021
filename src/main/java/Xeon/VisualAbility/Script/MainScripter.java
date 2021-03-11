package Xeon.VisualAbility.Script;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import Xeon.VisualAbility.VisualAbility;
import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MainModule.CommandManager;
import Xeon.VisualAbility.MainModule.EventManager;
import Xeon.VisualAbility.MajorModule.AbilityList;
import Xeon.VisualAbility.MinerModule.AUC;
import Xeon.VisualAbility.MinerModule.CommandInterface;


public class MainScripter implements CommandInterface{
	public enum ScriptStatus{
		NoPlay,
		ScriptStart,
		AbilitySelect,
		GameStart;
	}
	
	public static ScriptStatus Scenario = ScriptStatus.NoPlay;
	public LinkedList<Player> ExceptionList = new LinkedList<Player>();
	public static ArrayList<Player> PlayerList = new ArrayList<Player>();
	public ArrayList<Player> OKSign = new ArrayList<Player>();
	public VisualAbility va;
	
	public World gameworld;
	
	public S_GameReady s_GameReady;
	public S_GameStart s_GameStart;
	public S_GameProgress s_GameProgress;
	public S_GameCoordinates s_GameCoordinates;
	public S_GameWarnning s_GameWarnning;
	
	public MainScripter(VisualAbility va, CommandManager cm){
		this.va = va;
		cm.RegisterCommand(this);
		
		s_GameReady = new S_GameReady(this);
		s_GameStart = new S_GameStart(this);
		s_GameProgress = new S_GameProgress(this);
		s_GameCoordinates = new S_GameCoordinates();
		s_GameWarnning = new S_GameWarnning(this);
	}
	
	@Override
	public boolean onCommandEvent(CommandSender sender, Command command,
			String label, String[] data) {
		
		if(sender instanceof Player){
			Player p = (Player)sender;
			
			if(data[0].equalsIgnoreCase("help")){
				AUC.InfoTextOut(p);}
			
			else if(data[0].equalsIgnoreCase("start")){
				gameworld = p.getWorld();
				s_GameReady.GameReady(p);
			}
			
			else if(data[0].equalsIgnoreCase("ob")){
				vaob(p);}
			
			else if(data[0].equalsIgnoreCase("yes")){
				vayes(p);}
			
			else if(data[0].equalsIgnoreCase("no")){
				vano(p);}
			
			else if(data[0].equalsIgnoreCase("stop")){
				vastop(p);}
			
			else if(data[0].equalsIgnoreCase("alist")){
				vaalist(p);}
			
			else if(data[0].equalsIgnoreCase("elist")){
				vaelist(p);}
			
			else if(data[0].equalsIgnoreCase("tc")){
				vatc(p);}
			
			else if(data[0].equalsIgnoreCase("kill")){
				vakill(p, data);}
			
			else if(data[0].equalsIgnoreCase("debug")){
				vadebug(p);}
			
			else if(data[0].equalsIgnoreCase("skip")){
				vaskip(p);}
			
			else if(data[0].equalsIgnoreCase("uti")){
				vauti(p);}
			
			else if(data[0].equalsIgnoreCase("abi")){
				vaabi(p, data);}
			
			else if(data[0].equalsIgnoreCase("ablist")){
				vaablist(p, data);}
			
			return true;
		}
		
		if(data[0].equalsIgnoreCase("help"))
			sender.sendMessage("프롬프트에서는 사용할수 없는 명령입니다.");
		
		else if(data[0].equalsIgnoreCase("start"))
			sender.sendMessage("프롬프트에서는 사용할수 없는 명령입니다.");
		
		else if(data[0].equalsIgnoreCase("ob"))
			sender.sendMessage("프롬프트에서는 사용할수 없는 명령입니다.");

		else if(data[0].equalsIgnoreCase("yes"))
			sender.sendMessage("프롬프트에서는 사용할수 없는 명령입니다.");
		
		else if(data[0].equalsIgnoreCase("no"))
			sender.sendMessage("프롬프트에서는 사용할수 없는 명령입니다.");
		
		else if(data[0].equalsIgnoreCase("stop")){
			vastop(sender);}
		
		else if(data[0].equalsIgnoreCase("alist")){
			vaalist(sender);}
		
		else if(data[0].equalsIgnoreCase("elist")){
			vaelist(sender);}
		
		else if(data[0].equalsIgnoreCase("tc")){
			vatc(sender);}
		
		else if(data[0].equalsIgnoreCase("kill")){
			vakill(sender, data);}
		
		else if(data[0].equalsIgnoreCase("debug")){
			vadebug(sender);}
		
		else if(data[0].equalsIgnoreCase("skip")){
			vaskip(sender);}
		
		else if(data[0].equalsIgnoreCase("uti")){
			vauti(sender);}
		
		else if(data[0].equalsIgnoreCase("abi")){
			vaabi(sender, data);}
		
		else if(data[0].equalsIgnoreCase("ablist")){
			vaablist(sender, data);}
		
		return true;
	}
	
	public final void vaablist(CommandSender p, String[] d){
		if(p.isOp()){
			int page = 0;
			if(d.length == 2){
				try{
					page = Integer.valueOf(d[1]);
					if(page >= 0 && page <= 3){
						p.sendMessage(ChatColor.GOLD+"==== 능력 목록 및 코드 ====");
						p.sendMessage(String.format(ChatColor.AQUA+"페이지 %d...[0~3]", page));
						for(int code = page * 8; code < (page + 1) * 8; ++code){
							if(code < AbilityList.AbilityList.size()){
								AbilityBase a = AbilityList.AbilityList.get(code);
								p.sendMessage(String.format(ChatColor.GREEN+"[%d] "+ChatColor.WHITE+"%s", code, a.GetAbilityName()));
							}
						}
						p.sendMessage(ChatColor.GOLD+"================");
					}
					else{
						p.sendMessage(ChatColor.RED+"페이지가 올바르지 않습니다.");
					}
				}
				catch(NumberFormatException e){
					p.sendMessage(ChatColor.RED+"페이지가 올바르지 않습니다.");
					return;
				}
			}
			else{
				p.sendMessage(ChatColor.RED+"명령이 올바르지 않습니다.");
			}
		}
	}
	
	public final void vaabi(CommandSender p, String[] d){
		if(p.isOp()){
			if(d.length == 3){
				Player pn = Bukkit.getServer().getPlayerExact(d[1]);
				if(pn != null || d[1].equalsIgnoreCase("null")){
					int abicode = 0;
					try{
						abicode = Integer.valueOf(d[2]);
					}
					catch(NumberFormatException e){
						p.sendMessage(ChatColor.RED+"능력 코드가 올바르지 않습니다.");
						return;
					}
					if(abicode == -1){
						for(AbilityBase ab : AbilityList.AbilityList){
							if(ab.PlayerCheck(pn)){
								ab.SetPlayer(null, true);
							}
						}
						pn.sendMessage(ChatColor.RED+"당신의 능력이 모두 해제되었습니다.");
						p.sendMessage(String.format(ChatColor.GREEN+"%s"+ChatColor.WHITE+"님의 능력을 모두 해제했습니다."
								, pn.getName()));
						Bukkit.broadcastMessage(String.format(ChatColor.GOLD+"%s님이 누군가의 능력을 모두 해제했습니다.", p.getName()));
						return;
					}
					if(abicode >= 0 && abicode < AbilityList.AbilityList.size()){
						AbilityBase a = AbilityList.AbilityList.get(abicode);
						if(d[1].equalsIgnoreCase("null")){
							a.SetPlayer(null, true);
							p.sendMessage(String.format("%s 능력 초기화 완료", a.GetAbilityName()));
							return;
						}
						
						if(VisualAbility.AbilityOverLap){
							if(a.GetAbilityType() == AbilityBase.Type.Active_Continue
								|| a.GetAbilityType() == AbilityBase.Type.Active_Immediately){
								for(AbilityBase ab : AbilityList.AbilityList){
									if(ab.PlayerCheck(pn)){
										if(ab.GetAbilityType() == AbilityBase.Type.Active_Continue
												|| ab.GetAbilityType() == AbilityBase.Type.Active_Immediately){
											ab.SetPlayer(null, true);
										}
									}
								}
							}
						}
						else{
							for(AbilityBase ab : AbilityList.AbilityList){
								if(ab.PlayerCheck(pn)){
									ab.SetPlayer(null, true);
								}
							}
						}
						a.SetPlayer(pn, true);
						a.SetRunAbility(true);
						p.sendMessage(String.format(ChatColor.GREEN+"%s"+ChatColor.WHITE+"님에게 "+ChatColor.GREEN+
								"%s"+ChatColor.WHITE+" 능력 할당이 완료되었습니다.", pn.getName(), a.GetAbilityName()));
						Bukkit.broadcastMessage(String.format(ChatColor.GOLD+"%s님이 누군가에게 능력을 강제로 할당했습니다.", p.getName()));
						String s;
						if(p instanceof Player)
							s = p.getName();
						else
							s = "서버 개설자";
						VisualAbility.log.info(String.format("%s님이 %s님에게 %s 능력을 할당했습니다.", s, pn.getName(), a.GetAbilityName()));
					}
					else{
						p.sendMessage(ChatColor.RED+"능력 코드가 올바르지 않습니다.");
					}
				}
				else{
					p.sendMessage(ChatColor.RED+"존재하지 않는 플레이어입니다.");
				}
			}
			else{
				p.sendMessage(ChatColor.RED+"명령이 올바르지 않습니다.");
			}
		}
	}
	
	public final void vauti(CommandSender p){
		if(p.isOp()){
			p.sendMessage(ChatColor.GREEN+"VisualAbility Utility 도움말");
			p.sendMessage(ChatColor.YELLOW+"/va alist : "+ChatColor.WHITE+"능력자 목록을 봅니다. 옵 전용.");
			p.sendMessage(ChatColor.YELLOW+"/va elist : "+ChatColor.WHITE+"능력 확정이 안된 사람들을 보여줍니다. 옵 전용.");
			p.sendMessage(ChatColor.YELLOW+"/va ablist [페이지(0~2)] : "+ChatColor.WHITE+"능력 목록 및 능력 코드를 보여줍니다. 옵 전용.");
			p.sendMessage(ChatColor.YELLOW+"/va abi [닉네임] [능력 코드] : "+
			ChatColor.WHITE+"특정 플레이어에게 능력을 강제로 할당합니다. 같은 능력을 "+
			"여럿이서 가질수는 없으며 이미 할당된 능력을 타인에게 " +
			"주면 기존에 갖고있던 사람의 능력은 사라지게 됩니다. " +
			"액티브 능력은 두 종류 이상 중복해서 줄수 없습니다. " +
			"게임을 시작하지 않더라도 사용이 가능한 명령입니다. " +
			"닉네임칸에 null을 쓰면 해당 능력에 등록된 플레이어가 " +
			"해제되며 명령 코드에 -1을 넣으면 해당 플레이어가 가진"+
			"모든 능력이 해제됩니다.");
		}
	}
	
	public final void vadebug(CommandSender p){
		if(p.isOp()){
			p.sendMessage(ChatColor.GREEN+"VisualAbility Debug 도움말");
			p.sendMessage(ChatColor.YELLOW+"/va tc : "+ChatColor.WHITE+"[Debug] 모든 능력의 지속 효과 및 쿨타임을 초기화 합니다.");
			p.sendMessage(ChatColor.YELLOW+"/va kill 닉네임 : "+ChatColor.WHITE+"[Debug] 플러그인 내에서 이 플레이어를 사망 처리합니다.");
			p.sendMessage(ChatColor.YELLOW+"/va skip : "+ChatColor.WHITE+"[Debug] 모든 능력을 강제로 확정시킵니다.");
		}
	}
	
	public final void vaskip(CommandSender p){
		if(p.isOp()){
			if(Scenario == ScriptStatus.AbilitySelect){
				Bukkit.broadcastMessage(String.format(ChatColor.GRAY+"[VA Debug] %s님이 능력을 강제로 확정시켰습니다.", p.getName()));
				OKSign.clear();
				for(Player pl : PlayerList){
					OKSign.add(pl);
				}
				s_GameStart.GameStart();
			}
			else{
				p.sendMessage(ChatColor.RED+"능력 추첨중이 아닙니다.");
			}
		}
	}
	
	public final void vatc(CommandSender p){
		if(p.isOp()){
			for(AbilityBase a : AbilityList.AbilityList){
				a.AbilityDTimerCancel();
				a.AbilityCTimerCancel();
			}
			Bukkit.broadcastMessage(String.format(ChatColor.GRAY+"[VA Debug] %s님이 쿨타임및 지속시간을 초기화했습니다.", p.getName()));
		}
	}
	
	public final void vakill(CommandSender p, String[] d){
		if(p.isOp()){
			if(d.length == 2){
				Player pn = Bukkit.getServer().getPlayerExact(d[1]);
				if(pn != null){
					AbilityBase a = AbilityBase.FindAbility(pn);
					if(a != null){
						a.AbilityDTimerCancel();
						a.AbilityCTimerCancel();
					}
					pn.damage(5000);
					pn.kickPlayer("[Debug] 관리자가 당신을 사망 처리했습니다.");
					Bukkit.broadcastMessage(
							String.format(ChatColor.GRAY+"[VA Debug] %s님이 %s님을 사망처리했습니다.", p.getName(), pn.getName()));
				}
			}
			else{
				p.sendMessage("명령이 올바르지 않습니다.");
			}
		}
	}
	
	public final void vaelist(CommandSender p){
		if(p.isOp()){
			if(Scenario == ScriptStatus.AbilitySelect){
				p.sendMessage(ChatColor.GOLD+"- 확정하지 않은 사람 -");
				p.sendMessage(ChatColor.GREEN+"---------------");
				List<AbilityBase> pl = AbilityList.AbilityList;
				int count=0;
				
				for(int l=0;l<pl.size();++l){
					if(pl.get(l).GetPlayer() != null){
						AbilityBase tempab = pl.get(l);
						if(!OKSign.contains(tempab.GetPlayer())){
							p.sendMessage(String.format(ChatColor.GREEN+"%d. "+ChatColor.WHITE+"%s",
									count, tempab.GetPlayer().getName()));
							++count;
						}
					}
				}
				p.sendMessage(ChatColor.GREEN+"---------------");
			}
			else{
				p.sendMessage(ChatColor.RED+"능력 추첨중에만 가능합니다.");
			}
		}
		else{
			p.sendMessage(ChatColor.RED+"당신은 권한이 없습니다. 관리자에게 OP 권한을 요청하세요.");
		}
	}
	
	public final void vaalist(CommandSender p){
		if(p.isOp()){
			Bukkit.broadcastMessage(
					String.format(ChatColor.GREEN+"%s님이 플레이어들의 능력을 확인했습니다.", p.getName()));
			p.sendMessage(ChatColor.GOLD+"- 능력을 스캔했습니다. -");
			p.sendMessage(ChatColor.GREEN+"---------------");
			List<AbilityBase> pl = AbilityList.AbilityList;
			int count=0;
			
			for(int l=0;l<pl.size();++l){
				if(pl.get(l).GetPlayer() != null){
					Player temp = Bukkit.getServer().getPlayer(pl.get(l).GetPlayer().getName());
					AbilityBase tempab = pl.get(l);
					if(temp != null){
						p.sendMessage(String.format(
								ChatColor.GREEN+"%d. "+ChatColor.WHITE+"%s : "
								+ChatColor.RED+"%s "+ChatColor.WHITE+"["+AUC.TypeTextOut(tempab)+"]",
								count, temp.getName(), tempab.GetAbilityName()));
						++count;
					}
				}
			}
			if(count==0)
				p.sendMessage("아직 능력자가 없습니다.");
			p.sendMessage(ChatColor.GREEN+"---------------");
		}
		else
			p.sendMessage(ChatColor.RED+"당신은 권한이 없습니다. 관리자에게 OP 권한을 요청하세요.");
	}
	
	public final void vastop(CommandSender p){
		if(p.isOp()){
			if(Scenario != ScriptStatus.NoPlay){
				S_GameStart.PlayDistanceBuffer = 0;
				Bukkit.broadcastMessage(ChatColor.GRAY+"------------------------------");
				if(Scenario != ScriptStatus.GameStart)
					Bukkit.broadcastMessage(
							String.format(ChatColor.YELLOW+"%s님이 게임 카운터를 중단시켰습니다.", p.getName()));
				else
					Bukkit.broadcastMessage(
							String.format(ChatColor.YELLOW+"%s님이 게임 카운터를 중단시켰습니다.", p.getName()));
				Scenario = ScriptStatus.NoPlay;
				s_GameReady.GameReadyStop();
				s_GameStart.GameStartStop();
				s_GameProgress.GameProgressStop();
				s_GameCoordinates.GameCoordinatesStop();
				s_GameWarnning.GameWarnningStop();
				Bukkit.broadcastMessage(ChatColor.GRAY+"모든 설정이 취소됩니다.");
				Bukkit.broadcastMessage(ChatColor.GREEN+"옵저버 설정은 초기화 되지 않습니다.");
				OKSign.clear();
				EventManager.DamageGuard = false;
				for(int l=0; l<AbilityList.AbilityList.size(); ++l){
					AbilityList.AbilityList.get(l).AbilityDTimerCancel();
					AbilityList.AbilityList.get(l).AbilityCTimerCancel();
					AbilityList.AbilityList.get(l).SetRunAbility(false);
					AbilityList.AbilityList.get(l).SetPlayer(null, false);
				}
				PlayerList.clear();
			}
			else
				p.sendMessage(ChatColor.RED+"아직 게임을 시작하지 않았습니다.");
		}
		else
			p.sendMessage(ChatColor.RED+"당신은 권한이 없습니다. 관리자에게 OP 권한을 요청하세요.");
	}
	
	public final void vaob(Player p){
		if(Scenario == ScriptStatus.NoPlay){
			if(ExceptionList.contains(p)){
				PlayerList.add(p);
				ExceptionList.remove(p);
				p.sendMessage(ChatColor.GREEN+"게임 예외처리가 해제되었습니다.");
			}
			else{
				ExceptionList.add(p);
				PlayerList.remove(p);
				p.sendMessage(ChatColor.GOLD+"게임 예외처리가 완료되었습니다.");
				p.sendMessage(ChatColor.GREEN+"/va ob을 다시 사용하시면 해제됩니다.");
			}
		}
		else
			p.sendMessage(ChatColor.RED+"게임 시작 이후는 옵저버 처리가 불가능합니다.");
	}
	
	public final void vayes(Player p){
		if(Scenario == ScriptStatus.AbilitySelect && !ExceptionList.contains(p) && !OKSign.contains(p)){
			OKSign.add(p);
			p.sendMessage(ChatColor.GOLD+"능력이 확정되었습니다. 다른 사람을 기다리세요.");
			Bukkit.broadcastMessage(String.format(
					ChatColor.YELLOW+"%s"+ChatColor.WHITE+"님이 능력을 확정했습니다.", p.getName()));
			Bukkit.broadcastMessage(String.format(
					ChatColor.GREEN+"남은 인원 : "+ChatColor.WHITE+"%d명", PlayerList.size() - OKSign.size()));
			if(OKSign.size() == PlayerList.size())
				s_GameStart.GameStart();
		}
	}
	
	public final void vano(Player p){
		if(Scenario == ScriptStatus.AbilitySelect && !ExceptionList.contains(p) && !OKSign.contains(p)){
			if(reRandomAbility(p) == null){
				p.sendMessage(ChatColor.RED+"경고, 능력의 갯수가 부족합니다.");
				return;
			}
			AUC.InfoTextOut(p);
			OKSign.add(p);
			p.sendMessage(ChatColor.GOLD+"능력이 자동으로 확정되었습니다. 다른 사람을 기다리세요.");
			Bukkit.broadcastMessage(String.format(
					ChatColor.YELLOW+"%s"+ChatColor.WHITE+"님이 능력을 확정했습니다.", p.getName()));
			Bukkit.broadcastMessage(String.format(
					ChatColor.GREEN+"남은 인원 : "+ChatColor.WHITE+"%d명", PlayerList.size() - OKSign.size()));
			if(OKSign.size() == PlayerList.size())
				s_GameStart.GameStart();
		}
	}
	
	private AbilityBase reRandomAbility(Player p){
		ArrayList<AbilityBase> Alist = new ArrayList<AbilityBase>();
		Random r = new Random();
		int Findex = r.nextInt(AbilityList.AbilityList.size() - 1);
		int saveindex;
		if(Findex == 0)
			saveindex = AbilityList.AbilityList.size();
		else
			saveindex = Findex - 1;
		
		for(int i = 0 ; i < AbilityList.AbilityList.size(); ++i){
			if(AbilityList.AbilityList.get(Findex).PlayerCheck(p)){
				AbilityList.AbilityList.get(Findex).SetPlayer(null, false);
			}
			else if(AbilityList.AbilityList.get(Findex).GetPlayer() == null){
				if(MainScripter.PlayerList.size() <= 6 && AbilityList.AbilityList.get(Findex) == AbilityList.mirroring){}
				else{
					Alist.add(AbilityList.AbilityList.get(Findex));}
			}
			++Findex;
			
			if(Findex == saveindex)
				break;
			else if(Findex == AbilityList.AbilityList.size())
				Findex = 0;
		}
		if(Alist.size() == 0)
			return null;
		
		if(Alist.size() == 1){
			Alist.get(0).SetPlayer(p, false);
			return Alist.get(0);
		}
		
		int ran2 = r.nextInt(Alist.size()-1);
		Alist.get(ran2).SetPlayer(p, false);
		return Alist.get(ran2);
	}
}