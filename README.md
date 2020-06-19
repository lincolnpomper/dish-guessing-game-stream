

# Jogo de adivinhação de prato

  **Esta é uma aplicação para avaliar conhecimentos nas seguintes tecnologias:**

 - Orientação a Objetos (Herança, Polimorfismo, Encapsulamento)
 - Padrões de projeto (Observer, Repository, Singleton)
 - JAVA 8 (Stream, Interfaces)
 - Threads
 - Java Swing
 - JUnit
 - Leitura de arquivos CSV

---

## Descrição

Essa aplicação encontra um nome de um prato que o jogador pensa antes de começar. Se o jogador pensou num nome que o jogo ainda não sabe, ele irá aprender e na próxima iteração este novo nome poderá ser adivinhado.

---

**Para rodar o projeto:**

*Deve ser utilizado Java 11 para compilar o projeto.*

1. Clone esse repositório
2. Compile com `mvn install` e rode com `java -jar` o arquivo `com.lincolnpomper.dishguessstream-0.0.1-SNAPSHOT.jar` 
3. Outra opção pode ser importar o projeto em sua IDE de preferência e execute a classe **Main**

---

**Para testes está disponibilizado um arquivo csv com alguns dados pré-definidos.**

*Por utilizar um arquivo **Resource** diferente da aplicação principal, é possível desenvolver uma infinidade de testes para a aplicação.*

1. Se preferir, edite o arquivo `src\test\resources\foods.csv`.
2. Alterações em **IntegratedTests** podem ser necessárias.
3. Rode a classe **IntegratedTests** com JUnit.

---