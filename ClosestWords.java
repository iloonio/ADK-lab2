/* Labb 2 i DD2350 Algoritmer, datastrukturer och komplexitet    */
/* Se labbinstruktionerna i kursrummet i Canvas                  */
/* Ursprunglig författare: Viggo Kann KTH viggo@nada.kth.se      */
import java.util.LinkedList;
import java.util.List;

/**
 * ClosestWords can be improved by storing pre-calculated values in a matrix.
 * Also, the base cases where either one of the strings is empty can be autofilled, skipping
 * pointless recursions.
 * initial step -> check for word len, append matrix with values on [0,n] and [m, 0].
 * final step ->  compare [m,n] between words.
 */
public class ClosestWords {
  LinkedList<String> closestWords = null;

  int closestDistance = -1;

    /**
     * creates a matrix which will be used in wagnerFischer function for
     * determining distance of two strings.
     * We use 40 as the 2nd parameter for making the matrix. this is because we will never have to make the matrix
     * bigger than that, as given by the assignment!
     * @param l1 length of the first word
     * @return a distance matrix ready for wagnerFischer.
     */
  int[][] prepMatrix(int l1){
      int[][] d = new int[l1+1][41];
      for (int i = 0; i <= l1; i++) d[i][0] = i;
      for (int j = 0; j <= 40; j++) d[0][j] = j;
      return d;
  }

    /**
     * WagnerFischer uses flood filling to compute distances, which is
     * more efficient than the pre-existing recursive approach. More can be read
     * here: <a href="https://en.wikipedia.org/wiki/Wagner%E2%80%93Fischer_algorithm">Wagner-Fischer Algorithm</a>
     * @param w1 word 1
     * @param w2 word 2, typically from a dictionary given by ClosestWords
     * @return a distance matrix for word 1 and 2.
     */
  int wagnerFischerDistance(String w1, String w2){
      int[][] d = prepMatrix(w1.length());
      int subCost;

      // loop starts at i,j = 1 because index 0 is already filled with values
      // this also avoids out-of-bounds issues.
      for(int j = 1; j <= w1.length(); j++){
          for(int i = 1; i <= w2.length(); i++){
              if(w1.charAt(i) == w2.charAt(j)){
                subCost = 0;
              } else {
                  subCost = 1;
              }
              //gives the smallest of the 3 values.
              d[i][j] = Math.min(Math.min(
                                d[i-1][j] + 1,          //delete
                                d[i][j-1] + 1),         //add
                                d[i-1][j-1] + subCost   //substitute (
              );
          }
      }

      return d[w1.length()+1][w2.length()+1];
  }

  int distance(String w1, String w2) {
    return wagnerFischerDistance(w1, w2);
    //return partDist(w1, w2, w1.length(), w2.length());
  }

  public ClosestWords(String w, List<String> wordList) {
    for (String s : wordList) {
      int dist = distance(w, s);
      // System.out.println("d(" + w + "," + s + ")=" + dist);
      if (dist < closestDistance || closestDistance == -1) {
        closestDistance = dist;
        closestWords = new LinkedList<String>();
        closestWords.add(s);
      }
      else if (dist == closestDistance)
        closestWords.add(s);
    }
  }

  int getMinDistance() {
    return closestDistance;
  }

  List<String> getClosestWords() {
    return closestWords;
  }
}
