package Xeon.VisualAbility.Script;

import java.util.ArrayList;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import Xeon.VisualAbility.VisualAbility;
import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MajorModule.AbilityList;
import Xeon.VisualAbility.MinerModule.AUC;
import Xeon.VisualAbility.MinerModule.TimerBase;
import Xeon.VisualAbility.Script.MainScripter.ScriptStatus;

public final class S_GameReady {
	private MainScripter mainscripter;
	private S_ScriptTimer stimer = new S_ScriptTimer();
	private int peoplecount = 0;
	
	public S_GameReady(MainScripter mainscripter){
		this.mainscripter = mainscripter;
	}
	
	public void GameReady(Player p){
		if(p.isOp()){
			if(MainScripter.Scenario == ScriptStatus.NoPlay){
				MainScripter.Scenario = ScriptStatus.ScriptStart;
				Bukkit.broadcastMessage(ChatColor.GRAY+"------------------------------");
				Bukkit.broadcastMessage(ChatColor.YELLOW+"관리자가 게임 카운터를 시작시켰습니다.");
				if(!VisualAbility.QuickStart)
					stimer.StartTimer(33);
				else
					stimer.StartTimer(9);
			}
			else
				p.sendMessage(ChatColor.RED+"이미 게임이 시작되어있습니다.");
		}
		else
			p.sendMessage(ChatColor.RED+"당신은 권한이 없습니다.");
	}
	
	public void GameReadyStop(){
		stimer.StopTimer();
	}
	
	public final class S_ScriptTimer extends TimerBase {
		@Override
		public void EventStartTimer() {
		}

		@Override
		public void EventRunningTimer(int count) {
			if(VisualAbility.DebugMode){
				Player[] templist = Bukkit.getOnlinePlayers().toArray(new Player[0]);
				for(int l=0; l < templist.length; ++l){
					if(!mainscripter.ExceptionList.contains(templist[l])){
						if(l < AbilityBase.GetAbilityCount()){
							MainScripter.PlayerList.add(templist[l]);
							Bukkit.broadcastMessage(String.format(ChatColor.GREEN+"%d. "+ChatColor.WHITE+"%s", l, templist[l].getName()));
						}
						else{
							Bukkit.broadcastMessage(String.format(ChatColor.RED+"%d. %s (Error)", l, templist[l].getName()));
						}
					}
				}
				peoplecount = templist.length - mainscripter.ExceptionList.size();
				if(peoplecount <= AbilityBase.GetAbilityCount()){
					Bukkit.broadcastMessage(String.format(ChatColor.YELLOW+"총 인원수 : %d명 ", peoplecount));}
				else{
					Bukkit.broadcastMessage(String.format(ChatColor.RED+"총 인원수 : %d명 ", peoplecount));
					Bukkit.broadcastMessage("인원이 능력의 갯수보다 많습니다. Error 처리된분들은 능력을");
					Bukkit.broadcastMessage("받을수 없으며 모든 게임 진행 대상에서 제외됩니다.");
				}
				Bukkit.broadcastMessage(ChatColor.GOLD+"==========");
				if(MainScripter.PlayerList.size() == 0){
					Bukkit.broadcastMessage(ChatColor.RED+"경고, 실질 플레이어가 없습니다. 게임 강제 종료.");
					MainScripter.Scenario = ScriptStatus.NoPlay;
					stimer.StopTimer();
					Bukkit.broadcastMessage(ChatColor.GRAY+"모든 설정이 취소됩니다.");
					MainScripter.PlayerList.clear();
					return;
				}
				for(AbilityBase ab : AbilityList.AbilityList){
					ab.SetRunAbility(false);
					ab.SetPlayer(null, false);
				}
				MainScripter.Scenario = ScriptStatus.AbilitySelect;
				for(Player p : MainScripter.PlayerList){
					if(RandomAbility(p) == null){
						p.sendMessage(ChatColor.RED+"경고, 능력의 갯수가 부족합니다.");
						continue;
					}
					AUC.InfoTextOut(p);
					p.sendMessage(ChatColor.YELLOW+"/va yes "+ChatColor.WHITE+"명령을 사용하면 이 능력을 사용합니다.");
					p.sendMessage(ChatColor.YELLOW+"/va no "+ChatColor.WHITE+"명령을 사용하시면 1회 다시 추첨합니다.");
				}
				for(Player p : mainscripter.ExceptionList){
					p.sendMessage(ChatColor.GREEN+"능력 추첨중입니다");
				}
				this.EndTimer();
				return;
			}
			
			if(!VisualAbility.QuickStart){
				switch(count){
				case 0:
					Bukkit.broadcastMessage(ChatColor.AQUA+"인식된 플레이어 목록");
					Bukkit.broadcastMessage(ChatColor.GOLD+"==========");
					Player[] templist = Bukkit.getOnlinePlayers().toArray(new Player[0]);
					for(int l=0; l < templist.length; ++l){
						if(!mainscripter.ExceptionList.contains(templist[l])){
							if(l < AbilityBase.GetAbilityCount()){
								MainScripter.PlayerList.add(templist[l]);
								Bukkit.broadcastMessage(String.format(ChatColor.GREEN+"%d. "+ChatColor.WHITE+"%s", l, templist[l].getName()));
							}
							else{
								Bukkit.broadcastMessage(String.format(ChatColor.RED+"%d. %s (Error)", l, templist[l].getName()));
							}
						}
					}
					
					if(VisualAbility.IngyoeMan_Patch)
						Bukkit.broadcastMessage(ChatColor.GOLD+"특정 BJ 전용 버전입니다.");
					
					peoplecount = templist.length - mainscripter.ExceptionList.size();
					if(peoplecount <= AbilityBase.GetAbilityCount()){
						Bukkit.broadcastMessage(String.format(ChatColor.YELLOW+"총 인원수 : %d명 ", peoplecount));}
					else{
						Bukkit.broadcastMessage(String.format(ChatColor.RED+"총 인원수 : %d명 ", peoplecount));
						Bukkit.broadcastMessage("인원이 능력의 갯수보다 많습니다. Error 처리된분들은 능력을");
						Bukkit.broadcastMessage("받을수 없으며 모든 게임 진행 대상에서 제외됩니다.");
					}
					if(MainScripter.PlayerList.size() == 0){
						Bukkit.broadcastMessage(ChatColor.RED+"경고, 실질 플레이어가 없습니다. 게임 강제 종료.");
						MainScripter.Scenario = ScriptStatus.NoPlay;
						stimer.StopTimer();
						Bukkit.broadcastMessage(ChatColor.GRAY+"모든 설정이 취소됩니다.");
						MainScripter.PlayerList.clear();
						return;
					}
					Bukkit.broadcastMessage(ChatColor.GOLD+"==========");
					break;
					
				case 6:
					Bukkit.broadcastMessage(ChatColor.GREEN+"서버 관리자는 플레이어 리스트를 확인해주세요.");
					Bukkit.broadcastMessage(ChatColor.GREEN+"리스트가 잘릴시 서버 로그에서도 보실수 있습니다.");
					Bukkit.broadcastMessage(ChatColor.GREEN+"현재 카운트는 관리자가 /va stop 명령으로 중지시킬수 있습니다.");
					break;
					
				case 12:
					Bukkit.broadcastMessage(ChatColor.YELLOW+"게임 참가를 원치 않을경우 /va ob 명령으로 능력 추첨에서");
					Bukkit.broadcastMessage(ChatColor.YELLOW+"제외되실수 있습니다. 게임 스타트 전에만 가능합니다.");
					Bukkit.broadcastMessage("스카이프등의 통신 기능을 지금 팀별로 나누시기 바랍니다.");
					break;
					
				case 16:
					Bukkit.broadcastMessage(ChatColor.RED+"최근 구 버킷에서 접속 장애가 자주 발생하고 있습니다.");
					Bukkit.broadcastMessage(ChatColor.RED+"버킷 자체의 결함이니 되도록 최신 버킷 및 1.2.4를 쓰세요.");
					Bukkit.broadcastMessage("카페 내에서 최신 버킷을 제공하고 있습니다.");
					break;
					
				case 23:
					Bukkit.broadcastMessage(ChatColor.WHITE+"잠시 후 랜덤으로 능력을 추첨할것입니다. 추첨중에는");
					Bukkit.broadcastMessage(ChatColor.WHITE+"자신에게 걸린 능력을 "+ChatColor.RED+"1회에 한해 변경"+ChatColor.WHITE+"이 가능합니다.");
					Bukkit.broadcastMessage(ChatColor.WHITE+"1회 변경시 이전에 걸린 능력은 다시 걸리지 않습니다.");
					break;
				case 27:
					Bukkit.broadcastMessage(String.format(ChatColor.BLUE+"[VisualAbility]"));
					Bukkit.broadcastMessage(String.format(ChatColor.GRAY+"빌드 넘버 : %d", VisualAbility.BuildNumber));
					Bukkit.broadcastMessage(ChatColor.GREEN+"http://cafe.naver.com/craftproducer");
					Bukkit.broadcastMessage(ChatColor.YELLOW+"버그나 기타 제보는 xeon0527@naver.com");
					Bukkit.broadcastMessage(ChatColor.AQUA+"만든 이 : "+ChatColor.WHITE+"Xeon (제온)");
					Bukkit.broadcastMessage(ChatColor.AQUA+"1.16.5로 포팅한 이 : "+ChatColor.WHITE+"NamuTree0345 (나무)");
					Bukkit.broadcastMessage(ChatColor.GOLD+"아프리카 ID : "+ChatColor.WHITE+"axzs3526");
					if(VisualAbility.ProtoType)
						Bukkit.broadcastMessage(ChatColor.GRAY+"[VisualAbility Debug] ProtoType 테스트 버전");
					Bukkit.broadcastMessage("이 프로그램은 공개적으로 배포되며 무단 수정이 허용됩니다.");
					break;
				case 32:
					if(!VisualAbility.NoAbilitySetting){
						Bukkit.broadcastMessage(ChatColor.GRAY+"능력 설정 초기화중...");
						Bukkit.broadcastMessage(ChatColor.YELLOW+"능력을 확정하지 않은 사람은 /va elist로 알 수 있습니다.");
						for(AbilityBase ab : AbilityList.AbilityList){
							ab.SetRunAbility(false);
							ab.SetPlayer(null, false);
						}
					}
					else{
						Bukkit.broadcastMessage(ChatColor.GOLD+"능력을 추첨하지 않습니다.");
						Bukkit.broadcastMessage("시작전에 능력이 이미 부여되었다면 보존됩니다.");
						mainscripter.OKSign.clear();
						for(Player pl : MainScripter.PlayerList){
							mainscripter.OKSign.add(pl);
						}
						for(AbilityBase ab : AbilityList.AbilityList){
							ab.SetRunAbility(true);
						}
						mainscripter.s_GameStart.GameStart();
						this.StopTimer();
					}
					break;
				case 33:
					MainScripter.Scenario = ScriptStatus.AbilitySelect;
					if(peoplecount < AbilityBase.GetAbilityCount()){
						for(Player p : MainScripter.PlayerList){
							if(RandomAbility(p) == null){
								p.sendMessage(ChatColor.RED+"경고, 능력의 갯수가 부족합니다.");
								continue;
							}
							p.sendMessage(ChatColor.GREEN+"임시로 능력이 할당되었습니다. "+ChatColor.YELLOW+"/va help"+ChatColor.WHITE+"로 확인하세요.");
							p.sendMessage(ChatColor.YELLOW+"/va yes "+ChatColor.WHITE+"명령을 사용하면 이 능력을 사용합니다.");
							p.sendMessage(ChatColor.YELLOW+"/va no "+ChatColor.WHITE+"명령을 사용하시면 1회 다시 추첨합니다.");
						}
						for(Player p : mainscripter.ExceptionList){
							p.sendMessage(ChatColor.GREEN+"능력 추첨중입니다");
						}
						mainscripter.s_GameWarnning.GameWarnningStart();
					}
					else{
						Bukkit.broadcastMessage(ChatColor.AQUA+"능력 갯수보다 플레이어 수가 같거나 많으므로 즉시 확정됩니다.");
						for(Player p : MainScripter.PlayerList){
							if(RandomAbility(p) == null){
								p.sendMessage(ChatColor.RED+"경고, 능력의 갯수가 부족합니다.");
								continue;
							}
							mainscripter.OKSign.add(p);
							p.sendMessage(ChatColor.GREEN+"당신에게 능력이 부여되었습니다. "+
									ChatColor.YELLOW+"/va help"+ChatColor.WHITE+"로 확인하세요.");
						}
						for(Player p : mainscripter.ExceptionList){
							p.sendMessage(ChatColor.GREEN+"능력 추첨 완료");
						}
						mainscripter.s_GameStart.GameStart();
					}
					break;
				}
			}
			else{
				switch(count){
				case 0:
					Bukkit.broadcastMessage(ChatColor.AQUA+"인식된 플레이어 목록");
					Bukkit.broadcastMessage(ChatColor.GOLD+"==========");
					Player[] templist = Bukkit.getOnlinePlayers().toArray(new Player[0]);
					for(int l=0; l < templist.length; ++l){
						if(!mainscripter.ExceptionList.contains(templist[l])){
							if(l < AbilityBase.GetAbilityCount()){
								MainScripter.PlayerList.add(templist[l]);
								Bukkit.broadcastMessage(String.format(ChatColor.GREEN+"%d. "+ChatColor.WHITE+"%s", l, templist[l].getName()));
							}
							else{
								Bukkit.broadcastMessage(String.format(ChatColor.RED+"%d. %s (Error)", l, templist[l].getName()));
							}
						}
					}
					
					if(VisualAbility.IngyoeMan_Patch)
						Bukkit.broadcastMessage(ChatColor.GOLD+"특정 BJ 전용 버전입니다.");
					
					peoplecount = templist.length - mainscripter.ExceptionList.size();
					if(peoplecount <= AbilityBase.GetAbilityCount()){
						Bukkit.broadcastMessage(String.format(ChatColor.YELLOW+"총 인원수 : %d명 ", peoplecount));}
					else{
						Bukkit.broadcastMessage(String.format(ChatColor.RED+"총 인원수 : %d명 ", peoplecount));
						Bukkit.broadcastMessage("인원이 능력의 갯수보다 많습니다. Error 처리된분들은 능력을");
						Bukkit.broadcastMessage("받을수 없으며 모든 게임 진행 대상에서 제외됩니다.");
					}
					Bukkit.broadcastMessage(ChatColor.GOLD+"==========");
					if(MainScripter.PlayerList.size() == 0){
						Bukkit.broadcastMessage(ChatColor.RED+"경고, 실질 플레이어가 없습니다. 게임 강제 종료.");
						MainScripter.Scenario = ScriptStatus.NoPlay;
						stimer.StopTimer();
						Bukkit.broadcastMessage(ChatColor.GRAY+"모든 설정이 취소됩니다.");
						MainScripter.PlayerList.clear();
						return;
					}
					break;
				case 3:
					Bukkit.broadcastMessage(String.format(ChatColor.BLUE+"[VisualAbility]"));
					Bukkit.broadcastMessage(String.format(ChatColor.GRAY+"빌드 넘버 : %d", VisualAbility.BuildNumber));
					Bukkit.broadcastMessage(ChatColor.GREEN+"http://cafe.naver.com/craftproducer");
					Bukkit.broadcastMessage(ChatColor.YELLOW+"버그나 기타 제보는 xeon0527@naver.com");
					Bukkit.broadcastMessage(ChatColor.AQUA+"만든 이 : "+ChatColor.WHITE+"Xeon (제온)");
					Bukkit.broadcastMessage(ChatColor.AQUA+"1.16.5로 포팅한 이 : "+ChatColor.WHITE+"NamuTree0345 (나무)");
					Bukkit.broadcastMessage(ChatColor.GOLD+"아프리카 ID : "+ChatColor.WHITE+"axzs3526");
					if(VisualAbility.ProtoType)
						Bukkit.broadcastMessage(ChatColor.GRAY+"[VisualAbility Debug] ProtoType 테스트 버전");
					Bukkit.broadcastMessage("이 프로그램은 공개적으로 배포되며 무단 수정이 허용됩니다.");
					break;
				case 7:
					if(!VisualAbility.NoAbilitySetting){
						Bukkit.broadcastMessage(ChatColor.GRAY+"능력 설정 초기화 및 추첨 준비...");
						for(AbilityBase ab : AbilityList.AbilityList){
							ab.SetRunAbility(false);
							ab.SetPlayer(null, false);
						}
					}
					else{
						Bukkit.broadcastMessage(ChatColor.GOLD+"능력을 추첨하지 않습니다.");
						Bukkit.broadcastMessage("시작전에 능력이 이미 부여되었다면 보존됩니다.");
						mainscripter.OKSign.clear();
						for(Player pl : MainScripter.PlayerList){
							mainscripter.OKSign.add(pl);
						}
						for(AbilityBase ab : AbilityList.AbilityList){
							ab.SetRunAbility(true);
						}
						mainscripter.s_GameStart.GameStart();
						this.StopTimer();
					}
					break;
				case 9:
					MainScripter.Scenario = ScriptStatus.AbilitySelect;
					if(peoplecount < AbilityBase.GetAbilityCount()){
						for(Player p : MainScripter.PlayerList){
							if(RandomAbility(p) == null){
								p.sendMessage(ChatColor.RED+"경고, 능력의 갯수가 부족합니다.");
								continue;
							}
							p.sendMessage(ChatColor.GREEN+"임시로 능력이 할당되었습니다. "+ChatColor.YELLOW+"/va help"+ChatColor.WHITE+"로 확인하세요.");
							p.sendMessage(ChatColor.YELLOW+"/va yes "+ChatColor.WHITE+"명령을 사용하면 이 능력을 사용합니다.");
							p.sendMessage(ChatColor.YELLOW+"/va no "+ChatColor.WHITE+"명령을 사용하시면 1회 다시 추첨합니다.");
						}
						for(Player p : mainscripter.ExceptionList){
							p.sendMessage(ChatColor.GREEN+"능력 추첨중입니다");
						}
						mainscripter.s_GameWarnning.GameWarnningStart();
					}
					else{
						Bukkit.broadcastMessage(ChatColor.AQUA+"능력 갯수보다 플레이어 수가 같거나 많으므로 즉시 확정됩니다.");
						for(Player p : MainScripter.PlayerList){
							if(RandomAbility(p) == null){
								p.sendMessage(ChatColor.RED+"경고, 능력의 갯수가 부족합니다.");
								continue;
							}
							mainscripter.OKSign.add(p);
							p.sendMessage(ChatColor.GREEN+"당신에게 능력이 부여되었습니다. "+
									ChatColor.YELLOW+"/va help"+ChatColor.WHITE+"로 확인하세요.");
						}
						for(Player p : mainscripter.ExceptionList){
							p.sendMessage(ChatColor.GREEN+"능력 추첨 완료");
						}
						mainscripter.s_GameStart.GameStart();
					}
				}
			}
		}

		@Override
		public void EventEndTimer() {
		}
		
		private AbilityBase RandomAbility(Player p){
			ArrayList<AbilityBase> Alist = new ArrayList<AbilityBase>();
			Random r = new Random();
			int Findex = r.nextInt(AbilityList.AbilityList.size() - 1);
			int saveindex;
			if(Findex == 0)
				saveindex = AbilityList.AbilityList.size();
			else
				saveindex = Findex - 1;
			
			for(int i = 0 ; i < AbilityList.AbilityList.size(); ++i){
				if(AbilityList.AbilityList.get(Findex).GetPlayer() == null){
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
}
