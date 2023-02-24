package min.koba58.awswithspringboot.practice;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Practice {
    public static void main(String[] args) {
        int commitCount = 1000;
        int n = 1000;
        List<Integer> list = Collections.nCopies(n, 1);
        Iterator<Integer> iterator = list.iterator();
        int count = 0;

        while (iterator.hasNext()) {
            iterator.next();
            count++;

            if (commitCount == 0) {
                System.out.println("second conditional branch: %d".formatted(count));
            }else if (count % commitCount == 0 || !iterator.hasNext()) {
                System.out.println("first conditional branch: %d".formatted(count));
            }
        }
    }
}
