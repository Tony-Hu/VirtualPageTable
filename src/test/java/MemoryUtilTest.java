import org.junit.jupiter.api.Test;

import java.util.Random;


public class MemoryUtilTest {

  @Test
  void testPagesEqualToFrames(){
    int pages = 3;
    MemoryUtil memoryUtil = new MemoryUtil(pages, pages, true);

    for (int i = 0; i < pages; i++){
      memoryUtil.manuallySetProgramCounter(i * MemoryUtil.PAGE_SIZE);
      memoryUtil.executeInstruction();
    }
  }

  @Test
  void testSimpleLFUSelectVictim(){
    int pages = 3;
    MemoryUtil memoryUtil = new MemoryUtil(pages, 1, true);

    for (int i = 0; i < pages; i++){
      memoryUtil.manuallySetProgramCounter(i * MemoryUtil.PAGE_SIZE);
      memoryUtil.executeInstruction();
    }
  }

}
