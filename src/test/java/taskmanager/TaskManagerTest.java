package taskmanager;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.waldir.taskmanager.Task;
import com.waldir.taskmanager.TaskManager;

public class TaskManagerTest {

    private TaskManager manager;

    @Before
    public void setUp() throws Exception {
        manager = new TaskManager();
    }

    @Test
    public void testAddTask() {
        Task task = new Task(1, "Tarefa de teste", LocalDate.now().plusDays(1), "Trabalho", false);
        manager.addTask(task);
        assertEquals(1, manager.getAllTasks().size());  // Verifique se apenas 1 tarefa foi adicionada
        assertEquals(task, manager.getAllTasks().get(0));  // Verifique se a tarefa adicionada é a correta
    }

    @Test
    public void testRemoveTask() {
        Task task = new Task(1, "Tarefa de teste", LocalDate.now().plusDays(1), "Trabalho", false);
        manager.addTask(task);
        assertTrue(manager.removeTask(1));  // Verifique se a tarefa foi removida com sucesso
        assertEquals(0, manager.getAllTasks().size());  // Verifique se a lista de tarefas está vazia
    }

    @Test
    public void testMarkTaskAsCompleted() {
        Task task = new Task(1, "Tarefa de teste", LocalDate.now().plusDays(1), "Trabalho", false);
        manager.addTask(task);
        assertTrue(manager.markTaskAsCompleted(1, true));  // Corrigido para passar o segundo parâmetro
        assertTrue(manager.getAllTasks().get(0).isCompleted());  // Verifique se o status da tarefa foi atualizado
    }

    @Test
    public void testCannotMarkPastTaskAsCompleted() {
        Task task = new Task(1, "Tarefa de teste", LocalDate.now().minusDays(1), "Trabalho", false);
        manager.addTask(task);
        assertFalse(manager.markTaskAsCompleted(1, true));  // Corrigido para passar o segundo parâmetro
        assertFalse(manager.getAllTasks().get(0).isCompleted());  // Verifique se o status da tarefa não foi alterado
    }
}
