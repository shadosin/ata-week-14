package com.kenzie.dynamodbscan.icecream.extension;

import java.util.List;

/**
 * The {@code java.util.List} interface has a few methods that take lambdas directly.  Can you use just
 * these methods to solve the problems below and make the tests in {@code LambdaListsTests} pass?
 *
 * All of these problems can be solved by just using the two methods below:
 *
 * list.replaceAll(lambda) -- calls the lambda once for each item in the list, storing the result back
 * into the list (or other type of collection).
 *
 * list.removeIf(lambda) -- calls the lambda once for each item in the collection, removing each item
 * where the lambda returns true.
 *
 * Remove the 'throw new UnsupportedOperationException();' and implement the correct logic.
 */
public class LambdaLists {

    /**
     * Given a list of integers, return a list where each integer is multiplied by 2.
     *
     *
     * doubling([1, 2, 3]) → [2, 4, 6]
     * doubling([6, 8, 6, 8, -1]) → [12, 16, 12, 16, -2]
     * doubling([]) → []
     *
     * @param nums The list of Integers to process.
     * @return A list as described above.
     */
    public List<Integer> doubling(List<Integer> nums) {
        nums.replaceAll(num -> num * 2);
        return nums;
    }

    /**
     * Given a list of integers, return a list where each integer is multiplied with itself.
     *
     * square([1, 2, 3]) → [1, 4, 9]
     * square([6, 8, -6, -8, 1]) → [36, 64, 36, 64, 1]
     * square([]) → []
     *
     * @param nums The list of Integers to process.
     * @return A list as described above.
     */
    public List<Integer> square(List<Integer> nums) {
        nums.replaceAll(num -> num * num);
        return nums;
    }

    /**
     * Given a list of strings, return a list where each string has "*" added at its end.
     *
     * addStar(["a", "bb", "ccc"]) → ["a*", "bb*", "ccc*"]
     * addStar(["hello", "there"]) → ["hello*", "there*"]
     * addStar(["*"]) → ["**"]
     *
     * @param strings the Strings to process.
     * @return A list of Strings as described above.
     */
    public List<String> addStar(List<String> strings) {
        strings.replaceAll(s -> s + "*");
        return strings;
    }

    /***
     * Given a list of strings, return a list where each string is replaced by 3 copies
     * of the string concatenated together.
     *
     * copies3(["a", "bb", "ccc"]) → ["aaa", "bbbbbb", "ccccccccc"]
     * copies3(["24", "a", ""]) → ["242424", "aaa", ""]
     * copies3(["hello", "there"]) → ["hellohellohello", "theretherethere"]
     *
     * @param strings the Strings to process.
     * @return A list of Strings as described above.
     */
    public List<String> copies3(List<String> strings) {
        strings.replaceAll(s -> s + s + s);
        return strings;
    }

    /**
     * Given a list of strings, return a list where each string has "y" added at its start and end.
     *
     * moreY(["a", "b", "c"]) → ["yay", "yby", "ycy"]
     * moreY(["hello", "there"]) → ["yhelloy", "ytherey"]
     * moreY(["yay"]) → ["yyayy"]
     *
     * @param strings the Strings to process.
     * @return A list of Strings as described above.
     */
    public List<String> moreY(List<String> strings) {
        strings.replaceAll(s -> "y" + s + "y");
        return strings;
    }

    /**
     * Given a list of integers, return a list where each integer is added to 1 and the result is multiplied by 10.
     *
     * math1([1, 2, 3]) → [20, 30, 40]
     * math1([6, 8, 6, 8, 1]) → [70, 90, 70, 90, 20]
     * math1([10]) → [110]
     *
     * @param nums The list of Integers to process.
     * @return A list as described above.
     */
    public List<Integer> math1(List<Integer> nums) {
        nums.replaceAll(n ->  (n + 1) * 10);
        return nums;
    }

    /**
     * Given a list of strings, return a list where each string is converted to lower case.
     *
     * lower(["Hello", "Hi"]) → ["hello", "hi"]
     * lower(["AAA", "BBB", "ccc"]) → ["aaa", "bbb", "ccc"]
     * lower(["KitteN", "ChocolaTE"]) → ["kitten", "chocolate"]
     *
     * @param strings the Strings to process.
     * @return A list of Strings as described above.
     */
    public List<String> lower(List<String> strings) {
        strings.replaceAll(String::toLowerCase);
        return strings;
    }

    /**
     * Given a list of strings, return a list where each string has all its "x"s removed.
     *
     * noX(["ax", "bb", "cx"]) → ["a", "bb", "c"]
     * noX(["xxax", "xbxbx", "xxcx"]) → ["a", "bb", "c"]
     * noX(["x"]) → [""]
     *
     * @param strings the Strings to process.
     * @return A list of Strings as described above.
     */
    public List<String> noX(List<String> strings) {
        strings.replaceAll(s -> s.replace("x", ""));
        strings.replaceAll(s -> s.replace("X", ""));
        return strings;
    }

    /**
     * Given a list of integers, return a list of the integers, omitting any that are less than 0.
     *
     * noNeg([1, -2]) → [1]
     * noNeg([-3, -3, 3, 3]) → [3, 3]
     * noNeg([-1, -1, -1]) → []
     *
     * @param nums The list of Integers to process.
     * @return A list as described above.
     */
    public List<Integer> noNeg(List<Integer> nums) {
        nums.removeIf(n ->  n < 0);
        return nums;
    }


    /**
     * Given a list of integers, return a list of the integers, omitting any that are between 13 and 19 inclusive.
     *
     * noTeen([12, 13, 19, 20]) → [12, 20]
     * noTeen([1, 14, 1]) → [1, 1]
     * noTeen([15]) → []
     *
     * @param nums The list of Integers to process.
     * @return A list as described above.
     */
    public List<Integer> noTeen(List<Integer> nums) {
        nums.removeIf(n ->  n < 20 && n > 12);
        return nums;
    }

    /**
     * Given a list of strings, return a list of the strings, omitting any string length 4 or more.
     *
     * noLong(["this", "not", "too", "long"]) → ["not", "too"]
     * noLong(["a", "bbb", "cccc"]) → ["a", "bbb"]
     * noLong(["cccc", "cccc", "cccc"]) → []
     *
     * @param strings the Strings to process.
     * @return A list of Strings as described above.
     */
    public List<String> noLong(List<String> strings) {
        strings.removeIf(s -> s.length() > 3);
        return strings;
    }

    /**
     * Given a list of strings, return a list of the strings, omitting any string that contains a "z".
     *
     * noZ(["aaa", "bbb", "aza"]) → ["aaa", "bbb"]
     * noZ(["hziz", "hzello", "hi"]) → ["hi"]
     * noZ(["hello", "howz", "are", "youz"]) → ["hello", "are"]
     *
     * @param strings the Strings to process.
     * @return A list of Strings as described above.
     */
    public List<String> noZ(List<String> strings) {
        strings.removeIf(s -> s.contains("z"));
        return strings;
    }

    /**
     * Given a list of strings, return a list of the strings, omitting any string length 3 or 4.
     *
     * no34(["a", "bb", "ccc"]) → ["a", "bb"]
     * no34(["a", "bb", "ccc", "dddd"]) → ["a", "bb"]
     * no34(["ccc", "dddd", "apple"]) → ["apple"]
     *
     * @param strings the Strings to process.
     * @return A list of Strings as described above.
     */
    public List<String> no3Or4(List<String> strings) {
        strings.removeIf(s -> s.length() == 3 || s.length() == 4);
        return strings;
    }

    /**
     * Given a list of strings, return a list where each string has "y" added at its end, omitting any resulting
     * strings that contain "yy" as a substring anywhere.
     *
     * noYY(["a", "b", "c"]) → ["ay", "by", "cy"]
     * noYY(["a", "b", "cy"]) → ["ay", "by"]
     * noYY(["xx", "ya", "zz"]) → ["xxy", "yay", "zzy"]
     *
     * @param strings the Strings to process.
     * @return A list of Strings as described above.
     */
    public List<String> noYY(List<String> strings) {
        strings.replaceAll(s -> s + "y");
        strings.removeIf(string -> string.contains("yy"));
        return strings;
    }

    /**
     * Given a list of non-negative integers, return a list of the integers multiplied by 2, omitting any of the
     * resulting integers whose right most digit is 2.
     *
     * two2([1, 2, 3]) → [4, 6]
     * two2([2, 6, 11]) → [4]
     * two2([0]) → [0]
     *
     * @param nums The list of Integers to process.
     * @return A list as described above.
     */
    public List<Integer> two2(List<Integer> nums) {
        nums.replaceAll(num -> num * 2);
        nums.removeIf(n -> n % 10 == 2);
        return nums;
    }

    /**
     * Given a list of integers, return a list of the integers squared plus 10, omitting any of
     * the resulting numbers whose right most digit is 5 or 6.
     *
     * square56([3, 1, 4]) → [19, 11]
     * square56([1]) → [11]
     * square56([2]) → [14]
     *
     * @param nums The list of Integers to process.
     * @return A list as described above.
     */
    public List<Integer> square56(List<Integer> nums) {
        nums.replaceAll(n -> n * n + 10);
        nums.removeIf(n -> n % 10 == 5);
        nums.removeIf(n -> n % 10 == 6);
        return nums;
    }

}
