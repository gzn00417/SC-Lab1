 ![](https://img-blog.csdnimg.cn/20200308214236949.png)
# 2020年春季学期计算机学院《软件构造》课程Lab1实验报告

> - Software Construction Lab-1
> - Fundamental Java Programming and Testing
> - 1183710109郭茁宁
> - [CSDN博客](https://blog.csdn.net/gzn00417/article/details/104741341)

# 1 实验目标概述

本次实验通过求解三个问题，训练基本 Java 编程技能，能够利用 Java OO 开发基本的功能模块，能够阅读理解已有代码框架并根据功能需求补全代码，能够为所开发的代码编写基本的测试程序并完成测试，初步保证所开发代码的正确性。另一方面，利用 Git 作为代码配置管理的工具，学会 Git 的基本使用方法。
	基本的 Java OO 编程
	基于 Eclipse IDE 进行 Java 编程
	基于 JUnit 的测试
	基于 Git 的代码配置管理

# 2 实验环境配置
## 2.1 [Java & Eclipse & Maven 使用配置方法](https://blog.csdn.net/gzn00417/article/details/104160911)

## 2.2 [JUnit](https://blog.csdn.net/gzn00417/article/details/104163696)
# 3 实验过程
## 3.1 Magic Squares
- 幻方是一个有n*n个不同数字、且每行、每列和斜线上都有相同的和的方形结构。要求写出程序判断输入一个矩阵是否是幻方，并且构造幻方。
- Main函数有两个部分，分别是：读取5个矩阵并判断、生成一个矩阵判断后输出到文件中。在读取时，用for循环分别将字符1到5拼入地址中，输出同理。其中在生成幻方之前，判断n的合法性。
 ![](https://img-blog.csdnimg.cn/20200308214758311.png)


```java
	static final int N = 200;
	public static int[][] square = new int[N][N];
	public static boolean[] vis = new boolean[N * N + 1];

	public static void main(String[] args) throws IOException {
		for (char i = '1'; i <= '5'; i++) {
			System.out.println(i + " " + String.valueOf(isLegalMagicSquare("src/P1/txt/" + i + ".txt")));
		}
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		while (n <= 0 || n % 2 == 0) {
			System.out.println("Input Wrong");
			n = sc.nextInt();
		}
		generateMagicSquare(n);
		System.out.println("6" + " " + String.valueOf(isLegalMagicSquare("src/P1/txt/" + "6" + ".txt")));
		return;
	}
```

### 3.1.1 isLegalMagicSquare()
该函数要实现判断一个矩阵是否为幻方。
1.	读入文件
a)	创建FileReader、BufferReader、StringBuilder对象
b)	初始化line
2.	逐行将字符串转换为整型矩阵存储
a)	将非空readline的字符串分割
b)	去除头尾空格后转换为数字存储到二维数组中
c)	存储时判断该数字是否出现过
i.	出现过则报错
ii.	否则用Boolean表标记
d)	判断行列长度是否相等
3.	计算两条斜线的和并比较
a)	分别得出主对角线和次对角线的和
b)	比较
i.	若相等则记录，作为基准值
ii.	若不相等则报错
4.	计算每条纵线和横线和和并比较
a)	分别计算第i条横线和纵线的和
b)	与基准值比较
i.	不相等则报错
5.	确定是否为幻方

![](https://img-blog.csdnimg.cn/20200308214924831.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2d6bjAwNDE3,size_16,color_FFFFFF,t_70)

```java
	public static boolean isLegalMagicSquare(String fileName) throws IOException {
		File file = new File(fileName);
		FileReader reader = new FileReader(file);
		BufferedReader bReader = new BufferedReader(reader);
		StringBuilder sb = new StringBuilder();
		String line = "";

		int n = 0, m = 0;
		Arrays.fill(vis, false);
		while ((line = bReader.readLine()) != null) {
			String[] l = line.split("\t");
			m = l.length;
			for (int i = 0; i < m; i++) {
				square[n][i] = Integer.valueOf(l[i].trim());
				if (square[n][i] <= 0 || vis[square[n][i]])
					return false;
				else
					vis[square[n][i]] = true;
			}
			n++;
		}
		bReader.close();
		if (n != m)
			return false;
		int s1 = 0, s2 = 0, s = 0;
		for (int i = 0; i < n; i++) {
			s1 += square[i][i];
			s2 += square[n - i - 1][i];
		}
		if (s1 == s2)
			s = s1;
		else
			return false;
		for (int i = 0; i < n; i++) {
			s1 = s2 = 0;
			for (int j = 0; j < n; j++) {
				s1 += square[i][j];
				s2 += square[j][i];
			}
			if (s1 != s || s2 != s)
				return false;
		}
		return true;
	}
```

###  3.1.2 generateMagicSquare()
该函数要实现生成一个边长为奇数的幻方。
 - 初始化
i.	生成空矩阵
ii.	Row=0，Col=n/2
 - 循环n*n次填充矩阵
i.	将矩阵的[row, col]位置填充为i
ii.	在保证坐标在矩阵范围内的情况下使Row--，Col++
 - 打开文件，打印结果

![](https://img-blog.csdnimg.cn/20200308215001228.png)

```java
	public static boolean generateMagicSquare(int n) throws IOException {
		int magic[][] = new int[n][n];
		int row = 0, col = n / 2, i, j, square = n * n;
		for (i = 1; i <= square; i++) {
			magic[row][col] = i;
			if (i % n == 0)
				row++;
			else {
				if (row == 0)
					row = n - 1;
				else
					row--;
				if (col == (n - 1))
					col = 0;
				else
					col++;
			}
		}
		File file = new File("src/P1/txt/6.txt");
		PrintWriter output = new PrintWriter(file);
		for (i = 0; i < n; i++) {
			for (j = 0; j < n; j++)
				output.print(magic[i][j] + "\t");
			output.println();
		}
		output.close();
		return true;
	}
```

## 3.2 Turtle Graphics
 - 该任务需要我们clone已有的程序后，利用turtle按照要求画图，其中需要利用几何知识设计一些函数简化编程，最后可以发挥想象力进行Personal Art。首先分析turtle的package组成，了解类成员。

![](https://img-blog.csdnimg.cn/20200308215034920.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2d6bjAwNDE3,size_16,color_FFFFFF,t_70)

### 3.2.1 Problem 1: Clone and import
 - 打开目标存储文件夹 右键点击Git Bash
 - 输入git clone https://github.com/ComputerScienceHIT/Lab1-1183710109.git

![](https://img-blog.csdnimg.cn/20200308215103367.png)

### 3.2.2 Problem 3: Turtle graphics and drawSquare
- 该函数需要实现：已知边长，画出边长为指定数值的正方形。参数是海龟对象turtle和编程sidelength。
- 首先将海龟画笔设置为黑色。然后执行4次的前进sidelength长度、转完90度，即可完成一个边长为sidelength的正方形。下图是边长为200的正方形：

![](https://img-blog.csdnimg.cn/20200309102244663.png)

```java
    public static void drawSquare(Turtle turtle, int sideLength) {
        turtle.color(PenColor.BLACK);
        for (int i = 0; i < 4; i++) {
            turtle.forward(sideLength);
            turtle.turn(90);
        }
    }
```

### 3.2.3 Problem 5: Drawing polygons
- 该问题首先希望已知正多边形边数的情况下计算正多边形的内角度。根据几何知识可以推导得公式：
- $(double) 180.0 - (double) 360.0 / sides$
- 使用该公式，实现calculateRegularPolygonAngle，通过运行TurtleSoupTest中的Junit测试得：

![](https://img-blog.csdnimg.cn/20200309102345759.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2d6bjAwNDE3,size_16,color_FFFFFF,t_70)

```java
    public static double calculateRegularPolygonAngle(int sides) {
        return (double) 180.0 - (double) 360.0 / sides;
    }
```

- 该问题还希望已知正多变型得边数和边长画出一个正多边形。参照画正方形的方法，可以先前进sidelength，再使海龟旋转一个角度，执行“边数”次。其中，这个角度是正多边形内角的补角，利用calculateRegularPolygonAngle的功能计算出多边形内角，再用180°减去这个值即可。边长为100的正六边形效果如下：

![](https://img-blog.csdnimg.cn/20200309102443537.png)

```java
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        turtle.color(PenColor.BLACK);
        for (int i = 0; i < sides; i++) {
            turtle.forward(sideLength);
            turtle.turn((double) 180.0 - calculateRegularPolygonAngle(sides));
        }
    }
```

### 3.2.4 Problem 6: Calculating Bearings
该问题首先希望解决，已知起点和当前朝向角度，想知道到终点需要转动的角度。例如，如果海龟在（0，1）朝向 30 度，并且必须到达（0，0）它必须再转动 150 度。
1. 首先使用Math.atan2函数计算两点之间的边在坐标系的角度，减去当前朝向的角度；
2. 然后取相反数（海龟旋转的方向是顺时针，坐标轴角度的旋转角度的逆时针）；
3. 再减去90°（海龟的0°线是向上，坐标轴的0°线是向右，向右到向上要逆时针旋转90°）；
4. 最后调整为0-360°之间（可能大于360°或小于0°）。

![](https://img-blog.csdnimg.cn/20200309102532789.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2d6bjAwNDE3,size_16,color_FFFFFF,t_70)

```java
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY, int targetX,
            int targetY) {
        double angle = Math.atan2(targetY - currentY, targetX - currentX) * 180.0 / Math.PI;
        if (angle < 0)
            angle += 360.0;
        double bearing = (360 - angle + 90 >= 360 ? 90 - angle : 360 - angle + 90) - currentBearing;
        return bearing < 0 ? 360.0 + bearing : bearing;
    }
```

- 基于上一个问题，此时有若干个点，想知道从第一个点开始到第二个点，再从第二个点到第三个点……以此类推每次转向的角度。
1. 将“起点”选为第一个点（坐标为(xCoords.get(0),yCoords.get(0))）；
2. 循环n-1次（n为点的个数）
3. 每次将第i+1号点设置为“终点”，通过上一个函数计算旋转角度并存储到List中；
4. 将下一次的“起点”用当前“终点”更新，继续循环；
5. 退出循环后返回List。

![](https://img-blog.csdnimg.cn/20200309102618515.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2d6bjAwNDE3,size_16,color_FFFFFF,t_70)

```java
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
        double currentBearing = 0.0;
        int currentX = xCoords.get(0), currentY = yCoords.get(0), targetX, targetY;
        int length = xCoords.size();
        List<Double> ans = new ArrayList<>();
        for (int i = 1; i < length; i++) {
            targetX = xCoords.get(i);
            targetY = yCoords.get(i);
            ans.add(calculateBearingToPoint(currentBearing, currentX, currentY, targetX, targetY));
            currentBearing = ans.get(i - 1);
            currentX = targetX;
            currentY = targetY;
        }
        return ans;
    }
```

### 3.2.5 [Problem 7: Convex Hulls](https://blog.csdn.net/gzn00417/article/details/104553805)
#### 3.2.5.1 [凸包问题](https://blog.csdn.net/gzn00417/article/details/104553805)
#### 3.2.5.2 [算法描述](https://blog.csdn.net/gzn00417/article/details/104553805)
#### 3.2.5.3	JUnit测试结果

![](https://img-blog.csdnimg.cn/20200309102821576.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2d6bjAwNDE3,size_16,color_FFFFFF,t_70)

#### 3.2.6 [Problem 8: Personal art](https://blog.csdn.net/gzn00417/article/details/104521856)

![](https://img-blog.csdnimg.cn/20200309102950349.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2d6bjAwNDE3,size_16,color_FFFFFF,t_70)

### 3.2.7 [Submitting](https://blog.csdn.net/gzn00417/article/details/104173147)

## 3.3 Social Network
- 该任务要求设计一张社交网络图，基于连接人与人，并且能计算任意两人之间的联系情况。网络图基于两个类，分别是FriendshipGraph类和Person类。

![](https://img-blog.csdnimg.cn/20200309103715845.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2d6bjAwNDE3,size_16,color_FFFFFF,t_70)

### 3.3.1 设计/实现FriendshipGraph类
- 该类的实际意义是一张社交网络图，包括了代表每个Person的点、代表每两个Person之间联系的边、以及建立点和联系和计算距离的方法。

![](https://img-blog.csdnimg.cn/20200309103742849.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2d6bjAwNDE3,size_16,color_FFFFFF,t_70)

#### 3.3.1.1	邻接表存储结构
- 在存储社交网络时，我使用了邻接表。所有的Node被连在一起，方便查找，并补充了一个head变量用来标记首个Node。假定一个社交网络为

![](https://img-blog.csdnimg.cn/20200309103835150.png)

- 则该图转换为邻接表的示意图为：

![](https://img-blog.csdnimg.cn/20200309103857537.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2d6bjAwNDE3,size_16,color_FFFFFF,t_70)

#### 3.3.1.2	Class Node
- Node类要实现的是将一个Person转换为邻接表里的点，所以一个Node有邻接表中点的重要成员变量：下一个Node为next，对应的Person对象person，直接连接的边lastEdge，以及实现邻接表的相关方法。此外，为了能实现方法getDistance，我另外增设了vis和dis两个变量用来记录是否访问过以及与当前起点的最近距离。

![](https://img-blog.csdnimg.cn/20200309103934491.png)

- ***Class Node.Edge***
	该类是邻接表中的边，每个Edge对象存储了邻接表中的下一条边，以及对应的边的两个Person所对应的Node。
- ***Method Node.LoadData***
该方法将Person对象导入到Node中进行存储，需要的时候可以直接调用。
- ***Method Node.addNode***
该方法将相应的Node加入到Node的链表中（即邻接表图中的纵向链表）。
- ***Method Node.addNodeEdge***
该方法将新的边加入到对应的Node中，更新每个Node后的Edge链表。

```java
	/**
	 * @Class Node : A Node is linked with a particular Person based on graph
	 *        theory. TODO Build nodes and Linking persons
	 * @param next     the person joins in the graph after him/her
	 * @param person   class Person matched with
	 * @param vis      visited mark for BFS
	 * @param dis      lowest distance from a chose person
	 * @param lastEdge first edge in First-Search
	 * @method LoadData record which person THIS belongs to
	 * @method addNode add a new Node for a person
	 * @method addNodeEdge add a edge between 2 Nodes
	 */

	public class Node {
		private Node next = null;
		Person person;
		private boolean vis;
		private int dis = Integer.MAX_VALUE;

		private Edge lastEdge = null;

		public void LoadData(Person person) {
			this.person = person;
			person.node = this;
		}

		public void addNode(Node nextVertex) {
			nextVertex.next = this.next;
			this.next = nextVertex;
		}

		/**
		 * @Class Edge A edge between 2 Nodes
		 * @param origin   one Node
		 * @param terminal another Node
		 * @param nextEdge the next edge which has the same ORIGIN with THIS
		 */

		public class Edge {
			public Node origin = null, terminal = null;
			public Edge nextEdge = null;
		}

		public void addNodeEdge(Node toVertex) {
			Edge newEdge = new Edge();
			newEdge.origin = this;
			newEdge.terminal = toVertex;
			newEdge.nextEdge = this.lastEdge;
			this.lastEdge = newEdge;
		}
	}
```

#### 3.3.1.3	Method addVertex()
- 该方法目标是在社交网络图中增加一个新的节点，参数是要加入的Person类。首先，方法要对Person的名字进行判重：用哈希集合HashSet记录下已加入的所有Person的名字，每当新加入一个Person则进行判断是否在集合中；然后则新建一个Node类，使每一个Person与一个Node对应起来。

![](https://img-blog.csdnimg.cn/20200309104157972.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2d6bjAwNDE3,size_16,color_FFFFFF,t_70)

```java
	/**
	 * @method addVertex add new vertex if the person's name is unduplicated
	 * @param newPerson adding person
	 * @param head      head of Linked List of persons
	 * @param NameSet   set of persons' names, used for removing duplication
	 */

	private Node head = null;
	private HashSet<String> NameSet = new HashSet<>();

	public void addVertex(Person newPerson) {
		if (NameSet.contains(newPerson.Name)) {
			System.out.println("Person " + newPerson.Name + " already existed.");
			System.exit(0);
		}
		NameSet.add(newPerson.Name);
		Node NewVertex = new Node();
		newPerson.node = NewVertex;
		NewVertex.LoadData(newPerson);
		if (head == null)
			head = NewVertex;
		else
			head.addNode(NewVertex);
		return;
	}
```

#### 3.3.1.4	Method addEdge()
- 该方法目标是将两个Person之间进行联系，在邻接表中，用有向边来代表“有社交关系”，由于题目设定是社交默认为双向，则需要在函数中两次调用Node中的addNodeEdge方法加两个方向的边。考虑到可扩展性和可复用性，程序考虑到了“单向社交的情况”，仅需将双向加边中的“B->A”删除即可。

![](https://img-blog.csdnimg.cn/20200309104237580.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2d6bjAwNDE3,size_16,color_FFFFFF,t_70)

```java
	/**
	 * @method addEdge add edges of double directions
	 * @param a,b 2 persons being linking with an edge
	 * @param A,B 2 nodes of the 2 persons
	 */

	public void addEdge(Person a, Person b) {
		if (a == b) {
			System.out.println("They are the same one.");
			System.exit(0);
		}
		Node A = a.node, B = b.node;
		A.addNodeEdge(B);
		B.addNodeEdge(A);
		return;
	}
```

#### 3.3.1.5	Method getDistance()
- 该方法要计算任意两个Person之间的“距离”，若没有任何社交关系则输出“-1”。两个Person之间计算使用BFS，默认边权为1，则在搜索到边时加1即可，搜索到目标点退出；特殊情况根据要求输出0或-1。

![](https://img-blog.csdnimg.cn/20200309104454939.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2d6bjAwNDE3,size_16,color_FFFFFF,t_70)

```java
	/**
	 * @method getDistance
	 * @param sta path starting person
	 * @param end path ending person
	 * @return distance between 2 persons or -1 when unlinked
	 */

	public int getDistance(Person sta, Person end) {
		if (sta == end)
			return 0;
		Queue<Person> qu = new LinkedList<Person>();
		for (Node p = head; p != null; p = p.next) {
			p.vis = false;
			p.dis = 0;
		}
		sta.node.vis = true;
		for (qu.offer(sta); !qu.isEmpty();) {
			Person p = qu.poll();
			for (Node.Edge e = p.node.lastEdge; e != null; e = e.nextEdge) {
				if (!e.terminal.vis) {
					qu.offer(e.terminal.person);
					e.terminal.vis = true;
					e.terminal.dis = p.node.dis + 1;
					if (e.terminal.person == end)
						return end.node.dis;
				}
			}
		}
		return -1;
	}
```

### 3.3.2 设计/实现Person类
- 该类的目标是将每一个人对应到一个Person对象，并存储名字的信息。此外，在我的设计中，为了方便起见，我将每个Person对象在FriendshipGraph中对应的Node存储在对应Person的成员变量中。
![](https://img-blog.csdnimg.cn/20200309104525999.png)

```java
public class Person {
    public String Name;
    public FriendshipGraph.Node node = null;

    public Person(String PersonName) {
        Name = PersonName;
    }
}
```

### 3.3.3 设计/实现客户端代码main()
#### 3.3.3.1	重复名字错误测试

```java
    /**
     * TODO to test program exit when graph has the same names.
     */
    @Test
    void ExceptionProcess() {
        FriendshipGraph graph = new FriendshipGraph();

        Person a = new Person("a");
        graph.addVertex(a);

        Person b = new Person("b");
        graph.addVertex(b);

        // Person c = new Person("a");
        // graph.addVertex(c);
    }

```

#### 3.3.3.2	简单图测试

```java
    /**
     * Basic Network Test
     */
    @Test
    void GraphTest1() {
        FriendshipGraph graph = new FriendshipGraph();

        Person rachel = new Person("Rachel");
        Person ross = new Person("Ross");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");

        graph.addVertex(rachel);
        graph.addVertex(ross);
        graph.addVertex(ben);
        graph.addVertex(kramer);

        graph.addEdge(rachel, ross);
        graph.addEdge(ross, rachel);
        graph.addEdge(ross, ben);
        graph.addEdge(ben, ross);
        /*
         * System.out.println(graph.getDistance(rachel, ross));// 1
         * System.out.println(graph.getDistance(rachel, ben));// 2
         * System.out.println(graph.getDistance(rachel, rachel));// 0
         * System.out.println(graph.getDistance(rachel, kramer));// -1
         */
        assertEquals(1, graph.getDistance(rachel, ross));
        assertEquals(2, graph.getDistance(rachel, ben));
        assertEquals(0, graph.getDistance(rachel, rachel));
        assertEquals(-1, graph.getDistance(rachel, kramer));
    }

```
#### 3.3.3.3	复杂图测试

```java
    /**
     * Further Test
     */
    @Test
    void GrpahTest2() {
        FriendshipGraph graph = new FriendshipGraph();

        Person a = new Person("A");
        Person b = new Person("B");
        Person c = new Person("C");
        Person d = new Person("D");
        Person e = new Person("E");
        Person f = new Person("F");
        Person g = new Person("G");
        Person h = new Person("H");
        Person i = new Person("I");
        Person j = new Person("J");

        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);
        graph.addVertex(d);
        graph.addVertex(e);
        graph.addVertex(f);
        graph.addVertex(g);
        graph.addVertex(h);
        graph.addVertex(i);
        graph.addVertex(j);

        graph.addEdge(a, b);
        graph.addEdge(a, d);
        graph.addEdge(b, d);
        graph.addEdge(c, d);
        graph.addEdge(d, e);
        graph.addEdge(c, f);
        graph.addEdge(e, g);
        graph.addEdge(f, g);
        graph.addEdge(h, i);
        graph.addEdge(i, j);

        assertEquals(2, graph.getDistance(a, e));
        assertEquals(1, graph.getDistance(a, d));
        assertEquals(3, graph.getDistance(a, g));
        assertEquals(3, graph.getDistance(b, f));
        assertEquals(2, graph.getDistance(d, f));
        assertEquals(2, graph.getDistance(h, j));
        assertEquals(0, graph.getDistance(i, i));
        assertEquals(-1, graph.getDistance(d, j));
        assertEquals(-1, graph.getDistance(c, i));
        assertEquals(-1, graph.getDistance(f, h));
    }

```

### 3.3.4 设计/实现测试用例
#### 3.3.4.1	重复名字错误测试
- 测试当有重复名字“a”时，程序是否能终止

```java
        Person a = new Person("a");
        graph.addVertex(a);
        Person b = new Person("b");
        graph.addVertex(b);
        Person c = new Person("a");
        graph.addVertex(c);
```
#### 3.3.4.2	简单图测试
- 根据题目中的社交网络图：

![](https://img-blog.csdnimg.cn/20200309104808101.png)

- 分别测试：
1.	Rachel和Ross距离是1，Rachel和Ben距离是2
2.	Rachel和Rachel距离是0
3.	Rachel和Kramer距离是-1

#### 3.3.4.3	复杂图测试
- 设计10个点、10条边的社交网络图：

![](https://img-blog.csdnimg.cn/20200309104835706.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2d6bjAwNDE3,size_16,color_FFFFFF,t_70)

- 分别测试：
1.	AE距离2，AD距离1，AG距离3，BF距离3，DF距离2，HJ距离2
2.	II距离0
3.	DJ距离-1，CI距离-1，FH距离-1

#### 3.3.4.4	Junit测试结果

![](https://img-blog.csdnimg.cn/20200309104909162.png)

# 4 实验进度记录
- 略
# 5 实验过程中遇到的困难与解决途径

- [Java专栏](https://blog.csdn.net/gzn00417/category_9696603.html)

# 6 实验过程中收获的经验、教训、感想
- 略



