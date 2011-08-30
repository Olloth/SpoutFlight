/*
 * This file is part of SpoutFlight (https://github.com/Olloth/SpoutFlight).
 * 
 * SpoutFlight is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.olloth.plugins.flight.listener;

import me.olloth.plugins.flight.SpoutFlight;

import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.event.input.InputListener;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.event.input.KeyReleasedEvent;
import org.getspout.spoutapi.keyboard.Keyboard;
import org.getspout.spoutapi.player.SpoutPlayer;

public class Keys extends InputListener {

	SpoutFlight plugin;

	public Keys(SpoutFlight plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onKeyPressedEvent(KeyPressedEvent event) {
		boolean hasPerm;
		hasPerm=event.getPlayer().hasPermission("spoutflight.fly");
		Plugin permissionsPlugin = plugin.getServer().getPluginManager().getPlugin("Permissions");
	
		if (permissionsPlugin != null) {
			hasPerm=SpoutFlight.permissionHandler.has(event.getPlayer(), "spoutflight.fly");	    
		}
		
		if (plugin.getBindMode(event.getPlayer())) {
			plugin.setPlayerBind(event.getPlayer(), event.getKey().getKeyCode());
			event.getPlayer().sendMessage("Flight toggle key bound to " + event.getKey().toString());
			plugin.removeBindMode(event.getPlayer());
		}

		else if (event.getScreenType().toString().equals("GAME_SCREEN") && hasPerm) {
			SpoutPlayer player = event.getPlayer();

			if (event.getKey().equals(Keyboard.getKey(plugin.getPlayerBind(player)))) {
				if (plugin.getPlayerEnabled(player)) {
					plugin.setPlayerEnabled(player, false);
					player.setAirSpeedMultiplier(1);
					player.setGravityMultiplier(1);
					player.setCanFly(false);
					player.setFallDistance(0);

				}

				else {
					plugin.setPlayerEnabled(player, true);
					player.setCanFly(true);
					player.setAirSpeedMultiplier(1 * plugin.getPlayerSpeed(player));
					player.setGravityMultiplier(0);
				}
				player.setVelocity(new Vector(0, 0, 0));

			}

			if (plugin.getPlayerEnabled(player)) {

				player.setAirSpeedMultiplier(1 * plugin.getPlayerSpeed(player));

				if (event.getKey().equals(player.getJumpKey())) {
					player.setGravityMultiplier(-0.1 * plugin.getPlayerSpeed(player));
				}

				else if (event.getKey().equals(player.getSneakKey())) {
					player.setGravityMultiplier(0.1 * plugin.getPlayerSpeed(player));
				}

			}

		}

	}

	@Override
	public void onKeyReleasedEvent(KeyReleasedEvent event) {
		SpoutPlayer player = event.getPlayer();

		if (plugin.getPlayerEnabled(player)) {
			if (event.getKey().equals(player.getJumpKey())) {
				player.setGravityMultiplier(0);
				player.setVelocity(new Vector(0, 0, 0));
			}

			else if (event.getKey().equals(player.getSneakKey())) {
				player.setGravityMultiplier(0);
				player.setVelocity(new Vector(0, 0, 0));
			}

			else if (event.getKey().equals(player.getForwardKey())) {
				player.setVelocity(new Vector(0, 0, 0));
			}

			else if (event.getKey().equals(player.getBackwardKey())) {
				player.setVelocity(new Vector(0, 0, 0));
			}

			else if (event.getKey().equals(player.getLeftKey())) {
				player.setVelocity(new Vector(0, 0, 0));
			}

			else if (event.getKey().equals(player.getRightKey())) {
				player.setVelocity(new Vector(0, 0, 0));
			}
		}

	}

}
