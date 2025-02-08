package dev.marfien.minecraftonk8s.operator.util;

import dev.marfien.minecraftonk8s.agones.model.GameServerSpec;
import dev.marfien.minecraftonk8s.agones.model.GameServerSpecBuilder;
import dev.marfien.minecraftonk8s.api.model.minecraftserver.MinecraftServerSpec;
import io.fabric8.kubernetes.api.model.Container;
import java.util.List;

public final class GameServerUtil {

    private GameServerUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static GameServerSpec toGameServerSpec(MinecraftServerSpec spec) {
        return new GameServerSpecBuilder()
                .withNewSdkServer()
                .withLogLevel(spec.getSdkServerLogLevel())
                .endSdkServer()
                .withNewTemplateLike(spec.getTemplate())
                .editSpec()
                .withContainers(patchContainers(spec.getTemplate().getSpec().getContainers(), spec))
                .endSpec()
                .endTemplate()
                .build();
    }

    private static List<Container> patchContainers(List<Container> containers, MinecraftServerSpec spec) {
        return containers.stream()
                .parallel()
                .map(container ->
                        container.edit()
                                .addNewEnv()
                                .withName("MCS_TAGS")
                                .withValue(String.join(";", spec.getTags()))
                                .endEnv()
                                .addNewEnv()
                                .withName("MINECRAFT_SERVER_ALLOCATION_DEFAULTSTRATEGY")
                                .withValue(Configuration.MINECRAFT_SERVER_ALLOCATION_DEFAULTSTRATEGY)
                                .endEnv()
                                .build()
                )
                .toList();
    }

}
