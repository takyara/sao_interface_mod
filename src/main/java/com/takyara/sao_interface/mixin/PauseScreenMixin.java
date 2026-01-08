package com.takyara.sao_interface.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public class PauseScreenMixin extends Screen {
    
    protected PauseScreenMixin(Text title) {
        super(title);
    }

    /**
     * Injection dans la méthode init() pour ajouter des éléments personnalisés
     */
    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        // Ajouter un bouton personnalisé style SAO
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal("§6⚔ Menu SAO ⚔"),
            button -> {
                if (this.client != null && this.client.player != null) {
                    this.client.player.sendMessage(
                        Text.literal("§aBienvenue dans le menu SAO!"), 
                        false
                    );
                }
            })
            .dimensions(this.width / 2 - 100, this.height / 4 + 150, 200, 20)
            .build()
        );
    }
    
    /**
     * Injection dans render() pour personnaliser l'affichage
     */
    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        // Titre personnalisé style SAO
        context.drawCenteredTextWithShadow(
            this.textRenderer,
            Text.literal("§l§6═══════════════════════"),
            this.width / 2,
            15,
            0xFFD700
        );
        
        context.drawCenteredTextWithShadow(
            this.textRenderer,
            Text.literal("§l§eSOURD ART ONLINE"),
            this.width / 2,
            25,
            0xFFFFFF
        );
        
        context.drawCenteredTextWithShadow(
            this.textRenderer,
            Text.literal("§l§6═══════════════════════"),
            this.width / 2,
            35,
            0xFFD700
        );
        
        // Afficher les coordonnées du joueur (style RPG)
        if (this.client != null && this.client.player != null) {
            String coords = String.format("§7[§aX: §f%.0f §7|§a Y: §f%.0f §7|§a Z: §f%.0f§7]",
                this.client.player.getX(),
                this.client.player.getY(),
                this.client.player.getZ()
            );
            
            context.drawTextWithShadow(
                this.textRenderer,
                Text.literal(coords),
                10,
                this.height - 30,
                0xFFFFFF
            );
            
            // Niveau de santé
            String health = String.format("§c❤ HP: §f%.1f/20.0", 
                this.client.player.getHealth()
            );
            
            context.drawTextWithShadow(
                this.textRenderer,
                Text.literal(health),
                10,
                this.height - 20,
                0xFFFFFF
            );
        }
    }
}