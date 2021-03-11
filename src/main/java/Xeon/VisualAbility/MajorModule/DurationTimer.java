package Xeon.VisualAbility.MajorModule;

import org.bukkit.ChatColor;

import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MainModule.AbilityBase.ShowText;
import Xeon.VisualAbility.MinerModule.ACC;
import Xeon.VisualAbility.MinerModule.TimerBase;

public final class DurationTimer extends TimerBase {
private AbilityBase ab;
private CoolDownTimer ctimer;
	
	public DurationTimer(AbilityBase ab, CoolDownTimer ctimer){
		this.ab = ab;
		this.ctimer = ctimer;
	}

	@Override
	public void EventStartTimer(){
		ab.A_DurationStart();
	}

	@Override
	public void EventRunningTimer(int count){
		if(count <= 3 && count >= 1 && ab.GetShowText() == ShowText.All_Text || ab.GetShowText() == ShowText.No_CoolDownText)
			ab.GetPlayer().sendMessage(String.format(ChatColor.GREEN+"지속 시간"+ChatColor.WHITE+" %d초 전", count));
	}
	
	@Override
	public void EventEndTimer(){
		ab.A_DurationEnd();
		if(ab.GetShowText() != ShowText.Custom_Text)
			ab.GetPlayer().sendMessage(ACC.DefaultDurationEnd);
		ctimer.StartTimer(ab.GetCoolDown(), true);
	}

	@Override
	public void FinalEventEndTimer(){
		ab.A_FinalDurationEnd();
	}
}
