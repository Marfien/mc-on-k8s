package dev.marfien.minecraftonk8s.operator;

import io.fabric8.kubernetes.api.model.ConfigMap;

public interface BinariesConfigMapChecker {

    void checkBinariesConfigMap();

    ConfigMap getDesiredConfigMap();

}
