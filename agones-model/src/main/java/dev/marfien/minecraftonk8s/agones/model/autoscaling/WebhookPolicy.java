package dev.marfien.minecraftonk8s.agones.model.autoscaling;

import io.fabric8.kubernetes.api.model.ServiceReference;
import io.sundr.builder.annotations.Buildable;
import io.sundr.builder.annotations.BuildableReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Buildable(editableEnabled = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder", refs = {
        @BuildableReference(ServiceReference.class)
})
public class WebhookPolicy {

    private String url;

    private ServiceReference service;

    private byte[] caBundle;

}
