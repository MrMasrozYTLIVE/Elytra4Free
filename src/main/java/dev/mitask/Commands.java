package dev.mitask;

import com.mojang.brigadier.CommandDispatcher;
import dev.mitask.autopilot.Autopilot;
import dev.mitask.autopilot.FlightAnalytics;
import dev.mitask.autopilot.TraverseArea;
import dev.mitask.config.AutomaticElytraConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ColumnPos;

public class Commands {
    @Environment(EnvType.CLIENT)
    public static void registerCommands(){
//        ClientCommandRegistrationCallback.EVENT.register(Commands::SetDestinationCommand);
        ClientCommandRegistrationCallback.EVENT.register(Commands::SetLastDestinationCommand);
        CommandRegistrationCallback.EVENT.register(Commands::unsetDestinationCommand);
//        ClientCommandRegistrationCallback.EVENT.register(Commands::traverseAreaCommand);
        CommandRegistrationCallback.EVENT.register(Commands::analyticsCommand);
    }

    private static void SetLastDestinationCommand(CommandDispatcher<FabricClientCommandSource> fabricClientCommandSourceCommandDispatcher, CommandBuildContext CommandBuildContext) {
        fabricClientCommandSourceCommandDispatcher.register(ClientCommandManager.literal("setDestination")
            .executes(context -> {
                assert context.getSource().getPlayer() != null;
                BlockPos pos = Autopilot.getPrevDestination();
                if(pos != null){
                    if(AutomaticInfiniteElytraClient.autoFlight) {
                        Autopilot.initNewFlight(pos, false);
                        TraverseArea.stop();
                        context.getSource().getPlayer().displayClientMessage(Component.literal("Autopilot is set to coordinates " + pos.getX() + " " + pos.getZ()).withStyle(ChatFormatting.GREEN), false);
                    }
                    else context.getSource().getPlayer().displayClientMessage(Component.literal("You need to be flying and have Automatic Flight Mode enabled.").withStyle(ChatFormatting.RED), false);
                }
                else context.getSource().getPlayer().displayClientMessage(Component.literal("Previous destination is null").withStyle(ChatFormatting.RED), false);
                return 1;
            }));
    }

    /*private static void SetDestinationCommand(CommandDispatcher<FabricClientCommandSource> fabricClientCommandSourceCommandDispatcher, CommandBuildContext CommandBuildContext) {
        fabricClientCommandSourceCommandDispatcher.register(ClientCommandManager.literal("setDestination")
            .then(ClientCommandManager.argument("destination", CColumnPosArgument.columnPos())
                .executes(context -> {
                    assert context.getSource().getPlayer() != null;
                    //BlockPos pos = CBlockPosArgument.getBlockPos(context, "Z");
                    ColumnPos columnPos = CColumnPosArgument.getColumnPos(context, "destination");
                    BlockPos pos = AutomaticInfiniteElytra.blockPos(columnPos);
                    if(true){
                        if(AutomaticInfiniteElytraClient.autoFlight) {
                            Autopilot.initNewFlight(pos, false);
                            TraverseArea.stop();
                            context.getSource().getPlayer().displayClientMessage(Component.literal("Autopilot is set to coordinates " + pos.getX() + " " + pos.getZ()).withStyle(ChatFormatting.GREEN), false);
                        }
                        else context.getSource().getPlayer().displayClientMessage(Component.literal("You need to be flying and have Automatic Flight Mode enabled.").withStyle(ChatFormatting.RED), false);
                    }
                    else context.getSource().getPlayer().displayClientMessage(Component.literal("Autopilot is disabled. Please enable it in the Config.").withStyle(ChatFormatting.RED), false);
                   return 1;
        })));
    }*/

    private static void unsetDestinationCommand(CommandDispatcher<CommandSourceStack> serverCommandSourceCommandDispatcher, CommandBuildContext CommandBuildContext, net.minecraft.commands.Commands.CommandSelection registrationEnvironment) {
        serverCommandSourceCommandDispatcher.register(net.minecraft.commands.Commands.literal("removeDestination")
                .executes(context -> {
                    Autopilot.unsetLocation();
                    context.getSource().getPlayer().displayClientMessage(Component.literal("Autopilot deactivated."), false);
                    return 0;
                }));
    }

    /*private static void traverseAreaCommand(CommandDispatcher<FabricClientCommandSource> fabricClientCommandSourceCommandDispatcher, CommandBuildContext CommandBuildContext) {
        fabricClientCommandSourceCommandDispatcher.register(ClientCommandManager.literal("exploreArea")
                .then(ClientCommandManager.argument("starting", CColumnPosArgument.columnPos())
                        .then(ClientCommandManager.argument("ending", CColumnPosArgument.columnPos())
                            .executes(context -> {
                                assert context.getSource().getPlayer() != null;
                                //BlockPos pos = CBlockPosArgument.getBlockPos(context, "Z");
                                ColumnPos starting = CColumnPosArgument.getColumnPos(context, "starting");
                                ColumnPos ending = CColumnPosArgument.getColumnPos(context, "ending");
                                    if(AutomaticInfiniteElytraClient.autoFlight) {
                                        TraverseArea.init(starting, ending);
                                        context.getSource().getPlayer().displayClientMessage(Component.literal("Area traversal in progress").withStyle(ChatFormatting.GREEN), false);
                                    }
                                    else context.getSource().getPlayer().displayClientMessage(Component.literal("You need to be flying and have Automatic Flight Mode enabled.").withStyle(ChatFormatting.RED), false);
                                return 1;
                            }))));
    }*/


    private static void analyticsCommand(CommandDispatcher<CommandSourceStack> serverCommandSourceCommandDispatcher, CommandBuildContext CommandBuildContext, net.minecraft.commands.Commands.CommandSelection registrationEnvironment) {
        serverCommandSourceCommandDispatcher.register(net.minecraft.commands.Commands.literal("flightanalytics")
                .executes(context -> {
                    if(FlightAnalytics.isCompletedFlight() /*&& AutomaticElytraConfig.HANDLER.instance().record_analytics*/){
                        context.getSource().getPlayer().displayClientMessage(Component.literal("-"), false);
                        context.getSource().getPlayer().displayClientMessage(Component.literal("Getting Analytics"), false);
                        FlightAnalytics.printAnalytics(context.getSource().getPlayer());
                    }
                    else {
                        context.getSource().getPlayer().displayClientMessage(Component.literal("Flight data is unavailable").withStyle(ChatFormatting.RED), false);
                        context.getSource().getPlayer().displayClientMessage(Component.literal("This might be because you haven't flown yet, or Analytics is disabled in your config").withStyle(ChatFormatting.WHITE), false);
                    }
                    return 0;
                }));
    }
}
