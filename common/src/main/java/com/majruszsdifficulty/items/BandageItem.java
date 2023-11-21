package com.majruszsdifficulty.items;

import com.majruszlibrary.data.Reader;
import com.majruszlibrary.data.Serializables;
import com.majruszlibrary.emitter.SoundEmitter;
import com.majruszlibrary.entity.EffectDef;
import com.majruszlibrary.events.OnItemTooltip;
import com.majruszlibrary.events.OnPlayerInteracted;
import com.majruszlibrary.item.ItemHelper;
import com.majruszlibrary.math.Random;
import com.majruszlibrary.text.TextHelper;
import com.majruszlibrary.time.TimeHelper;
import com.majruszsdifficulty.MajruszsDifficulty;
import com.majruszsdifficulty.data.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.List;
import java.util.function.Supplier;

public class BandageItem extends Item {
	private static List< EffectDef > NORMAL_EFFECTS = List.of(
		new EffectDef( ()->MobEffects.REGENERATION, 0, 20.0f )
	);
	private static List< EffectDef > GOLDEN_EFFECTS = List.of(
		new EffectDef( ()->MobEffects.REGENERATION, 1, 20.0f ),
		new EffectDef( MajruszsDifficulty.BLEEDING_IMMUNITY, 0, 90.0f )
	);
	private final Supplier< List< EffectDef > > effects;

	public static Supplier< BandageItem > normal() {
		return ()->new BandageItem( Rarity.COMMON, ()->NORMAL_EFFECTS );
	}

	public static Supplier< BandageItem > golden() {
		return ()->new BandageItem( Rarity.UNCOMMON, ()->GOLDEN_EFFECTS );
	}

	static {
		OnPlayerInteracted.listen( BandageItem::use )
			.addCondition( data->data.itemStack.getItem() instanceof BandageItem )
			.addCondition( data->!ItemHelper.isOnCooldown( data.player, MajruszsDifficulty.BANDAGE.get(), MajruszsDifficulty.GOLDEN_BANDAGE.get() ) );

		OnItemTooltip.listen( BandageItem::addEffectInfo )
			.addCondition( data->data.itemStack.getItem() instanceof BandageItem );

		Serializables.getStatic( Config.Items.class )
			.define( "bandage", BandageItem.class );

		Serializables.getStatic( BandageItem.class )
			.define( "normal_effects", Reader.list( Reader.custom( EffectDef::new ) ), ()->NORMAL_EFFECTS, v->NORMAL_EFFECTS = v )
			.define( "golden_effects", Reader.list( Reader.custom( EffectDef::new ) ), ()->GOLDEN_EFFECTS, v->GOLDEN_EFFECTS = v );
	}

	private static void use( OnPlayerInteracted data ) {
		BandageItem bandage = ( BandageItem )data.itemStack.getItem();
		LivingEntity target = data.entity instanceof LivingEntity entity ? entity : data.player;

		bandage.getEffects().forEach( effectDef->target.addEffect( effectDef.toEffectInstance() ) );
		SoundEmitter.of( SoundEvents.ITEM_PICKUP )
			.volume( Random.nextFloat( 0.4f, 0.6f ) )
			.position( target.position() )
			.emit( target.level() );
		BandageItem.removeBleeding( bandage, data.player, target );
		ItemHelper.addCooldown( data.player, TimeHelper.toTicks( 0.7 ), MajruszsDifficulty.BANDAGE.get(), MajruszsDifficulty.GOLDEN_BANDAGE.get() );
		ItemHelper.consumeItemOnUse( data.itemStack, data.player );
		data.player.swing( data.hand, true );
		data.cancelInteraction( InteractionResult.SUCCESS );
	}

	private static void removeBleeding( BandageItem item, Player player, LivingEntity target ) {
		if( target.hasEffect( MajruszsDifficulty.BLEEDING.get() ) && player instanceof ServerPlayer serverPlayer ) {
			if( target.equals( serverPlayer ) ) {
				MajruszsDifficulty.HELPER.triggerAchievement( serverPlayer, "bandage_used" );
			} else if( item.equals( MajruszsDifficulty.GOLDEN_BANDAGE.get() ) ) {
				MajruszsDifficulty.HELPER.triggerAchievement( serverPlayer, "golden_bandage_used_on_others" );
			}
		}
		target.removeEffect( MajruszsDifficulty.BLEEDING.get() );
	}

	private static void addEffectInfo( OnItemTooltip data ) {
		BandageItem bandage = ( BandageItem )data.itemStack.getItem();
		for( EffectDef effectDef : bandage.effects.get() ) {
			data.components.add( effectDef.toComponent().withStyle( ChatFormatting.BLUE ) );
		}

		data.components.add( TextHelper.empty() );
		data.components.add( TextHelper.translatable( "potion.whenDrank" ).withStyle( ChatFormatting.DARK_PURPLE ) );
		data.components.add( TextHelper.translatable( "item.majruszsdifficulty.bandage.effect" ).withStyle( ChatFormatting.BLUE ) );
	}

	private BandageItem( Rarity rarity, Supplier< List< EffectDef > > effects ) {
		super( new Properties().stacksTo( 16 ).rarity( rarity ) );

		this.effects = effects;
	}

	private List< EffectDef > getEffects() {
		return this.effects.get();
	}
}
