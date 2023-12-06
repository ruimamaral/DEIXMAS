# DEIXMAS: Exercício Prático BL253/2023

O **DEI Christmas** (ou _DEIXMAS_) é uma iniciativa do Departamento de Engenharia Informática (DEI) que consiste em sortear cabazes festivos durante a temporada natalícia. Cada cabaz é cuidadosamente elaborado, repleto de alimentos festivos e deliciosos.

Cada sorteiro do DEI Christmas admite a participação de qualquer docente de carreira ou bolseiro do Departamento, sendo que cada participante se pode inscrever em tantos sorteios quanto desejar. O conteúdo de cada cabaz é escolhido de entre uma lista de cabazes disponíveis, sendo o custo de cada um suportado pelo DEI.

De forma a facilitar a gestão dos cabazes do DEI Christmas, é necessário o desenvolvimento de uma aplicação web simples para o efeito: o **DEIXMAS: DEI Christmas Management System** (o seu exercício).

Esta aplicação deve permitir a gestão de:

- possíveis participantes em sorteios de cabazes do DEI Christmas (`Participant`), guardando, para cada um, pelo menos o seu nome, IST ID, se é docente ou bolseiro, e outros campos que considere relevantes;
- possíveis cabazes a incluir num sorteiro (`Baskets`; guardados externamente - ver abaixo);
- sorteios de cabazes do DEI Christmas (`Raffles`), guardando, para cada um, pelo menos a data de sorteio, o tema do sorteio, um poster em formato de imagem (opcionalmente), o cabaz incluído no sorteio (opção normal e opção vegetariana), participantes inscritos e outros campos que considere relevantes;

Visto que os cabazes não são confecionados no DEI, mas sim fornecidos por uma empresa externa, a base de dados da aplicação não deve conter informação sobre os cabazes, estando esta presente no sistema proprietário **_BasketStore_** (que já se encontra desenvolvido). A _BasketStore_ disponibiliza uma API REST que permitirá a integração do **DEIXMAS** com a mesma de forma transparente ao utilizador.

Deve consultar a documentação da API da _BasketStore_[\[1\]] para obter mais informações sobre a mesma e determinar como a utilizar. Por motivos de segurança, o sistema _BasketStore_ exige que cada aplicação (incluindo o **DEIXMAS**) se autentique com um _token_ - para efeitos deste exercício, deve utilizar o seu IST ID (por exemplo, `ist1103252`) como _token_. A exceção são _endpoints_ de consulta, que não necessitam de autenticação. A aplicação **DEIXMAS** deve ser tão flexível quanto o sistema _BasketStore_ permitir, no que toca à gestão de cabazes e pedidos.

Note-se que, por razões de segurança, a API da _BasketStore_ só permite atualizar e eliminar pedidos (`Orders`) a quem os criou originalmente (comparando o _token_ enviado com o _token_ original). Para além disso, a manipulação de cabazes disponíveis (`Baskets`) é, evidentemente, da responsabilidade da empresa de catering, não sendo possível a aplicações externas, portanto, adicionar, atualizar ou remover cabazes através da API da _BasketStore_.

Inicialmente, o **DEIXMAS** será utilizado apenas por funcionários dos Serviços do DEI, estando acessível apenas dentro da rede da Secretaria, não estando por isso previsto para o momento atual o desenvolvimento de autenticação e autorização. Para além disso, não se pretende, por agora, a implementação de mecanismos de auditoria.

## Implementação

O objetivo deste exercício é desenvolver a aplicação **DEIXMAS** usando Spring Boot[\[2\]] v2 (backend) e Vue.js[\[3\]] v3 (frontend).

A aplicação **DEIXMAS** deverá permitir, pelo menos:

- Visualizar todos os participantes numa tabela de consulta rápida, contendo pelo menos nome e IST ID;
- Adicionar novos participantes;
- Atualizar e remover participantes existentes;
- Visualizar todos os cabazes disponíveis numa tabela de consulta rápida, contendo pelo menos nome e preço;
- Visualizar todo os sorteios numa tabela de consulta rápida, contendo pelo menos data e tema;
- Visualizar, na mesma tabela (descrita acima) ou noutra com características semelhantes, todos os sorteios em que um participante está inscrito;
- Visualizar um sorteio em particular numa página dedicada, contendo pelo menos data, tema, poster (se existir), cabazes incluidos (normal e vegetarino) e participantes inscritos;
- Adicionar novos sorteios, com um poster opcional (uma imagem);
- Atualizar e remover sorteios existentes;
- Inscrever e desinscrever participantes em sorteios existentes;
- Submeter um pedido junto da empresa de _catering_ a avisar sobre que cabazes esta necessita de fornecer em que dias, de forma a que estes possam ser preparados e entregues no DEI (deve escolher se esta ação deve ser tomada de forma explícita pelo utilizador ou automaticamente pelo sistema nos momentos indicados).

É permitida (mas não obrigatória) a implementação de funcionalidades adicionais às acima, especialmente se considerar que algum aspeto melhoraria significativamente a _User Experience (UX)_ durante a utilização do sistema.

É-lhe fornecido **código base** que deve analisar e utilizar como ponto de partida, estando algumas das funcionalidades acima já parcial ou integralmente implementadas. Deve seguir as convenções presentes no código base. Não é necessária a implementação de testes. Será penalizada qualquer falta de validação de input do utilizador.

Deve realizar o exercício de forma modular. Serão valorizadas qualidade e estética do código e da interface web apresentada.

_Note-se que a situação descrita neste enunciado foi simplificada e não retrata necessariamente a realidade. A sua solução será usada apenas para fins de avaliação._

## Submissão

É obrigatório e alvo de avaliação a utilização de um sistema de controlo de versões (especificamente, `git`[\[4\]]) durante o desenvolvimento do exercício. Deve submeter a sua solução no repositório privado que lhe foi atribuído para o efeito, na instância de _GitLab_ da RNL[\[5\]], e que inicialmente contém o código base, do qual deve partir. Não é necessária a utilização de nenhuma funcionalidade específica do _GitLab_ (e.g., _Issues_ e _Merge Requests_), apenas da ferramenta `git` em si. Assim, ser-nos-á possível validar a sua capacidade de utilização desta ferramenta, podendo até ser-lhe benéfico durante a componente de entrevista.

Deve incluir, junto da sua solução, um ficheiro `README` que descreva o procedimento para iniciar um servidor local de testes. Pode incluir nesse ficheiro `README` qualquer informação que considere relevante para a avaliação do seu trabalho, tal como alguma eventual justificação de decisões tomadas. Não submeta artefactos irrelevantes (e.g., pasta `node_modules`).

**Prazo máximo de entrega: quinta-feira, dia 14 de dezembro de 2023, às 23h59 (hora de Lisboa).**

Não serão aceites quaisquer entregas após o prazo limite, por qualquer razão. Qualquer candidato que não entregue uma solução até ao prazo limite será automaticamente avaliado com a cotação de 0 (zero) valores na componente de seleção correspondente ao exercício prático.

**Boa sorte!**

**NOTA:** Durante os testes de adição de novos pedidos à _BasketStore_, pedimos que seja responsável nos dados utilizados, sendo este um sistema partilhado entre todos os candidatos.

## Recursos Potencialmente Úteis

- https://vuetifyjs.com/en/
- https://spring.io/guides
- https://www.javatpoint.com/spring-boot-architecture

[\[1\]]: https://prohmakas.rnl.tecnico.ulisboa.pt/basket-store
[\[2\]]: https://spring.io/projects/spring-boot
[\[3\]]: https://vuejs.org/
[\[4\]]: https://git-scm.com/
[\[5\]]: https://gitlab.rnl.tecnico.ulisboa.pt/
