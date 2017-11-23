import org.junit.jupiter.api.Test;

import java.util.Random;


public class MemoryUtilTest {

  @Test
  void testRandom(){
    Random random = new Random(System.currentTimeMillis());
    for (int i = 0; i < 10; i++) {
      System.out.println(random.nextInt(160));
    }
  }

  @Test
  void testShiftOperation(){
    System.out.println((96 & 15));
  }

}
