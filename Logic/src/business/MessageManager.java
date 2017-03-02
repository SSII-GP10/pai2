package business;

import domain.Message;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import persistence.MessageRepository;

public class MessageManager {

    public static MessageManager instance;

    public static MessageManager getInstance() {
        if (instance == null) {
            instance = new MessageManager();
        }
        return instance;
    }

    private MessageManager() {

    }

    public void addMessage(Message message) throws SQLException {
        MessageRepository.insertMessage(message);
    }

    public Collection<Message> getSentMessages(Date date) throws SQLException {
        return MessageRepository.getSentMessages(date);
    }

    public Collection<Message> getMessagesWithNoIntegrity(Date date) throws SQLException {
        return MessageRepository.getMessagesWithNoIntegrity(date);
    }
    
    public Collection<Message> getMessagesWithIntegrity(Date date) throws SQLException {
        return MessageRepository.getMessagesWithIntegrity(date);
    }

    public Collection<Message> getAllMessages(Date date) throws SQLException {
        return MessageRepository.getAllMessages(date);
    }
        
    public Collection<Message> getAllMessages() throws SQLException {
        return MessageRepository.getAllMessages();
    }
}
