package me.przemovi.utils;

import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ItemTag;
import net.md_5.bungee.api.chat.KeybindComponent;
import net.md_5.bungee.api.chat.ScoreComponent;
import net.md_5.bungee.api.chat.SelectorComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import net.md_5.bungee.api.chat.hover.content.Entity;
import net.md_5.bungee.api.chat.hover.content.EntitySerializer;
import net.md_5.bungee.api.chat.hover.content.Item;
import net.md_5.bungee.api.chat.hover.content.ItemSerializer;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.chat.hover.content.TextSerializer;
import net.md_5.bungee.chat.ChatVersion;
import net.md_5.bungee.chat.ComponentSerializer;
import net.md_5.bungee.chat.KeybindComponentSerializer;
import net.md_5.bungee.chat.ScoreComponentSerializer;
import net.md_5.bungee.chat.SelectorComponentSerializer;
import net.md_5.bungee.chat.TextComponentSerializer;
import net.md_5.bungee.chat.TranslatableComponentSerializer;
import net.md_5.bungee.chat.VersionedComponentSerializer;

public class CSerializer {
	private static final VersionedComponentSerializer ver = VersionedComponentSerializer.forVersion(ChatVersion.V1_21_5);
	private static final Gson gson = new GsonBuilder()
			.registerTypeAdapter(BaseComponent.class, new ComponentSerializer())
			.registerTypeAdapter(TextComponent.class, new TextComponentSerializer(ver))
			.registerTypeAdapter(TranslatableComponent.class, new TranslatableComponentSerializer(ver))
			.registerTypeAdapter(KeybindComponent.class, new KeybindComponentSerializer(ver))
			.registerTypeAdapter(ScoreComponent.class, new ScoreComponentSerializer(ver))
			.registerTypeAdapter(SelectorComponent.class, new SelectorComponentSerializer(ver))
			.registerTypeAdapter(Entity.class, new EntitySerializer(ver))
			.registerTypeAdapter(Text.class, new TextSerializer())
			.registerTypeAdapter(Item.class, new ItemSerializer())
			.registerTypeAdapter(ItemTag.class, new ItemTag.Serializer()).create();
	public static final ThreadLocal<Set<BaseComponent>> serializedComponents = new ThreadLocal<Set<BaseComponent>>();

	public static BaseComponent[] parse(String json) {
		JsonElement jsonElement = gson.fromJson(json, JsonElement.class);

		if (jsonElement.isJsonArray()) {
			return gson.fromJson(jsonElement, BaseComponent[].class);
		} else {
			return new BaseComponent[] { gson.fromJson(jsonElement, BaseComponent.class) };
		}
	}
	
	public static BaseComponent[] parse(JsonElement jsonElement) {

		if (jsonElement.isJsonArray()) {
			return gson.fromJson(jsonElement, BaseComponent[].class);
		} else {
			return new BaseComponent[] { gson.fromJson(jsonElement, BaseComponent.class) };
		}
	}

	public static String toString(Object object) {
		return gson.toJson(object);
	}

	public static String toString(BaseComponent component) {
		return gson.toJson(component);
	}

	public static String toString(BaseComponent... components) {
		if (components.length == 1) {
			return gson.toJson(components[0]);
		} else {
			return gson.toJson(new TextComponent(components));
		}
	}
	
	public static JsonElement toJson(BaseComponent... components) {
		if (components.length == 1) {
			return gson.toJsonTree(components[0]);
		} else {
			return gson.toJsonTree(new TextComponent(components));
		}
	}
}
