package servlets;

import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.servlet.annotation.WebServlet;

@JMSDestinationDefinitions(
	    value = {
	        @JMSDestinationDefinition(
	            name = "java:/queue/QueuePedido",
	            interfaceName = "javax.jms.Queue",
	            destinationName = "QueuePedido"
	        ),
	        @JMSDestinationDefinition(
	            name = "java:/topic/TopicPedido",
	            interfaceName = "javax.jms.Topic",
	            destinationName = "TopicPedido"
	        )
	    })


@WebServlet("/config")
public class ServeletConfig {
	

}
