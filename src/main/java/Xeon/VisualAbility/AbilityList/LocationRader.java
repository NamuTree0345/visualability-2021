package Xeon.VisualAbility.AbilityList;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MajorModule.AbilityList;
import Xeon.VisualAbility.MinerModule.ACC;
import Xeon.VisualAbility.OtherModule.Vector;
import org.bukkit.inventory.meta.Damageable;


public class LocationRader extends AbilityBase {
	public LocationRader(){
		InitAbility("위치 추적기", Type.Active_Immediately, Rank.C,
			"현재 게임중인 모든 유저의 좌표를 원할때 볼수 있습니다.",
			"각 유저가 자신과 어느정도 거리에 있는지 표시됩니다.",
			"자신이 사용하는 모든 도구의 내구도가 무한이 됩니다.");
		InitAbility(3, 0, true, ShowText.No_Text);
		RegisterLeftClickEvent();
	}

	@Override
	public int A_Condition(Event event, int CustomData){
		PlayerInteractEvent Event = (PlayerInteractEvent)event;
		if(PlayerCheck(Event.getPlayer())){
			if(ItemCheck(ACC.DefaultItem)){
				return 0;
			}
			else{
				ItemStack i = Event.getPlayer().getInventory().getItemInMainHand();
				//i.setDurability((short)0);
				Damageable damageable = (Damageable) i.getItemMeta();
				assert damageable != null;
				damageable.setDamage(0);
				return -2;
			}
		}
		return -1;
	}

	@Override
	public void A_Effect(Event event, int CustomData){
		PlayerInteractEvent Event = (PlayerInteractEvent)event;
		Player p = Event.getPlayer();
		switch(CustomData){
		case 0:
			p.sendMessage(ChatColor.GOLD+"- 플레이어 위치 -");
			p.sendMessage(ChatColor.GREEN+"---------------");
			Vector vec = new Vector(p.getLocation());
			List<AbilityBase> pl = AbilityList.AbilityList;
			int count=0;
			
			for(int l=0;l<pl.size();++l){
				if(pl.get(l).GetPlayer() != null){
					Player temp = Bukkit.getServer().getPlayerExact(pl.get(l).GetPlayer().getName());
					if(temp != null){
						double len = vec.distance(temp.getLocation());
						int x = (int)temp.getLocation().getX();
						int y = (int)temp.getLocation().getY();
						int z = (int)temp.getLocation().getZ();
						p.sendMessage(String.format(
								ChatColor.GREEN+"%d. "+ChatColor.WHITE+"%s ("
								+ChatColor.AQUA+"%.1f"+ChatColor.WHITE+") : "
								+ChatColor.RED+"X"+ChatColor.WHITE+" %d, "
								+ChatColor.RED+"Y"+ChatColor.WHITE+" %d, "
								+ChatColor.RED+"Z"+ChatColor.WHITE+" %d",
								count, temp.getName(), len, x, y, z));
						++count;
					}
				}
			}
			p.sendMessage(ChatColor.GREEN+"---------------");
			break;
		}
	}
}
