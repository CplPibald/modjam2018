package com.rwtema.monkmod.advancements.criteria;

import com.rwtema.monkmod.data.MonkData;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.Vec3d;

public class MonkRequirementSunriseSunset extends MonkRequirementTick {
	public MonkRequirementSunriseSunset(int stareTime) {
		super("mediate_sunrise", stareTime);
	}

	@Override
	protected void doTick(EntityPlayerMP player, MonkData monkData) {
		double celestialAngle = player.world.getCelestialAngle(0) * Math.PI * 2;
		double sunHeight = Math.cos(celestialAngle);
		if (Math.abs(sunHeight) < 0.1) {
			Vec3d vec3d1 = player.getLook(1.0F);
			Vec3d sunDir = new Vec3d(-Math.sin(celestialAngle), sunHeight, 0);

			if (sunDir.dotProduct(vec3d1) > 0.99) {
				monkData.increaseProgress(1);
				if (monkData.getProgress() > requirementLimit) {
					grantLevel(player);
				}
			}
		} else {
			monkData.setProgress(0);
		}
	}
}
