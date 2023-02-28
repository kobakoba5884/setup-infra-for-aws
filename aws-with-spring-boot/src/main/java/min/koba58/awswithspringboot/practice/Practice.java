package min.koba58.awswithspringboot.practice;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Practice {
    public static void main(String[] args) {
        int commitCount = 0;
        int n = 1000;
        List<Integer> list = Collections.nCopies(n, 1);
        Iterator<Integer> iterator = list.iterator();
        int count = 0;

        while (iterator.hasNext()) {
            iterator.next();
            count++;

            boolean isCommitRequired = commitCount == 0
                    || count % commitCount == 0
                    || !iterator.hasNext();

            if (isCommitRequired) {
                System.out.println("second conditional branch: %d".formatted(count));
            }
        }
    }
}
