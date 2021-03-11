package Xeon.VisualAbility.AbilityList;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MinerModule.ACC;
import Xeon.VisualAbility.OtherModule.Vector;


public class Jumper extends AbilityBase {
	public Jumper(){
		InitAbility("점퍼", Type.Active_Immediately, Rank.B,
			"최대 35칸 거리를 순간이동 할수 있습니다.",
			"단, 벽은 통과할수 없고 낙사 데미지도 받습니다.",
			"자신이 갈 장소의 바닥 블럭을 클릭해야 텔포가 됩니다.",
			"사용시 유의하세요. 허공엔 사용이 안됩니다!");
		InitAbility(20, 0, true);
		RegisterLeftClickEvent();
	}

	@Override
	public int A_Condition(Event event, int CustomData){
		PlayerInteractEvent Event = (PlayerInteractEvent)event;
		if(PlayerCheck(Event.getPlayer()) && ItemCheck(ACC.DefaultItem)){
			Player p = Event.getPlayer();
			Block b = p.getTargetBlock(null , 0);
			Location loc = b.getLocation();
			Location ploc = p.getLocation();
			Vector targetvec;
			if(b.getRelative(0, +1, 0).getType() == Material.AIR && b.getRelative(0,+2,0).getType() == Material.AIR)
				targetvec = new Vector(loc.getX(),loc.getY(),loc.getZ());
			else
				targetvec = new Vector(loc.getX(),b.getWorld().getHighestBlockYAt(b.getX(), b.getZ()),loc.getZ());
			Vector playervec = new Vector(ploc.getX(),ploc.getY(),ploc.getZ());
			if(playervec.distance(targetvec) <= 35.0 && b.getY() != 0)
				return 0;
			else
				p.sendMessage(ChatColor.RED+"거리가 너무 멉니다.");
		}
		return -1;
	}

	@Override
	public void A_Effect(Event event, int CustomData){
		PlayerInteractEvent Event = (PlayerInteractEvent)event;
		Player p = Event.getPlayer();
		Block b = p.getTargetBlock(null , 0);
		Location loc = b.getLocation();
		Vector targetvec;
		
		if(b.getRelative(0, +1, 0).getType() == Material.AIR && b.getRelative(0,+2,0).getType() == Material.AIR){
			targetvec = new Vector(loc.getX(),loc.getY(),loc.getZ());
		}
		else{
			targetvec = new Vector(loc.getX(),b.getWorld().getHighestBlockYAt(b.getX(), b.getZ()),loc.getZ());
		}
		loc.setX(targetvec.getX()+0.5);
		loc.setY(targetvec.getY()+1);
		loc.setZ(targetvec.getZ()+0.5);
		loc.setPitch(p.getLocation().getPitch());
		loc.setYaw(p.getLocation().getYaw());
		p.teleport(loc);
	}
}
