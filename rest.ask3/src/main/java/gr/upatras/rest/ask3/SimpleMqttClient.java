package gr.upatras.rest.ask3;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleMqttClient implements MqttCallback {
	MqttClient myClient;
	MqttConnectOptions connOpt;
	
	static final String M2MIO_THING = UUID.randomUUID().toString();
	static final String BROKER_URL = "tcp://test.mosquitto.org:1883";
	public static final String TOPIC = "grupatras/1072755/exercise3";
	private static final Logger log = LoggerFactory.getLogger(SimpleMqttClient.class);
	
	public void connectionLost(Throwable t) {
		log.info("Connection lost");
	}
	public void deliveryComplete(IMqttDeliveryToken token) {
		
	}
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		log.info("\n");
		log.info("-------------------------------------------------");
		log.info("| Topic:" + topic);
		log.info("| Message: " + new String(message.getPayload()));
		log.info("-------------------------------------------------");
		log.info("\n");
	}
	
	public void runClient(Message messageToSend) {
		// Setup MQTT client
		String clientID = M2MIO_THING;
		connOpt = new MqttConnectOptions();
		connOpt.setCleanSession(true);
		connOpt.setKeepAliveInterval(30);
		
		// Connect to broker
		try {
			myClient = new MqttClient(BROKER_URL, clientID);
			myClient.setCallback(this);
			myClient.connect(connOpt);
		}
		catch (MqttException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		log.info("Connected to" + BROKER_URL);
		
		// Setup topic
		String myTopic = TOPIC;
		MqttTopic topic = myClient.getTopic(myTopic);
		int pubQoS = 0;
		
		// Publish message
		MqttMessage message = new MqttMessage(messageToSend.getText().getBytes());
		message.setQos(pubQoS);
		message.setRetained(false);
		
		log.info("Publishing to topic \"" + topic + "\" qos " + pubQoS + "\"text " + messageToSend.getText());
		
		MqttDeliveryToken token = null;
		try {
			token = topic.publish(message);
			token.waitForCompletion();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		// Disconnect
		try {
			myClient.disconnect();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}