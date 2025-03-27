package dev.mitask;

import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.plugin.Plugin;

public class ElytraFree extends Plugin {
	@Override
	public void onLoad() {
		getLogger().info("Loading Elytra4Free...");

		RusherHackAPI.getModuleManager().registerFeature(new ElytraFreeModule());
		RusherHackAPI.getCommandManager().registerFeature(new ExampleCommand());

		getLogger().info("Elytra4Free loaded!");
	}
	
	@Override
	public void onUnload() {
		getLogger().info("Elytra4Free unloaded!");
	}
}