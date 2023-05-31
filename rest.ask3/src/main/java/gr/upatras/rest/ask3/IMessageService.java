package gr.upatras.rest.ask3;

public interface IMessageService {
	Message postMessage(String text);
	void publishMessage(Message message);
}