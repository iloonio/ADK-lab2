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
      int[][] d = new int[l1][40];

      // initially set values to 0.
      for(int i = 0; i < l1; i++){
          for(int j = 0; j < 40; j++){
              d[i][j] = 0;
          }
      }
      // Set values to str splice length (0 through l1). This is because for an empty string vs splice, the
      // distance to the string is always determined by the splice length.
      for(int i = 0; i < l1; i++){
          d[i][0] = i;
      }
      for(int i = 0; i < 40; i++){
          d[0][i] = i;
      }

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
  int[][] wagnerFischer(String w1, String w2){
      int[][] d = prepMatrix(w1.length());
      int subCost;

      for(int j = 0; j < w1.length(); j++){
          for(int i = 0; i < w2.length(); i++){
              if(w1.charAt(i) == w2.charAt(j)){
                subCost = 0;
              } else {
                  subCost = 1;
              }
              //gives the smallest of the 3 values.
              d[i][j] = Math.min(Math.min(
                                d[i-1][j] + 1,
                                d[i][j-1] + 1),
                                d[i-1][j-1] + subCost
              );
          }
      }

      return d;
  }

  int partDist(String w1, String w2, int w1len, int w2len) {
    if (w1len == 0)
      return w2len;
    if (w2len == 0)
      return w1len;
    int res = partDist(w1, w2, w1len - 1, w2len - 1) + 
	(w1.charAt(w1len - 1) == w2.charAt(w2len - 1) ? 0 : 1);
    int addLetter = partDist(w1, w2, w1len - 1, w2len) + 1;
    if (addLetter < res)
      res = addLetter;
    int deleteLetter = partDist(w1, w2, w1len, w2len - 1) + 1;
    if (deleteLetter < res)
      res = deleteLetter;
    return res;
  }

  int distance(String w1, String w2) {
    return partDist(w1, w2, w1.length(), w2.length());
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
