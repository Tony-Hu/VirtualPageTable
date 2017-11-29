import org.junit.jupiter.api.Test;


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


  @Test
  void testTimeStamp(){
    int pages = 1;
    MemoryUtil memoryUtil = new MemoryUtil(pages, pages, true);

    for (int i = 0; i < 5; i++){
      memoryUtil.manuallySetProgramCounter(i);
      memoryUtil.executeInstruction();
    }
  }


  @Test
  void testSimpleSwapVictim(){
    int pages = 5;
    int frames = 2;
    MemoryUtil memoryUtil = new MemoryUtil(pages, frames, true);

    for (int i = 0; i < 5; i++){//Set page 0's time stamp to 5.
      memoryUtil.manuallySetProgramCounter(i);
      memoryUtil.executeInstruction();
    }

    memoryUtil.manuallySetProgramCounter( 2 * MemoryUtil.PAGE_SIZE);//page 1 loaded into frame.
    memoryUtil.executeInstruction();

    memoryUtil.manuallySetProgramCounter(3 * MemoryUtil.PAGE_SIZE);//The victim shall be page 1 rather than page 0.
    memoryUtil.executeInstruction();
  }



  @Test
  void testComplexSwapVictim(){
    int pages = 5;
    int frames = 2;
    MemoryUtil memoryUtil = new MemoryUtil(pages, frames, true);

    //Set page 0&1's time stamp to 5.
    for (int i = 0; i < 5; i++){
      for (int j = 0; j < frames; j++) {
        memoryUtil.manuallySetProgramCounter(j * MemoryUtil.PAGE_SIZE + i);
        memoryUtil.executeInstruction();
      }
    }

    memoryUtil.manuallySetProgramCounter( 3 * MemoryUtil.PAGE_SIZE);//Load page 3 into frame. page 0 shall be the victim.
    memoryUtil.executeInstruction();

    memoryUtil.manuallySetProgramCounter(12);//Reload page 0, time stamp shall be reset to 1.
    memoryUtil.executeInstruction();
  }

}
