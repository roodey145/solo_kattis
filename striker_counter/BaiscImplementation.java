import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class BaiscImplementation {
    enum PointType {START, END}
    
    public static void main(String[] args) throws IOException {
        boolean debug = false;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BaiscImplementation maxOverlap = new BaiscImplementation();
        
        int opponents = Integer.parseInt(reader.readLine());
        String[] opponent;
        Double x;
        Double y;
        Double r;
        Double l;
        Double projectionAngle;
        Double angleRange;
        Double angleElpse = 0.06;

        PriorityQueue<OpponentPoint> pq = new PriorityQueue<>();
        // OpponentPoint start;
        // OpponentPoint end;
        double start;
        double end;

        for (int i = 0; i < opponents; i++) {
            opponent = reader.readLine().split(" ");
            x = Double.parseDouble(opponent[0]);
            y = Double.parseDouble(opponent[1]);
            r = Double.parseDouble(opponent[2]);
            projectionAngle = Math.atan2(y, x);

            l = Math.sqrt(x*x + y*y);
            // angleRange = Math.atan(r / l);// - angleElpse;
            angleRange = Math.asin(r / l);// - angleElpse;
            if (debug) {
                System.out.println("Middle angle: " + (projectionAngle));
                System.out.println("Range: " + (angleRange));
                System.out.println("Start Range: " + (projectionAngle - angleRange));
                System.out.println("End Range: " + (projectionAngle + angleRange));
            }

            double PI = Math.PI;
            start = projectionAngle - angleRange;
            end = projectionAngle + angleRange;

            // Wrapping logic in case we have angles like 355-365. Here we need 
            // 355 to back to -5 so it covers -5 to 0 and then 0 to 15.
            if (start < -PI) {

                pq.add(maxOverlap.new OpponentPoint(PointType.START, -PI));
                pq.add(maxOverlap.new OpponentPoint(PointType.END, end));

                pq.add(maxOverlap.new OpponentPoint(PointType.START, start + 2*PI));
                pq.add(maxOverlap.new OpponentPoint(PointType.END, PI));

            }
            else if (end > PI) {

                pq.add(maxOverlap.new OpponentPoint(PointType.START, start));
                pq.add(maxOverlap.new OpponentPoint(PointType.END, PI));

                pq.add(maxOverlap.new OpponentPoint(PointType.START, -PI));
                pq.add(maxOverlap.new OpponentPoint(PointType.END, end - 2*PI));

            }
            else {

                pq.add(maxOverlap.new OpponentPoint(PointType.START, start));
                pq.add(maxOverlap.new OpponentPoint(PointType.END, end));

            }


            // // Create the oponent range
            // start = maxOverlap.new OpponentPoint(PointType.START, projectionAngle - angleRange);
            // end = maxOverlap.new OpponentPoint(PointType.END, projectionAngle + angleRange);
            // pq.add(start);
            // pq.add(end);
            

            
        }


        int maxOverlapCount = 0;
        int currentOverlapCount = 0;
        OpponentPoint currentPoint;
        while (!pq.isEmpty()) {
            currentPoint = pq.remove();
            if(currentPoint.type == PointType.START) {
                currentOverlapCount++;
                if(currentOverlapCount > maxOverlapCount) {
                    maxOverlapCount = currentOverlapCount;
                }
            } else {
                currentOverlapCount--;
            }
        }

        System.out.println(maxOverlapCount);
    }


    class OpponentPoint implements Comparable<OpponentPoint> {
        PointType type;
        Double angle;
        
        OpponentPoint(PointType type, Double angle) {
            this.type = type;
            this.angle = angle;
        }

        @Override
        public int compareTo(OpponentPoint o) {
            if (angle < o.angle) return -1;
            else if (angle > o.angle) return 1;
            else {
                if(type == PointType.END) {
                    return -1;
                } else if(o.type == PointType.END) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }
    
}