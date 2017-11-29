# Virtual Page Table Simulation<br><br>
This is another operation system simulation. For the **virtual memory management**.<br>
```
PAGE SIZE = 16
MAX SECTORs AVAILABLE = 1000
MAX FRAMEs AVAILABE = 100

Victim page selection algorithm: Least Frequently Used(LFU)
```
The program(Located in *test/java/ManualTest.java*) will:
 1. Ask user for the **page numbers** of their single program.
 2. Ask user for the **frame numbers** allocated to the program.
 3. **Randomly** generate the program counter's number(known as logical address).
 4. Look page table for the physical address. If not valid(the page not loaded into RAM yet), a **page fault** occurred, and set the time stamp to 1. If already there, increase the time stamp by 1.
 
 Note that, there are also another JUnit5 tests available in *test/java/MemoryUtilTest.java*.