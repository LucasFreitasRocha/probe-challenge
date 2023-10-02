# probe-challenge

vamos explicar como funciona o nosso desafio:

Imagine que um desenvolvedor recebeu uma tarefa de uma pessoa da equipe de produto. A pessoa de produto queria poder controlar sondas em outros planetas por meio de comandos. Para explicar o funcionamento do produto, o seguinte exemplo foi escrito em um pedaço de papel:

## Explicação da necessidade:

    Tamanho da área do planeta : 5x5
    Posição de pouso da sonda 1: x=1, y=2 apontando para Norte Sequencia de comandos: LMLMLMLMM
    Posição final da sonda: x=1 y=3 apontando para Norte
    Posição de pouso da sonda 2: x=3, y=3 apontando para Leste Sequencia de comandos: MMRMMRMRRML
    Posição final da sonda: x=5 y=1 apontando para Norte
## Detalhes sobre o funcionamento acima:

A sequência de comandos é um conjunto de instruções enviadas da terra para a sonda, onde :
   
    M -> Andar para a frente na direção que está 1 posição.
    L -> Virar a sonda para a esquerda (90 graus)
    R -> Virar a sonda para a direita (90 graus)
    
A orientação da sonda dentro do plano cartesiano usa uma rosa dos ventos como referência:
