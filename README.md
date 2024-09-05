# Gerenciador de Tarefas

Este é um aplicativo de Gerenciamento de Tarefas simples, desenvolvido em Java com uma interface gráfica baseada no Swing. O projeto permite adicionar, listar, remover e marcar tarefas como concluídas, além de categorizar tarefas em diferentes tipos, como "Trabalho", "Pessoal" e "Outros".

## Funcionalidades

- **Adicionar Tarefas:** Você pode adicionar tarefas com uma descrição, data de vencimento e categoria.
- **Listar Tarefas:** O aplicativo permite listar todas as tarefas, incluindo tarefas concluídas e não concluídas.
- **Remover Tarefas:** Tarefas podem ser removidas pelo ID.
- **Marcar como Concluída:** As tarefas podem ser marcadas como concluídas diretamente na interface gráfica.
- **Categorias:** As tarefas são categorizadas como "Trabalho", "Pessoal" ou "Outros".
- **Validação de Data:** Não é permitido agendar uma tarefa para uma data anterior ao dia atual.
- **Ordenação por Data:** As tarefas são exibidas em ordem cronológica.
- **Feedback Visual:** O status de ações como adicionar e remover tarefas aparece em uma barra de status e desaparece após 10 segundos.

## Como Usar

### Passo 1: Clonar o Repositório
Clone o repositório do projeto para sua máquina local com o seguinte comando:

```bash
git clone https://github.com/waldirjr94/task-manager-java.git
```

### Passo 2: Abrir o Projeto na IDE

Abra o projeto em sua IDE favorita (como Eclipse ou IntelliJ). Certifique-se de que o JDK está configurado corretamente.

### Passo 3: Executar o Projeto

Execute a classe `TaskManagerSwingApp.java`, que contém a interface gráfica principal do gerenciador de tarefas.

### Passo 4: Adicionar, Listar e Gerenciar Tarefas

Na interface gráfica, você pode adicionar tarefas preenchendo os campos e clicando no botão "Adicionar Tarefa". As tarefas serão exibidas em uma tabela que pode ser filtrada para mostrar todas, apenas as concluídas ou as não concluídas.

### Passo 5: Marcar Tarefas como Concluídas

Clique na caixa de seleção na coluna "Concluída" para marcar ou desmarcar uma tarefa como concluída. A tarefa será atualizada automaticamente.


## Tecnologias Utilizadas

* **Java:** Linguagem principal usada para o desenvolvimento do aplicativo.
* **Swing:** Usado para a criação da interface gráfica.
* **JUnit:** Ferramenta para testes unitários.
* **Git:** Controle de versão.

## Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir uma _issue_ ou enviar um _pull request_.

