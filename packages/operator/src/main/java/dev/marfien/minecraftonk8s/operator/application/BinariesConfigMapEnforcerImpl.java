package dev.marfien.minecraftonk8s.operator.application;

import dev.marfien.minecraftonk8s.common.Constant;
import dev.marfien.minecraftonk8s.common.Constant.K8sLabel;
import dev.marfien.minecraftonk8s.operator.application.MinecraftOnK8sConfig.BinaryConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

@Singleton
public class BinariesConfigMapEnforcerImpl implements BinariesConfigMapEnforcer {

    @Inject
    MinecraftOnK8sConfig config;

    @ConfigProperty(name = "quarkus.application.version")
    String version;

    @Inject
    KubernetesClient client;

    @Inject
    Logger logger;

    private ConfigMap desiredConfigMap;

    @PostConstruct
    void init(@Observes StartupEvent event) throws IOException {
        this.logger.info("Applying agent-bin configmap");

        String proxyAgentJarB64, gameserverAgentJarB64;

        try(InputStream proxyAgentJar = this.getClass().getResourceAsStream("/proxy-agent.jar");
            InputStream gameserverAgentJar = this.getClass().getResourceAsStream("/gameserver-agent.jar")) {
            if (proxyAgentJar == null || gameserverAgentJar == null) {
                throw new IllegalStateException("Agent binary not found");
            }
            proxyAgentJarB64 = Base64.getEncoder().encodeToString(proxyAgentJar.readAllBytes());
            gameserverAgentJarB64 = Base64.getEncoder().encodeToString(gameserverAgentJar.readAllBytes());
        }

        ConfigMap configMap = new ConfigMapBuilder()
                .withNewMetadata()
                    .withName(this.config.binaryConfigMap().name())
                    .withNamespace(this.config.binaryConfigMap().namespace()) // operator namespace
                    .addToLabels(K8sLabel.MANAGED_BY, Constant.OPERATOR_NAME)
                    .addToLabels(K8sLabel.COMPONENT, "operator")
                    .addToLabels(K8sLabel.VERSION, this.version)
                    .endMetadata()
                .addToBinaryData("proxy-agent.jar", proxyAgentJarB64)
                .addToBinaryData("gameserver-agent.jar", gameserverAgentJarB64)
                .build();

        // TODO warn if configmap already exists
        this.desiredConfigMap = configMap;
        this.client.resource(configMap).serverSideApply();
    }

    public void ensureAgentBinary() {
        BinaryConfigMap config = this.config.binaryConfigMap();
        
        ConfigMap configMap = this.client.configMaps()
                .inNamespace(this.config.binaryConfigMap().namespace())
                .withName(this.config.binaryConfigMap().name())
                .get();

        if (configMap != null) { // Check labels
            Map<String, String> labels = configMap.getMetadata().getLabels();
            if (Constant.OPERATOR_NAME.equals(labels.get(K8sLabel.MANAGED_BY))) {
                throw new IllegalStateException("ConfigMap %s is not managed by %s".formatted(config.name(), Constant.OPERATOR_NAME));
            }

            String actualVersion = labels.get(K8sLabel.VERSION);

            if (!this.version.equals(actualVersion)) {
                String message = "ConfigMap %s version mismatch: (Expected: %s, Actual: %s)"
                        .formatted(this.config.binaryConfigMap().name(), this.version, actualVersion);

                if (!this.config.binaryConfigMap().forceOverwrite()) {
                    throw new IllegalStateException(message);
                } else {
                    this.logger.warn(message);
                }
            }
        }

        this.client.resource(this.desiredConfigMap).serverSideApply();
    }

    @Override
    public ConfigMap getDesiredConfigMap() {
        return this.desiredConfigMap;
    }
}
