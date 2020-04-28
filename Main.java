import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

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

    public static int getParentIdx(int idx){
        return (idx - 1)/2;
    }

    public static int getRightChild(int idx){
        return (idx * 2) + 2;
    }

    public static int getLeftChild(int idx){
        return (idx * 2) + 1;
    }

    public static boolean childPresent(int idx, int len){
        return getLeftChild(idx) <= len;
    }

   /* public static int[] heapify(int[] array){
        
        for(int i=array.length-1; i>=0; i--){
            if(childPresent(i, array.length) && (array[i]>array[getLeftChild(i)] || array[i]>array[getRightChild(i)])){
                if(array[getLeftChild(i)] < array[getRightChild(i)]){
                    int temp = array[getLeftChild(i)];
                    array[getLeftChild(i)] = array[i];
                    array[i] = temp;
                }
                else{
                    int temp = array[getRightChild(i)];
                    array[getRightChild(i)] = array[i];
                    array[i] = temp;
                }
            }
        }

        return array;
    } */

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

    public static int getMin(ArrayList<Integer> arr){
        /*if(arr.size() <= 0){
            return null;
        } */

        int minimum = arr.get(0);
        arr.set(0, arr.get(arr.size()-1));
        arr.remove(arr.size()-1);
        if(arr.size() > 1){
            heapifydown(arr);
        }

        return minimum;
    }

    
    public static void sort(int[] numbers, int k) {
        int[] newNumbers = new int[numbers.length];

        //ArrayList<Integer> newNumbers1 = new ArrayList<Integer>(numbers.length);
        ArrayList<Integer> lst = new ArrayList<Integer>();

       for(int i=0; i<=k; i++){
            lst.add(numbers[i]);
            heapifyup(lst);
        }
        System.out.println(lst);

        int count = 0;
        for(int i=k+1; i<numbers.length; i++){
            //System.out.println("list before cleave: " + lst);
            newNumbers[count] = getMin(lst);
            numbers[count] = newNumbers[count];
            //System.out.println("cleave " + newNumbers[count]);
            //System.out.println(lst);
            count++;
            //System.out.println(numbers[i]);
            lst.add(numbers[i]);
            //System.out.println(lst);
            heapifyup(lst);
            //System.out.println(lst);
            //break;
        }

        int lstSize = lst.size();
        for(int i=0; i<lstSize; i++){
            newNumbers[count] = getMin(lst);
            numbers[count] = newNumbers[count];
            count++;
            
        } 
       
       //System.out.println(Arrays.toString(newNumbers));
       //System.out.println(lst);
       //numbers = newNumbers.clone();
        // passed 2 cases

        // TODO implement this function
        
    }
}