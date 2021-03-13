package Xeon.VisualAbility.MainModule;

import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.PacketPlayOutWorldEvent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import Xeon.VisualAbility.VisualAbility;
import Xeon.VisualAbility.MajorModule.AbilityList;
import Xeon.VisualAbility.MajorModule.CoolDownTimer;
import Xeon.VisualAbility.MajorModule.DurationTimer;
import Xeon.VisualAbility.MajorModule.RestrictionTimer;
import Xeon.VisualAbility.MinerModule.ACC;

import java.util.Objects;

abstract public class AbilityBase{
	protected static	CommandManager	cm;
	protected static	VisualAbility	va;
	protected static 	int				AbilityCount = 0;
	
	public final static RestrictionTimer restrictionTimer  = new RestrictionTimer();
	
	public static enum Type{
		Passive_AutoMatic,
		Passive_Manual,
		Active_Immediately,
		Active_Continue;
	}
	
	public static enum ShowText{
		All_Text,
		No_CoolDownText,
		No_DurationText,
		No_Text,
		Custom_Text;
	}
	
	public static enum Rank{
		S(ChatColor.DARK_GRAY+"S Rank"),
		A(ChatColor.RED+"A Rank"),
		B(ChatColor.GREEN+"B Rank"),
		C(ChatColor.YELLOW+"C Rank");
		
		private String s;
		
		Rank(String s){
			this.s = s;
		}
		
		public String GetText(){
			return s;
		}
	}
	
	private Rank			rank;
	private Player		player;
	private String		AbilityName;
	private Type			type;
	private String[]		Guide;
	private int			CoolDown	= 0;
	private int			Duration	= 0;
	private CoolDownTimer	CTimer;
	private DurationTimer 	DTimer;
	private boolean		RunAbility	= true;
	private ShowText		showtext	= ShowText.All_Text;
	
	public abstract int		A_Condition		(Event event, int CustomData);
	public abstract void		A_Effect		(Event event, int CustomData);
	public void				A_CoolDownStart	()			{};
	public void				A_CoolDownEnd	()			{};
	public void				A_DurationStart	()			{};
	public void				A_DurationEnd	()			{};
	public void				A_FinalDurationEnd()		{};
	public void				A_SetEvent		(Player p)	{};
	public void				A_ResetEvent	(Player p)	{};
	
	public final void RegisterLeftClickEvent()	{EventManager.LeftHandEvent.add(this);}
	public final void RegisterRightClickEvent()	{EventManager.RightHandEvent.add(this);}
	
	public final Player GetPlayer()			{return player;}
	public final void SetPlayer(Player p, boolean textout){
		DTimer.StopTimer();
		CTimer.StopTimer();
		if(player != null){
			if(textout){
				player.sendMessage(String.format(ChatColor.RED+"%s"+ChatColor.WHITE+" 능력이 해제되었습니다.", this.GetAbilityName()));
			}
			A_ResetEvent(player);
		}
		if(p != null && RunAbility){
			if(textout){
				p.sendMessage(String.format(ChatColor.GREEN+"%s"+ChatColor.WHITE+" 능력이 설정되었습니다.", this.GetAbilityName()));
			}
			A_SetEvent(p);
		}
		player = p;
	}
	
	public final String 	GetAbilityName()					{return AbilityName;}
	public final Type 		GetAbilityType()					{return (type == null) ? null : type;}
	public final String[] 	GetGuide()							{return Guide;}
	public final int 		GetCoolDown()						{return CoolDown;}
	public final Rank 		GetRank()							{return rank;}
	public final int 		GetDuration()						{return Duration;}
	public final boolean 	GetDurationState()				{return DTimer.GetTimerRunning();}
	
	public final void 		SetRunAbility(boolean RunAbility)	{this.RunAbility = RunAbility;}
	public final boolean 	GetRunAbility()						{return this.RunAbility;}
	
	public final ShowText	GetShowText()			{return showtext;}
	public final void 		AbilityDTimerCancel()	{DTimer.StopTimer();}
	public final void 		AbilityCTimerCancel()	{CTimer.StopTimer();}
	
	public final boolean PlayerCheck(Player p){
		if(player != null && p != null && p.getName().equalsIgnoreCase(player.getName())){
			if(player.isDead() || Bukkit.getServer().getPlayerExact(player.getName()) != null){
				player = p;
				return true;
			}
		}
		return false;
	}
	public final boolean PlayerCheck(Entity e){
		return e instanceof Player && PlayerCheck((Player) e);
	}
	
	public final boolean ItemCheck(Material item){
		return GetPlayer().getInventory().getItemInMainHand().getType() == item;
	}
	
	public final boolean 	AbilityExcute(Event event)	{return AbilityExcute(event, 0);}
	public final boolean 	AbilityExcute(Event event, int CustomData){
		if(player != null && RunAbility){
			int cd = A_Condition(event, CustomData);
			if(cd == -2){
				return true;
			}
			if(cd != -1){
				if(type == Type.Active_Continue || type == Type.Active_Immediately){
					if(DTimer.GetTimerRunning()){
						GetPlayer().sendMessage(String.format(
								ChatColor.GREEN+"남은 지속 시간 : "+ChatColor.WHITE+"%d초", DTimer.GetCount()));
						return true;
					}
					else if(CTimer.GetTimerRunning()){
						GetPlayer().sendMessage(String.format(
							ChatColor.RED+"재사용까지 남은 시간 : "+ChatColor.WHITE+"%d초", CTimer.GetCount()));
						return true;
					}
					
					if(AbilityList.abilitydetecter.GetPlayer() != null && this != AbilityList.machinegun
							&& this != AbilityList.abilitydetecter){
						AbilityList.abilitydetecter.GetPlayer().sendMessage(
								String.format(ChatColor.RED+"%s"+ChatColor.WHITE+"님이 "+ChatColor.RED+"%s"+ChatColor.WHITE+" 능력을 사용했습니다.", this.GetPlayer().getName(), this.AbilityName));
						AbilityList.abilitydetecter.GetPlayer().sendMessage(ChatColor.GREEN+"레벨 2 획득");
						AbilityList.abilitydetecter.GetPlayer().setLevel(AbilityList.abilitydetecter.GetPlayer().getLevel()+2);
					}
					
					if(GetShowText() != ShowText.Custom_Text)
						GetPlayer().sendMessage(ACC.DefaultAbilityUsed);
				}
				if(type == Type.Active_Immediately){
					A_Effect(event, cd);
					if(GetCoolDown() != 0)
						CTimer.StartTimer(GetCoolDown(), true);
				}
				else if(type == Type.Active_Continue)
					DTimer.StartTimer(GetDuration(), true);
				else
					A_Effect(event, cd);
				return true;
			}
		}
		return false;
	}
	
	public final boolean AbilityDuratinEffect(Event event){return AbilityDuratinEffect(event, 0);}
	public final boolean AbilityDuratinEffect(Event event, int CustomData){
		if(player != null && DTimer.GetTimerRunning()){
			A_Effect(event, CustomData);
			return true;
		}
		return false;
	}
	
	protected final void InitAbility(String AbilityName, Type type, Rank rank, String ... Manual){
		this.AbilityName = AbilityName;
		this.type = type;
		this.Guide = new String[Manual.length];
		for(int loop=0; loop<Manual.length; ++loop)	{this.Guide[loop] = Manual[loop];}
		this.CTimer = new CoolDownTimer(this);
		this.DTimer = new DurationTimer(this, CTimer);
		this.rank = rank;
	}
	
	protected final void InitAbility(int CoolDown, int Duration, boolean RunAbility){
		InitAbility(CoolDown, Duration, RunAbility, ShowText.All_Text);}
	protected final void InitAbility(int CoolDown, int Duration, boolean RunAbility, ShowText showtext){
		this.CoolDown =
			type == Type.Active_Continue ||
			type == Type.Active_Immediately
			? CoolDown : -1;
		this.Duration =
			type == Type.Active_Continue
			? Duration : -1;
		this.RunAbility = RunAbility;
		this.showtext = showtext;
		AbilityList.AbilityList.add(this);
		++AbilityCount;
	}
	
	public final static void		ShowEffect(Location l, Material BreakBlock, Effect Sound){
		if(Sound != null)
			l.getWorld().playEffect(l, Sound, 0);
		if(BreakBlock != null){
			PacketPlayOutWorldEvent packet = new PacketPlayOutWorldEvent(2001, new BlockPosition(l.getBlockX(), l.getBlockY(), l.getBlockZ()), BreakBlock.getId(), false);
			/*
			Packet61WorldEvent packet = new Packet61WorldEvent(2001, l.getBlockX(), l.getBlockY(), l.getBlockZ(), BreakBlock, false);
			((CraftServer)Bukkit.getServer()).getHandle().sendPacketNearby(l.getX(), l.getY(), l.getZ(), 64,
					((CraftWorld)l.getWorld()).getHandle().dimension, packet);

			 */
			((CraftServer)Bukkit.getServer()).getHandle().sendPacketNearby(null, l.getX(), l.getY(), l.getZ(), 64, ((CraftWorld) Objects.requireNonNull(l.getWorld())).getHandle().getDimensionKey(), packet);
		}
	}
	
	public final static int 		GetAbilityCount()		{return AbilityCount;}
	public final static AbilityBase FindAbility(Player p)	{
		for(AbilityBase a : AbilityList.AbilityList){if(a.PlayerCheck(p))	{return a;}}
		return null;}
	public final static AbilityBase FindAbility(String name){
		for(AbilityBase a : AbilityList.AbilityList){if(a.GetAbilityName().equalsIgnoreCase(name))	{return a;}}
		return null;
	}
	
	public final static void InitAbilityBase(VisualAbility va, CommandManager cm){
		AbilityBase.va = va;
		AbilityBase.cm = cm;
	}
}
