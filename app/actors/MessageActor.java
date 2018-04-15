package actors;


import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.FeedResponse;
import data.Message;
import data.NewsAgentResponse;
import services.FeedService;
import services.NewsAgentService;


import java.util.UUID;

import static data.Message.Sender.BOT;
import static data.Message.Sender.USER;

public class MessageActor extends UntypedActor {
    public FeedService feedService = new FeedService();
    public NewsAgentService newsAgentService = new NewsAgentService();
    public NewsAgentResponse newsAgentResponse=new NewsAgentResponse();
    private final ActorRef out;

    public static Props props(ActorRef out) {
        return Props.create(MessageActor.class, out);
    }

    public MessageActor(ActorRef out) {
        this.out = out;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        if ((message instanceof String)&&(message!="")) {
            Message messageObject = new Message();
            messageObject.text=(String)message;
            messageObject.sender= USER;
            out.tell(mapper.writeValueAsString(messageObject), self());
            String query=newsAgentService.getNewsAgentResponse(messageObject.text,UUID.randomUUID()).query;
            FeedResponse feedResponse=feedService.getFeedQuery(query);

            messageObject.text = (feedResponse.title == null) ? "No results found" : "Showing results for: " + query;
            messageObject.feedResponse=feedResponse;
            messageObject.sender=BOT;
            out.tell(mapper.writeValueAsString(messageObject), self());

        }
        else{
            Message message1=new Message();
            message1.text="Invalid Request";
            message1.sender= BOT;
            out.tell(mapper.writeValueAsString(message1), self());

        }

    }

}