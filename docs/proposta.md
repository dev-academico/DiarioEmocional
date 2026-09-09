# Diario Emocional
Um sistema que desenvolve seu emocional junto com você

## 1. Visão do produto

Declaração que responde por que o produto existe e qual problema resolve. Use este template:

```
Para adolescentes universitários
Que sofrem de saúde mental instável e falta de organização
O diário emocional 
É um aplicativo de registro de atividades e sentimentos diários
Que permite que o usuário relate suas atividades, sono e emoções
Diferente do Diário e Saúde da Apple
Nosso produto tem a conquista de pontuação diária, semelhante a streak do duolingo e controle de atividades

```

## 2. Definição do MVP

Acreditamos que os adolescentes universitários vão se sentir melhor mentalmente com o aplicativo porque vão conseguir desabafar os sentimentos.

| No MVP | Fora do MVP |
|---|---|
| Relato do dia (texto livre) | Fotos, GPS com o relato |
| Avaliação dos sentimentos do dia (escala simples, ex.: 1–5 + emoção principal) | Aspectos da vida relacionados |
| Registro de Pensamento Disfuncional (RPD) para momentos de crise (formulário guiado) | Compartilhar com o psicologo |
| Marcação de pontuação diária | Gamificação avançada (loja, ranking entre usuários, recompensas complexas) |
| Registro de higiene do sono (horas dormidas + uso de celular antes de dormir) | Múltiplos perfis e permissões |
| Preenchimento de cronograma semanal de atividades obrigatórias | Integração com wearables (smartwatch) |

## 3. Backlog inicial

Link para o backlog em formato de kanban: https://github.com/orgs/dev-academico/projects/3

## 4. Plataforma escolhida: Android

Levando em consideração o público-alvo do sistema, nesse caso, estudantes universitários, é necessário considerar o poder aquisitivo e a necessidade desses individuos, os quais geralmente se encontram em uma situação de ascensão social. De acordo com uma pesquisa realizada em 2022, pela Partyou, 56% dos entrevistados afirmaram utilizar Android, enquanto 44% deles utilizam o sistema IOS. Portanto, é possível inferir que a plataforma **Android** torna-se mais proveitosa para os usuários.
Contudo, mesmo sendo a preferível, nenhum dos componentes do grupo possível Android, sendo preciso instalar o emulador.

Segue link da pesquisa:
https://consumidormoderno.com.br/consumo-tecnologico-universitarios/

## 5. Escolha do backend: Supabase

Desde a criação do projeto, o grupo sabia que não criaria uma API própria backend, por dificuldades de tempo para criar-lá do zero. No entanto, também não foi cogitado utilizar APIs públicas, pelo empecilho de achar um nicho tão específico de diário e também falta de conformidade com os objetivos. Portanto, dentre as opções restantes, o grupo ficou em dúvida entre utilizar o Firebase ou Supabase, contudo, no final foi escolhido o **Supabase**.

Tendo em vista as funcionalidades do usuário baseadas em preenchimento de formulários, não haverá necessidade de um banco de dados não relacional, como ocorre com o Firebase, os dados possuem atributos e relações claras entre si. Além disso,  O Supabase também oferece Row Level Security (RLS), permitindo definir políticas de acesso diretamente sobre as linhas das tabelas. Dessa forma, cada usuário pode acessar somente seus próprios registros, o que é particularmente relevante para a privacidade dos dados pessoais armazenados pelo Diário Emocional.

Por fim, as consultas SQL proporcionadas pelo Supabase facilitaria a implementação do histórico e do resumo semanal, uma vez que as consultas conseguem buscar, filtrar e calcular informações a partir de vários registros.

## 6. Equipe

| Nome | Matrícula | Papel |
|---|---|---|
| Pedro Henrique de Sousa Varela | 20240023651 | infraestrutura inicial, marcar pontuação diária, cronograma de atividades
| Raquel dos Santos Garcia | 20240017860 | relato do dia, registro de pensamentos disfuncionais, higiene do sono, banco de dados no supabase

## 7. Corte de apresentação: Vídeo

Iremos enviar um vídeo sobre a apresentação, seguindo a estrutura do docs/proposta.md.

Não iremos integrar o projeto com outra disciplina.

Link do vídeo:

---
