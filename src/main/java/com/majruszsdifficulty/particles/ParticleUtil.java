package com.majruszsdifficulty.particles;

import com.majruszsdifficulty.MajruszsDifficulty;
import com.majruszsdifficulty.Registries;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber( modid = MajruszsDifficulty.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD )
public class ParticleUtil {
	@SubscribeEvent
	public static void registerParticles( RegisterParticleProvidersEvent event ) {
		event.register( Registries.BLOOD.get(), BloodParticle.Factory::new );
	}
}
