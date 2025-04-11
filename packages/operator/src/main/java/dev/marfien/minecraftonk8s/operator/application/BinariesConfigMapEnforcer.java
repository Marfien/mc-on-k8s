package dev.marfien.minecraftonk8s.operator.application;

import io.fabric8.kubernetes.api.model.ConfigMap;

public interface BinariesConfigMapEnforcer {

    void ensureAgentBinary();

    ConfigMap getDesiredConfigMap();

}
