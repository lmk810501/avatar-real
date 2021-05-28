package com.skt.treal.openavatar.build.api.model.vm;

import java.io.Serializable;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.MessageCreator;

public class JobMessageCreator implements MessageCreator, Serializable {

	private static final long serialVersionUID = 8769475175603088512L;

	private TextMessage message;
	
	private String jsonString;
	
	public JobMessageCreator( String jsonString ) {
		this.jsonString = jsonString;
	}

	@Override
	public Message createMessage(Session session) throws JMSException {
		Destination tempDest = session.createTemporaryQueue();
		message = session.createTextMessage();
		message.setJMSReplyTo(tempDest);
		message.setText(this.jsonString);
		return message;
	}

}
