import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Read in k, which represents the maximum
        // distance between a number's current position
        // and sorted position
        int k = Integer.parseInt(sc.nextLine());
        
        // Read in the list of numbers
        int[] numbers;
        String input = sc.nextLine();
        if (input.equals("")) {
            numbers = new int[0];
        } else {    
            String[] numberStrings = input.split(" ");
            numbers = new int[numberStrings.length];
            for (int i = 0; i < numberStrings.length; i++) {
                numbers[i] = Integer.parseInt(numberStrings[i]);
            }
        }

        // Sort the list
        sort(numbers, k);

        // Print the sorted list
        StringBuilder resultSb = new StringBuilder();
        for (int i = 0; i < numbers.length; i++) {
            resultSb.append(new Integer(numbers[i]).toString());
            if (i < numbers.length - 1) {
                resultSb.append(" ");
            }
        }
        System.out.println(resultSb.toString());
    }

    //#########################################################
    // THESE ARE HELPER METHODS

    public static int getParentIdx(int idx){
        return (idx - 1)/2;
    }

    public static int getRightChild(int idx){
        return (idx * 2) + 2;
    }

    public static int getLeftChild(int idx){
        return (idx * 2) + 1;
    }

    // Does node have a child?
    public static boolean childPresent(int idx, int len){
        return getLeftChild(idx) < len;
    }

    //########################################################

    // This heapifies up starting with the last element added to the list
    public static void heapifyup(ArrayList<Integer> arr){
        int idx = arr.size()-1;

        while(getParentIdx(idx) >= 0){
            if(arr.get(getParentIdx(idx)) > arr.get(idx)){
                int temp = arr.get(getParentIdx(idx));
                arr.set(getParentIdx(idx), arr.get(idx));
                arr.set(idx, temp);
            }

            idx = getParentIdx(idx);
            if(idx == 0){
                break;
            }
        }
    }


    // This is another helper method that returns the index of the smallest child
    public static int getSmallerChildIdx(ArrayList<Integer> lst, int idx){
        if(getRightChild(idx) >= lst.size()){
            return getLeftChild(idx);
        }

        else{
            int leftChild = lst.get(getLeftChild(idx));
            int rightChild = lst.get(getRightChild(idx));

            if(leftChild < rightChild){
                return getLeftChild(idx);
            }
            else{
                return getRightChild(idx);
            }
        }
    }


    // This methods heapifies down starting from the root node
    public static void heapifydown(ArrayList<Integer> arr){
        int idx = 0;

        while(childPresent(idx, arr.size())){
            int smallChildIdx = getSmallerChildIdx(arr, idx);
            int child = arr.get(smallChildIdx);
            int parent = arr.get(idx);

            if(parent > child){
                arr.set(idx, child);
                arr.set(smallChildIdx, parent);
            }

            idx = smallChildIdx;
        }
    }

    // This method returns the smallest element from the heap (root node) and then calls on heapifydown
    public static int getMin(ArrayList<Integer> arr){

        int minimum = arr.get(0);
        arr.set(0, arr.get(arr.size()-1));
        arr.remove(arr.size()-1);
        if(arr.size() > 1){
            heapifydown(arr);
        }

        return minimum;
    }

    
    // This sort algorithm has a runtime of O((k+1) * logk + (n-k+1) * 2logk + (k+1) * logk)
    // So is has a simplified runtime of O(n*logk)
    public static void sort(int[] numbers, int k) {

        // an empty array to save sorted list elements
        int[] newNumbers = new int[numbers.length];

        // This will serve as the heap
        ArrayList<Integer> lst = new ArrayList<Integer>();

        // First create a heap of size k + 1
        for(int i=0; i<=k; i++){
            lst.add(numbers[i]);
            heapifyup(lst);
        }
        
        // Now remove the smallest node, heapifydown, then add a node, and then heapifyup
        int count = 0;
        for(int i=k+1; i<numbers.length; i++){
            newNumbers[count] = getMin(lst);
            numbers[count] = newNumbers[count];
            count++;
            lst.add(numbers[i]);
            heapifyup(lst);
        }

        // k+1 nodes are left so remove smallest node, then heapifydown
        int lstSize = lst.size();
        for(int i=0; i<lstSize; i++){
            newNumbers[count] = getMin(lst);
            numbers[count] = newNumbers[count];
            count++;
            
        }
        
    }
}