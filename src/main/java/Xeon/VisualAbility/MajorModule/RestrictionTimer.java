package Xeon.VisualAbility.MajorModule;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import Xeon.VisualAbility.MinerModule.TimerBase;

public final class RestrictionTimer extends TimerBase {
	@Override
	public void EventStartTimer() {
	}

	@Override
	public void EventRunningTimer(int count) {
	}

	@Override
	public void EventEndTimer() {
		Bukkit.broadcastMessage(ChatColor.GREEN+"일부 능력의 제약이 해제되었습니다.");
	}
}
