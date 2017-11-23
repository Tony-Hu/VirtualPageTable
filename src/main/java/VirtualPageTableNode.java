public class VirtualPageTableNode {
  //private int pageNumber;
  private int frameNumber;
  private int sectorNumber;
  private boolean isValid;
  private int timeStamp;

  public int getFrameNumber() {
    return frameNumber;
  }

  public void setFrameNumber(VirtualPageTableNode anotherNode) {
    frameNumber = anotherNode.frameNumber;
  }

  public void setFrameNumber(int frameNumber) {
    this.frameNumber = frameNumber;
  }

  public int getSectorNumber() {
    return sectorNumber;
  }

  public void setSectorNumber(int sectorNumber) {
    this.sectorNumber = sectorNumber;
  }

  public boolean isValid() {
    return isValid;
  }

  public void setValid(boolean valid) {
    isValid = valid;
  }

  public int getTimeStamp() {
    return timeStamp;
  }

  public void timeStampIncreaseOne(){
    timeStamp++;
  }

  public void setTimeStampToOne(){
    timeStamp = 1;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (isValid) {
      sb.append(frameNumber).append("\t").append(isValid).append("\t")
          .append(sectorNumber).append("\t").append(timeStamp).append("\n");
    } else {// If not valid, ignore frame number, time stamp.
      sb.append("\t\t").append(isValid).append("\t")
          .append(sectorNumber).append("\t\n");
    }
    return sb.toString();
  }
}
