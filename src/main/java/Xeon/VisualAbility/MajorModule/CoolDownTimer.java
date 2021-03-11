package Xeon.VisualAbility.MajorModule;

import org.bukkit.ChatColor;

import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MainModule.AbilityBase.ShowText;
import Xeon.VisualAbility.MinerModule.ACC;
import Xeon.VisualAbility.MinerModule.TimerBase;

public final class CoolDownTimer extends TimerBase {
	private AbilityBase ab;
	
	public CoolDownTimer(AbilityBase ab){
		this.ab = ab;
	}
	
	@Override
	public void EventStartTimer(){
		ab.A_CoolDownStart();
	}

	@Override
	public void EventRunningTimer(int count){
		if(count <= 3 && count >= 1 && ab.GetShowText() == ShowText.All_Text || ab.GetShowText() == ShowText.No_DurationText)
			ab.GetPlayer().sendMessage(String.format(ChatColor.RED+"쿨다운"+ChatColor.WHITE+" %d초 전", count));
	}

	@Override
	public void EventEndTimer(){
		ab.A_CoolDownEnd();
		if(ab.GetShowText() != ShowText.Custom_Text)
			ab.GetPlayer().sendMessage(ACC.DefaultCoolDownEnd);
	}
}
