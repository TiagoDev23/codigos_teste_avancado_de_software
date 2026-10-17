# language: pt

Funcionalidade: Empréstimo de livros

  Cenário: Emprestar um exemplar disponível
    Dado que existe um exemplar disponível
    Quando solicito o empréstimo
    Então o empréstimo deve ser aceito
    E o exemplar deve ficar indisponível

  Cenário: Recusar empréstimo de um exemplar já emprestado
    Dado que existe um exemplar já emprestado
    Quando solicito o empréstimo
    Então o empréstimo deve ser recusado
    E o exemplar deve ficar indisponível