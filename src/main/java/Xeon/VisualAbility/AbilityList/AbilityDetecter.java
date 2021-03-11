package Xeon.VisualAbility.AbilityList;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MajorModule.AbilityList;
import Xeon.VisualAbility.MinerModule.ACC;
import Xeon.VisualAbility.MinerModule.AUC;


public class AbilityDetecter extends AbilityBase {
	public AbilityDetecter(){
		InitAbility("능력 감지기", Type.Active_Immediately, Rank.B,
			"현재 게임중인 모든 유저의 능력명, 능력 타입을 볼수 있습니다.",
			"유저들이 액티브 능력을 사용할때마다 경고를 받으며",
			"동시에 레벨을 2씩 얻게 됩니다.");
		InitAbility(3, 0, true, ShowText.No_Text);
		RegisterLeftClickEvent();
	}

	@Override
	public int A_Condition(Event event, int CustomData){
		PlayerInteractEvent Event = (PlayerInteractEvent)event;
		if(PlayerCheck(Event.getPlayer()) && ItemCheck(ACC.DefaultItem))
			return 0;
		return -1;
	}

	@Override
	public void A_Effect(Event event, int CustomData){
		PlayerInteractEvent Event = (PlayerInteractEvent)event;
		Player p = Event.getPlayer();
		p.sendMessage(ChatColor.GOLD+"- 플레이어 능력 -");
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
		p.sendMessage(ChatColor.GREEN+"---------------");
	}
}
