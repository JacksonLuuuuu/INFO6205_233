Machine Learning Algorithm for Flappy Bird using Neural Network and Genetic Algorithm
=======
![](/img/flappy-bird-header.jpg "Flappy Bird")
# INFO6205_233

|Name|NUID|
|:---|:---|
|Jarvis(Jiawei) Yao|001858168|
|Boxuan Lu|001833213|

****
## Directory
* [Problem Discription](#ProblemDiscription)
* [Gene Expression](#GeneExpression)
    * A
    * A
        *  A
        *  A
        
### Problem Discription
-------
[Flappy Bird](https://flappybird.io "Try Flappy Bird") used to be a prevailing mobile game through the past few years. In this game, players will be required to navigate their bird to pass columns of green pipes without hitting them. Players will get awarded every time the bird surpasses a column of pipe. But in fact, frequent death also tortures players after their impulsive tap spoils everything.
Genetic algorithm is a search-based optimization technique inspired by the process of natural selection and genetics. It uses the same combination of selection, crossover and mutation to evolve initial random population. In this case, we will assembly massive bird generations to explore the path towards the end. In addition, birds are encouraged to eat every available fruit randomly appearing in their path. 

### Gene Expression
-------
Gene of each bird describes a specific move at every unit step. A bird can choose to flap up, flap down, hover and stop to eat fruit. These four independent move is defined in gene class as **Enum** variable. An array is used to store seperate genes which is stochastically generated and combined in sequence.

|Genotype|phenotype|
|:---|:---|
|00|Flap Up|
|01|Hover|
|02|Flap Down|
|03|Stop and Eat|
