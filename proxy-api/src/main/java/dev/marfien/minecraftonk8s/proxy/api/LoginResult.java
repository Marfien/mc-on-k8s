package dev.marfien.minecraftonk8s.proxy.api;

import net.kyori.adventure.text.Component;

public record LoginResult(boolean allowed, Component message) {}
