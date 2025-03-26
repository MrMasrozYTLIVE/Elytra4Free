package dev.mitask;

import org.rusherhack.client.api.plugin.Plugin;

public class ElytraFree extends Plugin {
	@Override
	public void onLoad() {
		getLogger().info("Loading Elytra4Free...");

		ExampleModule exampleModule = new ExampleModule();
		RusherHackAPI.getModuleManager().registerFeature(exampleModule);

		ExampleCommand exampleCommand = new ExampleCommand();
		RusherHackAPI.getCommandManager().registerFeature(exampleCommand);

		getLogger().info("Elytra4Free loaded!");
	}
	
	@Override
	public void onUnload() {
		getLogger().info("Elytra4Free unloaded!");
	}
}