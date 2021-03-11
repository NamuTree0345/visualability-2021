package Xeon.VisualAbility.MinerModule;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import Xeon.VisualAbility.VisualAbility;
import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MainModule.AbilityBase.Type;
import Xeon.VisualAbility.MajorModule.AbilityList;

//Ability Utility Component
public final class AUC {
	public final static void InfoTextOut(Player p){
		AbilityBase a;
		
		if(AbilityList.assimilation.GetPlayer() == p){
			a = AbilityList.assimilation;
		}
		
		else{
			a = AbilityBase.FindAbility(p);
		}
		
		if(a != null){
			p.sendMessage(ChatColor.GREEN+"---------------");
			p.sendMessage(ChatColor.GOLD+"- 능력 정보 -");
			p.sendMessage(ChatColor.DARK_AQUA+"참고 : 능력 리스트중 가장 상단의 능력만 보여줍니다.");
			if(VisualAbility.ReverseMode){
				p.sendMessage(ChatColor.AQUA+a.GetAbilityName()+ChatColor.WHITE+" ["+a.GetRank().GetText()+ChatColor.WHITE+"] ");
			}
			else
				p.sendMessage(ChatColor.AQUA+a.GetAbilityName()+ChatColor.WHITE+" ["+TypeTextOut(a)+"] "+a.GetRank().GetText());
			for(int l=0;l<a.GetGuide().length;++l){
				p.sendMessage(a.GetGuide()[l]);
			}
			if(!VisualAbility.ReverseMode)
				p.sendMessage(TimerTextOut(a));
			p.sendMessage(ChatColor.GREEN+"---------------");
			return;
		}
		p.sendMessage(ChatColor.RED+"능력이 없거나 옵저버입니다.");
	}
	
	public final static String TypeTextOut(AbilityBase ab){
		Type type = ab.GetAbilityType();
		if(ab.GetRunAbility() == false)				{return ChatColor.RED+"능력 비활성화됨"+ChatColor.WHITE;}
		else if(type == Type.Active_Continue)	{return ChatColor.GREEN+"액티브 "+ChatColor.WHITE+"/ "+ChatColor.GOLD+"지속"+ChatColor.WHITE;}
		else if(type == Type.Active_Immediately){return ChatColor.GREEN+"액티브 "+ChatColor.WHITE+"/ "+ChatColor.GOLD+"즉발"+ChatColor.WHITE;}
		else if(type == Type.Passive_AutoMatic)	{return ChatColor.GREEN+"패시브 "+ChatColor.WHITE+"/ "+ChatColor.GOLD+"자동"+ChatColor.WHITE;}
		else if(type == Type.Passive_Manual)	{return ChatColor.GREEN+"패시브 "+ChatColor.WHITE+"/ "+ChatColor.GOLD+"수동"+ChatColor.WHITE;}
		else{return "Unknown";}
	}
	
	public final static String TimerTextOut(AbilityBase data){
		if(data.GetAbilityType() == Type.Active_Continue)
			{return String.format(ChatColor.RED+"쿨타임 : "+ChatColor.WHITE+"%d초 / "+ChatColor.RED+"지속시간 : "+ChatColor.WHITE+"%d초", data.GetCoolDown(), data.GetDuration());}
		else if(data.GetAbilityType() == Type.Active_Immediately)
			{return String.format(ChatColor.RED+"쿨타임 : "+ChatColor.WHITE+"%d초 / "+ChatColor.RED+"지속시간 : "+ChatColor.WHITE+"없음", data.GetCoolDown());}
		else if(data.GetAbilityType() == Type.Passive_AutoMatic)
			{return ChatColor.RED+"쿨타임 : "+ChatColor.WHITE+"없음 / "+ChatColor.RED+"지속시간 : "+ChatColor.WHITE+"없음";}
		else if(data.GetAbilityType() == Type.Passive_Manual)
			{return ChatColor.RED+"쿨타임 : "+ChatColor.WHITE+"없음 / "+ChatColor.RED+"지속시간 : "+ChatColor.WHITE+"없음";}
		else{return "None";}
	}
}
