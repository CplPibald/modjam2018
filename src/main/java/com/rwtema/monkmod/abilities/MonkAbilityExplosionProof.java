package com.rwtema.monkmod.abilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

public class MonkAbilityExplosionProof extends MonkAbilityProtection {

	private final float aFloat;

	public MonkAbilityExplosionProof(float protection) {
		super("explosion_resistance");
		aFloat = protection;
	}

	@Override
	public float getAbsorbtion(DamageSource source, EntityPlayer player) {
		return aFloat;
	}

	@Override
	public boolean canHandle(EntityPlayer player, DamageSource source) {
		return source.isExplosion();
	}
}
