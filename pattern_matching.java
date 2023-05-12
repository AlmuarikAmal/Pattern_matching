import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class pattern_matching{
       // Number of comparisons made by BF algorithm
      public static int numberOfComparisons;// Number of comparisons made for all algorithms 
      // 1- Main Class 
      
    public static void main(String[] args){
      
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Please Enter the sequence: ");
        String text = scanner.nextLine(); // read string input from user
        text = text.toLowerCase();
        // input sequence,pattern from the user 
        System.out.println("Please Enter the pattern: ");
        String pattern = scanner.nextLine(); // read string input from user
        pattern = pattern.toLowerCase();
        System.out.println("Pick number from the following to apply searcch for the required algorithm");
        System.out.println("choose (1) for brute force algorithm  \nchoose (2) for smart shift algorithm \nchoose (3) for Aho-Corasick(AC) Algorithm\nchoose (4) for exit");
        int choice = scanner.nextInt();
        do{
        switch(choice){
            case 1:
            int answer = BF(text , pattern);
            if(answer != -1){ 
                System.out.println("The pattern exist");
            }
            else{
                System.out.println("The pattern does not exist");  
            }
            break;
            case 2:
                // finding pattern using KMP approach
                int answer_1 = smart_shift(text , pattern);
                if(answer_1 != -1){ 
                System.out.println("The pattern exist in the sequence");  
                }
                else{
                    System.out.println("The pattern does not exist in the sequence");   
                }
                break; 
                case 3:
                   patternTrie(pattern);
                   ArrayList<Integer> answer_2 = search(text);

                	break;
            default:
                System.out.println("Invalid Input \nHint:Pick From the mentioned Algorithms");
        }
    }while(choice > 0 && choice >=4);
        scanner.close();
    }
    // Finding pattern 'Pattern' within 'text' using brute force approach
    public static int BF(String text, String pattern) { 
        
        int n = text.length(); // text length 
        int m = pattern.length(); // pattern length 
        int count = 0; // Counter for number of occurrences
        numberOfComparisons = 0; // number of comparisons made 

        for(int i = 0 ; i <= n-m ; i++){
            // Check if window matches pattern
            for(int j = 0 ; j < m ; j++){
    	        numberOfComparisons++; // Increment counter for each comparison made
                // compare the characters of pattern and sub-sequence
                if(pattern.charAt(j) != text.charAt(i+j)){
                    break; /*  exit the loop if mismatch occurs*/
                }
             // pattern is found
                if(j == m - 1){
                	count++;
                    System.out.println("The number of comparisons made by brute force algorithm is {"+numberOfComparisons+"}");
                    System.out.println("The match occur at index "+i);
                    return i;

                }
        }
        }
    
        System.out.println("The number of comparisons made by brute force algorithm is {"+numberOfComparisons+"}");
        return -1; // pattern not found 
    }
    public static int smart_shift(String Text , String Pattern) { 
        
        numberOfComparisons = 0; // number of comparisons
        int n = Text.length();  // length of text
        int m = Pattern.length(); // length of pattern
        
        if (n < m) 
            return -1;
        
        int[] shift_amount = Compute_shift_amount(Pattern);
      
        int PatternPointer = 0; // pointer for pattern
        int TextPointer = 0; // pointer for text

        while(TextPointer < n){
            numberOfComparisons++;
            if (Text.charAt(TextPointer) == Pattern.charAt(PatternPointer)){
                /* matching occur, increment both pointers */
                PatternPointer++;
                TextPointer++;
                if(PatternPointer == m) {
                    /* all of the characters matched */
                    System.out.println("Pattern occurs with shift "+(TextPointer - m)) ;
                    return TextPointer - m;
                }
            }
            else{ 
                if(PatternPointer == 0){
                    /* no matching */
                    TextPointer+=1;
                }
                else{
                    /* shift only the pattern pointer , not moving the text pointer */
                    PatternPointer = shift_amount[PatternPointer-1];
                }
            }
        } 
        System.out.println("Number of comparisons made by knuth-Morris Algorithm is {"+ numberOfComparisons+"}");
        return -1;
    }
    public static int[] Compute_shift_amount(String Pattern){

           
        int m = Pattern.length();
        /*  array to represent longest proper prefix 
            That will always start at 0 "shift_amount[0] = 0"  */ 
        int[] shift_amount = new int[m]; 
        
        int PrevLPS = 0; // pointing to 0 index
        int i = 1;      // iteration will start from index 1
            
        while (i < m){
            if(Pattern.charAt(i) == Pattern.charAt(PrevLPS)){
                // length of the longest proper prefiex that is also a suffix "LPS"
                PrevLPS+=1;
                shift_amount[i] = PrevLPS;
                i+=1;
            }
            else{ 
                // only empty proper prefiex exist
                if(PrevLPS == 0){
                    shift_amount[i]= 0;
                    i+=1;
                }
                // finding longest proper prefiex for i and decrementing prevLPS
                else{
                    PrevLPS = shift_amount[PrevLPS-1];
                }
            }
        }
        // for check up printing shift amount array 
        System.out.print("The shift amount content [");
        for( int num : shift_amount){
            System.out.print(num+" ");
        }
        System.out.println("\b]");
    return shift_amount;
    }
    public  static class TrieNode {
        TrieNode[] children;
        boolean isEndOfPattern;
 
        public  TrieNode() { // constructor 
            this.children = new TrieNode[4]; // storing DNA sequence letters A,C,G,T	   
            isEndOfPattern = false;
         }
    }
    public static int charToIndex(char c) {
        switch (c) {
            case 'A':
                return 0;
            case 'C':
                return 1;
            case 'G':
                return 2;
            case 'T':
                return 3;
            case 'a':
                return 0;
            case 'c':
                return 1;
            case 'g':
                return 2;
            case 't':
                return 3;
            default:
                throw new IllegalArgumentException("not a sequence DNA char: " + c);
        }
    }
    
    public  static void patternTrie(String pattern) {
    	  // initiating the root with an empty children array 
          TrieNode root = new TrieNode();
        
    	// first we check the pattern input if it is null or if its empty to avoid exceptions 
    	if (pattern == null || pattern.isEmpty()) {
            throw new IllegalArgumentException("Empty input, please try again with a DNA pattern");
         }
// we start at the root node and traverse the trie, creating new nodes for each of the pattern's characters 
    	TrieNode current = new TrieNode();
    	
   for (int i = 0; i < pattern.length(); i++) { // traversing until we reach the end of the pattern
            char c = pattern.charAt(i);
            int childIndex = charToIndex(c); // get the character's position in the children array
        if (current.children[childIndex] == null) { // check if a the current node has child of this character 
             TrieNode node = new TrieNode();
            current.children[childIndex] = node;
        	// move pointer to its child 
            current = node;
         } 
        else {
        	// move pointer to its child 
            current = current.children[childIndex];
        }
         }
   current.isEndOfPattern = true;   
   System.out.println("trie is built" );

    }

    public static  ArrayList<Integer> search(String text) {
        TrieNode root = new TrieNode();

    	// to count the number of occurrences 
    	int count = 0 ;
    	// to store the first index of each time the pattern occurs 
        ArrayList<Integer> indices = new ArrayList<>();
        // search if pattern exists from this index till the end of the text 
        for (int i = 0; i < text.length(); i++) {
            TrieNode current = root;
            // search if pattern occurred 
            for (int j = i; j < text.length(); j++) {
                char c = text.charAt(j);
                int childIndex =  charToIndex(c);
                if (current.children[childIndex] == null) {
                    break;    
                }
                // move pointer to its child 
                current = current.children[childIndex];
                if (current.isEndOfPattern) {
                    count++;
                    indices.add(i);
                    break;
                }
            }
        }
        System.out.println("number of pattern occurrences:" + count);
        return indices;
    }
}
