package dev.marfien.minecraftonk8s.operator.minecraftproxyfleet;

import dev.marfien.minecraftonk8s.agones.model.Fleet;
import dev.marfien.minecraftonk8s.agones.model.GameServerTemplateSpec;
import dev.marfien.minecraftonk8s.api.model.minecraftproxyfleet.MinecraftProxyFleet;
import dev.marfien.minecraftonk8s.common.Constant;
import dev.marfien.minecraftonk8s.common.Constant.AppLabel;
import dev.marfien.minecraftonk8s.common.Constant.K8sLabel;
import dev.marfien.minecraftonk8s.operator.CrdUtils;
import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.ContainerPort;
import io.fabric8.kubernetes.api.model.IntOrString;
import io.javaoperatorsdk.operator.api.reconciler.Cleaner;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.DeleteControl;
import io.javaoperatorsdk.operator.api.reconciler.ErrorStatusUpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.Workflow;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;
import java.util.List;

@Workflow(dependents = {
        @Dependent(type = FleetDependentResource.class),
        @Dependent(type = ServiceDependentResource.class)
})
@ControllerConfiguration
public class MinecraftProxyFleetReconciler
        implements Reconciler<MinecraftProxyFleet>, Cleaner<MinecraftProxyFleet> {

    public static final String LABEL_SELECTOR =
            K8sLabel.MANAGED_BY + "=" + Constant.OPERATOR_NAME + "," +
            AppLabel.RECONCILER + "=" + Constant.MinecraftProxyFleet.RECONCILER;

    @Override
    public UpdateControl<MinecraftProxyFleet> reconcile(MinecraftProxyFleet resource,
            Context<MinecraftProxyFleet> context) throws Exception {
        Fleet proxyFleet = context.getSecondaryResource(Fleet.class).orElseThrow();

        IntOrString serviceTargetPort = resource.getSpec().getService().getTargetPort();

        // Check if the target port has a matching container port
        GameServerTemplateSpec gsSpec = proxyFleet.getSpec().getTemplate();
        List<Container> containers = gsSpec.getSpec().getTemplate().getSpec().getContainers();
        if (containers.stream()
                .flatMap(c -> c.getPorts().stream())
                .noneMatch(port -> CrdUtils.containerPortEquals(port, serviceTargetPort))) {
            throw new IllegalArgumentException("The target port does not match any container port.");
        }

        return UpdateControl.patchStatus(
                resource.edit()
                        .editStatus()
                            .withReplicas(proxyFleet.getStatus().getReplicas())
                            .withReadyReplicas(proxyFleet.getStatus().getReadyReplicas())
                            .withAllocatedReplicas(proxyFleet.getStatus().getAllocatedReplicas())
                                .addNewCondition()
                                .withStatus("True")
                                .withType("Reconciled")
                                .withReason("FleetReconciled")
                                .withMessage("The fleet has been reconciled successfully.")
                                .endCondition()
                            .endStatus()
                        .build()
        );
    }

    @Override
    public ErrorStatusUpdateControl<MinecraftProxyFleet> updateErrorStatus(
            MinecraftProxyFleet resource, Context<MinecraftProxyFleet> context, Exception e) {
        return ErrorStatusUpdateControl.patchStatus(
                resource.edit()
                        .editStatus()
                            .addNewCondition()
                                .withStatus("False")
                                .withType("Error")
                                .withReason("ReconcileError")
                                .withMessage(e.getMessage())
                                .endCondition()
                            .endStatus()
                        .build());
    }

    @Override
    public DeleteControl cleanup(MinecraftProxyFleet resource,
            Context<MinecraftProxyFleet> context) {
        return DeleteControl.defaultDelete();
    }
}
