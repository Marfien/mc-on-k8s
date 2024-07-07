package dev.marfien.minecraftonk8s.client.proxy.agent.internal;

import dev.marfien.minecraftonk8s.api.model.minecraftserver.MinecraftServer;
import dev.marfien.minecraftonk8s.api.model.minecraftserver.MinecraftServerStatus;
import dev.marfien.minecraftonk8s.client.proxy.agent.ProxyInterface;
import io.fabric8.kubernetes.api.model.KubernetesResourceList;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.kubernetes.client.dsl.MixedOperation;
import io.fabric8.kubernetes.client.dsl.Resource;
import io.fabric8.kubernetes.client.informers.ResourceEventHandler;
import io.fabric8.kubernetes.client.informers.SharedIndexInformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinecraftServerInformer {

    private final KubernetesClient kubernetesClient = new KubernetesClientBuilder().build();
    private final MixedOperation<MinecraftServer, KubernetesResourceList<MinecraftServer>, Resource<MinecraftServer>>
            minecraftServerOperation = this.kubernetesClient.resources(MinecraftServer.class);

    private final ProxyInterface proxyInterface;
    private final long resyncPeriod;

    private SharedIndexInformer<MinecraftServer> informer;

    public MinecraftServerInformer(ProxyInterface proxyInterface, long resyncPeriod) {
        this.proxyInterface = proxyInterface;
        this.resyncPeriod = resyncPeriod;
    }

    public void start(String watchingNamespace) {
        this.informer = this.minecraftServerOperation
                .inNamespace(watchingNamespace)
                .inform(null, this.resyncPeriod);
        informer.addEventHandler(new MinecraftServerWatcherEventHandler());
    }

    public void stop() {
        if (this.informer != null) {
            this.informer.stop();
        }
        this.kubernetesClient.close();
    }

    private class MinecraftServerWatcherEventHandler implements
            ResourceEventHandler<MinecraftServer> {

        private final Logger logger = LoggerFactory.getLogger(MinecraftServerWatcherEventHandler.class);

        @Override
        public void onAdd(MinecraftServer obj) {
            this.logger.info("New MinecraftServer {} was found", obj.getMetadata().getName());
            String name = obj.getMetadata().getName();
            MinecraftServerStatus status = obj.getStatus();
            if (status.isReady()) {
                this.logger.info("MinecraftServer {} is ready. Adding it...", name);
                proxyInterface.addServer(name, status.getIp(), status.getPort());
            } else {
                this.logger.info("MinecraftServer {} is not ready yet. Adding it when turning ready.", name);
            }
        }

        @Override
        public void onUpdate(MinecraftServer oldObj, MinecraftServer newObj) {
            this.logger.info("MinecraftServer {} was updated", newObj.getMetadata().getName());
            MinecraftServerStatus oldStatus = oldObj.getStatus();
            MinecraftServerStatus newStatus = newObj.getStatus();

            if (!oldStatus.isReady() && newStatus.isReady()) {
                String name = newObj.getMetadata().getName();
                this.logger.info("MinecraftServer {} is ready now. Adding it...", name);
                proxyInterface.addServer(name, newStatus.getIp(), newStatus.getPort());
            }
        }

        @Override
        public void onDelete(MinecraftServer obj, boolean deletedFinalStateUnknown) {
            String name = obj.getMetadata().getName();
            this.logger.info("MinecraftServer {} was deleted", name);
            proxyInterface.removeServer(name);
        }
    }

}
