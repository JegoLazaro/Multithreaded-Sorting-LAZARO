import java.util.*;

public class multithreading{
    public static List<Integer> OGList,s_List1, s_List2, Sorted_List;
    public static final int LIST_SIZE = 10, LIST_RANGE = LIST_SIZE + 20;//10,20,50,100 list size

    public static void main(String args[]) {
        OGList = new ArrayList<>();
        Sorted_List = new ArrayList<>(OGList.size());
        Random rand = new Random();

        for(int i = 0; i < LIST_SIZE; i++)
            OGList.add(rand.nextInt(LIST_RANGE));

        System.out.println("Original List: " + OGList);
        s_List1 = new ArrayList<>(OGList.subList(0,(LIST_SIZE) / 2));
        System.out.println("Sublist1: " + s_List1);
        s_List2 = new ArrayList<>(OGList.subList((LIST_SIZE) / 2, LIST_SIZE));
        System.out.println("Sublist2: " + s_List2);

        QuickSort qSort = new QuickSort();

        Thread1 qSortRef1 = new Thread1(qSort);
        Thread2 qSortRef2 = new Thread2(qSort);
        Thread3 qSortRef3 = new Thread3(qSort);

        qSortRef1.start();
        try {
            qSortRef1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        qSortRef2.start();
        try {
            qSortRef2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        qSortRef3.start();
    }
}

class Thread1 extends Thread{
    QuickSort qSortRef;
    Thread1(QuickSort qSortRef){
        this.qSortRef = qSortRef;
    }

    @Override
    public void run(){
        qSortRef.quickSort(multithreading.s_List1, 0, multithreading.s_List1.size() - 1);
        System.out.println("SORTED SUBLIST 1: ");
        qSortRef.printArray(multithreading.s_List1);
    }
}

class Thread2 extends Thread{
    QuickSort qSortRef;
    Thread2(QuickSort qSortRef){
        this.qSortRef = qSortRef;
    }

    @Override
    public void run(){
        qSortRef.quickSort(multithreading.s_List2, 0, multithreading.s_List2.size() - 1);
        System.out.println("\nSORTED SUBLIST 2: ");
        qSortRef.printArray(multithreading.s_List2);
    }
}

class Thread3 extends Thread{
    QuickSort qSortRef;

    Thread3(QuickSort qSortRef){
        this.qSortRef = qSortRef;
    }

    @Override
    public void run(){
        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < multithreading.s_List1.size(); i++)
            multithreading.Sorted_List.add(multithreading.s_List1.get(i));

        for (int i = 0; i < multithreading.s_List2.size(); i++)
            multithreading.Sorted_List.add(multithreading.s_List2.get(i));

        qSortRef.quickSort(multithreading.Sorted_List, 0, multithreading.Sorted_List.size() - 1);
        System.out.println("\nMERGED LIST: ");
        qSortRef.printArray(multithreading.Sorted_List);
    }
}

class QuickSort{
    static void swap(List<Integer> arr, int i, int j) {
        int temp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, temp);
    }

    static int partition(List<Integer> arr, int low, int high) {
        int pivot = arr.get(high), i = (low - 1);
        for(int j = low; j <= high - 1; j++) {
            if (arr.get(j) < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }

    static void quickSort(List<Integer> arr, int low, int high) {
        if (low < high) {
            int p = partition(arr, low, high);
            quickSort(arr, low, p-1);
            quickSort(arr, p+1, high);
        }
    }
    static void printArray(List<Integer> arr) {
        for(int i = 0; i < arr.size(); i++){
            try{
                Thread.sleep(600);
            }catch (InterruptedException e){}
            System.out.print((i+1) +".) "+ Thread.currentThread().getName() + "-> "+ arr.get(i) + "| ");
        }
    }
}