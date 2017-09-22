# Artificial_Intelligence
## 사용환경
  ###Java
  
### 1. Hill Climbing
#### 클래스 설명
 - mainClass : main함수를 담고있는 class
     * main 메소드 : 프로그램이 시작되는 메소드
 - HillClimbing : HillClimbing알고리즘이 수행되는 class
     * public Board doHillClimbing(Board board) : board를 인자로 받아 내부에서 hill climbing 알고리즘을 수행
     * public Board doHillClimbing2(Board board) : local optimal에 빠질 경우에 호출.   
이 함수에서는 heuristic이 일시적으로 더 커져도 queue에 넣음
 - Board : 각 보드의 상태를 나타내는 class
     * private int [] board : board를 나타내는 배열 index가 column, value가 row
     * private int numberOfQueen : 전체 queen의 수
     * private int heuristic : 계산된 heuristic을 가지고 있는 변수
     * private int stepOfColumn : 각 단계에서 변경시킬 차례의 column을 뜻함
     * public Board(int N) : board의 생성자 보드의 크기를 인자로 받아 랜덤한 배열 생성하는 메소드
     * public Board(Board inputBoard) : clone과 같은 기능을 하는 메소드
     * public boolean checkOtherQueen(int column, int value) : 다른 퀸과의 제약조건을 체크하는 메소드
     * public setValueOfColumn(int column, int value) : 해당 column, row에 퀸을 두는 메소드
     * public void calculateHeuristic() : heuristic function을 계산하는 메소드
     * public int compareTo(Board target) : priority queue를 사용하기 위해 필요한 메소드  
(우선순위 결정 heuristic이 낮을수록 높은 우선순위)
  
    
#### 알고리즘 설명
    맨 처음 상태의 Board는 난수를 생성하여 초기화된다.  
    그뒤에는 hill climbing 알고리즘에 따라서 Board의 상태가 바뀐다.  
    매 단계에서 해당하는 column에 row별로 퀸을 둔다고 가정하고, heuristic이 늘어나지 않으면 queue에 넣는다.  
    queue에서는 heuristic에 따라 작은 값을 가장 큰 우선순위로 가지고 있다.  
    queue가 비게 되거나, heuristic이 0인 보드를 찾을때까지 반복문은 계속된다.   
    만약 첫번째 hill climbing 메소드로 답을 찾지 못하면 doHillClimbing2메소드가 수행된다.
    두번째 메소드는 heuristic이 늘어나도 queue에 넣어 답을 찾게한다.

#### Heuristic function
    이번 과제에서 heuristic function은 전체 보드에서 제약조건에 위반되는 퀸의 숫자이다.  
    예를들어,  
    0 0 0 0  
    0 0 0 1  
    0 1 0 0  
    1 0 1 0  
    이와 같은 배열이 있을 경우 heuristic의 값은 3이다. (1이 퀸 0은 보드)  
    따라서 heuristic이 0이 되면 제약조건에 위배되는 퀸이 없으므로 답을 찾았다고 판단한다.  
    priority queue에서는 heuristic이 낮은 것이 높은 우선순위를 갖는다고 판단한다.
    
### 2. CSP
#### 클래스 설명
 - mainClass : main함수를 담고있는 class
   * main 메소드 : 프로그램이 시작되는 메소드
 - CSP : 세가지 CSP알고리즘이 수행되는 class  
   * public State doStandardCSP(State currentState): standardCSP를 수행한다. State를 인자로 받아, 알고리즘 수행 결과를 반환한다.    
  * public State doCSPFowardChecking(State currentState): CSP를 수행한다. Foward Checking을 활용하여 수행시간이 좀더 짧다. State클래스의 domainUpdate 메소드를
 이용하여 매 단계마다 domain update를 수행한다.State를 인자로 받아, 알고리즘 수행 결과를 반환한다.
  * public State doCSPArcConsistency(State currentState): ArcConsistency를 적용한 CSP를 수행한다. 내부에서 State class의  arcConsistency 메소드를 이용해 필요한 Domain update를 수행한다. State를 인자로 받아, 알고리즘 수행 결과를 반환한다.
 - State : 체스판의 상태를 나타내는 class 
  * private int []board : 체스 보드를 나타낸다.
  * private ArrayList<Integer> [] domain : 도메인(퀸이 위치할수 있는 곳)을 나타낸다.
  * private ArrayList<Integer> [] variable : 변수들을 나타낸다.
  * public void domainUpdate(int column, int row) : cloumn, row에 queen이 놓여지게 되면 다른 variable의 domain도 update가 되어야하는데, 이를 구현한 함수이다. 현재  queen이 놓여진 지점을 기준으로 다른 variable들의 domain에서 위반되는 value들을 제거한다.
  * public void arcConsistency(int column, int row) : 기본적으로 forward checking방법을 사용하고, 이 함수에서는 모든 domain이 제약조건을 위배할 가능성이 없을때까지 각 variable들의 domain을 계속 update한다. 해당 기능을 구현하기 위하여 queue를 사용하였다.
      
#### 알고리즘 설명
    맨 처음 상태의 Board는 -1로 초기화가 되어있다. 이것은 빈 상태의 체스판을 뜻한다.
각 CSP알고리즘에서, 각 단계는 MRV를 활용하여 가장 적은 domain을 가진 variable을 골라 수행된다. standard CSP에서는 아무런 foward checking없이 매 단계가 진행되며, forward checking CSP에서는 각 단계가 진행될때마다, 다른 variable들의 domain을 remove해주는 방식으로 성능을 개선하고, Arc Consistency CSP에서는  앞선 foward checking을 바탕으로 제약조건을 위배하는 선택지가 없을때까지 계속해서 domain을 update해 나가는 방식으로 성능을 개선한다.

#### 1~2분 이내에 측정 가능한 최대 N과 측정 시간에 대한 분석
다음은 N을 20으로 하였을 때의 결과이다.
>Standard CSP
Location : 0 2 4 1 3 12 14 11 17 19 16 8 15 18 7 9 6 13 5 10
Total Elapsed Time : 84.284

>CSP with Forward Checking
Location : 0 2 4 13 16 3 15 6 11 17 14 18 5 9 19 10 7 1 12 8
Total Elapsed Time : 0.005

>CSP with Arc Consistency
Location : 0 2 4 13 16 3 15 6 11 17 14 18 5 9 19 10 7 1 12 8
Total Elapsed Time : 0.015

위의 결과로 미루어 보았을때 standard CSP와 fowardchecking을 사용한 두 방법의 차이는 상당한 것을 알 수 있다.

그 뒤에 N을 25로 하였을때의 결과이다.
>Standard CSP
Location : 0 2 4 1 3 8 10 12 14 18 20 23 19 24 22 5 7 9 6 13 15 17 11 16 21
Total Elapsed Time : 42.883

>CSP with Forward Checking
Location : 0 2 4 17 23 3 15 6 18 13 22 24 5 20 11 21 7 12 1 9 14 10 8 19 16
Total Elapsed Time : 0.015

>CSP with Arc Consistency
Location : 0 2 4 17 23 3 15 6 18 13 22 24 5 20 11 21 7 12 1 9 14 10 8 19 16
Total Elapsed Time : 0.029

위의 결과에서도 마찬가지로 standard CSP가 다른 두 방법보다 상당히 오래 걸리는 것을 확인할 수 있다.

### 3. Genetic Algorithm
#### 클래스 설명
 - GeneticAlgorithm.java : 유전 알고리즘을 구현한 클래스  
   * GeneticAlgorithm(int N, int p, double CR, double MR) : 클래스의 생성자로, 퀸의 수, 세대수, crossover rate, mutation rate를 인자로 받음. 초기 세대 부모를 랜덤으로 생성
   * doGeneticAlgorithm() : 유전알고리즘이 실행되는 메소드. 정답을 구할때까지 유전알고리즘을 수행. 부모 세대를 crossover, mutation하여 자식 세대를 구하는 과정을 반복합니다.
   * crossover(Status father, Status mother) : crossover를 구현한 함수로써, father와 mother를 parents에서 무작위로 두 개 선택하여 다음 세대를 만듭니다. 랜덤하게 중간 index를 골라 두 board를 섞습니다.  
예) [1, 2, 3, 4, 5] 와 [5, 4, 3, 2, 1]을 index 3에서 crossover할 경우 [1, 2, 3, 2, 1] 을 얻음
   * mutation(Status target) : 랜덤한 개수의 index들을 생성하고, 각 index마다 무작위로 value들을 골라 mutation을 구현합니다.  
예) [1, 2, 3, 4 ,5, 6]이 있고, index 가 3개 (0, 1, 5) 생성되고, 각각 (2, 5, 5)의 값이 배정되었으면 mutation 결과는 [2, 5, 3, 4, 5, 5] 입니다.
 - MatnClass.java : 메인함수를 구현
  * main(String[]args) : 프로그램 실행을 위한 메인 함수입니다. 유전알고리즘 실행을 위한 개체를 생성, 초기화하고 파일 입출력을 담당합니다.
 - SetComparator.java : 한 세대를 저장해두기 위해서 set 자료구조를 사용하는데, 이때 세대들이 fitness별로 정렬되있도록 하기 위해서 해당 내용을 구현한 클래스입니다.  
  * compare(T o1, T o2) : 각 상태를 나타내는 객체들이 fitness를 기준으로 내림차순 정렬되도록 하기 위해 사용하는 메소드입니다.
 - Status.java : 각 개체들을 나타내는 클래스입니다. Board변수는 체스판을, fitness는 정답에 얼마나 가까운지를 나타냅니다.
  * Status(int N) : 생성자입니다.
  * Status(int N, String args) : 초기 부모세대를 생성할때 이용하는 생성자입니다.
  * calculateFitness() : Fitness 를 계산하기 위하여 사용하는 함수입니다. 여기서 Fitness는 퀸의 수가 N이라면 N*(N-1)/2를 만점으로 하여 (n-queens의 정답조건) 정답조건을 위배하지 않으면 1점씩 더해지는 식으로 구성되어있습니다.
  * clone과 toString : 각각 객체를 복사하고 String형태로 만들어주는 메소드입니다.

#### 모델링  

  -  유전 알고리즘의 파라미터 : 
     * population : 한세대를 구성할 개체 수를 뜻합니다. 이 프로그램에서는 퀸의수의 세제곱에 해당하는 수를 한 세대로 생각합니다.
     * crossover rate : 한 세대 내에서 crossover를 하여 생성할 자식의 비율을 나타냅니다. 이 프로그램에서는 전체 세대의 10%는 이전 세대에서 그대로 물려오고, 90%는 새로 생성합니다.
     * mutation rate : crossover 이후 변이를 일으킬 비율을 정합니다. 이 프로그램에서는 10%에 해당하는 개체에 변이를 일으킵니다.
 - 사용한 자료구조 : 
  * parents 배열 : 한 세대에서 가장 높은순으로 부모를 뽑아 이 배열에 저장합니다. (Tournament selection)
  * epoch 트리셋 : 각 세대의 개체들을 fitness가 높은 순서대로 저장하고있습니다. 정답과 parent들을 뽑아내는데에 유용합니다.
  * Status 클래스 : 한 개체를 의미합니다. 여기서  board는 배열로 구성되는데, 배열의 index를 각 열로, value를 행으로 생각합니다. Gene은 이 배열의 한 element들로 생각하여 crossover와 mutation은 이 배열의 원소들을 조작합니다.
