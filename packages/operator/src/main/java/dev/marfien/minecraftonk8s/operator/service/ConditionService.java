package dev.marfien.minecraftonk8s.operator.service;

import io.fabric8.kubernetes.api.model.Condition;

public interface ConditionService {

    Condition ready(String type, String reason, String message);

    default Condition ready(String reason, String message) {
        return this.ready("Ready", reason, message);
    }

    Condition notReady(String type, String reason, String message);

    default Condition notReady(String reason, String message) {
        return this.notReady("Unknown", reason, message);
    }

    default Condition fromException(String reason, Exception e) {
        return this.notReady("Error", reason, e.getMessage());
    }

    default Condition fromException(Exception e) {
        return this.fromException("ReconcileError", e);
    }

}
