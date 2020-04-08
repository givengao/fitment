package com.zxyun.order;

import com.zxyun.order.fluent.Fluent;
import com.zxyun.order.util.AggregationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class UserServiceApplicationTests {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceApplicationTests.class);
//	@Test
	public void contextLoads() {

	}

	private static List<Integer[]> productSortRules = new ArrayList<Integer[]>(){
		{
			add(new Integer[]{1,1, 14, -22, 18, -87, -61});
			add(new Integer[]{6, 64, -82, 26, -98, 97, 45, 23, 2});
		}
	};

	private static List<Integer> singleList = Arrays.asList(1,1, -61, 14, -22, 18, -87, 6, 64, -82, 26, -98, 97, 45, 23, 2,
			-68, 45);


	public static void main (String[] args) {
		List<Integer> integerList = new ArrayList<>();
		integerList.addAll(Arrays.asList(1,1, -61, 14, -22, 18, -87, 6, 64, -82, 26, -98, 97, 45, 23, 2,
				-68, 45));

		prettyPrint("The initial list contains1: ", AggregationUtils.sortClassify(integerList, productSortRules));

		prettyPrint("The initial list contains1: ", AggregationUtils.sortInnerClassify(integerList, productSortRules));

		prettyPrint("The initial list contains3: ", AggregationUtils.sortSingleClassify(integerList, singleList));

		List<Integer> firstFiveNega = integerList.stream().filter(negatives()).collect(Collectors.toList());

		List<Integer> firstFiveNegatives = Fluent.from(integerList).sorted(Comparator.comparing(Integer::intValue)).distinct().toList();

		Map map = Fluent.from(integerList).sorted(Comparator.comparing(Integer::intValue)).distinct().toMap(e -> e);

		Map map1 = Fluent.from(integerList).sorted(Comparator.comparing(Integer::intValue)).group(e -> e);

		Integer value =  Fluent.from(integerList).sorted(Comparator.comparing(Integer::intValue)).distinct().last().get();
		prettyPrint("The first three negative values are: ", firstFiveNegatives);
	}

	private static Function<Integer, String> transformToString() {
		return integer -> "String[" + valueOf(integer) + "]";
	}

	private static Predicate<? super Integer> negatives() {
		return integer -> integer < 0;
	}

	private static Predicate<? super Integer> positives() {
		return integer -> integer > 0;
	}

	private static <E> void prettyPrint(String prefix, Iterable<E> iterable) {
		prettyPrint(", ", prefix, iterable);
	}

	private static <E> void prettyPrint(String delimiter, String prefix,
										Iterable<E> iterable) {
		StringJoiner joiner = new StringJoiner(delimiter, prefix, ".");
		Iterator<E> iterator = iterable.iterator();
		while (iterator.hasNext()) {
			joiner.add(iterator.next().toString());
		}

		LOGGER.info(joiner.toString());
	}
}
