package dev.marfien.agones.model;

import java.util.List;

public record ListStore(
        String name,
        long capacity,
        List<String> values
) {

    public ListStore(dev.agones.sdk.alpha.List list) {
        this(list.getName(), list.getCapacity(), list.getValuesList());
    }

    public dev.agones.sdk.alpha.List toAgonesList() {
        return dev.agones.sdk.alpha.List.newBuilder()
                .setName(this.name)
                .setCapacity(this.capacity)
                .addAllValues(this.values)
                .build();
    }

}
