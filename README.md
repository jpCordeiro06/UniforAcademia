# Unifor Academia

**Projeto acadêmico** de gerenciamento de uma academia, desenvolvido em Kotlin com Android Studio.  
Permite ao usuário (aluno, funcionário e administrador) navegar por telas de treino, frequência, lotação, chat com professor, relatórios e muito mais. Este repositório reúne todas as Activities, layouts, assets e configurações necessárias para compilar e executar o aplicativo em ambiente Android.

---

## Índice

- [Funcionalidades](#funcionalidades)  
- [Tecnologias](#tecnologias)  
- [Requisitos](#requisitos)  
- [Instalação](#instalação)  
- [Uso](#uso)  
- [Estrutura de Pastas](#estrutura-de-pastas)  
- [Licença](#licença)  

---

## Funcionalidades

1. **Menu Principal**  
   - Bottom navigation com três abas: Início, Chamar Prof, Menu.  
   - Acesso rápido a todas as seções do app.  
2. **Chamar Professor**  
   - Tela de confirmação com botões “Sim” e “Não”.  
   - Exibe mensagem de feedback imediata ao usuário.  
3. **Chat com Professor (Tutor IA)**  
   - Envio/recebimento de mensagens de texto, emojis, imagens e áudio.  
   - Interface baseada em Material CardView e RecyclerView.  
4. **Calendário de Frequência**  
   - Visualização de presença por dias da semana.  
   - Ativações de checkboxes para marcar presença/falta.  
5. **Relatórios**  
   - Gráficos semanais e mensais de uso da academia (MPAndroidChart).  
   - Download/exportação de imagens dos gráficos em PNG.  
6. **Perfil**  
   - Páginas de perfil para Aluno e Funcionário.  
   - Campos editáveis: nome, matrícula, curso, especialidade, status.  
7. **Lotação e Quantidade de Pessoas**  
   - Tela de visualização e edição de lotação por setor (musculação, dança, piscina etc.).  
   - Ajuste de mínimo, máximo e atual, com validação.  
8. **Configurações & Acessibilidade**  
   - Opções de texto, fonte, cores, movimento e legendas.  
   - Aplicação de temas escuro/claro via estilos.  

---

## Tecnologias

- **Kotlin**: código-fonte das Activities e lógica de negócio.  
- **Material Components for Android**: botões, TextInputLayout, BottomNavigationView.  
- **ConstraintLayout & LinearLayout**: layouts responsivos.  
- **CardView**: cartões de conteúdo (listagens, relatórios).  
- **MPAndroidChart**: geração de gráficos de uso semanal/mensal.  
- **ViewBinding**: acesso seguro aos elementos de layout.  
- **Git & GitHub**: controle de versão e colaboração.  

---

## Requisitos

- **Android Studio** 4.2 ou superior.  
- **Android SDK** (minSdkVersion 21, targetSdkVersion 33).  
- **JDK** 11, instalado ou embutido no Android Studio.  

---

## Instalação

1. **Clonar o repositório**  
   ```bash
   git clone https://github.com/SEU_USUARIO/unifor-academia.git
   cd unifor-academia
   ```
   Abrir no Android Studio
      
   File → Open... → selecione a pasta do projeto.
      
   Aguarde download de dependências e sincronização Gradle.
      
   Executar
   
   Conecte um dispositivo ou inicie um emulador (API 21+).
      
   Clique em Run (▶️) ou Shift+F10.

## Uso
   Navegue pelo menu inferior para experimentar cada funcionalidade.
   
   Na aba “Chamar Prof”, confirme ou cancele sua chamada.
   
   Em “Frequência”, marque os dias em que compareceu à academia.
   
   Confira relatórios em “Relatórios” e baixe as imagens dos gráficos.
   
   Edite perfil, treinos e lotação usando os botões de ação em cada tela.

## Estrutura de Pastas
unifor-academia/
├─ app/

│  ├─ src/main/java/com/example/uniforacademia/   ← Activities em Kotlin

│  ├─ src/main/res/layout/                       ← Layouts XML

│  ├─ src/main/res/drawable/                     ← Ícones e imagens

│  ├─ src/main/res/values/                       ← Cores, strings e estilos

│  └─ ...                                        ← Assets, menus, navigation

├─ build.gradle                                  ← Configuração do projeto

└─ settings.gradle                               ← Inclusão de módulos

## Licença
Este projeto está licenciado sob a MIT License. Consulte o arquivo LICENSE para detalhes.

Desenvolvido como trabalho da disciplina de Desenvol. disp. móveis na Universidade de Fortaleza (Unifor).
