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
package com.hmdzl.spspd.levels.painters;

import com.hmdzl.spspd.Dungeon;
import com.hmdzl.spspd.items.Gold;
import com.hmdzl.spspd.items.Heap;
import com.hmdzl.spspd.items.keys.IronKey;
import com.hmdzl.spspd.levels.Level;
import com.hmdzl.spspd.levels.Room;
import com.hmdzl.spspd.levels.Terrain;
import com.watabou.utils.Random;

public class TreasuryPainter extends Painter {

	public static void paint(Level level, Room room) {

		fill(level, room, Terrain.WALL);
		fill(level, room, 1, Terrain.EMPTY);

		set(level, room.center(), Terrain.STATUE);

		Heap.Type heapType = Random.Int(2) == 0 ? Heap.Type.CHEST
				: Heap.Type.HEAP;

		int n = Random.IntRange(2, 3);
		for (int i = 0; i < n; i++) {
			int pos;
			do {
				pos = room.random();
			} while (level.map[pos] != Terrain.EMPTY
					|| level.heaps.get(pos) != null);
			level.drop(new Gold().random(), pos).type = (i == 0
					&& heapType == Heap.Type.CHEST ? Heap.Type.MIMIC : heapType);
		}

		if (heapType == Heap.Type.HEAP) {
			for (int i = 0; i < n; i++) {
			int pos;
			do {
				pos = room.random();
			} while (level.map[pos] != Terrain.EMPTY
					|| level.heaps.get(pos) != null);
			level.drop(new Gold().random(), pos).type = (i == 0
					&& heapType == Heap.Type.CHEST ? Heap.Type.MIMIC : heapType);
			}
		}

		room.entrance().set(Room.Door.Type.LOCKED);
		level.addItemToSpawn(new IronKey(Dungeon.depth));
	}
}
