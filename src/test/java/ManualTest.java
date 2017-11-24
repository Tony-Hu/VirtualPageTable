import java.util.Scanner;

public class ManualTest {
  public static void main(String[] args) {
    System.out.println("How many pages does your program has?");
    Scanner scanner = new Scanner(System.in);
    int pages = Integer.parseInt(scanner.nextLine());
    System.out.println("How many frames are available?");
    int frames = Integer.parseInt(scanner.nextLine());
    MemoryUtil memoryUtil = new MemoryUtil(pages, frames, true);
    System.out.println("Initially, the VPTR is as follows:");
    memoryUtil.displayVPTR();

    while (true){
      System.out.println("\nContinue? (y/n)");
      if ("n".equals(scanner.nextLine())){
        break;
      }

      memoryUtil.nextPC();
      memoryUtil.executeInstruction();
    }
  }
}
