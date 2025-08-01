package com.projectkorra.projectkorra.airbending.combo;

import java.util.ArrayList;
import java.util.List;

import com.projectkorra.projectkorra.ability.util.ComboUtil;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import com.projectkorra.projectkorra.object.HorizontalVelocityTracker;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AirAbility;
import com.projectkorra.projectkorra.ability.ComboAbility;
import com.projectkorra.projectkorra.ability.util.Collision;
import com.projectkorra.projectkorra.ability.util.ComboManager.AbilityInformation;
import com.projectkorra.projectkorra.attribute.Attribute;
import com.projectkorra.projectkorra.command.Commands;
import com.projectkorra.projectkorra.firebending.combo.ComboStream;
import com.projectkorra.projectkorra.util.DamageHandler;

public class AirSweep extends AirAbility implements ComboAbility {

	private int progressCounter;
	@Attribute(Attribute.COOLDOWN)
	private long cooldown;
	@Attribute(Attribute.DAMAGE)
	private double damage;
	@Attribute(Attribute.SPEED)
	private double speed;
	@Attribute(Attribute.RANGE)
	private double range;
	@Attribute(Attribute.KNOCKBACK)
	private double knockback;
	private Location origin;
	private Location currentLoc;
	private Location destination;
	private Vector direction;
	private ArrayList<Entity> affectedEntities;
	private ArrayList<ComboStream> tasks;
	private double radius;

	public AirSweep(final Player player) {
		super(player);

		this.affectedEntities = new ArrayList<>();
		this.tasks = new ArrayList<>();

		if (!this.bPlayer.canBendIgnoreBindsCooldowns(this)) {
			return;
		}

		if (this.bPlayer.isOnCooldown(this)) {
			return;
		}

		this.damage = getConfig().getDouble("Abilities.Air.AirSweep.Damage");
		this.range = getConfig().getDouble("Abilities.Air.AirSweep.Range");
		this.speed = getConfig().getDouble("Abilities.Air.AirSweep.Speed");
		this.knockback = getConfig().getDouble("Abilities.Air.AirSweep.Knockback");
		this.cooldown = getConfig().getLong("Abilities.Air.AirSweep.Cooldown");
		this.radius = getConfig().getDouble("Abilities.Air.AirSweep.Radius");

		this.bPlayer.addCooldown(this);
		this.start();
	}

	@Override
	public String getName() {
		return "AirSweep";
	}

	@Override
	public boolean isCollidable() {
		return true;
	}

	@Override
	public void handleCollision(final Collision collision) {
		if (collision.isRemovingFirst()) {
			final ArrayList<ComboStream> newTasks = new ArrayList<>();
			final double collisionDistanceSquared = Math.pow(this.getCollisionRadius() + collision.getAbilitySecond().getCollisionRadius(), 2);
			// Remove all of the streams that are by this specific ourLocation.
			// Don't just do a single stream at a time or this algorithm becomes O(n^2) with Collision's detection algorithm.
			for (final ComboStream task : this.getTasks()) {
				if (task instanceof ComboStream) {
					final ComboStream stream = (ComboStream) task;
					if (stream.getLocation().distanceSquared(collision.getLocationSecond()) > collisionDistanceSquared) {
						newTasks.add(stream);
					} else {
						stream.cancel();
					}
				} else {
					newTasks.add(task);
				}
			}
			this.setTasks(newTasks);
		}
	}

	@Override
	public List<Location> getLocations() {
		final ArrayList<Location> locations = new ArrayList<>();
		for (final ComboStream task : this.getTasks()) {
			if (task instanceof ComboStream) {
				final ComboStream stream = (ComboStream) task;
				locations.add(stream.getLocation());
			}
		}
		return locations;
	}

	@Override
	public void progress() {
		this.progressCounter++;
		if (this.player.isDead() || !this.player.isOnline()) {
			this.remove();
			return;
		} else if (this.currentLoc != null && GeneralMethods.isRegionProtectedFromBuild(this, this.currentLoc)) {
			this.remove();
			return;
		}

		if (this.origin == null) {
			// AirSweep come from feet instead
			this.direction = this.player.getEyeLocation().getDirection().normalize();
			this.origin = this.player.getLocation().add(this.direction.clone().multiply(10));
		}
		if (this.progressCounter < 8) {
			return;
		}
		if (this.destination == null) {
			this.destination = GeneralMethods.getMainHandLocation(player).add(GeneralMethods.getMainHandLocation(player).getDirection().normalize().multiply(10));
			final Vector origToDest = GeneralMethods.getDirection(this.origin, this.destination);
			final Location feet = player.getLocation();
			for (double i = 0; i < 30; i++) {
				final Location endLoc = this.origin.clone().add(origToDest.clone().multiply(i / 30));
				if (GeneralMethods.locationEqualsIgnoreDirection(feet, endLoc)) {
					continue;
				}
				final Vector vec = GeneralMethods.getDirection(feet, endLoc);

				final ComboStream fs = new ComboStream(this.player, this, vec, feet, this.range, this.speed);
				fs.setDensity(1);
				fs.setSpread(0F);
				fs.setUseNewParticles(true);
				fs.setParticleEffect(getAirbendingParticles());
				fs.setCollides(false);
				fs.start();
				this.tasks.add(fs);
			}
		}
		this.manageAirVectors();
	}

	public void manageAirVectors() {
		for (int i = 0; i < this.tasks.size(); i++) {
			if (((ComboStream) this.tasks.get(i)).isRemoved()) {
				this.tasks.remove(i);
				i--;
			}
		}
		if (this.tasks.size() == 0) {
			this.remove();
			return;
		}
		for (int i = 0; i < this.tasks.size(); i++) {
			final ComboStream fstream = (ComboStream) this.tasks.get(i);
			final Location loc = fstream.getLocation();

			if (GeneralMethods.isRegionProtectedFromBuild(this, loc)) {
				fstream.remove();
				return;
			}

			if (!this.isTransparent(loc.getBlock())) {
				if (!this.isTransparent(loc.clone().add(0, 0.2, 0).getBlock())) {
					fstream.remove();
					return;
				}
			}
			if (i % 3 == 0) {
				for (final Entity entity : GeneralMethods.getEntitiesAroundPoint(loc, radius)) {
					if (GeneralMethods.isRegionProtectedFromBuild(this, entity.getLocation())) {
						this.remove();
						return;
					}
					if (!entity.equals(this.player) && !(entity instanceof Player && Commands.invincible.contains(((Player) entity).getName()))) {
						if (this.knockback != 0) {
							final Vector force = fstream.getLocation().getDirection();
							GeneralMethods.setVelocity(this, entity, force.clone().multiply(this.knockback));
							new HorizontalVelocityTracker(entity, this.player, 200l, this);
							entity.setFallDistance(0);
						}
						if(!this.affectedEntities.contains(entity)) {
							this.affectedEntities.add(entity);
							if (this.damage != 0) {
								if (entity instanceof LivingEntity) {
									DamageHandler.damageEntity(entity, this.damage, this);
								}
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void remove() {
		super.remove();
		for (final ComboStream task : this.tasks) {
			task.cancel();
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
		return new AirSweep(player);
	}

	@Override
	public ArrayList<AbilityInformation> getCombination() {
		return ComboUtil.generateCombinationFromList(this, ConfigManager.defaultConfig.get().getStringList("Abilities.Air.AirSweep.Combination"));
	}

	public Location getOrigin() {
		return this.origin;
	}

	public void setOrigin(final Location origin) {
		this.origin = origin;
	}

	public Location getCurrentLoc() {
		return this.currentLoc;
	}

	public void setCurrentLoc(final Location currentLoc) {
		this.currentLoc = currentLoc;
	}

	public Location getDestination() {
		return this.destination;
	}

	public void setDestination(final Location destination) {
		this.destination = destination;
	}

	public Vector getDirection() {
		return this.direction;
	}

	public void setDirection(final Vector direction) {
		this.direction = direction;
	}

	public int getProgressCounter() {
		return this.progressCounter;
	}

	public void setProgressCounter(final int progressCounter) {
		this.progressCounter = progressCounter;
	}

	public double getDamage() {
		return this.damage;
	}

	public void setDamage(final double damage) {
		this.damage = damage;
	}

	public double getSpeed() {
		return this.speed;
	}

	public void setSpeed(final double speed) {
		this.speed = speed;
	}

	public double getRange() {
		return this.range;
	}

	public void setRange(final double range) {
		this.range = range;
	}

	public double getKnockback() {
		return this.knockback;
	}

	public void setKnockback(final double knockback) {
		this.knockback = knockback;
	}

	public ArrayList<Entity> getAffectedEntities() {
		return this.affectedEntities;
	}

	public ArrayList<ComboStream> getTasks() {
		return this.tasks;
	}

	public void setTasks(final ArrayList<ComboStream> tasks) {
		this.tasks = tasks;
	}
}
