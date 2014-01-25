package com.oscarvarto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;

/**
 * Created by oscarvarto on 1/21/14.
 */
public class ControlFlowJava {
    public static void main(String[] args) {
      /*
      for (int i= 0; i < 5; i++) {
          System.out.println(i);
      }
      */

      List<Integer> xs = Arrays.asList(1, 2, 3, 4, 5);
      List<Integer> ys = new ArrayList<Integer>();
      for (int x: xs) {
        ys.add(x * 2);
      }

      List<Integer> zs = xs.stream().map(x -> 2*x).collect(toList());
    }
}
