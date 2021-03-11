package Xeon.VisualAbility.MajorModule;

import java.util.ArrayList;

import Xeon.VisualAbility.AbilityList.*;
import Xeon.VisualAbility.MainModule.AbilityBase;

public class AbilityList {
	public final static ArrayList<AbilityBase> AbilityList = new ArrayList<AbilityBase>();
	
	//이하 개별 능력 리스트
	public final static Feather feather						= new Feather();
	public final static Blaze blaze							= new Blaze();
	public final static Shadow shadow						= new Shadow();
	public final static Mirroring mirroring					= new Mirroring();
	public final static ReverseAlchemy reversealchemy	= new ReverseAlchemy();
	public final static Blind blind								= new Blind();
	public final static LocationRader locationrader		= new LocationRader();
	
	public final static Anorexia anorexia					= new Anorexia();
	public final static Jumper jumper						= new Jumper();
	public final static Medic medic							= new Medic();
	public final static AbilityDetecter abilitydetecter	= new AbilityDetecter();
	public final static Lockdown lockdown				= new Lockdown();
	public final static Time time								= new Time();
	public final static NuclearPunch nuclearpunch		= new NuclearPunch();
	public final static Bishop bishop						= new Bishop();
	public final static Phoenix phoenix					= new Phoenix();
	public final static HyperVisor hypervisor				= new HyperVisor();
	public final static Counter counter						= new Counter();
	
	public final static Berserker berserker				= new Berserker();
	public final static Archer archer						= new Archer();
	public final static Aegis aegis						= new Aegis();
	public final static Clocking clocking					= new Clocking();
	public final static MachineGun machinegun		= new MachineGun();
	public final static ShockWave shockwave			= new ShockWave();
	
	public final static ResourceRader resourcerader	= new ResourceRader();
	public final static Thor thor							= new Thor();
	public final static Assimilation assimilation			= new Assimilation();
	public final static DeathNote deathnote			= new DeathNote();
}
