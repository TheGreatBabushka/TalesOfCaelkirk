package skeeter144.toc.client;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import skeeter144.toc.util.CustomKey;

public class Keybindings {
	public static List<CustomKey> keyBinds;
	public static CustomKey SPELLBOOK_KEYBIND;
	public static CustomKey LEVELS_KEYBIND;
	public static CustomKey INVENTORY_KEYBIND;
	
	public static void registerKeybinds() {
		keyBinds = new ArrayList<CustomKey>();
		
		// instantiate the key bindings
		SPELLBOOK_KEYBIND = new CustomKey("key.magic.opengui", Keyboard.KEY_M, "key.toc.category");
		LEVELS_KEYBIND = new CustomKey("key.levels.opengui", Keyboard.KEY_L, "key.toc.category");
		INVENTORY_KEYBIND = new CustomKey("key.inventory.opengui", Keyboard.KEY_E, "key.toc.category");
		
		
		keyBinds.add(SPELLBOOK_KEYBIND);
		keyBinds.add(LEVELS_KEYBIND);
		keyBinds.add(INVENTORY_KEYBIND);
		
		
		for(CustomKey c : keyBinds)
			ClientRegistry.registerKeyBinding(c);
		
		int i = 0;
		for(KeyBinding kb : Minecraft.getMinecraft().gameSettings.keyBindings) {
			if(kb.equals(Minecraft.getMinecraft().gameSettings.keyBindInventory)) {
				Minecraft.getMinecraft().gameSettings.keyBindInventory = new KeyBinding("key.inventory", Keyboard.KEY_F19, "key.categories.inventory");
				Minecraft.getMinecraft().gameSettings.keyBindings[i] = Minecraft.getMinecraft().gameSettings.keyBindInventory;
				break;
			}
			++i;
		}
		
		
	}
	
	public enum TOCKeys{
		SPELLBOOK,
		LEVELS,
		UNKNOWN
	}
}
