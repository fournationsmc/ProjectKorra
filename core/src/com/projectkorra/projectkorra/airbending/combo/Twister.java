package com.projectkorra.projectkorra.airbending.combo;

import java.util.ArrayList;

import com.projectkorra.projectkorra.ability.util.ComboUtil;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.region.RegionProtection;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ability.AirAbility;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.util.ComboManager.AbilityInformation;
import com.projectkorra.projectkorra.attribute.Attribute;
import com.projectkorra.projectkorra.command.Commands;

public class Twister extends AirAbility implements ComboAbility {

	public enum AbilityState {
		TWISTER_MOVING, TWISTER_STATIONARY
	}

	@Attribute(Attribute.COOLDOWN)
	private long cooldown;
	private long time;
	@Attribute(Attribute.SPEED)
	private double speed;
	@Attribute(Attribute.RANGE)
	private double range;
	@Attribute(Attribute.HEIGHT)
	private double twisterHeight;
	@Attribute(Attribute.RADIUS)
	private double twisterRadius;
	private double twisterDegreeParticles;
	private double twisterHeightParticles;
	private double twisterRemoveDelay;
    private double throwStrength;
	private AbilityState state;
	private Location origin;
	private Location currentLoc;
	private Location destination;
	private Vector direction;

	private final ArrayList<Entity> affectedEntities;

	public Twister(final Player player) {
		super(player);

		this.affectedEntities = new ArrayList<>();

		if (!this.bPlayer.canBendIgnoreBindsCooldowns(this)) {
			return;
		}

		if (this.bPlayer.isOnCooldown(this)) {
			return;
		}

		this.range = getConfig().getDouble("Abilities.Air.Twister.Range");
		this.speed = getConfig().getDouble("Abilities.Air.Twister.Speed");
		this.cooldown = getConfig().getLong("Abilities.Air.Twister.Cooldown");
		this.twisterHeight = getConfig().getDouble("Abilities.Air.Twister.Height");
		this.twisterRadius = getConfig().getDouble("Abilities.Air.Twister.Radius");
		this.twisterDegreeParticles = getConfig().getDouble("Abilities.Air.Twister.DegreesPerParticle");
		this.twisterHeightParticles = getConfig().getDouble("Abilities.Air.Twister.HeightPerParticle");
		this.twisterRemoveDelay = getConfig().getLong("Abilities.Air.Twister.RemoveDelay");
        this.throwStrength = getConfig().getDouble("Abilities.Air.Twister.ThrowStrength");

		this.bPlayer.addCooldown(this);
		this.start();
	}

	@Override
	public String getName() {
		return "Twister";
	}

	@Override
	public void progress() {
        if (this.player.isDead() || !this.player.isOnline()) {
            this.remove();
            return;
        } else if (this.currentLoc != null && RegionProtection.isRegionProtected(this, this.currentLoc)) {
            this.remove();
            return;
        }

        if (this.destination == null) {
            this.state = AbilityState.TWISTER_MOVING;
            this.direction = this.player.getEyeLocation().getDirection().clone().normalize();
            this.direction.setY(0);
            this.origin = this.player.getLocation().add(this.direction.clone().multiply(2));
            this.destination = this.player.getLocation().add(this.direction.clone().multiply(this.range));
            this.currentLoc = this.origin.clone();
        }
        if (this.origin.distanceSquared(this.currentLoc) < this.origin.distanceSquared(this.destination) && this.state == AbilityState.TWISTER_MOVING) {
            this.currentLoc.add(this.direction.clone().multiply(this.speed));
        } else if (this.state == AbilityState.TWISTER_MOVING) {
            this.state = AbilityState.TWISTER_STATIONARY;
            this.time = System.currentTimeMillis();
        } else if (System.currentTimeMillis() - this.time >= this.twisterRemoveDelay) {
            this.remove();
            return;
        } else if (RegionProtection.isRegionProtected(this, this.currentLoc)) {
            this.remove();
            return;
        }

        final Block topBlock = GeneralMethods.getTopBlock(this.currentLoc, 3, -3);
        if (topBlock == null) {
            this.remove();
            return;
        }
        this.currentLoc.setY(topBlock.getLocation().getY());

        final double height = this.twisterHeight;
        final double radius = this.twisterRadius;
        for (double y = 0; y < height; y += this.twisterHeightParticles) {
            final double animRadius = ((radius / height) * y);
            for (double i = -180; i <= 180; i += this.twisterDegreeParticles) {
                final Vector animDir = GeneralMethods.rotateXZ(new Vector(1, 0, 1), i);
                final Location animLoc = this.currentLoc.clone().add(animDir.multiply(animRadius));
                animLoc.add(0, y, 0);
                playAirbendingParticles(animLoc, 1, 0, 0, 0);
            }
        }
        playAirbendingSound(this.currentLoc);


		for (int i = 0; i < height; i += 3) {
			for (final Entity entity : GeneralMethods.getEntitiesAroundPoint(this.currentLoc.clone().add(0, i, 0), radius * 0.75)) {
                Vector dir = entity.getLocation().toVector().subtract(this.currentLoc.toVector());

                // Slight uplift
                dir.setY(dir.getY() * 0.5 + 0.3);

                if (entity instanceof TNTPrimed primed) {
                    primed.setVelocity(dir.clone().normalize().multiply(this.throwStrength));
                    continue;
                }

                if (entity instanceof Arrow arrow) {
                    arrow.setVelocity(dir.clone().normalize().multiply(this.throwStrength));
                    continue;
                }

				if (!this.affectedEntities.contains(entity) && !entity.equals(this.player)) {
					this.affectedEntities.add(entity);
				}
			}
		}

		for (final Entity entity : this.affectedEntities) {
			if (RegionProtection.isRegionProtected(player, entity.getLocation(), this) || ((entity instanceof Player) && Commands.invincible.contains(entity.getName()))) {
				continue;
			}

			final Vector forceDir = GeneralMethods.getDirection(entity.getLocation(), this.currentLoc.clone().add(0, height, 0));
			GeneralMethods.setVelocity(this, entity, forceDir.clone().normalize().multiply(0.3));
		}
	}

	@Override
	public boolean isSneakAbility() {
		return true;
	}

	@Override
	public boolean isHarmlessAbility() {
		return false;
	}

	@Override
	public long getCooldown() {
		return this.cooldown;
	}

	@Override
	public Location getLocation() {
		return this.origin;
	}

	@Override
	public Object createNewComboInstance(final Player player) {
		return new Twister(player);
	}

	@Override
	public ArrayList<AbilityInformation> getCombination() {
		return ComboUtil.generateCombinationFromList(this, ConfigManager.defaultConfig.get().getStringList("Abilities.Air.Twister.Combination"));
	}
}
