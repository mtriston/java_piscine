package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.MessageRepository;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class MessageServiceImpl implements MessageService {
    private final UsersRepository usersRepository;
    private final MessageRepository messageRepository;

    public MessageServiceImpl(UsersRepository usersRepository, MessageRepository messageRepository) {
        this.usersRepository = usersRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public void saveMessage(String msg, String username) {
        User user = usersRepository.findByUsername(username).orElse(null);
        Message message = new Message(null, user, msg, new Timestamp(System.currentTimeMillis()));
        messageRepository.save(message);
    }
}