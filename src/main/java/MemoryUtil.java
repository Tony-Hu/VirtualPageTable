
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

public class MemoryUtil {
  public static int PAGE_SIZE = 16;
  public static int MAX_SECTORS = 1000;
  public static int MAX_FRAMES = 100;

  private int programCounter;
  private int pages;
  private int frames;
  private VirtualPageTableNode[] virtualPageTableRegister;//VPTR
  private Random random;
  private Stack<Integer> availableFrames;
  private boolean showOutputInfo;


  public MemoryUtil(int pages, int frames, boolean showOutputInfo){
    this.showOutputInfo = showOutputInfo;
    this.frames = frames;
    this.pages = pages;
    availableFrames = new Stack<>();
    random = new Random(System.currentTimeMillis());//Random generator's seed is binding with system time.
    virtualPageTableRegister = new VirtualPageTableNode[pages];

    generateAvailableFrames();
    generateSectorNumbers();

    showInitialInfo();
  }

  public void nextPC(){
    programCounter = random.nextInt(PAGE_SIZE * pages);

    if (showOutputInfo){
      System.out.println("Generate new PC address is: " + programCounter);
    }
  }

  private void generateSectorNumbers(){
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < pages;){
      int nextInt = random.nextInt(MAX_SECTORS);
      if (map.containsKey(nextInt)){
        continue;
      }
      map.put(nextInt,nextInt);
      virtualPageTableRegister[i] = new VirtualPageTableNode();
      virtualPageTableRegister[i].setSectorNumber(nextInt);
      i++;
    }
  }

  private void generateAvailableFrames(){
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < frames;){
      int nextInt = random.nextInt(MAX_FRAMES);
      if (map.containsKey(nextInt)){
        continue;
      }
      map.put(nextInt,nextInt);
      availableFrames.push(nextInt);
      i++;
    }
  }

  public void executeInstruction(){
    int offset = programCounter % PAGE_SIZE;//Bit operation version: int offset = programCounter & (PAGE_SIZE - 1)
    int pageNumber = programCounter / PAGE_SIZE;
    int frameNumber = findFrameNumberInVPTR(pageNumber);
    int physicalAddress = frameNumber * PAGE_SIZE + offset;
    if (showOutputInfo){
      displayVPTR();
      System.out.println("The page number is: " + pageNumber);
      System.out.println("The offset is: " + offset);
      System.out.println("The physical address is: " + frameNumber + " * " + PAGE_SIZE + " + " + offset + " = " + physicalAddress + "\n");
    }
  }

  //If found, already in RAM(main memory). Just return the frame number.
  //If not found, read the specified page from sector. Thus, it will create a page fault.
  //And, it may have to select a victim to be swiped out from RAM.
  //Algorithm used: LFU.
  private int findFrameNumberInVPTR(int pageNumber){
    VirtualPageTableNode node = virtualPageTableRegister[pageNumber];
    if (node.isValid()) {//Already in RAM, no page fault.
      node.timeStampIncreaseOne();//LFU algorithm. If the page already in the frame, increase the time stamp by 1.
    } else if (!availableFrames.empty()){//If there still have free frames. No victim shall be selected.
      node.setFrameNumber(availableFrames.pop());
      node.setValid(true);
      node.setTimeStampToOne();//LFU algorithm. If the page is newly swapped in, re-set the time stamp to 1.

      if (showOutputInfo){//Page fault occurred.
        System.out.println("Page fault occurred! ");
      }

    } else {//If no free frames available. Select a victim page according to the LFU algorithm.
      int victimPage = selectVictimPage();
      swapTwoPages(pageNumber, victimPage);

      if (showOutputInfo){//Page fault occurred.
        System.out.println("Page fault occurred! ");
      }
    }
    return node.getFrameNumber();
  }

  //Use LFU algorithm to select victim.
  private int selectVictimPage(){
    int minimum = Integer.MAX_VALUE;
    int minIndex = 0;
    for (int i = 0; i < virtualPageTableRegister.length; i++){
      VirtualPageTableNode node = virtualPageTableRegister[i];
      int min;
      if (node.isValid() && (min = node.getTimeStamp()) < minimum){
        minIndex = i;
        minimum = min;
      }
    }
    return minIndex;
  }


  private void swapTwoPages(int pageNumber, int victimPageNumber){
    virtualPageTableRegister[victimPageNumber].setValid(false);
    virtualPageTableRegister[pageNumber].setFrameNumber(virtualPageTableRegister[victimPageNumber]);
    virtualPageTableRegister[pageNumber].setValid(true);
    virtualPageTableRegister[pageNumber].setTimeStampToOne();
  }


  public void displayVPTR(){
    System.out.println("-----VPTR-----");
    System.out.println("P#\tF#\t\tV/I\t\tSec#\tTime stamp");
    for (int i = 0; i < virtualPageTableRegister.length; i++){
      System.out.println(i + "\t" + virtualPageTableRegister[i]);
    }
  }


  public void manuallySetProgramCounter(int logicalAddress){
    programCounter = logicalAddress;
    if (showOutputInfo){
      System.out.println("Generate new PC address is: " + programCounter);
    }
  }

  public void showInitialInfo(){
    if (showOutputInfo){
      System.out.println("A simulation for the program with " + pages + " pages and " + frames + " frames created successful!");
      System.out.println("NOTE:Page size is: " + PAGE_SIZE);
      System.out.println("Max sector numbers are: " + MAX_SECTORS);
      System.out.println("Max frame numbers are: " + MAX_FRAMES);

      System.out.println("The frames being allocated are: ");
      for (int frame : availableFrames){
        System.out.print(frame + "\t");
      }
      System.out.println("\n");
    }
  }
}
