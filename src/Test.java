import java.time.LocalDate;
import java.util.HashSet;

public class Test {
    public static void main(String[] args) {
        String s = "benidorm fest: benidorm:26-01-2022:3 :indie : pop :rock";
        Festival f = FestivalesIO.parsearLinea(s);
        System.out.println(f);

    }
}
