package dev.marfien.minecraftonk8s.operator.service;

import io.fabric8.kubernetes.api.model.Condition;
import io.fabric8.kubernetes.api.model.ConditionBuilder;
import jakarta.inject.Singleton;

@Singleton
public class ConditionServiceImpl implements ConditionService {

    @Override
    public Condition ready(String type, String reason, String message) {
        return new ConditionBuilder()
                .withType(type)
                .withStatus("True")
                .withReason(reason)
                .withMessage(message)
                .build();
    }

    @Override
    public Condition notReady(String type, String reason, String message) {
        return new ConditionBuilder()
                .withType(type)
                .withStatus("False")
                .withReason(reason)
                .withMessage(message)
                .build();
    }
}
