package br.edu.ufape.sguEditaisService.auth;

import br.edu.ufape.sguEditaisService.auth.eventos.RoleAssignmentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitAuthServiceClient {

    private final RabbitTemplate rabbitTemplate;

    public void assignRoleToUser(String userId, String clientid, String role) {
        RoleAssignmentEvent event = new RoleAssignmentEvent(userId, clientid, role);
        rabbitTemplate.convertAndSend("auth-role-exchange", "auth-role.assign", event);
    }
}
