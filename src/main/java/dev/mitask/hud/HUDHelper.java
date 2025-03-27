package dev.mitask.hud;

import dev.mitask.AutomaticInfiniteElytraClient;
import dev.mitask.autopilot.Autopilot;
import dev.mitask.config.AutomaticElytraConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static dev.mitask.AutomaticInfiniteElytraClient.autoFlight;
import static dev.mitask.AutomaticInfiniteElytraClient.getCurrentVelocity;

@Environment(EnvType.CLIENT)
public class HUDHelper {
    private static final Minecraft minecraftClient = Minecraft.getInstance();
    private static int altitude = 0;
    private static boolean isRunning = false;
    private static ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    public static void init(){
        service.scheduleWithFixedDelay(() -> {
            if(minecraftClient.player != null && AutomaticInfiniteElytraClient.showHud && !isRunning) {
                isRunning = true;
                altitude = altitude(minecraftClient.player);
                isRunning = false;
            }
        }, 0, 50, TimeUnit.MILLISECONDS);
    }

    public static void exit(){
        service.shutdownNow();
        service = Executors.newSingleThreadScheduledExecutor();
    }
    public static ArrayList<String> generateHUD(ArrayList<String> hudArray, int HUD_ELEMENTS){
        assert minecraftClient.player != null;
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        /*if(!isRunning) executorService.submit(() -> {
            altitude = altitude(minecraftClient.player);
        });
        executorService.shutdown();*/

        ItemStack itemStack = minecraftClient.player.getItemBySlot(EquipmentSlot.CHEST);
        String[] hudString = new String[HUD_ELEMENTS];
        if (hudArray == null) hudArray = new ArrayList<String>();
        else hudArray.clear();

//        if(AutomaticElytraConfig.HANDLER.instance().render_flight_mode)
            hudString[0] = "Flight mode: " + (autoFlight ? "Automatic" : "Manual");
        if(Autopilot.isLanding()) hudString[0] = hudString[0] + " Landing";
        if(Autopilot.isAutopilotRunning()) hudString[0] = hudString[0] + ", Autopilot running";

//        if(AutomaticElytraConfig.HANDLER.instance().render_altitude)
            hudString[1] = "Altitude: " + altitude;
//        if(AutomaticElytraConfig.HANDLER.instance().render_speed)
            hudString[2] = "Speed: " + String.format("%.2f", getCurrentVelocity() * 20) + " m/s";
//        if(AutomaticElytraConfig.HANDLER.instance().render_elytra_durability)
            hudString[3] = "Elytra Durability: " + (itemStack.getMaxDamage() - itemStack.getDamageValue());
//        if(Autopilot.isAutopilotRunning() && AutomaticElytraConfig.HANDLER.instance().render_autopilot_coords)
            hudString[4] = "Autopilot: " + Autopilot.getDestination().getX() + " " + Autopilot.getDestination().getZ() + " (" + Math.round(Math.pow(Autopilot.getDestination().distSqr(minecraftClient.player.blockPosition()), 0.5)) + ")";

        for(int i = 0; i < HUD_ELEMENTS; i++){
            if(hudString[i] != null && !hudString[i].isEmpty()) hudArray.add(hudString[i]);
        }
        return hudArray;
    }

    private static int altitude(Player player){
        Level world = player.level();
        BlockPos blockPos = player.blockPosition();
        int counter = 0;
        while(world.getBlockState(blockPos).isAir() && !isOverVoid(blockPos)){
            blockPos = blockPos.below();
            counter++;
            if(isOverVoid(blockPos)) return player.getBlockY() - blockPos.getY();
            if(counter >= 20000) return Integer.MAX_VALUE;
        }
        return player.getBlockY() - blockPos.getY();
    }

    public static int getAltitude() {
        return altitude;
    }

    private static boolean isOverVoid(BlockPos blockPos){
        return blockPos.getY() < -64;
    }
}
