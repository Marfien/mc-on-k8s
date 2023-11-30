package dev.marfien.agones.kubernetes.api.model.autoscaling;

public class FleetAutoscalerPolicy {

  private String type;

  private BufferPolicy buffer;

  private WebhookPolicy webhook;

  private CounterPolicy counter;

  private ListPolicy list;

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public BufferPolicy getBuffer() {
    return this.buffer;
  }

  public void setBuffer(BufferPolicy buffer) {
    this.buffer = buffer;
  }

  public WebhookPolicy getWebhook() {
    return this.webhook;
  }

  public void setWebhook(WebhookPolicy webhook) {
    this.webhook = webhook;
  }

  public CounterPolicy getCounter() {
    return this.counter;
  }

  public void setCounter(CounterPolicy counter) {
    this.counter = counter;
  }

  public ListPolicy getList() {
    return this.list;
  }

  public void setList(ListPolicy list) {
    this.list = list;
  }
}
