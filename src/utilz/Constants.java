package utilz;

import main.Game;

public class Constants {

	public static final float GRAVITY = 0.04f * Game.SCALE;
	public static final int ANI_SPEED = 25;
    public static final int SPRITE_THRESHOLD = 48;
	
	public static class EnemyConstants {
		public static final int DOGGY = 0;
		public static final int DOGGY2 = 1;

		public static final int RUNNING = 0;
		public static final int IDLE = 1;
		public static final int ATTACK = 2;
		public static final int DEAD = 3;
		public static final int HIT = 4;

		public static final int DOGGY_WIDTH_DEFAULT = 48;
		public static final int DOGGY_HEIGHT_DEFAULT = 48;
		
		public static final int DOGGY_HITBOX_WIDTH = 34;
		public static final int DOGGY_HITBOX_HEIGHT= 31;
		
		public static final int DOGGY_NUM_STATES = 5;
		public static final int DOGGY_MAX_INDEX = 6;

		public static final int DOGGY_WIDTH = (int) (DOGGY_WIDTH_DEFAULT * Game.SCALE);
		public static final int DOGGY_HEIGHT = (int) (DOGGY_HEIGHT_DEFAULT * Game.SCALE);

		public static final int DOGGY_DRAWOFFSET_X = (int) (4 * Game.SCALE);		// crabby hitbox starts at 3 x
		public static final int DOGGY_DRAWOFFSET_Y = (int) (16 * Game.SCALE);		// crabby hitbox starts at 16 y
		
		public static int GetSpriteAmount(int enemy_type, int enemy_state) {

			switch (enemy_type) {
			case DOGGY:
				switch (enemy_state) {
				case RUNNING:
					return 6;
				case IDLE:
					return 4;
				case ATTACK:
					return 4;
				case DEAD:
					return 4;
				case HIT:
					return 2;
				}
				
			case DOGGY2:
				switch (enemy_state) {
				case RUNNING:
					return 6;
				case IDLE:
					return 4;
				case ATTACK:
					return 4;
				case DEAD:
					return 4;
				case HIT:
					return 2;
				}
			}
			return 0;
		}

		// Health of Enemies
		public static int GetMaxHealth(int enemy_type) {
			switch (enemy_type) {
			case DOGGY:
				return 2;		// have 2 health
			default:
				return 1;
			}
		}

		// Attack Damage of Enemies
		public static int GetEnemyDmg(int enemy_type) {
			switch (enemy_type) {
			case DOGGY:
				return 1;		// can deal 1 dmg
			default:
				return 0;
			}
		}
		
		
	}
	
	
	
	public static class Menu_UI{
		//title
		public static final int T_WIDTH_DEFAULT = 500;
		public static final int T_HEIGHT_DEFAULT = 60;
		public static final double T_WIDTH = T_WIDTH_DEFAULT * Game.SCALE;
		public static final double T_HEIGHT = T_HEIGHT_DEFAULT * Game.SCALE;

		//menu cat
		public static final int C_WIDTH_DEFAULT = 95;
		public static final int C_HEIGHT_DEFAULT = 150;
		public static final double C_WIDTH = C_WIDTH_DEFAULT * Game.SCALE;
		public static final double C_HEIGHT = C_HEIGHT_DEFAULT * Game.SCALE;

		//menu buttons
		public static final int B_WIDTH_DEFAULT = 100;
		public static final int B_HEIGHT_DEFAULT = 55;
		public static final double B_WIDTH = B_WIDTH_DEFAULT * Game.SCALE;
		public static final double B_HEIGHT = B_HEIGHT_DEFAULT * Game.SCALE;

		//back button
		public static final int BK_WIDTH_DEFAULT = 60;
		public static final int BK_HEIGHT_DEFAULT = 18;
		public static final double BK_WIDTH = BK_WIDTH_DEFAULT * Game.SCALE;
		public static final double BK_HEIGHT = BK_HEIGHT_DEFAULT * Game.SCALE;

		//about left part
		public static final int AL_WIDTH_DEFAULT = 350;
		public static final int AL_HEIGHT_DEFAULT = 300;
		public static final double AL_WIDTH = AL_WIDTH_DEFAULT * Game.SCALE;
		public static final double AL_HEIGHT = AL_HEIGHT_DEFAULT * Game.SCALE;

		//about right part
		public static final int AR_WIDTH_DEFAULT = 277;
		public static final int AR_HEIGHT_DEFAULT = 81;
		public static final double AR_WIDTH = AR_WIDTH_DEFAULT * Game.SCALE;
		public static final double AR_HEIGHT = AR_HEIGHT_DEFAULT * Game.SCALE;

		//developers
		public static final int D_WIDTH_DEFAULT = 257;
		public static final int D_HEIGHT_DEFAULT = 119;
		public static final double D_WIDTH = D_WIDTH_DEFAULT * Game.SCALE;
		public static final double D_HEIGHT = D_HEIGHT_DEFAULT * Game.SCALE;
	}
	
	
	public static class UI {
		
		public static class URMButtons {
			public static final int URM_DEFAULT_SIZE = 56;
			public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);
			
		}	
		
	}
	
	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}

	public static class PlayerConstants {
		public static final int IDLE = 0;
		public static final int IDLE_SIT_TAIL_DOWN = 1;
		public static final int IDLE_STAND_TAIL_DOWN = 2;
		public static final int IDLE_STAND_TAIL_UP = 3;
		public static final int WALKING = 4;
		public static final int WALKING2 = 5;
		public static final int RUNNING = 6;
		public static final int RUNNING2 = 7;
		public static final int RUNNING3 = 8;
		public static final int SLEEP = 9;
		public static final int SLEEP1 = 10;
		public static final int SLEEP2 = 11;
		public static final int READY1 = 12;
		public static final int READY2 = 13;
		public static final int SIT_STAND = 14;
		public static final int STAND_SIT = 15;
		public static final int BACK_AWAY = 16;
		public static final int CHILLS = 17;
		public static final int SURPRISED = 18;
		public static final int JUMP = 19;
		public static final int FALLING = 20;
		public static final int EAT = 21;
		public static final int EAT_SIT = 22;
		public static final int SCRATCH = 23;
		public static final int SCRATCH_STAND = 24;
		public static final int LOOK_AROUND = 25;
		public static final int LOOK_FRONT = 26;
		public static final int LOOK_FRONT_END = 27;
		public static final int LOOK_BACK_START = 28;
		public static final int LOOK_BACK = 29;
		public static final int LOOK_BACK_END = 30;
		public static final int LICK = 31;
		public static final int LICK2 = 32;
		public static final int HURT = 33;
		public static final int DEATH_SEPARATE_HEAD = 34;
		public static final int DEATH_ROLL_HEAD = 35;
		public static final int DEATH = 36;
		public static final int REVIVE = 37;
		public static final int ATTACK1 = 38;
		public static final int ATTACK2 = 39;
		public static final int ATTACK_BACK1 = 40;
		public static final int ATTACK_BACK2 = 41;
		public static final int ATTACK_PUSH = 42;
		public static final int POOP = 43;
		public static final int POOP_DIG1 = 44;
		public static final int POOP_DIG2 = 45;
		public static final int TRICKS = 46;
		public static final int UPPIES_END = 47;
		public static final int UPPIES_START = 48;
		public static final int SPIN = 49;
		public static final int SPIN2 = 50;

		public static int GetSpriteAmount(int player_action) {
			switch (player_action) {

			case IDLE:
				return 16;
			case IDLE_SIT_TAIL_DOWN:
				return 8;
			case IDLE_STAND_TAIL_DOWN:
				return 8;
			case IDLE_STAND_TAIL_UP:
				return 8;
			case WALKING:
				return 16;
			case WALKING2:
				return 8;
			case RUNNING:
				return 9;
			case RUNNING2:
				return 2;
			case RUNNING3:
				return 3;
			case SLEEP:
				return 32;
			case SLEEP1:
				return 8;
			case SLEEP2:
				return 8;
			case READY1:
				return 4;
			case READY2:
				return 4;
			case SIT_STAND:
				return 4;
			case STAND_SIT:
				return 4;
			case BACK_AWAY:
				return 4;
			case CHILLS:
				return 8;
			case SURPRISED:
				return 8;
			case JUMP:
				return 3;
			case FALLING:
				return 1;
			case EAT:
				return 19;
			case EAT_SIT:
				return 4;
			case SCRATCH:
				return 16;
			case SCRATCH_STAND:
				return 4;
			case LOOK_AROUND:
				return 24;
			case LOOK_FRONT:
				return 4;
			case LOOK_FRONT_END:
				return 4;
			case LOOK_BACK_START:
				return 4;
			case LOOK_BACK:
				return 4;
			case LOOK_BACK_END:
				return 4;
			case LICK:
				return 16;
			case LICK2:
				return 4;
			case HURT:
				return 2;
			case DEATH_SEPARATE_HEAD:
				return 12;
			case DEATH_ROLL_HEAD:
				return 12;
			case DEATH:
				return 4;
			case REVIVE:
				return 8;
			case ATTACK1:
				return 4;
			case ATTACK2:
				return 4;
			case ATTACK_BACK1:
				return 4;
			case ATTACK_BACK2:
				return 4;
			case ATTACK_PUSH:
				return 4;
			case POOP_DIG1:
				return 4;
			case POOP_DIG2:
				return 4;
			case POOP:
				return 28;
			case UPPIES_START:
				return 8;
			case UPPIES_END:
				return 8;
			case TRICKS:
				return 24;
			case SPIN:
				return 4;
			case SPIN2:
				return 2;
			default:
				return 1;
			}
		}
	}
}


