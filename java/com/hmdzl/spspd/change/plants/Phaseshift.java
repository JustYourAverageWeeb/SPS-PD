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
package com.hmdzl.spspd.change.plants;

import com.hmdzl.spspd.change.Dungeon;
import com.hmdzl.spspd.change.actors.Char;
import com.hmdzl.spspd.change.actors.blobs.Blob;
import com.hmdzl.spspd.change.actors.blobs.WaterOfTransmutation;
import com.hmdzl.spspd.change.actors.blobs.WellWater;
import com.hmdzl.spspd.change.items.TransmutationBall;
import com.hmdzl.spspd.change.items.food.fruit.Blackberry;
import com.hmdzl.spspd.change.items.food.fruit.Blueberry;
import com.hmdzl.spspd.change.items.food.fruit.Cloudberry;
import com.hmdzl.spspd.change.items.food.fruit.Moonberry;
import com.hmdzl.spspd.change.items.potions.PotionOfMight;
import com.hmdzl.spspd.change.scenes.GameScene;
import com.hmdzl.spspd.change.sprites.ItemSpriteSheet;
import com.watabou.utils.Random;

public class Phaseshift extends Plant {

	{
		image = 14;
	}

	@Override
	public void activate(Char ch) {
		super.activate(ch);
		Dungeon.level.drop(new TransmutationBall(), pos).sprite.drop();
		switch (Random.Int(4)){
			case 0:
				Dungeon.level.drop(new Blackberry(), pos).sprite.drop();
				break;
			case 1:
				Dungeon.level.drop(new Blueberry(), pos).sprite.drop();
				break;
			case 2:
				Dungeon.level.drop(new Cloudberry(), pos).sprite.drop();
				break;
			case 3:
				Dungeon.level.drop(new Moonberry(), pos).sprite.drop();
				break;
		}
	}

	public static class Seed extends Plant.Seed {
		{
			image = ItemSpriteSheet.SEED_PHASEPITCHER;

			plantClass = Phaseshift.class;
			alchemyClass = PotionOfMight.class;				
		}
		

	}		
}
