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
* [Genetic Overview](#Overview)
* [Gene Expression](#GeneExpression)
* [Parallel Computation Mechanism](#ParallelComputationMechanism)
* [Class Definition](#ClassDefinition)
    * Class Diagram
    * grid
    * bird
    * stage
    * pipeUtil
    * gene
    * birdCase
    * generation
    * trainTask
    * Main
    
        
### ProblemDiscription
-------
[Flappy Bird](https://flappybird.io "Try Flappy Bird") used to be a prevailing mobile game through the past few years. In this game, players will be required to navigate their bird to pass columns of green pipes without hitting them. Players will get awarded every time the bird surpasses a column of pipe. But in fact, frequent death also tortures players after their impulsive tap spoils everything.
Genetic algorithm is a search-based optimization technique inspired by the process of natural selection and genetics. It uses the same combination of selection, crossover and mutation to evolve initial random population. In this case, we will assembly massive bird generations to explore the path towards the end. In addition, birds are encouraged to eat every available fruit randomly appearing in their path. 

### Overview
-------


### GeneExpression
-------
Gene of each bird describes a specific move at every unit step. A bird can choose to flap up, flap down, hover and stop to eat fruit. These four independent move is defined in gene class as **Enum** variable. An array is used to store seperate genes which is stochastically generated and combined in sequence.

|Genotype|phenotype|
|:---|:---|
|00|Flap Up|
|01|Hover|
|02|Flap Down|
|03|Stop and Eat|

### ParallelComputationMechanism
-------
![](/img/GA-Concurrency.jpg "Concurrency Process")

At first, the main thread achieves system initialization, and then creates five threads. For each generation, the population is split into n buckets and each thread performs the fitness evaluation, and selection for all the organisms of one bucket in parallel with all other buckets. We used a CyclicBarrier Object to make threads blocking when the thread finish running task of one generation. Until all buckets are done, the population of each bucket merges back into one sorted solution, generates new generation and releases all threads. When the generation reaches maximum number of generations. All the threads include main thread end.


### ClassDefinition
-------

#### ClassDiagram

![](/img/FlappyBird.cld.jpg "class diagram")

#### grid

This class difine the inherent property of a single grid which will be used to build the whole stage.
> 00 grid
>> 01 bird
>>> 02 ceilPipe
>>>> 03 groundPipe
>>>>> 04 ceil
>>>>>> 05 ground
>>>>>>> 06 end
>>>>>>>> 07 fruit
>>>>>>>>> 08 birdeating

#### bird

Bird class implemnts **Comparable** interface and override compareTo() method so as to make automatical sort possible in PriorityBlockingQueue<bird>. You can also check **mutate_individual()**, **crossover()** function definition here. <br>
   
**fatigued** attribute demonstrates whether or not this bird has explored the stage at once.<br>
**wholeLife** stores all the events triggered throughout last trip<br>
**load(stage stage)** transmits our birds into constant stage.<br>
**startHerEvilLIfe()** urges birds to set foot on their trip.<br>

<img src="/img/birdClass.jpg" width="280" height="500" alt="bird"/>

#### stage

Stage class taks the role of visualization and updating bird position in map. It implemnts **Clonable** interface. <br>
Different Grid Mark:
```Java
	public void draw() {
		for(int h=this.height-1;h>=0;h--) {
			for(int l=0;l<this.length;l++) {
				switch(grids[l][h].getGridStat()) {
					case 0: System.out.print("  ");break;
					case 1: System.out.print("_/");break;
					case 2: System.out.print("||");break;
					case 3: System.out.print("||");break;
					case 4: System.out.print("--");break;
					case 5: System.out.print("--");break;
					case 6: System.out.print("^");break;
					case 7: System.out.print("**");break;
					case 8: System.out.print("<o");break;
					default: System.out.print("  ");break;
				}
			}
			System.out.println();
		}
	}
```

<img src="/img/stageClass.jpg" width="280" height="500" alt="bird"/>

#### pipeUtil

Obviously **pipeUtil** is a utility class which defines functions to assist in parameter initialization in stage.

#### gene
Gene class is actually an enum class which defines gene compositions.

### birdCase
BirdCase class is actually an enum class. Stage will return the bird case everytime it moves the bird forward.

|00|01|02|03|04|05|06|
|:---|:---|:---|:---|:---|:---|:---|
|Start|TouchEnd|TouchBoundary|TouchPipe|Flying|CrossSlit|Eating|

#### generation
Generation class defines global hyper parameters used in generic algorithm:
```Java
	public static final int max_iter_step = 10000;
	public static final int generation_scale = 1000;
	public static final int stage_length = 250;
	public static final int stage_height= 15;
	public static final double mutat_ratio = 0.0001;
 ```
 Also, we implements Roulette Wheel Selection and Rank Selection in this class to pick up genes with highest adaptability.<br>
 **offSpringBreed** function is used to reproduce newborn birds from elder birds.
 <img src="/img/generationClass.jpg" width="700" height="450" alt="bird"/>
 
 #### trainTask
 **TrainTask** class makes multi-thread available in our project. As we have discussed above, five threads are drafted to run a batch of tasks of one whole generation and then combine the results at barrier.
 
 #### Main
 **Main** is the dominating controller to run a demo using in concurrent mode.
