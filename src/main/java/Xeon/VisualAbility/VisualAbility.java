package Xeon.VisualAbility;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MainModule.CommandManager;
import Xeon.VisualAbility.MainModule.EventManager;
import Xeon.VisualAbility.MajorModule.AbilityList;
import Xeon.VisualAbility.MinerModule.ACC;
import Xeon.VisualAbility.Script.MainScripter;


public class VisualAbility extends JavaPlugin{
	public static boolean DebugMode = false;
	public static boolean ProtoType = false;
	public static boolean IngyoeMan_Patch = false;
	public static int BuildNumber = 20210310;
	
	public static boolean Invincibility = true;
	public static boolean DefaultArmed = true;
	public static boolean SRankUsed = false;
	public static boolean Respawn = true;
	public static boolean AutoKick = true;
	public static boolean AutoBan = false;
	public static boolean HalfMonsterDamage = false;
	public static boolean AutoDifficultySetting = true;
	public static boolean MaxLevelSurvival = false;
	public static int EarlyInvincibleTime = 5;
	public static boolean NoFoodMode = false;
	public static boolean KillerOutput = true;
	public static boolean AutoCoordinateOutput = true;
	public static boolean NoAnimal = false;
	public static boolean QuickStart = false;
	public static boolean NoAbilitySetting = false;
	public static boolean NoClearInventory = false;
	public static boolean PrintTip = true;
	public static boolean ReverseMode = false;
	public static boolean AutoSave = false;
	public static boolean InventorySave = false;
	public static boolean AbilityOverLap = false;
	public static boolean InfinityDur = false;
	public static int RestrictionTime = 15;
	public static boolean NoDeathNote = false;
	public static boolean NoMirroring = false;
	
	
	public static Logger log = Logger.getLogger("Minecraft");
	public CommandManager cm;
	public MainScripter scripter;
	public AbilityList A_List;
	
	public void onEnable(){
		if(IngyoeMan_Patch)
			ACC.DefaultItem = Material.SLIME_BALL;
		else
			ACC.DefaultItem = Material.IRON_INGOT;
		
		log.info(String.format("[VisualAbility] Build Number " + BuildNumber));
		log.info(String.format("[VisualAbility Debug] 기본 초기화..."));
		cm = new CommandManager(this);
		
		getServer().getPluginManager().registerEvents(new EventManager(), this);
			
		log.info(String.format("[VisualAbility Debug] 설정을 불러오는중..."));
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		Invincibility = getConfig().getBoolean("시작후 초반 무적");
		DefaultArmed = getConfig().getBoolean("기본 무장 제공");
		Respawn = getConfig().getBoolean("시작시 리스폰으로 이동");
		AutoKick = getConfig().getBoolean("사망시 자동으로 킥");
		AutoBan = getConfig().getBoolean("사망시 자동으로 밴(킥이 활성화 되어야 가능)");
		SRankUsed = getConfig().getBoolean("S랭크 능력 사용");
		HalfMonsterDamage = getConfig().getBoolean("몬스터의 공격력 반감");
		AutoDifficultySetting = getConfig().getBoolean("난이도 자동으로 Easy로 설정");
		MaxLevelSurvival = getConfig().getBoolean("만렙 서바이벌 모드");
		EarlyInvincibleTime = getConfig().getInt("초반 무적 시간(분 단위)");
		NoFoodMode = getConfig().getBoolean("배고픔 무한 모드(관련 능력은 알아서 상향됨)");
		KillerOutput = getConfig().getBoolean("죽을 경우 죽인 사람을 보여줌");
		AutoCoordinateOutput = getConfig().getBoolean("일정시간마다 좌표 표시");
		NoAnimal = getConfig().getBoolean("동물 비활성화");
		QuickStart = getConfig().getBoolean("퀵 스타트 모드");
		NoAbilitySetting = getConfig().getBoolean("시작시 능력 추첨 안함");
		NoClearInventory = getConfig().getBoolean("시작시 인벤토리 초기화 안함");
		PrintTip = getConfig().getBoolean("시작후 팁 출력함");
		//ReverseMode = getConfig().getBoolean("로꾸거 모드");
		AutoSave = getConfig().getBoolean("서버 오토 세이브");
		InventorySave = getConfig().getBoolean("인벤토리 세이브");
		AbilityOverLap = getConfig().getBoolean("능력 중첩 가능");
		InfinityDur = getConfig().getBoolean("내구도 무한");
		RestrictionTime = getConfig().getInt("일부 능력 금지 시간(분 단위, 0은 사용 안함)");
		NoDeathNote = getConfig().getBoolean("데스노트 사용 안함");
		NoMirroring = getConfig().getBoolean("미러링 사용 안함");
			
		log.info("[VisualAbility Debug] 능력 초기화...");
		AbilityBase.InitAbilityBase(this, cm);
		A_List = new AbilityList();
			
		log.info("[VisualAbility Debug] 스크립터 초기화...");
		scripter = new MainScripter(this, cm);
		if(Invincibility && EarlyInvincibleTime <= 0){
			log.info("[VisualAbility Debug] 초반 무적 값이 올바르지 않습니다. 1분으로 강제 설정 됩니다.");
			EarlyInvincibleTime = 1;
		}
		
		if(RestrictionTime < 0){
			log.info("[VisualAbility Debug] 제약 시간 값이 올바르지 않습니다. 0으로 강제 설정 됩니다.");
			RestrictionTime = 0;
		}
			
		log.info(String.format("[VisualAbility Debug] 작업 완료. 등록된 능력 : %d개", AbilityList.AbilityList.size()));
		if(ProtoType)
			log.info("[VisualAbility Debug] ProtoType 테스트 버전");
		
		if(IngyoeMan_Patch)
			log.info("[VisualAbility Debug] 잉여맨 패치 적용 버전");
		
		if(AutoSave){
			for(World w : Bukkit.getServer().getWorlds()){
				w.setAutoSave(true);
			}
		}
	}
	
	public void onDisable(){
		log.info(String.format("[VisualAbility Debug] 플러그인 언로드"));
	}
}
