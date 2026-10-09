# Diario Emocional
Um sistema que desenvolve seu emocional junto com você.

## 1. Proposta do projeto
Para descobrir as funcionalidades e justificativas das escolhas de tecnologia, acesse o link: 

https://github.com/dev-academico/DiarioEmocional/blob/main/docs/proposta.md

## 2. Tecnologias utilizadas
- Banco de dados: Supabase
- Kotlin Multiplatform (Compose Multiplatform)
- IDE: Android Studio

---

## 3. Demonstração de Deep Links (Sprint 1 - T5)
O aplicativo suporta deep links para navegação direta a registros individuais. Os esquemas estão declarados no `AndroidManifest.xml` e no grafo de navegação (`AppNavigation.kt`).

### Comandos de Teste via ADB:
- **Abrir Relato Individual:**
  ```powershell
  adb shell am start -a android.intent.action.VIEW -d "diario://relato/09-09-2026"
  ```
- **Abrir Pensamento Disfuncional Individual:**
  ```powershell
  adb shell am start -a android.intent.action.VIEW -d "diario://pensamento/09-09-2026"
  ```

---

## 4. Acessibilidade (Sprint 1 - T8)
Implementações para garantir suporte a leitores de tela (TalkBack) e diretrizes do Material 3:
- **Cabeçalhos (`heading()`):** Todos os títulos de tela utilizam marcação semântica de cabeçalho.
- **Independência do Botão de Voltar no Cabeçalho:** O componente `Cabecalho` **não** utiliza `mergeDescendants = true` para que o botão de voltar (`<-`) mantenha seu foco interativo independente no leitor de tela (TalkBack), permitindo que usuários com deficiência visual o acionem facilmente sem que ele seja fundido ao texto estático.
- **Agrupamento Semântico (`mergeDescendants = true`):** Aplicado estrategicamente em cartões de listagem (`CardSistema`) e componentes de detalhes (que contêm apenas textos informativos estáticos), unificando a leitura de título e data.
- **Contraste e Tema:** Suporte completo aos modos claro e escuro utilizando paletas de cores validadas (Emerald e Zinc).
