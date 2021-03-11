package Xeon.VisualAbility.AbilityList;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import Xeon.VisualAbility.VisualAbility;
import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MinerModule.ACC;

public class Thor extends AbilityBase {
	public Thor(){
		if(VisualAbility.SRankUsed){
			InitAbility("토르", Type.Active_Immediately, Rank.S,
				"지정한 지점에 번개를 떨어뜨립니다. 번개가",
				"떨어진곳에 데미지와 폭발이 일어납니다.",
				"반드시 블럭을 조준해서 공격해야 제데로 타격이 됩니다.",
				"사정거리 제한이 없습니다. 철괴를 32개 이상 들고있을시",
				"번개가 3번 떨어지며 폭발 위력이 2배로 상승합니다.");
			InitAbility(27, 0, true);
			RegisterLeftClickEvent();
		}
	}
	
	@Override
	public int A_Condition(Event event, int CustomData){
		PlayerInteractEvent Event = (PlayerInteractEvent)event;
		if(PlayerCheck(Event.getPlayer()) && ItemCheck(ACC.DefaultItem)){
			return 0;
		}
		return -1;
	}
	
	@Override
	public void A_Effect(Event event, int CustomData){
		PlayerInteractEvent Event = (PlayerInteractEvent)event;
		Player p = Event.getPlayer();
		World w = p.getWorld();
		Location loc = p.getTargetBlock(null, 0).getLocation();
		if(p.getItemInHand().getAmount() >= 32){
			w.strikeLightning(loc);
			w.strikeLightning(loc);
			w.strikeLightning(loc);
			w.createExplosion(loc, 4.8F, true);
		}
		else{
			w.strikeLightning(loc);
			w.createExplosion(loc, 2.4F, true);
		}
	}
}
