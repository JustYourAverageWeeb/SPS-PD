/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.hmdzl.spspd.change.actors.mobs;

import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.Locked;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.buffs.Terror;
import com.hmdzl.spspd.change.actors.hero.Hero;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.items.Gold;
import com.hmdzl.spspd.change.items.bombs.Honeypot;
import com.hmdzl.spspd.change.items.Item;
import com.hmdzl.spspd.change.items.artifacts.MasterThievesArmband;
import com.hmdzl.spspd.change.sprites.CharSprite;
import com.hmdzl.spspd.change.sprites.ThiefSprite;
import com.hmdzl.spspd.change.utils.GLog;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class Thief extends Mob {

	protected static final String TXT_STOLE = "%s stole %s from you!";
	protected static final String TXT_CARRIES = "\n\n%s is carrying a _%s_. Stolen obviously.";
	protected static final String TXT_RATCHECK1 = "Spork is avail";
	protected static final String TXT_RATCHECK2 = "Spork is not avail";

	public Item item;

	{
		spriteClass = ThiefSprite.class;

		HP = HT = 80+(adj(0)*Random.NormalIntRange(3, 5));
		defenseSkill = 8+adj(0);

		EXP = 5;
		
		loot = new MasterThievesArmband().identify();
		lootChance = 0.01f;
		
		lootOther = Generator.Category.BERRY;
		lootChanceOther = 0.05f; // by default, see die()

		FLEEING = new Fleeing();
		
		properties.add(Property.ELF);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(1, 7+adj(0));
	}

	@Override
	protected float attackDelay() {
		return 0.5f;
	}

	@Override
	protected boolean act() {
		boolean result = super.act();

		if (state == FLEEING && buff(Terror.class) == null && enemy != null
				&& enemySeen && enemy.buff(Locked.class) == null) {
			state = HUNTING;
		}
		return result;
	}	
	
	@Override
	public void die(Object cause) {
		
		super.die(cause);

	}

	@Override
	protected Item createLoot() {
		if (!Dungeon.limitedDrops.armband.dropped()) {
			Dungeon.limitedDrops.armband.drop();
			return super.createLoot();
		} else
			return new Gold(Random.NormalIntRange(100, 250));
	}

	@Override
	public int attackSkill(Char target) {
		return 12;
	}

	@Override
	public int dr() {
		return 3;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(5) == 0) {
			Buff.affect(enemy, Locked.class,20f);
			state = FLEEING;
		}

		return damage;
	}

	@Override
	public int defenseProc(Char enemy, int damage) {
		if (state == FLEEING) {
			Dungeon.level.drop(new Gold(), pos).sprite.drop();
		}

		return damage;
	}
	
	private class Fleeing extends Mob.Fleeing {
		@Override
		protected void nowhereToRun() {
			if (buff(Terror.class) == null) {
				sprite.showStatus(CharSprite.NEGATIVE, Messages.get(Mob.class, "rage"));
				state = HUNTING;
			} else {
				super.nowhereToRun();
			}
		}
	}
}
