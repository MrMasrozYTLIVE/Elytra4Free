package dev.mitask;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.events.client.EventUpdate;
import org.rusherhack.client.api.events.render.EventRender2D;
import org.rusherhack.client.api.events.render.EventRender3D;
import org.rusherhack.client.api.feature.module.ModuleCategory;
import org.rusherhack.client.api.feature.module.ToggleableModule;
import org.rusherhack.client.api.render.IRenderer2D;
import org.rusherhack.client.api.render.IRenderer3D;
import org.rusherhack.client.api.render.font.IFontRenderer;
import org.rusherhack.client.api.setting.BindSetting;
import org.rusherhack.client.api.setting.ColorSetting;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.client.api.utils.WorldUtils;
import org.rusherhack.core.bind.key.NullKey;
import org.rusherhack.core.event.subscribe.Subscribe;
import org.rusherhack.core.setting.BooleanSetting;
import org.rusherhack.core.setting.NumberSetting;
import org.rusherhack.core.setting.StringSetting;
import org.rusherhack.core.utils.ColorUtils;

import java.awt.*;

public class ElytraFreeModule extends ToggleableModule {
    /**
     * Settings
     */
//    @AutoGen(category = "Automatic_Elytra_Flight", group = "Autoflight")
    private final NumberSetting<Integer> max_altitude = new NumberSetting<>("max_altitude", "default = 20", 200, 0, Integer.MAX_VALUE).incremental(1);

//    @AutoGen(category = "Automatic_Elytra_Flight", group = "Autoflight")
    private final BooleanSetting anti_collision = new BooleanSetting("anti_collision", "should anti-collision utility be activated?", true);

    // HUDHelper MAIN

//    @AutoGen(category = "Automatic_Elytra_HUD", group = "HUD_Settings")
    private final BooleanSetting render_hud = new BooleanSetting("render_hud", "", true);

    //RENDER FINE TUNES

//    @AutoGen(category = "Automatic_Elytra_HUD", group = "HUD_Settings")
    private final BooleanSetting render_flight_mode = new BooleanSetting("render_flight_mode", "", true);

//    @AutoGen(category = "Automatic_Elytra_HUD", group = "HUD_Settings")
    private final BooleanSetting render_altitude = new BooleanSetting("render_altitude", "", true);

//    @AutoGen(category = "Automatic_Elytra_HUD", group = "HUD_Settings")
    private final BooleanSetting render_speed = new BooleanSetting("render_speed", "", true);

//    @AutoGen(category = "Automatic_Elytra_HUD", group = "HUD_Settings")
    private final BooleanSetting render_elytra_durability = new BooleanSetting("render_elytra_durability", "", true);

    // AUTOPILOT

//    @AutoGen(category = "Automatic_Elytra_Flight", group = "Autopilot")
    private final BooleanSetting do_landing = new BooleanSetting("do_landing", "", true);;

//    @AutoGen(category = "Automatic_Elytra_HUD", group = "HUD_Settings")
    private final BooleanSetting render_autopilot_coords = new BooleanSetting("render_autopilot_coords", "", true);;

//    @AutoGen(category = "Automatic_Elytra_Flight", group = "Autopilot")
    private final BooleanSetting record_analytics = new BooleanSetting("record_analytics", "", true);

//    @AutoGen(category = "Automatic_Elytra_Flight", group = "Autopilot")
    private final BooleanSetting auto_send_analytics = new BooleanSetting("auto_send_analytics", "", false);

    /**
     * Constructor
     */
    public ElytraFreeModule() {
        super("ElytraFree", "Infinite flying on elytra (Pitch 40)", ModuleCategory.MOVEMENT);

        //subsettings
        render_hud.addSubSettings(render_altitude, render_autopilot_coords, render_elytra_durability, render_flight_mode, render_speed);
        record_analytics.addSubSettings(auto_send_analytics);

        //register settings
        this.registerSettings(
                max_altitude,
                anti_collision,
                render_hud,
                do_landing,
                record_analytics
        );
    }

    /**
     * 2d renderer demo
     */
    @Subscribe
    private void onRender2D(EventRender2D event) {
//        final IRenderer2D renderer = RusherHackAPI.getRenderer2D();
//        final IFontRenderer fontRenderer = RusherHackAPI.fonts().getFontRenderer();
//
//        //must begin renderer first
//        renderer.begin(event.getMatrixStack(), fontRenderer);
//
//        //draw stuff
//        renderer.drawRectangle(100, 100 + this.exampleDouble.getValue(), 100, 100, this.exampleColor.getValueRGB());
//        fontRenderer.drawString(this.exampleString.getValue(), 110, 110, Color.WHITE.getRGB());
//
//        //end renderer
//        renderer.end();
    }

    /**
     * Rotation demo
     */
    @Subscribe
    private void onUpdate(EventUpdate event) {
//        //only rotate while bind is held
//        if(this.rotate.getValue().isKeyDown()) {
//
//            //loop through entities to find a target
//            Entity target = null;
//            double dist = 999d;
//            for(Entity entity : WorldUtils.getEntitiesSorted()) {
//                if(mc.player.distanceTo(entity) < dist && entity instanceof LivingEntity) {
//                    target = entity;
//                    dist = mc.player.distanceTo(entity);
//                }
//            }
//
//            //rotate to target
//            if(target != null) {
//                RusherHackAPI.getRotationManager().updateRotation(target);
//            } else { //or rotate to the custom yaw
//                RusherHackAPI.getRotationManager().updateRotation(this.rotateYaw.getValue(), this.rotatePitch.getValue());
//            }
//        }
    }

    @Override
    public void onEnable() {
        if(mc.level != null) {
            ChatUtils.print("Hello World! Example module is enabled");
        }
    }

    @Override
    public void onDisable() {
        if(mc.level != null) {
            ChatUtils.print("Goodbye World! Example module has been disabled");
        }
    }
}
