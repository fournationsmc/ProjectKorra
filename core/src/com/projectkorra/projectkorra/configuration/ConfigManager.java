package com.projectkorra.projectkorra.configuration;

import com.google.common.collect.Sets;
import com.projectkorra.projectkorra.Element;
import com.projectkorra.projectkorra.GeneralMethods;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ConfigManager {

	public static Config presetConfig;
	public static Config defaultConfig;
	public static Config languageConfig;
	public static Config avatarStateConfig;
	public static Config collisionConfig;

	public ConfigManager() {
		presetConfig = new Config(new File("presets.yml"));
		defaultConfig = new Config(new File("config.yml"));
		languageConfig = new Config(new File("language.yml"));
		avatarStateConfig = new Config(new File("avatarstate.yml"));
		collisionConfig = new Config(new File("collision.yml"));

		configCheck(ConfigType.DEFAULT);
		configCheck(ConfigType.LANGUAGE);
		configCheck(ConfigType.PRESETS);
		configCheck(ConfigType.COLLISION);
		configCheck(ConfigType.AVATAR_STATE);
	}

	public static void configCheck(final ConfigType type) {
		FileConfiguration config;
		if (type == ConfigType.PRESETS) {
			config = presetConfig.get();

			final ArrayList<String> abilities = new ArrayList<String>();
			abilities.add("FireBlast");
			abilities.add("AirBlast");
			abilities.add("WaterManipulation");
			abilities.add("EarthBlast");
			abilities.add("FireBurst");
			abilities.add("AirBurst");
			abilities.add("Torrent");
			abilities.add("Shockwave");
			abilities.add("AvatarState");

			config.addDefault("Example", abilities);

			presetConfig.save();
		} else if (type == ConfigType.COLLISION) {
			config = collisionConfig.get();
			String header =
					"To add a collision copy this line into 'AddCollisions' and replace values:\n" +
							"- FirstAbility, SecondAbility, RemoveFirst, RemoveSecond\n\n" +
							"FirstAbility - first ability\n" +
							"SecondAbility - second ability\n" +
							"RemoveFirst - if true it removes the first ability on collision\n" +
							"RemoveSecond - if true it removes the second ability on collision\n\n" +
							"if ability is not found it will say in console which ability and what line is wrong";
			config.options().header(header);
			config.addDefault("AddCollisions", Arrays.asList("FirstAbility, SecondAbility, RemoveFirst, RemoveSecond"));
			config.addDefault("Collisions", Arrays.asList("FirstAbility, SecondAbility"));
			config.addDefault("FallingBlockCollisions", Arrays.asList("FallingBlockAbility, VelocityAbility"));
			collisionConfig.save();

		} else if (type == ConfigType.LANGUAGE) {
			config = languageConfig.get();

			config.addDefault("Chat.Enable", !hasChatPlugin());
			config.addDefault("Chat.Format", "<name>: <message>");
			config.addDefault("Chat.Colors.Avatar", "DARK_PURPLE");
			config.addDefault("Chat.Colors.Air", "GRAY");
			config.addDefault("Chat.Colors.AirSub", "DARK_GRAY");
			config.addDefault("Chat.Colors.Spiritual", "#cab0ff");
			config.addDefault("Chat.Colors.Flight", "#dbf5ff");
			config.addDefault("Chat.Colors.Water", "AQUA");
			config.addDefault("Chat.Colors.WaterSub", "DARK_AQUA");
			config.addDefault("Chat.Colors.Blood", "#a30010");
			config.addDefault("Chat.Colors.Ice", "#99f5ff");
			config.addDefault("Chat.Colors.Plant", "#008048");
			config.addDefault("Chat.Colors.Healing", "#36d2e3");
			config.addDefault("Chat.Colors.Earth", "GREEN");
			config.addDefault("Chat.Colors.EarthSub", "DARK_GREEN");
			config.addDefault("Chat.Colors.Lava", "#c73800");
			config.addDefault("Chat.Colors.Metal", "#c7c5c5");
			config.addDefault("Chat.Colors.Sand", "#ffdc82");
			config.addDefault("Chat.Colors.Fire", "RED");
			config.addDefault("Chat.Colors.FireSub", "DARK_RED");
			config.addDefault("Chat.Colors.BlueFire", "#1ac5fd");
			config.addDefault("Chat.Colors.Combustion", "#690213");
			config.addDefault("Chat.Colors.Lightning", "#820d0d");
			config.addDefault("Chat.Colors.Chi", "GOLD");
			config.addDefault("Chat.Colors.ChiSub", "YELLOW");
			config.addDefault("Chat.Branding.JoinMessage.Enabled", true);
			config.addDefault("Chat.Branding.Color", "GOLD");
			config.addDefault("Chat.Branding.Borders.TopBorder", "");
			config.addDefault("Chat.Branding.Borders.BottomBorder", "");
			config.addDefault("Chat.Branding.ChatPrefix.Prefix", "");
			config.addDefault("Chat.Branding.ChatPrefix.Suffix", " \u00BB ");
			config.addDefault("Chat.Branding.ChatPrefix.Main", "ProjectKorra");
			config.addDefault("Chat.Branding.ChatPrefix.Hover", "Bending brought to you by ProjectKorra!\\nClick for more info.");
			config.addDefault("Chat.Branding.ChatPrefix.Click", "https://projectkorra.com");

			config.addDefault("Chat.Prefixes.Air", "[Air]");
			config.addDefault("Chat.Prefixes.Earth", "[Earth]");
			config.addDefault("Chat.Prefixes.Fire", "[Fire]");
			config.addDefault("Chat.Prefixes.Water", "[Water]");
			config.addDefault("Chat.Prefixes.Chi", "[Chi]");
			config.addDefault("Chat.Prefixes.Avatar", "[Avatar]");
			config.addDefault("Chat.Prefixes.Nonbender", "[Nonbender]");

			config.addDefault("Board.Title", "&lAbilities");
			config.addDefault("Board.Prefix.Text", "> ");
			config.addDefault("Board.Prefix.SelectedColor", ChatColor.WHITE.getName());
			config.addDefault("Board.Prefix.NonSelectedColor", ChatColor.DARK_GRAY.getName());
			config.addDefault("Board.EmptySlot", "&8-- Slot {slot_number} --");
			config.addDefault("Board.MiscSeparator", "  ----------");
			
			if (!config.contains("Board.Extras")) {
			    config.addDefault("Board.Extras.RaiseEarthWall", Element.EARTH.getColor().getName());
			    config.addDefault("Board.Extras.SurgeWave", Element.WATER.getColor().getName());
			    config.addDefault("Board.Extras.SpoutHop", Element.WATER.getColor().getName());
				config.addDefault("Board.Extras.IceSpikeField", Element.ICE.getColor().getName());
			}

			config.addDefault("Extras.Water.NightMessage", "Your waterbending has become empowered due to the moon rising.");
			config.addDefault("Extras.Water.DayMessage", "You feel the empowering of your waterbending subside as the moon sets.");
			config.addDefault("Extras.Fire.NightMessage", "You feel the empowering of your firebending subside as the sun sets.");
			config.addDefault("Extras.Fire.DayMessage", "You feel the strength of the rising sun empower your firebending.");

			config.addDefault("Commands.NoPermission", "You do not have permission to do that.");
			config.addDefault("Commands.MustBePlayer", "You must be a player to perform this action.");
			config.addDefault("Commands.ProperUsage", "Proper Usage: {command}");

			config.addDefault("Commands.Who.Description", "This command will tell you what element all players that are online are (If you don't specify a player) or give you information about the player that you specify.");
			config.addDefault("Commands.Who.NoPlayersOnline", "There is no one online.");
			config.addDefault("Commands.Who.DatabaseOverload", "The database appears to be overloaded. Please try again later.");
			config.addDefault("Commands.Who.PlayerOffline", "{target} is currently offline. Please wait a moment...");
			config.addDefault("Commands.Who.PlayerUnknown", "{target} has not played on this server before");

			config.addDefault("Commands.Version.Description", "Displays the installed version of ProjectKorra.");

			config.addDefault("Commands.Toggle.Description", "This command will toggle a player's own bending on or off. If toggled off, all abilities should stop working until it is toggled back on. Logging off will automatically toggle your Bending back on. If you run the command /bending toggle all, Bending will be turned off for all players and cannot be turned back on until the command is run again.");
			config.addDefault("Commands.Toggle.ToggledOn", "You have turned your bending back on.");
			config.addDefault("Commands.Toggle.ToggledOff", "Your bending has been toggled off. You will not be able to use most abilities until you toggle it back.");
			config.addDefault("Commands.Toggle.ToggledPassivesOn", "You have turned your passives back on.");
			config.addDefault("Commands.Toggle.ToggledPassivesOff", "Your passives has been toggled off.");

			config.addDefault("Commands.Toggle.ToggleOnSingleElement", "You have toggled on your {element}.");
			config.addDefault("Commands.Toggle.ToggleOffSingleElement", "You have toggled off your {element}.");
			config.addDefault("Commands.Toggle.ToggleOnSingleElementPassive", "You have toggled on your {element} Passives.");
			config.addDefault("Commands.Toggle.ToggleOffSingleElementPassive", "You have toggled off your {element} Passives.");
			config.addDefault("Commands.Toggle.WrongElement", "You do not have that element.");
			config.addDefault("Commands.Toggle.All.ToggledOffForAll", "Bending is currently toggled off for all players.");
			config.addDefault("Commands.Toggle.All.ToggleOn", "Bending has been toggled back on for all players.");
			config.addDefault("Commands.Toggle.All.ToggleOff", "Bending has been toggled off for all players.");
			config.addDefault("Commands.Toggle.Other.ToggledOnElementConfirm", "You've toggled on {target}'s {element}");
			config.addDefault("Commands.Toggle.Other.ToggledOffElementConfirm", "You've toggled off {target}'s {element}");
			config.addDefault("Commands.Toggle.Other.ToggledOnElementByOther", "Your {element} has been toggled on by {sender}.");
			config.addDefault("Commands.Toggle.Other.ToggledOffElementByOther", "Your {element} has been toggled off by {sender}.");
			config.addDefault("Commands.Toggle.Other.PlayerNotFound", "Target is not found.");
			config.addDefault("Commands.Toggle.Other.WrongElement", "{target} doesn't have that element.");
			config.addDefault("Commands.Toggle.Reminder", "Reminder, you toggled your bending before signing off. Enable it again with /bending toggle.");

			config.addDefault("Commands.Remove.Description", "This command will remove the element of the targeted [Player]. The player will be able to re-pick their element after this command is run on them, assuming their bending was not permaremoved.");
			config.addDefault("Commands.Remove.Other.RemovedAllElements", "Your bending has been removed by {sender}.");
			config.addDefault("Commands.Remove.Other.RemovedAllElementsConfirm", "You've removed {target}'s bending.");
			config.addDefault("Commands.Remove.Other.RemovedElement", "Your {element} has been removed by {sender}.");
			config.addDefault("Commands.Remove.Other.RemovedElementConfirm", "You removed {target}'s {element}.");
			config.addDefault("Commands.Remove.Other.WrongElement", "{target} does not have that element.");
			config.addDefault("Commands.Remove.RemovedElement", "You've removed your {element}.");
			config.addDefault("Commands.Remove.InvalidElement", "That element is invalid!");
			config.addDefault("Commands.Remove.WrongElement", "You do not have that element.");
			config.addDefault("Commands.Remove.PlayerNotFound", "That player has not played before!");
			config.addDefault("Commands.Remove.NoElements", "You have no elements to remove!");
			config.addDefault("Commands.Remove.Other.NoElements", "{target} has no elements to remove!");
			config.addDefault("Commands.Remove.Other.NoElementsWithTemps", "{target} has no elements to remove, but they have temporary elements! Either specify the temp element to remove or use /b temp remove <player> all");

			config.addDefault("Commands.Reload.Description", "This command will reload the bending config files.");
			config.addDefault("Commands.Reload.SuccessfullyReloaded", "Bending Config reloaded!");

			config.addDefault("Commands.Preset.Description", "This command manages Presets, which are saved bindings. Use /bending preset list to view your existing presets, use /bending [create|delete] [name] to manage your presets, and use /bending bind [name] to bind an existing preset.");
			config.addDefault("Commands.Preset.NoPresets", "You do not have any presets.");
			config.addDefault("Commands.Preset.NoPresetName", "You don't have a preset with that name.");
			config.addDefault("Commands.Preset.Created", "Created a new preset named '{name}'.");
			config.addDefault("Commands.Preset.Delete", "You have deleted your '{name}' preset.");
			config.addDefault("Commands.Preset.Removed", "Your bending has been permanently removed.");
			config.addDefault("Commands.Preset.RemovedConfirm", "You have permanently removed {target}'s bending.");
			config.addDefault("Commands.Preset.SuccesfullyBound", "Your binds have been set to match the '{name}' preset.");
			config.addDefault("Commands.Preset.SuccesfullyCopied", "Your binds have been set to match {target}'s binds.");
			config.addDefault("Commands.Preset.FailedToBindAll", "Some abilities were not bound because you cannot bend the required element.");
			config.addDefault("Commands.Preset.DatabaseError", "An error occurred while processing the preset '{name}'");
			config.addDefault("Commands.Preset.AlreadyExists", "A preset with that name already exists.");
			config.addDefault("Commands.Preset.BendingPermanentlyRemoved", "Your bending was permanently removed.");
			config.addDefault("Commands.Preset.PlayerNotFound", "Player not found.");
			config.addDefault("Commands.Preset.InvalidName", "You must enter a valid name for your preset.");
			config.addDefault("Commands.Preset.MaxPresets", "You've reached your maximum number of presets.");
			config.addDefault("Commands.Preset.CantEditBinds", "You can't edit your binds right now!");
			config.addDefault("Commands.Preset.Other.BendingPermanentlyRemoved", "That player's bending was permanently removed.");
			config.addDefault("Commands.Preset.Other.SuccesfullyBoundConfirm", "The bound slots of {target} have been set to match the {name} preset.");
			config.addDefault("Commands.Preset.External.NoPresetName", "No external preset found with that name.");

			config.addDefault("Commands.Cooldown.Description", "Set, reset or view a cooldown for a player");
			config.addDefault("Commands.Cooldown.InvalidPlayer", "That player has not played before!");
			config.addDefault("Commands.Cooldown.InvalidCooldown", "Could not find an existing cooldown with the name '{cooldown}'!");
			config.addDefault("Commands.Cooldown.InvalidTime", "Could not parse {value} as a valid time! ");
			config.addDefault("Commands.Cooldown.View", "Listing {number} cooldowns for {player}");
			config.addDefault("Commands.Cooldown.ViewNone", "{player} has nothing on cooldown!");
			config.addDefault("Commands.Cooldown.ViewMax", "Listing top 10 cooldowns for {player}");
			config.addDefault("Commands.Cooldown.ResetPreview", "Click to reset {cooldown}");
			config.addDefault("Commands.Cooldown.Reset", "Cooldown '{cooldown}' reset for {player}!");
			config.addDefault("Commands.Cooldown.ResetAll", "Cleared all cooldowns for {player}");
			config.addDefault("Commands.Cooldown.SetAll", "You cannot set the value of every single cooldown to something other than 0!");
			config.addDefault("Commands.Cooldown.Set", "Set cooldown '{cooldown}' for {player} to {value}");
			config.addDefault("Commands.Cooldown.SetNoValue", "You must provide a value to set a cooldown to!");

			config.addDefault("Commands.Stats.InvalidLookup", "Invalid lookup argument.");
			config.addDefault("Commands.Stats.InvalidSearchType", "Invalid search type.");
			config.addDefault("Commands.Stats.InvalidStatistic", "Invalid statistic.");
			config.addDefault("Commands.Stats.InvalidPlayer", "Player '%player%' not found.");
			config.addDefault("Commands.Stats.Description", "This command manages statistics. View your own, another player's statistics along with the server leaderboard.");

			config.addDefault("Commands.PermaRemove.Description", "This command will permanently remove the bending of the targeted <Player>. Once removed, a player may only receive bending again if this command is run on them again. This command is typically reserved for administrators.");
			config.addDefault("Commands.PermaRemove.InvalidPlayer", "That player could not be found.");
			config.addDefault("Commands.PermaRemove.Restored", "Your bending has been restored.");
			config.addDefault("Commands.PermaRemove.RestoredConfirm", "You have restored the bending of {target}.");
			config.addDefault("Commands.PermaRemove.Removed", "Your bending has been permanently removed.");
			config.addDefault("Commands.PermaRemove.RemovedConfirm", "You have removed the bending of {target}.");

			config.addDefault("Commands.Invincible.Description", "This command will make you immune to all bending damage. Once you use this command, you will stay invincible until you log off or use this command again.");
			config.addDefault("Commands.Invincible.ToggledOn", "You are now invincible to all bending damage and effects. Use this command again to disable this.");
			config.addDefault("Commands.Invincible.ToggledOff", "You are no longer invincible to all bending damage and effects.");

			config.addDefault("Commands.Help.Description", "This command provides information on how to use other commands in ProjectKorra.");
			config.addDefault("Commands.Help.Required", "Required");
			config.addDefault("Commands.Help.Optional", "Optional");
			config.addDefault("Commands.Help.ProperUsage", "Proper Usage: {command1} or {command2}");
			config.addDefault("Commands.Help.Elements.LearnMore", "Learn more at our website! ");
			config.addDefault("Commands.Help.InvalidTopic", "That isn't a valid help topic. Use /bending help for more information.");
			config.addDefault("Commands.Help.Usage", "Usage: ");
			config.addDefault("Commands.Help.EnableQuickBind", true);
			config.addDefault("Commands.Help.BindStart", "Bind: &7&l◀");
			config.addDefault("Commands.Help.SlotFormat", " {element_color}{slot} ");
			config.addDefault("Commands.Help.BindSeparator", "&7|");
			config.addDefault("Commands.Help.BindEnd", "&7&l▶");
			config.addDefault("Commands.Help.HoverBind", "Click to bind {ability} to slot {slot}!");

			config.addDefault("Commands.Display.Description", "This command will show you all of the elements you have bound if you do not specify an element. If you do specify an element (Air, Water, Earth, Fire, or Chi), it will show you all of the available abilities of that element installed on the server.");
			config.addDefault("Commands.Display.NoCombosAvailable", "There are no {element} combos available.");
			config.addDefault("Commands.Display.NoPassivesAvailable", "There are no {element} passives available.");
			config.addDefault("Commands.Display.NoAbilitiesAvailable", "There are no {element} abilities on this server.");
			config.addDefault("Commands.Display.NoCombosAccess", "You do not have access to any available {element} combos.");
			config.addDefault("Commands.Display.NoPassivesAccess", "You do not have access to any available {element} passives.");
			config.addDefault("Commands.Display.NoAbilitiesAccess", "You do not have access to any available {element} abilities.");
			config.addDefault("Commands.Display.NoElementsAccess", "You do not have access to any available elements.");
			config.addDefault("Commands.Display.InvalidArgument", "Not a valid argument.");
			config.addDefault("Commands.Display.PlayersOnly", "This command is only usable by players.");
			config.addDefault("Commands.Display.NoBinds", "You do not have any abilities bound.\nIf you would like to see a list of available abilities, please use the /bending display [Element] command. Use /bending help for more information.");
			config.addDefault("Commands.Display.Format", "{ability}");
			config.addDefault("Commands.Display.Separator", "\\n");
			config.addDefault("Commands.Display.HoverType", "Click to display {type}.");
			config.addDefault("Commands.Display.HoverAbility", "Click to view how to use {ability}.");
			config.addDefault("Commands.Display.SubHeader", "\\n");
			config.addDefault("Commands.Display.ComboPassiveHeader", "\\n");

			config.addDefault("Commands.Debug.Description", "Outputs information on the current ProjectKorra installation to /plugins/ProjectKorra/debug.txt");
			config.addDefault("Commands.Debug.SuccessfullyExported", "Debug File Created as debug.txt in the ProjectKorra plugin folder.\nPut contents on pastie.org and create a bug report on the ProjectKorra forum if you need to.");

			config.addDefault("Commands.Board.Description", "Toggle bending board visibility.");
			config.addDefault("Commands.Board.Disabled", "Bending board is disabled.");
			config.addDefault("Commands.Board.ToggledOn", "You have made your bending board visible again.");
			config.addDefault("Commands.Board.ToggledOff", "You have hidden your bending board.");

			config.addDefault("Commands.Copy.Description", "This command will allow the user to copy the binds of another player either for himself or assign them to <Player> if specified.");
			config.addDefault("Commands.Copy.PlayerNotFound", "Couldn't find player.");
			config.addDefault("Commands.Copy.SuccessfullyCopied", "Your binds have been set to match {target}'s!");
			config.addDefault("Commands.Copy.FailedToBindAll", "Some moves were not bound due to insufficient permissions.");
			config.addDefault("Commands.Copy.Other.SuccessfullyCopied", "{target1}'s binds have been set to match {target2}'s.");
			config.addDefault("Commands.Copy.CantEditBinds", "You can't edit your binds right now!");
			config.addDefault("Commands.Copy.Other.CantEditBinds", "You can't edit their binds right now!");

			config.addDefault("Commands.Clear.Description", "This command will clear the bound ability from the slot you specify (if you specify one). If you choose not to specify a slot, all of your abilities will be cleared.");
			config.addDefault("Commands.Clear.CantEditBinds", "You can't edit your binds right now!");
			config.addDefault("Commands.Clear.Cleared", "Your bound abilities have been cleared.");
			config.addDefault("Commands.Clear.WrongNumber", "The slot must be an integer between 1 and 9.");
			config.addDefault("Commands.Clear.ClearedSlot", "You have cleared slot #{slot}.");
			config.addDefault("Commands.Clear.AlreadyEmpty", "That slot is already empty.");

			config.addDefault("Commands.Choose.Description", "This command will allow the user to choose a single element for themselves or <Player> if specified. This command can only be used once per player unless they have permission to rechoose their element.");
			config.addDefault("Commands.Choose.InvalidElement", "That is not a valid element.");
			config.addDefault("Commands.Choose.PlayerNotFound", "Could not find player.");
			config.addDefault("Commands.Choose.OnCooldown", "You must wait %cooldown% before changing your element.");
			config.addDefault("Commands.Choose.SuccessfullyChosenCFW", "You are now a {element}.");
			config.addDefault("Commands.Choose.SuccessfullyChosenAE", "You are now an {element}.");
			config.addDefault("Commands.Choose.Other.SuccessfullyChosenCFW", "{target} is now a {element}.");
			config.addDefault("Commands.Choose.Other.SuccessfullyChosenAE", "{target} is now an {element}.");

			config.addDefault("Commands.Check.Description", "Checks if ProjectKorra is up to date.");
			config.addDefault("Commands.Check.NewVersionAvailable", "There's a new version of ProjectKorra available!");
			config.addDefault("Commands.Check.CurrentVersion", "Current Version: {version}");
			config.addDefault("Commands.Check.LatestVersion", "Latest Version: {version}");
			config.addDefault("Commands.Check.UpToDate", "You have the latest version of ProjectKorra.");

			config.addDefault("Commands.Bind.Description", "This command will bind an ability to the slot you specify (if you specify one), or the slot currently selected in your hotbar (If you do not specify a Slot #).");
			config.addDefault("Commands.Bind.AbilityDoesntExist", "{ability} is not a valid ability.");
			config.addDefault("Commands.Bind.WrongNumber", "Slot must be an integer between 1 and 9.");
			config.addDefault("Commands.Bind.ElementToggledOff", "You have that ability's element toggled off currently.");
			config.addDefault("Commands.Bind.SuccessfullyBound", "Succesfully bound {ability} to slot {slot}.");
			config.addDefault("Commands.Bind.NoElement", "You are not a {element}!");
			config.addDefault("Commands.Bind.NoElementAE", "You are not an {element}!");
			config.addDefault("Commands.Bind.NoSubElement", "You don't have access to {subelement}!");
			config.addDefault("Commands.Bind.Unbindable", "{ability} cannot be bound!");
			config.addDefault("Commands.Bind.CantEditBinds", "You can't edit your binds right now!");

			config.addDefault("Commands.Add.Description", "This command will allow the user to add an element to the targeted <Player>, or themselves if the target is not specified. This command is typically reserved for server administrators.");
			config.addDefault("Commands.Add.SuccessfullyAddedCFW", "You are now also a {element}.");
			config.addDefault("Commands.Add.SuccessfullyAddedAE", "You are now also an {element}.");
			config.addDefault("Commands.Add.SuccessfullyAddedAll", "You now also have: ");
			config.addDefault("Commands.Add.PlayerNotFound", "That player could not be found.");
			config.addDefault("Commands.Add.InvalidElement", "You must specify a valid element.");
			config.addDefault("Commands.Add.AlreadyHasElement", "You already have that element!");
			config.addDefault("Commands.Add.AlreadyHasSubElement", "You already have that subelement!");
			config.addDefault("Commands.Add.AlreadyHasAllElements", "You already have all elements!");
			config.addDefault("Commands.Add.Other.SuccessfullyAddedCFW", "{target} is now also a {element}.");
			config.addDefault("Commands.Add.Other.SuccessfullyAddedAE", "{target} is now also an {element}.");
			config.addDefault("Commands.Add.Other.SuccessfullyAddedAll", "{target} now also has: ");
			config.addDefault("Commands.Add.Other.AlreadyHasElement", "{target} already has that element!");
			config.addDefault("Commands.Add.Other.AlreadyHasSubElement", "{target} already has that subelement!");
			config.addDefault("Commands.Add.Other.AlreadyHasAllElements", "{target} already has all elements!");

			config.addDefault("Commands.Temp.Description.Main", "This command will allow the user to add/modify temporary elements to the targeted <Player>. This command is typically reserved for server administrators.");
			config.addDefault("Commands.Temp.Description.Add", "Adds a temporary element to the specified <Player>. Cannot give elements to players that already have them.");
			config.addDefault("Commands.Temp.Description.Extend", "Extends a temporary element for the specified <Player> by the specified amount of <Time>. If the player does not have the element, they will be given it.");
			config.addDefault("Commands.Temp.Description.Reduce", "Reduces the time until the specified temporary element expires. If the time exceeds the expiry, it will be removed.");
			config.addDefault("Commands.Temp.Description.Remove", "Removes temporary elements from the specified <Player>. Can specify \"all\" to remove all temp elements.");
			config.addDefault("Commands.Temp.PlayerNotFound", "That player could not be found.");
			config.addDefault("Commands.Temp.InvalidElement", "You must specify a valid element.");
			config.addDefault("Commands.Temp.InvalidTime", "{time} is not a valid time period. Valid times are things like 60s, 30m, 4h, 2d");
			config.addDefault("Commands.Temp.Expired", "Your {element}{bending} &ehas expired.");
			config.addDefault("Commands.Temp.ExpiredOffline", "Your {element}{bending} &eexpired while you were offline.");
			config.addDefault("Commands.Temp.ExpiredAvatar", "Your Avatar elements have expired.");
			config.addDefault("Commands.Temp.ExpiredAvatarOffline", "Your Avatar elements expired while you were offline.");
			config.addDefault("Commands.Temp.Add.Success", "You have been given {element}{bending} &efor &c{time}&e.");
			config.addDefault("Commands.Temp.Add.SuccessAvatar", "You are now the {element} for &c{time}&e.");
			config.addDefault("Commands.Temp.Add.SuccessOther", "{target} has been given {element}{bending} &efor &c{time}&e.");
			config.addDefault("Commands.Temp.Add.SuccessAvatarOther", "{target} is now an {element} for &c{time}&e.");
			config.addDefault("Commands.Temp.AlreadyHasElement", "{target} already has that element!");
			config.addDefault("Commands.Temp.AlreadyHasSubElement", "{target} already has that subelement!");
			config.addDefault("Commands.Temp.AlreadyHasTempElement", "{target} already has that element temporarily! Extend the time with /b temp extend <player> <element> <time>");
			config.addDefault("Commands.Temp.AlreadyHasTempSubElement", "{target} already has that subelement temporarily! Extend the time with /b temp extend <player> <element> <time>");
			config.addDefault("Commands.Temp.Extend.SuccessOther", "{target}'s {element}{bending} &ehas been extended and will now expire in &c{time}&e.");
			config.addDefault("Commands.Temp.Extend.Success", "Your {element} &ehas been extended and will now expire in &c{time}&e.");
			config.addDefault("Commands.Temp.Reduce.SuccessOther", "{target}'s {element} &eexpiry time has been reduced and will now expire in &c{time}&e.");
			config.addDefault("Commands.Temp.Reduce.Success", "Your {element}&e's expiry time has been reduced and will now expire in &c{time}&e.");
			config.addDefault("Commands.Temp.Reduce.SuccessOtherRemove", "{target}'s {element}{bending} &ehas been removed due to the reduce time exceeding the expiry.");
			config.addDefault("Commands.Temp.Reduce.SuccessRemove", "Your {element}{bending}&e's expiry time has been reduced and has now expired.");
			config.addDefault("Commands.Temp.Remove.SuccessOther", "{target}'s {element}{bending} &ehas been removed.");
			config.addDefault("Commands.Temp.Remove.Success", "Your {element}{bending} &ehas been removed.");
			config.addDefault("Commands.Temp.Remove.ElementNotFound", "{target} does not have {element} &ctemporarily.");
			config.addDefault("Commands.Temp.Remove.NoElements", "{target} does not have any temporary elements.");
			config.addDefault("Commands.Temp.Remove.SuccessAll", "Your temporary elements has been removed.");

			config.addDefault("DeathMessages.Enabled", true);
			config.addDefault("DeathMessages.Default", "{victim} was slain by {attacker}'s {ability}");

			config.addDefault("Abilities.Avatar.AvatarState.Description", "The signature ability of the Avatar, this is a toggle. Left click to activate to become " + "nearly unstoppable. While in the Avatar State, the user takes severely reduced damage from " + "all sources, regenerates health rapidly, and is granted extreme speed. Nearly all abilities " + "are incredibly amplified in this state. Additionally, AirShield and FireJet become toggle-able " + "abilities and last until you deactivate them or the Avatar State. Left click again with the Avatar " + "State selected to deactivate it.");

			config.addDefault("Commands.Help.Elements.Air", "Air is the element of freedom. Airbenders are natural pacifists and great explorers. There is nothing stopping them from scaling the tallest mountains and walls easily. They specialize in redirection, from blasting things away with gusts of winds, to forming a shield around them to prevent damage. Easy to get across flat terrains, such as oceans, there is practically no terrain off limits to Airbenders. They lack much raw damage output, but make up for it with with their ridiculous amounts of utility and speed.\nEnter /b display Air for a list of the available air abilities.");
			config.addDefault("Abilities.Air.AirBlast.Description", "AirBlast is the most fundamental bending technique of an airbender. It allows the bender to be extremely agile and possess great mobility, but also has many utility options, such as cooling lava, opening doors and flicking levers.");
			config.addDefault("Abilities.Air.AirBlast.Instructions", "\n" + "(Push) " + "Left click while aiming at an entity to push them back." + "\n" + "(Throw) " + "Tap sneak to select a location and left click in a direction to throw entities away from the selected location.");
			config.addDefault("Abilities.Air.AirBlast.DeathMessage", "{victim} was flung by {attacker}'s {ability}");
			config.addDefault("Abilities.Air.AirBlast.HorizontalVelocityDeath", "{victim} experienced a fatal collision by {attacker}'s {ability}");
			config.addDefault("Abilities.Air.AirBurst.Description", "AirBurst is one of the most powerful abilities in the airbender's arsenal. It allows the bender to create space between them and whoever is close to them. AirBurst is extremely useful when you're surrounded by mobs, of if you're low in health and need to escape. It can also be useful for confusing your target also.");
			config.addDefault("Abilities.Air.AirBurst.Instructions", "\n" + "(Sphere) Hold sneak until particles appear and then release shift to create air that expands outwards, pushing entities back. If you fall from great height while you are on this slot, the burst will automatically activate." + "\n" + "(Cone) While charging the move with shift, click to send the burst in a cone only going in one direction.");
			config.addDefault("Abilities.Air.AirBurst.DeathMessage", "{victim} was thrown down by {attacker}'s {ability}");
			config.addDefault("Abilities.Air.AirBurst.HorizontalVelocityDeath", "{victim} experienced a fatal collision by {attacker}'s {ability}");
			config.addDefault("Abilities.Air.AirScooter.Description", "AirScooter is a fast means of transportation. It can be used to escape from enemies or confuse them by using air scooter around them.");
			config.addDefault("Abilities.Air.AirScooter.Instructions", "Sprint, jump, and left click while in the air to activate air scooter. You will then move forward in the direction you're looking.");
			config.addDefault("Abilities.Air.Tornado.Description", "Tornado is one of the most powerful and advanced abilities that an Airbender knows. If the tornado meets a player or mob, it will push them around. Tornado can also be used to push back projectiles and used for mobility. Use a tornado directly under you to propel yourself upwards.");
			config.addDefault("Abilities.Air.Tornado.Instructions", "Hold sneak and a tornado will form gradually wherever you look.");
			config.addDefault("Abilities.Air.AirShield.Description", "AirShield is one of the most powerful defensive techniques in existence. This ability is mainly used when you are low health and need protection. It's also useful when you're surrounded by mobs.");
			config.addDefault("Abilities.Air.AirShield.Instructions", "Hold sneak and a shield of air will form around you, blocking projectiles and pushing entities back.");
			config.addDefault("Abilities.Air.AirSpout.Description", "This ability gives the airbender limited sustained levitation. It allows an airbender to gain a height advantage to escape from mobs, players or just to dodge from attacks. This ability is also useful for building as it allows you to reach great heights.");
			config.addDefault("Abilities.Air.AirSpout.Instructions", "Left click to activate a spout beneath you and hold spacebar to go higher. If you wish to go lower, simply hold sneak. To disable this ability, left click once again.");
			config.addDefault("Abilities.Air.AirSuction.Description", "AirSuction is a basic ability that allows you to manipulation an entity's movement. It can be used to bring someone back to you when they're running away, or even to get yourself to great heights.");
			config.addDefault("Abilities.Air.AirSuction.Instructions", "\n" + "(Pull) Left click while aiming at a target to pull them towards you." + "\n" + "(Manipulation) Sneak to select a point and then left click at a target or yourself to send you or your target to the point that you selected.");
			config.addDefault("Abilities.Air.AirSuction.HorizontalVelocityDeath", "{victim} experienced a fatal collision by {attacker}'s {ability}");
			config.addDefault("Abilities.Air.AirSwipe.Description", "AirSwipe is the most commonly used damage ability in an airbender's arsenal. An arc of air will flow from you towards the direction you're facing, cutting and pushing back anything in its path. This ability will extinguish fires, cool lava, and cut things like grass, mushrooms, and flowers.");
			config.addDefault("Abilities.Air.AirSwipe.Instructions", "\n" + "(Uncharged) Simply left click to send an air swipe out that will damage targets that it comes into contact with." + "\n" + "(Charged) Hold sneak until particles appear, then release sneak to send a more powerful air swipe out that damages entity's that it comes into contact with.");
			config.addDefault("Abilities.Air.AirSwipe.DeathMessage", "{victim} was struck by {attacker}'s {ability}");
			config.addDefault("Abilities.Air.Flight.Description", "Fly through the air as Zaheer and Guru Laghima did! This multiability allows for three modes of flight: soaring, gliding, and levitating. You can also right-click another player while flying to have them become your passenger! When flying at fast speeds, flying past nearby enemies will damage them for half your speed and knock them in the direction you're heading!");
			config.addDefault("Abilities.Air.Flight.Instructions", "\n- (To start flying, jump and left-click)\n- (Soar) Left-Click to change flying speeds.\n- (Glide) Normal minecraft gliding. Slowing down or speeding up in this mode will affect the Soar speed.\n- (Levitate) Basically minecraft flying, allowing players to fly around for building purposes or a more controlled 'hovering'.\n- (Ending) Being in this mode sets any gliding and flight back the the state they were before using the ability.");
			config.addDefault("Abilities.Air.Suffocate.Description", "This ability is one of the most dangerous abilities an Airbender possesses. Although it is difficult to perform, it's extremely deadly once the ability starts, making it difficult for enemies to escape.");
			config.addDefault("Abilities.Air.Suffocate.Instructions", "Hold sneak while looking at a target to begin suffocating them. If the target goes out of range, you get damaged, or you release sneak, the ability will cancel.");
			config.addDefault("Abilities.Air.Suffocate.DeathMessage", "{victim} was asphyxiated by {attacker}'s {ability}");
			config.addDefault("Abilities.Air.Combo.Twister.Description", "Create a cyclone of air that travels along the ground grabbing nearby entities.");
			config.addDefault("Abilities.Air.Combo.Twister.Instructions", "AirShield (Tap Shift) > Tornado (Hold Shift) > AirBlast (Left Click)");
			config.addDefault("Abilities.Air.Combo.AirStream.Description", "Control a large stream of air that grabs onto enemies allowing you to direct them temporarily.");
			config.addDefault("Abilities.Air.Combo.AirStream.Instructions", "AirShield (Hold Shift) > AirSuction (Left Click) > AirBlast (Left Click)");
			config.addDefault("Abilities.Air.Combo.AirSweep.Description", "Sweep the air in front of you hitting multiple enemies, causing moderate damage and a large knockback. The radius and direction of AirSweep is controlled by moving your mouse in a sweeping motion. For example, if you want to AirSweep upward, then move your mouse upward right after you left click AirBurst");
			config.addDefault("Abilities.Air.Combo.AirSweep.DeathMessage", "{victim} was swept away by {attacker}'s {ability}");
			config.addDefault("Abilities.Air.Combo.AirSweep.Instructions", "AirSwipe (Left Click) > AirSwipe (Left Click) > AirBurst (Hold Shift) > AirBurst (Left Click)");
			config.addDefault("Abilities.Air.Passive.AirAgility.Description", "AirAgility is a passive ability which enables airbenders to run faster and jump higher.");
			config.addDefault("Abilities.Air.Passive.AirSaturation.Description", "AirSaturation is a passive ability which causes airbenders' hunger to deplete at a slower rate.");
			config.addDefault("Abilities.Air.Passive.GracefulDescent.Description", "GracefulDescent is a passive ability which allows airbenders to make a gentle landing, negating all fall damage on any surface.");

			config.addDefault("Commands.Help.Elements.Water", "Water is the element of change. Waterbending focuses on using your opponents own force against them. Using redirection and various dodging tactics, you can be made practically untouchable by an opponent. Waterbending provides agility, along with strong offensive skills while in or near water.\nEnter /b display Water for a list of the available water abilities.");
			config.addDefault("Abilities.Water.Bloodbending.Description", "Bloodbending is one of the most unique bending abilities that existed and it has immense power, which is why it was made illegal in the Avatar universe. People who are capable of bloodbending are immune to your technique, and you are immune to theirs.");
			config.addDefault("Abilities.Water.Bloodbending.Instructions", "\n" + "(Control) Hold sneak while looking at an entity to bloodbend them. You will then be controlling the entity, making them move wherever you look." + "\n" + "(Throw) While bloodbending an entity, left click to throw that entity in the direction you're looking.");
			config.addDefault("Abilities.Water.Bloodbending.DeathMessage", "{victim} was destroyed by {attacker}'s {ability}");
			config.addDefault("Abilities.Water.Bloodbending.HorizontalVelocityDeath", "{victim} experienced a fatal collision from {attacker}'s {ability}");
			config.addDefault("Abilities.Water.Bloodbending.ActionBarMessage", "* Bloodbent *");
			config.addDefault("Abilities.Water.HealingWaters.Description", "HealingWaters is an advanced waterbender skill that allows the player to heal themselves or others from the damage they've taken. If healing another player, you must continue to look at them to channel the ability.");
			config.addDefault("Abilities.Water.HealingWaters.Instructions", "Hold sneak to begin healing yourself or right click while sneaking to begin healing another player. You or the player must be in water and damaged for this ability to work, or you need to have water bottles in your inventory.");
			config.addDefault("Abilities.Water.IceBlast.Description", "IceBlast is a powerful ability that deals damage to entities it comes into contact with. Because IceBlast's travel time is pretty quick, it's incredibly useful for finishing off low health targets.");
			config.addDefault("Abilities.Water.IceBlast.Instructions", "Tap sneak while looking at an ice block and then click in a direction to send an ice blast in that direction.");
			config.addDefault("Abilities.Water.IceBlast.DeathMessage", "{victim} was shattered by {attacker}'s {ability}");
			config.addDefault("Abilities.Water.IceSpike.Description", "This ability offers a powerful ice utility for Waterbenders. It can be used to fire an ice blast or raise an ice spike. If the ice blast or ice spike comes into contact with another entity, it will give them slowness and deal some damage to them..");
			config.addDefault("Abilities.Water.IceSpike.Instructions", "\n" + "(Blast) Tap sneak on a water source and then left click in a direction to fire an ice blast in a direction. Additionally, you can left click to manipulate the ice blast while it's in the air to change the direction of the blast." + "\n" + "(Spike) While in range of ice, tap sneak to raise ice pillars from the ice. If a player is caught in these ice pillars they will be propelled into the air. You cannot be looking at ice or water or this feature will not activate. Alternatively, you can left click an ice block to raise a single pilar of ice.");
			config.addDefault("Abilities.Water.IceSpike.DeathMessage", "{victim} was impaled by {attacker}'s {ability}");
//			config.addDefault("Abilities.Water.PlantTether.Description", "PlantTether is unique ability that creates a vine from some plant source to either constrict and draw in an enemy, or pull the user.");
//			config.addDefault("Abilities.Water.PlantTether.Instructions", "Tap sneak while looking at a plant source. Then tap sneak again at the source to pull yourself towards it. Or, tap sneak at a target entity to have them pulled towards the plant source.");
//			config.addDefault("Abilities.Water.PlantTether.DeathMessage", "{victim} was constricted by {attacker}'s {ability}");
			config.addDefault("Abilities.Water.OctopusForm.Description", "OctopusForm is one of the most advanced abilities in a waterbender's arsenal. It has the possibility of doing high damage to anyone it comes into contact with.");
			config.addDefault("Abilities.Water.OctopusForm.Instructions", "Left click a water source and then hold sneak to form a set of water tentacles. This ability will channel as long as you are holding sneak. Additionally, if you left click this ability will whip targets you're facing dealing damage and knockback, if they're in range.");
			config.addDefault("Abilities.Water.OctopusForm.DeathMessage", "{victim} was slapped to death by {attacker}'s {ability}");
			config.addDefault("Abilities.Water.PhaseChange.Description", "PhaseChange is one of the most useful utility moves that a waterbender possess. This ability is better used when fighting, allowing you to create a platform on water that you can fight on and being territorial by manipulating your environment. It's also useful for travelling across seas.");
			config.addDefault("Abilities.Water.PhaseChange.Instructions", "\n" + "(Melt) To melt ice, hold sneak while looking at an ice block." + "\n" + "(Freeze) To freeze water and turn it into ice, simply left click at water. This ice will stay so long as you are in range, otherwise it will revert back to water. This only freezes the top layer of ice.");
			config.addDefault("Abilities.Water.Surge.Description", "Surge offers great utility and is one of the most important defence abilities for waterbender's. It can be used to push entities back, used to push yourself in a direction, trap entities and protect yourself with a shield.");
			config.addDefault("Abilities.Water.Surge.Instructions", "\n" + "(Shield) Left click on a water source and then hold sneak while looking up to create a water shield that will move wherever you look. Additionally, you can left click to turn this shield into ice. If you let go of sneak at any point, this ability will cancel." + "\n" + "(Surge) Tap sneak at a water source and click in a direction to fire a surge of water that will knock entities back. Additionally, if you tap sneak again before the surge reaches an entity, when it hits them it will encase them in ice.");
			config.addDefault("Abilities.Water.Torrent.Description", "Torrent is one of the strongest moves in a waterbender's arsenal. It has the potential to do immense damage and to be comboed with other abilities to perform a deal a large damage burst. Torrent is fundamental for waterbender's. ");
			config.addDefault("Abilities.Water.Torrent.Instructions", "\n" + "(Torrent) Left click at a water source and hold sneak to form the torrent. Then, left click and the torrent will shoot out, moving in the direction you're looking. If the torrent hits an entity, it can drag them and deal damage. Additionally, if you left click before the torrent hits a surface or entity it will freeze on impact." + "\n" + "(Wave) Left click a water source and hold sneak to form a torrent around you. Then, release sneak to send a wave of water expanding outwards every direction that will push entities back.");
			config.addDefault("Abilities.Water.Torrent.DeathMessage", "{victim} was washed away by {attacker}'s {ability}");
			config.addDefault("Abilities.Water.WaterArms.Description", "One of the most diverse moves in a Waterbender's arsenal, this move creates tendrils " + "of water from the players arms to emulate their actual arms. It has the potential to do a variety of things that can either do mass amounts of damage, or used for mobility.");
			config.addDefault("Abilities.Water.WaterArms.Instructions", "To activate this ability, tap sneak at a water source. Additionally, to de-activate this ability, hold sneak and left click." + "\n" + "(Pull) Left click at a target and your arms will expand outwards, pulling entities towards you if they're in range." + "\n" + "(Punch) Left click and one arm will expand outwards, punching anyone it hits and dealing damage." + "\n" + "(Grapple) Left click to send your arms forward, pulling you to whatever surface they land on." + "\n" + "(Grab) Left click to grab an entity that's in range. They will then be controlled and moved in whatever direction you look. Additionally, if you left click again you can throw the target that you're controlling." + "\n" + "(Freeze) Left click to rapidly fire ice blasts at a target, damaging the target and giving them slowness." + "\n" + "(Spear) Left click to send an ice spear out, damaging and freezing whoever it hits in ice blocks.");
			config.addDefault("Abilities.Water.WaterArms.SneakMessage", "Active Ability:");
			config.addDefault("Abilities.Water.WaterArms.Punch.DeathMessage", "{victim} was too slow for {attacker}'s {ability}");
			config.addDefault("Abilities.Water.WaterArms.Freeze.DeathMessage", "{victim} was frozen by {attacker}'s {ability}");
			config.addDefault("Abilities.Water.WaterArms.Spear.DeathMessage", "{victim} was speared to death by {attacker}'s {ability}");
			config.addDefault("Abilities.Water.WaterBubble.Description", "WaterBubble is a basic waterbending ability that allows the bender to create air pockets under water. This is incredibly useful for building under water.");
			config.addDefault("Abilities.Water.WaterBubble.Instructions", "Hold sneak when in range of water to push the water back and create a water bubble. Alternatively, you can click to create a bubble for a short amount of time.");
			config.addDefault("Abilities.Water.WaterManipulation.Description", "WaterManipulation is a fundamental ability for waterbenders. Although it is a basic move, it allows for fast damage due to its rapid fire nature, which is incredibly useful when wanting to finish off low health targets.");
			config.addDefault("Abilities.Water.WaterManipulation.Instructions", "Tap sneak while looking at a water source and left click to send a water manipulation to the point that you clicked. Additionally, you can left click again to change the direction of this move. This includes other players' WaterManipulations.");
			config.addDefault("Abilities.Water.WaterManipulation.DeathMessage", "{victim} was drowned by {attacker}'s {ability}");
			config.addDefault("Abilities.Water.WaterSpout.Description", "This ability provides a Waterbender with a means of transportation. It's the most useful mobility move that a waterbender possesses and is great for chasing down targets or escaping.");
			config.addDefault("Abilities.Water.WaterSpout.Instructions", "\n" + "(Spout) Left click to activate a spout beneath you and hold spacebar to go higher. If you wish to go lower, simply hold sneak. To disable this ability, left click once again." + "\n" + "(SpoutHop) While WaterSpout is active, hold sneak and left-click to jump forward!" + "\n" + "(Wave) Left click a water source and hold sneak until water has formed around you. Then, release sneak to ride a water wave that transports you in the direction you're looking. To cancel this water wave, left click with WaterSpout.");
			config.addDefault("Abilities.Water.Combo.IceBullet.Description", "Using a large cavern of ice, you can punch ice shards at your opponent causing moderate damage. To rapid fire, you must alternate between Left clicking and right clicking with IceBlast.");
			config.addDefault("Abilities.Water.Combo.IceBullet.DeathMessage", "{victim}'s heart was frozen by {attacker}'s {ability}");
			config.addDefault("Abilities.Water.Combo.IceBullet.Instructions", "WaterBubble (Tap Shift) > IceBlast (Hold Shift) > Wait for ice to Form > Then alternate between Left and Right click with IceBlast");
			config.addDefault("Abilities.Water.Combo.IceWave.Description", "PhaseChange your WaterWave into an IceWave that freezes and damages enemies.");
			config.addDefault("Abilities.Water.Combo.IceWave.DeathMessage", "{victim} was frozen solid by {attacker}'s {ability}");
			config.addDefault("Abilities.Water.Combo.IceWave.Instructions", "Create a WaterSpout Wave > PhaseChange (Left Click)");
			config.addDefault("Abilities.Water.Passive.FastSwim.Description", "FastSwim is a passive ability for waterbenders allowing them to travel quickly through the water. Simple hold shift while underwater to propel yourself forward.");
			config.addDefault("Abilities.Water.Passive.HydroSink.Description", "Hydrosink is a passive ability for waterbenders enabling them to softly land on any waterbendable surface, cancelling all damage.");

			config.addDefault("Commands.Help.Elements.Earth", "Earth is the element of substance. Earthbenders share many of the same fundamental techniques as Waterbenders, but their domain is quite different and more readily accessible. Earthbenders dominate the ground and subterranean, having abilities to pull columns of rock straight up from the earth or drill their way through the mountain. They can also launch themselves through the air using pillars of rock, and will not hurt themselves assuming they land on something they can bend. The more skilled Earthbenders can even bend metal, sand, and lava.\nEnter /b display Earth for a list of the available earth abilities.");
			config.addDefault("Abilities.Earth.Catapult.Description", "Catapult is an advanced earthbending ability that allows you to forcefully push yourself using earth, reaching great heights. This technique is best used when travelling, but it can also be used to quickly escape a battle.");
			config.addDefault("Abilities.Earth.Catapult.Instructions", "Hold sneak until you see particles and hear a sound and then release to be propelled in the direction you're looking. Additionally, you can left-click to be propelled with less power.");
			config.addDefault("Abilities.Earth.Collapse.Description", "This ability is a basic earthbending ability that allows the earthbender great utility. It allows them to control earth blocks by compressing earth. Players and mobs can be trapped and killed if earth is collapsed and they're stuck inside it, meaning this move is deadly when in cave systems.");
			config.addDefault("Abilities.Earth.Collapse.Instructions", "Left click an earthbendable block. If there's space under that block, it will be collapsed. Alternatively, you can tap sneak to collapse multiple blocks at a time.");
			config.addDefault("Abilities.Earth.Collapse.DeathMessage", "{victim} was suffocated by {attacker}'s {ability}");
			config.addDefault("Abilities.Earth.EarthArmor.Description", "This ability encases the Earthbender in armor, giving them protection. It is a fundamental earthbending technique that's used to survive longer in battles.");
			config.addDefault("Abilities.Earth.EarthArmor.Instructions", "Tap sneak while looking at an earthbendable block to bring those blocks towards you, forming earth armor. This ability will give you extra hearts and will be removed once those extra hearts are gone. You can disable this ability by holding sneak and left clicking with EarthArmor.");
			config.addDefault("Abilities.Earth.EarthBlast.Description", "EarthBlast is a basic yet fundamental earthbending ability. It allows you to deal rapid fire damage to your target to finish low health targets off or deal burst damage to them. Although it can be used at long range, it's potential is greater in close ranged combat.");
			config.addDefault("Abilities.Earth.EarthBlast.Instructions", "Tap sneak at an earthbendable block and then left click in a direction to send an earthblast. Additionally, you can left click again to change the direction of the earthblast. You can also redirect other earthbender's earth blast by left clicking. If the earth blast hits an entity it will deal damage and knockback.");
			config.addDefault("Abilities.Earth.EarthBlast.DeathMessage", "{victim} was shattered by {attacker}'s {ability}");
			config.addDefault("Abilities.Earth.EarthGrab.Description", "EarthGrab is one of the best defence abilities in an earthbender's arsenal. It allows you to trap someone who is running away so that you can catch up to someone. It is also of great utility use to an earthbender. It can be used to drag items, arrows, and crops that are on earthbendable blocks towards you, saving you the time of running to get them.");
			config.addDefault("Abilities.Earth.EarthGrab.Instructions", "\n" + "(Grab) To grab an entity, left click in the direction of the target. Your power will be sent through the earth, and then it will reach up and root them in their spot upon contact. The ability can be manually be disabled by sneaking or clicking again on the EarthGrab slot." + "\n" + "(Drag) To drag items towards you, sneak" + "\n(Escaping) To escape, the trap must be destroyed or the user damaged. The trap can be destroyed by damage or the trapped entity right-clicking it a certain number of times. Additionally, forcefully moving the entity with another earth ability destroys the trap.");
			config.addDefault("Abilities.Earth.EarthTunnel.Description", "Earth Tunnel is a completely utility ability for earthbenders. It allows you to dig a hole that lowers players down while you continue the ability, create fast escape routes or just great for making your own cave systems.");
			config.addDefault("Abilities.Earth.EarthTunnel.Instructions", "Hold sneak while looking at an earthbendable block to tunnel the blocks away. If you release sneak or look at a block that isn't earthbendable, the ability will cancel.");
			config.addDefault("Abilities.Earth.Extraction.Description", "This ability allows metalbenders to extract the minerals from ore blocks. This ability is extremely useful for gathering materials as it has a chance to extract double or triple the ores.");
			config.addDefault("Abilities.Earth.Extraction.Instructions", "Tap sneak while looking at an earthbendable ore to extract the ore.");
			config.addDefault("Abilities.Earth.LavaFlow.Description", "LavaFlow is an extremely advanced, and dangerous ability. It allows the earthbender to create pools of lava around them, or to solidify existing lava. This ability can be deadly when comboed with EarthGrab.");
			config.addDefault("Abilities.Earth.LavaFlow.Instructions", "\n" + "(Flow) Hold sneak and lava will begin expanding outwards. Once the lava has stopped expanding, you can release sneak. Additionally, if you tap sneak the lava you created will revert back to the earthbendable block." + "\n" + "(Lava Pool) Left click to slowly transform earthbendable blocks into a pool of lava." + "\n" + "(Solidify) Left click on lava to solidify it, turning it to stone.");
			config.addDefault("Abilities.Earth.LavaFlow.DeathMessage", "{victim} was caught by {attacker}'s {ability}");
			config.addDefault("Abilities.Earth.EarthSmash.Description", "EarthSmash is an advanced earthbending technique that has lots of utility. It can be comboed with abilities such as Shockwave, but also be used for mobility and to produce high damage. EarthSmash is great for escaping when at low health.");
			config.addDefault("Abilities.Earth.EarthSmash.Instructions", "\n" + "(Smash) Hold sneak until particles appear, then release sneak while looking at an earthbendable block which will raise an earth boulder. Then, hold sneak while looking at this boulder to control it. Left click to send the bounder in the direction you're facing, damaging entities and knocking them back." + "\n" + "(Ride) After you have created an earth boulder, hold sneak and right click on the boulder to ride it. You will now ride the boulder in whatever direction you look. Additionally, you can ride the boulder by going on top of it and holding sneak. If you come into contact with an entity while riding the boulder, it will drag them along with you. If you left go of sneak, the ability will cancel.");
			config.addDefault("Abilities.Earth.EarthSmash.DeathMessage", "{victim} was crushed by {attacker}'s {ability}");
			config.addDefault("Abilities.Earth.MetalClips.Description", "MetalClips is an advanced metalbending ability that allows you to take control of a fight. It gives the metalbender the ability to control an entity, create space between them and a player and even added utility.");
			config.addDefault("Abilities.Earth.MetalClips.Instructions", "\n" + "(Clips) This ability requires iron ingots in your inventory. Left click to throw an ingot at an entity, dealing damage to them. This ingot will form into armor, wrapping itself around the entity. Once enough armor pieces are around the entity, you can then control them. To control them, hold sneak while looking at them and then they will be moved in the direction you look. Additionally, you can release sneak to throw them in the direction you're looking." + "\n" + "(Magnet) Hold sneak with this ability to pull iron ingots towards you.");
			config.addDefault("Abilities.Earth.MetalClips.DeathMessage", "{victim} was too slow for {attacker}'s {ability}");
			config.addDefault("Abilities.Earth.MetalClips.ActionBarMessage", "* MetalClipped *");
			config.addDefault("Abilities.Earth.RaiseEarth.Description", "RaiseEarth is a basic yet useful utility move. It has the potential to allow the earthbender to create great escape routes by raising earth underneath them to propel themselves upwards. It also offers synergy with other moves, such as shockwave. RaiseEarth is often used to block incoming abilities.");
			config.addDefault("Abilities.Earth.RaiseEarth.Instructions", "\n" + "(Pillar) To raise a pillar of earth, left click on an earthbendable block." + "\n" + "(Wall) To raise a wall of earth, tap sneak on an earthbendable block.");
			config.addDefault("Abilities.Earth.Shockwave.Description", "Shockwave is one of the most powerful earthbending abilities. It allows the earthbender to deal mass damage to everyone around them and knock them back. It's extremely useful when fighting more than one target or if you're surrounded by mobs.");
			config.addDefault("Abilities.Earth.Shockwave.Instructions", "Hold sneak until you see particles and then release sneak to send a wave of earth outwards, damaging and knocking entities back that it collides with. Additionally, instead of releasing sneak you can send a cone of earth forwards by left clicking. If you are on the Shockwave slot and you fall from a great height, your Shockwave will automatically activate.");
			config.addDefault("Abilities.Earth.Shockwave.DeathMessage", "{victim} was blown away by {attacker}'s {ability}");
			config.addDefault("Abilities.Earth.Tremorsense.Description", "This is a pure utility ability for earthbenders. If you are in an area of low-light and are standing on top of an earthbendable block, this ability will automatically turn that block into glowstone, visible *only by you*. If you lose contact with a bendable block, the light will go out as you have lost contact with the earth and cannot 'see' until you can touch earth again. Additionally, if you click with this ability selected, smoke will appear above nearby earth with pockets of air beneath them.");
			config.addDefault("Abilities.Earth.Tremorsense.Instructions", "Simply left click while on an earthbendable block.");
			config.addDefault("Abilities.Earth.Combo.EarthDome.Description", "EarthDome allows earthbenders to surround themselves or another entity in earth, temporarily preventing anything from entering or escaping the dome.");
			config.addDefault("Abilities.Earth.Combo.EarthDome.Instructions", "\n" + "(Self) RaiseEarth (Right click) > Shockwave (Right click)" + "\n" + "(Projection) RaiseEarth (Right click) > Shockwave (Left click)");
			config.addDefault("Abilities.Earth.Combo.EarthPillars.Description", "Send players and entities flying into the air and possibly stunning them by raising pillars of earth under their feet, dealing damage initially as well. This combo can also be used by falling from high off the ground and landing while on the Catapult ability");
			config.addDefault("Abilities.Earth.Combo.EarthPillars.Instructions", "Shockwave (Tap sneak) > Shockwave (Hold sneak) > Catapult (Release sneak)");
			config.addDefault("Abilities.Earth.Passive.DensityShift.Description", "DensityShift is a passive ability which allows earthbenders to make a firm landing negating all fall damage on any earthbendable surface.");
			config.addDefault("Abilities.Earth.Passive.FerroControl.Description", "FerroControl is a passive ability which allows metalbenders to simply open and close iron doors by sneaking.");

			config.addDefault("Commands.Help.Elements.Fire", "Fire is the element of power. Firebenders focus on destruction and incineration. Their abilities are pretty straight forward: set things on fire. They do have a bit of utility however, being able to make themselves un-ignitable, extinguish large areas, cook food in their hands, extinguish large areas, small bursts of flight, and then comes the abilities to shoot fire from your hands.\nEnter /b display Fire for a list of the available fire abilities.");
			config.addDefault("Abilities.Fire.Blaze.Description", "Blaze is a basic firebending technique that can be extremely deadly if used right. It's useful to stop people from chasing you or to create space between you and other players..");
			config.addDefault("Abilities.Fire.Blaze.Instructions", "Left click to send an arc of fire in the direction you're facing that will burn entities in its path. Additionally, you can tap sneak to send a blaze all around you.");
			config.addDefault("Abilities.Fire.Blaze.DeathMessage", "{victim} was incinerated by {attacker}'s {ability}");
			config.addDefault("Abilities.Fire.Combustion.Description", "Combustion is a special firebending technique that's extremely deadly. It allows you to create a powerful blast to deal immense damage to players at long range.");
			config.addDefault("Abilities.Fire.Combustion.Instructions", "Tap sneak to send a combustion out in the direction you're looking. It will explode on impact, or you can left click to manually explode it. This deals damage to players who are in radius of the blast.");
			config.addDefault("Abilities.Fire.Combustion.DeathMessage", "{victim} was shot down by {attacker}'s {ability}");
			config.addDefault("Abilities.Fire.FireBlast.Description", "FireBlast is the most fundamental bending technique of a firebender. It allows the firebender to create mass amounts of fire blasts to constantly keep damaging an entity. It's great for rapid fire successions to deal immense damage.");
			config.addDefault("Abilities.Fire.FireBlast.Instructions", "\n" + "(Ball) Left click to send out a ball of fire that will deal damage and knockback entities it hits. Additionally, this ability can refuel furnace power if the blast connects with a furnace." + "\n" + "(Blast) Hold sneak until you see particles and then release sneak to send out a powerful fire blast outwards. This deals damage and knocks back anyone it hits, while exploding on impact.");
			config.addDefault("Abilities.Fire.FireBlast.DeathMessage", "{victim} was burnt by {attacker}'s {ability}");
			config.addDefault("Abilities.Fire.FireBurst.Description", "FireBurst is a very powerful firebending ability. " + "FireBurst is an advanced firebending technique that has a large range and the potential to deal immense damage. It's incredibly useful when surrounded by lots of mobs, to damage them all at once.");
			config.addDefault("Abilities.Fire.FireBurst.Instructions", "Hold sneak until you see particles and then release sneak to send out a sphere of fire expanding outwards, damaging anything it hits. Additionally, you can left click instead of releasing sneak to send the fire burst into one direction only.");
			config.addDefault("Abilities.Fire.FireBurst.DeathMessage", "{victim} was blown apart by {attacker}'s {ability}");
			config.addDefault("Abilities.Fire.FireJet.Description", "FireJet is a fundamental utility move for firebenders. It allows the firebender to blast fire behind them to propel them forward, which can prevent them from taking fall damage or to escape from deadly situations.");
			config.addDefault("Abilities.Fire.FireJet.Instructions", "Left click to propel yourself in the direction you're looking. Additionally, left click while flying to cancel the jet.");
			config.addDefault("Abilities.Fire.FireShield.Description", "FireShield is a basic defensive ability that allows a firebender to block projectiles or other bending abilities. It's useful while fighting off skeletons, or while trying to block bending abilities at low health.");
			config.addDefault("Abilities.Fire.FireShield.Instructions", "Hold sneak to create a fire shield around you that will block projectiles and other bending abilities. Additionally, left click to create a temporary fire shield. If entities step inside this fire shield, they will be ignited.");
			config.addDefault("Abilities.Fire.FireShield.DeathMessage", "{victim} scorched themselves on {attacker}'s {ability}");
			config.addDefault("Abilities.Fire.FireManipulation.Description", "FireManipulation is an extremely advanced and unique Firebending technique that allows the bender to create fire and manipulate it to block incoming attacks. You can also manipulate the fire you create to be used as an offence ability.");
			config.addDefault("Abilities.Fire.FireManipulation.Instructions", "Stream: Hold sneak and move your cursor around to create a fire where you look, blocking incoming attacks. Once you've created enough fire, left click to send the fire stream outwards, damaging anything it comes into contact with.");
			config.addDefault("Abilities.Fire.FireManipulation.DeathMessage", "{victim} scorched themselves on {attacker}'s {ability}");
			config.addDefault("Abilities.Fire.HeatControl.Description", "HeatControl is a fundamental firebending technique that allows the firebender to control and manipulate heat. This ability is extremely useful for ensuring that you're protected from your own fire and fire from that of other firebenders. It's also offers utility by melting ice or cooking food.");
			config.addDefault("Abilities.Fire.HeatControl.Instructions", "\n" + "(Melt) To melt ice, simply left click while looking at ice." + "\n" + "(Solidify) To solidify lava, hold sneak while looking at lava while standing still and it will start to solidify the lava pool you're looking at." + "\n" + "(Extinguish) To extinguish nearby fire or yourself, simply tap sneak." + "\n" + "(Cook) To cook food, place the raw food on your HeatControl slot and hold sneak. The food will then begin to cook.");
			config.addDefault("Abilities.Fire.Illumination.Description", "Illumination is a basic firebending technique that allows firebenders to manipulate their fire to create a light source. This ability will automatically activate when you're in low light.");
			config.addDefault("Abilities.Fire.Illumination.Instructions", "Left click to enable. Additionally, left click to disable.");
			config.addDefault("Abilities.Fire.Lightning.Description", "Lightning is an advanced firebending technique. It allows you to create lightning and manipulate it towards a target to deal immense damage.");
			config.addDefault("Abilities.Fire.Lightning.Instructions", "\n" + "(Lightning) Hold sneak to create lightning until particles appear, then release sneak to send lightning in the direction you're looking. This deals damage to entities that it hits and has a chance to stun them for a short duration." + "\n" + "(Redirection) When someone has fired a lightning strike at you, you can hold sneak to absorb this lightning and then release sneak to fire it back.");
			config.addDefault("Abilities.Fire.Lightning.DeathMessage", "{victim} was electrocuted by {attacker}'s {ability}");
			config.addDefault("Abilities.Fire.WallOfFire.Description", "WallOfFire is an advanced firebending technique that can be used aggressively or defensively. It's incredibly useful when trying to block off opponents from chasing you or to back them into corners.");
			config.addDefault("Abilities.Fire.WallOfFire.Instructions", "Left click to create a fire wall at the location you clicked. This fire wall will damage entities that run into it and deal knockback.");
			config.addDefault("Abilities.Fire.WallOfFire.DeathMessage", "{victim} ran into {attacker}'s {ability}");
			config.addDefault("Abilities.Fire.Combo.FireKick.Description", "A short ranged arc of fire launches from the player's feet dealing moderate damage to enemies.");
			config.addDefault("Abilities.Fire.Combo.FireKick.DeathMessage", "{victim} was kicked to the floor, in flames, from {attacker}'s {ability}");
			config.addDefault("Abilities.Fire.Combo.FireKick.Instructions", "FireBlast > FireBlast > (Hold sneak) > FireBlast");
			config.addDefault("Abilities.Fire.Combo.FireSpin.Description", "A circular array of fire that causes damage and massive knockback to nearby enemies.");
			config.addDefault("Abilities.Fire.Combo.FireSpin.DeathMessage", "{victim} was caught in {attacker}'s {ability} inferno");
			config.addDefault("Abilities.Fire.Combo.FireSpin.Instructions", "FireBlast > FireBlast > FireShield (Left Click) > FireShield (Tap Shift)");
			config.addDefault("Abilities.Fire.Combo.JetBlaze.Description", "Damages and burns all enemies in the proximity of your FireJet.");
			config.addDefault("Abilities.Fire.Combo.JetBlaze.DeathMessage", "{victim} was lit ablaze by {attacker}'s {ability}");
			config.addDefault("Abilities.Fire.Combo.JetBlaze.Instructions", "FireJet (Tap Shift) > FireJet (Tap Shift) > Blaze (Tap Shift) > FireJet");
			config.addDefault("Abilities.Fire.Combo.JetBlast.Description", "Create an explosive blast that propels your FireJet at higher speeds.");
			config.addDefault("Abilities.Fire.Combo.JetBlast.Instructions", "FireJet (Tap Shift) > FireJet (Tap Shift) > FireShield (Tap Shift) > FireJet");
			config.addDefault("Abilities.Fire.Combo.FireWheel.Description", "A high-speed wheel of fire that travels along the ground for long distances dealing high damage.");
			config.addDefault("Abilities.Fire.Combo.FireWheel.DeathMessage", "{victim} was incinerated by {attacker}'s {ability}");
			config.addDefault("Abilities.Fire.Combo.FireWheel.Instructions", "FireShield (Hold Shift) > Right Click a block in front of you twice > Switch to Blaze > Release Shift");

			config.addDefault("Commands.Help.Elements.Chi", "Chiblockers focus on bare handed combat, utilizing their agility and speed to stop any bender right in their path. Although they lack the ability to bend any of the other elements, they are great in combat, and a serious threat to any bender. Chiblocking was first shown to be used by Ty Lee in Avatar: The Last Airbender, then later by members of the Equalists in The Legend of Korra.\nEnter /b display Chi for a list of the available chi abilities.");
			config.addDefault("Abilities.Chi.AcrobatStance.Description", "AcrobatStance gives a Chiblocker a higher probability of blocking a Bender's Chi while granting them a Speed and Jump Boost. It also increases the rate at which the hunger bar depletes.");
			config.addDefault("Abilities.Chi.AcrobatStance.Instructions", "To use, simply left click to activate this stance. Left click once more to deactivate it.");
			config.addDefault("Abilities.Chi.HighJump.Description", "HighJump gives the Chiblocker the ability to leap into the air. This ability is used for mobility, and is often used to dodge incoming attacks.");
			config.addDefault("Abilities.Chi.HighJump.Instructions", "To use, simply left click while standing on the ground.");
			config.addDefault("Abilities.Chi.Smokescreen.Description", "Smokescreen, if used correctly, can serve as a defensive and offensive ability for Chiblockers. When used, a smoke bomb is fired which will blind anyone within a small radius of the explosion, allowing you to either get away, or move in for the kill.");
			config.addDefault("Abilities.Chi.Smokescreen.Instructions", "Left click and a smoke bomb will be fired in the direction you're looking.");
			config.addDefault("Abilities.Chi.WarriorStance.Description", "WarriorStance is an advanced chiblocker technique that gives the chiblocker increased damage but makes them a tad more vulnerable. This ability is useful when finishing off weak targets.");
			config.addDefault("Abilities.Chi.WarriorStance.Instructions", "Left click to activate the warrior stance mode. Additionally, left click to disable it.");
			config.addDefault("Abilities.Chi.Paralyze.Description", "Paralyzes the target, making them unable to do anything for a short period of time as they will be paralyzed where they're stood. ");
			config.addDefault("Abilities.Chi.Paralyze.Instructions", "Punch a player to paralyze them.");
			config.addDefault("Abilities.Chi.RapidPunch.Description", "This ability allows the chiblocker to punch rapidly in a short period. To use, simply punch. This has a short cooldown.");
			config.addDefault("Abilities.Chi.RapidPunch.Instructions", "Punch a player to deal massive damage.");
			config.addDefault("Abilities.Chi.RapidPunch.DeathMessage", "{victim} took all the hits from {attacker}'s {ability}");
			config.addDefault("Abilities.Chi.QuickStrike.Description", "QuickStrike enables a chiblocker to quickly strike an enemy, potentially blocking their chi.");
			config.addDefault("Abilities.Chi.QuickStrike.Instructions", "Left click on a player to quick strike them.");
			config.addDefault("Abilities.Chi.QuickStrike.DeathMessage", "{victim} was struck down by {attacker}'s {ability}");
			config.addDefault("Abilities.Chi.SwiftKick.Description", "SwiftKick allows a chiblocker to swiftly kick an enemy, potentially blocking their chi.");
			config.addDefault("Abilities.Chi.SwiftKick.Instructions", "Jump and left click on a player to swift kick them.");
			config.addDefault("Abilities.Chi.SwiftKick.DeathMessage", "{victim} was kicked to the floor by {attacker}'s {ability}");
			config.addDefault("Abilities.Chi.Combo.Immobilize.Description", "Immobilizes the opponent for several seconds.");
			config.addDefault("Abilities.Chi.Combo.Immobilize.Instructions", "QuickStrike > SwiftKick > QuickStrike > QuickStrike");
			config.addDefault("Abilities.Chi.Passive.ChiAgility.Description", "ChiAgility is a passive ability which enables chiblockers to run faster and jump higher.");
			config.addDefault("Abilities.Chi.Passive.ChiSaturation.Description", "ChiSaturation is a passive ability which causes chiblockers' hunger to deplete at a slower rate.");
			config.addDefault("Abilities.Chi.Passive.Acrobatics.Description", "Acrobatics is a passive ability which negates all fall damage based on a percent chance.");

			config.addDefault("Commands.Help.Elements.Avatar", "Avatars are the human embodiment of light and peace created through a connection with the Avatar Spirit. It is considered an Avatar's duty to master the four elements and use that power to keep balance among the four nations as well as act as the bridge between the physical and spiritual worlds.\nEnter /b display Avatar for a list of the available avatar abilities.");

			languageConfig.save();
		} else if (type == ConfigType.DEFAULT) {
			config = defaultConfig.get();

			final int mcVersion = GeneralMethods.getMCVersion();
			final ArrayList<String> earthBlocks = new ArrayList<String>();

			earthBlocks.add("#base_stone_nether"); // added in 1.16.2
			earthBlocks.add("#base_stone_overworld"); // added in 1.16.2

			if (mcVersion >= 1190) { //1.19
				earthBlocks.add("MUD");
				earthBlocks.add("MUDDY_MANGROVE_ROOTS");
			}
			if (mcVersion >= 1180) { //1.18
				earthBlocks.add("#terracotta");
			}
			if (mcVersion >= 1170) { //1.17
				earthBlocks.add("#coal_ores"); //These tags were only added in 1.17 and above
				earthBlocks.add("#diamond_ores");
				earthBlocks.add("#emerald_ores");
				earthBlocks.add("#lapis_ores");
				earthBlocks.add("#redstone_ores");
				earthBlocks.add("CALCITE");
				earthBlocks.add("DRIPSTONE_BLOCK");
				earthBlocks.add("LARGE_AMETHYST_BUD");
				earthBlocks.add("MEDIUM_AMETHYST_BUD");
				earthBlocks.add("SMALL_AMETHYST_BUD");
				earthBlocks.add("DIRT_PATH"); // renamed from grass_path in 1.17
				earthBlocks.add("ROOTED_DIRT");
			} else { //They are in 1.16
				earthBlocks.add("COAL_ORE");
				earthBlocks.add("DIAMOND_ORE");
				earthBlocks.add("EMERALD_ORE");
				earthBlocks.add("LAPIS_ORE");
				earthBlocks.add("REDSTONE_ORE");
				earthBlocks.add("GRASS_PATH");
			}

			earthBlocks.add("ANCIENT_DEBRIS");
			earthBlocks.add("CLAY");
			earthBlocks.add("COARSE_DIRT");
			earthBlocks.add("COAL_BLOCK");
			earthBlocks.add("COBBLESTONE");
			earthBlocks.add("COBBLESTONE_SLAB");
			earthBlocks.add("DIRT");
			earthBlocks.add("GRASS_BLOCK");
			earthBlocks.add("GRAVEL");
			earthBlocks.add("MYCELIUM");
			earthBlocks.add("PODZOL");
			earthBlocks.add("STONE_SLAB");

			final ArrayList<String> metalBlocks = new ArrayList<String>();

			if (mcVersion >= 1170) { //1.17
				metalBlocks.add("#copper_ores");
				metalBlocks.add("#gold_ores");
				metalBlocks.add("#iron_ores");
				metalBlocks.add("COPPER_BLOCK");
				metalBlocks.add("CUT_COPPER");
				metalBlocks.add("CUT_COPPER_SLAB");
				metalBlocks.add("CUT_COPPER_STAIRS");
				metalBlocks.add("EXPOSED_COPPER");
				metalBlocks.add("EXPOSED_CUT_COPPER");
				metalBlocks.add("EXPOSED_CUT_COPPER_SLAB");
				metalBlocks.add("EXPOSED_CUT_COPPER_STAIRS");
				metalBlocks.add("OXIDIZED_COPPER");
				metalBlocks.add("OXIDIZED_CUT_COPPER");
				metalBlocks.add("OXIDIZED_CUT_COPPER_SLAB");
				metalBlocks.add("OXIDIZED_CUT_COPPER_STAIRS");
				metalBlocks.add("RAW_COPPER_BLOCK");
				metalBlocks.add("RAW_GOLD_BLOCK");
				metalBlocks.add("RAW_IRON_BLOCK");
				metalBlocks.add("WAXED_COPPER_BLOCK");
				metalBlocks.add("WAXED_CUT_COPPER");
				metalBlocks.add("WAXED_CUT_COPPER_SLAB");
				metalBlocks.add("WAXED_CUT_COPPER_STAIRS");
				metalBlocks.add("WAXED_EXPOSED_COPPER");
				metalBlocks.add("WAXED_EXPOSED_CUT_COPPER");
				metalBlocks.add("WAXED_EXPOSED_CUT_COPPER_SLAB");
				metalBlocks.add("WAXED_EXPOSED_CUT_COPPER_STAIRS");
				metalBlocks.add("WAXED_OXIDIZED_COPPER");
				metalBlocks.add("WAXED_OXIDIZED_CUT_COPPER");
				metalBlocks.add("WAXED_OXIDIZED_CUT_COPPER_SLAB");
				metalBlocks.add("WAXED_OXIDIZED_CUT_COPPER_STAIRS");
				metalBlocks.add("WAXED_WEATHERED_COPPER");
				metalBlocks.add("WAXED_WEATHERED_CUT_COPPER");
				metalBlocks.add("WAXED_WEATHERED_CUT_COPPER_SLAB");
				metalBlocks.add("WAXED_WEATHERED_CUT_COPPER_STAIRS");
				metalBlocks.add("WEATHERED_COPPER");
				metalBlocks.add("WEATHERED_CUT_COPPER");
				metalBlocks.add("WEATHERED_CUT_COPPER_SLAB");
				metalBlocks.add("WEATHERED_CUT_COPPER_STAIRS");
			} else {
				metalBlocks.add("IRON_ORE");
				metalBlocks.add("GOLD_ORE");
			}

			metalBlocks.add("CHAIN");
			metalBlocks.add("GILDED_BLACKSTONE");
			metalBlocks.add("GOLD_BLOCK");
			metalBlocks.add("IRON_BLOCK");
			metalBlocks.add("NETHERITE_BLOCK");
			metalBlocks.add("NETHER_QUARTZ_ORE");
			metalBlocks.add("QUARTZ_BLOCK");

			final ArrayList<String> sandBlocks = new ArrayList<String>();
			sandBlocks.add("#sand");
			sandBlocks.add("RED_SANDSTONE");
			sandBlocks.add("RED_SANDSTONE_SLAB");
			sandBlocks.add("SANDSTONE");
			sandBlocks.add("SANDSTONE_SLAB");

			final ArrayList<String> iceBlocks = new ArrayList<String>();
			iceBlocks.add("#ice");

			final ArrayList<String> plantBlocks = new ArrayList<String>();
			plantBlocks.add("#bee_growables");
			plantBlocks.add("#flowers");
			plantBlocks.add("#leaves");
			plantBlocks.add("#saplings");

			plantBlocks.add("BROWN_MUSHROOM");
			plantBlocks.add("BROWN_MUSHROOM_BLOCK");
			plantBlocks.add("BAMBOO");
			plantBlocks.add("BAMBOO_SAPLING");
			plantBlocks.add("CACTUS");
			plantBlocks.add("CRIMSON_FUNGUS");
			plantBlocks.add("CRIMSON_ROOTS");
			plantBlocks.add("FERN");
			if (mcVersion < 1203) { //1.20.3 changed GRASS to SHORT_GRASS
				plantBlocks.add("GRASS");
			} else {
				plantBlocks.add("SHORT_GRASS");
			}
			plantBlocks.add("LARGE_FERN");
			plantBlocks.add("LILY_PAD");
			plantBlocks.add("MELON");
			plantBlocks.add("MELON_STEM");
			plantBlocks.add("MUSHROOM_STEM");
			plantBlocks.add("NETHER_SPROUTS");
			plantBlocks.add("PUMPKIN");
			plantBlocks.add("PUMPKIN_STEM");
			plantBlocks.add("RED_MUSHROOM");
			plantBlocks.add("RED_MUSHROOM_BLOCK");
			plantBlocks.add("SUGAR_CANE");
			plantBlocks.add("TALL_GRASS");
			plantBlocks.add("TWISTING_VINES_PLANT");
			plantBlocks.add("VINE");
			plantBlocks.add("WARPED_FUNGUS");
			plantBlocks.add("WARPED_ROOTS");
			plantBlocks.add("WEEPING_VINES_PLANT");

			if (mcVersion >= 1170) {
				plantBlocks.add("BIG_DRIPLEAF");
				plantBlocks.add("HANGING_ROOTS");
				plantBlocks.add("MOSS_BLOCK");
				plantBlocks.add("MOSS_CARPET");
				plantBlocks.add("SMALL_DRIPLEAF");
				plantBlocks.add("SPORE_BLOSSOM");
			}

			final ArrayList<String> snowBlocks = new ArrayList<>();
			snowBlocks.add("#snow"); // added in 1.17

			/*
			final ArrayList<String> waterTransformableBlocks = new ArrayList<>();
			waterTransformableBlocks.add("MUD>DIRT");
			waterTransformableBlocks.add("PACKED_MUD>DIRT");
			waterTransformableBlocks.add("MUDDY_MANGROVE_ROOTS>MANGROVE_ROOTS");
			waterTransformableBlocks.add("WET_SPONGE>SPONGE");
			 */

			config.addDefault("Properties.UpdateChecker", true);
			config.addDefault("Properties.Statistics", true);
			config.addDefault("Properties.DatabaseCooldowns", true);
			config.addDefault("Properties.BendingBoard", true);
			config.addDefault("Properties.BendingPreview", true);
			config.addDefault("Properties.BendingAffectFallingSand.Normal", true);
			config.addDefault("Properties.BendingAffectFallingSand.NormalStrengthMultiplier", 1.0);
			config.addDefault("Properties.BendingAffectFallingSand.TNT", true);
			config.addDefault("Properties.BendingAffectFallingSand.TNTStrengthMultiplier", 1.0);
			config.addDefault("Properties.GlobalCooldown", 500);
			config.addDefault("Properties.PlayerDataUnloadTime", 1000 * 60 * 5);
			config.addDefault("Properties.TogglePassivesWithAllBending", true);
			config.addDefault("Properties.SeaLevel", 62);
			config.addDefault("Properties.ChooseCooldown", 0L);
			config.addDefault("Properties.MaxPresets", 10);
			config.addDefault("Properties.IgnoreArmorPercentage.Default", 0.5);

			config.addDefault("Properties.HorizontalCollisionPhysics.Enabled", true);
			config.addDefault("Properties.HorizontalCollisionPhysics.DamageOnBarrierBlock", false);
			config.addDefault("Properties.HorizontalCollisionPhysics.WallDamageMinimumDistance", 5.0);
			config.addDefault("Properties.HorizontalCollisionPhysics.WallDamageCap", 5.0);

			config.addDefault("Properties.RegionProtection.AllowHarmlessAbilities", true);
			config.addDefault("Properties.RegionProtection.RespectWorldGuard", true);
			config.addDefault("Properties.RegionProtection.RespectGriefDefender", true);
			config.addDefault("Properties.RegionProtection.RespectGriefPrevention", true);
			config.addDefault("Properties.RegionProtection.RespectFactions", true);
			config.addDefault("Properties.RegionProtection.RespectTowny", true);
			config.addDefault("Properties.RegionProtection.RespectLWC", true);
			config.addDefault("Properties.RegionProtection.RespectLands", true);
			config.addDefault("Properties.RegionProtection.Residence.Flag", "bending");
			config.addDefault("Properties.RegionProtection.Residence.Respect", true);
			config.addDefault("Properties.RegionProtection.Kingdoms.Respect", true);
			config.addDefault("Properties.RegionProtection.Kingdoms.ProtectDuringInvasions", false);
			config.addDefault("Properties.RegionProtection.RespectPlotSquared", true);
			config.addDefault("Properties.RegionProtection.RespectRedProtect", true);
			config.addDefault("Properties.RegionProtection.CacheBlockTime", 5000);

			config.addDefault("Properties.Air.CanBendWithWeapons", false);
			config.addDefault("Properties.Air.Particles", "spell");
			config.addDefault("Properties.Air.PlaySound", true);
			config.addDefault("Properties.Air.Sound.Sound", "ENTITY_CREEPER_HURT");
			config.addDefault("Properties.Air.Sound.Volume", 1);
			config.addDefault("Properties.Air.Sound.Pitch", 2);

			config.addDefault("Properties.Water.DynamicSourcing", true);
			config.addDefault("Properties.Water.FreezePlayerHead", true);
			config.addDefault("Properties.Water.FreezePlayerFeet", true);
			config.addDefault("Properties.Water.CanBendWithWeapons", true);
			config.addDefault("Properties.Water.IceBlocks", iceBlocks);
			config.addDefault("Properties.Water.PlantBlocks", plantBlocks);
			config.addDefault("Properties.Water.SnowBlocks", snowBlocks);
			// config.addDefault("Properties.Water.TransformableBlocks", waterTransformableBlocks);
			config.addDefault("Properties.Water.NightFactor", 1.25);
			config.addDefault("Properties.Water.PlaySound", true);
			config.addDefault("Properties.Water.WaterSound.Sound", "BLOCK_WATER_AMBIENT");
			config.addDefault("Properties.Water.WaterSound.Volume", 1);
			config.addDefault("Properties.Water.WaterSound.Pitch", 1);
			config.addDefault("Properties.Water.IceSound.Sound", "ITEM_FLINTANDSTEEL_USE");
			config.addDefault("Properties.Water.IceSound.Volume", 1);
			config.addDefault("Properties.Water.IceSound.Pitch", 1);
			config.addDefault("Properties.Water.PlantSound.Sound", "BLOCK_GRASS_STEP");
			config.addDefault("Properties.Water.IceSound.Volume", 1);
			config.addDefault("Properties.Water.IceSound.Pitch", 1);
			config.addDefault("Properties.Water.MudSound.Sound", "BLOCK_MUD_STEP");
			config.addDefault("Properties.Water.MudSound.Volume", 1);
			config.addDefault("Properties.Water.MudSound.Pitch", 1);

			config.addDefault("Properties.Earth.DynamicSourcing", true);
			config.addDefault("Properties.Earth.RevertEarthbending", true);
			config.addDefault("Properties.Earth.SafeRevert", true);
			config.addDefault("Properties.Earth.RevertCheckTime", 300000);
			config.addDefault("Properties.Earth.CanBendWithWeapons", true);
			config.addDefault("Properties.Earth.EarthBlocks", earthBlocks);
			config.addDefault("Properties.Earth.MetalBlocks", metalBlocks);
			config.addDefault("Properties.Earth.SandBlocks", sandBlocks);
			config.addDefault("Properties.Earth.MetalPowerFactor", 1.5);
			config.addDefault("Properties.Earth.PlaySound", true);
			config.addDefault("Properties.Earth.EarthSound.Sound", "ENTITY_GHAST_SHOOT");
			config.addDefault("Properties.Earth.EarthSound.Volume", 1);
			config.addDefault("Properties.Earth.EarthSound.Pitch", 1);
			config.addDefault("Properties.Earth.MetalSound.Sound", "ENTITY_IRON_GOLEM_HURT");
			config.addDefault("Properties.Earth.MetalSound.Volume", 1);
			config.addDefault("Properties.Earth.MetalSound.Pitch", 1.25);
			config.addDefault("Properties.Earth.SandSound.Sound", "BLOCK_SAND_BREAK");
			config.addDefault("Properties.Earth.SandSound.Volume", 1);
			config.addDefault("Properties.Earth.SandSound.Pitch", 1);
			config.addDefault("Properties.Earth.LavaSound.Sound", "BLOCK_LAVA_AMBIENT");
			config.addDefault("Properties.Earth.LavaSound.Volume", 1);
			config.addDefault("Properties.Earth.LavaSound.Pitch", 1);
			config.addDefault("Properties.Earth.MudSound.Sound", "BLOCK_MUD_PLACE");
			config.addDefault("Properties.Earth.MudSound.Volume", 1);
			config.addDefault("Properties.Earth.MudSound.Pitch", 1);

			config.addDefault("Properties.Fire.CanBendWithWeapons", true);
			config.addDefault("Properties.Fire.DayFactor", 1.25);
			config.addDefault("Properties.Fire.PlaySound", true);
			config.addDefault("Properties.Fire.FireGriefing", false);
			config.addDefault("Properties.Fire.RevertTicks", 12000L);
			config.addDefault("Properties.Fire.FireSound.Sound", "BLOCK_FIRE_AMBIENT");
			config.addDefault("Properties.Fire.FireSound.Volume", 1);
			config.addDefault("Properties.Fire.FireSound.Pitch", 1);
			config.addDefault("Properties.Fire.CombustionSound.Sound", "ENTITY_FIREWORK_ROCKET_BLAST");
			config.addDefault("Properties.Fire.CombustionSound.Volume", 1);
			config.addDefault("Properties.Fire.CombustionSound.Pitch", 0);
			config.addDefault("Properties.Fire.LightningSound.Sound", "ENTITY_CREEPER_HURT");
			config.addDefault("Properties.Fire.LightningSound.Volume", 1);
			config.addDefault("Properties.Fire.LightningSound.Pitch", 0);
			config.addDefault("Properties.Fire.LightningCharge.Sound", "BLOCK_BEEHIVE_WORK");
			config.addDefault("Properties.Fire.LightningCharge.Volume", 2);
			config.addDefault("Properties.Fire.LightningCharge.Pitch", .5);
			config.addDefault("Properties.Fire.LightningHit.Sound", "ENTITY_LIGHTNING_BOLT_THUNDER");
			config.addDefault("Properties.Fire.LightningHit.Volume", 1);
			config.addDefault("Properties.Fire.LightningHit.Pitch", 2);
			config.addDefault("Properties.Fire.BlueFire.DamageFactor", 1.1);
			config.addDefault("Properties.Fire.BlueFire.CooldownFactor", .9);
			config.addDefault("Properties.Fire.BlueFire.RangeFactor", 1.2);
			config.addDefault("Properties.Fire.DynamicLight.Enabled", true);
			config.addDefault("Properties.Fire.DynamicLight.Brightness", 13);
			config.addDefault("Properties.Fire.DynamicLight.KeepAlive", 600);

			config.addDefault("Properties.Chi.CanBendWithWeapons", true);

			final ArrayList<String> disabledWorlds = new ArrayList<String>();
			disabledWorlds.add("TestWorld");
			disabledWorlds.add("TestWorld2");
			config.addDefault("Properties.DisabledWorlds", disabledWorlds);

			if (config.contains("Abilities.Avatar.AvatarState.PowerMultiplier")) { //If old values exist from the old config, migrate to the new system
				migrateAvatarState();
			}

			config.addDefault("Abilities.Air.Passive.Factor", 0.3);
			config.addDefault("Abilities.Air.Passive.AirAgility.Enabled", true);
			config.addDefault("Abilities.Air.Passive.AirAgility.JumpPower", 3);
			config.addDefault("Abilities.Air.Passive.AirAgility.SpeedPower", 2);
			config.addDefault("Abilities.Air.Passive.AirSaturation.Enabled", true);
			config.addDefault("Abilities.Air.Passive.GracefulDescent.Enabled", true);

			config.addDefault("Abilities.Air.AirBlast.Enabled", true);
			config.addDefault("Abilities.Air.AirBlast.Speed", 15);
			config.addDefault("Abilities.Air.AirBlast.Range", 20);
			config.addDefault("Abilities.Air.AirBlast.Radius", .5);
			config.addDefault("Abilities.Air.AirBlast.SelectRange", 10);
			config.addDefault("Abilities.Air.AirBlast.SelectParticles", 4);
			config.addDefault("Abilities.Air.AirBlast.Particles", 6);
			config.addDefault("Abilities.Air.AirBlast.Cooldown", 500);
			config.addDefault("Abilities.Air.AirBlast.Push.Self", 2.0);
			config.addDefault("Abilities.Air.AirBlast.Push.Entities", 1.6);
			config.addDefault("Abilities.Air.AirBlast.CanFlickLevers", true);
			config.addDefault("Abilities.Air.AirBlast.CanOpenDoors", true);
			config.addDefault("Abilities.Air.AirBlast.CanPressButtons", true);
			config.addDefault("Abilities.Air.AirBlast.CanCoolLava", true);
			config.addDefault("Abilities.Air.AirBlast.MaxChains", 5);
			config.addDefault("Abilities.Air.AirBlast.LongCooldown", 5000);

			config.addDefault("Abilities.Air.AirBurst.Enabled", true);
			config.addDefault("Abilities.Air.AirBurst.FallThreshold", 10);
			config.addDefault("Abilities.Air.AirBurst.PushFactor", 2.8);
			config.addDefault("Abilities.Air.AirBurst.ChargeTime", 1750);
			config.addDefault("Abilities.Air.AirBurst.Damage", 0);
			config.addDefault("Abilities.Air.AirBurst.Cooldown", 0);
			config.addDefault("Abilities.Air.AirBurst.SneakParticles", 10);
			config.addDefault("Abilities.Air.AirBurst.ParticlePercentage", 50);
			config.addDefault("Abilities.Air.AirBurst.AnglePhi", 10);
			config.addDefault("Abilities.Air.AirBurst.AngleTheta", 10);

			config.addDefault("Abilities.Air.AirScooter.Enabled", true);
			config.addDefault("Abilities.Air.AirScooter.ShowSitting", false);
			config.addDefault("Abilities.Air.AirScooter.Speed", 0.675);
			config.addDefault("Abilities.Air.AirScooter.Interval", 100);
			config.addDefault("Abilities.Air.AirScooter.Radius", 1);
			config.addDefault("Abilities.Air.AirScooter.Cooldown", 7000);
			config.addDefault("Abilities.Air.AirScooter.Duration", 0);
			config.addDefault("Abilities.Air.AirScooter.MaxHeightFromGround", 7);
			config.addDefault("Abilities.Air.AirScooter.DamageThreshold", 4);

			config.addDefault("Abilities.Air.AirShield.Enabled", true);
			config.addDefault("Abilities.Air.AirShield.Cooldown", 0);
			config.addDefault("Abilities.Air.AirShield.Duration", 0);
			config.addDefault("Abilities.Air.AirShield.MaxRadius", 7);
			config.addDefault("Abilities.Air.AirShield.InitialRadius", 1);
			config.addDefault("Abilities.Air.AirShield.Streams", 5);
			config.addDefault("Abilities.Air.AirShield.Speed", 10);
			config.addDefault("Abilities.Air.AirShield.Push", 0.5);
			config.addDefault("Abilities.Air.AirShield.Particles", 5);
			config.addDefault("Abilities.Air.AirShield.DynamicCooldown", false);

			config.addDefault("Abilities.Air.AirSpout.Enabled", true);
			config.addDefault("Abilities.Air.AirSpout.Cooldown", 0);
			config.addDefault("Abilities.Air.AirSpout.Duration", 0);
			config.addDefault("Abilities.Air.AirSpout.Height", 16);
			config.addDefault("Abilities.Air.AirSpout.Interval", 100);

			config.addDefault("Abilities.Air.AirSuction.Enabled", true);
			config.addDefault("Abilities.Air.AirSuction.Speed", 25);
			config.addDefault("Abilities.Air.AirSuction.Range", 20);
			config.addDefault("Abilities.Air.AirSuction.SelectRange", 10);
			config.addDefault("Abilities.Air.AirSuction.Radius", .5);
			config.addDefault("Abilities.Air.AirSuction.Push.Self", 2.0);
			config.addDefault("Abilities.Air.AirSuction.Push.Others", 1.3);
			config.addDefault("Abilities.Air.AirSuction.Cooldown", 500);
			config.addDefault("Abilities.Air.AirSuction.Particles", 6);
			config.addDefault("Abilities.Air.AirSuction.SelectParticles", 6);
			config.addDefault("Abilities.Air.AirSuction.MaxChains", 5);
			config.addDefault("Abilities.Air.AirSuction.LongCooldown", 5000);

			config.addDefault("Abilities.Air.AirSwipe.Enabled", true);
			config.addDefault("Abilities.Air.AirSwipe.Damage", 2);
			config.addDefault("Abilities.Air.AirSwipe.Range", 14);
			config.addDefault("Abilities.Air.AirSwipe.Radius", 0.5);
			config.addDefault("Abilities.Air.AirSwipe.Push", 0.5);
			config.addDefault("Abilities.Air.AirSwipe.Arc", 16);
			config.addDefault("Abilities.Air.AirSwipe.Speed", 25);
			config.addDefault("Abilities.Air.AirSwipe.Cooldown", 1500);
			config.addDefault("Abilities.Air.AirSwipe.ChargeFactor", 3);
			config.addDefault("Abilities.Air.AirSwipe.MaxChargeTime", 2500);
			config.addDefault("Abilities.Air.AirSwipe.Particles", 3);
			config.addDefault("Abilities.Air.AirSwipe.StepSize", 4);

			config.addDefault("Abilities.Air.Flight.Enabled", true);
			config.addDefault("Abilities.Air.Flight.Cooldown", 0);
			config.addDefault("Abilities.Air.Flight.BaseSpeed", 1.2);
			config.addDefault("Abilities.Air.Flight.Duration", 0);

			config.addDefault("Abilities.Air.Suffocate.Enabled", true);
			config.addDefault("Abilities.Air.Suffocate.ChargeTime", 500);
			config.addDefault("Abilities.Air.Suffocate.Cooldown", 6500);
			config.addDefault("Abilities.Air.Suffocate.Range", 20);
			config.addDefault("Abilities.Air.Suffocate.Damage", 2);
			config.addDefault("Abilities.Air.Suffocate.DamageInitialDelay", 2);
			config.addDefault("Abilities.Air.Suffocate.DamageInterval", 1);
			config.addDefault("Abilities.Air.Suffocate.SlowPotency", 1);
			config.addDefault("Abilities.Air.Suffocate.SlowDelay", 0.5);
			config.addDefault("Abilities.Air.Suffocate.SlowInterval", 1.25);
			config.addDefault("Abilities.Air.Suffocate.BlindPotentcy", 30);
			config.addDefault("Abilities.Air.Suffocate.BlindDelay", 2);
			config.addDefault("Abilities.Air.Suffocate.BlindInterval", 1.5);
			config.addDefault("Abilities.Air.Suffocate.CanBeUsedOnUndeadMobs", true);
			config.addDefault("Abilities.Air.Suffocate.RequireConstantAim", true);
			config.addDefault("Abilities.Air.Suffocate.RequireConstantAimRadius", 5);
			config.addDefault("Abilities.Air.Suffocate.AnimationRadius", 2.0);
			config.addDefault("Abilities.Air.Suffocate.AnimationParticleAmount", 1);
			config.addDefault("Abilities.Air.Suffocate.AnimationSpeed", 1.0);

			config.addDefault("Abilities.Air.Tornado.Enabled", true);
			config.addDefault("Abilities.Air.Tornado.Cooldown", 0);
			config.addDefault("Abilities.Air.Tornado.Duration", 0);
			config.addDefault("Abilities.Air.Tornado.Radius", 10);
			config.addDefault("Abilities.Air.Tornado.Height", 20);
			config.addDefault("Abilities.Air.Tornado.Range", 25);
			config.addDefault("Abilities.Air.Tornado.Speed", 1);
			config.addDefault("Abilities.Air.Tornado.NpcPushFactor", 1);
			config.addDefault("Abilities.Air.Tornado.PlayerPushFactor", 1);

			config.addDefault("Abilities.Air.Twister.Enabled", true);
			config.addDefault("Abilities.Air.Twister.Speed", 0.35);
			config.addDefault("Abilities.Air.Twister.Range", 16);
			config.addDefault("Abilities.Air.Twister.Height", 8);
			config.addDefault("Abilities.Air.Twister.Radius", 3.5);
			config.addDefault("Abilities.Air.Twister.RemoveDelay", 1500);
			config.addDefault("Abilities.Air.Twister.Cooldown", 10000);
			config.addDefault("Abilities.Air.Twister.DegreesPerParticle", 7);
			config.addDefault("Abilities.Air.Twister.HeightPerParticle", 1.25);
			config.addDefault("Abilities.Air.Twister.Combination", Arrays.asList("AirShield:SNEAK_DOWN", "AirShield:SNEAK_UP", "Tornado:SNEAK_DOWN", "AirBlast:LEFT_CLICK"));

			config.addDefault("Abilities.Air.AirStream.Enabled", true);
			config.addDefault("Abilities.Air.AirStream.Speed", 0.5);
			config.addDefault("Abilities.Air.AirStream.Range", 40);
			config.addDefault("Abilities.Air.AirStream.EntityCarry.Duration", 4000);
			config.addDefault("Abilities.Air.AirStream.EntityCarry.Height", 14);
			config.addDefault("Abilities.Air.AirStream.Cooldown", 7000);
			config.addDefault("Abilities.Air.AirStream.Combination", Arrays.asList("AirShield:SNEAK_DOWN", "AirSuction:LEFT_CLICK", "AirBlast:LEFT_CLICK"));

			config.addDefault("Abilities.Air.AirSweep.Enabled", true);
			config.addDefault("Abilities.Air.AirSweep.Speed", 1.4);
			config.addDefault("Abilities.Air.AirSweep.Range", 14);
			config.addDefault("Abilities.Air.AirSweep.Damage", 3);
			config.addDefault("Abilities.Air.AirSweep.Knockback", 3.5);
			config.addDefault("Abilities.Air.AirSweep.Cooldown", 6000);
			config.addDefault("Abilities.Air.AirSweep.Radius", 1);
			config.addDefault("Abilities.Air.AirSweep.Combination", Arrays.asList("AirSwipe:LEFT_CLICK", "AirSwipe:LEFT_CLICK", "AirBurst:SNEAK_DOWN", "AirBurst:LEFT_CLICK"));

			config.addDefault("Abilities.Water.Passive.FastSwim.Enabled", true);
			config.addDefault("Abilities.Water.Passive.FastSwim.Cooldown", 0);
			config.addDefault("Abilities.Water.Passive.FastSwim.Duration", 0);
			config.addDefault("Abilities.Water.Passive.FastSwim.SpeedFactor", 0.7);
			config.addDefault("Abilities.Water.Passive.FastSwim.AllowWaterArms", false);
			config.addDefault("Abilities.Water.Passive.Hydrosink.Enabled", true);

			config.addDefault("Abilities.Water.Bloodbending.Enabled", true);
			config.addDefault("Abilities.Water.Bloodbending.CanOnlyBeUsedAtNight", true);
			config.addDefault("Abilities.Water.Bloodbending.CanBeUsedOnUndeadMobs", true);
			config.addDefault("Abilities.Water.Bloodbending.Knockback", 2);
			config.addDefault("Abilities.Water.Bloodbending.Range", 10);
			config.addDefault("Abilities.Water.Bloodbending.Duration", 0);
			config.addDefault("Abilities.Water.Bloodbending.Cooldown", 3000);
			config.addDefault("Abilities.Water.Bloodbending.CanOnlyBeUsedDuringFullMoon", true);
			config.addDefault("Abilities.Water.Bloodbending.CanBloodbendOtherBloodbenders", false);
			
			List<String> bloodless = new ArrayList<>();
			bloodless.add(EntityType.SKELETON.name());
			bloodless.add(EntityType.IRON_GOLEM.name());
			bloodless.add(EntityType.BLAZE.name());
			bloodless.add(EntityType.MAGMA_CUBE.name());
			bloodless.add(EntityType.SHULKER.name());
			bloodless.add(EntityType.SKELETON_HORSE.name());
			bloodless.add(EntityType.WITHER_SKELETON.name());
			bloodless.add(EntityType.STRAY.name());
			
			config.addDefault("Abilities.Water.Bloodbending.Bloodless", bloodless);

			config.addDefault("Abilities.Water.HealingWaters.Enabled", true);
			config.addDefault("Abilities.Water.HealingWaters.Cooldown", 0);
			config.addDefault("Abilities.Water.HealingWaters.Range", 5);
			config.addDefault("Abilities.Water.HealingWaters.Interval", 750);
			config.addDefault("Abilities.Water.HealingWaters.ChargeTime", 1000);
			config.addDefault("Abilities.Water.HealingWaters.PotionPotency", 1);
			config.addDefault("Abilities.Water.HealingWaters.Duration", 0);
			config.addDefault("Abilities.Water.HealingWaters.EnableParticles", true);
			config.addDefault("Abilities.Water.HealingWaters.DynamicLight.Enabled", true);
			config.addDefault("Abilities.Water.HealingWaters.DynamicLight.Brightness", 10);
			config.addDefault("Abilities.Water.HealingWaters.DynamicLight.KeepAlive", 350);

			config.addDefault("Abilities.Water.IceBlast.Enabled", true);
			config.addDefault("Abilities.Water.IceBlast.Damage", 3);
			config.addDefault("Abilities.Water.IceBlast.Range", 20);
			config.addDefault("Abilities.Water.IceBlast.DeflectRange", 3);
			config.addDefault("Abilities.Water.IceBlast.CollisionRadius", 1.0);
			config.addDefault("Abilities.Water.IceBlast.Interval", 20);
			config.addDefault("Abilities.Water.IceBlast.Cooldown", 1500);
			config.addDefault("Abilities.Water.IceBlast.AllowSnow", false);

			config.addDefault("Abilities.Water.IceSpike.Enabled", true);
			config.addDefault("Abilities.Water.IceSpike.Cooldown", 2000);
			config.addDefault("Abilities.Water.IceSpike.Damage", 2);
			config.addDefault("Abilities.Water.IceSpike.Range", 20);
			config.addDefault("Abilities.Water.IceSpike.Push", 0.7);
			config.addDefault("Abilities.Water.IceSpike.Height", 6);
			config.addDefault("Abilities.Water.IceSpike.Speed", 25);
			config.addDefault("Abilities.Water.IceSpike.SlowCooldown", 5000);
			config.addDefault("Abilities.Water.IceSpike.SlowPotency", 2);
			config.addDefault("Abilities.Water.IceSpike.SlowDuration", 70);
			config.addDefault("Abilities.Water.IceSpike.Field.Damage", 2);
			config.addDefault("Abilities.Water.IceSpike.Field.Radius", 6);
			config.addDefault("Abilities.Water.IceSpike.Field.Knockup", 1);
			config.addDefault("Abilities.Water.IceSpike.Field.Cooldown", 2000);
			config.addDefault("Abilities.Water.IceSpike.Blast.Range", 20);
			config.addDefault("Abilities.Water.IceSpike.Blast.Damage", 1);
			config.addDefault("Abilities.Water.IceSpike.Blast.CollisionRadius", 1.0);
			config.addDefault("Abilities.Water.IceSpike.Blast.DeflectRange", 3);
			config.addDefault("Abilities.Water.IceSpike.Blast.Cooldown", 500);
			config.addDefault("Abilities.Water.IceSpike.Blast.SlowCooldown", 5000);
			config.addDefault("Abilities.Water.IceSpike.Blast.SlowPotency", 2);
			config.addDefault("Abilities.Water.IceSpike.Blast.SlowDuration", 70);
			config.addDefault("Abilities.Water.IceSpike.Blast.Interval", 20);

			config.addDefault("Abilities.Water.OctopusForm.Enabled", true);
			config.addDefault("Abilities.Water.OctopusForm.Range", 10);
			config.addDefault("Abilities.Water.OctopusForm.AttackRange", 2.5);
			config.addDefault("Abilities.Water.OctopusForm.Radius", 3);
			config.addDefault("Abilities.Water.OctopusForm.Damage", 2);
			config.addDefault("Abilities.Water.OctopusForm.Knockback", 1.75);
			config.addDefault("Abilities.Water.OctopusForm.FormDelay", 40);
			config.addDefault("Abilities.Water.OctopusForm.Cooldown", 0);
			config.addDefault("Abilities.Water.OctopusForm.Duration", 0);
			config.addDefault("Abilities.Water.OctopusForm.UsageCooldown", 0);
			config.addDefault("Abilities.Water.OctopusForm.AngleIncrement", 45);

			config.addDefault("Abilities.Water.PhaseChange.Enabled", true);
			config.addDefault("Abilities.Water.PhaseChange.SourceRange", 12);
			config.addDefault("Abilities.Water.PhaseChange.Freeze.Cooldown", 250);
			config.addDefault("Abilities.Water.PhaseChange.Freeze.Radius", 4);
			config.addDefault("Abilities.Water.PhaseChange.Freeze.Depth", 1);
			config.addDefault("Abilities.Water.PhaseChange.Freeze.ControlRadius", 25);
			config.addDefault("Abilities.Water.PhaseChange.Melt.Cooldown", 2000);
			config.addDefault("Abilities.Water.PhaseChange.Melt.Speed", 8.0);
			config.addDefault("Abilities.Water.PhaseChange.Melt.Radius", 7);
			config.addDefault("Abilities.Water.PhaseChange.Melt.AllowFlow", true);

			config.addDefault("Abilities.Water.Surge.Enabled", true);
			config.addDefault("Abilities.Water.Surge.Wave.Radius", 3);
			config.addDefault("Abilities.Water.Surge.Wave.Range", 20);
			config.addDefault("Abilities.Water.Surge.Wave.SelectRange", 12);
			config.addDefault("Abilities.Water.Surge.Wave.Knockback", 1);
			config.addDefault("Abilities.Water.Surge.Wave.Knockup", 0.2);
			config.addDefault("Abilities.Water.Surge.Wave.MaxFreezeRadius", 7);
			config.addDefault("Abilities.Water.Surge.Wave.Cooldown", 500);
			config.addDefault("Abilities.Water.Surge.Wave.Interval", 30);
			config.addDefault("Abilities.Water.Surge.Wave.SolidifyLava.Enabled", true);
			config.addDefault("Abilities.Water.Surge.Wave.SolidifyLava.Duration", 36000);
			config.addDefault("Abilities.Water.Surge.Wall.Range", 5);
			config.addDefault("Abilities.Water.Surge.Wall.SelectRange", 5);
			config.addDefault("Abilities.Water.Surge.Wall.Radius", 2);
			config.addDefault("Abilities.Water.Surge.Wall.Cooldown", 0);
			config.addDefault("Abilities.Water.Surge.Wall.Duration", 0);
			config.addDefault("Abilities.Water.Surge.Wall.Interval", 30);
			config.addDefault("Abilities.Water.Surge.Wall.SolidifyLava.Enabled", true);
			config.addDefault("Abilities.Water.Surge.Wall.SolidifyLava.Duration", 36000);
			config.addDefault("Abilities.Water.Surge.Wave.IceRevertTime", 60000);

			config.addDefault("Abilities.Water.Torrent.Enabled", true);
			config.addDefault("Abilities.Water.Torrent.Range", 25);
			config.addDefault("Abilities.Water.Torrent.SelectRange", 16);
			config.addDefault("Abilities.Water.Torrent.InitialDamage", 3);
			config.addDefault("Abilities.Water.Torrent.DeflectDamage", 1);
			config.addDefault("Abilities.Water.Torrent.SuccessiveDamage", 1);
			config.addDefault("Abilities.Water.Torrent.MaxLayer", 3);
			config.addDefault("Abilities.Water.Torrent.MaxHits", 2);
			config.addDefault("Abilities.Water.Torrent.Knockback", 1);
			config.addDefault("Abilities.Water.Torrent.Angle", 20);
			config.addDefault("Abilities.Water.Torrent.Radius", 3);
			config.addDefault("Abilities.Water.Torrent.Knockup", 0.2);
			config.addDefault("Abilities.Water.Torrent.Interval", 30);
			config.addDefault("Abilities.Water.Torrent.Cooldown", 0);
			config.addDefault("Abilities.Water.Torrent.ChargeTimeout", 0);
			config.addDefault("Abilities.Water.Torrent.Revert", true);
			config.addDefault("Abilities.Water.Torrent.RevertTime", 60000);
			config.addDefault("Abilities.Water.Torrent.Wave.Radius", 12);
			config.addDefault("Abilities.Water.Torrent.Wave.Knockback", 1.5);
			config.addDefault("Abilities.Water.Torrent.Wave.Height", 1);
			config.addDefault("Abilities.Water.Torrent.Wave.GrowSpeed", 0.5);
			config.addDefault("Abilities.Water.Torrent.Wave.Interval", 30);
			config.addDefault("Abilities.Water.Torrent.Wave.Cooldown", 0);

			config.addDefault("Abilities.Water.Plantbending.RegrowTime", 180000);

			config.addDefault("Abilities.Water.WaterArms.Enabled", true);
			config.addDefault("Abilities.Water.WaterArms.DisplayBoundMsg", false);

			config.addDefault("Abilities.Water.WaterArms.Arms.InitialLength", 4);
			config.addDefault("Abilities.Water.WaterArms.Arms.SourceGrabRange", 12);
			config.addDefault("Abilities.Water.WaterArms.Arms.MaxAttacks", 10);
			config.addDefault("Abilities.Water.WaterArms.Arms.MaxAlternateUsage", 50);
			config.addDefault("Abilities.Water.WaterArms.Arms.MaxIceShots", 8);
			config.addDefault("Abilities.Water.WaterArms.Arms.Cooldown", 20000);
			config.addDefault("Abilities.Water.WaterArms.Arms.AllowPlantSource", true);

			config.addDefault("Abilities.Water.WaterArms.Arms.Lightning.Enabled", true);
			config.addDefault("Abilities.Water.WaterArms.Arms.Lightning.Damage", Double.valueOf(10.0));
			config.addDefault("Abilities.Water.WaterArms.Arms.Lightning.KillUser", false);

			config.addDefault("Abilities.Water.WaterArms.Arms.Cooldowns.UsageCooldown.Enabled", false);
			config.addDefault("Abilities.Water.WaterArms.Arms.Cooldowns.UsageCooldown.Pull", 200);
			config.addDefault("Abilities.Water.WaterArms.Arms.Cooldowns.UsageCooldown.Punch", 200);
			config.addDefault("Abilities.Water.WaterArms.Arms.Cooldowns.UsageCooldown.Grapple", 200);
			config.addDefault("Abilities.Water.WaterArms.Arms.Cooldowns.UsageCooldown.Grab", 200);
			config.addDefault("Abilities.Water.WaterArms.Arms.Cooldowns.UsageCooldown.Freeze", 200);
			config.addDefault("Abilities.Water.WaterArms.Arms.Cooldowns.UsageCooldown.Spear", 200);

			config.addDefault("Abilities.Water.WaterArms.Whip.MaxLength", 12);
			config.addDefault("Abilities.Water.WaterArms.Whip.MaxLengthWeak", 8);

			config.addDefault("Abilities.Water.WaterArms.Whip.NightAugments.MaxLength.Normal", 16);
			config.addDefault("Abilities.Water.WaterArms.Whip.NightAugments.MaxLength.FullMoon", 20);

			config.addDefault("Abilities.Water.WaterArms.Whip.Pull.Multiplier", 0.15);

			config.addDefault("Abilities.Water.WaterArms.Whip.Punch.Damage", 0.5);
			config.addDefault("Abilities.Water.WaterArms.Whip.Punch.MaxLength", 8);
			config.addDefault("Abilities.Water.WaterArms.Whip.Punch.NightAugments.MaxLength.Normal", 11);
			config.addDefault("Abilities.Water.WaterArms.Whip.Punch.NightAugments.MaxLength.FullMoon", 13);

			config.addDefault("Abilities.Water.WaterArms.Whip.Grapple.RespectRegions", false);

			config.addDefault("Abilities.Water.WaterArms.Whip.Grab.Duration", 3500);

			config.addDefault("Abilities.Water.WaterArms.Freeze.Range", 20);
			config.addDefault("Abilities.Water.WaterArms.Freeze.Damage", 2);

			config.addDefault("Abilities.Water.WaterArms.Spear.Range", 30);
			config.addDefault("Abilities.Water.WaterArms.Spear.Damage", 3);
			config.addDefault("Abilities.Water.WaterArms.Spear.DamageEnabled", true);
			config.addDefault("Abilities.Water.WaterArms.Spear.SphereRadius", 2);
			config.addDefault("Abilities.Water.WaterArms.Spear.Duration", 4500);
			config.addDefault("Abilities.Water.WaterArms.Spear.Length", 18);

			config.addDefault("Abilities.Water.WaterArms.Spear.NightAugments.Range.Normal", 45);
			config.addDefault("Abilities.Water.WaterArms.Spear.NightAugments.Range.FullMoon", 60);
			config.addDefault("Abilities.Water.WaterArms.Spear.NightAugments.Sphere.Normal", 3);
			config.addDefault("Abilities.Water.WaterArms.Spear.NightAugments.Sphere.FullMoon", 6);
			config.addDefault("Abilities.Water.WaterArms.Spear.NightAugments.Duration.Normal", 7000);
			config.addDefault("Abilities.Water.WaterArms.Spear.NightAugments.Duration.FullMoon", 12000);

			config.addDefault("Abilities.Water.WaterBubble.Enabled", true);
			config.addDefault("Abilities.Water.WaterBubble.Cooldown.Shift", 0);
			config.addDefault("Abilities.Water.WaterBubble.Cooldown.Click", 0);
			config.addDefault("Abilities.Water.WaterBubble.Radius", 4.0);
			config.addDefault("Abilities.Water.WaterBubble.Speed", 0.5);
			config.addDefault("Abilities.Water.WaterBubble.ClickDuration", 2000L);
			config.addDefault("Abilities.Water.WaterBubble.MustStartAboveWater", false);

			config.addDefault("Abilities.Water.WaterManipulation.Enabled", true);
			config.addDefault("Abilities.Water.WaterManipulation.Damage", 3.0);
			config.addDefault("Abilities.Water.WaterManipulation.Range", 25);
			config.addDefault("Abilities.Water.WaterManipulation.SelectRange", 16);
			config.addDefault("Abilities.Water.WaterManipulation.CollisionRadius", 1.0);
			config.addDefault("Abilities.Water.WaterManipulation.DeflectRange", 3);
			config.addDefault("Abilities.Water.WaterManipulation.Speed", 35);
			config.addDefault("Abilities.Water.WaterManipulation.Knockback", 0.3);
			config.addDefault("Abilities.Water.WaterManipulation.Cooldown", 1000);

			config.addDefault("Abilities.Water.WaterSpout.Enabled", true);
			config.addDefault("Abilities.Water.WaterSpout.Cooldown", 0);
			config.addDefault("Abilities.Water.WaterSpout.Duration", 0);
			config.addDefault("Abilities.Water.WaterSpout.Height", 16);
			config.addDefault("Abilities.Water.WaterSpout.Interval", 50);
			config.addDefault("Abilities.Water.WaterSpout.BlockSpiral", true);
			config.addDefault("Abilities.Water.WaterSpout.Particles", false);
			config.addDefault("Abilities.Water.WaterSpout.SpoutHop.Enabled", true);
			config.addDefault("Abilities.Water.WaterSpout.SpoutHop.Power", 0.85);
			config.addDefault("Abilities.Water.WaterSpout.SpoutHop.Cooldown", 0);

			config.addDefault("Abilities.Water.WaterSpout.Wave.Particles", false);
			config.addDefault("Abilities.Water.WaterSpout.Wave.Enabled", true);
			config.addDefault("Abilities.Water.WaterSpout.Wave.AllowPlantSource", true);
			config.addDefault("Abilities.Water.WaterSpout.Wave.Radius", 3.8);
			config.addDefault("Abilities.Water.WaterSpout.Wave.WaveRadius", 1.5);
			config.addDefault("Abilities.Water.WaterSpout.Wave.SelectRange", 6);
			config.addDefault("Abilities.Water.WaterSpout.Wave.AnimationSpeed", 1.2);
			config.addDefault("Abilities.Water.WaterSpout.Wave.ChargeTime", 500);
			config.addDefault("Abilities.Water.WaterSpout.Wave.FlightDuration", 2500);
			config.addDefault("Abilities.Water.WaterSpout.Wave.Speed", 1.3);
			config.addDefault("Abilities.Water.WaterSpout.Wave.Cooldown", 6000);
			config.addDefault("Abilities.Water.WaterSpout.Wave.TrailRevertTime", 1000);

			config.addDefault("Abilities.Water.IceWave.Enabled", true);
			config.addDefault("Abilities.Water.IceWave.Damage", 3);
			config.addDefault("Abilities.Water.IceWave.Cooldown", 6000);
			config.addDefault("Abilities.Water.IceWave.ThawRadius", 10);
			config.addDefault("Abilities.Water.IceWave.RevertSphere", true);
			config.addDefault("Abilities.Water.IceWave.IceSphereRadius", 2.5);
			config.addDefault("Abilities.Water.IceWave.RevertSphereTime", 30000L);
			config.addDefault("Abilities.Water.IceWave.Combination", Arrays.asList("WaterSpout:SHIFT_UP", "PhaseChange:LEFT_CLICK"));

			config.addDefault("Abilities.Water.IceBullet.Enabled", true);
			config.addDefault("Abilities.Water.IceBullet.Damage", 2);
			config.addDefault("Abilities.Water.IceBullet.Radius", 2.5);
			config.addDefault("Abilities.Water.IceBullet.Range", 12);
			config.addDefault("Abilities.Water.IceBullet.MaxShots", 30);
			config.addDefault("Abilities.Water.IceBullet.AnimationSpeed", 1);
			config.addDefault("Abilities.Water.IceBullet.ShootTime", 10000);
			config.addDefault("Abilities.Water.IceBullet.Cooldown", 10000);
			config.addDefault("Abilities.Water.IceBullet.ShotCooldown", 500);
			config.addDefault("Abilities.Water.IceBullet.Combination", Arrays.asList("WaterBubble:SNEAK_DOWN", "WaterBubble:SNEAK_UP", "IceBlast:SNEAK_DOWN"));

//			config.addDefault("Abilities.Water.PlantTether.Enabled", true);
//			config.addDefault("Abilities.Water.PlantTether.Cooldown", 15000);
//			config.addDefault("Abilities.Water.PlantTether.Damage", 2);
//			config.addDefault("Abilities.Water.PlantTether.SelectRange", 10);

			config.addDefault("Abilities.Earth.Passive.Duration", 2500);
			config.addDefault("Abilities.Earth.Passive.DensityShift.Enabled", true);
			config.addDefault("Abilities.Earth.Passive.FerroControl.Enabled", true);

			config.addDefault("Abilities.Earth.Catapult.Enabled", true);
			config.addDefault("Abilities.Earth.Catapult.Cooldown", 7000);
			config.addDefault("Abilities.Earth.Catapult.StageTimeMult", 2.0);
			config.addDefault("Abilities.Earth.Catapult.Angle", 45);
			config.addDefault("Abilities.Earth.Catapult.CancelWithAngle", false);

			config.addDefault("Abilities.Earth.Collapse.Enabled", true);
			config.addDefault("Abilities.Earth.Collapse.SelectRange", 20);
			config.addDefault("Abilities.Earth.Collapse.Radius", 7);
			config.addDefault("Abilities.Earth.Collapse.Speed", 8);
			config.addDefault("Abilities.Earth.Collapse.Column.Height", 6);
			config.addDefault("Abilities.Earth.Collapse.Column.Cooldown", 500);
			config.addDefault("Abilities.Earth.Collapse.Wall.Height", 6);
			config.addDefault("Abilities.Earth.Collapse.Wall.Cooldown", 500);

			config.addDefault("Abilities.Earth.EarthArmor.Enabled", true);
			config.addDefault("Abilities.Earth.EarthArmor.SelectRange", 10);
			config.addDefault("Abilities.Earth.EarthArmor.GoldHearts", 4);
			config.addDefault("Abilities.Earth.EarthArmor.Cooldown", 7500);
			config.addDefault("Abilities.Earth.EarthArmor.MaxDuration", 17500);

			config.addDefault("Abilities.Earth.EarthBlast.Enabled", true);
			config.addDefault("Abilities.Earth.EarthBlast.CanHitSelf", false);
			config.addDefault("Abilities.Earth.EarthBlast.SelectRange", 10);
			config.addDefault("Abilities.Earth.EarthBlast.Range", 30);
			config.addDefault("Abilities.Earth.EarthBlast.Speed", 35);
			config.addDefault("Abilities.Earth.EarthBlast.Revert", true);
			config.addDefault("Abilities.Earth.EarthBlast.Damage", 3);
			config.addDefault("Abilities.Earth.EarthBlast.Push", 0.3);
			config.addDefault("Abilities.Earth.EarthBlast.Cooldown", 500);
			config.addDefault("Abilities.Earth.EarthBlast.DeflectRange", 3);
			config.addDefault("Abilities.Earth.EarthBlast.CollisionRadius", 1.5);

			config.addDefault("Abilities.Earth.EarthDome.Enabled", true);
			config.addDefault("Abilities.Earth.EarthDome.Cooldown", 10000);
			config.addDefault("Abilities.Earth.EarthDome.Radius", 2);
			config.addDefault("Abilities.Earth.EarthDome.Range", 14);
			config.addDefault("Abilities.Earth.EarthDome.Height", 3);
			config.addDefault("Abilities.Earth.EarthDome.Combination.Self", Arrays.asList("RaiseEarth:RIGHT_CLICK_BLOCK", "Shockwave:RIGHT_CLICK_BLOCK"));
			config.addDefault("Abilities.Earth.EarthDome.Combination.Others", Arrays.asList("RaiseEarth:RIGHT_CLICK_BLOCK", "Shockwave:LEFT_CLICK"));

			config.addDefault("Abilities.Earth.EarthGrab.Enabled", true);
			config.addDefault("Abilities.Earth.EarthGrab.SelectRange", 7);
			config.addDefault("Abilities.Earth.EarthGrab.DragSpeed", 0.8);
			config.addDefault("Abilities.Earth.EarthGrab.Cooldown", 5000);
			config.addDefault("Abilities.Earth.EarthGrab.Range", 14);
			config.addDefault("Abilities.Earth.EarthGrab.TrapHitInterval", 400);
			config.addDefault("Abilities.Earth.EarthGrab.TrapHP", 3);
			config.addDefault("Abilities.Earth.EarthGrab.DamageThreshold", 4);

			config.addDefault("Abilities.Earth.EarthTunnel.Enabled", true);
			config.addDefault("Abilities.Earth.EarthTunnel.RevertCheckTime", 300000);
			config.addDefault("Abilities.Earth.EarthTunnel.Cooldown", 0);
			config.addDefault("Abilities.Earth.EarthTunnel.MaxRadius", 1);
			config.addDefault("Abilities.Earth.EarthTunnel.Range", 10);
			config.addDefault("Abilities.Earth.EarthTunnel.Radius", 0.25);
			config.addDefault("Abilities.Earth.EarthTunnel.Revert", true);
			config.addDefault("Abilities.Earth.EarthTunnel.DropLootIfNotRevert", false);

			final ArrayList<String> earthTunnelIgnored = new ArrayList<String>();
			earthTunnelIgnored.add(Material.COAL_ORE.toString()); // no longer needed in 1.17
			earthTunnelIgnored.add(Material.IRON_ORE.toString()); // no longer needed in 1.17
			earthTunnelIgnored.add(Material.REDSTONE_ORE.toString()); // no longer needed in 1.17
			earthTunnelIgnored.add(Material.LAPIS_ORE.toString()); // no longer needed in 1.17
			earthTunnelIgnored.add(Material.DIAMOND_ORE.toString()); // no longer needed in 1.17
			earthTunnelIgnored.add(Material.EMERALD_ORE.toString()); // no longer needed in 1.17
			earthTunnelIgnored.add("#coal_ores"); // added in 1.17
			earthTunnelIgnored.add("#iron_ores"); // added in 1.17
			earthTunnelIgnored.add("#gold_ores"); // added in 1.16.1
			earthTunnelIgnored.add("#copper_ores"); // added in 1.17
			earthTunnelIgnored.add("#redstone_ores"); // added in 1.17
			earthTunnelIgnored.add("#lapis_ores"); // added in 1.17
			earthTunnelIgnored.add("#diamond_ores"); // added in 1.17
			earthTunnelIgnored.add("#emerald_ores"); // added in 1.17
			earthTunnelIgnored.add(Material.ANCIENT_DEBRIS.toString());
			earthTunnelIgnored.add(Material.GILDED_BLACKSTONE.toString());
			earthTunnelIgnored.add(Material.NETHER_QUARTZ_ORE.toString());

			config.addDefault("Abilities.Earth.EarthTunnel.IgnoredBlocks", earthTunnelIgnored);

			config.addDefault("Abilities.Earth.EarthTunnel.Interval", 30);
			config.addDefault("Abilities.Earth.EarthTunnel.BlocksPerInterval", 1);

			config.addDefault("Abilities.Earth.Extraction.Enabled", true);
			config.addDefault("Abilities.Earth.Extraction.SelectRange", 5);
			config.addDefault("Abilities.Earth.Extraction.Cooldown", 500);
			config.addDefault("Abilities.Earth.Extraction.TripleLootChance", 10);
			config.addDefault("Abilities.Earth.Extraction.DoubleLootChance", 30);
			config.addDefault("Abilities.Earth.Extraction.IronGolem.Drops", 1);
			config.addDefault("Abilities.Earth.Extraction.IronGolem.Damage", 4);

			config.addDefault("Abilities.Earth.LavaFlow.Enabled", true);
			config.addDefault("Abilities.Earth.LavaFlow.ShiftCooldown", 20000);
			config.addDefault("Abilities.Earth.LavaFlow.ClickLavaCooldown", 10000);
			config.addDefault("Abilities.Earth.LavaFlow.ClickLandCooldown", 500);
			config.addDefault("Abilities.Earth.LavaFlow.ShiftCleanupDelay", 10000);
			config.addDefault("Abilities.Earth.LavaFlow.ClickLavaCleanupDelay", 7000);
			config.addDefault("Abilities.Earth.LavaFlow.ClickLandCleanupDelay", 20000);
			config.addDefault("Abilities.Earth.LavaFlow.ClickRange", 10.0);
			config.addDefault("Abilities.Earth.LavaFlow.ShiftRadius", 7.0);
			config.addDefault("Abilities.Earth.LavaFlow.ShiftPlatformRadius", 1.5);
			config.addDefault("Abilities.Earth.LavaFlow.ClickRadius", 5.0);
			config.addDefault("Abilities.Earth.LavaFlow.ClickLavaCreateSpeed", 0.045);
			config.addDefault("Abilities.Earth.LavaFlow.ClickLandCreateSpeed", 0.10);
			config.addDefault("Abilities.Earth.LavaFlow.ShiftFlowSpeed", 0.01);
			config.addDefault("Abilities.Earth.LavaFlow.ShiftRemoveSpeed", 3.0);
			config.addDefault("Abilities.Earth.LavaFlow.ClickLavaStartDelay", 1500);
			config.addDefault("Abilities.Earth.LavaFlow.UpwardFlow", 2);
			config.addDefault("Abilities.Earth.LavaFlow.DownwardFlow", 4);
			config.addDefault("Abilities.Earth.LavaFlow.AllowNaturalFlow", false);
			config.addDefault("Abilities.Earth.LavaFlow.ParticleDensity", 0.11);
			config.addDefault("Abilities.Earth.LavaFlow.RevertMaterial", "STONE");

			config.addDefault("Abilities.Earth.EarthSmash.Enabled", true);
			config.addDefault("Abilities.Earth.EarthSmash.Duration", 30000);
			config.addDefault("Abilities.Earth.EarthSmash.SelectRange", 12);
			config.addDefault("Abilities.Earth.EarthSmash.ChargeTime", 1500);
			config.addDefault("Abilities.Earth.EarthSmash.Cooldown", 3000);
			config.addDefault("Abilities.Earth.EarthSmash.MinimumDamage", 2);
			config.addDefault("Abilities.Earth.EarthSmash.MaximumDamage", 5);
			config.addDefault("Abilities.Earth.EarthSmash.Knockback", 3.5);
			config.addDefault("Abilities.Earth.EarthSmash.Knockup", 0.15);
			config.addDefault("Abilities.Earth.EarthSmash.Lift.Knockup", 1.1);
			config.addDefault("Abilities.Earth.EarthSmash.Lift.Range", 3.5);
			config.addDefault("Abilities.Earth.EarthSmash.Flight.Enabled", true);
			config.addDefault("Abilities.Earth.EarthSmash.Flight.Speed", 0.72);
			config.addDefault("Abilities.Earth.EarthSmash.Flight.Duration", 3000);
			config.addDefault("Abilities.Earth.EarthSmash.Flight.AnimationInterval", 0);
			config.addDefault("Abilities.Earth.EarthSmash.Flight.DetectionRadius", 3.5);
			config.addDefault("Abilities.Earth.EarthSmash.Grab.Enabled", true);
			config.addDefault("Abilities.Earth.EarthSmash.Grab.Range", 16);
			config.addDefault("Abilities.Earth.EarthSmash.Grab.DetectionRadius", 2.5);
			config.addDefault("Abilities.Earth.EarthSmash.Shoot.Range", 25);
			config.addDefault("Abilities.Earth.EarthSmash.Shoot.AnimationInterval", 25);
			config.addDefault("Abilities.Earth.EarthSmash.Shoot.CollisionRadius", 2.5);
			config.addDefault("Abilities.Earth.EarthSmash.RequiredBendableBlocks", 11);
			config.addDefault("Abilities.Earth.EarthSmash.MaxBlocksToPassThrough", 3);
			config.addDefault("Abilities.Earth.EarthSmash.LiftAnimationInterval", 30);

			config.addDefault("Abilities.Earth.MetalClips.Enabled", true);
			config.addDefault("Abilities.Earth.MetalClips.Damage", 2);
			config.addDefault("Abilities.Earth.MetalClips.Range", 10);
			config.addDefault("Abilities.Earth.MetalClips.Cooldown", 6000);
			config.addDefault("Abilities.Earth.MetalClips.Duration", 10000);
			config.addDefault("Abilities.Earth.MetalClips.Shoot.Cooldown", 600);
			config.addDefault("Abilities.Earth.MetalClips.Shoot.Speed", 1.2);
			config.addDefault("Abilities.Earth.MetalClips.Magnet.Range", 20);
			config.addDefault("Abilities.Earth.MetalClips.Magnet.Speed", 0.6);
			config.addDefault("Abilities.Earth.MetalClips.Magnet.Cooldown", 1000);
			config.addDefault("Abilities.Earth.MetalClips.Crush.Damage", 1);
			config.addDefault("Abilities.Earth.MetalClips.Crush.Cooldown", 2000);
			config.addDefault("Abilities.Earth.MetalClips.ThrowEnabled", true);

			config.addDefault("Abilities.Earth.RaiseEarth.Enabled", true);
			config.addDefault("Abilities.Earth.RaiseEarth.Speed", 10);
			config.addDefault("Abilities.Earth.RaiseEarth.Column.SelectRange", 20);
			config.addDefault("Abilities.Earth.RaiseEarth.Column.Height", 6);
			config.addDefault("Abilities.Earth.RaiseEarth.Column.Cooldown", 500);
			config.addDefault("Abilities.Earth.RaiseEarth.Wall.SelectRange", 20);
			config.addDefault("Abilities.Earth.RaiseEarth.Wall.Height", 6);
			config.addDefault("Abilities.Earth.RaiseEarth.Wall.Width", 6);
			config.addDefault("Abilities.Earth.RaiseEarth.Wall.Cooldown", 500);

			config.addDefault("Abilities.Earth.Shockwave.Enabled", true);
			config.addDefault("Abilities.Earth.Shockwave.FallThreshold", 12);
			config.addDefault("Abilities.Earth.Shockwave.ChargeTime", 2500);
			config.addDefault("Abilities.Earth.Shockwave.Cooldown", 6000);
			config.addDefault("Abilities.Earth.Shockwave.Damage", 4);
			config.addDefault("Abilities.Earth.Shockwave.Knockback", 1.1);
			config.addDefault("Abilities.Earth.Shockwave.Range", 15);
			config.addDefault("Abilities.Earth.Shockwave.Angle", 40);

			config.addDefault("Abilities.Earth.Tremorsense.Enabled", true);
			config.addDefault("Abilities.Earth.Tremorsense.MaxDepth", 10);
			config.addDefault("Abilities.Earth.Tremorsense.Radius", 5);
			config.addDefault("Abilities.Earth.Tremorsense.LightThreshold", 7);
			config.addDefault("Abilities.Earth.Tremorsense.Cooldown", 1000);
			config.addDefault("Abilities.Earth.Tremorsense.StickyRange", 3);

			config.addDefault("Abilities.Earth.EarthPillars.Enabled", true);
			config.addDefault("Abilities.Earth.EarthPillars.Cooldown", 8000);
			config.addDefault("Abilities.Earth.EarthPillars.Radius", 9);
			config.addDefault("Abilities.Earth.EarthPillars.Knockup", 1.2);
			config.addDefault("Abilities.Earth.EarthPillars.Damage.Enabled", true);
			config.addDefault("Abilities.Earth.EarthPillars.Damage.Value", 2);
			config.addDefault("Abilities.Earth.EarthPillars.FallThreshold", 12);
			config.addDefault("Abilities.Earth.EarthPillars.Combination", Arrays.asList("Shockwave:SNEAK_DOWN", "Shockwave:SNEAK_UP", "Shockwave:SNEAK_DOWN", "Catapult:SNEAK_UP"));

			config.addDefault("Abilities.Fire.Blaze.Enabled", true);
			config.addDefault("Abilities.Fire.Blaze.Arc", 14);
			config.addDefault("Abilities.Fire.Blaze.Range", 7);
			config.addDefault("Abilities.Fire.Blaze.Speed", 15);
			config.addDefault("Abilities.Fire.Blaze.Cooldown", 500);
			config.addDefault("Abilities.Fire.Blaze.Ring.Range", 7);
			config.addDefault("Abilities.Fire.Blaze.Ring.Angle", 10);
			config.addDefault("Abilities.Fire.Blaze.Ring.Cooldown", 1000);

			config.addDefault("Abilities.Fire.Combustion.Enabled", true);
			config.addDefault("Abilities.Fire.Combustion.Cooldown", 10000);
			config.addDefault("Abilities.Fire.Combustion.BreakBlocks", false);
			config.addDefault("Abilities.Fire.Combustion.ExplosivePower", 2.0);
			config.addDefault("Abilities.Fire.Combustion.Damage", 4);
			config.addDefault("Abilities.Fire.Combustion.Radius", 4);
			config.addDefault("Abilities.Fire.Combustion.Range", 35);
			config.addDefault("Abilities.Fire.Combustion.Speed", 25);

			config.addDefault("Abilities.Fire.FireBlast.Enabled", true);
			config.addDefault("Abilities.Fire.FireBlast.Speed", 20);
			config.addDefault("Abilities.Fire.FireBlast.Range", 20);
			config.addDefault("Abilities.Fire.FireBlast.CollisionRadius", 1.0);
			config.addDefault("Abilities.Fire.FireBlast.Knockback", 0.3);
			config.addDefault("Abilities.Fire.FireBlast.Damage", 3);
			config.addDefault("Abilities.Fire.FireBlast.Cooldown", 1500);
			config.addDefault("Abilities.Fire.FireBlast.Dissipate", false);
			config.addDefault("Abilities.Fire.FireBlast.FireTicks", 0);
			config.addDefault("Abilities.Fire.FireBlast.SmokeParticleRadius", 0.3);
			config.addDefault("Abilities.Fire.FireBlast.FlameParticleRadius", 0.275);
			config.addDefault("Abilities.Fire.FireBlast.Charged.ChargeTime", 3000);
			config.addDefault("Abilities.Fire.FireBlast.Charged.Cooldown", 2000);
			config.addDefault("Abilities.Fire.FireBlast.Charged.CollisionRadius", 2);
			config.addDefault("Abilities.Fire.FireBlast.Charged.MinimumDamage", 2);
			config.addDefault("Abilities.Fire.FireBlast.Charged.MaximumDamage", 4);
			config.addDefault("Abilities.Fire.FireBlast.Charged.DamageRadius", 4);
			config.addDefault("Abilities.Fire.FireBlast.Charged.DamageBlocks", true);
			config.addDefault("Abilities.Fire.FireBlast.Charged.ExplosionRadius", 1);
			config.addDefault("Abilities.Fire.FireBlast.Charged.Range", 20);
			config.addDefault("Abilities.Fire.FireBlast.Charged.FireTicks", 0);

			config.addDefault("Abilities.Fire.FireBurst.Enabled", true);
			config.addDefault("Abilities.Fire.FireBurst.Damage", 2);
			config.addDefault("Abilities.Fire.FireBurst.Ignite", true);
			config.addDefault("Abilities.Fire.FireBurst.ChargeTime", 3500);
			config.addDefault("Abilities.Fire.FireBurst.Cooldown", 0);
			config.addDefault("Abilities.Fire.FireBurst.Range", 14);
			config.addDefault("Abilities.Fire.FireBurst.AnglePhi", 10);
			config.addDefault("Abilities.Fire.FireBurst.AngleTheta", 10);
			config.addDefault("Abilities.Fire.FireBurst.ParticlesPercentage", 5);

			config.addDefault("Abilities.Fire.FireJet.Enabled", true);
			config.addDefault("Abilities.Fire.FireJet.Speed", 0.8);
			config.addDefault("Abilities.Fire.FireJet.Duration", 2000);
			config.addDefault("Abilities.Fire.FireJet.Cooldown", 7000);
			config.addDefault("Abilities.Fire.FireJet.ShowGliding", false);

			config.addDefault("Abilities.Fire.FireManipulation.Enabled", false);

			config.addDefault("Abilities.Fire.FireManipulation.Stream.Cooldown", 12000);
			config.addDefault("Abilities.Fire.FireManipulation.Stream.Range", 50);
			config.addDefault("Abilities.Fire.FireManipulation.Stream.Damage", 2);
			config.addDefault("Abilities.Fire.FireManipulation.Stream.Speed", 0.75);
			config.addDefault("Abilities.Fire.FireManipulation.Stream.Particles", 50);

			config.addDefault("Abilities.Fire.FireManipulation.Shield.Cooldown", 6000);
			config.addDefault("Abilities.Fire.FireManipulation.Shield.Range", 4);
			config.addDefault("Abilities.Fire.FireManipulation.Shield.Damage", 1);
			config.addDefault("Abilities.Fire.FireManipulation.Shield.MaxDuration", 5000L);
			config.addDefault("Abilities.Fire.FireManipulation.Shield.Particles", 12);

			config.addDefault("Abilities.Fire.FireShield.Enabled", true);
			config.addDefault("Abilities.Fire.FireShield.Shield.Radius", 3);
			config.addDefault("Abilities.Fire.FireShield.Shield.Duration", 0);
			config.addDefault("Abilities.Fire.FireShield.Shield.Cooldown", 0);
			config.addDefault("Abilities.Fire.FireShield.Disc.Radius", 1.5);
			config.addDefault("Abilities.Fire.FireShield.Disc.Duration", 1000);
			config.addDefault("Abilities.Fire.FireShield.Disc.Cooldown", 500);
			config.addDefault("Abilities.Fire.FireShield.Shield.FireTicks", 2);
			config.addDefault("Abilities.Fire.FireShield.Disc.FireTicks", 2);

			config.addDefault("Abilities.Fire.HeatControl.Enabled", true);
			config.addDefault("Abilities.Fire.HeatControl.Cook.Interval", 1000);
			config.addDefault("Abilities.Fire.HeatControl.Extinguish.Cooldown", 5000);
			config.addDefault("Abilities.Fire.HeatControl.Extinguish.Radius", 6);
			config.addDefault("Abilities.Fire.HeatControl.Melt.Range", 15);
			config.addDefault("Abilities.Fire.HeatControl.Melt.Radius", 5);
			config.addDefault("Abilities.Fire.HeatControl.Solidify.MaxRadius", 10);
			config.addDefault("Abilities.Fire.HeatControl.Solidify.Range", 7);
			config.addDefault("Abilities.Fire.HeatControl.Solidify.Revert", true);
			config.addDefault("Abilities.Fire.HeatControl.Solidify.RevertTime", 120000);

			config.addDefault("Abilities.Fire.Illumination.Enabled", true);
			config.addDefault("Abilities.Fire.Illumination.Passive", true);
			config.addDefault("Abilities.Fire.Illumination.Range", 5);
			config.addDefault("Abilities.Fire.Illumination.Cooldown", 500);
			config.addDefault("Abilities.Fire.Illumination.LightThreshold", 7);
			if (mcVersion >= 1170) { //Only add this for 1.17 and above
				config.addDefault("Abilities.Fire.Illumination.LightLevel", 13);
			}

			config.addDefault("Abilities.Fire.Lightning.Enabled", true);
			config.addDefault("Abilities.Fire.Lightning.Damage", 4.0);
			config.addDefault("Abilities.Fire.Lightning.Range", 20.0);
			config.addDefault("Abilities.Fire.Lightning.ChargeTime", 2500);
			config.addDefault("Abilities.Fire.Lightning.Cooldown", 500);
			config.addDefault("Abilities.Fire.Lightning.StunChance", 0.20);
			config.addDefault("Abilities.Fire.Lightning.StunDuration", 30.0);
			config.addDefault("Abilities.Fire.Lightning.MaxArcAngle", 2.5);
			config.addDefault("Abilities.Fire.Lightning.SubArcChance", 0.00125);
			config.addDefault("Abilities.Fire.Lightning.ChainArcRange", 6.0);
			config.addDefault("Abilities.Fire.Lightning.ChainArcChance", 0.50);
			config.addDefault("Abilities.Fire.Lightning.MaxChainArcs", 2);
			config.addDefault("Abilities.Fire.Lightning.WaterArcs", 4);
			config.addDefault("Abilities.Fire.Lightning.WaterArcRange", 20.0);
			config.addDefault("Abilities.Fire.Lightning.ConductivityRange", 5.0);
			config.addDefault("Abilities.Fire.Lightning.MaxCopperArcs", 8);
			config.addDefault("Abilities.Fire.Lightning.SelfHitWater", true);
			config.addDefault("Abilities.Fire.Lightning.SelfHitClose", false);
			config.addDefault("Abilities.Fire.Lightning.ArcOnIce", false);
			config.addDefault("Abilities.Fire.Lightning.ArcOnCopper", true);
			config.addDefault("Abilities.Fire.Lightning.AllowOnFireJet", true);
			config.addDefault("Abilities.Fire.Lightning.TransformMobs", true);
			config.addDefault("Abilities.Fire.Lightning.ChargeCreeper", true);
			config.addDefault("Abilities.Fire.Lightning.ChainLightningRods", true);

			config.addDefault("Abilities.Fire.WallOfFire.Enabled", true);
			config.addDefault("Abilities.Fire.WallOfFire.Range", 3);
			config.addDefault("Abilities.Fire.WallOfFire.Height", 4);
			config.addDefault("Abilities.Fire.WallOfFire.Width", 4);
			config.addDefault("Abilities.Fire.WallOfFire.Duration", 5000);
			config.addDefault("Abilities.Fire.WallOfFire.Damage", 1);
			config.addDefault("Abilities.Fire.WallOfFire.Cooldown", 11000);
			config.addDefault("Abilities.Fire.WallOfFire.Interval", 250);
			config.addDefault("Abilities.Fire.WallOfFire.DamageInterval", 500);
			config.addDefault("Abilities.Fire.WallOfFire.FireTicks", 0);
			config.addDefault("Abilities.Fire.WallOfFire.MaxAngle", 50);

			config.addDefault("Abilities.Fire.FireKick.Enabled", true);
			config.addDefault("Abilities.Fire.FireKick.Range", 7.0);
			config.addDefault("Abilities.Fire.FireKick.Damage", 3.0);
			config.addDefault("Abilities.Fire.FireKick.Cooldown", 6000);
			config.addDefault("Abilities.Fire.FireKick.Speed", 1);
			config.addDefault("Abilities.Fire.FireKick.Combination", Arrays.asList("FireBlast:LEFT_CLICK", "FireBlast:LEFT_CLICK", "FireBlast:SNEAK_DOWN", "FireBlast:LEFT_CLICK"));

			config.addDefault("Abilities.Fire.FireSpin.Enabled", true);
			config.addDefault("Abilities.Fire.FireSpin.Range", 7);
			config.addDefault("Abilities.Fire.FireSpin.Damage", 3.0);
			config.addDefault("Abilities.Fire.FireSpin.Knockback", 3.0);
			config.addDefault("Abilities.Fire.FireSpin.Cooldown", 5000);
			config.addDefault("Abilities.Fire.FireSpin.Speed", 0.3);
			config.addDefault("Abilities.Fire.FireSpin.Combination", Arrays.asList("FireBlast:LEFT_CLICK", "FireBlast:LEFT_CLICK", "FireShield:LEFT_CLICK", "FireShield:SNEAK_DOWN", "FireShield:SNEAK_UP"));

			config.addDefault("Abilities.Fire.FireWheel.Enabled", true);
			config.addDefault("Abilities.Fire.FireWheel.HitboxMultiplier",0.2);
			config.addDefault("Abilities.Fire.FireWheel.Range", 20.0);
			config.addDefault("Abilities.Fire.FireWheel.Damage", 4.0);
			config.addDefault("Abilities.Fire.FireWheel.Speed", 0.55);
			config.addDefault("Abilities.Fire.FireWheel.Cooldown", 6000);
			config.addDefault("Abilities.Fire.FireWheel.FireTicks", 2.5);
			config.addDefault("Abilities.Fire.FireWheel.Height", 2);
			config.addDefault("Abilities.Fire.FireWheel.Combination", Arrays.asList("FireShield:SNEAK_DOWN", "FireShield:RIGHT_CLICK_BLOCK", "FireShield:RIGHT_CLICK_BLOCK", "Blaze:SNEAK_UP"));

			config.addDefault("Abilities.Fire.JetBlast.Enabled", true);
			config.addDefault("Abilities.Fire.JetBlast.Speed", 1.2);
			config.addDefault("Abilities.Fire.JetBlast.Cooldown", 6000);
			config.addDefault("Abilities.Fire.JetBlast.Duration", 5000);
			config.addDefault("Abilities.Fire.JetBlast.Combination", Arrays.asList("FireJet:SNEAK_DOWN", "FireJet:SNEAK_UP", "FireJet:SNEAK_DOWN", "FireJet:SNEAK_UP", "FireShield:SNEAK_DOWN", "FireShield:SNEAK_UP", "FireJet:LEFT_CLICK"));

			config.addDefault("Abilities.Fire.JetBlaze.Enabled", true);
			config.addDefault("Abilities.Fire.JetBlaze.Speed", 1.1);
			config.addDefault("Abilities.Fire.JetBlaze.Damage", 4);
			config.addDefault("Abilities.Fire.JetBlaze.Cooldown", 6000);
			config.addDefault("Abilities.Fire.JetBlaze.FireTicks", 2.5);
			config.addDefault("Abilities.Fire.JetBlaze.Duration", 5000);
			config.addDefault("Abilities.Fire.JetBlaze.Combination", Arrays.asList("FireJet:SNEAK_DOWN", "FireJet:SNEAK_UP", "FireJet:SNEAK_DOWN", "FireJet:SNEAK_UP", "Blaze:SNEAK_DOWN", "Blaze:SNEAK_UP", "FireJet:LEFT_CLICK"));

			config.addDefault("Abilities.Chi.Passive.Acrobatics.Enabled", true);
			config.addDefault("Abilities.Chi.Passive.Acrobatics.FallReductionFactor", 0.5);
			config.addDefault("Abilities.Chi.Passive.FallReductionFactor", 0.5);
			config.addDefault("Abilities.Chi.Passive.ChiAgility.Enabled", true);
			config.addDefault("Abilities.Chi.Passive.ChiAgility.JumpPower", 1);
			config.addDefault("Abilities.Chi.Passive.ChiAgility.SpeedPower", 1);
			config.addDefault("Abilities.Chi.Passive.ChiSaturation.Enabled", true);
			config.addDefault("Abilities.Chi.Passive.ChiSaturation.ExhaustionFactor", 0.3);
			config.addDefault("Abilities.Chi.Passive.BlockChi.Chance", 25);
			config.addDefault("Abilities.Chi.Passive.BlockChi.Duration", 1000);

			config.addDefault("Abilities.Chi.Immobilize.Enabled", true);
			config.addDefault("Abilities.Chi.Immobilize.ParalyzeDuration", 3500);
			config.addDefault("Abilities.Chi.Immobilize.Cooldown", 15000);
			config.addDefault("Abilities.Chi.Immobilize.Combination", Arrays.asList("QuickStrike:LEFT_CLICK", "SwiftKick:LEFT_CLICK", "QuickStrike:LEFT_CLICK", "QuickStrike:LEFT_CLICK_ENTITY"));

			config.addDefault("Abilities.Chi.AcrobatStance.Enabled", true);
			config.addDefault("Abilities.Chi.AcrobatStance.Cooldown", 0);
			config.addDefault("Abilities.Chi.AcrobatStance.Duration", 0);
			config.addDefault("Abilities.Chi.AcrobatStance.ChiBlockBoost", 3);
			config.addDefault("Abilities.Chi.AcrobatStance.Speed", 3);
			config.addDefault("Abilities.Chi.AcrobatStance.Jump", 3);

			config.addDefault("Abilities.Chi.HighJump.Enabled", true);
			config.addDefault("Abilities.Chi.HighJump.Height", 1.3);
			config.addDefault("Abilities.Chi.HighJump.Cooldown", 3000);

			config.addDefault("Abilities.Chi.Paralyze.Enabled", true);
			config.addDefault("Abilities.Chi.Paralyze.Cooldown", 10000);
			config.addDefault("Abilities.Chi.Paralyze.Duration", 1500);
			config.addDefault("Abilities.Chi.Paralyze.EntityDamageThreshold", 4);

			config.addDefault("Abilities.Chi.RapidPunch.Enabled", true);
			config.addDefault("Abilities.Chi.RapidPunch.Damage", 1);
			config.addDefault("Abilities.Chi.RapidPunch.Cooldown", 6000);
			config.addDefault("Abilities.Chi.RapidPunch.Punches", 3);
			config.addDefault("Abilities.Chi.RapidPunch.Interval", 500);

			config.addDefault("Abilities.Chi.Smokescreen.Enabled", true);
			config.addDefault("Abilities.Chi.Smokescreen.Cooldown", 25000);
			config.addDefault("Abilities.Chi.Smokescreen.Radius", 4);
			config.addDefault("Abilities.Chi.Smokescreen.Duration", 12);

			config.addDefault("Abilities.Chi.WarriorStance.Enabled", true);
			config.addDefault("Abilities.Chi.WarriorStance.Cooldown", 0);
			config.addDefault("Abilities.Chi.WarriorStance.Duration", 0);
			config.addDefault("Abilities.Chi.WarriorStance.Strength", 1);
			config.addDefault("Abilities.Chi.WarriorStance.Resistance", -3);

			config.addDefault("Abilities.Chi.QuickStrike.Enabled", true);
			config.addDefault("Abilities.Chi.QuickStrike.Damage", 2);
			config.addDefault("Abilities.Chi.QuickStrike.Cooldown", 3000);
			config.addDefault("Abilities.Chi.QuickStrike.ChiBlockChance", 10);

			config.addDefault("Abilities.Chi.SwiftKick.Enabled", true);
			config.addDefault("Abilities.Chi.SwiftKick.Damage", 2);
			config.addDefault("Abilities.Chi.SwiftKick.ChiBlockChance", 15);
			config.addDefault("Abilities.Chi.SwiftKick.Cooldown", 4000);

			config.addDefault("Storage.engine", "sqlite");

			config.addDefault("Storage.MySQL.host", "localhost");
			config.addDefault("Storage.MySQL.port", 3306);
			config.addDefault("Storage.MySQL.pass", "");
			config.addDefault("Storage.MySQL.db", "minecraft");
			config.addDefault("Storage.MySQL.user", "root");
			config.addDefault("Storage.MySQL.properties", "autoReconnect=true");

			config.addDefault("debug", false);

			defaultConfig.save();
		} else if (type == ConfigType.AVATAR_STATE) {
			config = avatarStateConfig.get();

			config.addDefault("AvatarState.Enabled", true);
			config.addDefault("AvatarState.Cooldown", 7200000);
			config.addDefault("AvatarState.Duration", 480000);
			config.addDefault("AvatarState.PlaySound", true);
			config.addDefault("AvatarState.ShowParticles", true);
			config.addDefault("AvatarState.GlowEnabled", false);
			config.addDefault("AvatarState.Sound.Sound", "BLOCK_BEACON_ACTIVATE");
			config.addDefault("AvatarState.Sound.Volume", 1);
			config.addDefault("AvatarState.Sound.Pitch", 1.5);
			config.addDefault("AvatarState.CanBeChiblocked", false);

			config.addDefault("LowHealth.Enabled", true);
			config.addDefault("LowHealth.Threshold", 4);
			config.addDefault("LowHealth.BoostHealth.Enabled", true);
			config.addDefault("LowHealth.BoostHealth.Amount", 2);
			config.addDefault("LowHealth.BoostHealth.YellowHearts", false);
			config.addDefault("LowHealth.PreventDeath", false);

			//Because the effects are "keys", they are always added back if removed.
			//We also check "Abilities" instead of PotionEffects in case users remove the entire section
			if (!config.contains("Abilities")) {
				config.addDefault("PotionEffects.Regeneration", 4);
				config.addDefault("PotionEffects.Speed", 3);
				config.addDefault("PotionEffects.Resistance", 3);
				config.addDefault("PotionEffects.Fire_Resistance", 3);
			}

			config.addDefault("Abilities._All.Damage", "x2.0");
			config.addDefault("Abilities._All.Cooldown", "x0.5");
			config.addDefault("Abilities._All.ChargeTime", "x0.5");
			config.addDefault("Abilities._All.Duration", "x2.0");
			config.addDefault("Abilities._All.Range", "x2.0");
			config.addDefault("Abilities._All.SelectRange", "x3.0");

			config.addDefault("Abilities.Air._All.Knockback", "x1.5");
			config.addDefault("Abilities.Air._All.Range", "x1.5");
			config.addDefault("Abilities.Air._All.Speed", "x1.2");
			config.addDefault("Abilities.Air._All.Cooldown", "x0.5");
			config.addDefault("Abilities.Air._All.SelectRange", "x3.0");
			config.addDefault("Abilities.Air.AirBlast.Knockback", "x2.5");
			config.addDefault("Abilities.Air.AirBlast.SelfPush", "x2.0");
			config.addDefault("Abilities.Air.AirBlast.Cooldown", 200);
			config.addDefault("Abilities.Air.AirSpout.Height", 40);
			config.addDefault("Abilities.Air.AirSuction.Push", 3.5);
			config.addDefault("Abilities.Air.AirSwipe.Cooldown", 1000);
			config.addDefault("Abilities.Air.AirSwipe.Damage", "x2.0");
			config.addDefault("Abilities.Air.AirSwipe.Knockback", "x2.0");
			config.addDefault("Abilities.Air.AirSwipe.Range", "x1.6");
			config.addDefault("Abilities.Air.AirSwipe.Radius", "x0.5");
			config.addDefault("Abilities.Air.AirBurst.ChargeTime", 1000);
			config.addDefault("Abilities.Air.AirBurst.Damage", 3);
			config.addDefault("Abilities.Air.AirShield.IsToggle", true);
			config.addDefault("Abilities.Air.AirShield.Knockback", 2.5);
			config.addDefault("Abilities.Air.Suffocate.Cooldown", 0);
			config.addDefault("Abilities.Air.Suffocate.ChargeTime", 1000);
			config.addDefault("Abilities.Air.Suffocate.Damage", 3);
			config.addDefault("Abilities.Air.Suffocate.Range", 16);
			config.addDefault("Abilities.Air.AirStream.Range", 60);
			config.addDefault("Abilities.Air.AirStream.EntityCarryHeight", "x1.5");
			config.addDefault("Abilities.Air.AirStream.EntityCarryDuration", 20000);
			config.addDefault("Abilities.Air.AirStream.Cooldown", 0);
			config.addDefault("Abilities.Air.AirSweep.Damage", "x2.0");
			config.addDefault("Abilities.Air.AirSweep.Cooldown", 0);
			config.addDefault("Abilities.Air.AirSweep.Range", 21);
			config.addDefault("Abilities.Air.AirSweep.Knockback", 4);
			config.addDefault("Abilities.Air.Twister.Range", "x1.5");
			config.addDefault("Abilities.Air.Twister.Height", "+6");
			config.addDefault("Abilities.Air.Twister.Cooldown", 0);

			config.addDefault("Abilities.Earth._All.Width", "x4.0");
			config.addDefault("Abilities.Earth._All.Height", "x2.0");
			config.addDefault("Abilities.Earth._All.SelectRange", "x5.0");
			config.addDefault("Abilities.Earth._All.Damage", "x2.0");
			config.addDefault("Abilities.Earth._All.Speed", "x1.5");
			config.addDefault("Abilities.Earth._All.Range", "x1.5");
			config.addDefault("Abilities.Earth.Catapult.MaxDistance", 80);
			config.addDefault("Abilities.Earth.Catapult.Cooldown", 0);
			config.addDefault("Abilities.Earth.LavaFlow.ShiftCooldown", 1500);
			config.addDefault("Abilities.Earth.LavaFlow.ClickLavaCooldown", 1500);
			config.addDefault("Abilities.Earth.LavaFlow.ClickSpeed", "x2.5");
			config.addDefault("Abilities.Earth.LavaFlow.ShiftSpeed", "x2.0");
			config.addDefault("Abilities.Earth.LavaFlow.ClickLavaStartDelay", 500);
			config.addDefault("Abilities.Earth.LavaFlow.ClickRange", "x2.0");
			config.addDefault("Abilities.Earth.LavaFlow.ShiftPlatformRadius", 2);
			config.addDefault("Abilities.Earth.LavaFlow.ClickRadius", 12);
			config.addDefault("Abilities.Earth.LavaFlow.ShiftRadius", 20);
			config.addDefault("Abilities.Earth.MetalClips.Cooldown", 2000);
			config.addDefault("Abilities.Earth.MetalClips.Range", "x3.0");
			config.addDefault("Abilities.Earth.MetalClips.CrushDamage", "x2.5");
			config.addDefault("Abilities.Earth.RaiseEarth.Height", "x2.0");
			config.addDefault("Abilities.Earth.RaiseEarth.Width", "x4.0");
			config.addDefault("Abilities.Earth.RaiseEarth.Speed", "x0.8");
			config.addDefault("Abilities.Earth.Collapse.Height", "x2.0");
			config.addDefault("Abilities.Earth.Collapse.Speed", "x0.8");
			config.addDefault("Abilities.Earth.Collapse.Radius", 15);
			config.addDefault("Abilities.Earth.EarthArmor.Cooldown", 2000);
			config.addDefault("Abilities.Earth.EarthArmor.GoldHearts", 6);
			config.addDefault("Abilities.Earth.EarthBlast.Cooldown", 200);
			config.addDefault("Abilities.Earth.EarthBlast.Damage", "x2.0");
			config.addDefault("Abilities.Earth.EarthGrab.Cooldown", 0);
			config.addDefault("Abilities.Earth.EarthGrab.Height", 6);
			config.addDefault("Abilities.Earth.EarthGrab.Radius", 6);
			config.addDefault("Abilities.Earth.Shockwave.Range", "x1.5");
			config.addDefault("Abilities.Earth.Shockwave.Cooldown", 0);
			config.addDefault("Abilities.Earth.Shockwave.ChargeTime", 500);
			config.addDefault("Abilities.Earth.Shockwave.Damage", 5);
			config.addDefault("Abilities.Earth.Shockwave.Knockback", "x2.0");
			config.addDefault("Abilities.Earth.EarthSmash.SelectRange", 16);
			config.addDefault("Abilities.Earth.EarthSmash.GrabRange", 16);
			config.addDefault("Abilities.Earth.EarthSmash.ChargeTime", 1000);
			config.addDefault("Abilities.Earth.EarthSmash.Cooldown", 0);
			config.addDefault("Abilities.Earth.EarthSmash.MinimumDamage", 3);
			config.addDefault("Abilities.Earth.EarthSmash.MaximumDamage", 7);
			config.addDefault("Abilities.Earth.EarthSmash.Knockback", "x1.5");
			config.addDefault("Abilities.Earth.EarthSmash.FlightSpeed", 1.0);
			config.addDefault("Abilities.Earth.EarthSmash.FlightTimer", "+6000");
			config.addDefault("Abilities.Earth.EarthSmash.ShootRange", 45);
			config.addDefault("Abilities.Earth.EarthTunnel.Radius", "x3.0");
			config.addDefault("Abilities.Earth.EarthTunnel.Speed", "x10.0");
			config.addDefault("Abilities.Earth.EarthTunnel.Range", "x2.0");

			config.addDefault("Abilities.Fire._All.Damage", "x2.0");
			config.addDefault("Abilities.Fire._All.Cooldown", "x0.5");
			config.addDefault("Abilities.Fire._All.ChargeTime", "x0.5");
			config.addDefault("Abilities.Fire._All.Duration", "x2.0");
			config.addDefault("Abilities.Fire._All.Range", "x2.0");
			config.addDefault("Abilities.Fire._All.Speed", "x1.5");
			config.addDefault("Abilities.Fire.FireJet.IsToggle", true);
			config.addDefault("Abilities.Fire.Blaze.Arc", "x1.8");
			config.addDefault("Abilities.Fire.FireShield.ShieldRadius", "x3.0");
			config.addDefault("Abilities.Fire.FireShield.DiscRadius", "x3.0");
			config.addDefault("Abilities.Fire.FireShield.ShieldCooldown", 0);
			config.addDefault("Abilities.Fire.FireShield.DiscCooldown", 500);
			config.addDefault("Abilities.Fire.FireShield.ShieldDuration", "x2.5");
			config.addDefault("Abilities.Fire.FireShield.DiscDuration", "x2.5");
			config.addDefault("Abilities.Fire.JetBlast.Cooldown", 0);
			config.addDefault("Abilities.Fire.JetBlast.Speed", "x1.5");
			config.addDefault("Abilities.Fire.JetBlaze.Cooldown", 0);
			config.addDefault("Abilities.Fire.JetBlaze.Speed", "x1.5");
			config.addDefault("Abilities.Fire.Lightning.ChargeTime", 500);
			config.addDefault("Abilities.Fire.Lightning.Damage", "x2.0");
			config.addDefault("Abilities.Fire.Lightning.MaxChainArcs", "x4");
			config.addDefault("Abilities.Fire.Lightning.Cooldown", 0);
			config.addDefault("Abilities.Fire.Lightning.Range", 60);
			config.addDefault("Abilities.Fire.FireBurst.ChargeTime", 100);
			config.addDefault("Abilities.Fire.FireBurst.Damage", "x1.5");
			config.addDefault("Abilities.Fire.FireBurst.Cooldown", 0);
			config.addDefault("Abilities.Fire.FireBlast.ChargeTime", "x0.25");
			config.addDefault("Abilities.Fire.FireBlast.Speed", "x2.0");
			config.addDefault("Abilities.Fire.FireKick.Damage", "x2.0");
			config.addDefault("Abilities.Fire.FireKick.Range", "x2.0");
			config.addDefault("Abilities.Fire.FireSpin.Cooldown", 0);
			config.addDefault("Abilities.Fire.FireSpin.Damage", "x1.8");
			config.addDefault("Abilities.Fire.FireSpin.Range", "x2.0");
			config.addDefault("Abilities.Fire.FireSpin.Knockback", "x1.0");
			config.addDefault("Abilities.Fire.FireWheel.Damage", 6);
			config.addDefault("Abilities.Fire.FireWheel.Range", "x2.5");
			config.addDefault("Abilities.Fire.FireWheel.Height", 3);
			config.addDefault("Abilities.Fire.FireWheel.Speed", "x1.2");
			config.addDefault("Abilities.Fire.FireWheel.FireTicks", 4);
			config.addDefault("Abilities.Fire.WallOfFire.Height", "x1.6");
			config.addDefault("Abilities.Fire.WallOfFire.Width", "x2.5");
			config.addDefault("Abilities.Fire.WallOfFire.Duration", "x1.5");
			config.addDefault("Abilities.Fire.WallOfFire.FireTicks", 2);
			config.addDefault("Abilities.Fire.WallOfFire.Cooldown", "x1.0");

			config.addDefault("Abilities.Water._All.Damage", "x1.5");
			config.addDefault("Abilities.Water._All.Cooldown", "x0.1");
			config.addDefault("Abilities.Water._All.ChargeTime", "x0.5");
			config.addDefault("Abilities.Water._All.Duration", "x2.0");
			config.addDefault("Abilities.Water._All.Range", "x2.0");
			config.addDefault("Abilities.Water._All.SelectRange", "x3.0");
			config.addDefault("Abilities.Water.OctopusForm.AttackRange", 3);
			config.addDefault("Abilities.Water.OctopusForm.Radius", "x2.0");
			config.addDefault("Abilities.Water.OctopusForm.Damage", "x2.0");
			config.addDefault("Abilities.Water.OctopusForm.Knockback", "x3.0");
			config.addDefault("Abilities.Water.Surge.WallRadius", "x2.0");
			config.addDefault("Abilities.Water.Surge.Radius", "x2.0");
			config.addDefault("Abilities.Water.Surge.WallRange", 8);
			config.addDefault("Abilities.Water.Torrent.InitialDamage", 5);
			config.addDefault("Abilities.Water.Torrent.FreezeRadius", "x2.0");
			config.addDefault("Abilities.Water.Torrent.SuccessiveDamage", "x1.5");
			config.addDefault("Abilities.Water.Torrent.MaxHits", "x1.5");
			config.addDefault("Abilities.Water.Torrent.Knockback", "x1.5");
			config.addDefault("Abilities.Water.WaterManipulation.Damage", 5);
			config.addDefault("Abilities.Water.PhaseChange.FreezeRadius", "x3.0");
			config.addDefault("Abilities.Water.PhaseChange.FreezeDepth", 2);
			config.addDefault("Abilities.Water.PhaseChange.MeltCooldown", 500);
			config.addDefault("Abilities.Water.PhaseChange.MeltRadius", "x3.0");
			config.addDefault("Abilities.Water.PhaseChange.MeltSpeed", "x5.0");
			config.addDefault("Abilities.Water.PhaseChange.SelectRange", "x3.0");
			config.addDefault("Abilities.Water.PhaseChange.ControlRadius", "x3.0");
			config.addDefault("Abilities.Water.IceBlast.Damage", 4);
			config.addDefault("Abilities.Water.IceBlast.Range", 45);
			config.addDefault("Abilities.Water.IceBlast.Cooldown", 0);
			config.addDefault("Abilities.Water.IceBullet.Cooldown", 0);
			config.addDefault("Abilities.Water.IceSpike.Damage", "x1.5");
			config.addDefault("Abilities.Water.IceSpike.Range", "x1.5");
			config.addDefault("Abilities.Water.IceSpike.Knockup", "+0.3");
			config.addDefault("Abilities.Water.IceSpike.Height", "+2");
			config.addDefault("Abilities.Water.IceSpike.NumberOfSpikes", "x5.0");
			config.addDefault("Abilities.Water.IceSpike.SlowPotency", 3);
			config.addDefault("Abilities.Water.IceSpike.SlowCooldown", 0);
			config.addDefault("Abilities.Water.IceSpike.Cooldown", 0);
			config.addDefault("Abilities.Water.IceSpike.SlowDuration", 90);
			config.addDefault("Abilities.Water.IceSpike.Radius", "x2.5");
			config.addDefault("Abilities.Water.IceBullet.Damage", 5);
			config.addDefault("Abilities.Water.IceBullet.Range", 45);
			config.addDefault("Abilities.Water.IceBullet.MaxShots", 45);
			config.addDefault("Abilities.Water.IceBullet.ShotCooldown", 100);
			config.addDefault("Abilities.Water.IceBullet.ShootTime", 15000);
			config.addDefault("Abilities.Water.IceWave.Damage", 6);
			config.addDefault("Abilities.Water.IceWave.Cooldown", 0);
			config.addDefault("Abilities.Water.WaterSpout.Height", 40);
			config.addDefault("Abilities.Water.WaterSpout.Cooldown", 0);
			config.addDefault("Abilities.Water.WaterSpout.ChargeTime", 0);
			config.addDefault("Abilities.Water.WaterSpout.FlightDuration", "x2.0");
			config.addDefault("Abilities.Water.WaterBubble.Radius", "x1.5");
			config.addDefault("Abilities.Water.WaterBubble.ClickDuration", "x10.0");
			config.addDefault("Abilities.Water.WaterBubble.Speed", "x1.5");

			avatarStateConfig.save();
		}

	}

	public static FileConfiguration getConfig() {
		return ConfigManager.defaultConfig.get();
	}

	private static boolean hasChatPlugin() {
		List<String> plugins = Arrays.asList("EssentialsChat", "VentureChat", "LPC", "ChatManager", "ChatControl", "DeluxeChat");

		return Arrays.stream(Bukkit.getPluginManager().getPlugins()).anyMatch(pl -> plugins.contains(pl.getName()));
	}

	private static void migrateAvatarState() {
		FileConfiguration config = ConfigManager.defaultConfig.get();
		FileConfiguration avatarState = ConfigManager.avatarStateConfig.get();

		if (avatarState.getKeys(false).isEmpty()) { //Don't migrate if they have an AvatarState config file already
			return;
		}

		Map<String, String> translations = new HashMap<>();
		translations.put("IsAvatarStateToggle", "IsToggle");
		translations.put("AirBlast.Push.Entities", "AirBlast.Knockback");
		translations.put("AirBlast.Push.Self", "AirBlast.SelfPush");
		translations.put("Push", "Knockback");

		//Migrate potion effects.
		if (config.getBoolean("Abilities.Avatar.AvatarState.PotionEffects.Regeneration.Enabled")) {
			avatarState.set("PotionEffects.Regeneration", config.getInt("Abilities.Avatar.AvatarState.PotionEffects.Regeneration.Power"));
		}
		if (config.getBoolean("Abilities.Avatar.AvatarState.PotionEffects.Speed.Enabled")) {
			avatarState.set("PotionEffects.Speed", config.getInt("Abilities.Avatar.AvatarState.PotionEffects.Speed.Power"));
		}
		if (config.getBoolean("Abilities.Avatar.AvatarState.PotionEffects.DamageResistance.Enabled")) {
			avatarState.set("PotionEffects.Resistance", config.getInt("Abilities.Avatar.AvatarState.PotionEffects.DamageResistance.Power"));
		}
		if (config.getBoolean("Abilities.Avatar.AvatarState.PotionEffects.FireResistance.Enabled")) {
			avatarState.set("PotionEffects.Fire_Resistance", config.getInt("Abilities.Avatar.AvatarState.PotionEffects.FireResistance.Power"));
		}

		//Migrate all other ability keys
		for (String key : config.getConfigurationSection("Abilities.Avatar.AvatarState").getKeys(true)) {
			String newkey = key;

			if (!key.startsWith("PotionEffects") && !key.startsWith("PowerMultiplier")) { //Dont migrate these two keys
				//Go through all translations and check the key doesn't need to be translated to something new
				for (String translation : translations.keySet()) {
					if (key.endsWith(translation)) {
						newkey = key.substring(0, key.length() - translation.length()) + translations.get(translation); //Replace the old part with the new part
						break;
					}
				}

				avatarState.set("Abilities." + newkey, config.get("Abilities.Avatar.AvatarState." + key)); //Migrate all other keys
				continue;
			}

			config.set("Abilities.AvatarState." + key, null); //Remove the old key from the original config

		}

		//Save both configs
		ConfigManager.defaultConfig.save();
		ConfigManager.avatarStateConfig.save();
	}
}
