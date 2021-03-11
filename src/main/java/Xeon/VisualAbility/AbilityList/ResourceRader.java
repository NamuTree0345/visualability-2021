package Xeon.VisualAbility.AbilityList;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerInteractEvent;

import Xeon.VisualAbility.VisualAbility;
import Xeon.VisualAbility.MainModule.AbilityBase;
import Xeon.VisualAbility.MinerModule.ACC;
import Xeon.VisualAbility.OtherModule.Vector;

public class ResourceRader extends AbilityBase {
	public ResourceRader(){
		if(VisualAbility.SRankUsed){
			InitAbility("자원 탐지기", Type.Active_Immediately, Rank.S,
				"자신 주변 약 20칸 범위 내에 있는 모든 광물 원석의",
				"갯수 및 위치를 측량할 수 있습니다. 각 광물마다 자신과 가장",
				"가까운 광물의 X, Y, Z 좌표를 알려줍니다.",
				"좌표가 약간 부정확할 수 있습니다.");
			InitAbility(20, 0, true);
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
		Block b = p.getLocation().getBlock();
		int redstone=0, gold=0, coal=0, lapis=0, iron=0, diamond=0;
		Vector
		rvec=new Vector(0d, 0d, 0d), 
		gvec=new Vector(0d, 0d, 0d), 
		cvec=new Vector(0d, 0d, 0d), 
		lvec=new Vector(0d, 0d, 0d), 
		ivec=new Vector(0d, 0d, 0d), 
		dvec=new Vector(0d, 0d, 0d);
		int rlen=1000, glen=1000, clen=1000, llen=1000, ilen=1000, dlen=1000;
		
		for(int loop1=-10;loop1<10;++loop1){
			for(int loop2=-5;loop2<5;++loop2){
				for(int loop3=-10;loop3<10;++loop3){
					Vector pvec = new Vector(p.getLocation());
					double len = pvec.distance((double)loop1, (double)loop2, (double)loop3);
					switch(b.getRelative(loop1, loop2, loop3).getType()){
					case REDSTONE_ORE:
						++redstone;
						if((int)len < rlen){
							rlen = (int)len;
							rvec.setX(b.getRelative(loop1, loop2, loop3).getX());
							rvec.setY(b.getRelative(loop1, loop2, loop3).getY());
							rvec.setZ(b.getRelative(loop1, loop2, loop3).getZ());
						}
						break;
					case GOLD_ORE:
						++gold;
						if((int)len < glen){
							glen = (int)len;
							gvec.setX(b.getRelative(loop1, loop2, loop3).getX());
							gvec.setY(b.getRelative(loop1, loop2, loop3).getY());
							gvec.setZ(b.getRelative(loop1, loop2, loop3).getZ());
						}
						break;
					case COAL_ORE:
						++coal;
						if((int)len < clen){
							clen = (int)len;
							cvec.setX(b.getRelative(loop1, loop2, loop3).getX());
							cvec.setY(b.getRelative(loop1, loop2, loop3).getY());
							cvec.setZ(b.getRelative(loop1, loop2, loop3).getZ());
						}
						break;
					case LAPIS_ORE:
						++lapis;
						if((int)len < llen){
							llen = (int)len;
							lvec.setX(b.getRelative(loop1, loop2, loop3).getX());
							lvec.setY(b.getRelative(loop1, loop2, loop3).getY());
							lvec.setZ(b.getRelative(loop1, loop2, loop3).getZ());
						}
						break;
					case IRON_ORE:
						++iron;
						if((int)len < ilen){
							ilen = (int)len;
							ivec.setX(b.getRelative(loop1, loop2, loop3).getX());
							ivec.setY(b.getRelative(loop1, loop2, loop3).getY());
							ivec.setZ(b.getRelative(loop1, loop2, loop3).getZ());
						}
						break;
					case DIAMOND_ORE:
						++diamond;
						if((int)len < dlen){
							dlen = (int)len;
							dvec.setX(b.getRelative(loop1, loop2, loop3).getX());
							dvec.setY(b.getRelative(loop1, loop2, loop3).getY());
							dvec.setZ(b.getRelative(loop1, loop2, loop3).getZ());
						}
						break;
					default:
						break;
					}
				}
			}
		}
		
		p.sendMessage(ChatColor.GOLD+"광물 스캔 결과");
		p.sendMessage(ChatColor.GREEN+"---------------");
		
		if(coal==0){p.sendMessage(String.format(ChatColor.GRAY+"석탄------ : "+ChatColor.WHITE+"없음"));}
		else{p.sendMessage(String.format(ChatColor.GRAY+"석탄------ : "+ChatColor.WHITE+"%d / "+ChatColor.GREEN+"X : "+ChatColor.WHITE+"%d, "+ChatColor.GREEN+"Y : "+ChatColor.WHITE+"%d, "+ChatColor.GREEN+"Z : "+ChatColor.WHITE+"%d", coal, (int)cvec.getX(), (int)cvec.getY(), (int)cvec.getZ()));}
		
		if(iron==0){p.sendMessage(String.format(ChatColor.WHITE+"철-------- : "+ChatColor.WHITE+"없음"));}
		else{p.sendMessage(String.format(ChatColor.WHITE+"철-------- : "+ChatColor.WHITE+"%d / "+ChatColor.GREEN+"X : "+ChatColor.WHITE+"%d, "+ChatColor.GREEN+"Y : "+ChatColor.WHITE+"%d, "+ChatColor.GREEN+"Z : "+ChatColor.WHITE+"%d", iron, (int)ivec.getX(), (int)ivec.getY(), (int)ivec.getZ()));}
		
		if(lapis==0){p.sendMessage(String.format(ChatColor.BLUE+"청금석---- : "+ChatColor.WHITE+"없음"));}
		else{p.sendMessage(String.format(ChatColor.BLUE+"청금석---- : "+ChatColor.WHITE+"%d / "+ChatColor.GREEN+"X : "+ChatColor.WHITE+"%d, "+ChatColor.GREEN+"Y : "+ChatColor.WHITE+"%d, "+ChatColor.GREEN+"Z : "+ChatColor.WHITE+"%d", lapis, (int)lvec.getX(), (int)lvec.getY(), (int)lvec.getZ()));}
		
		if(redstone==0){p.sendMessage(String.format(ChatColor.RED+"레드스톤-- : "+ChatColor.WHITE+"없음"));}
		else{p.sendMessage(String.format(ChatColor.RED+"레드스톤-- : "+ChatColor.WHITE+"%d / "+ChatColor.GREEN+"X : "+ChatColor.WHITE+"%d, "+ChatColor.GREEN+"Y : "+ChatColor.WHITE+"%d, "+ChatColor.GREEN+"Z : "+ChatColor.WHITE+"%d", redstone, (int)rvec.getX(), (int)rvec.getY(), (int)rvec.getZ()));}
		
		if(gold==0){p.sendMessage(String.format(ChatColor.YELLOW+"금 --------: "+ChatColor.WHITE+"없음"));}
		else{p.sendMessage(String.format(ChatColor.YELLOW+"금 --------: "+ChatColor.WHITE+"%d / "+ChatColor.GREEN+"X : "+ChatColor.WHITE+"%d, "+ChatColor.GREEN+"Y : "+ChatColor.WHITE+"%d, "+ChatColor.GREEN+"Z : "+ChatColor.WHITE+"%d", gold, (int)gvec.getX(), (int)gvec.getY(), (int)gvec.getZ()));}
		
		if(diamond==0){p.sendMessage(String.format(ChatColor.AQUA+"다이아몬드 : "+ChatColor.WHITE+"없음"));}
		else{p.sendMessage(String.format(ChatColor.AQUA+"다이아몬드 : "+ChatColor.WHITE+"%d / "+ChatColor.GREEN+"X : "+ChatColor.WHITE+"%d, "+ChatColor.GREEN+"Y : "+ChatColor.WHITE+"%d, "+ChatColor.GREEN+"Z : "+ChatColor.WHITE+"%d", diamond, (int)dvec.getX(), (int)dvec.getY(), (int)dvec.getZ()));}
		p.sendMessage(ChatColor.GREEN+"---------------");
	}
}
