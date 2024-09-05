package com.waldir.taskmanager;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Comparator;
import java.util.stream.Collectors;

public class TaskManagerSwingApp {
    private TaskManager taskManager;
    private DefaultTableModel tableModel;
    private JTable taskTable;
    private JComboBox<String> categoryComboBox;
    private JLabel statusLabel;
    private DateTimeFormatter brazilianDateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public TaskManagerSwingApp() {
        taskManager = new TaskManager();

        // Criação da Janela
        JFrame frame = new JFrame("Gerenciador de Tarefas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Configuração do Layout
        frame.setLayout(new BorderLayout());

        // Tabela de Tarefas
        String[] columnNames = {"ID", "Descrição", "Data", "Categoria", "Completada"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4) {
                    return Boolean.class;  // Define a coluna "Completada" como tipo Boolean para exibir a caixa de seleção
                }
                return super.getColumnClass(columnIndex);
            }
        };
        taskTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(taskTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Detecta alterações na coluna "Completada" e atualiza o status da tarefa
        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getColumn() == 4 && e.getType() == TableModelEvent.UPDATE) {
                    int row = e.getFirstRow();
                    Boolean isCompleted = (Boolean) tableModel.getValueAt(row, 4);
                    int taskId = (Integer) tableModel.getValueAt(row, 0);
                    taskManager.markTaskAsCompleted(taskId, isCompleted);
                    updateTaskDisplay(taskManager.getAllTasks());  // Atualiza a exibição para garantir consistência
                }
            }
        });

        // Painel inferior com botões e campo de texto
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Títulos dos campos
        JLabel taskLabel = new JLabel("Descrição da Tarefa:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        bottomPanel.add(taskLabel, gbc);

        JLabel dateLabel = new JLabel("Data (ddMMyyyy):");
        gbc.gridx = 1;
        gbc.gridy = 0;
        bottomPanel.add(dateLabel, gbc);

        // Campo de texto para entrada de tarefas
        JTextField taskInputField = new JTextField();
        gbc.gridx = 0;
        gbc.gridy = 1;
        bottomPanel.add(taskInputField, gbc);

        // Campo de texto para entrada da data
        JTextField dateInputField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        bottomPanel.add(dateInputField, gbc);

        // ComboBox de categorias
        String[] categories = {"Trabalho", "Pessoal", "Outros"};
        categoryComboBox = new JComboBox<>(categories);
        gbc.gridx = 2;
        gbc.gridy = 1;
        bottomPanel.add(categoryComboBox, gbc);

        // Painel de Botões
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addButton = new JButton("Adicionar Tarefa");
        buttonPanel.add(addButton);

        JButton removeButton = new JButton("Remover Tarefa");
        buttonPanel.add(removeButton);

        JButton listAllButton = new JButton("Listar Todas");
        buttonPanel.add(listAllButton);

        JButton listCompletedButton = new JButton("Listar Completadas");
        buttonPanel.add(listCompletedButton);

        JButton listNotCompletedButton = new JButton("Listar Não Completadas");
        buttonPanel.add(listNotCompletedButton);

        // Adicionando os botões no painel inferior
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        bottomPanel.add(buttonPanel, gbc);

        // Status Label para feedback
        statusLabel = new JLabel(" ");
        gbc.gridx = 0;
        gbc.gridy = 3;
        bottomPanel.add(statusLabel, gbc);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Foco no campo de texto ao abrir a janela
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent e) {
                taskInputField.requestFocus();
            }
        });

        // Função para adicionar a tarefa (extraída para evitar repetição)
        ActionListener addTaskAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String taskDescription = taskInputField.getText();
                String category = (String) categoryComboBox.getSelectedItem();
                String dateInput = dateInputField.getText();

                if (dateInput.isEmpty()) {
                    showStatusMessage("A data é obrigatória.");
                    return;
                }

                try {
                    LocalDate dueDate = parseDate(dateInput);
                    if (dueDate.isBefore(LocalDate.now())) {
                        showStatusMessage("A data não pode ser anterior ao dia atual.");
                        return;
                    }

                    if (!taskDescription.isEmpty()) {
                        Task newTask = new Task(taskManager.getAllTasks().size() + 1, taskDescription, dueDate, category, false);
                        taskManager.addTask(newTask);
                        updateTaskDisplay(taskManager.getAllTasks());
                        taskInputField.setText(""); // Limpa o campo de texto
                        dateInputField.setText(""); // Limpa o campo de data
                        showStatusMessage("Tarefa adicionada com sucesso!");
                    } else {
                        showStatusMessage("Descrição da tarefa não pode estar vazia.");
                    }
                } catch (Exception ex) {
                    showStatusMessage("Formato de data inválido. Use ddMMyyyy ou ddMMyy.");
                }
            }
        };

        // Ação para adicionar tarefa ao clicar no botão
        addButton.addActionListener(addTaskAction);

        // Ação para adicionar tarefa ao pressionar "Enter" no campo de data
        dateInputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    addTaskAction.actionPerformed(null); // Aciona a ação de adicionar tarefa
                }
            }
        });

        // Ação para remover tarefa
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String taskIdStr = JOptionPane.showInputDialog("Digite o ID da tarefa a ser removida:");
                    if (taskIdStr != null) {
                        int taskId = Integer.parseInt(taskIdStr);
                        if (taskManager.removeTask(taskId)) {
                            JOptionPane.showMessageDialog(frame, "Tarefa removida com sucesso!");
                            updateTaskDisplay(taskManager.getAllTasks());
                        } else {
                            JOptionPane.showMessageDialog(frame, "Tarefa não encontrada!");
                        }
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Por favor, insira um número válido!");
                }
            }
        });

        // Ação para listar todas as tarefas
        listAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTaskDisplay(taskManager.getAllTasks());
            }
        });

        // Ação para listar tarefas completadas
        listCompletedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTaskDisplay(taskManager.getCompletedTasks());
            }
        });

        // Ação para listar tarefas não completadas
        listNotCompletedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTaskDisplay(taskManager.getNotCompletedTasks());
            }
        });

        // Exibe a janela
        frame.setVisible(true);
    }

    // Método para formatar a data
    private LocalDate parseDate(String input) {
        DateTimeFormatter shortFormatter = DateTimeFormatter.ofPattern("ddMMyy");
        DateTimeFormatter longFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        if (input.length() == 6) {
            return LocalDate.parse(input, shortFormatter); // Formato ddMMyy
        } else if (input.length() == 8) {
            return LocalDate.parse(input, longFormatter); // Formato ddMMyyyy
        } else {
            throw new IllegalArgumentException("Formato de data inválido.");
        }
    }

    // Atualiza a área de exibição de tarefas
    private void updateTaskDisplay(List<Task> tasks) {
        tableModel.setRowCount(0); // Limpa as linhas anteriores

        // Ordena as tarefas por data antes de exibir
        List<Task> sortedTasks = tasks.stream()
                .sorted(Comparator.comparing(Task::getDueDate))
                .collect(Collectors.toList());

        for (Task task : sortedTasks) {
            Object[] rowData = {task.getId(), task.getDescription(), task.getDueDate().format(brazilianDateFormat), task.getCategory(), task.isCompleted()};
            tableModel.addRow(rowData);
        }
    }

    // Exibe a mensagem de status e oculta após 10 segundos
    private void showStatusMessage(String message) {
        statusLabel.setText(message);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                statusLabel.setText("");
            }
        }, 10000); // 10 segundos
    }

    public static void main(String[] args) {
        new TaskManagerSwingApp();
    }
}
