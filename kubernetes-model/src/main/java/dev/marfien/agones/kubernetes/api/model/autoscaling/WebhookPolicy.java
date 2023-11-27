package dev.marfien.agones.kubernetes.api.model.autoscaling;

import io.fabric8.kubernetes.api.model.ServiceReference;

public class WebhookPolicy {

    private String url;

    private ServiceReference service;

    private byte[] caBundle;

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ServiceReference getService() {
        return this.service;
    }

    public void setService(ServiceReference service) {
        this.service = service;
    }

    public byte[] getCaBundle() {
        return this.caBundle;
    }

    public void setCaBundle(byte[] caBundle) {
        this.caBundle = caBundle;
    }
}
