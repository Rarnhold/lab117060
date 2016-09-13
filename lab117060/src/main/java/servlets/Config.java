package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSDestinationDefinition;
import javax.jms.JMSDestinationDefinitions;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import operacao.Venda;

/*@JMSDestinationDefinitions(
	    value = {
	        @JMSDestinationDefinition(
	            name = "java:/queue/QueuePedido",
	            interfaceName = "javax.jms.Queue",
	            destinationName = "QueuePedido"
	        ),
	        @JMSDestinationDefinition(
	            name = "java:/topic/TopicVenda",
	            interfaceName = "javax.jms.Topic",
	            destinationName = "TopicVenda"
	        )
	    })*/


@WebServlet("/config")
public class Config extends HttpServlet {

	private static final long serialVersionUID = 1L; private static final int MSG_COUNT = 5;

    @Inject
    private JMSContext context;

    @Resource(lookup = "java:/queue/QueuePedido")
    private Queue queue;

    @Resource(lookup = "java:/topic/TopicVenda")
    private Topic topic;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	try {
			boolean useTopic = req.getParameterMap().keySet().contains("topic");
			final Destination destination = useTopic ? topic : queue;
			Venda venda = new Venda();
			ObjectMessage objectMessage = context.createObjectMessage();
			objectMessage.setObject((Serializable) venda);
			context.createProducer().send(destination, objectMessage);

		} catch (Throwable th) {
			th.printStackTrace();
		}
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
