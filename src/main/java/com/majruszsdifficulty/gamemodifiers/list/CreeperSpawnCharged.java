package com.majruszsdifficulty.gamemodifiers.list;

import com.majruszsdifficulty.GameStage;
import com.majruszsdifficulty.Registries;
import com.majruszsdifficulty.gamemodifiers.CustomConditions;
import com.mlib.annotations.AutoInstance;
import com.mlib.gamemodifiers.Condition;
import com.mlib.gamemodifiers.GameModifier;
import com.mlib.gamemodifiers.contexts.OnSpawned;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.monster.Creeper;

@AutoInstance
public class CreeperSpawnCharged extends GameModifier {
	public CreeperSpawnCharged() {
		super( Registries.Modifiers.DEFAULT );

		OnSpawned.listenSafe( this::chargeCreeper )
			.addCondition( CustomConditions.gameStageAtLeast( GameStage.NORMAL ) )
			.addCondition( Condition.chanceCRD( 0.125, true ) )
			.addCondition( Condition.isServer() )
			.addCondition( Condition.excludable() )
			.addCondition( OnSpawned.isNotLoadedFromDisk() )
			.addCondition( Condition.predicate( data->data.target instanceof Creeper ) )
			.insertTo( this );

		this.name( "CreeperSpawnCharged" ).comment( "Creeper may spawn charged." );
	}

	private void chargeCreeper( OnSpawned.Data data ) {
		Creeper creeper = ( Creeper )data.target;
		LightningBolt lightningBolt = EntityType.LIGHTNING_BOLT.create( data.getServerLevel() );
		if( lightningBolt != null ) {
			creeper.thunderHit( data.getServerLevel(), lightningBolt );
			creeper.clearFire();
		}
	}
}
