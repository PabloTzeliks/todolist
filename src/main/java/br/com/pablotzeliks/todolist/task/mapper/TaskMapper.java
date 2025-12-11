package br.com.pablotzeliks.todolist.task.mapper;

import br.com.pablotzeliks.todolist.task.dto.TaskRequestDTO;
import br.com.pablotzeliks.todolist.task.dto.TaskResponseDTO;
import br.com.pablotzeliks.todolist.task.model.Task;
import org.springframework.stereotype.Component;

/**
 * Mapper responsável pela conversão entre DTOs e Entidades de Tarefa.
 * <p>
 * Esta classe implementa o padrão Mapper (ou Converter), isolando a lógica de
 * transformação de objetos em um componente dedicado. Isso promove a separação
 * de responsabilidades, mantendo os Services focados nas regras de negócio e
 * evitando poluição de código de conversão.
 * </p>
 * <p>
 * A conversão manual (em vez de bibliotecas como MapStruct) foi escolhida para
 * manter controle total sobre o mapeamento e facilitar a manutenção em projetos
 * de pequeno e médio porte.
 * </p>
 *
 * @author Pablo Tzeliks
 * @version 2.0.0
 * @since 1.0.0
 * @see TaskRequestDTO
 * @see TaskResponseDTO
 * @see Task
 */
@Component
public class TaskMapper {

    /**
     * Converte uma entidade Task em TaskResponseDTO.
     * <p>
     * Este método mapeia todos os campos da entidade para o DTO de resposta,
     * que será serializado e enviado ao cliente.
     * </p>
     *
     * @param task Entidade de tarefa a ser convertida
     * @return DTO de resposta com os dados da tarefa, ou null se a entidade for null
     */
    public TaskResponseDTO toResponse(Task task) {

        if (task == null) return null;

        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStartAt(),
                task.getEndAt(),
                task.getPriority(),
                task.getUserId(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }

    /**
     * Converte um TaskRequestDTO em uma entidade Task.
     * <p>
     * Este método cria uma nova instância de Task e popula apenas os campos
     * fornecidos no DTO de requisição. Campos como ID, userId, createdAt e
     * updatedAt são gerenciados pela camada de serviço e persistência.
     * </p>
     *
     * @param dto DTO de requisição contendo os dados da tarefa
     * @return Nova entidade Task com os dados do DTO, ou null se o DTO for null
     */
    public Task toEntity(TaskRequestDTO dto) {

        if (dto == null) return null;

        Task task = new Task();
        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setStartAt(dto.startAt());
        task.setEndAt(dto.endAt());
        task.setPriority(dto.priority());

        return task;
    }
}