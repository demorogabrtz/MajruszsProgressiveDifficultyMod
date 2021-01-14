package com.majruszs_difficulty.events.monster_spawn;

import net.minecraft.entity.LivingEntity;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

/** Handling all 'OnEnemySpawn' events. */
@Mod.EventBusSubscriber
public class OnEnemyToBeSpawnedEvent {
	private static final List< OnEnemyToBeSpawnedBase > registryList = new ArrayList<>();

	static {
		registryList.add( new StrengthenedEntityAttributesOnSpawn() );
		registryList.add( new GiveWitherSkeletonSwordOnSpawn() );
		registryList.add( new GiveEvokerTotemOnSpawn() );
		registryList.add( new ChargeCreeperOnSpawn() );
		registryList.add( new ApplyingNegativeEffectOnCreeperOnSpawn() );
		registryList.add( new SpawnPiglinGroup() );
		registryList.add( new SpawnPillagerGroup() );
		registryList.add( new SpawnSkeletonGroup() );
		registryList.add( new SpawnZombieGroup() );
	}

	@SubscribeEvent
	public static void onSpawn( LivingSpawnEvent.SpecialSpawn event ) {
		LivingEntity entity = event.getEntityLiving();

		for( OnEnemyToBeSpawnedBase register : registryList )
			if( register.shouldBeExecuted( entity ) ) {
				register.onExecute( entity, ( ServerWorld )entity.world );

				if( register.shouldSpawnBeCancelled() )
					event.setCanceled( true );
			}
	}
}
