# Diario Emocional
Um sistema que desenvolve seu emocional junto com você.

## 1. Proposta do projeto
Para descobrir as funcionalidades e justificativas das escolhas de tecnologia, acesse o link: 


https://github.com/dev-academico/DiarioEmocional/blob/main/docs/proposta.md

## 2.Tecnologias a serem utilizadas
- Banco de dados: Supabase
- Kotlin Multiplataform, especificamente para Android
- IDE: Android Studio

## 3. Acessibilidade (item T8 Sprint 1)
Para garantir a usabilidade, os seguintes ajustes de acessibilidade foram implementados no aplicativo:
- **Cabeçalhos de Tela:** Todos os títulos principais das telas utilizam `heading()` através do componente de cabeçalho padronizado.
- **Agrupamento Semântico:** Os cartões de listagem (`CardSistema`) e componentes de detalhes utilizam `mergeDescendants = true` para que o leitor de tela leia o título, data e conteúdo de forma unificada e fluida.
- **Alvos de Toque:** Componentes interativos seguem o padrão mínimo de $48\times 48\text{ dp}$ recomendado pelo Material Design.
- **Contraste e Temas:** O aplicativo possui suporte completo a temas claro e escuro utilizando paletas de cores com contraste validado.
