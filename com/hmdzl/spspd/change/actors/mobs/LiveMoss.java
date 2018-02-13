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

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.buffs.Buff;
import com.hmdzl.spspd.change.actors.buffs.GrowSeed;
import com.hmdzl.spspd.change.actors.buffs.Poison;
import com.hmdzl.spspd.change.items.Generator;
import com.hmdzl.spspd.change.levels.Level;
import com.hmdzl.spspd.change.messages.Messages;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.items.food.MysteryMeat;
import com.hmdzl.spspd.change.sprites.CrabSprite;
import com.hmdzl.spspd.change.sprites.LiveMossSprite;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class LiveMoss extends Mob {

	private boolean liveseed = false;
	
	{
		spriteClass = LiveMossSprite.class;

		HP = HT = 50+(adj(0)*Random.NormalIntRange(1, 3));
		defenseSkill = 5+adj(1);

		EXP = 3;
		maxLvl = 9;

		loot = Generator.random(Generator.Category.MUSHROOM);
		lootChance = 0.3f;
		
		properties.add(Property.PLANT);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange(3, 6+adj(0));
	}

	@Override
	public int attackSkill(Char target) {
		return 12+adj(0);
	}

	@Override
	public int dr() {
		return 4;
	}

	@Override
	public int attackProc(Char enemy, int damage) {
		if (Random.Int(3) == 0 && !liveseed) {
			Buff.affect(enemy, GrowSeed.class).reignite(enemy);
			liveseed=true;
		}

		return damage;
	}
	
	private final String LIVESEED = "liveseed";

	@Override
	public void storeInBundle(Bundle bundle) {
		super.storeInBundle(bundle);
		bundle.put(LIVESEED, liveseed);
	}

	@Override
	public void restoreFromBundle(Bundle bundle) {
		super.restoreFromBundle(bundle);
		liveseed = bundle.getBoolean(LIVESEED);
	}	

}
