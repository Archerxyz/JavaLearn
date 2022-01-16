package JDK1_8;

import DataStruct.BinaryTree_Learn;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Stream_Learn {

    private static class Node
    {
        int data;
        Node left;
        Node right;
        int val;

        private Node(int data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

    }

    public static void main(String[] args) {

//        CreateStreamDemo();

//        FilterAndForEachStreamDemo();

//        MapStreamDemo();

//        MatchStreamDemo();

//        ReduceStreamDemo();

//        CollectStreamDemo();

//        flatMapTest();

//        anyOrder();
        System.out.println(Stream.iterate(2, f -> f + 1).limit(1).collect(Collectors.toList()));
        Node m = new Node(0, null,null);
    }


    private static void CreateStreamDemo() {
        List<String> list = new ArrayList<>();
        list.add("武汉加油");
        list.add("中国加油");
        list.add("世界加油");
        list.add("世界加油");

        // distinct() 方法是一个中间操作（去重），它会返回一个新的流（没有共同元素）
        // count() 方法是一个终端操作，返回流中的元素个数。
        long count = list.stream().distinct().count();
        System.out.println(count);


        String[] arr = new String[]{"武汉加油", "中国加油", "世界加油"};
        Stream<String> streamStringArray1 = Arrays.stream(arr);
        Stream<String> streamStringArray2 = Stream.of("中国加油", "武汉加油", "世界加油");

        System.out.println(Arrays.toString(streamStringArray1.sorted().toArray()));
        System.out.println(streamStringArray2);
    }

    private static void FilterAndForEachStreamDemo() {
        List<String> list = new ArrayList<>();
        list.add("周杰伦");
        list.add("王力宏");
        list.add("陶喆");
        list.add("林俊杰");
        // 过滤
        Stream<String> stream = list.stream().filter(element -> element.contains("王"));
        stream.forEach(System.out::println);
        list.forEach(System.out::println);
    }




    private static void MapStreamDemo() {
        List<String> list = new ArrayList<>();
        list.add("周杰伦JayChou");
        list.add("王力宏");
        list.add("陶喆");
        list.add("林俊杰");
        Stream<Integer> stream = list.stream().map(String::length);
        stream.forEach(System.out::println);


        // stream 只能被打开一次！
//        List<Integer> list2 = stream.collect(Collectors.toList());
//        list2.forEach(System.out::println);

        Set<String> temp = new HashSet<>();
        List tempList = new ArrayList<>(temp);
        

        Map<String, Integer> map = list.stream().collect(Collectors.toMap(element -> element, String::length));
        map.forEach((k, v) -> System.out.println(k + "," + v));
    }

    // flatmap把对象扁平化
    public static void flatMapTest() {
        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );
        List<Integer> collect = inputStream
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        collect.forEach(number -> System.out.print(number + ","));
    }

    // findFirst 方法在查找到需要的数据之后就会返回不再遍历数据了
    public void findFirstTest() {
        List<Integer> numberList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        Optional<Integer> firstNumber = numberList.stream()
                .findFirst();
        System.out.println(firstNumber.orElse(-1));
    }

    public void limitOrSkipTest() {
        // 生成自己的随机数流
        List<Integer> ageList = Arrays.asList(11, 22, 13, 14, 25, 26);
        ageList.stream()
                .limit(3)
                .forEach(age -> System.out.print(age + ","));
        System.out.println();

        ageList.stream()
                .skip(3)
                .forEach(age -> System.out.print(age + ","));
    }

    private static void MatchStreamDemo() {
        List<String> list = new ArrayList<>();
        list.add("周杰伦");
        list.add("王力宏");
        list.add("陶喆");
        list.add("林俊杰");

        boolean anyMatchFlag = list.stream().anyMatch(element -> element.contains("王"));
        boolean allMatchFlag = list.stream().allMatch(element -> element.length() > 1);
        boolean noneMatchFlag = list.stream().noneMatch(element -> element.endsWith("沉"));
        System.out.println(anyMatchFlag);
        System.out.println(allMatchFlag);
        System.out.println(noneMatchFlag);
    }

    private static void ReduceStreamDemo() {
        Integer[] ints = {3, 1, 2, 3};
        List<Integer> list = Arrays.asList(ints);


        // 不设起始值。返回一个运算规则。
        Optional<Integer> optional = list.stream().reduce((a, b) -> a + b);
        Optional<Integer> optional1 = list.stream().reduce(Integer::sum);

        Optional<Integer> optionalEQ = list.stream().reduce((a, b) -> a * b);

        // 没有计算结果的时候返回orElse
        System.out.println(optional.orElse(0));
        System.out.println(optional);
        System.out.println(optionalEQ.get());


        // 其中6是起始值
        int reduce = list.stream().reduce(6, (a, b) -> a + b);
        System.out.println(reduce);
        int reduce1 = list.stream().reduce(6, Integer::sum);
        System.out.println(reduce1);
    }

    // 按年龄分组
    public void groupByTest() {
        List<Integer> ageList = Arrays.asList(11, 22, 13, 14, 25, 26);
        Map<String, List<Integer>> ageGrouyByMap = ageList.stream()
                .collect(Collectors.groupingBy(age -> String.valueOf(age / 10)));
        ageGrouyByMap.forEach((k, v) -> {
            System.out.println("年龄" + k + "0多岁的有：" + v);
        });
    }

    public static void CollectStreamDemo() {
        List<String> list = new ArrayList<>();
        list.add("周杰伦");
        list.add("王力宏");
        list.add("陶喆");
        list.add("林俊杰");

        String[] strArray = list.stream().toArray(String[]::new);
        System.out.println(Arrays.toString(strArray));

        List<Integer> list1 = list.stream().map(String::length).collect(Collectors.toList());
        List<String> list2 = list.stream().collect(Collectors.toCollection(ArrayList::new));
        System.out.println(list1);
        System.out.println(list2);

        String str = String.join(", ", list);
        System.out.println(str);
    }

    // 数学计算测试

    public void mathTest() {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        IntSummaryStatistics stats = list.stream().mapToInt(x -> x).summaryStatistics();
        System.out.println("最小值：" + stats.getMin());
        System.out.println("最大值：" + stats.getMax());
        System.out.println("个数：" + stats.getCount());
        System.out.println("和：" + stats.getSum());
        System.out.println("平均数：" + stats.getAverage());
    }


    /**
     * partitioningBy
     * 按某个条件分组
     * 给一组年龄，分出成年人和未成年人
     */
    public void partitioningByTest() {
        List<Integer> ageList = Arrays.asList(11, 22, 13, 14, 25, 26);
        Map<Boolean, List<Integer>> ageMap = ageList.stream()
                .collect(Collectors.partitioningBy(age -> age > 18));
        System.out.println("未成年人：" + ageMap.get(false));
        System.out.println("成年人：" + ageMap.get(true));
    }


    // 并行流
    public static void anyOrder() {
        List<Integer> numberList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
        numberList.parallelStream().forEach(System.out::println);

        numberList.parallelStream().forEach(System.out::println);
//        Optional<Integer> firstNumber = numberList.stream()
//                .findFirst();
//        System.out.println(firstNumber.orElse(-1));
    }
}
