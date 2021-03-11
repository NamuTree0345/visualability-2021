package Xeon.VisualAbility.Script;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import Xeon.VisualAbility.VisualAbility;
import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MainModule.EventManager;
import Xeon.VisualAbility.MinerModule.TimerBase;

public class S_GameProgress {
	private MainScripter ms;
	private S_ScriptTimer stimer = new S_ScriptTimer();
	private int EarlyInvincibleTime = 300;
	private boolean gcon = false;
	
	public S_GameProgress(MainScripter ms){
		this.ms = ms;
		EarlyInvincibleTime = VisualAbility.EarlyInvincibleTime * 60;
	}
	
	public void GameProgress(){
		stimer.StartTimer(99999999);
	}
	
	public void GameProgressStop(){
		gcon = false;
		stimer.StopTimer();
	}
	
	public final class S_ScriptTimer extends TimerBase {
		@Override
		public void EventStartTimer() {
		}

		@Override
		public void EventRunningTimer(int count) {
		switch(count){
			case 8:
				Bukkit.broadcastMessage(ChatColor.AQUA+"=== 서버 세팅 상태 ===");
				if(VisualAbility.Invincibility){
					Bukkit.broadcastMessage("시작후 초반 무적 "+ChatColor.GREEN+"ON");
					Bukkit.broadcastMessage(ChatColor.GREEN+"초반 무적 시간 : "+ChatColor.WHITE+String.valueOf(VisualAbility.EarlyInvincibleTime));
				}
				else{
					Bukkit.broadcastMessage("초반 무적 "+ChatColor.RED+"OFF");}
				
				Bukkit.broadcastMessage(ChatColor.GREEN+"일부 능력 제약 시간 : "+ChatColor.WHITE+String.valueOf(VisualAbility.RestrictionTime));

				
				if(VisualAbility.DefaultArmed)
					Bukkit.broadcastMessage("기본 무장 제공 "+ChatColor.GREEN+"ON");
				else
					Bukkit.broadcastMessage("기본 무장 제공 "+ChatColor.RED+"OFF");
				
				if(VisualAbility.SRankUsed)
					Bukkit.broadcastMessage("S랭크 능력 사용 "+ChatColor.GREEN+"ON");
				else
					Bukkit.broadcastMessage("S랭크 능력 사용 "+ChatColor.RED+"OFF");
				
				if(VisualAbility.Respawn)
					Bukkit.broadcastMessage("시작시 리스폰으로 이동 "+ChatColor.GREEN+"ON");
				else
					Bukkit.broadcastMessage("시작시 리스폰으로 이동 "+ChatColor.RED+"OFF");
				
				if(VisualAbility.AutoKick){
					Bukkit.broadcastMessage("사망시 자동으로 킥 "+ChatColor.GREEN+"ON");
					if(VisualAbility.AutoBan)
						Bukkit.broadcastMessage(ChatColor.GREEN+"사망시 자동으로 밴 "+ChatColor.WHITE+"ON");
					else
						Bukkit.broadcastMessage(ChatColor.RED+"사망시 자동으로 밴 "+ChatColor.WHITE+"OFF");
				}
				else
					Bukkit.broadcastMessage("사망시 자동으로 킥 "+ChatColor.RED+"OFF");
				
				if(VisualAbility.HalfMonsterDamage)
					Bukkit.broadcastMessage("몬스터의 공격력 반감 "+ChatColor.GREEN+"ON");
				else
					Bukkit.broadcastMessage("몬스터의 공격력 반감 "+ChatColor.RED+"OFF");
				
				if(VisualAbility.AutoDifficultySetting)
					Bukkit.broadcastMessage("월드 난이도 자동 Easy 설정 "+ChatColor.GREEN+"ON");
				else
					Bukkit.broadcastMessage("월드 난이도 자동 Easy 설정 "+ChatColor.RED+"OFF");
				
				if(VisualAbility.MaxLevelSurvival)
					Bukkit.broadcastMessage("만렙 서바이벌 모드 "+ChatColor.GREEN+"ON");
				else
					Bukkit.broadcastMessage("만렙 서바이벌 모드 "+ChatColor.RED+"OFF");
				
				break;
				
			case 16:
				Bukkit.broadcastMessage(ChatColor.AQUA+"=== 서버 세팅 상태 ===");
				
				if(VisualAbility.NoFoodMode)
					Bukkit.broadcastMessage("배고픔 무한 모드 "+ChatColor.GREEN+"ON");
				else
					Bukkit.broadcastMessage("배고픔 무한 모드 "+ChatColor.RED+"OFF");
				
				if(VisualAbility.KillerOutput)
					Bukkit.broadcastMessage("죽인 사람 출력 "+ChatColor.GREEN+"ON");
				else
					Bukkit.broadcastMessage("죽인 사람 출력 "+ChatColor.RED+"OFF");
				
				if(VisualAbility.AutoCoordinateOutput)
					Bukkit.broadcastMessage("좌표 자동 출력 "+ChatColor.GREEN+"ON");
				else
					Bukkit.broadcastMessage("좌표 자동 출력 "+ChatColor.RED+"OFF");
				
				if(VisualAbility.NoAnimal)
					Bukkit.broadcastMessage("동물 비활성화 "+ChatColor.GREEN+"ON");
				else
					Bukkit.broadcastMessage("동물 비활성화 "+ChatColor.RED+"OFF");
				
				if(VisualAbility.NoClearInventory)
					Bukkit.broadcastMessage("시작시 인벤토리 초기화 안함 "+ChatColor.GREEN+"ON");
				else
					Bukkit.broadcastMessage("시작시 인벤토리 초기화 안함 "+ChatColor.RED+"OFF");
				
				if(VisualAbility.PrintTip)
					Bukkit.broadcastMessage("팁 출력함 "+ChatColor.GREEN+"ON");
				else
					Bukkit.broadcastMessage("팁 출력함 "+ChatColor.RED+"OFF");
				
				if(VisualAbility.AutoSave)
					Bukkit.broadcastMessage("서버 오토 세이브 "+ChatColor.GREEN+"ON");
				else
					Bukkit.broadcastMessage("서버 오토 세이브 "+ChatColor.RED+"OFF");
				
				if(VisualAbility.InventorySave)
					Bukkit.broadcastMessage("인벤토리 세이브 "+ChatColor.GREEN+"ON");
				else
					Bukkit.broadcastMessage("인벤토리 세이브 "+ChatColor.RED+"OFF");
				
				break;
				
			case 24:
				Bukkit.broadcastMessage(ChatColor.AQUA+"=== 서버 세팅 상태 ===");
				
				if(VisualAbility.AbilityOverLap)
					Bukkit.broadcastMessage("능력 중복 가능 "+ChatColor.GREEN+"ON");
				else
					Bukkit.broadcastMessage("능력 중복 가능 "+ChatColor.RED+"OFF");
				
				if(VisualAbility.InfinityDur)
					Bukkit.broadcastMessage("내구도 무한 "+ChatColor.GREEN+"ON");
				else
					Bukkit.broadcastMessage("내구도 무한 "+ChatColor.RED+"OFF");
				break;
			}
		
			if(VisualAbility.PrintTip)
				PrintTip(count);
		
			if(count > 20 && count % 15 == 0){
				ms.gameworld.setStorm(false);
				if(gcon)
					System.gc();
			}
			
			if(count > 20 && count % 600 == 0){
				Bukkit.broadcastMessage(String.format(ChatColor.BLUE+"[VisualAbility]"));
				Bukkit.broadcastMessage(String.format(ChatColor.GRAY+"빌드 넘버 : %d", VisualAbility.BuildNumber));
				if(VisualAbility.ProtoType)
					Bukkit.broadcastMessage(ChatColor.GRAY+"ProtoType Test Version");
			}
			
			if(count > 20 && count % 120 == 0){
				if(VisualAbility.AutoSave)
					Bukkit.getServer().savePlayers();
				
				double JVMm = (double)Runtime.getRuntime().totalMemory() / (1024 * 1024);
				double Freem = (double)Runtime.getRuntime().freeMemory() / (1024 * 1024);
				double Usem = JVMm - Freem;
				
				if(count % 600 == 0){
					Bukkit.broadcastMessage(String.format(ChatColor.GREEN+"JVM 가용 메모리 : "+ChatColor.WHITE+"%.2f MB", JVMm));
					if(Freem < 375.0){
						Bukkit.broadcastMessage(String.format(ChatColor.RED+"사용중인 메모리 : "+ChatColor.WHITE+"%.2f MB", Usem));
						Bukkit.broadcastMessage(String.format(ChatColor.RED+"남은 메모리 : "+ChatColor.WHITE+"%.2f MB", Freem));
						Bukkit.broadcastMessage(ChatColor.RED+"메모리 소모량이 너무 많습니다. 서버가 불시에");
						Bukkit.broadcastMessage(ChatColor.RED+"중단될 수 있습니다. 메모리 정리중...");
					}
					else{
						Bukkit.broadcastMessage(String.format(ChatColor.GREEN+"사용중인 메모리 : "+ChatColor.WHITE+"%.2f MB", Usem));
						Bukkit.broadcastMessage(String.format(ChatColor.GREEN+"남은 메모리 : "+ChatColor.WHITE+"%.2f MB", Freem));
					}
				}
				if(Freem < 375.0){
					System.gc();
					gcon = true;
				}
				else{
					gcon = false;
				}
			}
			
			if(VisualAbility.Invincibility && EarlyInvincibleTime > 60
					&& (EarlyInvincibleTime - 60) == count)
				Bukkit.broadcastMessage(ChatColor.YELLOW+"초반 무적이 "+ChatColor.WHITE+"1분후 해제됩니다.");
			
			if(VisualAbility.Invincibility && (EarlyInvincibleTime - 5) == count)
				Bukkit.broadcastMessage(ChatColor.YELLOW+"초반 무적 해제까지"+ChatColor.WHITE+" 5초전");
			
			if(VisualAbility.Invincibility && (EarlyInvincibleTime - 4) == count)
				Bukkit.broadcastMessage(ChatColor.YELLOW+"초반 무적 해제까지"+ChatColor.WHITE+" 4초전");
			
			if(VisualAbility.Invincibility && (EarlyInvincibleTime - 3) == count)
				Bukkit.broadcastMessage(ChatColor.YELLOW+"초반 무적 해제까지"+ChatColor.WHITE+" 3초전");
			
			if(VisualAbility.Invincibility && (EarlyInvincibleTime - 2) == count)
				Bukkit.broadcastMessage(ChatColor.YELLOW+"초반 무적 해제까지"+ChatColor.WHITE+" 2초전");
			
			if(VisualAbility.Invincibility && (EarlyInvincibleTime - 1) == count)
				Bukkit.broadcastMessage(ChatColor.YELLOW+"초반 무적 해제까지"+ChatColor.WHITE+" 1초전");
			
			if(VisualAbility.Invincibility && EarlyInvincibleTime == count){
				Bukkit.broadcastMessage(ChatColor.GREEN+"초반 무적이 해제되었습니다.");
				EventManager.DamageGuard = false;
				if(VisualAbility.RestrictionTime != 0){
					Bukkit.broadcastMessage(String.format(ChatColor.YELLOW+"%d분 제약 카운트가 시작되었습니다.",
							VisualAbility.RestrictionTime));
					AbilityBase.restrictionTimer.StartTimer(VisualAbility.RestrictionTime*60);
				}else
					Bukkit.broadcastMessage(ChatColor.YELLOW+"제약 카운트는 동작하지 않습니다.");
			}
		}

		@Override
		public void EventEndTimer() {
		}
		
		public void PrintTip(int c){
			switch(c){
			case 27:
				Bukkit.broadcastMessage(ChatColor.AQUA+"*** TIP ***");
				Bukkit.broadcastMessage("능력은 액티브 능력과 패시브 능력으로 나뉩니다.");
				break;
			
			case 30:	
				Bukkit.broadcastMessage(ChatColor.AQUA+"*** TIP ***");
				Bukkit.broadcastMessage("액티브 능력의 특징은 사용자가 원할때 사용할수 있다는 점입니다.");
				Bukkit.broadcastMessage("액티브 능력은 반드시 "+ChatColor.GREEN+"철괴"+ChatColor.WHITE+"를 소유해야 능력을 쓸 수 있습니다.");
				Bukkit.broadcastMessage("별도의 언급이 없는 한 액티브 능력은 철괴를 든 채로 마우스");
				Bukkit.broadcastMessage("왼클릭으로 작동합니다. 철괴는 소모되지 않습니다.");
				break;
			
			case 45:
				Bukkit.broadcastMessage(ChatColor.AQUA+"*** TIP ***");
				Bukkit.broadcastMessage("액티브 능력은 지속형과 즉발형으로 나뉩니다.");
				Bukkit.broadcastMessage("지속형은 사용시 특정한 지속 효과가 작동하며");
				Bukkit.broadcastMessage("즉발형은 사용 즉시 효과가 작동됩니다.");
				break;
			
			case 60:
				Bukkit.broadcastMessage(ChatColor.AQUA+"*** TIP ***");
				Bukkit.broadcastMessage("패시브 능력의 특징은 철괴를 사용하지 않으며 쿨타임이 없습니다.");
				Bukkit.broadcastMessage("자동형과 수동형 패시브로 나뉩니다.");
				Bukkit.broadcastMessage("자동형은 이유불문 그냥 무제한으로 작동하며");
				Bukkit.broadcastMessage("수동형은 특정한 조건이 만족되어야 효과가 발생합니다.");
				break;
				
			case 75:
				Bukkit.broadcastMessage(ChatColor.AQUA+"*** TIP ***");
				Bukkit.broadcastMessage("능력 분류와 관련된 간략한 설명이 끝났습니다.");
				Bukkit.broadcastMessage("다시 말씀드리지만 능력 확인은 /va help로 하실수 있습니다.");
				Bukkit.broadcastMessage("이후에는 간단한 팁이 나오게 됩니다.");
				break;
				
			case 95:
				Bukkit.broadcastMessage(ChatColor.AQUA+"*** TIP ***");
				Bukkit.broadcastMessage("게임중에는 비나 눈 날씨를 차단합니다.");
				Bukkit.broadcastMessage("따라서 기상 변화가 거의 없습니다.");
				break;
				
			case 115:
				Bukkit.broadcastMessage(ChatColor.AQUA+"*** TIP ***");
				Bukkit.broadcastMessage("게임중에는 일정시간마다 모두의 좌표가 공개됩니다.");
				Bukkit.broadcastMessage("시간 텀은 10분부터 시작해서 1분씩 감소하며 3분까지 감소합니다.");
				break;
	
			case 145:
				Bukkit.broadcastMessage(ChatColor.AQUA+"*** TIP ***");
				Bukkit.broadcastMessage("자나깨나 불, 용암 조심!");
				Bukkit.broadcastMessage("내 발밑, 내 코앞도 두드려보자.");
				Bukkit.broadcastMessage(ChatColor.GRAY+"물론 관련 능력자 빼구요...");
				break;
				
			case 175:
				Bukkit.broadcastMessage(ChatColor.AQUA+"*** TIP ***");
				Bukkit.broadcastMessage("다시 말씀드리지만 자신의 능력은 /va help 명령으로");
				Bukkit.broadcastMessage("확인이 가능합니다.");
				break;
				
			case 205:
				Bukkit.broadcastMessage(ChatColor.AQUA+"*** TIP ***");
				Bukkit.broadcastMessage("자신의 능력이 액티브 능력일 시 설명에 언급이 없다면");
				Bukkit.broadcastMessage("철괴를 손에 들고 왼클릭을 하시면 사용됩니다.");
				break;
				
			case 235:
				Bukkit.broadcastMessage(ChatColor.AQUA+"*** TIP ***");
				Bukkit.broadcastMessage("서버가 꺼질시 능력이 그대로 증발해버립니다. 주의하세요.");
				Bukkit.broadcastMessage("단, 플레이어의 재접속 정도는 능력이 초기화 되지 않습니다.");
				Bukkit.broadcastMessage("또한 서버 관리자는 플러그인 폴더 내의 설정 파일에서");
				Bukkit.broadcastMessage("다양한 옵션을 설정할 수 있습니다.");
				break;
				
			case 265:
				Bukkit.broadcastMessage(ChatColor.AQUA+"*** TIP ***");
				Bukkit.broadcastMessage("패시브 능력은 앉아만 있어도 능력이 활성화 되어 있습니다.");
				break;
				
			case 360:
				Bukkit.broadcastMessage(ChatColor.AQUA+"*** TIP ***");
				Bukkit.broadcastMessage("\"아 아까 떴던 좌표를 어떻게 봐야되지?\"");
				Bukkit.broadcastMessage("채팅을 위해 창을 켜면 채팅 로그가 보입니다!");
				break;
				
			case 450:
				Bukkit.broadcastMessage(ChatColor.AQUA+"*** TIP ***");
				Bukkit.broadcastMessage("\"아 내 능력은 게임 닉네임을 써야되잖아?\"");
				Bukkit.broadcastMessage("TAB키(기본설정)를 누르면 닉네임이 나옵니다");
				break;
				
			case 540:
				Bukkit.broadcastMessage(ChatColor.AQUA+"*** TIP ***");
				Bukkit.broadcastMessage("창고를 애용합시다. 자연사 해도");
				Bukkit.broadcastMessage("든든한 창고가 당신의 멘탈을 지켜줍니다.");
				break;
				
			case 630:
				Bukkit.broadcastMessage(ChatColor.AQUA+"*** TIP ***");
				Bukkit.broadcastMessage("능력의 랭크는 절대적이지 않습니다.");
				Bukkit.broadcastMessage("얼마든지 당신의 랭크는 무장과 컨트롤로 극복이 가능함을");
				Bukkit.broadcastMessage("잊지 마시고 역전을 노려보세요.");
				break;
				
			case 720:
				Bukkit.broadcastMessage(ChatColor.AQUA+"*** TIP ***");
				Bukkit.broadcastMessage("쉬프트 + F3을 누르면 좌표를 깨끗하게 띄울수 있습니다.");
				break;
				
			case 810:
				Bukkit.broadcastMessage(ChatColor.AQUA+"*** TIP ***");
				Bukkit.broadcastMessage("방송과 마인크래프트 서버를 함께 켜게 되면 두 프로그램이");
				Bukkit.broadcastMessage("발생시키는 트래픽으로 인해 문제가 생길수 있습니다.");
				Bukkit.broadcastMessage("고사양 컴퓨터가 아니라면 별도로 돌리미를 두시기 바랍니다.");
				break;
			
			case 900:
				Bukkit.broadcastMessage(ChatColor.AQUA+"*** TIP ***");
				Bukkit.broadcastMessage("당신의 능력이 알게 모르게 패치되었을지도 모릅니다.");
				Bukkit.broadcastMessage("능력 설명을 반드시 읽어주시기 바랍니다.");
				Bukkit.broadcastMessage("더불어 액티브 능력은 별도의 언급이 없는 한 철괴를 사용합니다.");
				break;
				
			case 990:
				Bukkit.broadcastMessage(ChatColor.AQUA+"*** TIP ***");
				Bukkit.broadcastMessage("대부분의 상황에서는 선제공격을 하는것이 더 유리합니다.");
				Bukkit.broadcastMessage("물론 역관광을 당할수도 있으니 대비하는것이 좋습니다.");
				break;
				
			case 1080:
				Bukkit.broadcastMessage(ChatColor.AQUA+"*** TIP ***");
				Bukkit.broadcastMessage("자연사당하면 결국 억울한것은 본인입니다.");
				Bukkit.broadcastMessage("반드시 주의하시기 바랍니다. 저도 많이 죽었어요.");
				Bukkit.broadcastMessage("낙사와 용암이 정말 사람 혈압오르게 합니다. 네.");
				break;
				
			case 1170:
				Bukkit.broadcastMessage(ChatColor.AQUA+"*** TIP ***");
				Bukkit.broadcastMessage("토르등의 능력은 2차 피해로 공격하는 능력입니다.");
				Bukkit.broadcastMessage("이런 능력의 경우 공격시 플러그인이 사망 처리를");
				Bukkit.broadcastMessage("올바르게 할수 없습니다. 여러분이 판단해주세요.");
				break;
				
			case 1260:
				Bukkit.broadcastMessage(ChatColor.AQUA+"*** TIP ***");
				Bukkit.broadcastMessage("카페 위치가 변경되었습니다. 더이상 Wiki 카페는 활동하지 않습니다.");
				Bukkit.broadcastMessage("새로운 카페 주소 : http://cafe.naver.com/craftproducer");
				break;
			}
		}
	}
}