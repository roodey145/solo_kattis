import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class PriorityQueue {
    private static BufferedReader _reader;
    private static PriorityQueue _instance = new PriorityQueue();
    private static int _MAX_CLASSES_LENGTH = 10;
    public static void main(String[] args) throws IOException {
        _reader = new BufferedReader(new InputStreamReader(System.in));
        int cases = Integer.parseInt(_reader.readLine());
        for(int i = 0; i < cases; i++) {
            handleCase();
            System.out.println("==============================");
        }
    }

    private static void handleCase() throws IOException {
        int people = Integer.parseInt(_reader.readLine());
        String[] personMetaData;
        Person[] persons = new Person[people];
        for(int i = 0; i < people; i++) {
            personMetaData = _reader.readLine().replace(" class", "").split(": ");
            persons[i] = _instance.new Person(personMetaData[0], calcStatus(personMetaData[1]));
        }
        Arrays.sort(persons);
        for(Person person : persons) {
            System.out.println(person.name);
        }
    }

    private static int calcStatus(String statusStr) {
        String[] statusArr = statusStr.split("-");
        int status = 0;
        int statusClassBase;
        for(int i = 0; i < _MAX_CLASSES_LENGTH; i++) {
            if(statusArr.length > i) {
                statusClassBase = statusBase(statusArr[statusArr.length - 1 - i]);
            } else {
                statusClassBase = 2;
            }
            // status += Math.pow(statusClassBase, _MAX_CLASSES_LENGTH - i);
            status = status * 3 + statusClassBase;
        }

        return status;
    }

    private static int statusBase(String statusClass) {
        if(statusClass.equals("upper")){
            return 3;
        } else if (statusClass.equals("lower")) {
            return 1;
        }

        return 2;
    }

    class Person implements Comparable<Person> {
        String name;
        int status;
        Person(String name, int status) {
            this.name = name;
            this.status = status;
        }

        @Override
        public int compareTo(Person o) {
            int results =  Integer.compare(o.status, status);

            if(results == 0) {
                return name.compareTo(o.name);
            }

            return results;
        }
    }
}